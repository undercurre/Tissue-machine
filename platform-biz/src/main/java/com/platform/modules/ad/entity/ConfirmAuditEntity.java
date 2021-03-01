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

import lombok.Data;

import java.io.Serializable;

/**
 * 审核实体
 *
 * @author Cury
 * @date 2020-04-27 13:39:50
 */
@Data
public class ConfirmAuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String[] ids;
    private String auditStatus;
    private String auditDesc;
}
