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
package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.sys.entity.SysConfigEntity;
import com.platform.modules.sys.service.SysConfigService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zqh
 */
@RestController
@RequestMapping("/app/user")
@Api(tags = "AppUserController|APP用户接口")
public class AppUserController {

    @Autowired
    private MallAccountLogService accountLogService;
    @Autowired
    private MallUserBankCardService mallUserBankCardService;
    @Autowired
    private MallBankTypeService mallBankTypeService;
    @Autowired
    private MallUserLevelService userLevelService;
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private MallUserService userService;
    /**
     * 根据token获取当前登录用户信息
     *
     * @param user user
     * @return RestResponse
     */
    @GetMapping("/userInfo")
    @ApiOperation(value = "获取登录用户信息", notes = "根据token获取用户信息,包括用户今天剩余的免费数及购买数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse userInfo(@LoginUser MallUserEntity user) {
        MallUserLevelEntity levelEntity = userLevelService.getById(user.getUserLevelId());
        if (null != levelEntity) {
            user.setUserLevelName(levelEntity.getName());
        } else {
            user.setUserLevelName("");
        }

        Map<String,Integer> map = adOrderService.getUserRemainNumber(user);
        user.setRemainBuyToday(map.get("remainBuyNumber"));
        user.setRemainFreeToday(map.get("remainFreeNumber"));
        user.setUserRemainBuyToday(map.get("userRemainBuyToday"));
        user.setUserRemainFreeToday(map.get("userRemainFreeToday"));

        return RestResponse.success().put("data", user);
    }


    /**
     * 当前登录用户账户余额变动记录
     *
     * @param user user
     * @return RestResponse
     */
    @GetMapping("accountLogList")
    @ApiOperation(value = "当前登录用户收藏列表", notes = "当前登录用户收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse accountLogList(@LoginUser MallUserEntity user) {
        List<MallAccountLogEntity> accountLogEntityList = accountLogService.list(
                new QueryWrapper<MallAccountLogEntity>().eq("USER_ID", user.getId()).ne("TYPE", 0));
        return RestResponse.success().put("data", accountLogEntityList);
    }

    /**
     * 当前登录用户银行卡列表
     *
     * @param user user
     * @return RestResponse
     */
    @GetMapping("/getBankCard")
    @ApiOperation(value = "当前登录用户银行卡列表", notes = "当前登录用户银行卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse getBankCard(@LoginUser MallUserEntity user) {
        String userId = user.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }
        List<MallUserBankCardEntity> list = mallUserBankCardService.getCardList(
                new QueryWrapper<MallUserBankCardEntity>()
                        .eq("USER_ID", userId)
                        .eq("CARD_STATUS", Constant.CardStatus.YBD.getValue())
        );

        list.forEach(r->{
            String cardNumber = r.getCardNumber();
            int length = cardNumber.length();
            r.setCardNumber(cardNumber.substring(length - 4));
        });

        return RestResponse.success().put("data", list);
    }

    /**
     * 绑定银行卡
     *
     * @param user user
     * @return RestResponse
     */
    @PostMapping("bindingCard")
    @ApiOperation(value = "绑定银行卡", notes = "绑定银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "cardName", value = "收款人姓名"),
                    @ExampleProperty(mediaType = "cardNumber", value = "银行卡号"),
                    @ExampleProperty(mediaType = "cardType", value = "卡类型"),
                    @ExampleProperty(mediaType = "bankTypeId", value = "银行类型ID"),
            }), required = true, dataType = "string")
    })
    public RestResponse bindingCard(@LoginUser MallUserEntity user, @RequestBody JSONObject jsonParam) {

        String userId = user.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }

        String cardName = StringUtils.trimToNull(jsonParam.getString("cardName"));
        String cardNumber = StringUtils.trimToNull(jsonParam.getString("cardNumber"));
        String cardType = StringUtils.trimToNull(jsonParam.getString("cardType"));
        String bankTypeId = StringUtils.trimToNull(jsonParam.getString("bankTypeId"));
        if (cardName == null || cardNumber == null || bankTypeId == null)
            throw new BusinessException("信息不全！");
        // 先前是否绑定过
        if (null != mallUserBankCardService.getOne(new QueryWrapper<MallUserBankCardEntity>().eq("CARD_NUMBER", cardNumber)))
            throw new BusinessException("已绑定当前卡！");


        MallUserBankCardEntity mallUserBankCardEntity = new MallUserBankCardEntity();
        mallUserBankCardEntity.setUserId(userId);
        mallUserBankCardEntity.setCardName(cardName);
        mallUserBankCardEntity.setCardNumber(cardNumber);
        mallUserBankCardEntity.setCardType(cardType);
        mallUserBankCardEntity.setBankTypeId(Integer.valueOf(bankTypeId));
        mallUserBankCardEntity.setCardStatus(Constant.CardStatus.YBD.getValue());
        mallUserBankCardService.add(mallUserBankCardEntity);

        return RestResponse.success();
    }

    /**
     * 解绑银行卡
     *
     * @param user user
     * @return RestResponse
     */
    @PostMapping("unbindingCard")
    @ApiOperation(value = "解绑银行卡", notes = "解绑银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "id", value = "ID"),
            }), required = true, dataType = "string")
    })
    public RestResponse unbindingCard(@LoginUser MallUserEntity user, @RequestBody JSONObject jsonParam) {
        String userId = user.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }
        // 绑定的银行卡列id
        String id = StringUtils.trimToNull(jsonParam.getString("id"));
        if (id == null)
            throw new BusinessException("解绑信息有误，请稍后再尝试！");

        MallUserBankCardEntity cardEntity = mallUserBankCardService.getById(id);
        if (cardEntity == null) {
            throw new BusinessException("解绑信息有误，请稍后再尝试！");
        }
        if (!cardEntity.getUserId().equals(userId)) {
            throw new BusinessException("越权，不可解绑他人银行卡");
        }

        cardEntity.setCardStatus(Constant.CardStatus.YJB.getValue());
        mallUserBankCardService.update(cardEntity);

        return RestResponse.success();
    }

    /**
     * 获取银行卡类型表
     */
    @IgnoreAuth
    @GetMapping("bankTypeList")
    @ApiOperation(value = "获取银行卡类型表", notes = "获取银行卡类型表")
    public RestResponse bankTypeList() {
        List<MallBankTypeEntity> list = mallBankTypeService.list(
                new QueryWrapper<MallBankTypeEntity>());
        return RestResponse.success().put("data", list);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("modifyInfo")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "nickname", value = "昵称"),
                    @ExampleProperty(mediaType = "userName", value = "用户名")
            }), required = true, dataType = "string")
    })
    public RestResponse modifyInfo(@LoginUser MallUserEntity user, @RequestBody JSONObject jsonParam) {
        String userId = user.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }

        String nickname = StringUtils.trimToNull(jsonParam.getString("nickname"));
        String userName = StringUtils.trimToNull(jsonParam.getString("userName"));

        if (nickname == null || userName == null)
            throw new BusinessException("信息不全！");
        System.out.println(nickname);
        System.out.println(userName);

        user.setUserName(userName);
        user.setNickname(nickname);

        userService.update(user);

        return RestResponse.success();
    }
}
