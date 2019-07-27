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
  public boolean isValidType() {
    return this.getMessageTypeId() > -1;
  }

  @Override
  public int getMMSI() {
    return this.message.getSourceMmsi().getMMSI();
  }

  @Override
  public AISMessageType getMessageType() {
    return this.message.getMessageType();
  }

  @Override
  public int getMessageTypeId() {
    if (!(this.isValidType())) {
      //return 0;
      return getMessageType().getCode();
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
  public int getNavStatusId() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public int getManeuverIndicatorId() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

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
  public int getIMO() {
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
  public int getShipTypeId() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

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
