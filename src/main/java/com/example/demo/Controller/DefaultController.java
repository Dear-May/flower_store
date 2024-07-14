package com.example.demo.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {
    @RequestMapping({"/", "/index"})
    public String login() {
       return "/index";

    }
    @RequestMapping({"/carttest"})
    public String toCart(){
        return "/carts";
    }
}
