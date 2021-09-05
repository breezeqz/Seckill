package com.site.seckill.service;

import com.site.seckill.bo.GoodsBo;
import org.springframework.stereotype.Service;
import java.util.List;

public interface SeckillGoodsService {
    //获取所有商品业务对象（商品和秒杀商品的左连接）
    List<GoodsBo> getSeckillGoodsList();
    //根据id获取商品业务对象
    GoodsBo getseckillGoodsBoByGoodsId(long goodsId);
    //减少秒杀商品库存
    int reduceStock(long goodsId);
}
