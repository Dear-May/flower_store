package com.example.demo.Mapper;

import com.example.demo.Entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    @Select("select * from orders where user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "orderTime", column = "ordered_time"),
            @Result(property = "orderStatus", column = "order_state"),
            @Result(property = "orderPrice", column = "count_price")
    })
    public List<OrderEntity> selectByUserId(int userId);

}
