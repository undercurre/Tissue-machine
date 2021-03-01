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
import java.util.List;

/**
 * 机柜库存表实体
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Data
@TableName("AD_MACHINE_STOCK")
public class AdMachineStockEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 机柜ID
     */
    private String machineId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 排序等级 升序
     */
    private Integer level;
    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String machineName;

    @TableField(exist = false)
    private String goodsName;

    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private List<AdGoodsBannerEntity> goodsBanner;

    @TableField(exist = false)
    private Integer maxLevel;
}
