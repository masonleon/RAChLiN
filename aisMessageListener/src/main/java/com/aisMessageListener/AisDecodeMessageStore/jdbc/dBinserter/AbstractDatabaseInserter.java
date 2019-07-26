package com.aisMessageListener.AisDecodeMessageStore.jdbc;


public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  private MessageDataInterface message;

  public AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;
  }


}
