package jar;

/**
 * Main method for tcp server.  Starts up server on specified port number.
 */
public class Driver {

  public static void main(String[] args) throws IllegalArgumentException {
    if (args.length != 1) {
      System.err.println("Exactly one argument required: port number.");
      System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    if (portNumber < 0 || portNumber > 65535) {
      System.err.println("Specified port number must be between 0 and 65535.");
      System.exit(1);
    }

    new AisTcpServer().start(portNumber);
  }
}
