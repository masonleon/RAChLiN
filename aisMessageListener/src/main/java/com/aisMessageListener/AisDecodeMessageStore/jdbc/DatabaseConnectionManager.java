package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * API for handling interactions with a PostgreSQL database.  Make this class available to any
 * methods that require database access.
 */
public class DatabaseConnectionManager extends AbstractDatabaseConnectionManager {
    /**
     * Create a database connection manager using the provided database information.
     *
     * @param host         the database host. An AWS endpoint, or localhost for testing.
     * @param databaseName the name of the database.
     * @param username     the database username.
     * @param password     the database password.
     */
    public DatabaseConnectionManager(String host, String databaseName, String username,
                                     String password) {
        super(host, databaseName, username, password);
    }

    //TODO: consider if we will need transactions for our db update methods.
    //TODO: consider handling for when SQL exception is thrown.  getTestedConnection() can
    // potentially resolve stale connection issues.
    //Sample insert method.  See also getOrInsertTerm from Prof. Rachlin sample.
    public int insertOneRecord(String insertSQL) throws SQLException {
        int key = -1;
        Connection con = getValidatedConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            key = rs.getInt(1);
        }

        rs.close();
        stmt.clearBatch();

        return key;
    }
}



