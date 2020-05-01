package io.seeport.rachlin.aismessagelistener.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abstract implementation of a PostgreSQL DatabaseConnectionManager, that holds common logic that
 * is API-independent.
 */
public abstract class AbstractDatabaseConnectionManager implements DatabaseConnectionInterface {

  private static final String port = "5432";
  private final String url;
  private final Properties properties;
  protected Connection connection;

  /**
   * Create a database connection manager using the provided database information.
   *
   * @param host         the database host. An AWS endpoint, or localhost for testing.
   * @param databaseName the name of the database.
   * @param username     the database username.
   * @param password     the database password.
   */
  AbstractDatabaseConnectionManager(String host,
                                    String databaseName,
                                    String username,
                                    String password) {
    this.url = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
    connectToDatabase();
  }

  @Override
  public void connectIfDropped() throws SQLException {
    if (this.connection == null || !this.connection.isValid(2)) {
      connectToDatabase();
    }
  }

  @Override
  public void closeConnection() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      System.err.println("Error while closing connection.\n");
      System.exit(1);
    }
  }

  /**
   * Helper method to get a database connection. Exits the program if a connection is not possible.
   * Will overwrite current connection stored by connection manager.
   *
   * @return a new connection to the database.
   */
  private void connectToDatabase() {
    try {
      Class.forName("org.postgresql.Driver");
      this.connection = DriverManager.getConnection(this.url, this.properties);
    } catch (SQLException e) {
      System.err.println("Error while connecting to database.\n");
      e.printStackTrace();
      System.exit(1);
    } catch (ClassNotFoundException ex){
	  ex.printStackTrace();
    }
  }
}
