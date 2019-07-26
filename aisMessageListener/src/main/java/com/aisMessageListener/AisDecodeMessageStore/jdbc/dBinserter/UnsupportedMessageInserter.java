package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }

  @Override
  public void writeMessageData() {

  }

  @Override
  public void writeVesselSignature() {

  }

  @Override
  public void writeVoyageData() {

  }

  @Override
  public void writeVesselData() {

  }

  @Override
  public void writeNavigationData() {

  }

  @Override
  public void writeGeospatialData() {

  }
}
