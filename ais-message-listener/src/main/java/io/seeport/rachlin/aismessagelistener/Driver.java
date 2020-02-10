package io.seeport.rachlin.aismessagelistener;

import io.seeport.rachlin.aismessagelistener.jdbc.DatabaseConnectionInterface;
import io.seeport.rachlin.aismessagelistener.jdbc.DatabaseConnectionManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main method for the tcp AIS message client.  Configures database connection then starts up client
 * on specified port number.
 */
public class Driver {

  /**
   * The main method.  Imports database access credentials and tcp listening port from a separate
   * file.
   *
   * @param args specifies 'test' or 'production'. This determines what filepath is used for
   *             database access credentials.
   * @throws InterruptedException does not need to be handled. Only occurs when another thread
   *                              interrupts the sleep method during connection recovery.
   */
  public static void main(String[] args) throws InterruptedException {
    if (args.length != 1) {
      System.err.println("Must specify whether this is a 'test' or 'production' deployment.\n");
      System.exit(1);
    }

    String databaseAccessFilePath = "";
    if (args[0].equals("test")) {
      databaseAccessFilePath = "./aisMessageListener/src/test/testData/database_credentials.txt";
    } else if (args[0].equals("production")) {
      databaseAccessFilePath = "/usr/local/bin/database_credentials.txt";
    } else {
      System.err.println("Must specify 'test' or 'production' in input parameters.\n");
    }

    String databaseHost = null;
    String databaseName = null;
    String username = null;
    String password = null;
    int tcpServerPort = -1;

    try (BufferedReader br = new BufferedReader(new FileReader(databaseAccessFilePath))) {
      databaseHost = br.readLine();
      databaseName = br.readLine();
      username = br.readLine();
      password = br.readLine();
      tcpServerPort = Integer.parseInt(br.readLine());


    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(2);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(3);
    }

    if (tcpServerPort < 0 || tcpServerPort > 65535) {
      System.err.println("Specified port number must be between 0 and 65535.");
      System.exit(4);
    }

    DatabaseConnectionInterface dbConnection = new DatabaseConnectionManager(
            databaseHost, databaseName, username, password);

    new AisTcpClient(tcpServerPort, dbConnection).start();
  }
}
