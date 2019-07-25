package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abstract implementation of a DatabaseConnectionManager, that holds common logic that is API-independent.
 */
public abstract class AbstractDatabaseConnectionManager implements DatabaseConnectionInterface {

    private static final String port = "5432";
    private final String url;
    private final Properties properties;
    private Connection connection;

    /**
     * Create a database connection manager using the provided database information.
     *
     * @param host         the database host. An AWS endpoint, or localhost for testing.
     * @param databaseName the name of the database.
     * @param username     the database username.
     * @param password     the database password.
     */
    AbstractDatabaseConnectionManager(String host, String databaseName, String username,
                                     String password) {
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
        this.connection = null;
    }

    /**
     * Returns a Connection to this database.  Same as getConnection() except connection is tested for
     * validity first (connections can go stale). Testing a connection can affect performance (many
     * systems test by performing a SQL query) so consider using this method only to resolve
     * SQLExceptions.  Exits the program if a connection is not possible.
     *
     * @return a Connection to the database.
     * @throws SQLException if isValid timeout is negative (e.g., exception does not need to be
     *                      handled here).
     */
    public Connection getValidatedConnection() throws SQLException {
        if (this.connection == null) {
            return connectToDatabase();
        }
        if (this.connection.isValid(2)) {
            return this.connection;
        }
        closeConnection();
        return connectToDatabase();
    }

    /**
     * Returns a Connection to this database.  Exits the program if a connection is not possible.
     *
     * @return a Connection to the database.
     */
    public Connection getConnection() {
        if (this.connection == null) {
            return connectToDatabase();
        }
        return this.connection;
    }

    /**
     * Helper method to close the database connection.  Exits the program if a close is not possible.
     */
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
     *
     * @return a new connection to the database.
     */
    private Connection connectToDatabase() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.properties);
        } catch (SQLException e) {
            System.err.println("Error while connecting to database.\n");
            System.exit(1);
        }
        return this.connection;
    }
}
