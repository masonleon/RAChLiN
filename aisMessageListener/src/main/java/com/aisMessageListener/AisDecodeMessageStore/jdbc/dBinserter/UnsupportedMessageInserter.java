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

class UnsupportedMessageInserter extends AbstractDatabaseInserter {

  UnsupportedMessageInserter(MessageDataInterface message) {
    super(message);
  }

  @Override
  public WriteResult writeMessage() throws SQLException {
    connection.connectIfDropped();
    connection.beginTransaction();

    try{
      System.out.println(message.getRawNMEA().toString());
      System.out.println(message.getMessageType().toString());

      preparedStatementWriteMessageData();

      connection.commitTransaction();
      return WriteResult.SUCCESS;
    } catch (SQLException ex) {
      connection.rollBackTransaction();

      //ex.printStackTrace();
    }
    return WriteResult.FAILURE;
  }

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
      ps.setNull(6, Types.INTEGER);
      ps.setNull(7, Types.INTEGER);
      ps.setNull(8, Types.INTEGER);
      ps.setNull(9, Types.INTEGER);
      ps.setNull(10, Types.INTEGER);

      int primaryKey = ps.executeUpdate();

      if (primaryKey == -1) {
        throw new SQLException("Error recording row entry for vessel_signature record.\n");
      }

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {

          return WriteResult.SUCCESS;

        } else {
          throw new SQLException("Error recording primary key for message_data record.\n");
        }
      }
    } catch (UnsupportedMessageType ex) {
      // WRITE BLANK RECORD WITH NULL COLUMNS

      //ex.printStackTrace();
      return WriteResult.UNSUPPORTED;

    } catch (Exception ex) {
      ex.printStackTrace();
      return WriteResult.FAILURE;
    }
  }

}
