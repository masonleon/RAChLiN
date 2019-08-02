package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;


import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.util.CoordinateUtil;

import org.postgresql.geometric.PGpoint;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

import static java.sql.Types.NULL;

import java.sql.SQLException;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;


public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  protected MessageDataInterface message;
  protected DatabaseConnectionInterface connection;


//  protected long messageDataPrimaryKey;
//  protected long vesselSignaturePrimaryKey;
//  protected long navigationDataPrimaryKey;
//  protected long voyageDataPrimaryKey;
//  protected long vesselDataPrimaryKey;
//  protected long geospatialDataPrimaryKey;

  private int messageDataPrimaryKey;
  private int vesselSignaturePrimaryKey;
  private int navigationDataPrimaryKey;
  private int voyageDataPrimaryKey;
  private int vesselDataPrimaryKey;
  private int geospatialDataPrimaryKey;





  protected AbstractDatabaseInserter(MessageDataInterface message, DatabaseConnectionInterface connection) {
    this(message);
    attachConnection(connection);
  }

  protected AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;

    // -1 Indicates that a pk has not been created for this message.
    vesselSignaturePrimaryKey = -1;
    navigationDataPrimaryKey = -1;
    voyageDataPrimaryKey = -1;
    vesselDataPrimaryKey = -1;
    geospatialDataPrimaryKey = -1;
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

      writeVesselData();
      writeVesselSignature();
      writeVoyageData();
      writeNavigationData();
      writeGeospatialData();
      writeMessageData();

      connection.commitTransaction();
      return WriteResult.SUCCESS;
    } catch (SQLException ex) {
      ex.printStackTrace();  // For testing.
      connection.rollBackTransaction();
    }
    return WriteResult.FAILURE;
  }

  @Override
  public WriteResult writeMessageData() throws SQLException {
    try {
      String timeReceived = message.getTimeReceived();
      Boolean isValidMessage = message.isValidType();
      Boolean isMultiPart = message.hasMultipleParts();
      String rawNMEA = message.getRawNMEA();
      int messageTypeId = message.getMessageTypeId();
      String geoDataKey = geospatialDataPrimaryKey == -1 ? "NULL" : Integer.toString(geospatialDataPrimaryKey);
      String navDataKey = navigationDataPrimaryKey == -1 ? "NULL" : Integer.toString(navigationDataPrimaryKey);
      String voyageDataKey = voyageDataPrimaryKey == -1 ? "NULL" : Integer.toString(voyageDataPrimaryKey);
      String vesselDataKey = vesselDataPrimaryKey == -1 ? "NULL" : Integer.toString(vesselDataPrimaryKey);

//      String timeReceived = message.getTimeReceived();
//      Boolean isValidMessage = message.isValidType();
//      Boolean isMultiPart = message.hasMultipleParts();
//      String rawNMEA = message.getRawNMEA();
//      Integer messageTypeId = message.getMessageTypeId();



      if (vesselSignaturePrimaryKey == -1) {
        System.err.println("Cannot update message_data table without first updating vessel_signature.\n");
      }

      String sqlUpdate =
              "INSERT INTO message_data(" +
                      "time_received," +
                      "is_valid_msg," +
                      "is_multi_part," +
                      "raw_nmea," +
                      "message_type_id," +
                      "geospatial_data_id," +
                      "navigation_data_id," +
                      "voyage_data_id," +
                      "vessel_signature_id," +
                      "vessel_data_id" +
                      ") " +
                      "VALUES ('" +
                      timeReceived + "'," +
                      isValidMessage + "," +
                      isMultiPart + ",'" +
                      rawNMEA + "'," +
                      messageTypeId + "," +
                      geoDataKey + "," +
                      navDataKey + "," +
                      voyageDataKey + "," +
                      vesselSignaturePrimaryKey + "," +
                      vesselDataKey +
                      ")";

      int primaryKey = connection.insertOneRecord(sqlUpdate);


      String SQL_INSERT =
              "INSERT INTO message_data(time_received, is_valid_msg, is_multi_part, " +
                      "raw_nmea, message_type_id, geospatial_data_id, navigation_data_id, " +
                      "voyage_data_id, vessel_signature_id, vessel_data_id)" +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      ps.setTimestamp(1, Timestamp.valueOf(timeReceived));
      ps.setBoolean(2, isValidMessage);
      ps.setBoolean(3, isMultiPart);
      ps.setString(4, rawNMEA);
      ps.setObject(5, messageTypeId, Types.INTEGER);
      ps.setObject(6, geospatialDataPrimaryKey, Types.INTEGER);
      ps.setObject(7, navigationDataPrimaryKey, Types.INTEGER);
      ps.setObject(8, voyageDataPrimaryKey, Types.INTEGER);
      ps.setObject(9, vesselSignaturePrimaryKey, Types.INTEGER);
      ps.setObject(10, vesselDataPrimaryKey, Types.INTEGER);

      int primaryKey = ps.executeUpdate();

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          this.messageDataPrimaryKey = generatedKeys.getInt(1);
        }
      }



      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // None of these methods should throw an UnsupportedMessageType exception.
      System.err.println("Could not access data needed for writing to message_data table.\n");
      System.exit(1);


      return WriteResult.UNSUPPORTED;
    }
  }











  @Override
  public WriteResult writeVesselSignature() throws SQLException {
    try {
      int mmsi = message.getMMSI();
      int imo = message.getIMO();
      String callSign = message.getCallsign();
      String name = message.getShipName();
      int vesselTypeId = message.getVesselTypeId();

      // Check if vessel signature already exists in table.
      int vesselSignatureID = connection.checkVesselSig(mmsi, imo, callSign, name, vesselTypeId);
      if (vesselSignatureID != -1) {
        this.vesselSignaturePrimaryKey = vesselSignatureID;
        return WriteResult.SUCCESS;
      }

      String sqlUpdate =
              "INSERT INTO vessel_signature(" +
                      "mmsi," +
                      "imo," +
                      "call_sign," +
                      "name," +
                      "vessel_type_id" +
                      ") " +
                      "VALUES (" +
                      mmsi + "," +
                      imo + ",'" +
                      callSign + "','" +
                      name + "'," +
                      vesselTypeId +
                      ")";


      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for vessel_signature record.\n");
      }
      this.vesselSignaturePrimaryKey = primaryKey; // Update for writeMessageData foreign key.
      return WriteResult.SUCCESS;

    } catch (UnsupportedMessageType ex) {

      // Check if vessel signature already exists in table.
      int vesselSignatureID = connection.checkVesselSigWithNulls(message.getMMSI(), message.getVesselTypeId());
      if (vesselSignatureID != -1) {
        this.vesselSignaturePrimaryKey = vesselSignatureID;
        return WriteResult.SUCCESS;
      }

      String sqlUpdate =
              "INSERT INTO vessel_signature(" +
                      "mmsi," +
                      "imo," +
                      "call_sign," +
                      "name," +
                      "vessel_type_id" +
                      ") " +
                      "VALUES (" +
                      message.getMMSI() + "," +
                      "NULL," +
                      "NULL," +
                      "NULL," +
                      message.getVesselTypeId() +
                      ")";

      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for MMSI-only vessel_signature record.\n");
      }
      this.vesselSignaturePrimaryKey = primaryKey;
      return WriteResult.SUCCESS;
    }
  }

  @Override
  public WriteResult writeVoyageData() throws SQLException {
    try {
      Float draught = message.getDraught();
      String eta = message.getETA();
      String destination = message.getDestination();
      String sqlUpdate;
      if (eta == null) {
        sqlUpdate =
                "INSERT INTO voyage_data(" +
                        "draught," +
                        "eta," +
                        "destination" +
                        ") " +
                        "VALUES (" +
                        draught + "," +
                        "NULL,'" +
                        destination +
                        "')";
      } else {
        sqlUpdate =
                "INSERT INTO voyage_data(" +
                        "draught," +
                        "eta," +
                        "destination" +
                        ") " +
                        "VALUES (" +
                        draught + ",'" +
                        eta + "','" +
                        destination +
                        "')";
      }

      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for voyage_data record.\n");
      }
      this.voyageDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // This message does not contain voyage data. FK will be null in Message_Data table.
      return WriteResult.UNSUPPORTED;
    }
  }

  @Override
  public WriteResult writeVesselData() throws SQLException {
    try {
      Integer toBow = message.getToBow();
      Integer toStern = message.getToStern();
      Integer toPort = message.getToPort();
      Integer toStarboard = message.getToStarboard();

      // TODO CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

      String sqlUpdate =
              "INSERT INTO vessel_data(" +
                      "to_bow," +
                      "to_stern," +
                      "to_port," +
                      "to_starboard" +
                      ") " +
                      "VALUES (" +
                      toBow + "," +
                      toStern + "," +
                      toPort + "," +
                      toStarboard +
                      ")";

      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for vessel_data record.\n");
      }
      this.vesselDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // This message does not contain vessel data. FK will be null in Message_Data table.
      return WriteResult.UNSUPPORTED;
    }
  }

  @Override
  public WriteResult writeNavigationData() throws SQLException {
    try {

      Float sog = message.getSpeedOverGround();
      Float cog = message.getCourseOverGround();
      Float heading = message.getHeading();
      Float rot = message.getRateOfTurn();
      int navStatusId = message.getNavStatusId();
      int maneuverIndicatorId = message.getManeuverIndicatorId();

      String sqlUpdate =
              "INSERT INTO navigation_data(" +
                      "speed_over_ground," +
                      "course_over_ground," +
                      "heading," +
                      "rate_of_turn," +
                      "nav_status_id," +
                      "maneuver_indicator_id" +
                      ") " +
                      "VALUES (" +
                      sog + "," +
                      cog + "," +
                      heading + "," +
                      rot + "," +
                      navStatusId + "," +
                      maneuverIndicatorId +
                      ")";

      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for navigation_data record.\n");
      }
      this.navigationDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // This message does not contain navigation data. FK will be null in Message_Data table.
      return WriteResult.UNSUPPORTED;
    }
  }

  @Override
  public WriteResult writeGeospatialData() throws SQLException {
    try {

      int accuracy = message.getAccuracy() ? 1 : 0;
      // TODO: use appropriate geography data type from PostGIS
      PGpoint coord = CoordinateUtil.getCoord(message.getLat(), message.getLong());

      String sqlUpdate =
              "INSERT INTO geospatial_data(" +
                      "coord," +
                      "accuracy" +
                      ") " +
                      "VALUES ('" +
                      coord + "'," +
                      accuracy +
                      ")";

      int primaryKey = connection.insertOneRecord(sqlUpdate);
      if (primaryKey == -1) {
        throw new SQLException("Error recording primary key for geospatial_data record.\n");
      }
      this.geospatialDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.

      return WriteResult.SUCCESS;
    } catch (UnsupportedMessageType ex) {
      // This message does not contain geospatial data. FK will be null in Message_Data table.
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
//        } catch (UnsupportedMessageType ex) {
//            // WRITE BLANK RECORD WITH NULL COLUMNS
//            this.geospatialDataPrimaryKey = NULL;
//            //ex.printStackTrace();
//            return WriteResult.UNSUPPORTED;


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
//        } catch (UnsupportedMessageType ex) {
//            // WRITE BLANK RECORD WITH NULL COLUMNS
//            this.navigationDataPrimaryKey  = NULL;
//            //ex.printStackTrace();
//            return WriteResult.UNSUPPORTED;

      } catch (Exception ex) {
        ex.printStackTrace();
        return WriteResult.FAILURE;
      }
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
//        } catch (UnsupportedMessageType ex) {
//            // WRITE BLANK RECORD WITH NULL COLUMNS
//            this.vesselDataPrimaryKey  = NULL;
//            //ex.printStackTrace();
//            return WriteResult.UNSUPPORTED;

      } catch (Exception ex) {
        ex.printStackTrace();
        return WriteResult.FAILURE;
      }
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
        ps.setObject(2, eta, Types.TIME_WITH_TIMEZONE);
//            if (eta.equals("NULL")) {
//                ps.setTimestamp(2, Timestamp.valueOf(eta));
//            }else {
//                ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);
//            }
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
//        } catch (UnsupportedMessageType ex) {
//            // WRITE BLANK RECORD WITH NULL COLUMNS
//            this.voyageDataPrimaryKey  = NULL;
//            //ex.printStackTrace();
//            return WriteResult.UNSUPPORTED;

      } catch (Exception ex) {
        ex.printStackTrace();
        return WriteResult.FAILURE;
      }
    }
  }



