package com.example.demo.Controller;

import com.example.demo.Entity.OrderEntity;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value="/orderList", method = RequestMethod.POST)
    public void orderList(@RequestBody Map<String, Object> userInfo, HttpServletResponse response) throws IOException {
        String userName = (String) userInfo.get("userName");
        int userID = userMapper.selectUserId(userName);
        List<OrderEntity> orderList = orderMapper.selectByUserId(userID);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(orderList.toString());
    }
}
