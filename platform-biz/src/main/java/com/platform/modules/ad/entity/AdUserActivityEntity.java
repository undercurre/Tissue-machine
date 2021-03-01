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
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户活动表实体
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Data
@ToString
@TableName("AD_USER_ACTIVITY")
public class AdUserActivityEntity implements Serializable {
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
     * 活动ID
     */
    private String activityId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否有效
     */
    @TableField(exist = false)
    private Boolean valid = true;
    /**
     * 该活动当天可领取的次数
     */
    @TableField(exist = false)
    private Integer dayCount;
    /**
     * 活动总额
     */
    @TableField(exist = false)
    private Integer count;
    /**
     * 活动类型 1-纸巾
     */
    @TableField(exist = false)
    private Integer type;
    /**
     * 描述
     */
    @TableField(exist = false)
    private String remark;
    /**
     * 是否已删除  0未删除 1已删除
     */
    @TableField(exist = false)
    private Integer isDelete;
    /**
     * 过期时间
     */
    @TableField(exist = false)
    private Date expireTime;
    /**
     * 是否有门槛
     */
    @TableField(exist = false)
    private Boolean confine;
    /**
     * 是否有参加
     */
    @TableField(exist = false)
    private Boolean join = true;
}
