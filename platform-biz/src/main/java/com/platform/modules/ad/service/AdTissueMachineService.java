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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.protocol.entity.TissueProtocol;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.vo.RegionSortVo;

import java.util.List;
import java.util.Map;

/**
 * 纸巾机表Service接口
 *
 * @author zqh
 * @date 2020-09-22 11:34:22
 */
public interface AdTissueMachineService extends IService<AdTissueMachineEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @param user 发起请求的用户信息
     * @return List
     */
    List<AdTissueMachineEntity> queryAll(Map<String, Object> params, SysUserEntity user);

    /**
     * 分页查询纸巾机表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params, SysUserEntity user);

    /**
     * 根据ID查询机柜
     *
     * @param id id
     * @return AdTissueMachineEntity
     */
    AdTissueMachineEntity getById(String id);

    /**
     * 根据Sn码查询机柜
     *
     * @param sn sn码
     * @return AdTissueMachineEntity
     */
    AdTissueMachineEntity getBySn(String sn);

    /**
     * 新增纸巾机表
     *
     * @param adTissueMachine 纸巾机表
     * @return 新增结果
     */
    boolean add(AdTissueMachineEntity adTissueMachine) throws Exception;

    /**
     * 根据主键更新纸巾机表
     *
     * @param adTissueMachine 纸巾机表
     * @return 更新结果
     */
    boolean update(AdTissueMachineEntity adTissueMachine);

    /**
     * 根据主键删除纸巾机表
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
     * 更新软删除
     *
     * @param adTissueMachineEntity 机柜信息
     * @return boolean
     */
    boolean updateIsDelete(AdTissueMachineEntity adTissueMachineEntity);
    /**
     * 更新是否显示Logo
     *
     * @param adTissueMachineEntity 机柜信息
     * @return boolean
     */
    boolean updateIsShowLogo(AdTissueMachineEntity adTissueMachineEntity);

    /**
     * 更新软删除
     *
     * @param adTissueMachineEntity 机柜信息
     * @return boolean
     */
    boolean updateStatus(AdTissueMachineEntity adTissueMachineEntity);


    /**
     * 更新软删除
     *
     * @param adTissueMachineEntity 机柜信息
     * @return boolean
     */
    boolean updateDetailById(AdTissueMachineEntity adTissueMachineEntity);

    /**
     * 发送HTTP请求控制设备出货
     *
     * @param  sn
     * @return boolean
     */
    TissueProtocol pushGoods(String sn);

    /**
     * 发送HTTP请求控制设备出货
     *
     * @param  id
     * @return boolean
     */
    AdMachineOperateEntity restart(String id);

    boolean updateIsOpenLocate(AdTissueMachineEntity adTissueMachine);

    List<AdTissueMachineEntity> queryNoVoiceList();

    /**
     * 分页查询对应语音的纸巾机列表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryMachineListBySupId(Map<String, Object> params);

    /**
     * 根据市级分组查询纸巾机数量
     *
     * @return RegionSortVo
     */
    List<RegionSortVo> groupByTissueMachineInCity();

    /**
     * 根据区级分组查询纸巾机数量
     *
     * @return RegionSortVo
     */
    List<RegionSortVo> groupByTissueMachineInDistrict();

    /**
     * 根据设备分组通过公众号发送消息给负责人
     */
    Boolean sendMessage(AdTissueMachineEntity adTissueMachineEntity,Integer status);
}
