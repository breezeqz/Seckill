package com.site.seckill.dao;

import com.site.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    //根据电话号码和密码查询用户
    User selectByPhoneAndPassword(@Param("phone") String phone , @Param("password") String password);
    //根据电话号码查询用户
    //@Param用于绑定xml中的值
    User checkPhone(@Param("phone") String phone );
}
