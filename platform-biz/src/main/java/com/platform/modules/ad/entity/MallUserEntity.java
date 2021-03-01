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
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户
 *
 * @author zqh
 */
@Data
@TableName("MALL_USER")
public class MallUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @ApiModelProperty(hidden = true)
    private String id;
    /**
     * 用户名
     */
    @ApiModelProperty(hidden = true)
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(hidden = true)
    private String password;
    /**
     * 用户的性别（1是男性，2是女性，0是未知）
     */
    @ApiModelProperty(hidden = true)
    private Integer gender;
    /**
     * 生日
     */
    @ApiModelProperty(hidden = true)
    private Date birthday;
    /**
     * 注册时间
     */
    @ApiModelProperty(hidden = true)
    private Date registerTime;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(hidden = true)
    private Date lastLoginTime;
    /**
     * 最后登录IP
     */
    @ApiModelProperty(hidden = true)
    private String lastLoginIp;
    /**
     * 会员等级ID
     */
    @ApiModelProperty(hidden = true)
    private String userLevelId;
    /**
     * 微信昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickname;
    /**
     * 手机号
     */
    @ApiModelProperty(hidden = true)
    private String mobile;
    /**
     * 注册ip
     */
    @ApiModelProperty(hidden = true)
    private String registerIp;
    /**
     * 用户头像
     */
    @ApiModelProperty(hidden = true)
    private String headImgUrl;
    /**
     * 支付宝用户标识
     */
    @ApiModelProperty(hidden = true)
    private String aliUserId;
    /**
     * 微信小程序用户的标识
     */
    @ApiModelProperty(hidden = true)
    private String openId;
    /**
     * QQ小程序用户的标识
     */
    @ApiModelProperty(hidden = true)
    private String qqOpenId;
    /**
     * 公众号用户的标识
     */
    @ApiModelProperty(hidden = true)
    private String mpOpenId;
    /**
     * 需要用户将公众号、小程序绑定到微信开放平台帐号
     * 同一主体下的unionId一致
     */
    @ApiModelProperty(hidden = true)
    private String unionId;
    /**
     * 公众号关注状态（1是关注，0是未关注），未关注时获取不到其余信息
     */
    @ApiModelProperty(hidden = true)
    private Integer subscribe;
    /**
     * 用户关注公众号时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @ApiModelProperty(hidden = true)
    private String subscribeTime;
    /**
     * 签到、购物获得总积分
     */
    @ApiModelProperty(hidden = true)
    private BigDecimal signAllIntegral = new BigDecimal(0);
    /**
     * 已兑换积分
     */
    @ApiModelProperty(hidden = true)
    private BigDecimal signUsedIntegral = new BigDecimal(0);
    /**
     * 余额
     */
    @ApiModelProperty(hidden = true)
    private BigDecimal balance = new BigDecimal(0);
    /**
     * 用户参与问卷调查的标识
     */
    @ApiModelProperty(hidden = true)
    private int interestSurvey;
    /**
     * 用户纸巾数
     */
    @ApiModelProperty(hidden = true)
    private Integer tissueCount;
    /**
     * 一天可以购买的纸巾数目
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer userRemainBuyToday;
    /**
     * 一天可以免费获得的纸巾数目
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer remainFreeToday;
    /**
     * 用户今天剩余的可以购买纸巾数目
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer remainBuyToday;
    /**
     * 用户今天剩余的可以免费获得纸巾数目
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer userRemainFreeToday;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String userLevelName;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Boolean isDistributor;
    /**
     * 设备ID
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String machineId;
    /**
     * 注册来源
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer fromType;
}
