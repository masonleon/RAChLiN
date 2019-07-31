package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.util.CoordinateUtil;

import org.postgresql.geometric.PGpoint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

import static java.sql.Types.NULL;

class ClassAPositionReportInserter extends AbstractDatabaseInserter {

    ClassAPositionReportInserter(MessageDataInterface message) {
        super(message);
    }

    @Override
    public WriteResult preparedStatementWriteVoyageData() throws SQLException {
        this.voyageDataPrimaryKey = NULL;
        return WriteResult.UNSUPPORTED;
    }

    @Override
    public WriteResult preparedStatementWriteVesselData() throws SQLException {
        this.vesselDataPrimaryKey = NULL;
        return WriteResult.UNSUPPORTED;
    }

    @Override
    public WriteResult preparedStatementWriteGeospatialData() throws SQLException {

        String SQL_INSERT =
                "INSERT INTO geospatial_data(coord, accuracy) " +
                        "VALUES (?, ?)";

        final PGpoint coord = CoordinateUtil.getCoord(message.getLat(), message.getLong());
        final Integer accuracy = message.getAccuracy() ? 1 : 0;

        try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setObject(1, coord);
            ps.setInt(2, accuracy);

            int primaryKey = ps.executeUpdate();

            if (primaryKey == -1) {
                throw new SQLException("Error recording row entry for geospatial_data record.\n");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.geospatialDataPrimaryKey = generatedKeys.getLong(1);
                    return WriteResult.SUCCESS;

                } else {
                    throw new SQLException("Error recording primary key for geospatial_data record.\n");
                }
            }
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            this.geospatialDataPrimaryKey = NULL;
            //ex.printStackTrace();
            return WriteResult.UNSUPPORTED;


        } catch (Exception ex) {
            ex.printStackTrace();
            return WriteResult.FAILURE;
        }
    }

    @Override
    public WriteResult preparedStatementWriteNavigationData() throws SQLException {

        String SQL_INSERT =
                "INSERT INTO navigation_data(speed_over_ground, course_over_ground, heading, " +
                        "rate_of_turn, nav_status_id, maneuver_indicator_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        final Float sog = message.getSpeedOverGround();
        final Float cog = message.getCourseOverGround();
        final Float heading = message.getHeading();
        final Float rot = message.getRateOfTurn();
        final Integer navStatusId = message.getNavStatusId();
        final Integer maneuverIndicatorId = message.getManeuverIndicatorId();

        try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setFloat(1, sog);
            ps.setFloat(2, cog);
            ps.setFloat(3, heading);
            ps.setFloat(4, rot);
            ps.setInt(5, navStatusId);
            ps.setInt(6, maneuverIndicatorId);

            int primaryKey = ps.executeUpdate();

            if (primaryKey == -1) {
                throw new SQLException("Error recording row entry for navigation_data record.\n");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.navigationDataPrimaryKey = generatedKeys.getLong(1);
                    return WriteResult.SUCCESS;

                } else {
                    throw new SQLException("Error recording primary key for navigation_data record.\n");
                }
            }
        } catch (UnsupportedMessageType ex) {
            // WRITE BLANK RECORD WITH NULL COLUMNS
            this.navigationDataPrimaryKey  = NULL;
            //ex.printStackTrace();
            return WriteResult.UNSUPPORTED;

        } catch (Exception ex) {
            ex.printStackTrace();
            return WriteResult.FAILURE;
        }
    }
}


