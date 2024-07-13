package com.example.demo.Mapper;

import com.example.demo.Entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userImage", column = "user_imgurl"),
            @Result(property = "userPhone", column = "user_phone"),
    })
    UserEntity selectUserInfo(@Param("userName") String userName);

    @Select("select user_name from user where user_name = #{userName}")
    public String selectUserName(String userName);

    @Select("select user_password from user where user_name = #{userName}")
    public String selectUserPassword(String userName);

    @Insert("insert into user(user_name, user_password) values(#{userName}, #{userPassword})")
    public void addUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

}

