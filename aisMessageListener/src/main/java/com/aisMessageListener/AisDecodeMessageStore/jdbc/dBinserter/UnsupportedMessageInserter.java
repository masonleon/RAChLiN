package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }

  @Override
  public void writeMessageData(DatabaseConnectionInterface con) {

  }

  @Override
  public void writeVesselSignature(DatabaseConnectionInterface con) {

  }

  @Override
  public void writeVoyageData(DatabaseConnectionInterface con) {

  }

  @Override
  public void writeVesselData(DatabaseConnectionInterface con) {

  }

  @Override
  public void writeNavigationData(DatabaseConnectionInterface con) {

  }

  @Override
  public void writeGeospatialData(DatabaseConnectionInterface con) {

  }
}
