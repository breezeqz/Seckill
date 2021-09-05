package com.site.seckill.vo;

import com.site.seckill.bo.GoodsBo;
import com.site.seckill.entity.OrderInfo;
import lombok.Getter;
import lombok.Setter;
//VO：value object 值对象 / view object 表现层对象
//1 ．主要对应页面显示（web页面/swt、swing界面）的数据对象。
//2 ．可以和表对应，也可以不，这根据业务的需要。
@Setter
@Getter
public class OrderDetailVo {
    private GoodsBo goods;
    private OrderInfo order;
}
