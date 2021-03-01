/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单纸巾表实体
 *
 * @author zqh
 * @date 2020-09-22 16:27:18
 */
@Data
@TableName("AD_ORDER_TISSUE")
public class AdOrderTissueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 机柜详情库存ID
     */
    private String stockId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private Integer goodsCount;
    /**
     * 出货数量
     */
    private Integer shipmentNumber;
    /**
     * 出货状态 0-初始化 1-出货成功 2-出货失败
     */
    private Integer status;
    /**
     * 出货时间
     */
    private Date sellTime;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 顺序
     */
    private Integer sortLevel;
}
