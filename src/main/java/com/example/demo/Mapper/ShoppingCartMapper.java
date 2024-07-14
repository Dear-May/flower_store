package com.example.demo.Mapper;

import com.example.demo.Entity.ShoppingCartEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ShoppingCartMapper {
    @Select("select * from shopping_cart where user_id=#{userId} and order_id is null")
    public List<ShoppingCartEntity> getCartList(@Param("userId") int userId);//获取购物车列表

    @Select("SELECT cart_goodnum from shopping_cart where id=#{id}")
    public int getgoodnumber(@Param("id") int id);//查询这个商品的数量

    @Select("select cart_goodid  from shopping_cart where id=#{id}")
    public int getgoodid(@Param("id") int id);

    @Update("update shopping_cart set cart_goodnum=cart_goodnum +1 where id=#{id}")
    public boolean CartGoodIncrement(@Param("id") int id);//增加数量

    @Update("update shopping_cart set cart_goodnum=cart_goodnum -1 where id=#{id}")
    public boolean CartGoodDecrement(@Param("id") int id);//减少数量

    @Update("update shopping_cart set order_id=#{orderId} where id=#{id}")
    public boolean ChangeCartStatus(@Param("id") int id, @Param("orderId") String orderId);

    @Delete("delete from shopping_cart where id=#{id}")
    public boolean CartDelete(@Param("id") int id);
}
