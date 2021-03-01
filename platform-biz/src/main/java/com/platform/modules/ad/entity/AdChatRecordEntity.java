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

/**
 * 客服聊天记录表实体
 *
 * @author zqh,dxd
 * @date 2020-10-28 14:14:46
 */
@Data
@TableName("AD_CHAT_RECORD")
public class AdChatRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String id;
    /**
     * 用户openId
     */
    private String openId;
    /**
     * 操作码
     */
    private Integer operCode;
    /**
     * 聊天记录
     */
    private String text;
    /**
     * 信息发送时间
     */
    private Date createdTime;
    /**
     * 客服ID
     */
    private String workerId;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 客服昵称
     */
    @TableField(exist = false)
    private String workerName;
    /**
     * 对话结束时间
     */
    @TableField(exist = false)
    private Date endTime;
}
