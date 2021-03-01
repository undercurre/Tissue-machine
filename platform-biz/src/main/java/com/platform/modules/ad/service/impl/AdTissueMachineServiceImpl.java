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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.AdTissueMachineDao;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.*;
import com.platform.modules.protocol.entity.TissueProtocol;
import com.platform.modules.protocol.utils.HttpPostUtil;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.vo.MachineStatusRemindVo;
import com.platform.modules.vo.RegionSortVo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 纸巾机表Service实现类
 *
 * @author zqh
 * @date 2020-09-22 11:34:22
 */
@Service("adTissueMachineService")
@Slf4j
public class AdTissueMachineServiceImpl extends ServiceImpl<AdTissueMachineDao, AdTissueMachineEntity> implements AdTissueMachineService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private AdMachineOperateService adMachineOperateService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private AdWorkOrderService adWorkOrderService;
    @Autowired
    private WxMpSendMsgService wxMpSendMsgService;
    @Autowired
    private AdGroupService adGroupService;

    @Value("${wx.map.key}")
    private String key;

    @Override
    public List<AdTissueMachineEntity> queryAll(Map<String, Object> params, SysUserEntity user) {
        Integer level = null;
        if (user.getIsStaff() == 1) {
            user.setUserId(user.getCreateUserId());
            level = sysUserDao.getUserLevel(user.getCreateUserId());
        } else {
            level = sysUserDao.getUserLevel(user.getUserId());
        }

        if (level == Constant.roleLevel.ZDL.getValue()) {
            return this.list();
        } else {
            return this.list(new QueryWrapper<AdTissueMachineEntity>().eq("AGENT_ID", user.getUserId()));
        }
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Integer level = null;
        Page<AdTissueMachineEntity> page = new Query<AdTissueMachineEntity>(params).getPage();
        if (user.getIsStaff() == 1) {
            level = sysUserDao.getUserLevel(user.getCreateUserId());
            user.setUserId(user.getCreateUserId());
        } else {
            level =  sysUserDao.getUserLevel(user.getUserId());
        }
        BigDecimal simMonthData = new BigDecimal(sysConfigService.getValue("SIM_MONTH_DATA"));
        params.put("simMonthData",simMonthData);
        if (level == Constant.roleLevel.ZDL.getValue()) {
            List<AdTissueMachineEntity> adTissueMachineEntityList = baseMapper.selectAdTissueMachinePage(page, params);
            return page.setRecords(adTissueMachineEntityList);
        } else {
            List<AdTissueMachineEntity> list = baseMapper.selectAdTissueMachinePage(page, params);
            list = list.stream().filter(e->StringUtils.isNotBlank(e.getAgentId()))
                                .filter(e->e.getAgentId().equals(user.getUserId())).collect(Collectors.toList());

            return page.setRecords(list);
        }
    }

    @Override
    public AdTissueMachineEntity getById(String id){
        return baseMapper.getById(id);
    }

    @Override
    public AdTissueMachineEntity getBySn(String sn){
        return baseMapper.getBySn(sn);
    }

    @Override
    @Transactional
    public boolean add(AdTissueMachineEntity adTissueMachine) throws Exception {

        // 给机柜设置默认语音
        AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
        adMachineOperateEntity.setMachineId(adTissueMachine.getId());
        adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.VOICE.getValue());
        adMachineOperateEntity.setCreateTime(new Date());
        adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
        adMachineOperateEntity.setContent(sysConfigService.getValue("DEFAULT_VOICE"));
        adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
        adMachineOperateService.save(adMachineOperateEntity);

        // 设置机柜行政区代码
        Map<String, Object> params = new HashMap<>();
        params.put("longitude", adTissueMachine.getLongitude());
        params.put("latitude", adTissueMachine.getLatitude());
        params.put("get_poi", 1);
        JSONObject jsonObject = adWorkOrderService.getAddress(params);
        JSONObject adInfo = jsonObject.getJSONObject("result").getJSONObject("ad_info");
        int adCode = Integer.parseInt(adInfo.getString("adcode"));
        adTissueMachine.setProvince(adInfo.getString("province"));
        adTissueMachine.setCity(adInfo.getString("city"));
        adTissueMachine.setDistrict(adInfo.getString("district"));
        adTissueMachine.setProvinceCode((adCode / 10000) * 10000);
        adTissueMachine.setCityCode((adCode / 100) * 100);
        adTissueMachine.setDistrictCode(adCode);
        return this.save(adTissueMachine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(AdTissueMachineEntity adTissueMachine){
        AdTissueMachineEntity oldAdTissueMachine = this.getById(adTissueMachine.getId());
        if (!oldAdTissueMachine.getLongitude().equals(adTissueMachine.getLongitude()) ||
                !oldAdTissueMachine.getLatitude().equals(adTissueMachine.getLatitude())) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("longitude", adTissueMachine.getLongitude());
                params.put("latitude", adTissueMachine.getLatitude());
                params.put("get_poi", 1);
                JSONObject jsonObject = adWorkOrderService.getAddress(params);
                int adCode = Integer.parseInt(jsonObject.getJSONObject("result").getJSONObject("ad_info").getString("adcode"));
                adTissueMachine.setProvinceCode((adCode / 10000) * 10000);
                adTissueMachine.setCityCode((adCode / 100) * 100);
                adTissueMachine.setDistrictCode(adCode);
            } catch (Exception e) {
                return false;
            }
        }
        return this.updateById(adTissueMachine);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIsDelete(AdTissueMachineEntity adTissueMachineEntity){
        baseMapper.updateIsDelete(adTissueMachineEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIsShowLogo(AdTissueMachineEntity adTissueMachineEntity) {
        baseMapper.updateIsShowLogo(adTissueMachineEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(AdTissueMachineEntity adTissueMachineEntity){
        baseMapper.updateStatus(adTissueMachineEntity);
        return true;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIsOpenLocate(AdTissueMachineEntity adTissueMachine) {
        baseMapper.updateIsOpenLocate(adTissueMachine);
        return true;
    }

    @Override
    public List<AdTissueMachineEntity> queryNoVoiceList() {
        return baseMapper.queryNoVoiceList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDetailById(AdTissueMachineEntity adTissueMachine){
        AdTissueMachineEntity oldAdTissueMachine = this.getById(adTissueMachine.getId());
        if (!oldAdTissueMachine.getLongitude().equals(adTissueMachine.getLongitude()) ||
                !oldAdTissueMachine.getLatitude().equals(adTissueMachine.getLatitude())) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("longitude", adTissueMachine.getLongitude());
                params.put("latitude", adTissueMachine.getLatitude());
                params.put("get_poi", 1);
                JSONObject jsonObject = adWorkOrderService.getAddress(params);
                JSONObject adInfo = jsonObject.getJSONObject("result").getJSONObject("ad_info");
                int adCode = Integer.parseInt(adInfo.getString("adcode"));
                adTissueMachine.setProvince(adInfo.getString("province"));
                adTissueMachine.setCity(adInfo.getString("city"));
                adTissueMachine.setDistrict(adInfo.getString("district"));
                adTissueMachine.setProvinceCode((adCode / 10000) * 10000);
                adTissueMachine.setCityCode((adCode / 100) * 100);
                adTissueMachine.setDistrictCode(adCode);
            } catch (Exception e) {
                return false;
            }
        }
        return baseMapper.updateDetailById(adTissueMachine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TissueProtocol pushGoods(String sn) {
        String uri = "http://www.nolan.net.cn:10023/ls/post/";
        Map<String,String> map = new HashMap<>();
        map.put("ID",sn);
        map.put("TP","1");
        map.put("CM","04");
        map.put("KY","2222");
        map.put("DATA","1");
        ArrayList<Map<String,String>> list = new ArrayList<>();
        list.add(map);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        uri,
                        HttpPostUtil.generatePostJson(list),
                        String.class
                );
        String s = apiResponse.getBody();
        List<TissueProtocol> res = JSONObject.parseArray(s, TissueProtocol.class);
        return res.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdMachineOperateEntity restart(String id) {
        AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
        adMachineOperateEntity.setMachineId(id);
        adMachineOperateEntity.setCreateTime(new Date());
        adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.RESTART.getValue());
        adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
        adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
        adMachineOperateService.save(adMachineOperateEntity);
        return adMachineOperateEntity;
    }

    @Override
    public Page queryMachineListBySupId(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdTissueMachineEntity> page = new Query<AdTissueMachineEntity>(params).getPage();
        List<AdTissueMachineEntity> list = baseMapper.queryMachineListBySupId(page, params);
        return page.setRecords(list);
    }

    @Override
    public List<RegionSortVo> groupByTissueMachineInCity () {
        return baseMapper.groupByTissueMachineInCity();
    }

    @Override
    public List<RegionSortVo> groupByTissueMachineInDistrict () {
        return baseMapper.groupByTissueMachineInDistrict();
    }

    /**
     * 根据设备分组通过公众号发送消息给负责人
     */
    @Override

//    @Transactional(rollbackFor = Exception.class)
    public Boolean sendMessage(AdTissueMachineEntity adTissueMachineEntity,Integer Status)  {
        String openIdByMachineId = adGroupService.getGroupOpenIdByMachineId(adTissueMachineEntity.getId());
        //如果设备有负责人
        if(StringUtils.isNotBlank(openIdByMachineId)) {
            MachineStatusRemindVo machineStatusRemindVo = new MachineStatusRemindVo();
            machineStatusRemindVo.setMachineSn(adTissueMachineEntity.getSn());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            machineStatusRemindVo.setDate(date);
            machineStatusRemindVo.setRemark(adTissueMachineEntity.getAddress());
            switch (Status) {
                case 2:
                    machineStatusRemindVo.setMachineStatus("异常");
                    break;
                case 3:
                    machineStatusRemindVo.setMachineStatus("出纸异常");
                    break;
                case 4:
                    machineStatusRemindVo.setMachineStatus("位置异常");
                    break;
                case 5:
                    machineStatusRemindVo.setMachineStatus("纸巾不足");
                    break;
                case 6:
                    machineStatusRemindVo.setMachineStatus("电量不足");
                    break;
                case 7:
                    machineStatusRemindVo.setMachineStatus("语音修改异常");
                    break;
                case 8:
                    machineStatusRemindVo.setMachineStatus("设备离线");
                    break;
            }
            List<WxMpTemplateData> data = wxMpSendMsgService.machineStatusRemindDate(machineStatusRemindVo);
            try {
                wxMpSendMsgService.sendTemplateMessage(openIdByMachineId, Constant.TemplateId.machineStatus.getValue(), data, null);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
