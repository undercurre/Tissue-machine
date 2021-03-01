package com.platform.modules.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdOrderAccountVo {
    /**
     * 销售总额
     */
    private BigDecimal saleSum;
    /**
     * 有效订单总数
     */
    private Integer validOrderNumber;
    /**
     * 无效订单总数
     */
    private Integer invalidOrderNumber;
    /**
     * 有效订单总额
     */
    private BigDecimal validOrderSum;
    /**
     * 无效订单总额
     */
    private BigDecimal invalidOrderSum;
    /**
     * 已完成订单总额
     */
    private BigDecimal finishOrderSum;
    /**
     * 已完成订单数目
     */
    private BigDecimal finishOrderNumber;
}
