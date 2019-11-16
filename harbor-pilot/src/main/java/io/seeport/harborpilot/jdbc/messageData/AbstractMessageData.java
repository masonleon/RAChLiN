package io.seeport.harborpilot.jdbc.messageData;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

/**
 * A wrapper class around the MessageData interface. Most getters are unsupported in the abstract
 * class, and have the correct implementation in the relevant objects that should be accessing these
 * getters. We are adapting different message types to a consolidated abstract object.
 */
public abstract class AbstractMessageData implements MessageDataInterface {
  private final AISMessage message;

  /**
   * Default constructor instantiates a Message Data wrapper via an input AISMessage object.
   */
  public AbstractMessageData(AISMessage message) {
    this.message = message;
  }

  @Override
  public boolean isValidType() {
    return this.message.getMessageType().getCode() > -1;
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
  public boolean hasMultipleParts() {
    return this.message.getNmeaMessages().length > 1;
  }

  @Override
  public OffsetDateTime getTimeReceived() {
    Instant time = this.message.getMetadata().getReceived();
    return time.atOffset(ZoneOffset.UTC);
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
  public Optional<Integer> getIMO() {
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
  public Optional<Integer> getVesselTypeId() { throw new UnsupportedMessageType(getMessageTypeId()); }

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
  public Optional<OffsetDateTime> getETA() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }

  @Override
  public String getDestination() {
    throw new UnsupportedMessageType(getMessageTypeId());
  }
}
