package com.forezp.hbaseApi;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.Arrays;


/**
 * Created by jixin on 18-2-25.
 */
public class HBaseFilterTest {
  @Test
  public void addFileDetails() {
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "name", "file1.txt");
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "type", "txt");
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "size", "1024");
    HBaseUtil.putRow("FileTable", "rowkey1", "saveInfo", "creator", "jixin");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "name", "file2.jpg");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "type", "jpg");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "size", "1024");
    HBaseUtil.putRow("FileTable", "rowkey2", "saveInfo", "creator", "jixin");
    HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "name", "file3.jpg");
    HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "type", "jpg");
    HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "size", "1024");
    HBaseUtil.putRow("FileTable", "rowkey3", "saveInfo", "creator", "jixin");

  }

  @Test
  public void rowFilterTest() {
    Filter filter = new RowFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("rowkey1")));

    FilterList filterList = new FilterList(Operator.MUST_PASS_ONE, Arrays.asList(filter));

    ResultScanner scanner = HBaseUtil
        .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

    if (scanner != null) {
      scanner.forEach(result -> {
        System.out.println("rowkey=" + Bytes.toString(result.getRow()));
        System.out.println("fileName=" + Bytes
            .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
      });
      scanner.close();
    }
  }

  @Test
  public void prefixFilterTest() {
    Filter filter = new PrefixFilter(Bytes.toBytes("1-1-4-1-"));
    FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, Arrays.asList(filter));
    long begin = System.currentTimeMillis();
    ResultScanner scanner = HBaseUtil
        .getScanner("lbfs", "1-1-1-1-1566412410470", "1-1-7-48-1566571500491", filterList);
    System.out.println(System.currentTimeMillis()-begin);
    if (scanner != null) {
      scanner.forEach(result -> {
        System.out.println("rowkey=" + Bytes.toString(result.getRow()));
        System.out.println("Current=" + Bytes
            .toString(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("Current"))));
      });
      scanner.close();
    }

  }

  @Test
  public void keyOnlyFilterTest() {
    Filter filter = new KeyOnlyFilter(true);
    FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, Arrays.asList(filter));
    ResultScanner scanner = HBaseUtil
        .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

    if (scanner != null) {
      scanner.forEach(result -> {
        System.out.println("rowkey=" + Bytes.toString(result.getRow()));
        System.out.println("fileName=" + Bytes
            .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
      });
      scanner.close();
    }
  }

  @Test
  public void columnPrefixFilterTest() {
    Filter filter = new ColumnPrefixFilter(Bytes.toBytes("nam"));
    FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, Arrays.asList(filter));
    ResultScanner scanner = HBaseUtil
        .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

    if (scanner != null) {
      scanner.forEach(result -> {
        System.out.println("rowkey=" + Bytes.toString(result.getRow()));
        System.out.println("fileName=" + Bytes
            .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
        System.out.println("fileType=" + Bytes
            .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("type"))));
      });
      scanner.close();
    }
  }
}
