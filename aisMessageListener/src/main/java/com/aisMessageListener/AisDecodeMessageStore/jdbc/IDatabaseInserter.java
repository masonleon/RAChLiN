package com.aisMessageListener.AisDecodeMessageStore.jdbc;

public interface IDatabaseInserter {

  // Some possible insertion methods.  Classes that implement this will take an AISmessage and
  // and DatabaseConnectionManager as input.  Each implementing class will contain update logic for
  // a single message type and use the connection manager's API to make the updates.
  void writeMessageData();

  void writeVesselSignature();

  void writeVoyageData();

  void writeVesselData();

  void writeNavigationData();

  void writeGeospatialData();

}
