package com.forezp.web;

import clojure.lang.IFn;
import com.forezp.hbaseApi.HBaseUtil;
import com.forezp.phoenix.domain.ProcessData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by forezp on 2019/5/11.
 */

@RestController
public class ProviderController {

    Logger logger= LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/services")
    public String getServices(){
        List<String> serviceNames=discoveryClient.getServices();

        StringBuilder stringBuilder=new StringBuilder();
        for (String s: serviceNames){
            stringBuilder.append(s).append("\n");
            List<ServiceInstance> serviceInstances=discoveryClient.getInstances(s);
            if(serviceInstances!=null&&serviceInstances.size()>0){
                for (ServiceInstance serviceInstance: serviceInstances){
                    logger.info("serviceName:"+s+" host:"+serviceInstance.getHost()+" port:"+serviceInstance.getPort());
                }
            }
        }
        return stringBuilder.toString();
    }

    @GetMapping("/hi")
    public ResponseEntity hi(@RequestParam(value = "tableName",required = true)String tableName,@RequestParam(value = "prefix",required = true)String prefix,@RequestParam(value = "start",required = true)String start, @RequestParam(value = "end",required = true)String end){
        return new ResponseEntity(HBaseUtil.findByRowKey(tableName,prefix,start,end), HttpStatus.OK);
    }
}

