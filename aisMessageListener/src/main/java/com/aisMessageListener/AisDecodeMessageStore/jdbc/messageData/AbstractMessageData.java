package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.ais.messages.types.IMO;
import dk.tbsalling.aismessages.ais.messages.types.ManeuverIndicator;
import dk.tbsalling.aismessages.ais.messages.types.NavigationStatus;
import dk.tbsalling.aismessages.ais.messages.types.PositionFixingDevice;
import dk.tbsalling.aismessages.ais.messages.types.ShipType;
import dk.tbsalling.aismessages.nmea.exceptions.InvalidMessage;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

public abstract class AbstractMessageData implements MessageDataInterface {
  private final AISMessage message;

  public AbstractMessageData(AISMessage message) {
    this.message = message;
  }

  @Override
  public void processMessage() {
    if (getType() == null) {
      throw new InvalidMessage("Invalid NMEA Message due to NULL message type");
    }

    switch (getType()) {

      // Message Type 1
      case PositionReportClassAScheduled:
        break;

      // Message Type 2
      case PositionReportClassAAssignedSchedule:
        break;

      // Message Type 3
      case PositionReportClassAResponseToInterrogation:
        break;

      // Message Type 5
      case ShipAndVoyageRelatedData:
        break;

      // Unsupported Message
      default:
        throw new UnsupportedMessageType(getTypeId());

    }
  }

  @Override
  public boolean isValidType() {
    return this.getTypeId() > -1;
  }

  @Override
  public int getMMSI() {
    return this.message.getSourceMmsi().getMMSI();
  }

  @Override
  public AISMessageType getType() {
    return this.message.getMessageType();
  }

  @Override
  public int getTypeId() {
    return getType().getCode();
  }

  @Override
  public String getRawNMEA() {
    NMEAMessage[] nmeaSentence = this.message.getNmeaMessages();
    if (hasMultipleParts()) {
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

  @Override
  public boolean hasMultipleParts() {
    return this.message.getNmeaMessages().length > 1;
  }

  @Override
  public String getTimeReceived() {
    Instant time = this.message.getMetadata().getReceived();
    ZonedDateTime UTCtime = time.atZone(ZoneOffset.UTC);
    int year = UTCtime.getYear();
    String month = addZeroToSingleDigitInt(UTCtime.getMonthValue());
    String day = addZeroToSingleDigitInt(UTCtime.getDayOfMonth());
    String hour = addZeroToSingleDigitInt(UTCtime.getHour());
    String minute = addZeroToSingleDigitInt(UTCtime.getMinute());
    String second = addZeroToSingleDigitInt(UTCtime.getSecond());
    return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + " UTC";
  }

  @Override
  public Float getLat() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getLong() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Boolean getAccuracy() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public NavigationStatus getNavStatus() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public ManeuverIndicator getManeuverIndicator() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getSpeedOverGround() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getCourseOverGround() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getHeading() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getRateOfTurn() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public IMO getIMO() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public String getCallsign() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public String getShipName() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public ShipType getShipType() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Integer getToBow() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Integer getToStern() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Integer getToStarboard() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Integer getToPort() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public PositionFixingDevice getPositionFixingDevice() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Float getDraught() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public String getETA() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public String getDestination() {
    throw new UnsupportedMessageType(getTypeId());
  }

  @Override
  public Boolean isDataTerminalReady() {
    throw new UnsupportedMessageType(getTypeId());
  }

  protected String addZeroToSingleDigitInt(int value) {
    String formattedValue = "" + value;
    if (formattedValue.length() == 1) {
      formattedValue = "0" + value;
    }
    return formattedValue;
  }
}
