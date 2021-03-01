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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员等级管理实体
 *
 * @author zqh
 * @date 2019-07-02 09:05:15
 */
@Data
@TableName("MALL_USER_LEVEL")
public class MallUserLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty("主键")
    private String id;
    /**
     * 等级名称
     */
    @ApiModelProperty("等级名称")
    private String name;
    /**
     * 等级
     */
    @ApiModelProperty("等级")
    private int level;
    /**
     * 积分
     */
    @ApiModelProperty("积分")
    private int integral;
    /**
     * 活动ID
     */
    @ApiModelProperty("活动ID")
    private String activityId;
    /**
     * 会员等级图片
     */
    @ApiModelProperty("会员等级图片")
    private String imageUrl;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 成为高级会员的活动价格
     */
    @ApiModelProperty("成为高级会员的活动价格")
    @TableField(exist = false)
    private BigDecimal price;
}
