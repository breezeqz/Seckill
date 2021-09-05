package com.site.seckill.dao;

import com.site.seckill.bo.GoodsBo;
import com.site.seckill.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GoodsMapper {
    //根据商品id删除商品
    int deleteByPrimaryKey(Long id);
    //增加商品
    int insert(Goods record);
    //选择性的增加商品
    int insertSelective(Goods record);
    //根据id查询商品
    Goods selectByPrimaryKey(Long id);
    //有选择的修改商品
    int updateByPrimaryKeySelective(Goods record);
    //根据id更新商品全部信息（包括详情）
    int updateByPrimaryKeyWithBLOBs(Goods record);
    //根据商品id修改除商品详情外的商品信息
    int updateByPrimaryKey(Goods record);
    //获取所有商品，将seckill_goods表根据商品id连接到goods表上
    List<GoodsBo> selectAllGoodes ();
    //根据商品id获取商品业务对象（包含秒杀信息）
    GoodsBo getseckillGoodsBoByGoodsId(long goodsId);
    //减少秒杀商品库存
    int updateStock(long goodsId);
}