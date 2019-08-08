package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.SQLException;

/**
 * DBMS Agnostic interface that provides layout for functions required for API Database
 * connections.
 */
public interface DatabaseConnectionInterface {

  /**
   * Checks if connection has dropped and reestablishes connection as applicable.  Call before
   * making updates or queries, as connections can go stale over time.  Exits program if connection
   * is not possible.
   *
   * @throws SQLException if timeout occurs
   */
  void connectIfDropped() throws SQLException;

  /**
   * Helper method to close the database connection.  Exits the program if a close is not possible.
   */
  void closeConnection();

  /**
   * Puts the program in a transaction state.
   *
   * @throws SQLException if error occurs during database access.
   * @see #commitTransaction()
   */
  void beginTransaction() throws SQLException;

  /**
   * Commits a started transaction to the database.
   *
   * @throws SQLException if the transaction could not be committed to database.
   * @see #rollBackTransaction()
   */
  void commitTransaction() throws SQLException;

  /**
   * Rolls back a transaction that shouldn't be committed to database.
   *
   * @throws SQLException if the transaction cannot be rolled back.
   */
  void rollBackTransaction() throws SQLException;

  /**
   * Grabs a vessel_signature_id from the vesselSignature table using a fully qualified vesselSignature record.
   *
   * @param mmsi
   * @param imo
   * @param callSign
   * @param name
   * @param vesselTypeID
   *
   * @return an integer vessel_signature_id if found.
   * @throws SQLException if the query fails.
   */
  int getVesselSignatureIdFromFullyQualifiedSignature(int mmsi, String imo, String callSign, String name, String vesselTypeID)
          throws SQLException;

  /**
   * Grabs a vessel_signature_id from the vesselSignature table using an MMSI number.
   *
   * @param mmsi
   * @return an integer vessel_signature_id if found.
   * @throws SQLException if the query fails.
   */
  int getVesselSignatureIdWithMMSI(int mmsi) throws SQLException;

  /**
   * Grabs a vessel_data_id from the vesselData table using values for all four columns.
   *
   * @param toBow
   * @param toStern
   * @param toPort
   * @param toStarboard
   * @return an integer vessel_data_id if found.
   * @throws SQLException if the query fails.
   */
  int getVesselDataIdFromRecord(int toBow, int toStern, int toPort, int toStarboard) throws SQLException;

  /**
   * Inserts one record to the database using a specified sql query string.
   *
   * @param insertSQL
   * @return
   * @throws SQLException
   */
  int insertOneRecord(String insertSQL) throws SQLException;
}