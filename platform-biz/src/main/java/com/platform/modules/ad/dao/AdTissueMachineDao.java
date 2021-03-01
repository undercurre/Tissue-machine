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
package com.platform.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.vo.RegionSortVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 纸巾机表Dao
 *
 * @author zqh
 * @date 2020-09-22 11:34:22
 */
@Mapper
public interface AdTissueMachineDao extends BaseMapper<AdTissueMachineEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdTissueMachineEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdTissueMachineEntity> getAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdTissueMachineEntity> selectAdTissueMachinePage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 更新软删除
     *
     * @param adTissueMachineEntity 机柜信息
     * @return List
     */
    void updateIsDelete(AdTissueMachineEntity adTissueMachineEntity);
    /**
     * 更新是否显示Logo
     *
     * @param adTissueMachineEntity 机柜信息
     */
    void updateIsShowLogo(AdTissueMachineEntity adTissueMachineEntity);
    /**
     * 更新机柜状态
     *
     * @param adTissueMachineEntity 机柜信息
     * @return List
     */
    void updateStatus(AdTissueMachineEntity adTissueMachineEntity);


    /**
     * 根据机柜ID获取详细信息
     *
     * @param id id
     * @return AdTissueMachineEntity
     */
    AdTissueMachineEntity getById(String id);

    /**
     * 根据机柜Sn码获取详细信息
     *
     * @param sn sn
     * @return AdTissueMachineEntity
     */
    AdTissueMachineEntity getBySn(String sn);

    /**
     * 更新机柜信息
     *
     * @param adTissueMachineEntity 机柜信息
     */
    boolean updateDetailById(AdTissueMachineEntity adTissueMachineEntity);

    void updateIsOpenLocate(AdTissueMachineEntity adTissueMachine);

    List<AdTissueMachineEntity> queryNoVoiceList();

    List<AdTissueMachineEntity> queryMachineListBySupId(IPage page, @Param("params") Map<String, Object> params);

    List<RegionSortVo> groupByTissueMachineInCity();

    List<RegionSortVo> groupByTissueMachineInDistrict();
}
