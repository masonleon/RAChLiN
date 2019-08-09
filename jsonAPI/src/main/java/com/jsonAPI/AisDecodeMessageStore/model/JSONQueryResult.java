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
    return "SELECT md.vessel_signature_id,\n" +
            "       time_received,\n" +
            "       gd.coord,\n" +
            "       gd.accuracy,\n" +
            "       nd.speed_over_ground,\n" +
            "       nd.course_over_ground,\n" +
            "       nd.heading,\n" +
            "       nd.rate_of_turn,\n" +
            "       ns.description as \"navigation_status\",\n" +
            "       mi.description as \"maneuver_indicator\",\n" +
            "       sig.name,\n" +
            "       sig.call_sign,\n" +
            "       sig.loa as \"length_overall\",\n" +
            "       sig.beam as \"beam_width\",\n" +
            "       sig.ais_vessel_code,\n" +
            "       sig.ais_ship_cargo_classification as \"ship_classification\",\n" +
            "       sig.vessel_group,\n" +
            "       sig.note as \"note\"\n" +
            "FROM message_data md\n" +
            "         JOIN vessel_signature vs USING (vessel_signature_id)\n" +
            "         JOIN msg_5_signature sig ON (vs.mmsi = sig.mmsi)\n" +
            "         JOIN geospatial_data gd USING (geospatial_data_id)\n" +
            "         JOIN navigation_data nd USING (navigation_data_id)\n" +
            "         JOIN nav_status ns USING (nav_status_id)\n" +
            "         JOIN maneuver_indicator mi USING (maneuver_indicator_id)\n" +
            "LIMIT 5000;";
  }

  private String getQueryWithTimeSpan() {
    // TODO Make sure this works.
    String startTime = timeSpan.getStartTime();
    String endTime = timeSpan.getEndTime();

    return "SELECT md.vessel_signature_id,\n" +
            "       time_received,\n" +
            "       gd.coord,\n" +
            "       gd.accuracy,\n" +
            "       nd.speed_over_ground,\n" +
            "       nd.course_over_ground,\n" +
            "       nd.heading,\n" +
            "       nd.rate_of_turn,\n" +
            "       ns.description as \"navigation_status\",\n" +
            "       mi.description as \"maneuver_indicator\",\n" +
            "       sig.name,\n" +
            "       sig.call_sign,\n" +
            "       sig.loa as \"length_overall\",\n" +
            "       sig.beam as \"beam_width\",\n" +
            "       sig.ais_vessel_code,\n" +
            "       sig.ais_ship_cargo_classification as \"ship_classification\",\n" +
            "       sig.vessel_group,\n" +
            "       sig.note as \"note\"\n" +
            "FROM message_data md\n" +
            "         JOIN vessel_signature vs USING (vessel_signature_id)\n" +
            "         JOIN msg_5_signature sig ON (vs.mmsi = sig.mmsi)\n" +
            "         JOIN geospatial_data gd USING (geospatial_data_id)\n" +
            "         JOIN navigation_data nd USING (navigation_data_id)\n" +
            "         JOIN nav_status ns USING (nav_status_id)\n" +
            "         JOIN maneuver_indicator mi USING (maneuver_indicator_id)\n" +
            "WHERE (DATE_PART('day', '" + endTime + "'::timestamp - md.time_received) * 24 +\n" +
            "       DATE_PART('hour', '" + endTime + "'::timestamp - md.time_received)) * 60 +\n" +
            "      DATE_PART('minute', '" + endTime + "'::timestamp - md.time_received) >= 0 and\n" +
            "      (DATE_PART('day', md.time_received - '" + startTime + "'::timestamp) * 24 +\n" +
            "       DATE_PART('hour', md.time_received - '" + startTime + "'::timestamp)) * 60 +\n" +
            "      DATE_PART('minute', md.time_received - '" + startTime + "'::timestamp) >= 0" +
            "LIMIT 5000;";
  }

  public void setTimeSpan(TimeSpan timeSpan) {
    this.timeSpan = timeSpan;
  }
}

