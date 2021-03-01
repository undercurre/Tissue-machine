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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.HttpUtils;
import com.platform.modules.ad.entity.AdChatRecordEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客服聊天记录表Service接口
 *
 * @author zqh,dxd
 * @date 2020-10-28 14:14:46
 */
public interface AdChatRecordService extends IService<AdChatRecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdChatRecordEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询客服聊天记录表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增客服聊天记录表
     *
     * @param adChatRecord 客服聊天记录表
     * @return 新增结果
     */
    boolean add(AdChatRecordEntity adChatRecord);

    /**
     * 根据主键更新客服聊天记录表
     *
     * @param adChatRecord 客服聊天记录表
     * @return 更新结果
     */
    boolean update(AdChatRecordEntity adChatRecord);

    /**
     * 根据主键删除客服聊天记录表
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

//    Map<String,Object> getMsgList();
}
