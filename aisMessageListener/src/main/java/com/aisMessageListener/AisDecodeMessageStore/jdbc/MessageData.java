package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.time.Instant;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.nmea.exceptions.InvalidMessage;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

import static java.sql.Types.NULL;

// This is a starting point a utilities class that gets data from an AISmessage.
// Could be used in conjunction with IDatabaseInserter or as
// a starting point for classes that implement the interface.
public abstract class MessageData {

  private AISMessage message;
  private Instant timeRecieved;
  private boolean isValidMsg;
  private String rawNMEA;
  private int msgTypeId;
  private int geospatialDataId;
  private int navigationDataId;
  private int voyageDataId;
  private int vesselSignatureId;
  private int vesselDataId;
  private int mmsi;

  private String time_received_station;
  private String msg_nmea_raw;
  private String msg_nmea_decoded;

  public MessageData(AISMessage message) {
    this.message = message;

    this.timeRecieved = this.message.getMetadata().getReceived();
    this.isValidMsg = this.getIsValidMsg();
    this.rawNMEA = this.getRawNMEA();
    this.msgTypeId = this.getMsgTypeId();
    this.geospatialDataId = NULL;
    this.navigationDataId = NULL;
    this.voyageDataId = NULL;
    this.vesselSignatureId = NULL;
    this.vesselDataId = NULL;
    this.mmsi = this.getMMSI();
  }


  public void checkIsSupported() {
    AISMessageType messageType = message.getMessageType();
    int messageTypeID = message.getMessageType().getCode();

    if (messageType != null) {
      switch (messageType) {

        //Message Type 1
        case PositionReportClassAScheduled:


          break;

        //Message Type 2
        case PositionReportClassAAssignedSchedule:


          break;

        //Message Type 3
        case PositionReportClassAResponseToInterrogation:


          break;

        //Message Type 5
        case ShipAndVoyageRelatedData:


          break;

        //Unsupported Message
        default:
          throw new UnsupportedMessageType(messageType.getCode());
      }
    } else {
      //isInvalid Message field
      throw new InvalidMessage("Invalid NMEA message");
    }

  }

  public boolean getIsValidMsg() {
    return this.msgTypeId != -1;
  }

  public int getMMSI() {
    return this.message.getSourceMmsi().getMMSI();
  }

  public int getMsgTypeId() {
    return this.message.getMessageType().getCode();
  }

  public String getRawNMEA() {
    NMEAMessage[] nmeaSentence = this.message.getNmeaMessages();
    if (nmeaSentence.length > 1) {
      StringBuilder messages = new StringBuilder();

      for (int i = 0; i < nmeaSentence.length; i++) {
        messages.append(nmeaSentence[i].getRawMessage());

        if (i != nmeaSentence.length - 1) {
          messages.append(" | ");
        }
      }
      return messages.toString();
    } else {
      return nmeaSentence[0].getRawMessage();
    }
  }
}
