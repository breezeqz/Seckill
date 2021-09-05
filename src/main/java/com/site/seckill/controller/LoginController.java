package com.site.seckill.controller;

import com.site.seckill.common.Const;
import com.site.seckill.entity.User;
import com.site.seckill.param.LoginParam;
import com.site.seckill.redis.RedisService;
import com.site.seckill.redis.UserKey;
import com.site.seckill.result.Result;
import com.site.seckill.service.UserService;
import com.site.seckill.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    //查询数据库中是否有数据，查询成功就把sessionId写入cookie中，并持久化，把sessionId作为键，用户信息作为值存到redis中，然后返回查询结果状态
    public Result<User> doLogin(HttpServletResponse response, HttpSession session , @Valid LoginParam loginParam) {
        Result<User> login = userService.login(loginParam);
        if (login.isSuccess()){
            //登录成功就把SessionId存入到cookie中，并持久化到硬盘上，所有浏览器共享
            CookieUtil.writeLoginToken(response,session.getId());
            //将sessionid作为键，用户信息作为值存放进redis中，并设置过期时间，30分钟后过期
            redisService.set(UserKey.getByName , session.getId() ,login.getData(), Const.RedisCacheExtime.REDIS_SESSION_EXTIME );
        }
        return login;
    }

    @RequestMapping("/logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readLoginToken(request);
        //删除持久化的cookie
        CookieUtil.delLoginToken(request , response);
        //删除redis中的key
        redisService.del(UserKey.getByName , token);
        return "login";
    }
}
