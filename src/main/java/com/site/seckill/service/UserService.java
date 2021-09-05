package com.site.seckill.service;

import com.site.seckill.entity.User;
import com.site.seckill.param.LoginParam;
import com.site.seckill.result.Result;

public interface UserService {
    Result<User> login(LoginParam loginParam);
}
