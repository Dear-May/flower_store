package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.UserTestEntity;
import com.example.demo.service.UserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenzhk
 * &#064;Date  2024/6/23 14:42
 **/

@Service
public class UserService implements UserIService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserTestEntity> queryAllUser() {
        return userDao.queryAllUser();
    }
}
