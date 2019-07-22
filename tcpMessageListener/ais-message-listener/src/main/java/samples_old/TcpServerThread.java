package samples_old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// This is an outdated class.  kplex cannot effectively send messages, and must be accessed by a
// tcp client.

/**
 * Thread created by AisTcpServer to process a single connection from kplex multiplexer.  Reads all
 * stream data, passes that data to a handler, then closes the socket.
 */
@Deprecated
public class TcpServerThread extends Thread {

  private Socket socket;

  /**
   * Creates a new thread using the provided tcp socket.
   *
   * @param socket the socket to be read.
   */
  TcpServerThread(Socket socket) {
    super("TcpServerThread");
    this.socket = socket;
  }

  /**
   * Method to run the thread.  Passes the kplex message stream to a message decoder/handler.
   */
  public void run() {

    try (BufferedReader messageStream = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
    ) {
      // Swap this with AisInputStreamReader.run() to allow decoding.
      printBufferedInputStream(messageStream);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Prints lines to the terminal from the provided BufferedReader.  For testing only.  In
   * production, the input stream will be passed to the Ais decoder library.
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
