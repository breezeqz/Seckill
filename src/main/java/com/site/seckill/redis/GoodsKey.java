package com.site.seckill.redis;

public class GoodsKey extends BasePrefix{

	private GoodsKey(String prefix) {
		super(prefix);
	}
	//缓存商品列表页的组成键
	public static GoodsKey getGoodsList = new GoodsKey("gl");
	//缓存商品详情页的组成键
	public static GoodsKey getGoodsDetail = new GoodsKey("gd");
	//缓存商品库存的组成键
	public static GoodsKey getSeckillGoodsStock= new GoodsKey( "gs");
}
