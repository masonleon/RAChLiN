package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

//TODO: test transactions
/**
 * JUnit tests for DatabaseConnectionManager.
 */
public class DatabaseConnectionManagerTest {

  private DatabaseConnectionInterface connectionManager;


  @Before
  public void setUp() throws Exception {
    String filepath = "src/test/testData/dbConnectParams.txt";
    Scanner fileScanner = new Scanner(new File(filepath));
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
  public void testConnectIfDropped() throws SQLException {
    connectionManager.closeConnection();
    connectionManager.connectIfDropped();
    connectionManager.queryOneInt("select nav_status_id from nav_status", 1);
    // No exceptions thrown.  Successful pass.

  }

  @Test
  public void testQueryOneInt() throws SQLException {
    int result = connectionManager.queryOneInt("select nav_status_id from " +
            "nav_status where description = 'not under command'", 1);
    assertEquals(result, 2);
  }

  @Test
  public void testQueryOneString() throws SQLException {
    String result = connectionManager.queryOneString("select description from " +
            "nav_status where nav_status_id = 5", 1);
    assertEquals(result, "moored");
  }

  //TODO: update test to remove inserted result at the end.
  @Test
  public void testInsertOneRecord() throws SQLException {
    int key = connectionManager.insertOneRecord("insert into voyage_data(draught, " +
            "eta, destination) values (638.2, '2019-07-01 19:10:25 America/New_York', 'nowhere')");

    String result = connectionManager.queryOneString("select destination from " +
            "voyage_data where voyage_data_id = " + key, 1);

    assertEquals(result, "nowhere");
  }


}