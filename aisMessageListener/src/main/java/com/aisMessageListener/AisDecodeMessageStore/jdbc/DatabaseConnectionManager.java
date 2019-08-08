package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * API for handling interactions with a PostgreSQL database.  Make this class available to any
 * methods that require database access.
 */
public class DatabaseConnectionManager extends AbstractDatabaseConnectionManager {
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
    super(host, databaseName, username, password);
  }

  /**
   * Inserts a single record and returns the value of the first column as an int if applicable
   * (useful for determing an auto-assigned primary key).
   *
   * @param insertSQL a SQL insert statement.
   * @return the primary key of the inserted row, or -1.
   * @throws SQLException if SQL statement is invalid.
   */
  public int insertOneRecord(String insertSQL) throws SQLException {
    int key = -1;
    Statement stmt = this.connection.createStatement();
    stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

    ResultSet rs = stmt.getGeneratedKeys();
    if (rs.next()) {
      key = rs.getInt(1);
    }

    rs.close();
    stmt.close();
    return key;
  }

  @Override
  public int getVesselSignatureIdFromFullyQualifiedSignature(
          int mmsi, String imo, String callSign, String name, String vesselTypeID) throws SQLException {

    String sqlQuery =
            "SELECT vessel_signature_id from vessel_signature WHERE " +
                    "mmsi = " + mmsi + " and " +
                    "imo = " + imo + " and " +
                    "call_sign = '" + callSign + "' and " +
                    "name = '" + name + "' and " +
                    "vessel_type_id = " + vesselTypeID;

    return queryOneInt(sqlQuery, 1);
  }

  @Override
  public int getVesselSignatureIdWithMMSI(int mmsi)
          throws SQLException {

    String sqlQuery =
            "SELECT vessel_signature_id from vessel_signature WHERE mmsi = " + mmsi;
    return queryOneInt(sqlQuery, 1);
  }

  @Override
  public int getVesselDataIdFromRecord(int toBow, int toStern, int toPort, int toStarboard)
          throws SQLException {

    String sqlQuery =
            "SELECT vessel_data_id from vessel_data WHERE " +
                    "to_bow = " + toBow + " and " +
                    "to_stern = " + toStern + " and " +
                    "to_port = " + toPort + " and " +
                    "to_starboard = " + toStarboard;

    return queryOneInt(sqlQuery, 1);
  }

  /**
   * Executes provided SQL query and returns an integer from the first returned row in the specified
   * column.  Returns -1 if the query produces no results.
   *
   * @param selectSQL a SQL query.
   * @param column    the column number of the int to return (1 for primary keys).
   * @return an integer from the specified column in the first row of the query result.
   * @throws SQLException if query or column number are invalid.
   */
  private int queryOneInt(String selectSQL, int column) throws SQLException {
    int result = -1;
    Statement stmt = this.connection.createStatement();

    ResultSet rs = stmt.executeQuery(selectSQL);
    if (rs.next()) {
      result = rs.getInt(column);
    }

    rs.close();
    stmt.close();
    return result;
  }

  /**
   * Executes provided SQL query and returns a String from the first returned row in the specified
   * column.  Returns empty String if the query produces no results.
   *
   * @param selectSQL a SQL query.
   * @param column    the column number of the int to return (1 for primary keys).
   * @return a String from the specified column in the first row of the query result.
   * @throws SQLException if query or column number are invalid.
   */
  public String queryOneString(String selectSQL, int column) throws SQLException {
    String result = "";
    Statement stmt = this.connection.createStatement();

    ResultSet rs = stmt.executeQuery(selectSQL);
    if (rs.next()) {
      result = rs.getString(column);
    }

    rs.close();
    stmt.close();
    return result;
  }

  @Override
  public void beginTransaction() throws SQLException {
    connection.setAutoCommit(false);
  }

  @Override
  public void commitTransaction() throws SQLException {
    connection.commit();
    connection.setAutoCommit(true);
  }

  @Override
  public void rollBackTransaction() throws SQLException {
    connection.rollback();
    connection.setAutoCommit(true);
  }
}



