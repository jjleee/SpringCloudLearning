package com.forezp.hbaseApi;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jixin on 18-2-25.
 */
public class HBaseConnTest {

  @Test
  public void getConnTest() {
    Connection conn = HBaseConn.getHBaseConn();
    System.out.println(conn.isClosed());
    HBaseConn.closeConn();
    System.out.println(conn.isClosed());
  }

  @Test
  public void getTableTest() {
    try {
      Table table = HBaseConn.getTable("lbfs");
      System.out.println(table.getName().getNameAsString());
      table.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
