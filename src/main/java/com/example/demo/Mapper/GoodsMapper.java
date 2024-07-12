package com.example.demo.Mapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GoodsMapper {

    @Update("update goods set GoodsState='已下架' where id=#{GoodsID}")
    public boolean DeleteGoods(@Param("GoodsID")int GoodsID);

    @Update("update goods set goods_name=#{GoodsName} where id=#{GoodsID}")
    public boolean UpdateGoodName(@Param("GoodsID")int GoodsID,@Param("GoodsName")String GoodsName);

    @Update("update goods set goods_describe=#{GoodsDescribe} where id=#{GoodsID}")
    public boolean UpdateGoodDes(@Param("GoodsID")int GoodsID,@Param("GoodsDescribe")String GoodsDescribe);

    @Update("update goods set good_price=#{GoodPrice} where id=#{GoodsID}")
    public boolean UpdateGoodPrice(@Param("GoodsID")int GoodsID,@Param("GoodPrice")String GoodPrice);

    @Update("update goods set goods_category=#{GoodsCategory} where id=#{GoodsID}")
    public boolean UpdateGoodsCategory(@Param("GoodsID")int GoodsID,@Param("GoodsCategory")String GoodsCategory);
}