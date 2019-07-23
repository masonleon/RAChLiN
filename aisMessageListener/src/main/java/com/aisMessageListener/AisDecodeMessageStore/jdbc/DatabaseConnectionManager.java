package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//TODO: this should implement an interface.  Prof. Rachlin stated that he would be checking if our
// database connection "API" is hidden behind a DBMS-agnostic interface.

/**
 * API for handling interactions with a PostgreSQL database.  Make this class available to any
 * methods that require database access.
 */
public class DatabaseConnectionManager {

  private static final String port = "5432";
  private final String url;
  private final Properties properties;
  private Connection connection;


  /**
   * Create a database connection manager using the provided database information.
   *
   * @param host         the database host. An AWS endpoint, or localhost for testing.
   * @param databaseName the name of the database.
   * @param username     the database username.
   * @param password     the database password.
   */
  public DatabaseConnectionManager(String host, String databaseName, String username,
                                   String password) {
    this.url = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
    this.connection = null;
  }


  /**
   * Returns a Connection to this database.  Exits the program if a connection is not possible.
   *
   * @return a Connection to the database.
   */
  public Connection getConnection() {
    if (this.connection == null) {
      return connectToDatabase();
    }
    return this.connection;
  }

  /**
   * Returns a Connection to this database.  Same as getConnection() except connection is tested for
   * validity first (connections can go stale). Testing a connection can affect performance (many
   * systems test by performing a SQL query) so consider using this method only to resolve
   * SQLExceptions.  Exits the program if a connection is not possible.
   *
   * @return a Connection to the database.
   * @throws SQLException if isValid timeout is negative (e.g., exception does not need to be
   *                      handled here).
   */
  public Connection getTestedConnection() throws SQLException {
    if (this.connection == null) {
      return connectToDatabase();
    }
    if (this.connection.isValid(2)) {
      return this.connection;
    }
    closeConnection();
    return connectToDatabase();
  }

  //Sample insert method.
  //TODO: consider if we will need transactions for our db update methods.
  //TODO: consider handling for when SQL exception is thrown.  We would prefer it if our
  // app does not exit.  getTestedConnection() can potentially resolve stale connection issues.
  public int insertOneRecord(String insertSQL) throws SQLException {
    int key = -1;
    Connection con = getConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

    ResultSet rs = stmt.getGeneratedKeys();
    if (rs.next()) {
      key = rs.getInt(1);
    }

    rs.close();
    stmt.clearBatch();

    return key;
  }


  /**
   * Helper method to get a database connection. Exits the program if a connection is not possible.
   *
   * @return a new connection to the database.
   */
  private Connection connectToDatabase() {
    try {
      this.connection = DriverManager.getConnection(this.url, this.properties);
    } catch (SQLException e) {
      System.err.println("Error while connecting to database.\n");
      System.exit(1);
    }
    return this.connection;
  }

  /**
   * Helper method to close the database connection.  Exits the program if a close is not possible.
   */
  private void closeConnection() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      System.err.println("Error while closing connection.\n");
      System.exit(1);
    }
  }


}



