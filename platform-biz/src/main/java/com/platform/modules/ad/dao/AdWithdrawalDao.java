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
import com.platform.modules.ad.entity.AdWithdrawalEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 提现记录表Dao
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@Mapper
public interface AdWithdrawalDao extends BaseMapper<AdWithdrawalEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdWithdrawalEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdWithdrawalEntity> selectAdWithdrawalPage(IPage page, @Param("params") Map<String, Object> params);



    /**
     * 获取用户最近使用过的银行卡
     *
     * @param userId   用户Id
     * @return List
     */
    AdWithdrawalEntity getLastestCard(String userId);

    /**
     * 根据ID获取新行卡信息
     *
     * @param id   id
     * @return AdWithdrawalEntity
     */
    AdWithdrawalEntity getDetailById(String userId);
}