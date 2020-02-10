package io.seeport.rachlin.aismessagelistener;

import io.seeport.rachlin.aismessagelistener.jdbc.DatabaseConnectionInterface;
import io.seeport.rachlin.aismessagelistener.jdbc.dBinserter.DatabaseInserterFactory;
import io.seeport.rachlin.aismessagelistener.jdbc.dBinserter.DatabaseInserterInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

import dk.tbsalling.aismessages.AISInputStreamReader;
import dk.tbsalling.aismessages.ais.messages.AISMessage;

/**
 * A TCP client that requests AIS messages and uses them to update the message database. The
 * expected server is a Raspberry Pi running a kplex NMEA multiplexer. The multiplexer receives
 * messages from an AIS receiver connected over USB-Serial interface.
 */
class AisTcpClient {

  private DatabaseConnectionInterface connectManager;
  private int portNumber;

  /**
   * Creates a client using a the provided port number and connection manager.
   *
   * @param portNumber     the port number to connect to for tcp requests.
   * @param connectManager the connection manager for database updates.
   */
  AisTcpClient(int portNumber, DatabaseConnectionInterface connectManager) {
    this.portNumber = portNumber;
    this.connectManager = connectManager;
  }

  /**
   * Starts connection with the kplex server and requests AIS messages indefinitely. Received
   * messages will be forwarded to an inserter for database update. Before running, open an ssh
   * tunnel that forwards the desired port from localhost.  Use autossh to keep the tunnel alive.
   *
   * @throws InterruptedException does not need to be handled. Only occurs when another thread
   *                              interrupts the sleep method during connection recovery.
   */
  void start() throws InterruptedException {

    while (true) {
      try (
              Socket clientSocket = new Socket(InetAddress.getLoopbackAddress(), portNumber);
              InputStream messageStream = clientSocket.getInputStream()
      ) {
        AISInputStreamReader streamReader = new AISInputStreamReader(
                messageStream, this::insertMessageIntoDatabase);

        streamReader.run();

      } catch (IOException e) {
        // Wait 5 seconds and attempt to reconnect. If tunnel dropped, autossh will reconnect it.
        System.err.println("Error connecting to kplex server.\n");
        Thread.sleep(5000);
      }
    }
  }

  /**
   * Inserts a decoded AIS message into the database as a transaction.
   *
   * @param message a decoded AIS message.
   */
  private void insertMessageIntoDatabase(AISMessage message) {
    DatabaseInserterInterface inserter = DatabaseInserterFactory.getDatabaseInserter(message);

    try {
      inserter.attachConnection(connectManager);
      inserter.writeMessage();

    } catch (SQLException e) {
      System.err.println("Unexpected database error\n");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Prints lines to the terminal from the provided BufferedReader.  For testing only.  In
   * production, the input stream will be passed to the AIS decoder library.
   *
   * @param reader the character stream to be read
   * @throws IOException if error occurs while reading
   */
  private static void printBufferedInputStream(BufferedReader reader) throws IOException {

    String line;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
  }
}
