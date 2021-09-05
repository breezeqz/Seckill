package com.site.seckill.dao;

import com.site.seckill.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdeInfoMapper {
    //根据id删除订单
    int deleteByPrimaryKey(Long id);
    //增加订单
    int insert(OrderInfo record);
    //选择性的增加订单
    int insertSelective(OrderInfo record);
    //根据id获取订单
    OrderInfo selectByPrimaryKey(Long id);
    //根据id选择性的更新订单信息
    int updateByPrimaryKeySelective(OrderInfo record);
    //根据订单更新全部订单信息
    int updateByPrimaryKey(OrderInfo record);
}