package com.forezp.hbaseApi;

import com.forezp.phoenix.domain.ProcessData;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lijj
 * @date 2019-08-27
 */
public class HBaseUtil {

    /**
     * 创建HBase表.
     *
     * @param tableName 表名
     * @param cfs       列族的数组
     * @return 是否创建成功
     */
    public static boolean createTable(String tableName, List<String> cfs) {
        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {
            if (admin.tableExists(TableName.valueOf(tableName))) {
                return false;
            }
            List<ColumnFamilyDescriptor> familyDescriptors = new ArrayList<>(cfs.size());
            for (String column : cfs) {
                familyDescriptors.add(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(column)).build());
            }
            TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
                    .setColumnFamilies(familyDescriptors).build();
            admin.createTable(tableDescriptor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 删除hbase表.
     *
     * @param tableName 表名
     * @return 是否删除成功
     */
    public static boolean deleteTable(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * hbase插入一条数据.
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @param cfName    列族名
     * @param qualifier 列标识
     * @param data      数据
     * @return 是否插入成功
     */
    public static boolean putRow(String tableName, String rowKey, String cfName, String qualifier,
                                 String data) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier), Bytes.toBytes(data));
            table.put(put);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    public static boolean putRows(String tableName, List<Put> puts) {
        try (Table table = HBaseConn.getTable(tableName)) {
            table.put(puts);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 获取单条数据.
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @return 查询结果
     */
    public static Result getRow(String tableName, String rowKey) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static Result getRow(String tableName, String rowKey, FilterList filterList) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setFilter(filterList);
            return table.get(get);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static ResultScanner getScanner(String tableName) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setCaching(-1);
            scan.setCacheBlocks(false);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 批量检索数据.
     *
     * @param tableName   表名
     * @param startRowKey 起始RowKey
     * @param endRowKey   终止RowKey
     * @return ResultScanner实例
     */
    public static ResultScanner getScanner(String tableName, String startRowKey, String endRowKey) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.withStartRow(Bytes.toBytes(startRowKey));
            scan.withStopRow(Bytes.toBytes(endRowKey));
            scan.setCaching(-1);
            scan.setCacheBlocks(false);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static ResultScanner getScanner(String tableName, String startRowKey, String endRowKey,
                                           FilterList filterList) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.withStartRow(Bytes.toBytes(startRowKey));
            scan.withStopRow(Bytes.toBytes(endRowKey));
            scan.setFilter(filterList);
            scan.setCaching(-1);
            scan.setCacheBlocks(false);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * HBase删除一行记录.
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @return 是否删除成功
     */
    public static boolean deleteRow(String tableName, String rowKey) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    public static boolean deleteColumnFamily(String tableName, String cfName) {
        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(cfName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteQualifier(String tableName, String rowKey, String cfName,
                                          String qualifier) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier));
            table.delete(delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    public static List<ProcessData> findByRowKey(String tableName, String prefix, String startKey, String endKey) {
        Filter filter = new PrefixFilter(Bytes.toBytes(prefix));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));
        ResultScanner scanner = HBaseUtil
                .getScanner(tableName, startKey, endKey, filterList);
        List<ProcessData> list = new ArrayList<>();
        if (scanner != null) {
            scanner.forEach(result -> {
                ProcessData data = new ProcessData();
                data.setRowKey(Bytes.toString(result.getRow()));
                data.setBatteryTemperature(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("batterytemperature"))));
                data.setLineNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("lineno"))));
                data.setCabNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("cabno"))));
                data.setChannelNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("channelno"))));
                data.setCapacity(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("capacity"))));
                data.setCellNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("cellno"))));
                data.setCurrent(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("curr"))));
                data.setCurrentTime(Bytes
                        .toLong(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("currenttime"))));
                data.setEnergy(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("energy"))));
                data.setFuncCode(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("funccode"))));
                data.setLoopNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("loopno"))));
                data.setPovl(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("povl"))));
                data.setRatio(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("ratio"))));
                data.setRunState(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("runstate"))));
                data.setRunTime(Bytes
                        .toLong(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("runtime"))));
                data.setStepNo(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("stepno"))));
                data.setStepType(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("steptype"))));
                data.setSumStep(Bytes
                        .toInt(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("sumstep"))));
                data.setVoltage(Bytes
                        .toDouble(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("volt"))));
                list.add(data);
            });
            scanner.close();
        }
        return list;
    }
}
