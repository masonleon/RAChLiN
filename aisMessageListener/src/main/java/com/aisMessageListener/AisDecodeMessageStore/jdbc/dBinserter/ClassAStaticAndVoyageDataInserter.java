package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

/**
 * Extension of the DatabaseInserter to handle specific functionality related to
 * ClassAStaticAndVoyageData messages (type 5).
 */
class ClassAStaticAndVoyageDataInserter extends AbstractDatabaseInserter {

  // Currently, all logic is implemented in the abstract class. This class may support additional
  // functionality in the future.
  ClassAStaticAndVoyageDataInserter(MessageDataInterface message) {
    super(message);
  }
}

