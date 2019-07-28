package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

import java.sql.SQLException;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  protected MessageDataInterface message;
  protected DatabaseConnectionInterface connection;

  protected AbstractDatabaseInserter(MessageDataInterface message, DatabaseConnectionInterface connection) {
    this(message);

    attachConnection(connection);
  }

  protected AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;
  }

  @Override
  public void attachConnection(DatabaseConnectionInterface conn) {
    this.connection = conn;
  }

  @Override
  public WriteResult writeMessage() throws SQLException {
    connection.connectIfDropped();
    connection.beginTransaction();

    try {

      writeMessageData();
      writeGeospatialData();
      writeNavigationData();
      writeVoyageData();
      writeVesselData();
      writeVesselSignature();
      writeMessageData();
      connection.commitTransaction();
      return WriteResult.SUCCESS;
    } catch (SQLException ex) {
      connection.rollBackTransaction();
    }
    return WriteResult.FAILURE;
  }

  @Override
  public WriteResult writeMessageData() {
    message.getTimeReceived();
    message.isValidType();
    message.hasMultipleParts();
    message.getRawNMEA();
    message.getMessageTypeId();

    return WriteResult.UNSUPPORTED;
  }

  @Override
  public WriteResult writeVesselSignature() throws SQLException {
    try {
      message.getMMSI();
      message.getIMO();
      message.getCallsign();
      message.getShipName();
      message.getShipTypeId();

      // CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

  @Override
  public WriteResult writeVoyageData() throws SQLException {
    try {
      message.getDraught();
      message.getETA();
      message.getDestination();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

  @Override
  public WriteResult writeVesselData() throws SQLException {
    try {
      message.getToBow();
      message.getToStern();
      message.getToPort();
      message.getToStarboard();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }
  @Override
  public WriteResult writeNavigationData() throws SQLException {
    try {
      message.getSpeedOverGround();
      message.getCourseOverGround();
      message.getDestination();
      message.getHeading();
      message.getRateOfTurn();
      message.getNavStatusId();
      message.getManeuverIndicatorId();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

  @Override
  public WriteResult writeGeospatialData() throws SQLException {
    try {
      message.getCoord();
      message.getAccuracy();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }


  // NOTE: vessel_type table is a static reference table
  @Override
  public WriteResult writeVesselType() throws SQLException {
    try {
      message.getShipTypeId();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

  // NOTE: nav_status table is a static reference table
  @Override
  public WriteResult writeNavigationStatus() throws SQLException {
    try {

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

  // NOTE: maneuver_indicator table is a static reference table
  @Override
  public WriteResult writeManeuverIndicator() throws SQLException {
    try {
      message.getManeuverIndicatorId();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;

    }
  }
}
