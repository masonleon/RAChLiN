package jdbc;


import com.bostontportt.jdbc.util.DataTransferObject;


public class Message implements DataTransferObject {
    private long id;
    private int mmsi;
    private String msgType;
    private String time_received_station;
   // private int time_sent_vessel;
    private String msg_nmea_raw;
    private String msg_nmea_decoded;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMmsi() {
        return mmsi;
    }

    public void setMmsi(Integer mmsi) {
        this.mmsi = mmsi;
    }

    public String getMsgType(){
        return msgType;
    }

    public void setMsgType(String msgType){
        this.msgType = msgType;
    }

    public String getTime_received_station(){
        return time_received_station;
    }

    public void setTime_received_station(String time_received_station){


        this.time_received_station = time_received_station;
    }

//    public int getTime_sent_vessel(){
//        return time_sent_vessel;
//    }
//
//    public void setTime_sent_vessel(int time_sent_vessel){
//        this.time_sent_vessel = time_sent_vessel;
//    }

    public String getMsg_nmea_raw() {
        return msg_nmea_raw;
    }

    public void setMsg_nmea_raw(String msg_nmea_raw) {
        this.msg_nmea_raw = msg_nmea_raw;
    }

    public String getMsg_nmea_decoded(){
        return this.msg_nmea_decoded = msg_nmea_decoded;
    }

    public void setMsg_nmea_decoded(String msg_nmea_decoded){
        this.msg_nmea_decoded = msg_nmea_decoded;
    }
}
