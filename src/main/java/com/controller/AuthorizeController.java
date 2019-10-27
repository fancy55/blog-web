package com.controller;

import com.dto.AccessTokenDTO;
import com.dto.GithubUser;
import com.mapper.UserMapper;
import com.model.User;
import com.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller  //把当前类作为路由api的承载着
public class AuthorizeController {

    @Autowired //自动把spring容器里写好的实例化的实例加载到上下文
    private GithubProvider githubProvider;

    @Value("${github.client.id}") //配置文件 k-v
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){//参数接收,session是在request中拿到的
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setStatus(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("user:"+githubUser.getName());//在令牌中设置
        if (githubUser != null){
            //登陆成功，写cookie和session
            System.out.println("login ok");
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println(user.toString());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);//在银行中创建账户成功，但还没给前端银行卡
            return "redirect:/";//用前缀，就重定向；否则只是渲染一下
        }else{
            System.out.println("login err");
            return "redirect:/";
        }
        //return "index";//返回index页面
    }


}
