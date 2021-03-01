package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdScanEntity;
import com.platform.modules.ad.entity.AdUserActivityEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdActivityService;
import com.platform.modules.ad.service.AdScanService;
import com.platform.modules.ad.service.AdUserActivityService;
import com.platform.modules.ad.service.MallUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/app/scan")
@Api(tags = "AppScanAdController|APP扫描广告接口")
public class AppScanAdController {
    @Autowired
    AdScanService scanService;
    /**
     * 获取扫码广告
     */
    @GetMapping("/getScan")
    @IgnoreAuth
    @ApiOperation(value = "获取扫码广告", notes = "获取扫码广告")
    public RestResponse getScan() {

        List<AdScanEntity> list = scanService.list(new QueryWrapper<AdScanEntity>().eq("AVAIL", 1));

        return RestResponse.success().put("data", list.get(new Random().nextInt(list.size())));
    }



}
