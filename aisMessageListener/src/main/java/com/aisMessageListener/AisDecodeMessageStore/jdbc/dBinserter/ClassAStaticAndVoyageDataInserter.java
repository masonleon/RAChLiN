package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

import java.sql.Connection;

public class ClassAStaticAndVoyageDataInserter extends AbstractDatabaseInserter {

  public ClassAStaticAndVoyageDataInserter(MessageDataInterface message){
    super(message);
  }


  @Override
  public void writeMessageData(Connection connection) {

  }

  @Override
  public void writeVesselSignature(Connection connection) {

  }

  @Override
  public void writeVoyageData(Connection connection) {

  }

  @Override
  public void writeVesselData(Connection connection) {

  }

  @Override
  public void writeNavigationData(Connection connection) {

  }

  @Override
  public void writeGeospatialData(Connection connection) {

  }
}
