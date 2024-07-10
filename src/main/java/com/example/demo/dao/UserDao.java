package com.example.demo.dao;

import com.example.demo.entity.UserTestEntity;

import java.util.List;

public interface UserDao {

    List<UserTestEntity> queryAllUser();
}
