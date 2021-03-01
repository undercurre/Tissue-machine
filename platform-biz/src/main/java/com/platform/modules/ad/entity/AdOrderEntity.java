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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表实体
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
@Data
@TableName("AD_ORDER")
public class AdOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId
    private String id;
    /**
     * 用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序 6:QQ小程序
     */
    private Integer fromType;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 会员ID
     */
    private String userId;
    /**
     * 订单类型 1：普通订单  2：免费领取
     */
    private Integer orderType;
    /**
     * 订单状态 0未支付  1已支付 2退款中 3已退款  4已失效 用户取消
     */
    private Integer orderStatus;
    /**
     * 出货状态 0未申请出货 1 申请出货 2出货成功
     */
    private Integer shipmentStatus;
    /**
     * 支付类型 1余额支付 2微信支付
     */
    private Integer payType;
    /**
     * 订单总价格
     */
    private BigDecimal totalPrice;
    /**
     * 实际消费金额
     */
    private BigDecimal actualPrice;
    /**
     * 消费金
     */
    private BigDecimal comsumePrice;
    /**
     * 纸巾机ID
     */
    private String machineId;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 是否已删除  0未删除 1已删除
     */
    private Integer isDelete;
    /**
     * 订单过期时间
     */
    private Date expireTime;
    /**
     * 使用的优惠券ID
     */
    private String couponId;
    /**
     * 优惠价格
     */
    private BigDecimal couponPrice;
    /**
     * 活动ID
     */
    private String activityId;
    @TableField(exist = false)
    private String nickname;
    /**
     * 购买商品 用于前端展示  如：维达纸巾X2，洁柔纸巾X1，
     */
    @TableField(exist = false)
    private String keyValue;

    @TableField(exist = false)
    private String machineName;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 出货数量
     */
    @TableField(exist = false)
    private Integer shipmentNumber;

    /**
     * 1微信支付 2消费金抵扣    交易明细(如果又消费金抵扣、又微信支付，生成两个订单)
     */
    @TableField(exist = false)
    private Integer orderPayType;

    /**
     * 充值余额
     */
    @TableField(exist = false)
    private BigDecimal rechargeBalance;
}
