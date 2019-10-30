package com.model;

import lombok.Data;

@Data //减少很多重复代码的书写
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
