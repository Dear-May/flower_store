package com.example.demo.Mapper;

import com.example.demo.Entity.GoodEntity;
import com.example.demo.Entity.GoodEntityGet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ShoppingCartMapper {
    @Select("select goods.* from shopping_cart inner join goods on shopping_cart.cart_goodid=goods.id where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public List<GoodEntityGet> findPersonalGoodInCart(@Param("userId") int userId,@Param("GoodsID")int GoodsID);

    @Select("SELECT cart_goodnum from shopping_cart where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public int getgoodnumber(@Param("userId") int userId,@Param("GoodsID")int GoodsID);//查询当前用户是否已经将这个商品加入购物车

}
