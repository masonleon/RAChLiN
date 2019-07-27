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
    public WriteResult writeMessageData() {
       return WriteResult.UNSUPPORTED;
    }

    @Override
    public WriteResult writeVesselSignature() throws SQLException {
        connection.beginTransaction();
        try {
            message.getMMSI();
            message.getIMO();
            message.getCallsign();
            message.getShipName();

            // CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeVoyageData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getDraught();
            message.getETA();
            message.getDestination();

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeVesselData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getToBow();
            message.getToPort();
            message.getToStarboard();
            message.getToStern();

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeVesselType() throws SQLException {
        connection.beginTransaction();
        try {

            message.getShipTypeId();

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeNavigationStatus() throws SQLException {
        connection.beginTransaction();
        try {

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeNavigationData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getCourseOverGround();
            message.getDestination();
            message.getHeading();
            message.getRateOfTurn();

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeGeospatialData() throws SQLException {
        connection.beginTransaction();
        try {
            message.getAccuracy();
            // TODO: coord?

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }

    @Override
    public WriteResult writeManeuverIndicator() throws SQLException {
        connection.beginTransaction();
        try {
            message.getManeuverIndicatorId();

            connection.commitTransaction();
            return WriteResult.SUCCESS;
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            connection.commitTransaction();
            return WriteResult.UNSUPPORTED;
        } catch (Exception ex) {
            connection.rollBackTransaction();
        }
        return WriteResult.FAILURE;
    }
}
