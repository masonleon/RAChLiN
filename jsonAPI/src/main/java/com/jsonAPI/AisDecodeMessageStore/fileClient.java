package com.jsonAPI.AisDecodeMessageStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import dk.tbsalling.aismessages.AISInputStreamReader;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;

/**
 * Class fileClient reads a .txt file containing encoded NMEA sentences and decodes the AIS messages
 * to the terminal. Used for testing purposes and when access to live AIS server over TCP is not
 * available.
 */
public class fileClient {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_RED = "\u001B[31m";

  public static void main(String[] args) {

    System.out.println("AISMessages fileClient Test");
    System.out.println("--------------------");

    Map<AISMessageType, Integer> messageTypeList = new HashMap<>();
    Map<Integer, List<AISMessage>> aisMessageMapToVesselMMSI = new HashMap<>();
    AtomicInteger messageCount = new AtomicInteger();

    try {
      InputStream textLog = new FileInputStream("src/data/nmea_msg_raw/AIS_NMEA_LOG.txt");

//      Scanner scanner = new Scanner(textLog);
//
//      while (scanner.hasNextLine()) {
//        String line = scanner.nextLine();
//        InputStream message = new ByteArrayInputStream(line.getBytes());

      try {

//          AISInputStreamReader streamReader = new AISInputStreamReader(message, aisMessage ->
//                  System.out.println("Received AIS message from MMSI " + aisMessage.getSourceMmsi().getMMSI() + ": " + aisMessage)
//          );

        AISInputStreamReader streamReader = new AISInputStreamReader(textLog, aisMessage ->
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
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}