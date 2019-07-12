package jar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A TCP server that receives AIS messages and passes the input stream to a handler.  Only one
 * client connection is expected -  a raspberry pi running a kplex NMEA multiplexer. The multiplexer
 * acts as a server for an AIS receiver connected over USB-Serial interface.
 */
public class AisTcpServer {
  // TODO: implement security manager.
  // TODO: consider if multi-threading or backlog management is applicable here.
  // TODO: consider implementing an error log.

  /**
   * Binds a server socket to the designated port and listens for tcp connection.  Once connection
   * occurs, input stream is passed to a handler.  Continues to listen after a client disconnects.
   *
   * @param port the port to bind to.
   */
  void start(int port) {
    // try-with-resources statement - socket and reader will be closed by Java runtime.
    try (
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            BufferedReader messageStream = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
    ) {
      // Swap this with AisInputStreamReader.run() to allow decoding.
      printBufferedInputStream(messageStream);
    } catch (IOException e) {
      System.err.println("Exception caught when trying to listen on port "
              + port + " or listening for a connection.");
    }
  }

  /**
   * Prints up to 50 lines to the terminal from the provided BufferedReader.  For testing only.  In
   * production, the input stream will be passed to the Ais decoder library.
   *
   * @param reader the character stream to be read
   * @throws IOException if error occurs while reading
   */
  private void printBufferedInputStream(BufferedReader reader) throws IOException {
    int readCount = 0;
    int readLimit = 50;

    String line;
    while ((line = reader.readLine()) != null && readCount < readLimit) {
      System.out.println(line);
      readCount++;
    }
  }
}
