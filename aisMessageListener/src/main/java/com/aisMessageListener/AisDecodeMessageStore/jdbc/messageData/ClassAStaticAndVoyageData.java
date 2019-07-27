package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;
import dk.tbsalling.aismessages.ais.messages.types.PositionFixingDevice;
import dk.tbsalling.aismessages.ais.messages.types.ShipType;

import java.time.ZonedDateTime;
import java.util.Optional;

public class ClassAStaticAndVoyageData extends AbstractMessageData {

  private final ShipAndVoyageData shipVoyageData;

  public ClassAStaticAndVoyageData(AISMessage message) {
    super(message);

    if (!(message instanceof ShipAndVoyageData)) {
      throw new IllegalArgumentException("Message is not a ship and voyage data!");
    }

    this.shipVoyageData = (ShipAndVoyageData) message;
  }

  @Override
  public int getIMO() {
    return shipVoyageData.getImo().getIMO();
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
  public int getShipTypeId() {
    return shipVoyageData.getShipType().getCode();
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
  public Optional<ZonedDateTime> getETA() {
    return shipVoyageData.getEtaAfterReceived();
  }

  @Override
  public String getDestination() {
    return shipVoyageData.getDestination();
  }

}
