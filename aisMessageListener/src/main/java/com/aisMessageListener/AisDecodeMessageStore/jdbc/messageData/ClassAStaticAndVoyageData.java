package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.ZonedDateTime;
import java.util.Optional;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;

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
  public Integer getIMO() {
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
  public Integer getVesselTypeId() {
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
  public String getETA() {
    Optional<ZonedDateTime> optionalTime = shipVoyageData.getEtaAfterReceived();
    if (!optionalTime.isPresent()) {
      return "NULL";
    }
    ZonedDateTime time = optionalTime.get();
    int year = time.getYear();
    String month = addZeroToSingleDigitInt(time.getMonthValue());
    String day = addZeroToSingleDigitInt(time.getDayOfMonth());
    String hour = addZeroToSingleDigitInt(time.getHour());
    String minute = addZeroToSingleDigitInt(time.getMinute());
    return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + " UTC";
  }

  @Override
  public String getDestination() {
    return shipVoyageData.getDestination();
  }

}
