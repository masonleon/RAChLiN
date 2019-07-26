package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;
import dk.tbsalling.aismessages.ais.messages.types.IMO;
import dk.tbsalling.aismessages.ais.messages.types.PositionFixingDevice;
import dk.tbsalling.aismessages.ais.messages.types.ShipType;

public class ClassAStaticAndVoyageData extends AbstractMessageData {

  private final ShipAndVoyageData shipVoyageData;

  public ClassAStaticAndVoyageData(AISMessage message) {
    super(message);

    if (!(message instanceof ShipAndVoyageData)) {
      throw new IllegalArgumentException("Message is not a ship and voyage data!");
    }

    this.shipVoyageData = (ShipAndVoyageData) message;
  }

  public IMO getIMO() {
    return shipVoyageData.getImo();
  }

  public String getCallsign() {
    return shipVoyageData.getCallsign();
  }

  public String getShipName() {
    return shipVoyageData.getShipName();
  }

  public ShipType getShipType() {
    return shipVoyageData.getShipType();
  }

  public Integer getToBow() {
    return shipVoyageData.getToBow();
  }

  public Integer getToStern() {
    return shipVoyageData.getToStern();
  }

  public Integer getToStarboard() {
    return shipVoyageData.getToStarboard();
  }

  public Integer getToPort() {
    return shipVoyageData.getToPort();
  }

  public PositionFixingDevice getPositionFixingDevice() {
    return shipVoyageData.getPositionFixingDevice();
  }

  public Float getDraught() {
    return shipVoyageData.getDraught();
  }

  public String getDestination() {
    return shipVoyageData.getDestination();
  }

  public Boolean isDataTerminalReady() {
    return shipVoyageData.getDataTerminalReady();
  }

  private final ShipAndVoyageData shipVoyageData;

  public ClassAStaticAndVoyageData(AISMessage message) {
    super(message);

    if (!(message instanceof ShipAndVoyageData)) {
      throw new IllegalArgumentException("Message is not a ship and voyage data!");
    }

    this.shipVoyageData = (ShipAndVoyageData) message;
  }

  @Override
  public IMO getIMO() {
    return shipVoyageData.getImo();
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
  public ShipType getShipType() {
    return shipVoyageData.getShipType();
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
  public PositionFixingDevice getPositionFixingDevice() {
    return shipVoyageData.getPositionFixingDevice();
  }

  @Override
  public Float getDraught() {
    return shipVoyageData.getDraught();
  }

  @Override
  public String getDestination() {
    return shipVoyageData.getDestination();
  }

  @Override
  public Boolean isDataTerminalReady() {
    return shipVoyageData.getDataTerminalReady();
  }
}
