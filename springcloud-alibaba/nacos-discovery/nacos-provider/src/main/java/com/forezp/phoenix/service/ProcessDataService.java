package com.forezp.phoenix.service;

import com.forezp.phoenix.dao.ProcessDataMapper;
import com.forezp.phoenix.domain.ProcessData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangzhipeng on 2017/4/20.
 */
@Service
public class ProcessDataService {
    
    @Autowired
    private ProcessDataMapper processDataMapper;


    public List<ProcessData> findByRowKey(String rowkey) {
        return processDataMapper.findByKey(rowkey);
    }

    public List<ProcessData> findByCondition(String lineNo,String cabNo,String cellNo,String channelNo,String time1,String time2){
        return processDataMapper.findByMultipleConditions(lineNo,cabNo,cellNo,channelNo,time1,time2);
    }

    public List<ProcessData> findByTime(String time1,String time2){
        return processDataMapper.findByTime(time1,time2);
    }
}