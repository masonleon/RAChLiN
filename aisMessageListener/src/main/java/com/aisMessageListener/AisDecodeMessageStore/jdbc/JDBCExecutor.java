package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

  public static void main(String... args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
            "testais", "masonleon", "password");
    try {
      Connection connection = dcm.getConnection();
      // Statement statement = connection.createStatement();
      // ResultSet resultSet = ((Statement) statement).executeQuery("SELECT COUNT(*) FROM message_log");
//      while (resultSet.next()) {
//        System.out.println(resultSet.getInt(1));
//      }

//      MessageDAO messageDAO = new MessageDAO(connection);
//      Message message = new Message();
//      message.setMmsi(432624000);
//      message.setMsgType("ShipAndVoyageRelatedData");
//      message.setTime_received_station("2019-07-02T17:58:29.945382Z");
//      message.setMsg_nmea_raw("!AIVDM,2,1,2,A,56LUAP02>M9oL`<v2210u<DT@tr0hD4@E:22221?2rhF:5l40Dl0H0m0,0*6E");
//      message.setMsg_nmea_decoded("ShipAndVoyageData{messageType=ShipAndVoyageRelatedData, imo=IMO [imo=9335965], callsign='7JCO', shipName='POSEIDON LEADER', shipType=CargoNoAdditionalInfo, toBow=23, toStern=176, toStarboard=10, toPort=22, positionFixingDevice=Gps, eta='08-07 04:00', draught=8.3, destination='PA CTB', dataTerminalReady=false} AISMessage{nmeaMessages=[NMEAMessage{rawMessage='!AIVDM,2,1,2,A,56LUAP02>M9oL`<v2210u<DT@tr0hD4@E:22221?2rhF:5l40Dl0H0m0,0*6E'}, NMEAMessage{rawMessage='!AIVDM,2,2,2,A,`88888888888880,2*7E'}], metadata=Metadata{source='SRC', received=2019-07-02T17:58:29.945382Z}, repeatIndicator=0, sourceMmsi=MMSI [mmsi=432624000]}");

//      messageDAO.create(message);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
