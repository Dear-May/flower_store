package com.example.demo.Mapper;

import com.example.demo.Entity.OrderEntity;
import com.example.demo.Entity.OrderInfoEntity;
import org.apache.ibatis.annotations.*;
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


    @Select("select * from orders where id = #{orderId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "orderTime", column = "ordered_time"),
            @Result(property = "orderStatus", column = "order_state"),
            @Result(property = "orderPrice", column = "count_price")
    })
    public OrderEntity selectByOrderId(String orderId);

    @Select("select order_goodId,order_goodnum from ordergoodsinfo where order_id = #{orderId}")
    public List<OrderInfoEntity> selectGoodsByOrderId(String orderId);

    @Insert("insert into orders(id,user_id, ordered_time, order_state, count_price) values(#{id}, #{userId}, NOW(), #{orderStatus} , #{orderPrice})")
    public boolean insertOrder(@Param("id") String id, @Param("userId") int userId, @Param("orderStatus") String orderStatus, @Param("orderPrice") double orderPrice);

    @Insert("insert into ordergoodsinfo(order_id, order_goodId, order_goodnum) VALUE (#{orderId}, #{goodId}, #{goodNum})")
    public boolean insertOrderGood(@Param("orderId") String orderId, @Param("goodId") int goodId, @Param("goodNum") int goodNum);
}
