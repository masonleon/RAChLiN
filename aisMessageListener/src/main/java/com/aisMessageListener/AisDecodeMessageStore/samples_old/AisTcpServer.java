package com.aisMessageListener.AisDecodeMessageStore.samples_old;

import java.io.IOException;
import java.net.ServerSocket;

// This is an outdated class.  kplex cannot effectively send messages, and must be accessed by a
// tcp client.

/**
 * A TCP server that receives AIS messages and passes the input stream to a handler.  Only one
 * client connection is expected -  a raspberry pi running a kplex NMEA multiplexer. The multiplexer
 * acts as a server for an AIS receiver connected over USB-Serial interface.  The server allows
 * multithreading to help resolve backlog of multiplexer connections, and to allow for additional
 * receivers in future versions.
 */
@Deprecated
class AisTcpServer {

  /**
   * Binds a server socket to the designated port and listens for tcp connections indefinitely. Once
   * connection occurs, input stream is passed to a handler on a new thread.
   *
   * @param port the port to bind to.
   */
  void start(int port) {
    // try-with-resources statement - socket will be closed by Java runtime.
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        new TcpServerThread(serverSocket.accept()).start();
      }
    } catch (IOException e) {
      System.err.println("Could not connect to port " + port + ".");
      System.exit(1);
    }
  }
}


