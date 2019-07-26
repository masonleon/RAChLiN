package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.Instant;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.nmea.exceptions.InvalidMessage;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

import static java.sql.Types.NULL;

public class AbstractMessageData implements MessageDataInterface {
  private AISMessage message;
  private int geospatialDataId;
  private int navigationDataId;
  private int voyageDataId;
  private int vesselSignatureId;
  private int vesselDataId;

  public AbstractMessageData(AISMessage message) {
    this.message = message;

    this.geospatialDataId = NULL;
    this.navigationDataId = NULL;
    this.voyageDataId = NULL;
    this.vesselSignatureId = NULL;
    this.vesselDataId = NULL;
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
  public Instant getTimeReceived() {
    return this.message.getMetadata().getReceived();
  }

  @Override
  public int getGeospatialDataId() {
    return geospatialDataId;
  }

  @Override
  public int getNavigationDataId() {
    return navigationDataId;
  }

  @Override
  public int getVoyageDataId() {
    return voyageDataId;
  }

  @Override
  public int getVesselSignatureId() {
    return vesselSignatureId;
  }

  @Override
  public int getVesselDataId() {
    return vesselDataId;
  }
}
