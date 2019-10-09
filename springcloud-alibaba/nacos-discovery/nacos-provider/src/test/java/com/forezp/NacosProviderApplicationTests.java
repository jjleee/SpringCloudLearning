package com.forezp;

import com.forezp.hbaseApi.HBaseUtil;
import com.forezp.phoenix.dao.ProcessDataMapper;
import com.forezp.phoenix.dao.UserInfoMapper;
import com.forezp.phoenix.domain.ProcessData;
import com.forezp.phoenix.domain.UserInfo;
import com.forezp.phoenix.service.ProcessDataService;
import com.forezp.phoenix.service.UserInfoService;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NacosProviderApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    UserInfoService userInfoService;

//  @Autowired
//  ProcessDataService processDataService;

    @Autowired
    ProcessDataService processDataService;

    @Test
    public void findAllUser() {
        List<UserInfo> userInfos = userInfoService.findAllUser();
        for (UserInfo userInfo : userInfos) {
            System.out.println(String.format("ID=%s;NAME=%s", userInfo.getId(), userInfo.getName()));
        }
    }

    @Test
    public void findByRowkey() {
        String rowkey = "1-1-8-9-1566640535438";
        List<ProcessData> byRowKey = processDataService.findByRowKey(rowkey);
        for (ProcessData processData : byRowKey) {
            System.out.println(String.format("ROWKEY=%s;CurrentTime=%s", processData.getRowKey(), processData.getCurrentTime()));
        }
    }

    @Test
    public void findByCondition() {
        List<ProcessData> byCondition = processDataService.findByCondition("1",
                "1", "8", "43", "1566571496493", "1566647649551");
        for (ProcessData processData : byCondition) {
            System.out.println(String.format("ROWKEY=%s;CurrentTime=%s", processData.getRowKey(), processData.getCurrentTime()));
        }
    }

    @Test
    public void findByTime(){
        List<ProcessData> byTime = processDataService.findByTime("1566625759", "1566636722");
        for (ProcessData processData : byTime) {
            System.out.println(String.format("ROWKEY=%s;CurrentTime=%s", processData.getRowKey(), processData.getCurrentTime()));
        }
    }

    @Test
    public void addUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId("1007");
        userInfo.setName("Jerry");
        userInfo.setAge("21");
        userInfo.setPhone("13113113111");
        userInfo.setEmail("dsafdf@sd.com");
        userInfoService.addUser(userInfo);
    }

    @Test
    public void findByPrefix(){
        List<ProcessData>data = HBaseUtil.findByRowKey("LBFS", "1-1-4", "1-1-4-1-1567002040000", "1-1-4-48-1567062040000");
            System.out.println(data);
        }
}
