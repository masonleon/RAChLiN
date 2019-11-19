package io.seeport.rachlin.aisMessageListener.jdbc.dBinserter;

import io.seeport.rachlin.aisMessageListener.jdbc.messageData.ClassAPositionReportData;
import io.seeport.rachlin.aisMessageListener.jdbc.messageData.ClassAStaticAndVoyageData;
import io.seeport.rachlin.aisMessageListener.jdbc.messageData.UnsupportedMessageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

/**
 * Factory to handle an incoming AISMessage and parse its type in order to construct a
 * DatabaseInserter object.
 */
public final class DatabaseInserterFactory {

  /**
   * Converts a decoded AISmessage into a database inserter.  A database inserter contains all logic
   * necessary to add a particular message to each database table.
   *
   * @param message an AISmessage to be added to the database.
   * @return a database inserter corresponding to the provided message's type.
   */
  public static DatabaseInserterInterface getDatabaseInserter(AISMessage message) {
    AISMessageType messageType = message.getMessageType();

    switch (messageType) {
      // Message Type 1, 2, and 3, respectively
      case PositionReportClassAScheduled:
      case PositionReportClassAAssignedSchedule:
      case PositionReportClassAResponseToInterrogation:
        return new ClassAPositionReportInserter(new ClassAPositionReportData(message));

      // Message Type 5
      case ShipAndVoyageRelatedData:
        return new ClassAStaticAndVoyageDataInserter(new ClassAStaticAndVoyageData(message));

      // Unsupported Message
      default:
        return new UnsupportedMessageInserter(new UnsupportedMessageData(message));
    }
  }
}
