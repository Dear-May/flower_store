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

    @Select("SELECT cart_goodnum from shopping_cart where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public int getgoodnumber(@Param("userId") int userId,@Param("GoodsID")int GoodsID);//查询这个商品的数量

    @Update("update shopping_cart set cart_goodnum=cart_goodnum +1 where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public boolean CartGoodIncrement(@Param("userId") int userId,@Param("GoodsID")int GoodsID);//增加数量

    @Update("update shopping_cart set cart_goodnum=cart_goodnum -1 where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public boolean CartGoodDecrement(@Param("userId") int userId,@Param("GoodsID")int GoodsID);//减少数量

    @Delete("delete from shopping_cart where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public boolean CartDelete(@Param("userId") int userId,@Param("GoodsID")int GoodsID);
}
