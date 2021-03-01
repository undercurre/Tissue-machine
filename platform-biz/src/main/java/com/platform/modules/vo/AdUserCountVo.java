package com.platform.modules.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdUserCountVo {

    /**
     * 注册会员总数
     */
    private Integer registerUserNumber;
    /**
     * 有订单的会员数
     */
    private Integer haveOrderUserNumber;
    /**
     * 会员订单总数
     */
    private Integer orderNumber;
    /**
     * 会员购物总额
     */
    private BigDecimal orderAmount;
    /**
     * 会员购买率
     */
    private Double userBuyRate;
    /**
     * 每会员订单数
     */
    private Double orderNumberPerPerson;
    /**
     * 每会员购物额
     */
    private BigDecimal orderAmountPerPerson;

}
