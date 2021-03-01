package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.AdAdvertAdvertTypeService;
import com.platform.modules.ad.service.AdAdvertService;
import com.platform.modules.ad.service.AdAdvertTypeService;
import com.platform.utils.RegexUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/advert")
@Api(tags = "AppAdvertController|APP投广告接口")
public class AppAdvertController {

    @Autowired
    private AdAdvertService adAdvertService;
    @Autowired
    private AdAdvertTypeService adAdvertTypeService;
    @Autowired
    private AdAdvertAdvertTypeService adAdvertAdvertTypeService;

    /**
     * 广告类型
     */
    @GetMapping("showUnitType")
    @ApiOperation(value = "广告类型", notes = "广告类型")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse showUnitType(@LoginUser MallUserEntity loginUser) {
        String userId = loginUser.getId();
        if (StringUtils.isBlank(userId)) {
            return RestResponse.error("用户为空!");
        }

        // 筛选出未删除状态的广告类型
        List<AdAdvertTypeEntity> adAdvertTypeEntityList = adAdvertTypeService.list().stream()
                .filter(e->e.getIsDelete() != null)
                .filter(e->e.getIsDelete().equals(0))
                .collect(Collectors.toList());

        return RestResponse.success().put("data", adAdvertTypeEntityList);
    }

    /**
     * 投广告
     */
    @PostMapping("adverting")
    @ApiOperation(value = "投广告", notes = "投广告")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "name", value = "广告商姓名"),
                    @ExampleProperty(mediaType = "mobile", value = "手机号"),
                    @ExampleProperty(mediaType = "workUnitType", value = "单位性质 1：企业，2：商家，3：个人"),
                    @ExampleProperty(mediaType = "workUnitName", value = "单位名称"),
                    @ExampleProperty(mediaType = "advertType", value = "广告类型")
            }), required = true, dataType = "string")
    })
    public RestResponse adverting(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        String userId = loginUser.getId();
        if (StringUtils.isBlank(userId)) {
            return RestResponse.error("用户为空!");
        }

        String name = StringUtils.trimToNull(jsonParam.getString("name"));
        String mobile = StringUtils.trimToNull(jsonParam.getString("mobile"));
        Integer workUnitType = jsonParam.getInteger("workUnitType");
        String workUnitName = StringUtils.trimToNull(jsonParam.getString("workUnitName"));
        JSONArray advertType = jsonParam.getJSONArray("advertType");
        List<String> advertTypeList = advertType.toJavaList(String.class);

        if (StringUtils.isBlank(name)) {
            return RestResponse.error("请输入姓名");
        }
        if (StringUtils.isBlank(mobile)) {
            return RestResponse.error("请输入联系方式");
        }
        // 检验手机号是否为1开头的11位号码
        if (!RegexUtils.validateMobilePhone(mobile)) {
            return RestResponse.error("请输入正确的手机号");
        }
        if (workUnitType == null) {
            return RestResponse.error("请选择单位性质");
        } else if (!workUnitType.equals(Constant.workUnitType.GR.getValue()) && StringUtils.isBlank(workUnitName)) {
            return RestResponse.error("请输入单位名称");
        }
        if (advertTypeList.size() <= 0) {
            return RestResponse.error("请选择广告类型");
        }

        //生成uuid
        String advertId = UUID.randomUUID().toString().replaceAll("-", "");

        // 添加广告类型到中间表
        advertTypeList.forEach(e->{
            AdAdvertAdvertTypeEntity adAdvertAdvertTypeEntity = new AdAdvertAdvertTypeEntity();
            adAdvertAdvertTypeEntity.setAdvertId(advertId);
            adAdvertAdvertTypeEntity.setAdvertTypeId(e);
            adAdvertAdvertTypeService.add(adAdvertAdvertTypeEntity);
        });

        // 添加到广告表
        AdAdvertEntity adAdvertEntity = new AdAdvertEntity();
        adAdvertEntity.setId(advertId);
        adAdvertEntity.setName(name);
        adAdvertEntity.setMobile(mobile);
        adAdvertEntity.setWorkunitType(workUnitType);
        adAdvertEntity.setWorkunitName(workUnitName);
        adAdvertEntity.setStatus(Constant.advertStatus.DHF.getValue());
        adAdvertEntity.setUserId(userId);
        adAdvertEntity.setCreateTime(new Date());
        adAdvertService.add(adAdvertEntity);

        return RestResponse.success();
    }
}
