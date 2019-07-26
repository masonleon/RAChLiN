package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  private MessageDataInterface message;

  protected AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;
  }


}
