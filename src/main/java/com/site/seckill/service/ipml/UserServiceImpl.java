package com.site.seckill.service.ipml;

import com.site.seckill.entity.User;
import com.site.seckill.dao.UserMapper;
import com.site.seckill.param.LoginParam;
import com.site.seckill.result.CodeMsg;
import com.site.seckill.result.Result;
import com.site.seckill.service.UserService;
import com.site.seckill.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result<User> login(LoginParam loginParam) {

        User user = userMapper.checkPhone(loginParam.getMobile());
        if(user == null){
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPwd= user.getPassword();//获取数据库密码
        String saltDB = user.getSalt();//用于拼接密码后在加密
        String calcPass = MD5Util.formPassToDBPass(loginParam.getPassword(), saltDB);//输入的密码
        if(!StringUtils.equals(dbPwd , calcPass)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        user.setPassword(StringUtils.EMPTY);//去除密码
        return Result.success(user);
    }
}
