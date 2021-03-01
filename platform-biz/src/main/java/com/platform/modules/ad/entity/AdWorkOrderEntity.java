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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 运维中心工单表实体
 *
 * @author dxd
 * @date 2020-10-19 14:57:09
 */
@Data
@TableName("AD_WORK_ORDER")
public class AdWorkOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    @TableId
    private String id;
    /**
     * 指派人员ID
     */
    private String workerId;
    /**
     * 工单创建者ID
     */
    private String creatorId;
    /**
     * 工单类型 0：采购工单 1：设备维护工单 2：送货工单 3：贴印刷广告工单
     */
    private String workType;
    /**
     * 工单状态 0：未响应 1：待处理 2：已处理
     */
    private Integer status;
    /**
     * 工单创建时间
     */
    private Date createTime;
    /**
     * 工单绑定的机柜
     */
    private String machineId;
    /**
     * 运维人员接单时间
     */
    private Date acceptTime;
    /**
     * 工单完成时间
     */
    private Date finishTime;
    /**
     * 工单描述
     */
    private String des;
    /**
     * 父级工单ID
     */
    private String parentId;
    /**
     * 子级工单排序号
     */
    private Integer orderNum;
    /**
     * 是否完结
     */
    private Integer isEnd;
    /**
     * 工单备注
     */
    private String remark;
    /**
     * 工单完成时限（开始时间）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 工单完成时限（结束时间）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deadlineTime;
    /**
     * 是否作废
     */
    private Integer isAvail;
    /**
     * 作废理由
     */
    private String reason;
    /**
     * 地址
     */
    private String address;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 工单名
     */
    private String name;
    /**
     * 软删除标志
     */
    private Integer isDeleted;
    /**
     * 机柜名称
     */
    @TableField(exist = false)
    private String machineName;
    /**
     * 运维人员名称
     */
    @TableField(exist = false)
    private String workerName;
    /**
     * 工单完成状况图片列表
     */
    @TableField(exist = false)
    private List<AdWorkOrderImageEntity> imageList;
    /**
     * 子工单列表
     */
    @TableField(exist = false)
    private List<AdWorkOrderEntity> sonWorkOrderList;
}
