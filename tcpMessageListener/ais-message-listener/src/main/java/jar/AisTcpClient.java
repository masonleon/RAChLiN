package jar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A TCP client that requests AIS messages and passes the message stream to a handler.  The expected
 * server is a Raspberry Pi running a kplex NMEA multiplexer. The multiplexer receives messages from
 * an AIS receiver connected over USB-Serial interface.
 */
class AisTcpClient {

  /**
   * Starts connection with the server on the provided port and requests AIS messages indefinitely.
   * Before running, open an ssh tunnel that forwards the desired port from localhost.  Use autossh
   * to keep the tunnel alive.
   *
   * @param portNumber the port number to bind to.
   * @throws InterruptedException does not need to be handled. Only occurs when another thread
   *                              interrupts the sleep method during connection recovery.
   */
  void start(int portNumber) throws InterruptedException {

    while (true) {
      try (// try-with-resources statement - resources will be closed by Java runtime.

           Socket clientSocket = new Socket(InetAddress.getLoopbackAddress(), portNumber);
           BufferedReader messageStream = new BufferedReader(new InputStreamReader(
                   clientSocket.getInputStream()));
      ) {
        // For testing only.  In production, swap this method with AisInputStreamReader.run() to
        // allow decoding.  Also check if any AIS exceptions would need to be handled.
        printBufferedInputStream(messageStream);

      } catch (IOException e) {
        // Wait 5 seconds and attempt to reconnect. If tunnel dropped, autossh will reconnect it.
        System.err.println("Error connecting to kplex server.\n");
        Thread.sleep(5000);
      }
    }
  }

  /**
   * Prints lines to the terminal from the provided BufferedReader.  For testing only.  In
   * production, the input stream will be passed to the AIS decoder library.
   *
   * @param reader the character stream to be read
   * @throws IOException if error occurs while reading
   */
  private void printBufferedInputStream(BufferedReader reader) throws IOException {

    String line;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
  }
}
