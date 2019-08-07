package org.apache.struts.helloworld.model;

/**
 * Model class that stores a message.
 *
 * @author Bruce Phillips
 */
public class MessageStore {

  private String message;

  public MessageStore() {
    message = "On n’est point toujours une bête pour l’avoir été quelquefois.";
  }

  public String getMessage() {
    return message;
  }

}
