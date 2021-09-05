package com.site.seckill.common;

public class Const {

    public interface RedisCacheExtime{
        //键过期时间
        int REDIS_SESSION_EXTIME = 60 * 30;//30分种
        int GOODS_LIST = 60 * 30 * 24;//12小时
        int GOODS_ID = 60;//1分钟
        int SECKILL_PATH = 60;//1分钟
        int GOODS_INFO = 60;//1分钟
    }
}
