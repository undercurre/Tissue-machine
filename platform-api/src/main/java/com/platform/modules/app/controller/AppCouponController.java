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
import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.entity.AdCouponEntity;
import com.platform.modules.ad.entity.AdUserCouponEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdCouponService;
import com.platform.modules.ad.service.AdUserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gjw
 */
@Slf4j
@RestController
@RequestMapping("/app/coupon")
@Api(tags = "AppCouponController|优惠券接口")
public class AppCouponController extends AppBaseController {
    @Autowired
    AdCouponService adCouponService;
    @Autowired
    AdUserCouponService adUserCouponService;

    /**
     * 用户优惠券列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "用户优惠券列表", notes = "用户优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态", example = "1", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "agentId", value = "机柜SN码", dataType = "string")
    })
    public RestResponse list(@LoginUser MallUserEntity loginUser, @RequestParam Map<String, Object> params) {
        String userId = loginUser.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }
        Integer status = Integer.parseInt((String) params.get("status"));
        List<AdUserCouponEntity> list = adUserCouponService.selectAllByStatus(userId, status);
        list.forEach(e -> {
            e.setBeginTime(e.getBeginTime().split(" ")[0]);
            e.setEndTime(e.getEndTime().split(" ")[0]);
        });

        return RestResponse.success().put("data", list).put("number", list.size());
    }

    /**
     * 用户优惠券列表
     */
    @GetMapping("/getCouponNumber")
    @ApiOperation(value = "用户优惠券列表", notes = "用户优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse getCouponNumber(@LoginUser MallUserEntity loginUser) {
        String userId = loginUser.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }
        List<Map<String, Object>> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> resMap = new HashMap<>();
            if (i == 0) {
                resMap.put("name", "未使用");
            } else if (i==1) {
                resMap.put("name", "已使用");
            } else {
                resMap.put("name", "已过期");
            }
            resMap.put("number", adUserCouponService.list(new QueryWrapper<AdUserCouponEntity>().eq("USER_ID", userId).eq("STATUS", i)).size());
            res.add(resMap);
        }
        return RestResponse.success().put("data", res);
    }

//    /**
//     * 用户优惠券列表
//     */
//    @GetMapping("/getAvailableCoupon")
//    @ApiOperation(value = "用户优惠券列表", notes = "用户优惠券列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
//    })
//    public RestResponse getAvailableCoupon(@LoginUser MallUserEntity loginUser) {
//        String userId = loginUser.getId();
//        if (StringUtils.isBlank(userId)) {
//            throw new BusinessException("用户为空!");
//        }
//        List<AdUserCouponEntity> list = adUserCouponService.list(new QueryWrapper<AdUserCouponEntity>().eq("USER_ID", userId).eq("STATUS", Constant.CouponStatus.WSY.getValue()));
//        return RestResponse.success().put("data", list).put("number", list.size());
//    }

}
