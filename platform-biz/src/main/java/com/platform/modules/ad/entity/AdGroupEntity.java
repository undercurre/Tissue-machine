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
 * 设备分组(片区)表实体
 *
 * @author zqh
 * @date 2021-01-08 16:15:53
 */
@Data
@TableName("AD_GROUP")
public class AdGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 负责人OPEN_ID
     */
    private String userOpenId;
    /**
     * 分组(片区)名字
     */
    private String groupName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 机柜列表
     */
    @TableField(exist = false)
    private List<String> machineIds;
    /**
     * 机柜列表
     */
    @TableField(exist = false)
    private String nickname;
}
