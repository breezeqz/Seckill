package com.site.seckill.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
	//每second秒只能请求maxCount次
	int seconds();//时间
	int maxCount();//最大请求次数
	boolean needLogin() default true;//是否需要登录
}
