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
 * 问题表实体
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Data
@TableName("AD_ISSUE")
public class AdIssueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * USER_ID
     */
    private String userId;
    /**
     * MACHINE_ID
     */
    private String machineId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除 0未删除 1已删除
     */
    private Integer isDelete;
    /**
     * 处理状态 0未处理 1已处理
     */
    private Integer status;

    /**
     * 会员nickname
     */
    @TableField(exist = false)
    private String nickname;

    /**
     * 会员nickname
     */
    @TableField(exist = false)
    private String machineName;

    /**
     * 问题反馈图片列表
     */
    @TableField(exist = false)
    private List<AdIssueImageEntity> adIssueImageEntityList;
}
