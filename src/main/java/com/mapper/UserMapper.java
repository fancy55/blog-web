package com.mapper;

import com.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_creat,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")//mybatis:把形参类user里面的属性自动填充到#{}中
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//不是类需要加注解

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);
}
