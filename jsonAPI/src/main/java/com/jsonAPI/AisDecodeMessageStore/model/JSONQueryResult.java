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
  private static JSONQueryResult instance;

  public static JSONQueryResult getInstance() {
    if (instance == null) {
      JSONQueryResult.instance = new JSONQueryResult();
    }
    return JSONQueryResult.instance;
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
      String query = getQuery();
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
    return "SELECT vessel_signature_id,\n" +
            "       gd.coord,\n" +
            "       nd.heading\n" +
            "FROM message_data md\n" +
            "       JOIN geospatial_data gd USING (geospatial_data_id)\n" +
            "       JOIN navigation_data nd USING (navigation_data_id)\n" +
            "LIMIT 1000;";
  }
}

