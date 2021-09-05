package com.site.seckill.dao;

import com.site.seckill.entity.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SeckillOrderMapper {
    //根据id删除秒杀订单
    int deleteByPrimaryKey(Long id);
    //增加秒杀订单
    int insert(SeckillOrder record);
    //选择性的增加秒杀订单
    int insertSelective(SeckillOrder record);
    //根据id查询秒杀订单
    SeckillOrder selectByPrimaryKey(Long id);
    //根据id选择性的修改秒杀订单
    int updateByPrimaryKeySelective(SeckillOrder record);
    //根据id修改秒杀订单
    int updateByPrimaryKey(SeckillOrder record);
    //根据用户id和商品id查询秒杀订单
    SeckillOrder selectByUserIdAndGoodsId(@Param("userId") long userId , @Param("goodsId") long goodsId );
}