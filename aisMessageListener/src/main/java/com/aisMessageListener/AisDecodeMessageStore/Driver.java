package com.aisMessageListener.AisDecodeMessageStore;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionManager;

/**
 * Main method for the tcp AIS message client.  Configures database connection then starts up client
 * on specified port number.
 */
public class Driver {

  /**
   * The main method.  TCP server port specifies target port to request messages from kplex. All
   * other parameters define database access.
   *
   * @param args tcpServerPort, dbHost dbName dbUsername dbPassword
   * @throws IllegalArgumentException for invalid or nonexistent port name.
   * @throws InterruptedException     does not need to be handled. Only occurs when another thread
   *                                  interrupts the sleep method during connection recovery.
   */
  public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
    if (args.length != 5) {
      System.err.println(
              "Five arguments required: tcpServerPort, dbHost dbName dbUsername dbPassword.");
      System.exit(1);
    }

    int tcpServerPort = Integer.parseInt(args[0]);
    if (tcpServerPort < 0 || tcpServerPort > 65535) {
      System.err.println("Specified port number must be between 0 and 65535.");
      System.exit(1);
    }

    String databaseHost = args[1];
    String databaseName = args[2];
    String username = args[3];
    String password = args[4];

    DatabaseConnectionInterface dbConnection = new DatabaseConnectionManager(
            databaseHost, databaseName, username, password);

    new AisTcpClient(tcpServerPort, dbConnection).start();
  }
}
