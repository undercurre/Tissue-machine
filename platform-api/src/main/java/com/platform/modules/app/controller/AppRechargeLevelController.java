package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.entity.AdOrderTissueEntity;
import com.platform.modules.ad.entity.AdRechargeLevelEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdRechargeLevelService;
import com.platform.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/rechargeLevel")
@Api(tags = "AppIndexController|APP首页接口")
public class AppRechargeLevelController extends AppBaseController {
    @Autowired
    private AdRechargeLevelService adRechargeLevelService;
    @Autowired
    private SysConfigService sysConfigService;
    /**
     * 获取充值档位
     */
    @GetMapping("list")
    @ApiOperation(value = "订单列表", notes = "根据用户ID获取订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse list(@LoginUser MallUserEntity loginUser) {
        List<AdRechargeLevelEntity> list = adRechargeLevelService.list(new QueryWrapper<AdRechargeLevelEntity>().orderByAsc("BALANCE"));
        return RestResponse.success().put("data",list);
    }

    /**
     * 获取系统设置参数
     */
    @GetMapping("getCanYue")
    @ApiOperation(value = "获取系统是否开通余额充值设置参数", notes = "获取系统是否开通余额充值设置参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse getCanYue(@LoginUser MallUserEntity loginUser) {
        String canBalanceDeposit = sysConfigService.getValue("CAN_BALANCE_DEPOSIT");
        return RestResponse.success().put("data",Integer.parseInt(canBalanceDeposit));
    }
}
