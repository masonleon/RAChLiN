package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODO: consider using transactions for updates.
//TODO: consider strategy for handing SQL exceptions.

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
    Connection con = getValidatedConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

    ResultSet rs = stmt.getGeneratedKeys();
    if (rs.next()) {
      key = rs.getInt(1);
    }

    rs.close();
    stmt.close();
    return key;
  }

  /**
   * Executes provided SQL query and returns an integer from the first returned row in the specified
   * column.  Returns -1 if the query produces no results.
   *
   * @param selectSQL a SQL query.
   * @param column the column number of the int to return (1 for primary keys).
   * @return an integer from the specified column in the first row of the query result.
   * @throws SQLException if query or column number are invalid.
   */
  public int queryOneInt(String selectSQL, int column) throws SQLException {
    int result = -1;
    Connection con = getValidatedConnection();
    Statement stmt = con.createStatement();

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
   * @param column the column number of the int to return (1 for primary keys).
   * @return a String from the specified column in the first row of the query result.
   * @throws SQLException if query or column number are invalid.
   */
  public String queryOneString(String selectSQL, int column) throws SQLException {
    String result = "";
    Connection con = getValidatedConnection();
    Statement stmt = con.createStatement();

    ResultSet rs = stmt.executeQuery(selectSQL);
    if (rs.next()) {
      result = rs.getString(column);
    }

    rs.close();
    stmt.close();
    return result;
  }
}



