package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.time.Instant;

import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

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

  int getGeospatialDataId();

  int getNavigationDataId();

  int getVoyageDataId();

  int getVesselSignatureId();

  int getVesselDataId();
}

