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
 * 机柜发放纸巾中间表实体
 *
 * @author zqh
 * @date 2020-11-02 13:54:01
 */
@Data
@TableName("AD_MACHINE_PUSH")
public class AdMachinePushEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 机柜SN
     */
    private String machineSn;
    /**
     * 需要下放纸巾的数量
     */
    private Integer number;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 创建日期
     */
    private Date createdTime;

    /**
     * 申请出纸状态
     */
    private Integer status;

}
