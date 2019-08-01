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

public abstract class AbstractDatabaseInserter implements DatabaseInserterInterface {

  protected MessageDataInterface message;
  protected DatabaseConnectionInterface connection;

  protected long messageDataPrimaryKey;
  protected long vesselSignaturePrimaryKey;
  protected long navigationDataPrimaryKey;
  protected long voyageDataPrimaryKey;
  protected long vesselDataPrimaryKey;
  protected long geospatialDataPrimaryKey;

//  protected AbstractDatabaseInserter(MessageDataInterface message, DatabaseConnectionInterface connection) {
//    this(message);
//    attachConnection(connection);
//
//    // -1 Indicates that a pk has not been created for this message.
//    messageDataPrimaryKey = -1;
//    vesselSignaturePrimaryKey = -1;
//    navigationDataPrimaryKey = -1;
//    voyageDataPrimaryKey = -1;
//    vesselDataPrimaryKey = -1;
//    geospatialDataPrimaryKey = -1;
//
////    messageDataPrimaryKey = NULL;
////    vesselSignaturePrimaryKey = NULL;
////    navigationDataPrimaryKey = NULL;
////    voyageDataPrimaryKey = NULL;
////    vesselDataPrimaryKey = NULL;
////    geospatialDataPrimaryKey = NULL;
//  }

  protected AbstractDatabaseInserter(MessageDataInterface message) {
    this.message = message;

    this.messageDataPrimaryKey = -1;
    this.vesselSignaturePrimaryKey = -1;
    this.navigationDataPrimaryKey = -1;
    this.voyageDataPrimaryKey = -1;
    this.vesselDataPrimaryKey = -1;
    this.geospatialDataPrimaryKey = -1;
  }

  @Override
  public void attachConnection(DatabaseConnectionInterface conn) {
    this.connection = conn;
  }

//  @Override
//  public WriteResult writeMessage() throws SQLException {
//    connection.connectIfDropped();
//    connection.beginTransaction();
//
//    try{
//      System.out.println(message.getRawNMEA().toString());
//      System.out.println(message.getMessageType().toString());
////      try {
////        writeVesselData();
////      } catch (SQLException e) {
////        e.printStackTrace();
////      }
//     // try {
//        //writeVesselSignature();
////      } catch (SQLException e) {
////        e.printStackTrace();
////      }
////      try {
////        writeVoyageData();
////      } catch (SQLException e) {
////        e.printStackTrace();
////      }
////      try {
////        writeNavigationData();
////      } catch (SQLException e) {
////        e.printStackTrace();
////      }
////      try {
////        writeGeospatialData();
////      } catch (SQLException e) {
////        e.printStackTrace();
////      }
////      try {
// //       writeMessageData();
////      } catch (Exception e) {
////        e.printStackTrace();
////      }
//
////      //try {
////        writeVesselData();
////        writeVesselSignature();
////        writeVoyageData();
////        writeNavigationData();
////        writeGeospatialData();
////        writeMessageData();
////      } catch (Exception e) {
////        e.printStackTrace();
////      }
//
//      preparedStatementWriteVesselData();
//      preparedStatementWriteVesselSignature();
//      preparedStatementWriteVoyageData();
//      preparedStatementWriteNavigationData();
//      preparedStatementWriteGeospatialData();
//      preparedStatementWriteMessageData();
//
//
//      connection.commitTransaction();
//      return WriteResult.SUCCESS;
//    } catch (SQLException ex) {
//      connection.rollBackTransaction();
//
//      //ex.printStackTrace();
//    }
//    return WriteResult.FAILURE;
//  }

