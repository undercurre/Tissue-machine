package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdAboutEntity;
import com.platform.modules.ad.service.AdAboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/app/about")
@Api(tags = "AppAboutController|APP纸巾机关于我们信息接口")
public class AppAboutController {

    @Autowired
    private AdAboutService adAboutService;

    @GetMapping("getAbout")
    @IgnoreAuth
    @ApiOperation(value = "关于我们信息", notes = "获取关于我们的相关信息")
    public RestResponse quertAll(){
        List<AdAboutEntity> adAboutList = adAboutService.queryAll(new HashMap<>());
        AdAboutEntity adAbout = adAboutList.get(0);
        return RestResponse.success().put("data", adAbout);
    }
}
