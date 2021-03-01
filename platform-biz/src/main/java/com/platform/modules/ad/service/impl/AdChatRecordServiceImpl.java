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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdChatRecordDao;
import com.platform.modules.ad.entity.AdChatRecordEntity;
import com.platform.modules.ad.entity.AdChatWorkerEntity;
import com.platform.modules.ad.service.AdChatRecordService;
import com.platform.modules.ad.service.AdChatWorkerService;
import com.platform.modules.vo.AdChatRecordVo;
import com.platform.modules.vo.AdChatWorkerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 客服聊天记录表Service实现类
 *
 * @author zqh, dxd
 * @date 2020-10-28 14:14:46
 */
@Service("adChatRecordService")
public class AdChatRecordServiceImpl extends ServiceImpl<AdChatRecordDao, AdChatRecordEntity> implements AdChatRecordService {

    @Autowired
    private AdChatWorkerService adChatWorkerService;

    @Override
    public List<AdChatRecordEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
//        params.put("sidx", "T.id");
//        params.put("asc", false);
        Page<AdChatRecordEntity> page = new Query<AdChatRecordEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdChatRecordPage(page, params));
    }

    @Override
    public boolean add(AdChatRecordEntity adChatRecord) {
        return this.save(adChatRecord);
    }

    @Override
    public boolean update(AdChatRecordEntity adChatRecord) {
        return this.updateById(adChatRecord);
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

//    @Override
//    public Map<String, Object> getMsgList() {
//
//        List<AdChatWorkerEntity> adChatWorkerEntityList = this.getChatWorkerList();
//
//        String result = null;
//        try {
//            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8531bbbbe17790a0&secret=09f6024d0606da28c09535e69f0bb339");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        String token = jsonObject.getString("access_token");
//
//        JSONObject params = new JSONObject();
//        params.put("starttime", 1603814400);
//        params.put("endtime", 1603900800);
//        params.put("msgid", 1);
//        params.put("number", 10000);
//
//        try {
//            result = HttpUtils.sendPost("https://api.weixin.qq.com/customservice/msgrecord/getmsglist?access_token=" + token,
//                    params.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        jsonObject = JSON.parseObject(result);
//        List<AdChatRecordVo> adChatRecordVoList = JSON.parseArray(jsonObject.getString("recordlist"), AdChatRecordVo.class);
//        List<AdChatRecordEntity> adChatRecordEntityList = new ArrayList<>();
//        for (AdChatRecordVo vo : adChatRecordVoList) {
//            AdChatRecordEntity adChatRecordEntity = new AdChatRecordEntity();
//            adChatRecordEntity.setOpenId(vo.getOpenid());
//            adChatRecordEntity.setOperCode(vo.getOpercode());
//            adChatRecordEntity.setText(vo.getText());
//            Date date = new Date(vo.getTime());
//            adChatRecordEntity.setCreatedTime(date);
//            adChatRecordEntity.setWorkerId(vo.getWorker());
//            this.add(adChatRecordEntity);
//            adChatRecordEntityList.add(adChatRecordEntity);
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("chatRecordList", adChatRecordEntityList);
//        map.put("chatWorkerList", adChatWorkerEntityList);
//        System.out.println(adChatRecordEntityList);
//        System.out.println(adChatWorkerEntityList);
//        return map;
//    }
//
//    public List<AdChatWorkerEntity> getChatWorkerList () {
//        String result = null;
//        try {
//            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8531bbbbe17790a0&secret=09f6024d0606da28c09535e69f0bb339");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        String token = jsonObject.getString("access_token");
//
//        try {
//            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        jsonObject = JSONObject.parseObject(result);
//        List<AdChatWorkerVo> adChatWorkerVoList = JSON.parseArray(jsonObject.getString("kf_list"), AdChatWorkerVo.class);
//        List<AdChatWorkerEntity> adChatWorkEntityList = new ArrayList<>();
//        for (AdChatWorkerVo vo : adChatWorkerVoList) {
//            AdChatWorkerEntity adChatWorkerEntity = new AdChatWorkerEntity();
//            adChatWorkerEntity.setWorkerId(vo.getKf_account());
//            adChatWorkerEntity.setNickname(vo.getKf_nick());
//            adChatWorkEntityList.add(adChatWorkerEntity);
//        }
//
//        Map<String, Integer> map = new HashMap<>();
//        for(AdChatWorkerEntity ad:adChatWorkerService.list()) {
//            map.put(ad.getWorkerId(), 0);
//        }
//
//        for(AdChatWorkerEntity ad: adChatWorkEntityList) {
//            if(map.get(ad.getWorkerId()) != null){
//                adChatWorkerService.add(ad);
//            } else {
//                adChatWorkerService.update(ad);
//            }
//        }
//
//
//        return adChatWorkEntityList;
//    }
}
