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
package com.platform.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.JedisUtil;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.ad.dao.MallUserDao;
import com.platform.modules.ad.entity.AdMachineAgentEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.entity.MallUserLevelEntity;
import com.platform.modules.ad.service.AdMachineAgentService;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.ad.service.MallUserLevelService;
import com.platform.modules.ad.service.MallUserService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.vo.RegionSortVo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zqh
 */
@Service("userService")
public class MallUserServiceImpl extends ServiceImpl<MallUserDao, MallUserEntity> implements MallUserService {
    @Autowired
    JedisUtil jedisUtil;
    @Autowired
    MallUserLevelService levelService;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    AdTissueMachineService adTissueMachineService;
    @Autowired
    AdMachineAgentService adMachineAgentService;

    @Override
    public List<MallUserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.REGISTER_TIME");
        params.put("asc", false);
        String sysUserId = (String) params.get("sysUserId");
        SysUserEntity sysUserEntity = sysUserDao.queryById(sysUserId);
        if (!StringUtils.isEmpty(sysUserEntity)) {
            // 如果是员工
            if (sysUserEntity.getIsStaff().equals(Constant.isStaff.YES.getValue())) {
                String createUserId = sysUserEntity.getCreateUserId();
                // 且他的上级是代理
                if (sysUserDao.getUserLevel(createUserId).equals(Constant.roleLevel.DL.getValue())) {
                    List<AdTissueMachineEntity> adTissueMachineEntityList = adTissueMachineService.list(new QueryWrapper<AdTissueMachineEntity>()
                            .eq("AGENT_ID", createUserId)
                    );
                    if(adTissueMachineEntityList.size() > 0){
                        List<String> item = adTissueMachineEntityList.stream().map(e->e.getId()).collect(Collectors.toList());
                        params.put("machineIds",item);
                        Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
                        List<MallUserEntity> user = baseMapper.selectMallUserPageByAgent(page, params).stream()
                                .filter(distinctByKey(MallUserEntity::getId))
                                .collect(Collectors.toList());
                        return page.setRecords(user);
                    }else {
                        Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
                        List<MallUserEntity> user = null;
                        return page.setRecords(user);
                    }
                }
                // 他的上级是总代理
                else if(sysUserDao.getUserLevel(createUserId).equals(Constant.roleLevel.ZDL.getValue())){
                    Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
                    List<MallUserEntity> user = baseMapper.selectMallUserPageByAgent(page, params).stream()
                            .filter(e->e.getFromType() != null)
                            .filter(e->!e.getFromType().equals(Constant.userFromType.XD.getValue()))
                            .collect(Collectors.toList());
                    return page.setRecords(user);
                }
            }
            // 如果是代理
            else{
                if (sysUserDao.getUserLevel(sysUserId).equals(Constant.roleLevel.DL.getValue())){
                    List<AdTissueMachineEntity> adTissueMachineEntityList = adTissueMachineService.list(new QueryWrapper<AdTissueMachineEntity>()
                            .eq("AGENT_ID", sysUserId)
                    );
                    if(adTissueMachineEntityList.size() > 0){
                        List<String> item = adTissueMachineEntityList.stream().map(e->e.getId()).collect(Collectors.toList());
                        params.put("machineIds",item);
                        Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
                        List<MallUserEntity> user = baseMapper.selectMallUserPageByAgent(page, params).stream()
                                .filter(distinctByKey(MallUserEntity::getId))
                                .collect(Collectors.toList());
                        return page.setRecords(user);
                    } else {
                        Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
                        List<MallUserEntity> user = null;
                        return page.setRecords(user);
                    }
                }
            }
        }
        // 总代理
        Page<MallUserEntity> page = new Query<MallUserEntity>(params).getPage();
        List<MallUserEntity> user = baseMapper.selectMallUserPage(page, params);
        return page.setRecords(user);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public boolean add(MallUserEntity mallUser) {
        return this.save(mallUser);
    }

