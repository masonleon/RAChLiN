package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.ShipAndVoyageData;
import dk.tbsalling.aismessages.ais.messages.types.*;

/**
 * Base Interface for supporting incoming Message data. Messages come in from a VHF Receiver,
 * whereby they are processed and written to the underlying PostgreSQL database.
 */
public interface MessageDataInterface {

  /**
   * Processes a message. If it's supported, we write it to tables beyond the overarching
   * MessageData table, e.g. the GeoPositional Data table. Unsupported messages are just dumped to
   * the table, so that they can be retroactively conformed as standards change.
   */
  void processMessage();

  /**
   * Checks if the the integer message type is valid.
   *
   * @return boolean if message type >= 0, false otherwise
   */
  boolean isValidType();

  /**
   * Grabs the MMSI of the sender.
   *
   * @return int
   */
  int getMMSI();

  /**
   * Grabs the message type of this message.
   *
   * @return an AISMessageType object
   */
  AISMessageType getType();

  /**
   * Grabs the integer type Id of the message.
   *
   * @return int
   */
  int getTypeId();

  /**
   * Grabs the raw NMEA string message from the incoming receiver, to be dumped to the table.
   *
   * @return String
   */
  String getRawNMEA();

  /**
   * Checks if the message has multiple parts, specified by the repeat indicator 2 bit integer.
   *
   * @return boolean
   */
  boolean hasMultipleParts();

  /**
   * Checks the instant this message was received.
   *
   * @return Instant
   */
  Instant getTimeReceived();

  Float getLat();

  Float getLong();

  Boolean getAccuracy();

  NavigationStatus getNavStatus();

  ManeuverIndicator getManeuverIndicator();

  Float getSpeedOverGround();

  Float getCourseOverGround();

  Float getHeading();

  Float getRateOfTurn();

  IMO getIMO();

  String getCallsign();

  String getShipName();

  ShipType getShipType();

  Integer getToBow();

  Integer getToStern();

  Integer getToStarboard();

  Integer getToPort();

  PositionFixingDevice getPositionFixingDevice();

  Float getDraught();

  Optional<ZonedDateTime> getETA();

  String getDestination();

  Boolean isDataTerminalReady();
}

