package com.example.demo.controller;

import cn.hutool.json.JSONUtil;
import com.example.demo.entity.UserTestEntity;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ResponseBody
    public String queryAllUser() {
        List<UserTestEntity> userEntities = userService.queryAllUser();
        return JSONUtil.toJsonStr(userEntities);
    }
}
