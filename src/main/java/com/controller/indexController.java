package com.controller;

import com.dto.LeaveDTO;
import com.mapper.LeaveMapper;
import com.mapper.UserMapper;
import com.model.Leave;
import com.model.User;
import com.service.leaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private leaveService leaveService;

    @GetMapping("/")//访问首页，循环去看cookie
    public String index(HttpServletRequest request, Model model){//持久化登录
        Cookie[] cookies = request.getCookies();//请求刚设的cookie
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {//持久化登录
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //model属性可把后端数据写到前端
        List<LeaveDTO> leaves = leaveService.getLeave();//leavemapper是针对表leave,这时就用到service
        model.addAttribute("leaves",leaves);
        return "index";
    }
}
