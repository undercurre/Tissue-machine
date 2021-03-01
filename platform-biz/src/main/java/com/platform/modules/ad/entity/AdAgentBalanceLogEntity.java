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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理流水表实体
 *
 * @author zqh
 * @date 2020-12-09 10:12:34
 */
@Data
@TableName("AD_AGENT_BALANCE_LOG")
public class AdAgentBalanceLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 代理商用户ID
     */
    private String agentId;
    /**
     * 交易金额
     */
    private BigDecimal balance;
    /**
     * 类型  1收入  2 支出
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单ID
     */
    private String orderId;
}
