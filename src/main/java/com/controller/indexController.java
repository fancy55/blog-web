package com.controller;

import com.mapper.UserMapper;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")//访问首页，循环去看cookie
    public String index(HttpServletRequest request){//持久化登录
        Cookie[] cookies = request.getCookies();//请求刚设的cookie
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null){//持久化登录
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
