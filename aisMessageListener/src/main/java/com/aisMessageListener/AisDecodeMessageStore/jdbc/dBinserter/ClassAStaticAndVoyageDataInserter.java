package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

import static java.sql.Types.NULL;

class ClassAStaticAndVoyageDataInserter extends AbstractDatabaseInserter {

    ClassAStaticAndVoyageDataInserter(MessageDataInterface message) {
        super(message);
    }

    @Override
    public WriteResult preparedStatementWriteVesselData() throws SQLException {

        final Integer toBow = message.getToBow();
        final Integer toStern = message.getToStern();
        final Integer toPort = message.getToPort();
        final Integer toStarboard = message.getToStarboard();

        // TODO CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

        String SQL_INSERT =
                "INSERT INTO vessel_data(to_bow, to_stern, to_port, to_starboard)" +
                        "VALUES (?, ?, ?, ?) ";

        try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, toBow);
            ps.setInt(2, toStern);
            ps.setInt(3, toPort);
            ps.setInt(4, toStarboard);

            int primaryKey = ps.executeUpdate();

            if (primaryKey == -1) {
                throw new SQLException("Error recording row entry for vessel_data record.\n");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.vesselDataPrimaryKey = generatedKeys.getLong(1);
                    return WriteResult.SUCCESS;

                } else {
                    throw new SQLException("Error recording primary key for vessel_data record.\n");
                }
            }
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            this.vesselDataPrimaryKey  = NULL;
            //ex.printStackTrace();
            return WriteResult.UNSUPPORTED;

        } catch (Exception ex) {
            ex.printStackTrace();
            return WriteResult.FAILURE;
        }
    }

    @Override
    public WriteResult preparedStatementWriteNavigationData() throws SQLException {
        return null;
    }

    @Override
    public WriteResult preparedStatementWriteGeospatialData() throws SQLException {
        return null;
    }

    @Override
    public WriteResult preparedStatementWriteVoyageData() throws SQLException {

        Float draught = message.getDraught();
        String eta = message.getETA();
        String destination = message.getDestination();

        String SQL_INSERT =
                "INSERT INTO voyage_data(draught, eta, destination) " +
                        "VALUES (?, ?, ?)";

        try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setFloat(1, draught);

            if (eta.equals("NULL")) {
                ps.setTimestamp(2, Timestamp.valueOf(eta));
            }else {
                ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);
            }
            ps.setString(3, destination);


            int primaryKey = ps.executeUpdate();

            if (primaryKey == -1) {
                throw new SQLException("Error recording row entry for voyage_data record.\n");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.voyageDataPrimaryKey = generatedKeys.getLong(1);
                    return WriteResult.SUCCESS;

                } else {
                    throw new SQLException("Error recording primary key for voyage_data record.\n");
                }
            }
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            this.voyageDataPrimaryKey  = NULL;
            //ex.printStackTrace();
            return WriteResult.UNSUPPORTED;

        } catch (Exception ex) {
            ex.printStackTrace();
            return WriteResult.FAILURE;
        }
    }
}

