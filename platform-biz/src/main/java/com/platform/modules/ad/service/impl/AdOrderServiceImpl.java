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
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdOrderDao;
import com.platform.modules.ad.entity.AdIssueEntity;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.entity.AdOrderTissueEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdOrderService;
import com.platform.modules.ad.service.AdOrderTissueService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.vo.AdOrderAccountVo;
import com.platform.modules.vo.AdUserCountVo;
import com.platform.modules.vo.RegionSortVo;
import com.platform.modules.vo.TissueByDateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 订单表Service实现类
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
@Service("adOrderService")
public class AdOrderServiceImpl extends ServiceImpl<AdOrderDao, AdOrderEntity> implements AdOrderService {

    @Autowired
    private AdOrderTissueService adOrderTissueService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<AdOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdOrderEntity> page = new Query<AdOrderEntity>(params).getPage();
        List<AdOrderEntity> order = null;
        Integer level = sysUserDao.getUserLevel(user.getUserId());
        if (user.getIsStaff() == 1) {
            user.setUserId(user.getCreateUserId());
            level = sysUserDao.getUserLevel(user.getCreateUserId());
        }

        if (level == Constant.roleLevel.ZDL.getValue()) {
            order  = baseMapper.selectAdOrderPage(page, params);
        } else {
            params.put("agentId", user.getUserId());
            order = baseMapper.selectAdOrderPage(page, params);
        }

