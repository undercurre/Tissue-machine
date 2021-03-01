package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.dao.AdMessageDao;
import com.platform.modules.ad.entity.AdMessageEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdMessageService;
import com.platform.modules.ad.service.MallUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/message")
@Api(tags = "AppMessageController|APP纸巾机消息中心接口")
public class AppMessageController {

    @Autowired
    private AdMessageService adMessageService;

    @GetMapping("getMessageNumber")
    @ApiOperation(value = "消息列表数量", notes = "根据用户ID获取消息列表数量")
    public RestResponse getMessageNumber(@LoginUser MallUserEntity loginUser){

        List<AdMessageEntity> adMessageEntityList = adMessageService.list(new QueryWrapper<AdMessageEntity>().eq("USER_ID", loginUser.getId()).eq("HAD_READ", "0"));

        return RestResponse.success().put("data", adMessageEntityList.size());
    }

    @GetMapping("getAll")
    @ApiOperation(value = "消息列表", notes = "根据用户ID获取消息列表")
    public RestResponse getAllMessage(@LoginUser MallUserEntity loginUser){

//        List<AdMessageEntity> adMessageEntityList = adMessageService.list(new QueryWrapper<AdMessageEntity>().eq("USER_ID", loginUser.getId()).orderByDesc("CREATE_TIME"));
        List<AdMessageEntity> adMessageEntityList = adMessageService.getAllList(loginUser);

        System.out.println(adMessageEntityList);

        // 将未读消息设置为已读
        adMessageService.setHadReadByIds(loginUser.getId());

        return RestResponse.success().put("data", adMessageEntityList);
    }
}
