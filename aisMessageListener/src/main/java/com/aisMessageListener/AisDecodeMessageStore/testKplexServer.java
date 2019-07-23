package com.aisMessageListener.AisDecodeMessageStore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Test class that emulates kplex.  Listens for requests from Tcp client on on local loopback
 * address (no ssh tunnel required) and responds with 3 batches of AISmessages.  Modify
 * testMessages.txt to test different message types or to look for AIS exceptions.
 */
class testKplexServer {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Argument must be exactly one port number.\n");
      System.exit(1);
    }

    int port = Integer.parseInt(args[0]);

    for (int i = 0; i < 3; i++) {
      try (
              ServerSocket server = new ServerSocket(
                      port, 0, InetAddress.getLoopbackAddress());
              Socket socket = server.accept();
              PrintWriter out = new PrintWriter(socket.getOutputStream());
              Scanner fileScanner = new Scanner(new File("testMessages.txt"));
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


