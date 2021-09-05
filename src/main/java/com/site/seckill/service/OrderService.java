package com.site.seckill.service;

import com.site.seckill.entity.OrderInfo;
import org.springframework.stereotype.Service;

public interface OrderService {
    //增加订单
    long addOrder(OrderInfo orderInfo);
    //根据id获取订单信息
    OrderInfo getOrderInfo(long rderId);
}
