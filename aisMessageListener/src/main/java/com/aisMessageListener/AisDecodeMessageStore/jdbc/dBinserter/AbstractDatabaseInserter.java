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
      writeGeospatialData();
      writeVoyageData();
      writeNavigationStatus();
      writeManeuverIndicator();
      writeNavigationData();
      writeVesselData();
      writeVesselType();
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
    return WriteResult.UNSUPPORTED;
  }

  @Override
  public WriteResult writeVesselSignature() throws SQLException {
    try {
      message.getMMSI();
      message.getIMO();
      message.getCallsign();
      message.getShipName();

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
      message.getToPort();
      message.getToStarboard();
      message.getToStern();

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }



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

  @Override
  public WriteResult writeNavigationData() throws SQLException {
    try {
      message.getCourseOverGround();
      message.getDestination();
      message.getHeading();
      message.getRateOfTurn();

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
      message.getAccuracy();
      // TODO: coord?

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS
      return WriteResult.UNSUPPORTED;
    } catch (Exception ex) {
      return WriteResult.FAILURE;
    }
  }

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
