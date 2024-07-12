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
    @RequestMapping(value = "/getCartgood", method = RequestMethod.GET)//获取当前种类所有商品
    public Map<GoodEntityGet,Integer> getCartgood(@RequestParam(value = "GoodsId", required = false) int GoodsID, @RequestParam(value = "userId", required = false) int userId) {
        Map<GoodEntityGet,Integer> map =new HashMap<>();
        List<GoodEntityGet> list=shoppingCartMapper.findPersonalGoodInCart(userId,GoodsID);
        for(int i=0;i<list.size();i++)
        {
            int num=shoppingCartMapper.getgoodnumber(userId,list.get(i).getId());
            map.put(list.get(i),num);
        }
        System.out.println(list);
        System.out.println(map);
        return map;
    }
}
