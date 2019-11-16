package io.seeport.harborpilot.aisMessageListener.jdbc.messageData;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;


/**
 * Extension of message to support Class A position reports (i.e message type = 5).
 */
public final class ClassAStaticAndVoyageData extends AbstractMessageData {

  private final ShipAndVoyageData shipVoyageData;

  /**
   * Constructor initializes a ClassAStaticAndVoyageData message using an AIS message.
   *
   * @param message an AISMessage object
   * @throws IllegalArgumentException if the AIS message isn't a {@link ShipAndVoyageData}.
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
    try {
      return Optional.of(shipVoyageData.getImo().getIMO());
    } catch (NullPointerException e) {
      return Optional.empty();
    }
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
    try {
      return  Optional.of(shipVoyageData.getShipType().getCode());
    } catch (NullPointerException e) {
      return Optional.empty();
    }
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
    return shipVoyageData.getEtaAfterReceived().map(ZonedDateTime::toOffsetDateTime);
  }

  @Override
  public String getDestination() {
    return shipVoyageData.getDestination();
  }

}
