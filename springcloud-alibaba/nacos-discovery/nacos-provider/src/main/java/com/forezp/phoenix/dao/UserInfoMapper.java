package com.forezp.phoenix.dao;

import com.forezp.phoenix.domain.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jixin on 18-3-11.
 */
@Mapper
public interface UserInfoMapper {

   @Select("select * from USER")
   List<UserInfo> findUsers();

   @Insert("UPSERT INTO USER(id,name,age,phone,email ) VALUES(#{user.id}, #{user.name},#{user.age}, #{user.phone},#{user.email})")
   void addUser(@Param("user") UserInfo userInfo);
}
