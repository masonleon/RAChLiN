package io.seeport.rachlin.aisMessageListener.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;

/**
 * Placeholder for all unsupported message types.
 */
public final class UnsupportedMessageData extends AbstractMessageData {

  /**
   * Default constructor does not modify any parent functionality.
   *
   * @param message the AIS Message
   */
  public UnsupportedMessageData(AISMessage message) {
    super(message);
  }

}
