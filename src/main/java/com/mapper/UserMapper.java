package com.mapper;

import com.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
//@Component(value="UserMapper");
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_creat,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")//mybatis:把user里面的属性自动填充到#{}中
    void insert(User user);
}
