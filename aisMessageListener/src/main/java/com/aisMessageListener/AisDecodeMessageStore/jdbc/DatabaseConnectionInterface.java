package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DBMS Agnostic interface that provides layout for functions required for API Database
 * connections.
 */
public interface DatabaseConnectionInterface {

  Connection getValidatedConnection() throws SQLException;

  Connection getConnection();

  void closeConnection();

  int queryOneInt(String selectSQL, int column) throws SQLException;

  String queryOneString(String selectSQL, int column) throws SQLException;

  int insertOneRecord(String insertSQL) throws SQLException;



}