        order.forEach(e->{
            String keyValue = new String();
            List<AdOrderTissueEntity> tissueOrder = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", e.getId()));
            Iterator<AdOrderTissueEntity> iterator = tissueOrder.iterator();
            while(iterator.hasNext()){
                AdOrderTissueEntity next = iterator.next();
                keyValue=keyValue+next.getGoodsName()+"X"+next.getGoodsCount()+",";
                //删除最后逗号
                if(!iterator.hasNext()){
                    StringBuilder s1 = new StringBuilder(keyValue);
                    s1 = s1.deleteCharAt(s1.length() - 1);
                    keyValue = s1.toString();
                }
            }
            e.setKeyValue(keyValue);

        });
        return page.setRecords(order);
    }

    @Override
    public boolean add(AdOrderEntity adOrder) {
        return this.save(adOrder);
    }

    @Override
    public boolean update(AdOrderEntity adOrder) {
        return this.updateById(adOrder);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public boolean deleteOrderById(String orderId) {
        return this.baseMapper.deleteOrderById(orderId);
    }

    @Override
    public List<AdOrderEntity> getOrderListByUserId(String userId) {
        return this.baseMapper.getOrderListByUserId(userId);
    }

    @Override
    public List<AdOrderEntity> getOrderList(QueryWrapper<AdOrderEntity> queryWrapper) {
        return this.baseMapper.getOrderList(queryWrapper);
    }

    @Override
    public Map<String, Integer> getUserRemainNumber(MallUserEntity user){
        Map<String, Integer> remainNumMap = new HashMap<String, Integer>();
        // 根据支付时间来获取用户今天的购买订单
        List<AdOrderEntity> buyOrderList = this.list(new QueryWrapper<AdOrderEntity>()
                .eq("USER_ID", user.getId())
                .eq("ORDER_TYPE", Constant.OrderType.PTDD.getValue())
                .eq("ORDER_STATUS", Constant.OrderStatus.YZF.getValue())
                .or()
                .eq("ORDER_STATUS",Constant.OrderStatus.YTK.getValue())
                .between("PAY_TIME", DateUtils.getStartDate(), DateUtils.getEndDate())
        );
        Integer todayBuyNum = 0;
        for(AdOrderEntity adOrderEntity: buyOrderList){
            List<AdOrderTissueEntity> adOrderTissueEntityList = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>()
                    .eq("ORDER_ID", adOrderEntity.getId()));
            for(AdOrderTissueEntity adOrderTissueEntity: adOrderTissueEntityList){
                todayBuyNum += adOrderTissueEntity.getShipmentNumber();
            }
        }

        // 根据订单创建时间来获取用户今天的免费领取订单
        List<AdOrderEntity> freeOrderList = this.list(new QueryWrapper<AdOrderEntity>()
                .eq("USER_ID", user.getId())
                .eq("ORDER_TYPE", Constant.OrderType.MFLQ.getValue())
                .eq("ORDER_STATUS", Constant.OrderStatus.YZF.getValue())
                .between("CREATE_TIME",DateUtils.getStartDate(), DateUtils.getEndDate())
        );
        int user_daily_free_num = Integer.parseInt(sysConfigService.getValue("USER_DAILY_FREE_NUM"));
        int user_daily_buy_num = Integer.parseInt(sysConfigService.getValue("USER_DAILY_BUY_NUM"));
        remainNumMap.put("remainFreeNumber",user_daily_free_num);
        remainNumMap.put("remainBuyNumber",user_daily_buy_num);
        remainNumMap.put("userRemainFreeToday",user_daily_free_num - freeOrderList.size());
        remainNumMap.put("userRemainBuyToday",user_daily_buy_num  - todayBuyNum);
        return remainNumMap;
    }

    @Override
    public AdOrderEntity queryById(String id) {
        return this.baseMapper.queryById(id);
    }

    @Override
    public AdOrderAccountVo orderAccount(Integer day,Map<String,Object> level) {
        int levelInt = Integer.parseInt(level.get("level").toString());
        if(levelInt == Constant.roleLevel.ZDL.getValue()){
            return this.baseMapper.orderAccount(day);
        }else{
            Object userId = level.get("userId");

            return this.baseMapper.orderAccountByProxyAgentId(day,userId.toString());
        }
    }

    @Override
    public AdUserCountVo userCount(Integer days, Map<String, Object> map){
        Integer level = Integer.parseInt(map.get("level").toString());
        String agentId = (String)map.get("userId");
        Integer allRegisterUserNumber = null;
        AdOrderAccountVo adOrderAccountVo = null;
        // 如果是总代理就不带agentId查询，查出来的是所有的数据
        if (level == Constant.roleLevel.ZDL.getValue()) {
            agentId = null;
            allRegisterUserNumber = this.baseMapper.registerUserCount(null, null);
            adOrderAccountVo = this.baseMapper.orderAccount(days);
        } else {
            allRegisterUserNumber = this.baseMapper.registerUserCount(null, agentId);
            adOrderAccountVo = this.baseMapper.orderAccountByProxyAgentId(days, agentId);
        }
        System.out.println(adOrderAccountVo.toString());
        // 会员订单总数、有订单的会员数
        AdUserCountVo adUserCountVo = this.baseMapper.userCount(agentId);
        // 注册会员数
        if (days != null) {
            adUserCountVo.setRegisterUserNumber(this.baseMapper.registerUserCount(days, agentId));
        } else {
            adUserCountVo.setRegisterUserNumber(allRegisterUserNumber);
        }
        // 会员购物总额
        adUserCountVo.setOrderAmount(adOrderAccountVo.getInvalidOrderSum());
        // 会员购买率
        adUserCountVo.setUserBuyRate(new BigDecimal(adUserCountVo.getHaveOrderUserNumber() / (allRegisterUserNumber * 1.0) * 100).setScale(2, RoundingMode.HALF_UP).doubleValue());
        // 每会员订单数
        adUserCountVo.setOrderNumberPerPerson(new BigDecimal(adUserCountVo.getOrderNumber() / (allRegisterUserNumber * 1.0)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        // 每会员购物额
        System.out.println(allRegisterUserNumber);
        adUserCountVo.setOrderAmountPerPerson(adOrderAccountVo.getInvalidOrderSum().divide(new BigDecimal(allRegisterUserNumber), 2, RoundingMode.HALF_UP));
        return adUserCountVo;
    }

    @Override
    public Integer queryVipCountByDate(Integer type) {
        return this.baseMapper.queryVipCountByDate(type);
    }

    @Override
    public TissueByDateVo queryTissueByDate(Integer type) {
        return this.baseMapper.queryTissueByDate(type);
    }

    @Override
    public List<RegionSortVo> groupByTissueInCity() {
        return baseMapper.groupByTissueInCity();
    }

    @Override
    public List<RegionSortVo> groupByTissueInDistrict() {
        return baseMapper.groupByTissueInDistrict();
    }
}
