package com.example.demo.Mapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GoodsMapper {

    @Update("update goods set GoodsState='已下架' where GoodsID=#{GoodsID}")
    public boolean DeleteGoods(@Param("GoodsID")int GoodsID);

    @Update("update goods set GoodsName=#{GoodsName} where GoodsID=#{GoodsID}")
    public boolean UpdateGoodName(@Param("GoodsID")int GoodsID,@Param("GoodsName")String GoodsName);

    @Update("update goods set GoodsDescribe=#{GoodsDescribe} where GoodsID=#{GoodsID}")
    public boolean UpdateGoodDes(@Param("GoodsID")int GoodsID,@Param("GoodsDescribe")String GoodsDescribe);

    @Update("update goods set GoodPrice=#{GoodPrice} where GoodsID=#{GoodsID}")
    public boolean UpdateGoodPrice(@Param("GoodsID")int GoodsID,@Param("GoodPrice")String GoodPrice);

    @Update("update goods set GoodsCategory=#{GoodsCategory} where GoodsID=#{GoodsID}")
    public boolean UpdateGoodsCategory(@Param("GoodsID")int GoodsID,@Param("GoodsCategory")String GoodsCategory);
}