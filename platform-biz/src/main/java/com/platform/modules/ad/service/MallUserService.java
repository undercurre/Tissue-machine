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
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.vo.RegionSortVo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户service
 *
 * @author zqh
 */
public interface MallUserService extends IService<MallUserEntity> {


    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MallUserEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param mallUser
     * @return 新增结果
     */
    boolean add(MallUserEntity mallUser);

    /**
     * 根据主键更新
     *
     * @param mallUser
     * @return 更新结果
     */
    boolean update(MallUserEntity mallUser);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * queryByMobile
     *
     * @param mobile 手机号
     * @return UserEntity
     */
    MallUserEntity queryByMobile(String mobile);

    /**
     * 登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return String
     */
    MallUserEntity loginByMobile(String mobile, String password);

    /**
     * 根据openId获取用户
     *
     * @param openId openId
     * @return UserEntity
     */
    MallUserEntity selectByOpenId(String openId);

    /**
     * 新增或者修改
     *
     * @param user user
     * @return UserEntity
     */
    MallUserEntity saveOrUpdateByOpenId(WxMpUser user);

    /**
     * 修改用户的会员积分
     */
    void modifyUserIntegral(String userId, BigDecimal integral);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    List<MallUserEntity> selectMallUserByAgent(Map<String, Object> params);

    /**
     * 注册总数
     *
     *  @param type (type=1 本年，type=2 本季度，type=3 本月，type=4 本周，type=5 本日)
     * @return Integer
     */
    Integer queryRegisterTotal(Integer type);

    /**
     * 根据市级分组查询会员数量
     *
     * @return RegionSortVo
     */
    List<RegionSortVo> groupByUserInCity();

    /**
     * 根据区级分组查询会员数量
     *
     * @return RegionSortVo
     */
    List<RegionSortVo> groupByUserInDistinct();
}