  @Override
  public WriteResult preparedStatementWriteMessageData() {

    String timeReceived = message.getTimeReceived();
    Boolean isValidMessage = message.isValidType();
    Boolean isMultiPart = message.hasMultipleParts();
    String rawNMEA = message.getRawNMEA();
    Integer messageTypeId = message.getMessageTypeId();

    String SQL_INSERT =
            "INSERT INTO message_data(time_received, is_valid_msg, is_multi_part, " +
                    "raw_nmea, message_type_id, geospatial_data_id, navigation_data_id, " +
                    "voyage_data_id, vessel_signature_id, vessel_data_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

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

      if (primaryKey == -1) {
        throw new SQLException("Error recording row entry for vessel_signature record.\n");
      }

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          this.messageDataPrimaryKey = generatedKeys.getLong(1);
          return WriteResult.SUCCESS;

        } else {
          throw new SQLException("Error recording primary key for message_data record.\n");
        }
      }
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      this.messageDataPrimaryKey = NULL;
//      //ex.printStackTrace();
//      return WriteResult.UNSUPPORTED;

    } catch (Exception ex) {
      // ex.printStackTrace();
      return WriteResult.FAILURE;
    }
  }


  @Override
  public WriteResult preparedStatementWriteVoyageData() throws SQLException {
    this.voyageDataPrimaryKey = NULL;
    return WriteResult.UNSUPPORTED;
  }

  @Override
  public WriteResult preparedStatementWriteVesselData() throws SQLException{
    this.vesselDataPrimaryKey = NULL;
    return WriteResult.UNSUPPORTED;
  }

  @Override
  public WriteResult preparedStatementWriteNavigationData() throws SQLException{
    this.navigationDataPrimaryKey = NULL;
    return WriteResult.UNSUPPORTED;
  }

  @Override
  public WriteResult preparedStatementWriteGeospatialData() throws SQLException{
    this.geospatialDataPrimaryKey = NULL;
    return WriteResult.UNSUPPORTED;
  }


  @Override
  public WriteResult preparedStatementWriteVesselSignature() throws SQLException {

    Integer mmsi = message.getMMSI();
    Integer imo = message.getIMO();
    String callSign = message.getCallsign();
    String name = message.getShipName();
    Integer vesselTypeId = message.getVesselTypeId();

    // TODO CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY

    String SQL_INSERT =
            "INSERT INTO vessel_signature(mmsi, imo, call_sign, name, vessel_type_id)" +
                    "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, mmsi);
      ps.setInt(2, imo);
      ps.setString(3, callSign);
      ps.setString(4, name);
      ps.setInt(5, vesselTypeId);

      int primaryKey = ps.executeUpdate();

      if (primaryKey == -1) {
        throw new SQLException("Error recording row entry for vessel_signature record.\n");
      }

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          this.vesselSignaturePrimaryKey = generatedKeys.getLong(1);
          return WriteResult.SUCCESS;

        } else {
          throw new SQLException("Error recording primary key for vessel_signature record.\n");
        }
      }
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      this.vesselSignaturePrimaryKey = NULL;
//      //ex.printStackTrace();
//      return WriteResult.UNSUPPORTED;

    } catch (Exception ex) {
      ex.printStackTrace();
      return WriteResult.FAILURE;
    }
  }


