package com.example.demo.Controller;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/successLogin")
    public String successLogin() {
        return "successLogin";
    }

    @RequestMapping("/successRegister")
    public String successRegister() {
        return "successRegister";
    }

    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(@RequestBody UserEntity user) {
        System.out.println(user.getUserName());
        String result = userMapper.selectUserName(user.getUserName());
        System.out.println(result);
        if (result == null) {
            return "0";
        }
        return "1";
    }


    @ResponseBody
    @RequestMapping(value = "/selectUserName", method = RequestMethod.POST)
    public String selectUserName(@RequestBody UserEntity user) {
        String userName = user.getUserName();
        String userPassword = user.getUserPassword();
        if(userName.isEmpty() || userPassword.isEmpty())
        {
            return "-1";
        }
        System.out.println(userName + userPassword);

        String result = "-1";

        //将输入的密码加密
        String passwordMD5 = passwordMD5(userName, userPassword);

        //用户不存在
        if (userMapper.selectUserName(userName) == null) {
//            return "用户不存在";
            result = "0";
            return result;
            //用户存在，但密码输入错误
        } else if (!userMapper.selectUserPassword(userName).equals(passwordMD5)) {
            result = "1";
            return result;
//            return "账号或密码输入错误";
        } else if (userMapper.selectUserPassword(userName).equals(passwordMD5)) {
            result = "2";
//            return "成功登录";
            return result;
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody UserEntity user) {
        String userName = user.getUserName();
        String userPassword = user.getUserPassword();
        try
        {
            String selectRes = userMapper.selectUserName(user.getUserName());
            System.out.println(selectRes);
            if(selectRes!=null)//说明数据库有重复的用户名了
            {
                return "2";
            }
            System.out.println(userName + "***" + userPassword);
            String passwordMD5 = passwordMD5(userName, userPassword);
            userMapper.addUser(userName, passwordMD5);
            return "1";
        }catch (Exception e)
        {
            return "-1";
        }

    }

    public String passwordMD5(String userName, String userPassword) {
        // 需要加密的字符串
        String src = userName + userPassword;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
