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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录表实体
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@Data
@TableName("AD_WITHDRAWAL")
public class AdWithdrawalEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String id;
    /**
     * USER_BANK表ID
     */
    private String userBankId;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * 提现状态 0：待审核 1：提现成功  2：提现失败
     */
    private Integer status;
    /**
     * 提现发起时间
     */
    private Date createTime;
    /**
     * 提现成功时间
     */
    private Date finishTime;
    /**
     * 处理人ID
     */
    private String handlerId;
    /**
     * 手续费
     */
    private BigDecimal commission;
    /**
     * 银行名称
     */
    @TableField(exist = false)
    private String bankName;
    /**
     * 银行卡卡号
     */
    @TableField(exist = false)
    private String bankNo;
    /**
     * 处理人姓名
     */
    @TableField(exist = false)
    private String realName;
    /**
     * 银行卡归属人
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 银行卡绑定手机号
     */
    @TableField(exist = false)
    private String mobile;
}