//  @Override
//  public WriteResult writeMessageData() {
//    try {
//      String timeReceived = message.getTimeReceived();
//      Boolean isValidMessage = message.isValidType();
//      Boolean isMultiPart = message.hasMultipleParts();
//      String rawNMEA = message.getRawNMEA();
//      Integer messageTypeId = message.getMessageTypeId();
//
//      String sqlUpdate =
//              "INSERT INTO message_data(" +
//                      "time_received," +
//                      "is_valid_msg," +
//                      "is_multi_part," +
//                      "raw_nmea," +
//                      "message_type_id," +
//                      "geospatial_data_id," +
//                      "navigation_data_id," +
//                      "voyage_data_id," +
//                      "vessel_signature_id," +
//                      "vessel_data_id" +
//                      ") " +
//                      "VALUES ('" +
//                      timeReceived + "'," +
//                      isValidMessage + "," +
//                      isMultiPart + ",'" +
//                      rawNMEA + "'," +
//                      messageTypeId + "," +
//                      geospatialDataPrimaryKey + "," +
//                      navigationDataPrimaryKey + "," +
//                      voyageDataPrimaryKey + "," +
//                      vesselSignaturePrimaryKey + "," +
//                      vesselDataPrimaryKey + "," +
//                      ")";
//
//      int primaryKey = connection.insertOneRecord(sqlUpdate);
//      if (primaryKey == -1) {
//        throw new SQLException("Error recording primary key for msg_data record.\n");
//      }
//      this.messageDataPrimaryKey = primaryKey;
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      this.messageDataPrimaryKey = NULL;
//
//
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      ex.printStackTrace();
//      return WriteResult.FAILURE;
//    }
//  }
//@Override
//public WriteResult writeVesselSignature() throws SQLException {
//  try {
//    Integer mmsi = message.getMMSI();
//    Integer imo = message.getIMO();
//    String callSign = message.getCallsign();
//    String name = message.getShipName();
//    Integer vesselTypeId = message.getVesselTypeId();
//
//    // TODO CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY
//
//    String sqlUpdate =
//            "INSERT INTO vessel_signature(" +
//                    "mmsi," +
//                    "imo," +
//                    "call_sign," +
//                    "name," +
//                    "vessel_type_id" +
//                    ") " +
//                    "VALUES (" +
//                    mmsi + "," +
//                    imo + ",'" +
//                    callSign + "','" +
//                    name + "'," +
//                    vesselTypeId +
//                    ")";
//
//    int primaryKey = connection.insertOneRecord(sqlUpdate);
//    if (primaryKey == -1) {
//      throw new SQLException("Error recording primary key for vessel_signature record.\n");
//    }
//    this.vesselSignaturePrimaryKey = primaryKey; // Update for writeMessageData foreign key.
//
//    return WriteResult.SUCCESS;
//  } catch (UnsupportedMessageType ex) {
//    // WRITE BLANK RECORD WITH NULL COLUMNS
//
//    this.vesselSignaturePrimaryKey = NULL;
//
//    return WriteResult.UNSUPPORTED;
//  } catch (Exception ex) {
//    return WriteResult.FAILURE;
//  }
//}
//  @Override
//  public WriteResult writeVoyageData() throws SQLException {
//    try {
//      Float draught = message.getDraught();
//      String eta = message.getETA();
//      String destination = message.getDestination();
//      String sqlUpdate;
//
//      if (eta.equals("NULL")) {
//        sqlUpdate =
//                "INSERT INTO voyage_data(draught, eta, destination) " +
//                        "VALUES (" +
//                        draught + "," +
//                        NULL + ",'" +
//                        destination +
//                        "')";
//      }else {
//        sqlUpdate =
//                "INSERT INTO voyage_data(" +
//                        "draught," +
//                        "eta," +
//                        "destination" +
//                        ") " +
//                        "VALUES (" +
//                        draught + ",'" +
//                        eta + "','" +
//                        destination +
//                        "')";
//      }
//
//      int primaryKey = connection.insertOneRecord(sqlUpdate);
//      if (primaryKey == -1) {
//        throw new SQLException("Error recording primary key for voyage_data record.\n");
//      }
//      this.voyageDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // This message does not contain voyage data. FK will be null in Message_Data table.
//
//      //this.vesselDataPrimaryKey = -1;
//      this.vesselDataPrimaryKey = NULL;
//
//      return WriteResult.UNSUPPORTED;
//    }
//  }
//
//  @Override
//  public WriteResult writeVesselData() throws SQLException {
//    try {
//      Integer toBow = message.getToBow();
//      Integer toStern = message.getToStern();
//      Integer toPort = message.getToPort();
//      Integer toStarboard = message.getToStarboard();
//
//      // TODO CHECK TO SEE IF THIS PERMUTATION IN TABLE FIRST THEN WRITE IF NECESSARY
//
//      String sqlUpdate =
//              "INSERT INTO vessel_data(" +
//                      "to_bow," +
//                      "to_stern," +
//                      "to_port," +
//                      "to_starboard" +
//                      ") " +
//              "VALUES (" +
//                      toBow + "," +
//                      toStern + "," +
//                      toPort + "," +
//                      toStarboard +
//                      ")";
//
//      int primaryKey = connection.insertOneRecord(sqlUpdate);
//      if (primaryKey == -1) {
//        throw new SQLException("Error recording primary key for vessel_data record.\n");
//      }
//      this.vesselDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//
//      //this.vesselDataPrimaryKey = -1;
//      this.vesselDataPrimaryKey = NULL;
//
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      return WriteResult.FAILURE;
//    }
//  }
//
//  @Override
//  public WriteResult writeNavigationData() throws SQLException {
//    try {
//
//      Float sog = message.getSpeedOverGround();
//      Float cog = message.getCourseOverGround();
//      Float heading = message.getHeading();
//      Float rot = message.getRateOfTurn();
//      Integer navStatusId = message.getNavStatusId();
//      Integer maneuverIndicatorId = message.getManeuverIndicatorId();
//
//      String sqlUpdate =
//              "INSERT INTO navigation_data(" +
//                      "speed_over_ground," +
//                      "course_over_ground," +
//                      "heading," +
//                      "rate_of_turn," +
//                      "nav_status_id," +
//                      "maneuver_indicator_id" +
//                      ") " +
//              "VALUES (" +
//                      sog + "," +
//                      cog + "," +
//                      heading + "," +
//                      rot + "," +
//                      navStatusId + "," +
//                      maneuverIndicatorId +
//                      ")";
//
//      int primaryKey = connection.insertOneRecord(sqlUpdate);
//      if (primaryKey == -1) {
//        throw new SQLException("Error recording primary key for navigation_data record.\n");
//      }
//      this.navigationDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//
//      this.navigationDataPrimaryKey = NULL;
//      //this.navigationDataPrimaryKey = -1;
//
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      return WriteResult.FAILURE;
//    }
//  }
//
//  @Override
//  public WriteResult writeGeospatialData() throws SQLException {
//    try {
//
//      int accuracy = message.getAccuracy() ? 1 : 0;
//      PGpoint coord = CoordinateUtil.getCoord(message.getLat(), message.getLong());
//
//      String sqlUpdate =
//              "INSERT INTO geospatial_data(" +
//                      "coord," +
//                      "accuracy" +
//                      ") " +
//              "VALUES (" +
//                      coord + "," +
//                      accuracy +
//                      ")";
//
//      int primaryKey = connection.insertOneRecord(sqlUpdate);
//      if (primaryKey == -1) {
//        throw new SQLException("Error recording primary key for geospatial_data record.\n");
//      }
//      this.geospatialDataPrimaryKey = primaryKey; // Update for writeMessageData foreign key.
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//
//      this.geospatialDataPrimaryKey = NULL;
//      //this.geospatialDataPrimaryKey = -1;
//
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      return WriteResult.FAILURE;
//    }
//  }
//
//  // NOTE: vessel_type table is a static reference table
//  @Deprecated
//  @Override
//  public WriteResult writeVesselType() throws SQLException {
//    try {
//      message.getVesselTypeId();
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      return WriteResult.FAILURE;
//    }
//  }
//
//  // NOTE: nav_status table is a static reference table
//  @Deprecated
//  @Override
//  public WriteResult writeNavigationStatus() throws SQLException {
//    try {
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      return WriteResult.UNSUPPORTED;
//    }
//  }
//
//  // NOTE: maneuver_indicator table is a static reference table
//  @Deprecated
//  @Override
//  public WriteResult writeManeuverIndicator() throws SQLException {
//    try {
//      message.getManeuverIndicatorId();
//
//      return WriteResult.SUCCESS;
//    } catch (UnsupportedMessageType ex) {
//      // WRITE BLANK RECORD WITH NULL COLUMNS
//      return WriteResult.UNSUPPORTED;
//    } catch (Exception ex) {
//      return WriteResult.FAILURE;
//
//    }
//  }
}
