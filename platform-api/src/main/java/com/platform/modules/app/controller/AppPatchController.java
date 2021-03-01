package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdPatchEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdPatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/patch")
@Api(tags = "AppPatchController|广告信息接口")
public class AppPatchController {
    @Autowired
    private AdPatchService adPatchService;

    /**
     * 获取机柜贴片详情信息
     *
     * @return RestResponse
     */
    @IgnoreAuth
    @GetMapping("/queryTissueMachinePatch")
    @ApiOperation(value = "获取机柜贴片", notes = "获取机柜贴片")
    public RestResponse queryTissueMachinePatch() {
        List<AdPatchEntity> adPatch = adPatchService.list(new QueryWrapper<AdPatchEntity>()
                .eq("TYPE", Constant.PatchType.JGTP.getValue())
                .last("LIMIT 0,1")
                .eq("AVAIL",1)
        );
        return RestResponse.success().put("data", adPatch);
    }

    /**
     * 查询首页轮播图
     *
     * @return RestResponse
     */
    @IgnoreAuth
    @RequestMapping("/getOrderPatch")
    @ApiOperation(value = "查询首页轮播图", notes = "查询首页轮播图")
    public RestResponse getOrderPatch() {
        List<AdPatchEntity> adPatchEntityList = adPatchService.list(new QueryWrapper<AdPatchEntity>().eq("TYPE", 5));

        return RestResponse.success().put("data", adPatchEntityList);
    }

    /**
     * 查询首页轮播图
     *
     * @return RestResponse
     */
    @IgnoreAuth
    @RequestMapping("/getPersonCenterPatch")
    @ApiOperation(value = "查询首页轮播图", notes = "查询首页轮播图")
    public RestResponse getPersonCenterPatch() {
        List<AdPatchEntity> adPatchEntityList = adPatchService.list(new QueryWrapper<AdPatchEntity>().eq("TYPE", 3).last("limit 1"));

        return RestResponse.success().put("data", adPatchEntityList);
    }
}
