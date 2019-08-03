package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.ClassAPositionReportData;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.ClassAStaticAndVoyageData;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.UnsupportedMessageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

/**
 * TODO java doc
 */
public class DatabaseInserterFactory {

  /**
   * Converts a decoded AISmessage into a database inserter.  A database inserter contains all logic
   * necessary to add a particular message to each database table.
   *
   * @param message an AISmessage to be added to the database.
   * @return a database inserter corresponding to the provided message's type.
   */
  public static DatabaseInserterInterface getDatabaseInserter(AISMessage message) {
    AISMessageType messageType = message.getMessageType();
    DatabaseInserterInterface inserter;

    switch (messageType) {
      // Message Type 1
      case PositionReportClassAScheduled:
        inserter = new ClassAPositionReportInserter(new ClassAPositionReportData(message));
        break;

      // Message Type 2
      case PositionReportClassAAssignedSchedule:
        inserter = new ClassAPositionReportInserter(new ClassAPositionReportData(message));
        break;

      // Message Type 3
      case PositionReportClassAResponseToInterrogation:
        inserter = new ClassAPositionReportInserter(new ClassAPositionReportData(message));
        break;

      // Message Type 5
      case ShipAndVoyageRelatedData:
        inserter = new ClassAStaticAndVoyageDataInserter(new ClassAStaticAndVoyageData(message));
        break;

      // Unsupported Message
      default:
        inserter = new UnsupportedMessageInserter(new UnsupportedMessageData(message));

    }
    return inserter;
  }
}
