package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;
import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

import java.sql.SQLException;

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
    public void writeMessageData() {
    }

    @Override
    public void writeVesselSignature() throws SQLException {
        connection.beginTransaction();
        try {
            message.getMMSI();
            message.getIMO();
            message.getCallsign();
            message.getShipName();

            // CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }

    @Override
    public void writeVoyageData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getDraught();
            message.getETA();
            message.getDestination();

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }

    @Override
    public void writeVesselData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getToBow();
            message.getToPort();
            message.getToStarboard();
            message.getToStern();

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }

    @Override
    public void writeNavigationData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getCourseOverGround();
            message.getDestination();
            message.getHeading();
            message.getRateOfTurn();

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }

    @Override
    public void writeGeospatialData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getAccuracy();
            // TODO: coord?

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }

    @Override
    public void writeManeuverIndicatorData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getManeuverIndicator();

            connection.commitTransaction();
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
    }
}
