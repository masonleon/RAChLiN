package com.aisMessageListener.AisDecodeMessageStore.jdbc;

public interface DatabaseInserterInterface {


  void writeMessageData();

  void writeVesselSignature();

  void writeVoyageData();

  void writeVesselData();

  void writeNavigationData();

  void writeGeospatialData();

}
