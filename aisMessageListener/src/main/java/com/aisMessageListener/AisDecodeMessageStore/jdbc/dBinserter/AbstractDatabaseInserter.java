package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  private MessageDataInterface message;

  public AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;
  }


}
