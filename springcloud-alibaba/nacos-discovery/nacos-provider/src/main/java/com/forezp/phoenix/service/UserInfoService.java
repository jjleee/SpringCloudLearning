package com.forezp.phoenix.service;


import com.forezp.phoenix.dao.UserInfoMapper;
import com.forezp.phoenix.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangzhipeng on 2017/4/20.
 */
@Service
public class UserInfoService {
    
    @Autowired
    private UserInfoMapper userInfoMapper;


    public List<UserInfo> findAllUser() {
        return userInfoMapper.findUsers();
    }

    public void addUser(UserInfo userInfo){
        userInfoMapper.addUser(userInfo);
    }


}