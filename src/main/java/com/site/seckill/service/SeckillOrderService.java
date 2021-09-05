package com.site.seckill.service;

import com.site.seckill.bo.GoodsBo;
import com.site.seckill.entity.OrderInfo;
import com.site.seckill.entity.SeckillOrder;
import com.site.seckill.entity.User;

public interface SeckillOrderService {
    //根据用户id和商品id获取秒杀订单
    SeckillOrder getSeckillOrderByUserIdGoodsId(long userId , long goodsId);
    //扣秒杀商品库存，添加订单和秒杀订单
    OrderInfo insert(User user , GoodsBo goodsBo);
    //根据秒杀订单信息获取订单信息
    OrderInfo getOrderInfo(long orderId);
    //根据用户id和商品id获取订单id
    long getSeckillResult(Long userId, long goodsId);

    boolean checkPath(User user, long goodsId, String path);

    String createMiaoshaPath(User user, long goodsId);

}
