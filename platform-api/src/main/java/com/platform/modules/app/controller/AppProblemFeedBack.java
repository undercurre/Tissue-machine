package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdIssueEntity;
import com.platform.modules.ad.entity.AdIssueImageEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdIssueImageService;
import com.platform.modules.ad.service.AdIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/app/problemFeedBack")
@Api(tags = "AppProblemFeedBackController|APP纸巾机问题反馈接口")
public class AppProblemFeedBack {

    @Autowired
    private AdIssueService adIssueService;

    @Autowired
    private AdIssueImageService adIssueImageService;

    @PostMapping("submitProblem")
    @ApiOperation(value = "问题反馈上传", notes = "问题反馈上传接口")
    private RestResponse submitProblemFeedBack(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonObject){

        AdIssueEntity adIssueEntity = new AdIssueEntity();
        adIssueEntity.setUserId(loginUser.getId());
        adIssueEntity.setMachineId(jsonObject.getString("machineId"));
        adIssueEntity.setContent(jsonObject.getString("introduce"));
        adIssueEntity.setCreateTime(new Date());
        adIssueEntity.setIsDelete(0);
        System.out.println(adIssueEntity.toString());
        adIssueService.add(adIssueEntity);

        JSONArray picturePath = jsonObject.getJSONArray("picturePath");
        if(picturePath != null){
            for(Object pic: picturePath) {
                AdIssueImageEntity adIssueImageEntity = new AdIssueImageEntity();
                adIssueImageEntity.setIssueId(adIssueEntity.getId());
                adIssueImageEntity.setImageUrl((String) pic);
                adIssueImageEntity.setCreateTime(new Date());
                adIssueImageService.add(adIssueImageEntity);
            }
        }
        return RestResponse.success();
    }
}
