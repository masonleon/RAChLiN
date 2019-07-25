package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseConnectionManagerTest {

  private String filepath;
  private Scanner fileScanner;
  private DatabaseConnectionInterface connectionManager;


  @Before
  public void setUp() throws Exception {
    filepath = "src/test/testData/dbConnectParams.txt";
    fileScanner = new Scanner(new File(filepath));
    String host = fileScanner.next();
    String dbName = fileScanner.next();
    String username = fileScanner.next();
    String password = fileScanner.next();
    fileScanner.close();
    connectionManager = new DatabaseConnectionManager(host, dbName, username, password);
  }

  @After
  public void tearDown() {
    connectionManager.closeConnection();
  }

  @Test
  public void testGetConnection() throws SQLException {
    Connection connection = connectionManager.getConnection();
    assertTrue(connection.isValid(0));
    // Test for appropriate handling on redundant connect request.
    connection = connectionManager.getConnection();
    assertTrue(connection.isValid(0));
  }

  @Test
  public void testGetValidatedConnection() throws SQLException {
    Connection connection = connectionManager.getValidatedConnection();
    assertTrue(connection.isValid(0));
    // Test for appropriate handling on redundant connect request.
    connection = connectionManager.getValidatedConnection();
    assertTrue(connection.isValid(0));
  }

  @Test
  public void testGetValidatedConnectionValidation() throws SQLException {
    Connection connection = connectionManager.getConnection();
    connectionManager.closeConnection();
    assertFalse(connection.isValid(0));
  }

}