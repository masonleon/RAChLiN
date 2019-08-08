package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import java.util.Optional;

import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

/**
 * Base Interface for supporting incoming Message data. Messages come in from a VHF Receiver,
 * whereby they are processed and written to the underlying PostgreSQL database.
 */
public interface MessageDataInterface {

  /**
   * Checks if the the integer message type is valid.
   *
   * @return boolean if message type >= 0, false otherwise
   */
  boolean isValidType();

  /**
   * TODO edit java doc Grabs the MMSI of the sender.
   *
   * s regulated  I believe each * country has a different organization responsible for allocating
   * their block of MMSI's. In the US, * federal users get them through the National
   * Telecommunications Administration and everyone else * through the . The problem with inaccurate
   * MMSI mostly isn't always intentional, * however there are issues when a owner sells the boat,
   * gets a new transponder and doesn't update the MMSI being broadcast. * There can be other issues
   * with invalid or corrupted MMSI's due to interference or even if the ship's antenna is struck by
   * lightning, etc.
   *
   * @return int
   */
  int getMMSI();

  /**
   * Grabs the message type of this message.
   *
   * @return an AISMessageType object
   */
  AISMessageType getMessageType();

  /**
   * Grabs the integer type Id of the message.
   *
   * @return int
   */
  int getMessageTypeId();

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
   * @return String
   */
  String getTimeReceived();

  /**
   * Grabs the Latitude from a message containing geometric information that has already been
   * calculated to 1/10 000 min (+/-90 deg, North = positive (as per 2's complement), South =
   * negative (as per 2's complement). 91deg (3412140h) = not available = default)
   *
   * @return Float
   */
  Float getLat();

  /**
   * Grabs the Longitude from a message containing geometric information that has already been
   * calculated to 1/10 000 min (+/-180 deg, East = positive (as per 2's complement), West =
   * negative (as per 2's complement). 181= (6791AC0h) = not available = default)
   *
   * @return Float
   */
  Float getLong();

  /**
   * Grabs the position accuracy flag from a message containing geometric information which
   * indicates the accuracy of the vessel's Electronic Position-Fixing Device (EPFD). A value of 1
   * or True indicates a DGPS-quality fix with an accuracy of < 10ms. A value of 0 or False, the
   * default, indicates an unaugmented GNSS fix with accuracy > 10m.
   *
   * @return Boolean
   */
  Boolean getAccuracy();

  /**
   * Grabs the navigation status of a vessel from a message.
   *
   * @return NavigationStatus
   */
  int getNavStatusId();

  /**
   * Grabs the special maneuver indicator of a vessel from a message. This may indicate whether a
   * vessel is engaged in a special maneuver, i.e.: regional passing arrangement on Inland
   * Waterway.
   *
   * @return ManeuverIndicator
   */
  int getManeuverIndicatorId();

  /**
   * Grabs the speed over ground (SOG) of a vessel from a message already calculated in 1/10 knot
   * steps (0-102.2 knots). value 1 023 = not available, value 1 022 = 102.2 knots or higher.
   *
   * @return Float
   */
  Float getSpeedOverGround();

  /**
   * Grabs the course over ground (COG) relative to true north of a vessel from a message already
   * calculated in 1/10 degree precision = (0-3599). 3600 (E10h) = not available = default. 3 601-4
   * 095 should not be used.
   *
   * @return Float
   */
  Float getCourseOverGround();

  /**
   * Grabs the true heading of a vessel from a message. Degrees 0-359. Value of 511 indicates not
   * available = default.
   *
   * @return Float
   */
  Float getHeading();

  /**
   * Grabs the rate of turn (ROT) of a vessel from a message. 0 to +126 = turning right at up to 708
   * deg per min or higher 0 to -126 = turning left at up to 708 deg per min or higher Values
   * between 0 and 708 deg per min coded by ROT_ais = 4.733 * SQRT(ROT_sensor) degrees per min where
   * ROT_sensor is the Rate of Turn as input by an external Rate of Turn Indicator (TI). ROT_AIS is
   * rounded to the nearest integer value. +127 = turning right at more than 5 deg per 30 s (No TI
   * available) -127 = turning left at more than 5 deg per 30 s (No TI available) -128 (80 hex)
   * indicates no turn information available (default).
   *
   * @return Float
   */
  Float getRateOfTurn();

  /**
   * Grabs the IMO (International Maritime Organization) number of a vessel from a message. This
   * number is assigned on behalf of the UN's IMO and is a unique permanent identifier that remains
   * the same even when vessel is sold. Not all vessels may be required to have this identifier.
   *
   * @return int
   */
  Optional<Integer> getIMO();

  /**
   * Grabs the radio call sign of a vessel from a message. Call sign is governed by the
   * International Telecommunications Union (ITU) and assigned by an organization responsible for a
   * particular country. In the US, for non-governmental users, this is the Federal Communications
   * Commission (FCC) Not all vessels may be required to have this identifier.
   *
   * @return IMO
   */
  String getCallsign();

  /**
   * Grabs the name of a vessel from a message. Maximum 20 characters 6 bit ASCII
   * "@@@@@@@@@@@@@@@@@@@@" = not available = default. The Name should be as shown on the station
   * radio license. For SAR aircraft, it should be set to “SAR AIRCRAFT NNNNNNN” where NNNNNNN
   * equals the aircraft registration number.
   *
   * @return String
   */
  String getShipName();

  /**
   * Grabs the ship type and cargo type object of a vessel from a message. This can change depending
   * on a vessel's current voyage and the particular goods being transported.
   *
   * @return ShipType
   */
  Optional<Integer> getVesselTypeId();

  /**
   * Grabs the length in Meters from the Electronic Position-Fixing Device (EPFD) antenna to the bow
   * of a vessel from a message. Will be 0 if not available.
   *
   * @return Integer
   */
  Integer getToBow();

  /**
   * Grabs the length in Meters from the Electronic Position-Fixing Device (EPFD) antenna to the
   * stern of a vessel from a message. Will be 0 if not available.
   *
   * @return Integer
   */
  Integer getToStern();

  /**
   * Grabs the length in Meters from the Electronic Position-Fixing Device (EPFD) antenna to the
   * starboard side of a vessel from a message. Will be 0 if not available.
   *
   * @return Integer
   */
  Integer getToStarboard();

  /**
   * Grabs the length in Meters from the Electronic Position-Fixing Device (EPFD) antenna to the
   * port side of a vessel from a message. Will be 0 if not available.
   *
   * @return Integer
   */
  Integer getToPort();

  /**
   * Grabs the maximum present static draught for a vessel from a message. In 1/10 meters, 255 =
   * draught 25.5 m or greater, 0 = not available = default; in accordance with IMO Resolution
   * A.851. Not applicable to SAR aircraft, should be set to 0.
   *
   * @return Float
   */
  Float getDraught();


  /**
   * Grabs the voyage estimated time of arrival (ETA) for a vessel from a message. In UTC time.
   *
   * @return String
   */
  Optional<String> getETA();


  /**
   * Grabs the voyage destination for a vessel from a message. Maximum 20 characters using 6-bit
   * ASCII; @@@@@@@@@@@@@@@@@@@@ = not available; For SAR aircraft, the use of this field may be
   * decided by the responsible administration.
   *
   * @return String
   */
  String getDestination();

}

