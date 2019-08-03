package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.SQLException;

/**
 * DBMS Agnostic interface that provides layout for functions required for API Database
 * connections.
 */
public interface DatabaseConnectionInterface {

  /**
   * TODO java doc
   */
  void connectIfDropped() throws SQLException;

  /**
   * TODO java doc
   */
  void closeConnection();

  /**
   * TODO java doc
   */
  void beginTransaction() throws SQLException;

  /**
   * TODO java doc
   */
  void commitTransaction() throws SQLException;

  /**
   * TODO java doc
   */
  void rollBackTransaction() throws SQLException;

  /**
   * TODO java doc
   */
  int queryOneInt(String selectSQL, int column) throws SQLException;

  /**
   * TODO java doc
   */
  String queryOneString(String selectSQL, int column) throws SQLException;

  /**
   * TODO java doc
   */
  int checkVesselSig(int mmsi, int imo, String callSign, String name, int vesselTypeID)
          throws SQLException;

  /**
   * TODO java doc
   */
  int checkVesselSigWithNulls(int mmsi, int vesselTypeID) throws SQLException;

  /**
   * TODO java doc
   */
  int checkVesselData(int toBow, int toStern, int toPort, int toStarboard) throws SQLException;

  /**
   * TODO java doc
   */
  int insertOneRecord(String insertSQL) throws SQLException;
}