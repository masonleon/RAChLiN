package com.jsonAPI.AisDecodeMessageStore.model;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JSONQueryResult {
  // TODO Should this port number be hardcoded like this?
  private static final String port = "5432";
  private String databseURL;
  private Properties properties;
  private Connection connection;
  private TimeSpan timeSpan;
  private int accesses;
  private static JSONQueryResult instance;

  public static JSONQueryResult getInstance() {
    if (instance == null) {
      JSONQueryResult.instance = new JSONQueryResult();
    }

    JSONQueryResult.instance.increment();
    return JSONQueryResult.instance;
  }

  private void increment() {
    accesses += 1;
  }

  public String getTimeSpanStatus() {
    return timeSpan == null ? "NULL " + accesses : timeSpan.toString() + " " + accesses;
  }

  // TODO Find a more secure way to get database credentials.
  private JSONQueryResult() {
    String databaseName;
    String username = null;
    String password;
    String catalinaHome = System.getenv("CATALINA_HOME");
    try (BufferedReader br = new BufferedReader(
            new FileReader(catalinaHome + "/webapps/credentials/database_credentials"))) {
      databseURL = br.readLine();
      databaseName = br.readLine();
      username = br.readLine();
      password = br.readLine();

      this.databseURL = "jdbc:postgresql://" + databseURL + ":" + port + "/" + databaseName;
      this.properties = new Properties();
      this.properties.setProperty("user", username);
      this.properties.setProperty("password", password);
      connectToDatabase();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void connectToDatabase() {
    try {
      Class.forName("org.postgresql.Driver");
      this.connection = DriverManager.getConnection(this.databseURL, this.properties);
    } catch (SQLException e) {
      System.err.println("Error while connecting to database.\n");
      e.printStackTrace();
      System.exit(1);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  public String getMessage() {
    String result = "";
    Statement stmt = null;
    JSONArray jsonArray = null;
    try {
      stmt = this.connection.createStatement();
      // TODO add try catch finally blocks here
      // TODO remove 1000 limit
      String query = timeSpan == null ? getQuery() : getQueryWithTimeSpan();
      ResultSet rs = stmt.executeQuery(query);
      jsonArray = ResultSetConverter.apply(rs);
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      result = e.toString();
    }

    if (jsonArray != null) {
      result = jsonArray.toString();
    }

    return result;
  }

  private String getQuery() {
    // TODO copy from report reasoning behind this query
    // TODO replace with the more full-fledged query and use a pop for descriptions.
    // TODO figure out how to do heading from
    return "select vessel_signature_id, gd.coord, nd.heading\n" +
            "from message_data\n" +
            "join msg_5_signature using (vessel_signature_id)\n" +
            "join geospatial_data gd on message_data.geospatial_data_id = gd.geospatial_data_id\n" +
            "join navigation_data nd on message_data.navigation_data_id = nd.navigation_data_id\n" +
            "order by vessel_signature_id, heading;";
  }

  private String getQueryWithTimeSpan() {
    // TODO Make sure this works.
    String startTime = timeSpan.getStartTime();
    String endTime = timeSpan.getEndTime();

    return "select md.vessel_signature_id, gd.coord, nd.heading, md.time_received\n" +
            "from message_data md\n" +
            "         join msg_5_signature using (vessel_signature_id)\n" +
            "         join geospatial_data gd using (geospatial_data_id)\n" +
            "         join navigation_data nd using(navigation_data_id)\n" +
            "where (DATE_PART('day', '" + endTime + "'::timestamp - md.time_received) * 24 +\n" +
            "       DATE_PART('hour', '" + endTime + "'::timestamp - md.time_received)) * 60 +\n" +
            "      DATE_PART('minute', '" + endTime + "'::timestamp - md.time_received) >= 0 and\n" +
            "      (DATE_PART('day', md.time_received - '" + startTime + "'::timestamp) * 24 +\n" +
            "       DATE_PART('hour', md.time_received - '" + startTime + "'::timestamp)) * 60 +\n" +
            "      DATE_PART('minute', md.time_received - '" + startTime + "'::timestamp) >= 0;";
  }

  public void setTimeSpan(TimeSpan timeSpan) {
    this.timeSpan = timeSpan;
  }
}

