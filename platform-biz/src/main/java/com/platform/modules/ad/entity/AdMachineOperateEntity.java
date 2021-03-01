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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备操作表实体
 *
 * @author zqh
 * @date 2020-11-06 09:49:25
 */
@Data
@TableName("AD_MACHINE_OPERATE")
public class AdMachineOperateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId
    private String id;
    /**
     * 操作的机柜ID
     */
    private String machineId;
    /**
     * 操作码 1更新语音 2获取SIM卡CCID 3获取设备版本 4获取设备位置信息 5获取服务器URL 6设备重启
     */
    private Integer operateCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态 1未执行  2 申请执行 3执行成功 4执行异常
     */
    private Integer status;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 排序指数
     */
    private Integer sortLevel;
    /**
     * 操作内容
     */
    private String content;
}
