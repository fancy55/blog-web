package com.dto;

import com.model.User;
import lombok.Data;

@Data
public class LeaveDTO {
    //传输层 ，基本属性和leave一样，但多了一个user对象
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

    private User user;
}
