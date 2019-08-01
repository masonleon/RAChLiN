package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.SQLException;

/**
 * DBMS Agnostic interface that provides layout for functions required for API Database
 * connections.
 */
public interface DatabaseConnectionInterface {

  void connectIfDropped() throws SQLException;

  void closeConnection();

  void beginTransaction() throws SQLException;

  void commitTransaction() throws SQLException;

  void rollBackTransaction() throws SQLException;

  int queryOneInt(String selectSQL, int column) throws SQLException;

  String queryOneString(String selectSQL, int column) throws SQLException;

  int checkVesselSig(int mmsi, int imo, String callSign, String name, int vesselTypeID)
          throws SQLException;

  int checkVesselSigWithNulls(int mmsi, int vesselTypeID) throws SQLException;

  int insertOneRecord(String insertSQL) throws SQLException;


}