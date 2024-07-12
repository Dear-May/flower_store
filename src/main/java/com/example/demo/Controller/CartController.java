package com.example.demo.Controller;

import com.example.demo.Entity.GoodEntity;
import com.example.demo.Entity.GoodEntityGet;
import com.example.demo.Mapper.GoodsMapper;
import com.example.demo.Mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Cart")
public class CartController {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;


    @ResponseBody
    @RequestMapping(value = "/getCartgood", method = RequestMethod.GET)//获取当前购物车所有商品
    public Map<GoodEntityGet,Integer> getCartgood( @RequestParam(value = "userId", required = false) int userId) {
        Map<GoodEntityGet,Integer> map =new HashMap<>();
        List<GoodEntityGet> list=shoppingCartMapper.findPersonalGoodInCart(userId);
        for(int i=0;i<list.size();i++)
        {
            int num=shoppingCartMapper.getgoodnumber(userId,list.get(i).getId());
            map.put(list.get(i),num);
        }
        System.out.println(list);
        System.out.println(map);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/goodsIncreament", method = RequestMethod.POST)//添加商品到购物车，如果购物车没有这条记录就insert 有这一条记录就将这条记录的number+1
    public String goodsIncreament(@RequestParam(value = "GoodsId", required = false) int GoodsID,@RequestParam(value = "userId", required = false) int userId) {
        if(shoppingCartMapper.CartGoodIncrement(userId,GoodsID))
        {
            return "1";//添加成功
        }
        else{
            return "-1";//添加失败
        }
    }

    @ResponseBody
    @RequestMapping(value = "/goodsDecreament", method = RequestMethod.POST)//添加商品到购物车，如果购物车没有这条记录就insert 有这一条记录就将这条记录的number+1
    public String goodsDecreament(@RequestParam(value = "GoodsId", required = false) int GoodsID,@RequestParam(value = "userId", required = false) int userId) {
        if(shoppingCartMapper.getgoodnumber(userId,GoodsID)==1)//此时商品余量只剩下0，继续删除的话就会变成-1，因此将数据库的这一条记录删除
        {
            if(shoppingCartMapper.CartDelete(userId,GoodsID))
            {
                return "1";

            }
            else {
                return "-1";
            }



        }
        if(shoppingCartMapper.CartGoodDecrement(userId,GoodsID))
        {
            return "1";//减少成功
        }
        else{
            return "-1";//减少失败
        }
    }
}
