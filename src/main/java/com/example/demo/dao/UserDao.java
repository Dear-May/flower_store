package com.example.demo.dao;

import com.example.demo.Entity.UserTestEntity;

import java.util.List;

public interface UserDao {

    List<UserTestEntity> queryAllUser();
}
