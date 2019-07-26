package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import java.sql.Connection;

public interface DatabaseInserterInterface {


  void writeMessageData(Connection connection);

  void writeVesselSignature(Connection connection);

  void writeVoyageData(Connection connection);

  void writeVesselData(Connection connection);

  void writeNavigationData(Connection connection);

  void writeGeospatialData(Connection connection);

}
