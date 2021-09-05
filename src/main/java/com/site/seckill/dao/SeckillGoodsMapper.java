package com.site.seckill.dao;

import com.site.seckill.entity.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillGoodsMapper {
    //根据id删除秒杀商品
    int deleteByPrimaryKey(Long id);
    //增加秒杀商品
    int insert(SeckillGoods record);
    //选择性的增加秒杀商品
    int insertSelective(SeckillGoods record);
    //根据id查询秒杀商品
    SeckillGoods selectByPrimaryKey(Long id);
    //根据id选择性的更新秒杀商品
    int updateByPrimaryKeySelective(SeckillGoods record);
    //根据id更新秒杀商品的全部信息
    int updateByPrimaryKey(SeckillGoods record);
}