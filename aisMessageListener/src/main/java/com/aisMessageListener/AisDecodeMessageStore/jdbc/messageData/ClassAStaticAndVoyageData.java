package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;

/**
 * TODO java doc
 */
public class ClassAStaticAndVoyageData extends AbstractMessageData {

  private final ShipAndVoyageData shipVoyageData;

  /**
   * TODO java doc
   */
  public ClassAStaticAndVoyageData(AISMessage message) {
    super(message);

    if (!(message instanceof ShipAndVoyageData)) {
      throw new IllegalArgumentException("Message is not a ship and voyage data!");
    }

    this.shipVoyageData = (ShipAndVoyageData) message;
  }

  @Override
  public Optional<Integer> getIMO() {
    Optional<Integer> imo = Optional.empty();
    try {
      imo = Optional.of(shipVoyageData.getImo().getIMO());
    } catch (NullPointerException e) {
    }
    return imo;
  }

  @Override
  public String getCallsign() {
    return shipVoyageData.getCallsign();
  }

  @Override
  public String getShipName() {
    return shipVoyageData.getShipName();
  }

  @Override
  public Optional<Integer> getVesselTypeId() {
    Optional<Integer> ship_type = Optional.empty();
    try {
      ship_type = Optional.of(shipVoyageData.getShipType().getCode());
    } catch (NullPointerException e) {
    }
    return ship_type;
  }

  @Override
  public Integer getToBow() {
    return shipVoyageData.getToBow();
  }

  @Override
  public Integer getToStern() {
    return shipVoyageData.getToStern();
  }

  @Override
  public Integer getToStarboard() {
    return shipVoyageData.getToStarboard();
  }

  @Override
  public Integer getToPort() {
    return shipVoyageData.getToPort();
  }

  @Override
  public Float getDraught() {
    return shipVoyageData.getDraught();
  }

  @Override
  public Optional<OffsetDateTime> getETA() {
    Optional<ZonedDateTime> optionalTime = shipVoyageData.getEtaAfterReceived();
    if (!optionalTime.isPresent()) {
      return Optional.empty();
    }
    OffsetDateTime offsetTime = optionalTime.get().toOffsetDateTime();
    return Optional.of(offsetTime);
  }

  @Override
  public String getDestination() {
    return shipVoyageData.getDestination();
  }

}
