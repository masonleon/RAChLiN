/*
 * AISMessages
 * - a java-based library for decoding of AIS messages from digital VHF radio traffic related
 * to maritime navigation and safety in compliance with ITU 1371.
 *
 * (C) Copyright 2011- by S-Consult ApS, DK31327490, http://s-consult.dk, Denmark.
 *
 * Released under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 * For details of this license see the nearby LICENCE-full file, visit http://creativecommons.org/licenses/by-nc-sa/3.0/
 * or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 *
 * NOT FOR COMMERCIAL USE!
 * Contact sales@s-consult.dk to obtain a commercially licensed version of this software.
 *
 */

package io.seeport.harborpilot.aisMessageListener.unused_sample_code;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import dk.tbsalling.aismessages.AISInputStreamReader;

public class SimpleDemoApp {

  public void runDemo() throws IOException {

    InputStream inputStream = new ByteArrayInputStream(demoNmeaStrings.getBytes());

    System.out.println("AISMessages Demo App");
    System.out.println("--------------------");

    AISInputStreamReader streamReader = new AISInputStreamReader(inputStream, aisMessage ->
            System.out.println("Received AIS message from MMSI " + aisMessage.getSourceMmsi().getMMSI() + ": " + aisMessage)
    );

    streamReader.run();
  }

  public static void main(String[] args) throws IOException {
    new SimpleDemoApp().runDemo();
  }

  //TODO: test for 2 Part Messages
  private final String demoNmeaStrings = new String(
          "!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53\n" +
                  "!AIVDM,2,1,1,,539L8BT29ked@90F220I8TE<h4pB22222222220o1p?4400Ht00000000000,0*49\n" +
                  "!AIVDM,2,2,1,,00000000008,2*6C\n" +
                  "!AIVDM,1,1,,B,15NG3N0019rrjbpH?JAdfr7>041t,0*61\n" +
                  "!AIVDM,2,1,5,A,56=;<842@Hr<8hQ6221Tn1DpTHu8lUAV22222216O1EGC5s80JUDp3V@,0*6F\n" +
                  "!AIVDM,1,1,,A,15NfDOPP01JrmHhH>VGWMgwL08KD,0*53\n"
  );

}