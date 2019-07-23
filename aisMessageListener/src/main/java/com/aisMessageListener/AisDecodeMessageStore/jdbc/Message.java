package com.aisMessageListener.AisDecodeMessageStore.jdbc;


import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.ais.messages.types.MMSI;
import dk.tbsalling.aismessages.nmea.exceptions.InvalidMessage;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;


public class Message {

  private AISMessage message;
  private long id;
  private int mmsi;
  private String msgType;
  private String time_received_station;
  // private int time_sent_vessel;
  private String msg_nmea_raw;
  private String msg_nmea_decoded;

  public Message(AISMessage message) {
    this.message = message;


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


  public MMSI getMmsi() {
    return this.message.getSourceMmsi();
  }


  public String getMsgType() {
    return this.message.getMessageType().getValue();
  }


  public NMEAMessage[] getNMEAraw() {
    return this.message.getNmeaMessages();
  }


  public String getMsg_nmea_decoded() {
    return this.message.toString();
  }

  public void setMsg_nmea_decoded(String msg_nmea_decoded) {
    this.msg_nmea_decoded = msg_nmea_decoded;
  }
}
