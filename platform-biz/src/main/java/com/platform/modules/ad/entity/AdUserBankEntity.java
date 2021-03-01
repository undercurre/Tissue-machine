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

/**
 * 用户银行卡信息表实体
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@Data
@TableName("AD_USER_BANK")
public class AdUserBankEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String id;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡卡号
     */
    private Long bankNo;
    /**
     * 绑定的用户ID
     */
    private String userId;
    /**
     * 绑定的用户姓名
     */
    private String userName;
    /**
     * 银行卡绑定手机号
     */
    private String mobile;
    /**
     * 银行卡类型
     */
    private Integer type;
}
