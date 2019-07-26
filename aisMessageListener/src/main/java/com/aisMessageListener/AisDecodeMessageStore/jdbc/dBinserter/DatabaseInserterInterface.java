package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;

import java.sql.SQLException;

public interface DatabaseInserterInterface {

  void writeMessageData(DatabaseConnectionInterface con) throws SQLException;

  void writeVesselSignature(DatabaseConnectionInterface con) throws SQLException;

  void writeVoyageData(DatabaseConnectionInterface con) throws SQLException;

  void writeVesselData(DatabaseConnectionInterface con) throws SQLException;

  void writeNavigationData(DatabaseConnectionInterface con) throws SQLException;

  void writeGeospatialData(DatabaseConnectionInterface con) throws SQLException;


}
