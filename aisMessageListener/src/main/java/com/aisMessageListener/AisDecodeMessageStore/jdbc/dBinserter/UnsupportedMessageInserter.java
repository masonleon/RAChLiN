package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }
}