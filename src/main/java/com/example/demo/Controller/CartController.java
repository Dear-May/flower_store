package com.example.demo.Controller;

import com.example.demo.Entity.GoodEntity;
import com.example.demo.Entity.ShoppingCartEntity;
import com.example.demo.Mapper.GoodsMapper;
import com.example.demo.Mapper.ShoppingCartMapper;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;


    @ResponseBody
    @RequestMapping(value = "/getcartgood", method = RequestMethod.POST)//获取当前购物车所有商品
    public void getCartgood(@RequestBody Map<String, Object> userInfo, HttpServletResponse response) throws IOException {
        String userName = (String) userInfo.get("userName");
        int userId = userMapper.selectUserId(userName);
        List<ShoppingCartEntity> cartList = shoppingCartMapper.getCartList(userId);
        List<Integer> goodsIds = new ArrayList<>();
        for(ShoppingCartEntity cart:cartList){
            goodsIds.add(cart.getCart_goodid());
        }
        List<GoodEntity> goodInfos = new ArrayList<>();
        for(int id:goodsIds){
            GoodEntity goodentity = goodsMapper.selectGoodInoById(id);
            goodInfos.add(goodentity);
        }

        List<Map<String, Object>> cartListMap = getMaps(cartList, goodInfos);

        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(cartListMap.toString());

    }

    private static List<Map<String, Object>> getMaps(List<ShoppingCartEntity> cartList, List<GoodEntity> goodInfos) {
        List<Map<String, Object>> cartListMap = new ArrayList<>();
        for(int i = 0; i< cartList.size(); i++){
            Map<String, Object> cartMap = new HashMap<>();
            cartMap.put("cart_goodid", cartList.get(i).getCart_goodid());
            cartMap.put("cart_goodname", goodInfos.get(i).getGoods_name());
            cartMap.put("cart_goodprice", goodInfos.get(i).getGood_price());
            cartMap.put("cart_goodimage", "\""+goodInfos.get(i).getGoods_imgurl()+"\"");
            cartMap.put("cart_goodnumber", cartList.get(i).getCart_goodnum());
            cartListMap.add(cartMap);
        }
        return cartListMap;
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

    @ResponseBody
    @RequestMapping(value = "/goodsRemove", method = RequestMethod.POST)//添加商品到购物车，如果购物车没有这条记录就insert 有这一条记录就将这条记录的number+1
    public String goodsRemove(@RequestParam(value = "GoodsId", required = false) int GoodsID,@RequestParam(value = "userId", required = false) int userId) {
        if(shoppingCartMapper.CartDelete(userId,GoodsID))
            {
                return "1";

            }
            else {
                return "-1";
            }
        }

}
