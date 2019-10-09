package com.forezp.phoenix.dao;


import com.forezp.phoenix.domain.ProcessData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

/**
 * Created by jixin on 18-3-11.
 */
@Mapper
public interface ProcessDataMapper {
   /**
    * 按rowkey查找
    * @param rowKey
    * @return
    */
   List<ProcessData> findByKey(@Param("rowKey") String rowKey);

   /**
    * 多条件查找
    * @param lineNo
    * @param cabNo
    * @param cellNo
    * @param channelNo
    * @param time1
    * @param time2
    * @return
    */
   List<ProcessData> findByMultipleConditions(@Param("lineNo")String lineNo,@Param("cabNo")String cabNo,
                                              @Param("cellNo")String cellNo,@Param("channelNo")String channelNo,
                                              @Param("time1")String time1,@Param("time2")String time2);


   /**
    * 根据时间区段查找
    * @param time1
    * @param time2
    * @return
    */
   List<ProcessData> findByTime(@Param("time1") String time1,@Param("time2") String time2);
}
