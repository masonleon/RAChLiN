package org.apache.struts.helloworld.model;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JSONQueryResult {
  private static final String port = "5432";
  private String message;
  private String databseURL;
  private Properties properties;
  private Connection connection;
  private static JSONQueryResult instance;

  public static JSONQueryResult getInstance() {
    if (instance == null) {
      JSONQueryResult.instance = new JSONQueryResult();
    }
    return JSONQueryResult.instance;
  }

  private JSONQueryResult() {
    // TODO Remove password from database connection
    // TODO make a private file to pull from that isn't committed to GitHub

    String databaseName;
    String username = null;
    String password;
    try (BufferedReader br = new BufferedReader(new FileReader("database_credentials"))) {
      databseURL = br.readLine();
      databaseName = br.readLine();
      username = br.readLine();
      password = br.readLine();

      this.databseURL = "jdbc:postgresql://" + databseURL + ":" + port + "/" + databaseName;
      this.properties = new Properties();
      this.properties.setProperty("user", username);
      this.properties.setProperty("password", password);
      connectToDatabase();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
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
    Statement stmt = null;
    JSONArray jsonArray = null;
    try {
      stmt = this.connection.createStatement();

      // TODO add try catch finally blocks here
      String query = "SELECT md.time_received as \"time\",\n" +
              "       gd.coord,\n" +
              "       gd.accuracy,\n" +
              "       nd.speed_over_ground as \"sog\",\n" +
              "       nd.course_over_ground as \"cog\",\n" +
              "       nd.heading as \"hdg\",\n" +
              "       nd.rate_of_turn as \"rot\",\n" +
              "       ns.description as \"nav stat\",\n" +
              "       mi.description as \"maneuver ind\",\n" +
              "       sig.mmsi,\n" +
              "       sig.imo,\n" +
              "       sig.name,\n" +
              "       sig.call_sign,\n" +
              "       sig.loa,\n" +
              "       sig.beam,\n" +
              "       sig.ais_vessel_code,\n" +
              "       sig.ais_ship_cargo_classification as \"ship/cargo classification\",\n" +
              "       sig.vessel_group,\n" +
              "       sig.note as \"ship/cargo note\"\n" +
              "FROM message_data md\n" +
              "         JOIN vessel_signature vs USING (vessel_signature_id)\n" +
              "         JOIN msg_5_signature sig ON (vs.mmsi = sig.mmsi)\n" +
              "         JOIN geospatial_data gd USING (geospatial_data_id)\n" +
              "         JOIN navigation_data nd USING (navigation_data_id)\n" +
              "         JOIN nav_status ns USING (nav_status_id)\n" +
              "         JOIN maneuver_indicator mi USING (maneuver_indicator_id)\n" +
              "ORDER BY time_received DESC\n" +
              "LIMIT 100;";
      ResultSet rs = stmt.executeQuery(query); // TODO
      jsonArray = ResultSetConverter.apply(rs);
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    String result = jsonArray == null ? "mierde" : jsonArray.toString();
    return result;
  }

}

