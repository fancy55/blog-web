package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class testController {

    @GetMapping("/greet")//mapping
    public String greet(@RequestParam(name="name",required=false,defaultValue = "World")String name, Model model){//请求参数
        model.addAttribute("name",name);//k-v,把浏览器传的值放到model里面
        return "greeting";//去templates中找greeting.html
    }
}
