package com.site.seckill.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private BigDecimal goodsPrice;
    //库存
    private Integer goodsStock;

    private Date createDate;

    private Date updateDate;

    private String goodsDetail;
}