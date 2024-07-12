package com.example.demo.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select user_name from user where user_name = #{userName}")
    public String selectUserName(String userName);

    @Select("select user_password from user where user_name = #{userName}")
    public String selectUserPassword(String userName);

    @Insert("insert into user(user_name, user_password) values(#{userName}, #{userPassword})")
    public void addUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

//    @Update("update goods set GoodsState='已下架' where GoodsID=#{GoodsID}")
//    public boolean DeleteGoods(@Param("GoodsID")int GoodsID);
//
//    @Update("update goods set GoodsName=#{GoodsName} where GoodsID=#{GoodsID}")
//    public boolean UpdateGoodName(@Param("GoodsID")int GoodsID,@Param("GoodsName")String GoodsName);
}

