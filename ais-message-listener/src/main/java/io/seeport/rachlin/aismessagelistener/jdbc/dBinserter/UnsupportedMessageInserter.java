package io.seeport.rachlin.aismessagelistener.jdbc.dBinserter;

import io.seeport.rachlin.aismessagelistener.jdbc.messageData.MessageDataInterface;

/**
 * Extension of the DatabaseInserter to handle specific functionality related to currently
 * unsupported messages.
 */
class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  // Currently, all logic is implemented in the abstract class. This class may support additional
  // functionality in the future.
  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }
}
