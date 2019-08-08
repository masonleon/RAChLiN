package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

/**
 * Extension of the DatabaseInserter to handle specific functionality related to currently unsupported messages.
 */
class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }
}
