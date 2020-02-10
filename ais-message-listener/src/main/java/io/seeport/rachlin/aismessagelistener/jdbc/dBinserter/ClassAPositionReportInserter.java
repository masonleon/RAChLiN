package io.seeport.rachlin.aismessagelistener.jdbc.dBinserter;

import io.seeport.rachlin.aismessagelistener.jdbc.messageData.MessageDataInterface;

/**
 * Extension of the DatabaseInserter to handle specific functionality related to
 * ClassAPositionReport messages (types 1, 2, or 3).
 */
class ClassAPositionReportInserter extends AbstractDatabaseInserter {

  // Currently, all logic is implemented in the abstract class. This class may support additional
  // functionality in the future.
  ClassAPositionReportInserter(MessageDataInterface message) {
    super(message);
  }
}


