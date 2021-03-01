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

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员优惠券实体
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Data
@TableName("AD_USER_COUPON")
public class AdUserCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 会员ID
     */
    private String userId;
    /**
     * 优惠券ID
     */
    private String couponId;
    /**
     * 领用时间
     */
    private Date addTime;
    /**
     * 领取类型 0:平台发放 1:自动发放
     */
    private Integer type;
    /**
     * 状态 0:未使用 1:已使用 2：过期
     */
    private Integer status;
    /**
     * 使用时间
     */
    @TableField(updateStrategy= FieldStrategy.IGNORED)
    private Date usedTime;
    /**
     * 使用的订单ID
     */
    @TableField(updateStrategy= FieldStrategy.IGNORED)
    private String orderId;
    /**
     * 微信昵称
     */
    @TableField(exist = false)
    private String nickname;
    /**
     * 使用订单编号
     */
    @TableField(exist = false)
    private String orderSn;
    /**
     * 优惠券标题
     */
    @TableField(exist = false)
    private String title;
    /**
     * 优惠券编号
     */
    @TableField(exist = false)
    private String couponSn;
    /**
     * 优惠券类型：1:代金券 2:折扣
     */
    @TableField(exist = false)
    private Integer couponType;
    /**
     * 最低消费金额
     */
    @TableField(exist = false)
    private BigDecimal minPrice;
    /**
     * 优惠金额
     */
    @TableField(exist = false)
    private BigDecimal subPrice;
    /**
     * 折扣率
     */
    @TableField(exist = false)
    private BigDecimal discount;
    /**
     * 有效期开始时间
     */
    @TableField(exist = false)
    private String beginTime;
    /**
     * 有效期结束时间
     */
    @TableField(exist = false)
    private String endTime;
    /**
     * 指定使用类型 0：全场通用券 1：指定商品 2：指定品牌
     */
    @TableField(exist = false)
    private Integer limitType;
}
