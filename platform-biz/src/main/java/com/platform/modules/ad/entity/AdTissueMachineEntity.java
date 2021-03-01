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
 * 纸巾机表实体
 *
 * @author zqh
 * @date 2020-09-22 11:34:22
 */
@Data
@TableName("AD_TISSUE_MACHINE")
public class AdTissueMachineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 纸巾机ID
     */
    @TableId
    private String id;
    /**
     * 终端唯一码
     */
    private String sn;
    /**
     * 纸巾机名称
     */
    private String name;
    /**
     * 纸巾机状态 1正常 2设备维修中
     */
    private Integer status;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 机器图片
     */
    private String machineImage;
    /**
     * 地址
     */
    private String address;
    /**
     * 工作时间 如：周一至周五 9:00-22:00
     */
    private String workTime;
    /**
     * 剩余纸巾数量
     */
    private Integer tissueNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改设备时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否删除 0未删除 1已删除
     */
    private Integer isDelete;
    /**
     * 机柜Logo
     */
    private String machineLogo;
    /**
     * 是否显示Logo
     */
    private Integer isShowLogo;

    /**
     * 代理ID
     */
    private String agentId;
    /**
     * 机柜版本号
     */
    private String machineVersion;
    /**
     * 机柜SIM的CCID号
     */
    private String simCcid;
    /**
     * 机柜服务器URL
     */
    private String serverUrl;
    /**
     * 经度
     */
    private BigDecimal reportLongitude;
    /**
     * 纬度
     */
    private BigDecimal reportLatitude;
    /**
     * 是否开启定位，0不开启（室内），1开启（室外）
     */
    private Integer isOpenLocate;
    /**
     * 设备电量  百分比
     */
    private Integer electricityQuantity;
    /**
     * 出纸异常订单数量
     */
    private Integer orderFailCount;
    /**
     * 是否正在使用
     */
    private Integer isUsed;

    /**
     * 灯条状态 0关闭状态  1亮状态
     */
    private Integer lightStatus;

    /**
     * 已使用流量
     */
    private BigDecimal usedFlow;

    /**
     * 机柜语音内容
     */
    private String Content;

    /**
     * 省级区划代码
     */
    private Integer provinceCode;

    /**
     * 市级区划代码
     */
    private Integer cityCode;

    /**
     * 区级区划代码
     */
    private Integer districtCode;

    /**
     * 省级区划
     */
    private String province;

    /**
     * 市级区划
     */
    private String city;

    /**
     * 区级区划
     */
    private String district;

    /**
     * 机柜贴片广告
     */
    @TableField(exist = false)
    private List<AdPatchEntity> orderPatch;

    /**
     * 机柜分组中间表ID
     */
    @TableField(exist = false)
    private String machineGroupId;
}
