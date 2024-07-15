package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Mapper.GoodsMapper;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Mapper.ShoppingCartMapper;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.asm.Type.getType;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public void orderList(@RequestBody Map<String, Object> userInfo, HttpServletResponse response) throws IOException {
        String userName = (String) userInfo.get("userName");
        int userID = userMapper.selectUserId(userName);
        List<OrderEntity> orderList = orderMapper.selectByUserId(userID);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(orderList.toString());
    }

    @RequestMapping(value = "/showOrder", method = RequestMethod.POST)
    public void showOrder(@RequestBody Map<String, Object> Order, HttpServletResponse response) throws IOException {
        String orderId = (String) Order.get("orderId");
        OrderEntity order = orderMapper.selectByOrderId(orderId);
        UserEntity user = userMapper.selectUserInfoById(order.getUserId());
        List<OrderInfoEntity> goodsIds = orderMapper.selectGoodsByOrderId(orderId);
        List<GoodEntity> goodsList = new ArrayList<>();
        for (OrderInfoEntity goodsId : goodsIds) {
            GoodEntity good = goodsMapper.selectGoodInoById(goodsId.getOrder_goodId());
            goodsList.add(good);
        }
        String OrderMap = getMapsAsJson(order, user, goodsList, goodsIds);

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

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public void addOrder(@RequestBody Map<String, Object> Order, HttpServletResponse response) throws IOException {

        boolean isSuccess = false;
        String userName = (String) Order.get("userName");
        List<Integer> cartsid = (List<Integer>) Order.get("cartsid");
        double total_price = ((Number) Order.get("total_price")).doubleValue(); // 确保total_price是double类型
        System.out.println(userName);
        System.out.println(cartsid);
        System.out.println(total_price);
        int userId = userMapper.selectUserId(userName);
        List<Integer> goodsNum = new ArrayList<>();
        List<Integer> goodsIds = new ArrayList<>();
        for (int cartId : cartsid) {
            goodsNum.add(shoppingCartMapper.getgoodnumber(cartId));
            goodsIds.add(shoppingCartMapper.getgoodid(cartId));
        }
        //生成18位订单号 UUID
        String orderId = UUID.randomUUID().toString().replace("-", "");
        orderId = orderId.substring(0, 18);
        if (!orderMapper.insertOrder(orderId, userId, "已支付", total_price)) {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("false");
            return;
        }
        for (int i = 0; i < goodsNum.size(); i++) {
            if (!orderMapper.insertOrderGood(orderId, goodsIds.get(i), goodsNum.get(i))) {
                response.setContentType("text/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("false");
                return;
            }
        }
        for (int cartId : cartsid) {
            if (!shoppingCartMapper.ChangeCartStatus(cartId, orderId)) {
                response.setContentType("text/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("false");
                return;
            }
        }
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("success");
    }
}
