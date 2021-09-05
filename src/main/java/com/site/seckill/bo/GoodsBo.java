package com.site.seckill.bo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
//BO：business object 业务对象,业务对象主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象
@Setter//setter方法
@Getter//getter方法
@ToString//toString方法
@NoArgsConstructor//无参构造方法
@AllArgsConstructor//有参构造方法
public class GoodsBo{
	//秒杀商品属性
	private BigDecimal seckillPrice;

	private Integer stockCount;

	private Date startDate;

	private Date endDate;

	//商品属性
	private Long id;

	private String goodsName;

	private String goodsTitle;

	private String goodsImg;

	private BigDecimal goodsPrice;

	private Integer goodsStock;

	private Date createDate;

	private Date updateDate;

	private String goodsDetail;

}
