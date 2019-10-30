package com.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;//描述
    private String avatarUrl;//github上avatar_url,fastjson自动把下划线来定义的属性自动映射为驼峰
}
