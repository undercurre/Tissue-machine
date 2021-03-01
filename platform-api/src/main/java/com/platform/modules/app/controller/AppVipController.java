package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdAboutEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdAboutService;
import com.platform.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/app/vip")
@Api(tags = "AppVipController|VIP相关信息接口")
public class AppVipController {

    @Autowired
    private SysConfigService sysConfigService;

    @GetMapping("info")
    @ApiOperation(value = "VIP价格相关信息", notes = "VIP价格相关信息")
    public RestResponse info(@LoginUser MallUserEntity mallUserEntity){
        return RestResponse.success()
                .put("price", sysConfigService.getValue("VIP_PRICE"))
                .put("number", sysConfigService.getValue("VIP_TISSUE_NUMBER"))
                .put("userLevel", mallUserEntity.getUserLevelId());
    }
}
