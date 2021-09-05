package com.site.seckill.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskUtil {
    //cron表达式有6位或7位组成，最后1位可以省略，从左到右分别是秒，分，时，天，月，星期，年（可选）
    @Scheduled(cron = "59 59 23 * * 5")//cron 表达式，每周五 23:59:59 执行
    public void doTask(){
        System.out.println("我是定时任务");

    }
}
