package com.aisMessageListener.AisDecodeMessageStore.jdbc;


//public class MessageDAO extends DataAccessObject<Message> {
//
//    private static final String INSERT = "INSERT INTO message_log (mmsi, msg_type, time_received_station," +
//            "msg_nmea_raw, msg_nmea_decoded) VALUES (?, ?, ?, ?, ?)";
//
//    public MessageDAO(Connection connection) {
//        super(connection);
//    }
//
//    @Override
//    public Message findById(long message_id) {
////        Message message = new Message();
////        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
////            statement.setLong(1, message_id);
////            ResultSet rs = statement.executeQuery();
////            while(rs.next()){
////                message.setId(rs.getLong("message_id"));
////                message.setMmsi(rs.getInt("mmsi"));
////                message.setMsgType(rs.getString("message_type"));
////                message.setTime_received_station(rs.getString("time_received_station"));
////                message.setMsg_nmea_raw(rs.getString("nmea_raw"));
////                message.setMsg_nmea_decoded(rs.getString("nmea_decoded"));
////
////            }
////        }catch (SQLException e){
////            e.printStackTrace();
////            throw new RuntimeException(e);
////        }
////        return message;
//        return null;
//    }
//
//    @Override
//    public List<Message> findAll() {
//        return null;
//    }
//
//    @Override
//    public Message update(Message dto) {
//        return null;
//    }
//
//    @Override
//    public Message create(Message dto) {
//        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);){
//            statement.setInt(1, dto.getMmsi());
//            statement.setString(2, dto.getMsgType());
//            statement.setString(3, dto.getTime_received_station());
//            statement.setString(4, dto.getMsg_nmea_raw());
//            statement.setString(5, dto.getMsg_nmea_decoded());
//
//            int id = this.getLastVal(MESSAGE_SEQUENCE);
//            return this.findById(id);
//            //statement.execute();
//            //return null;
//        }catch(SQLException e){
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void delete(long id) {
//
//    }
// }
