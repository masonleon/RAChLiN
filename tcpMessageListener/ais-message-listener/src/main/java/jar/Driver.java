package jar;


/**
 * Main method for the tcp AIS message client.  Starts up client on specified port number.
 */
public class Driver {

  /**
   * The main method.
   *
   * @param args the port name to bind to.
   * @throws IllegalArgumentException for invalid or nonexistent port name.
   * @throws InterruptedException     does not need to be handled. Only occurs when another thread
   *                                  interrupts the sleep method during connection recovery.
   */
  public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
    if (args.length != 1) {
      System.err.println("Exactly one argument required: port number.");
      System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    if (portNumber < 0 || portNumber > 65535) {
      System.err.println("Specified port number must be between 0 and 65535.");
      System.exit(1);
    }

    new AisTcpClient().start(portNumber);
  }
}
