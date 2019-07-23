package com.aisMessageListener.AisDecodeMessageStore.jdbc;

public interface IDBInsertion {

  void writeMessageData();

  void writeVesselSignature();

  void writeVoyageData();

  void writeVesselData();

  void writeNavigationData();

  void writeGeospatialData();

}
