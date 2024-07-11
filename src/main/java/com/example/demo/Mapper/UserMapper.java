package com.example.demo.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select userName from user where userName = #{userName}")
    public String selectUserName(String userName);

    @Select("select userPassword from user where userName = #{userName}")
    public String selectUserPassword(String userName);

    @Insert("insert into user(userName, userPassword) values(#{userName}, #{userPassword})")
    public void addUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

    @Update("update goods set GoodsState='已下架' where GoodsID=#{GoodsID}")
    public boolean DeleteGoods(@Param("GoodsID")int GoodsID);

    @Update("update goods set GoodsName=#{GoodsName} where GoodsID=#{GoodsID}")
    public boolean UpdateGoodName(@Param("GoodsID")int GoodsID,@Param("GoodsName")String GoodsName);
}

