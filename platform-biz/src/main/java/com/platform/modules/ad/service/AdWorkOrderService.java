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
package com.platform.modules.ad.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdWorkOrderEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zqh
 * @date 2020-10-19 14:05:28
 */
public interface AdWorkOrderService extends IService<AdWorkOrderEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdWorkOrderEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryMyWorkOrderPage(Map<String, Object> params);

    /**
     * 移动端分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page AppQueryPage(Map<String, Object> params, String userId);

    /**
     * 新增
     *
     * @param adWorkOrder
     * @return 新增结果
     */
    boolean add(AdWorkOrderEntity adWorkOrder);

    /**
     * 根据主键更新
     *
     * @param adWorkOrder
     * @return 更新结果
     */
    boolean update(AdWorkOrderEntity adWorkOrder);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * 根据ID查询详情
     *
     * @param id   id
     * @return AdWorkOrderEntity
     */
    AdWorkOrderEntity getDetailById(String id);

    /**
     * 获取经纬度对应的地址
     *
     * @param params  请求参数
     * @return JSONObject 调用API后返回的参数
     */
    JSONObject getAddress(Map<String, Object> params) throws Exception;

    /**
     * 新工单公众号消息推送
     *
     * @param openId
     * @param workTypeTitle 1:有新的工单需要处理, 2:有工单需要确认完结, 3:工单已确认完结
     */
    void sendNewWorkOrder(AdWorkOrderEntity adWorkOrderEntity, String openId, Integer workTypeTitle);

    /**
     * 循环发送公众号模板消息给部门经理
     *
     * @param adWorkOrderEntity
     */
    void sendMessageToManage(AdWorkOrderEntity adWorkOrderEntity);

    /**
     * 发送公众号模板消息给最后提交完结的人
     *
     * @param adWorkOrderEntity
     */
    void sendFinishWorkMessageToWorker(AdWorkOrderEntity adWorkOrderEntity);

    /**
     * 获取工单创建者所属部门的所有部门经理的openId
     *
     * @param parentId 父级工单ID
     * @return List<String>
     */
    List<String> getParentOpenId(String parentId);
}
