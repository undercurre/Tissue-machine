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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.JsonUtils;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdWorkOrderDao;
import com.platform.modules.ad.entity.AdWorkOrderEntity;
import com.platform.modules.ad.entity.AdWorkOrderImageEntity;
import com.platform.modules.ad.entity.AdWorkTypeEntity;
import com.platform.modules.ad.service.AdWorkOrderImageService;
import com.platform.modules.ad.service.AdWorkOrderService;
import com.platform.modules.ad.service.AdWorkTypeService;
import com.platform.modules.ad.service.WxMpSendMsgService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.dao.SysUserDepartmentDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.vo.NewOrderVo;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service实现类
 *
 * @author zqh
 * @date 2020-10-19 14:05:28
 */
@Service("adWorkOrderService")
public class AdWorkOrderServiceImpl extends ServiceImpl<AdWorkOrderDao, AdWorkOrderEntity> implements AdWorkOrderService {

    @Autowired
    private AdWorkOrderImageService adWorkOrderImageService;
    @Autowired
    private WxMpSendMsgService mpSendMsgService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private AdWorkTypeService adWorkTypeService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserDepartmentDao sysUserDepartmentDao;

    @Value("${wx.map.key}")
    private String key;

    @Override
    public List<AdWorkOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdWorkOrderEntity> page = new Query<AdWorkOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdWorkOrderPage(page, params));
    }

    @Override
    public Page queryMyWorkOrderPage(Map<String, Object> params) {

        Page<AdWorkOrderEntity> page = new Query<AdWorkOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.queryMyWorkOrder(page, params));
    }

    @Override
    public Page AppQueryPage(Map<String, Object> params, String userId) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        params.put("userId", userId);
        Page<AdWorkOrderEntity> page = new Query<AdWorkOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.AppSelectAdWorkOrderPage(page, params));
    }

    @Override
    public boolean add(AdWorkOrderEntity adWorkOrder) {
        return this.save(adWorkOrder);
    }

    @Override
    public boolean update(AdWorkOrderEntity adWorkOrder) {
        return this.updateById(adWorkOrder);
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
    public AdWorkOrderEntity getDetailById(String id) {
        AdWorkOrderEntity adWorkOrderEntity = baseMapper.getDetailById(id);

        if (adWorkOrderEntity == null) {
            return null;
        }

        adWorkOrderEntity.setImageList(adWorkOrderImageService.list(new QueryWrapper<AdWorkOrderImageEntity>()
                .eq("WORK_ORDER_ID", adWorkOrderEntity.getId())));

        List<AdWorkOrderEntity> sonList = baseMapper.getSonList(id);

        sonList.forEach(e -> {
            System.out.println(e.toString());
            e.setImageList(adWorkOrderImageService.list(new QueryWrapper<AdWorkOrderImageEntity>().eq("WORK_ORDER_ID", e.getId())));
        });

        adWorkOrderEntity.setSonWorkOrderList(sonList);
        return  adWorkOrderEntity;
    }

    @Override
    public JSONObject getAddress(Map<String, Object> params) throws Exception {

        try{
            String baseUrl = "https://apis.map.qq.com/ws/geocoder/v1/?";
            String longitude = params.get("longitude").toString();
            String latitude = params.get("latitude").toString();
            String get_poi = params.get("get_poi").toString();
            String res = HttpUtils.sendGet(baseUrl + "location=" + latitude + "," + longitude
                    + "&get_poi=" + get_poi
                    + "&key=" + key);
            return JSON.parseObject(res);
        } catch (Exception e){
            throw new Exception("获取位置信息时出现错误!");
        }
    }

    @Override
    public void sendNewWorkOrder(AdWorkOrderEntity adWorkOrderEntity, String openId, Integer workTypeTitle){
        NewOrderVo newOrderVo = new NewOrderVo();
        // 标题
        String title = null;
        if (workTypeTitle.equals(Constant.WorkTypeTitle.XYCL.getValue())) {
            title = sysConfigService.getValue("NEW_WORK_ORDER_TITLE", "您好，您有新的工单需要处理");
        } else if (workTypeTitle.equals(Constant.WorkTypeTitle.QRWJ.getValue())) {
            title = sysConfigService.getValue("TO_FINISH_WORK_ORDER_TITLE", "您好，您有新的工单需要确认完结");
        } else if (workTypeTitle.equals(Constant.WorkTypeTitle.YWJ.getValue())) {
            title = sysConfigService.getValue("WORK_ORDER_FINISHED_TITLE", "您好，您的工单已完结");
        }
        newOrderVo.setTitle(title);
        // 工单编号
        newOrderVo.setWorkOrderId(adWorkOrderEntity.getId());
        // 服务类型（工单类型 0：广告采购工单 1：设备维护工单 2：送货工单 3：贴印刷广告工单）
        String workType = adWorkOrderEntity.getWorkType();
        AdWorkTypeEntity adWorkTypeEntity = adWorkTypeService.getById(workType);
        newOrderVo.setWorkType(adWorkTypeEntity.getName());
        // 下单时间
        Date time = adWorkOrderEntity.getCreateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(time);
        newOrderVo.setDate(date);
        // 客户姓名
        SysUserEntity sysUserEntity = sysUserDao.queryById(adWorkOrderEntity.getCreatorId());
        newOrderVo.setName(sysUserEntity.getRealName());
        // 客户地址
        newOrderVo.setAddress(adWorkOrderEntity.getAddress());
        // 备注
        newOrderVo.setRemark(adWorkOrderEntity.getDes());

        List<WxMpTemplateData> data = mpSendMsgService.newOrderDate(newOrderVo);

        try {
            mpSendMsgService.sendTemplateMessage(openId, Constant.TemplateId.newWorkOrder.getValue(), data, null);
//            throw new WxErrorException(WxError.fromJson(result, WxType.MP));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToManage(AdWorkOrderEntity adWorkOrderEntity) {
        List<String> parentOpenIdList = new ArrayList<>();
        // 如果工单是一个子级工单
        if (adWorkOrderEntity.getParentId() != null && !adWorkOrderEntity.getParentId().trim().equals("")) {
            // 获取最后提交完结的运维人员openId
            parentOpenIdList = this.getParentOpenId(adWorkOrderEntity.getParentId());
        }
        else {
            parentOpenIdList = this.getParentOpenId(adWorkOrderEntity.getId());
        }
        // 公众号推送消息
        if (parentOpenIdList != null) {
            for (String openId : parentOpenIdList) {
                this.sendNewWorkOrder(adWorkOrderEntity, openId, Constant.WorkTypeTitle.QRWJ.getValue());
            }
        }
    }

    @Override
    public List<String> getParentOpenId(String parentId) {
        List<String> parentOpenIdList = new ArrayList<>();
        // 父级工单
        AdWorkOrderEntity parentWorkOrder = this.getById(parentId);
        // 父级工单创建人
        String creatorId = parentWorkOrder.getCreatorId();
        // 判断工单创建人是否是运维经理
        boolean isManage = false;
        for (String role :sysUserDao.getRoleNameByUserId(creatorId)) {
            if (role.equals("运维经理")){
                isManage = true;
            }
        }
        if (isManage) {
            SysUserEntity sysUserEntity = sysUserDao.queryById(creatorId);
            parentOpenIdList.add(sysUserEntity.getMpOpenId());
        } else {
            // 所属部门
            List<String> departmentList = sysUserDepartmentDao.queryDepartmentIdList(creatorId);
            // 所属部门的所有部门经理的openId
            parentOpenIdList = sysUserDepartmentDao.selectManageByDepartment(departmentList);
        }
        return parentOpenIdList;
    }

    @Override
    public void sendFinishWorkMessageToWorker(AdWorkOrderEntity adWorkOrderEntity) {
        // 获取最后提交完结的openId
        String openId = sysUserDao.queryById(adWorkOrderEntity.getWorkerId()).getMpOpenId();
        if (openId != null) {
            // 公众号推送消息
            try {
                this.sendNewWorkOrder(adWorkOrderEntity, openId, Constant.WorkTypeTitle.YWJ.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
