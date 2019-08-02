package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;


public abstract class AbstractMessageData implements MessageDataInterface {
  private final AISMessage message;

  public AbstractMessageData(AISMessage message) {
    this.message = message;
  }

  @Override
  public Boolean isValidType() {
    return this.message.getMessageType().getCode() > -1;
  }

  @Override
  public Integer getMMSI() {
    return this.message.getSourceMmsi().getMMSI();
  }

  @Override
  public AISMessageType getMessageType() {
    return this.message.getMessageType();
  }

  @Override
  public Integer getMessageTypeId() {
    if (!(this.isValidType())) {

      //return getMessageType().getCode();
      return 0;
    }
    return getMessageType().getCode();
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
  public Boolean hasMultipleParts() {
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
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Float getLong() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Boolean getAccuracy() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getNavStatusId() {
    //return 15; // Default for unavailable navigation status. Override as needed.
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getManeuverIndicatorId() {
    return 0;
  } // Default for unavailable maneuver indicator. Override as needed.


  @Override
  public Float getSpeedOverGround() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Float getCourseOverGround() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Float getHeading() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Float getRateOfTurn() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getIMO() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public String getCallsign() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public String getShipName() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getVesselTypeId() {
    return 0;
  } // For unavailable vessel types. Override as needed.

  @Override
  public Integer getToBow() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getToStern() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getToStarboard() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Integer getToPort() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public Float getDraught() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
//  public Optional<ZonedDateTime> getETA() {
//    throw new UnsupportedMessageType(getMessageTypeId());
  public String getETA() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public String getDestination() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  protected String addZeroToSingleDigitInt(int value) {
    String formattedValue = "" + value;
    if (formattedValue.length() == 1) {
      formattedValue = "0" + value;
    }
    return formattedValue;
  }

}
