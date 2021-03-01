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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机柜开关灯时间表实体
 *
 * @author dxd
 * @date 2020-12-22 14:13:54
 */
@Data
@TableName("AD_MACHINE_LIGHT")
public class AdMachineLightEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String id;
    /**
     * 机柜SN码
     */
    private String machineSn;
    /**
     * 开灯时间
     */
    @JsonFormat(pattern = "HH:mm", timezone="GMT+8")
    private Date startTime;
    /**
     * 关灯时间
     */
    @JsonFormat(pattern = "HH:mm", timezone="GMT+8")
    private Date endTime;
    /**
     * 机柜ID
     */
    @TableField(exist = false)
    private String machineId;
    /**
     * 机柜名称
     */
    @TableField(exist = false)
    private String machineName;
}
