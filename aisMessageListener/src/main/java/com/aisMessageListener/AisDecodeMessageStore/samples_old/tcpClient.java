package com.aisMessageListener.AisDecodeMessageStore.samples_old;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import dk.tbsalling.aismessages.AISInputStreamReader;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

/**
 * Class tcpClient connects via TCP socket to a raspberry pi running kplex NMEA multiplexer. Kplex
 * acts as a server for an AIS receiver connected over USB-Serial interface. This class decodes the
 * live AIS messages to the terminal. .
 */
@Deprecated
public class tcpClient {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_RED = "\u001B[31m";

  public static void main(String[] args) {

    System.out.println("AISMessages tcpClient Test");
    System.out.println("--------------------");

    Map<AISMessageType, Integer> messageTypeList = new HashMap<>();
    Map<Integer, List<AISMessage>> aisMessageMapToVesselMMSI = new HashMap<>();
    AtomicInteger messageCount = new AtomicInteger();

    try {
      Socket socket = new Socket("10.0.0.101", 10110);
      System.out.println("Connected to AIS server on " + socket.getInetAddress() + " " + socket.getPort());

      try {

        AISInputStreamReader streamReader = new AISInputStreamReader(socket.getInputStream(), aisMessage ->
        {
          AISMessageType messageType = aisMessage.getMessageType();
          int mmsi = aisMessage.getSourceMmsi().getMMSI();
          messageCount.getAndIncrement();

          System.out.println("Received AIS message from MMSI: " + aisMessage.getSourceMmsi().getMMSI() + ": " + aisMessage);

          List<AISMessage> broadcastLog = aisMessageMapToVesselMMSI.get(mmsi);

          if (broadcastLog == null) {
            System.out.println(ANSI_RED + "Vessel Record not found, creating new entry." + ANSI_RESET);
            broadcastLog = new LinkedList<>();
            aisMessageMapToVesselMMSI.put(mmsi, broadcastLog);
          }

          int count = messageTypeList.getOrDefault(messageType, 0);
          messageTypeList.put(messageType, count + 1);

          broadcastLog.add(aisMessage);
          System.out.println("MMSI          : " + mmsi + " message log updated.");
          System.out.println("Message Type  : " + messageType.getValue());
          System.out.println("Vessel Count  : " + aisMessageMapToVesselMMSI.size());
          System.out.println("Message Count : " + messageCount);

          System.out.print("Message Type Counts : ");

          for (AISMessageType key : messageTypeList.keySet()) {
            System.out.print("| #" + key.getCode() + " " + key.getValue() + " : " + messageTypeList.get(key) + " ");
          }

          System.out.println("\n-----------------------------------------------------------------------------------\n");
        }
        );

        streamReader.run();

      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}