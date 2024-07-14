package com.example.demo.Controller;

import cn.hutool.json.ObjectMapper;
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

        String cartListMap = getMaps(cartList, goodInfos);

        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(cartListMap);

    }

    private static String getMaps(List<ShoppingCartEntity> cartList, List<GoodEntity> goodInfos) {
        List<Map<String, Object>> cartListMap = new ArrayList<>();
        for(int i = 0; i< cartList.size(); i++){
            Map<String, Object> cartMap = new HashMap<>();
            cartMap.put("cart_goodid", cartList.get(i).getCart_goodid());
            cartMap.put("cart_goodname", goodInfos.get(i).getGoods_name());
            cartMap.put("cart_goodprice", goodInfos.get(i).getGood_price());
            cartMap.put("cart_goodimage", goodInfos.get(i).getGoods_imgurl());
            cartMap.put("cart_goodnumber", cartList.get(i).getCart_goodnum());
            cartListMap.add(cartMap);
        }
        //转化为json字符串
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < cartListMap.size(); i++) {
            Map<String, Object> cartMap = cartListMap.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"cart_goodid\":").append(cartMap.get("cart_goodid")).append(",");
            jsonBuilder.append("\"cart_goodname\":\"").append(cartMap.get("cart_goodname")).append("\",");
            jsonBuilder.append("\"cart_goodprice\":").append(cartMap.get("cart_goodprice")).append(",");
            jsonBuilder.append("\"cart_goodimage\":\"").append(cartMap.get("cart_goodimage")).append("\",");
            jsonBuilder.append("\"cart_goodnumber\":").append(cartMap.get("cart_goodnumber"));
            jsonBuilder.append("}");
            if (i < cartListMap.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        String jsonStr = jsonBuilder.toString();

        return jsonStr;
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
