package com.example.demo.Mapper;

import com.example.demo.Entity.GoodEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GoodsMapper {

    @Update("update goods set goods_state='已下架' where id=#{GoodsID}")
    public boolean DeleteGoods(@Param("GoodsID") int GoodsID);

    @Update("update goods set goods_name=#{GoodsName} where id=#{GoodsID}")
    public boolean UpdateGoodName(@Param("GoodsID") int GoodsID, @Param("GoodsName") String GoodsName);

    @Update("update goods set goods_describe=#{GoodsDescribe} where id=#{GoodsID}")
    public boolean UpdateGoodDes(@Param("GoodsID") int GoodsID, @Param("GoodsDescribe") String GoodsDescribe);

    @Update("update goods set good_price=#{GoodPrice} where id=#{GoodsID}")
    public boolean UpdateGoodPrice(@Param("GoodsID") int GoodsID, @Param("GoodPrice") String GoodPrice);

    @Update("update goods set goods_category=#{GoodsCategory} where id=#{GoodsID}")
    public boolean UpdateGoodsCategory(@Param("GoodsID") int GoodsID, @Param("GoodsCategory") String GoodsCategory);

    @Insert("insert into goods(goods_name, goods_describe,good_price,goods_category,goods_imgurl) values(#{goods_name}, #{goods_describe}, #{good_price}, #{goods_category}, #{goods_imgurl})")
    public boolean addGoods(@Param("goods_name") String goods_name, @Param("goods_describe") String goods_describe, @Param("good_price") String good_price, @Param("goods_category") String goods_category, @Param("goods_imgurl") String goods_imgurl);

    @Select("select * from goods")
    public List<GoodEntity> getGoods();

    @Select("select * from goods order by good_price desc")
    public List<GoodEntity> selectGoodDescend();

    @Select("select * from goods order by good_price asc")
    public List<GoodEntity> selectGoodAscend();

    @Select("select * from goods where goods_category=#{goods_category}")
    public List<GoodEntity> selectGoodByCate(@Param("goods_category") String goods_category);

    @Select("SELECT * from goods where id=#{id}")
    public GoodEntity selectGoodInoById(@Param("id") int id);

    @Select("select * from goods where goods_name like CONCAT('%', #{goods_name}, '%')")
    public List<GoodEntity> selectGoodByName(@Param("goods_name") String goods_name);

    @Select("SELECT count(*) from shopping_cart where user_id=#{userId} and cart_goodid=#{GoodsID} and order_id is null")
    public int selectCartRecord(@Param("userId") int userId, @Param("GoodsID") int GoodsID);//查询当前用户是否已经将这个商品加入购物车

    @Insert("insert into shopping_cart (user_id,cart_goodid) values(#{userId},#{GoodsID})")
    public boolean insertShopCart(@Param("userId") int userId, @Param("GoodsID") int GoodsID);//给购物车新建一条记录

    @Update("update shopping_cart set cart_goodnum=cart_goodnum+1 where user_id=#{userId} and cart_goodid=#{GoodsID}")
    public boolean CartGoodIncrement(@Param("userId") int userId, @Param("GoodsID") int GoodsID);

}