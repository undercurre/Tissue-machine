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
import java.util.Date;
import java.util.List;

/**
 * 广告表实体
 *
 * @author gjw
 * @date 2020-12-19 09:34:35
 */
@Data
@TableName("AD_ADVERT")
public class AdAdvertEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 广告商姓名
     */
    private String name;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 单位性质 1：企业，2：商家，3：个人
     */
    private Integer workunitType;
    /**
     * 单位名称
     */
    private String workunitName;
    /**
     * 回访状态 0：待回访，1：已回访
     */
    private Integer status;
    /**
     * 是否删除 0：未删除 1：已删除
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickname;
}
