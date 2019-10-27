package com.provider;

import com.alibaba.fastjson.JSON;
import com.dto.AccessTokenDTO;
import com.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component //仅仅把当前类初始化到spring的上下文 不需要在main()中实例化  自动实例化 -->ioc
public class GithubProvider {
    //参数超过2个就封装成对象
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            //System.out.println("token:"+token);
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            //System.out.println(accessToken);
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//把json string自动解析为java类对象
            //System.out.println(string+"\n"+"githubUser:"+githubUser);
            return githubUser;
        }catch(IOException e){
        }
        return null;
    }
}
