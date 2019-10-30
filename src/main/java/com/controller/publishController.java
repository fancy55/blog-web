package com.controller;

import com.mapper.LeaveMapper;
import com.mapper.UserMapper;
import com.model.Leave;
import com.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@Param("title")String title, @Param("description")String description, @Param("tag")String tag,
                            HttpServletRequest request, Model model){
        User user = null;
        Cookie[] cookies = request.getCookies();//请求刚设的cookie
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {//持久化登录
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Leave leave = new Leave();
        leave.setTitle(title);
        leave.setDescription(description);
        leave.setTag(tag);
        leave.setCreator(user.getId());
        leave.setGmtCreate(System.currentTimeMillis());
        leave.setGmtModified(leave.getGmtCreate());
        leaveMapper.insert(leave);
        return "redirect:/";
    }
}