    @Override
    public boolean update(MallUserEntity mallUser) {
        return this.updateById(mallUser);
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public MallUserEntity queryByMobile(String mobile) {
        return this.getOne(new QueryWrapper<MallUserEntity>().eq("MOBILE", mobile), false);
    }

    @Override
    public MallUserEntity loginByMobile(String mobile, String password) {
        MallUserEntity user = queryByMobile(mobile);
        AbstractAssert.isNull(user, "该手机暂未绑定用户");

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            throw new BusinessException("手机号或密码错误");
        }

        return user;
    }

    @Override
    public MallUserEntity selectByOpenId(String openId) {
        MallUserEntity userEntity = new MallUserEntity();
        userEntity.setOpenId(openId);
        return baseMapper.selectOne(new QueryWrapper<MallUserEntity>().eq("OPEN_ID", openId));
    }

    @Override
    public MallUserEntity saveOrUpdateByOpenId(WxMpUser userWxInfo) {
        MallUserEntity entity = this.getOne(new QueryWrapper<MallUserEntity>().eq("OPEN_ID", userWxInfo.getOpenId()));
        if (entity == null) {
            entity = new MallUserEntity();
            entity.setRegisterTime(new Date());
        }
        entity.setNickname(userWxInfo.getNickname());
        entity.setHeadImgUrl(userWxInfo.getHeadImgUrl());
        entity.setOpenId(userWxInfo.getOpenId());
        entity.setGender(userWxInfo.getSex());
        this.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void modifyUserIntegral(String userId, BigDecimal integral) {
        MallUserEntity user = baseMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户为空");
        }
        BigDecimal allIntegral = user.getSignAllIntegral().add(integral);

        List<MallUserLevelEntity> levelEntityList = levelService.list();
        int level = -1;
        MallUserLevelEntity levelEntity = null;
        for (MallUserLevelEntity l : levelEntityList) {
            // 当前总积分大于等于某个会员等级的要求，且该会员等级的级别高于tempLevel，则更新
            if (allIntegral.compareTo(BigDecimal.valueOf(l.getIntegral())) >= 0 && l.getLevel() >= level) {
                level = l.getLevel();
                levelEntity = l;
            }
        }
        user.setSignAllIntegral(allIntegral);
        if (levelEntity != null) {
            user.setUserLevelId(levelEntity.getId());
        }
        this.update(user);
    }

    @Override
    public List<MallUserEntity> selectMallUserByAgent(Map<String, Object> params) {
        String sysUserId = (String) params.get("sysUserId");
        // 代理
        if (sysUserDao.getUserLevel(sysUserId).equals(Constant.roleLevel.DL.getValue())) {
            List<AdTissueMachineEntity> adTissueMachineEntityList = adTissueMachineService.list(new QueryWrapper<AdTissueMachineEntity>()
                    .eq("AGENT_ID", sysUserId)
            );
            if (adTissueMachineEntityList.size() > 0) {
                List<String> item = adTissueMachineEntityList.stream().map(e->e.getId()).collect(Collectors.toList());
                params.put("machineIds",item);
                List<MallUserEntity> user = baseMapper.selectMallUserByAgent(params).stream()
                        .filter(distinctByKey(MallUserEntity::getId))
                        .collect(Collectors.toList());
                return user;
            } else {
                List<MallUserEntity> user = null;
                return user;
            }
        }
        // 总代理
        else {
            List<MallUserEntity> user = baseMapper.queryAll(params);
//            List<MallUserEntity> user = baseMapper.selectMallUserByAgent(params).stream()
//                    .filter(e->e.getFromType() != null)
//                    .filter(e->!e.getFromType().equals(Constant.userFromType.XD.getValue()))
//                    .collect(Collectors.toList());
            return user;
        }
    }

    @Override
    public Integer queryRegisterTotal(Integer type) {
        return baseMapper.queryRegisterTotal(type);
    }

    @Override
    public List<RegionSortVo> groupByUserInCity() {
        return baseMapper.groupByUserInCity();
    }

    @Override
    public List<RegionSortVo> groupByUserInDistinct() {
        return baseMapper.groupByUserInDistrict();
    }
}
