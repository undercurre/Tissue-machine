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
package com.platform.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.builder.TextBuilder;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.modules.ad.entity.AdMpUserEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdMpUserService;
import com.platform.modules.ad.service.MallUserService;
import com.platform.modules.wx.entity.WxMpConfigEntity;
import com.platform.modules.wx.service.WxMpConfigService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 微信公众号消息处理（用户关注）
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {
    @Autowired
    private MallUserService userService;
    @Autowired
    private WxMpConfigService mpConfigService;
    @Autowired
    private AdMpUserService adMpUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser());
            String openId = userWxInfo.getOpenId();
            if (userWxInfo != null) {
                this.logger.info("用户: " + userWxInfo.toString());
                AdMpUserEntity one = adMpUserService.getOne(new QueryWrapper<AdMpUserEntity>().eq("OPEN_ID", openId));
                if (one == null) {
                    AdMpUserEntity adMpUserEntity = new AdMpUserEntity();
                    adMpUserEntity.setNickname(userWxInfo.getNickname());
                    adMpUserEntity.setOpenId(openId);
                    adMpUserEntity.setSex(userWxInfo.getSex());
                    adMpUserService.save(adMpUserEntity);
                }
            }
        } catch (WxErrorException e) {
            this.logger.error(e.getLocalizedMessage());
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            WxMpConfigEntity mpConfigEntity = mpConfigService.getOne(new QueryWrapper<WxMpConfigEntity>().eq("APP_ID", weixinService.getWxMpConfigStorage().getAppId()));
            return new TextBuilder().build(mpConfigEntity.getContent(), wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {

        return null;
    }

}
