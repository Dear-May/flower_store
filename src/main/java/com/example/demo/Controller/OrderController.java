package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Mapper.GoodsMapper;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @RequestMapping(value="/orderList", method = RequestMethod.POST)
    public void orderList(@RequestBody Map<String, Object> userInfo, HttpServletResponse response) throws IOException {
        String userName = (String) userInfo.get("userName");
        int userID = userMapper.selectUserId(userName);
        List<OrderEntity> orderList = orderMapper.selectByUserId(userID);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(orderList.toString());
    }

    @RequestMapping(value="/showOrder", method = RequestMethod.POST)
    public void showOrder(@RequestBody Map<String, Object> Order, HttpServletResponse response) throws IOException {
        int orderId = (int) Order.get("orderId");
        OrderEntity order = orderMapper.selectByOrderId(orderId);
        UserEntity user = userMapper.selectUserInfoById(order.getUserId());
        List<OrderInfoEntity> goodsIds = orderMapper.selectGoodsByOrderId(orderId);
        List<GoodEntity> goodsList = new ArrayList<>();
        for (OrderInfoEntity goodsId : goodsIds) {
            GoodEntity good = goodsMapper.selectGoodInoById(goodsId.getOrder_goodId());
            goodsList.add(good);
        }
        String OrderMap = getMapsAsJson(order, user, goodsList,goodsIds);

        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(OrderMap);
    }

    private static String getMapsAsJson(OrderEntity order, UserEntity user, List<GoodEntity> goodsList, List<OrderInfoEntity> goodsIds) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{");

        // 添加订单信息
        jsonBuilder.append("\"orderId\":").append(order.getId()).append(",");
        jsonBuilder.append("\"orderTime\":\"").append(order.getOrderTime()).append("\",");
        jsonBuilder.append("\"totalPrice\":").append(order.getOrderPrice()).append(",");
        jsonBuilder.append("\"userPhone\":\"").append(user.getUserPhone()).append("\",");

        // 添加商品列表
        jsonBuilder.append("\"goodsList\":[");
        for (int i = 0; i < goodsList.size(); i++) {
            GoodEntity good = goodsList.get(i);
            OrderInfoEntity orderInfo = goodsIds.get(i);

            jsonBuilder.append("{");
            jsonBuilder.append("\"goodsName\":\"").append(good.getGoods_name()).append("\",");
            jsonBuilder.append("\"goodsPrice\":").append(good.getGood_price()).append(",");
            jsonBuilder.append("\"goodsImgUrl\":\"").append(good.getGoods_imgurl()).append("\",");
            jsonBuilder.append("\"goodsNum\":").append(orderInfo.getOrder_goodnum());
            jsonBuilder.append("}");

            if (i < goodsList.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

}
