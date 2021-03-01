package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/app/activity")
@Api(tags = "AppActivityController|APP活动接口")
public class AppActivityController {
    @Autowired
    AdActivityService adActivityService;
    @Autowired
    AdUserActivityService adUserActivityService;
    @Autowired
    MallUserService mallUserService;
//    /**
//     * 获取用户活动列表
//     *
//     * @param user user
//     * @return RestResponse
//     */
//    @GetMapping("/list")
//    @ApiOperation(value = "获取用户活动列表", notes = "获取用户活动列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
//    })
//    public RestResponse getTissueMachine(@LoginUser MallUserEntity user) {
//
//        List<AdUserActivityEntity> activityList = new ArrayList<>();
//        String userId = user.getId();
//
//        adActivityService.list()
//                .stream()
//                .filter(
//                        a -> a.getIsDelete() == 0 || !(a.getExpireTime() != null && a.getExpireTime().before(new Date())))
//                .forEach(activity -> {
//                    String activityId = activity.getId();
//                            AdUserActivityEntity v = adUserActivityService.getOne(
//                            new QueryWrapper<AdUserActivityEntity>()
//                                    .eq("ACTIVITY_ID", activityId)
//                                    .eq("USER_ID", userId)
//                    );
//                    if (v == null) {
//                        v = new AdUserActivityEntity();
//                        v.setActivityId(activityId);
//                        v.setUserId(userId);
//                        v.setJoin(false); // 是否有参加该活动
//                    }
//                    v.setConfine(activity.getConfine() == 1);
//                    v.setCount(activity.getCount());
//                    v.setType(activity.getType());
//                    v.setRemark(activity.getRemark());
//                    v.setDayCount(adUserActivityService.getDayCount(v.getActivityId(), v.getFrequency(), userId));
//
//                    activityList.add(v);
//                });
//
//        return RestResponse.success().put("data", activityList);
//    }
//

    @GetMapping("modifyUserIntegral")
    @ApiOperation(value = "修改用户积分", notes = "修改用户积分")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "integral", value = "积分", required = true, dataType = "int", example = "1")
    })
    public RestResponse modifyUserIntegral(@LoginUser MallUserEntity user, Integer integral) {
        mallUserService.modifyUserIntegral(user.getId(), BigDecimal.valueOf(integral));
        return RestResponse.success().put("data", integral);
    }
}
