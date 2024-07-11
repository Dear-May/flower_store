package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Mapper.GoodsMapper;

@Controller
@RequestMapping("/Goods")
public class GoodsController {
    @Autowired
    GoodsMapper goodsMapper;

    @ResponseBody
    @RequestMapping(value = "/deleteGood", method = RequestMethod.POST)//下架商品
    public String deleteGood(@RequestBody int GoodsID) {
//        System.out.println(userMapper.DeleteGoods(GoodsID));
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
    public String updateGoodName(@RequestBody int GoodsID,@RequestBody String GoodsName) {
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
    public String updateGoodDes(@RequestBody int GoodsID,@RequestBody String GoodsDescribe) {

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
    public String updateGoodPrice(@RequestBody int GoodsID,@RequestBody String GoodPrice) {
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
    public String updateGoodsCategory(@RequestBody int GoodsID,@RequestBody String GoodsCategory) {
        if(goodsMapper.UpdateGoodsCategory(GoodsID,GoodsCategory))
        {
            return "1";//修改
        }
        else {
            return "-1";//修改
        }
    }


}

