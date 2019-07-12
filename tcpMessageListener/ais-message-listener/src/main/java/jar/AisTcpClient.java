package jar;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A TCP client for informal testing of AisTcpServer on localhost.  Takes the place of the AIS
 * receiver to be used in production.
 */
public class AisTcpClient {

  public static final String[] TEST_MESSAGES = {
          "!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53",
          "!AIVDM,2,1,0,B,539S:k40000000c3G04PPh63<00000000080000o1PVG2uGD:00000000000,0*34",
          "!AIVDM,2,2,0,B,00000000000,2*27",
          "!AIVDM,1,1,,B,15NLol0P00rrmE6H>VKv4?vd28=M,0*20",
          "!AIVDM,1,1,,B,15N4KNPOi<rrmdjH?6aMNii40HCg,0*3C",
          "!AIVDM,1,1,,A,15NK=p005@Jrs8hH>gsd5Ig60D1S,0*39",
          "!AIVDM,1,1,,A,152LbcOP00JrpGJH>`v`Pgw:2<1G,0*05",
          "!AIVDM,1,1,,A,35N4KNP01=rrmcRH?73=LQi>01e0,0*3F",
          "!AIVDM,1,1,,A,35NG3N0000rrmL`H?E>:uh3@054S,0*2F",
          "!AIVDM,1,1,,B,35N4KNP01=rrmblH?7BeLikB00ph,0*20",
          "!AIVDM,1,1,,B,15NLol0P00rrmEDH>VJv4?wF20S1,0*6A",
          "!AIVDM,1,1,,A,15N4KNP01=rrmbFH?7M=MAkF00T:,0*07",
          "!AIVDM,1,1,,A,15Ngd7001Grrm:hH?@jc58ql00Rm,0*00",
          "!AIVDM,1,1,,A,152LbcOP00JrpGFH>`vpK?wl2<1G,0*0C",
          "!AIVDM,1,1,,B,15Ngd70OiGrrm0hH?A1c7pr>0H48,0*24",
          "!AIVDM,1,1,,B,152LbcOP00JrpGJH>`w8Iwv@26:l,0*07",
          "!AIVDM,1,1,,B,15N4KNP01<rrmT6H?9u=RQbL088B,0*65",
          "!AIVDM,1,1,,A,152LbcOP00JrpGLH>`vpNOvR2D1G,0*34",
          "!AIVDM,1,1,,A,15Ngd7001Irrln8H?ABK:ptV0D1N,0*46",
          "!AIVDM,1,1,,B,15N2bAPP00Jrq7@H>aaC;wv`08<F,0*5D",
          "!AIVDM,1,1,,B,35NOEI5000JrmIFH?F<FOh:j0000,0*4D",
          "!AIVDM,1,1,,A,15N5P9SP00Jrq:FH>b=aFOvl28?2,0*5A",
          "!AIVDM,1,1,,B,152LbcOP00JrpGFH>`vpNgvn26:l,0*7B",
          "!AIVDM,1,1,,B,15N1doP001JrmwHH?Gg02F9:0@Dv,0*0C"};


  /**
   * Main method to connect to the server on localhost using the designated port.  The server must
   * be running.
   *
   * @param args the port number.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java AisTcpClient <port number>");
      System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);

    // try-with-resources statement - socket and print writer will be closed by Java runtime
    try (
            Socket clientSocket = new Socket(InetAddress.getLocalHost(), portNumber);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
    ) {
      for (String message : TEST_MESSAGES) {
        out.println(message);
      }
    } catch (IOException e) {
      System.err.println("Could not get I/O connection for localhost. Is the server running?");
      System.exit(1);
    }
  }
}
