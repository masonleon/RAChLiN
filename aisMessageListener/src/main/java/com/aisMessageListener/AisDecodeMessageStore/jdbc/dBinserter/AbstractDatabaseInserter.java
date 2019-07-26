package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

   private MessageDataInterface message;
   protected DatabaseConnectionInterface connection;

   protected AbstractDatabaseInserter(MessageDataInterface message) {
     this.message = message;
   }

   @Override
   public void attachConnection(DatabaseConnectionInterface conn) {
       this.connection = conn;
   }
}
