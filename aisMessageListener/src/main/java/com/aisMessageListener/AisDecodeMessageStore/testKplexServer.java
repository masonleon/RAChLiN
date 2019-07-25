package com.aisMessageListener.AisDecodeMessageStore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Test class that emulates kplex.  Listens for requests from Tcp client on local loopback address
 * (no ssh tunnel required) and responds with 3 separate batches of AISmessages.
 */
class testKplexServer {

  /**
   * The main class.  Searches for test message files in the test folder.
   *
   * @param args port test_file.txt
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Argument must be <port> <message_file.txt>.\n");
      System.exit(1);
    }

    int port = Integer.parseInt(args[0]);
    String filepath = "./aisMessageListener/src/test/testData/" + args[1];

    for (int i = 0; i < 3; i++) {
      try (
              ServerSocket server = new ServerSocket(
                      port, 0, InetAddress.getLoopbackAddress());
              Socket socket = server.accept();
              PrintWriter out = new PrintWriter(socket.getOutputStream());
              Scanner fileScanner = new Scanner(new File(filepath))
      ) {
        while (fileScanner.hasNext()) {
          String line = fileScanner.nextLine();
          out.println(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
}


