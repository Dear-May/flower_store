package com.example.demo.Controller;
import com.example.demo.Entity.GoodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Mapper.GoodsMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsMapper goodsMapper;

    @RequestMapping("/goodslist")
    public String goodsList() {
        return "shop";
    }//返回GoodsList页面，然后在GoodList页面启动时，到getGoods控制器查询数据，并将数据返回


    @RequestMapping("/goodinfo")
    public String GoodInfo(@RequestParam(value = "GoodsId", required = false) int GoodsID, Model model) {
        System.out.println(GoodsID);
        model.addAttribute("GoodsId",GoodsID);
        return "good";
    }//返回GoodInfo页面 并传递一个当前商品的id，页面在加载的时候通过这个id到数据库搜索全部的商品信息

    @ResponseBody
    @RequestMapping(value = "/deleteGood", method = RequestMethod.POST)//下架商品
    public String deleteGood(@RequestParam(value = "GoodsId", required = false) int GoodsID) {
        System.out.println("id"+GoodsID);
        if(goodsMapper.DeleteGoods(GoodsID))
        {
            return "1";//删除成功
        }
        else {
            return "-1";//删除失败
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoodName", method = RequestMethod.POST)//修改商品名称
    public String updateGoodName(@RequestParam(value = "GoodsId", required = false) int GoodsID,
                                 @RequestParam(value = "GoodsName", required = false)  String GoodsName)
    {
        if(goodsMapper.UpdateGoodName(GoodsID,GoodsName))
        {
            return "1";//修改
        }
        else {
            return "-1";//修改
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoodDes", method = RequestMethod.POST)//修改商品描述
    public String updateGoodDes(@RequestParam(value = "GoodsId", required = false) int GoodsID,
                                @RequestParam(value = "GoodsDescribe", required = false)  String GoodsDescribe) {

        if(goodsMapper.UpdateGoodDes(GoodsID,GoodsDescribe))
        {
            return "1";//修改
        }
        else {
            return "-1";//修改
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoodPrice", method = RequestMethod.POST)//修改商品价格
    public String updateGoodPrice(@RequestParam(value = "GoodsId", required = false) int GoodsID,
                                  @RequestParam(value = "GoodPrice", required = false)  String GoodPrice) {
        if(goodsMapper.UpdateGoodPrice(GoodsID,GoodPrice))
        {
            return "1";//修改
        }
        else {
            return "-1";//修改
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoodsCategory", method = RequestMethod.POST)//修改商品种类
    public String updateGoodsCategory(@RequestParam(value = "GoodsId", required = false) int GoodsID,
                                      @RequestParam(value = "GoodsCategory", required = false)String GoodsCategory) {
        if(goodsMapper.UpdateGoodsCategory(GoodsID,GoodsCategory))
        {
            return "1";//修改
        }
        else {
            return "-1";//修改
        }
    }



//缺少一个函数 获取前端上传的图片，将图片上传至服务器 并返回一个图片链接
    public String uploadImg(MultipartFile file)
    {
        String res="";
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)//添加商品
    public String addGoods(@RequestBody GoodEntity good) {
        System.out.println("good"+good);
        String goodsName=good.getGoods_name();
        String goodsDes=good.getGoods_describe();
        String goodPrice=good.getGood_price();
        String goodCate=good.getGoods_category();
        MultipartFile ImgFile=good.getGoods_imgFile();
        if(goodsName==null||goodsDes==null||goodPrice==null||goodCate==null||ImgFile==null)
        {
            return "-1";//提示不能为空
        }
        else{
            String goodImgUrl=uploadImg(ImgFile);//上传到服务器并获取图片
            boolean flag=goodsMapper.addGoods(goodsName,goodsDes,goodPrice,goodCate,goodImgUrl);
            if(flag)
            {
                return "1";//提示插入成功
            }
            else
            {
                return "-2";//提示插入失败
            }
        }

    }

    @ResponseBody
    @RequestMapping(value = "/getGoods", method = RequestMethod.GET)//获取所有商品
    public List<GoodEntity> getGoods() {
        List<GoodEntity>  goodlist=goodsMapper.selectGood();
        System.out.println(goodlist);
        System.out.println(goodlist);
        return goodlist;
    }

    @ResponseBody
    @RequestMapping(value = "/getGoodsByCate", method = RequestMethod.GET)//获取当前种类所有商品
    public List<GoodEntity> getGoodsByCate( @RequestParam(value = "GoodsCategory", required = false)String GoodsCategory) {
        List<GoodEntity>  goodlist=goodsMapper.selectGoodByCate(GoodsCategory);
        System.out.println(goodlist);
        System.out.println(goodlist);
        return goodlist;
    }

    @ResponseBody
    @RequestMapping(value = "/getGoodInfo", method = RequestMethod.POST)//获取商品详细信息
    public GoodEntity getGoodInfo(@RequestParam(value = "GoodsId", required = false) int GoodsID) {
        GoodEntity good=goodsMapper.selectGoodInoById(GoodsID);
        System.out.println(good);
        return good;
    }

    //将商品添加到购物车
    @ResponseBody
    @RequestMapping(value = "/AddGoodsIntoShoppingCart", method = RequestMethod.POST)//添加商品到购物车，如果购物车没有这条记录就insert 有这一条记录就将这条记录的number+1
    public String AddGoodsIntoShoppingCart(@RequestParam(value = "GoodsId", required = false) int GoodsID,@RequestParam(value = "userId", required = false) int userId) {
       int record=goodsMapper.selectCartRecord(GoodsID,userId);
        System.out.println(record);
       if(record==0)//说明需要添加一条记录
       {
            if(goodsMapper.insertShopCart(GoodsID,userId))
            {
                return "1";//添加成功
            }
            else {
                return "-1";//添加失败
            }
       }
       else {//说明要将这条记录的num+1
           if(goodsMapper.CartGoodIncrement(userId,GoodsID))
           {
               return "1";//添加成功
           }
           else {
               return "-1";//添加失败
           }
       }

//        return "1";
    }
}

