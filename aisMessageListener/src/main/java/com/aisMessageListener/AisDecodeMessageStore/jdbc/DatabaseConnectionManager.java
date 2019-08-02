package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

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

  public int checkVesselSig(int mmsi, int imo, String callSign, String name, int vesselTypeID)
          throws SQLException {

    String sqlQuery =
            "SELECT vessel_signature_id from vessel_signature WHERE " +
                    "mmsi = " + mmsi + " and " +
                    "imo = " + imo + " and " +
                    "call_sign = '" + callSign + "' and " +
                    "name = '" + name + "' and " +
                    "vessel_type_id = " + vesselTypeID;

    return queryOneInt(sqlQuery, 1);
  }

  public int checkVesselSigWithNulls(int mmsi, int vesselTypeID)
          throws SQLException {

    String sqlQuery =
            "SELECT vessel_signature_id from vessel_signature WHERE " +
                    "mmsi = " + mmsi + " and " +
                    "vessel_type_id = " + vesselTypeID;

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
  public int queryOneInt(String selectSQL, int column) throws SQLException {
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


  @Override
  public Statement createStatement() throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    return null;
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {
    return null;
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {

  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return false;
  }

  @Override
  public void commit() throws SQLException {

  }

  @Override
  public void rollback() throws SQLException {

  }

  @Override
  public void close() throws SQLException {

  }

  @Override
  public boolean isClosed() throws SQLException {
    return false;
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return null;
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {

  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return false;
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {

  }

  @Override
  public String getCatalog() throws SQLException {
    return null;
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {

  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return 0;
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  @Override
  public void clearWarnings() throws SQLException {

  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return null;
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

  }

  @Override
  public void setHoldability(int holdability) throws SQLException {

  }

  @Override
  public int getHoldability() throws SQLException {
    return 0;
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return null;
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    return null;
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {

  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {

  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return null;
  }

  @Override
  public Clob createClob() throws SQLException {
    return null;
  }

  @Override
  public Blob createBlob() throws SQLException {
    return null;
  }

  @Override
  public NClob createNClob() throws SQLException {
    return null;
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return null;
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return false;
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {

  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {

  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return null;
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return null;
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return null;
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return null;
  }

  @Override
  public void setSchema(String schema) throws SQLException {

  }

  @Override
  public String getSchema() throws SQLException {
    return null;
  }

  @Override
  public void abort(Executor executor) throws SQLException {

  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return 0;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}



