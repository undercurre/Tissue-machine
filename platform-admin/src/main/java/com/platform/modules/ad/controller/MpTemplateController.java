package com.platform.modules.ad.controller;

import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.MallUserSignRecordEntity;
import com.platform.modules.ad.service.WxMpSendMsgService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.vo.MachineStatusRemindVo;
import com.platform.modules.vo.NewOrderVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("mall/mpTemplate")
@Api(tags = "MpTemplateController|公众号发送模板消息接口")
public class MpTemplateController extends AbstractController {
    @Value("${wx.pay.appId}")
    private String appId;
    @Autowired
    private WxMpSendMsgService mpSendMsgService;

    /**
     * 测试发送模板消息
     *
     *
     * @return RestResponse
     */
    @RequestMapping("/sendMessage")
//    @RequiresPermissions("mall:mpTemplate:sendMessage")
    public void sendMessage(@RequestParam String openId, @RequestParam Integer type, @RequestParam(required = false) String pagePath) {
        List<WxMpTemplateData> data = new ArrayList<>();
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        if (type == 1) {
            NewOrderVo newOrderVo = new NewOrderVo();
            newOrderVo.setTitle("您好，您有新的工单需要处理");
            newOrderVo.setWorkOrderId("20210109");
            newOrderVo.setWorkType("异常，需维修");
            newOrderVo.setDate("2021年1月9日");
            newOrderVo.setName("居居");
            newOrderVo.setAddress("佛山");
            newOrderVo.setRemark("正常");

            data = mpSendMsgService.newOrderDate(newOrderVo);
        } else if (type == 2) {
            MachineStatusRemindVo machineStatusRemindVo = new MachineStatusRemindVo();
            machineStatusRemindVo.setDate("2021年1月9日");
            machineStatusRemindVo.setMachineSn("111");
            machineStatusRemindVo.setMachineStatus("正常");
            machineStatusRemindVo.setRemark("测试成功");

            data = mpSendMsgService.machineStatusRemindDate(machineStatusRemindVo);
        }
        if (pagePath != null) {
            miniProgram.setAppid(appId);
            miniProgram.setPagePath(pagePath);
        }

        try {
            String result = mpSendMsgService.sendTemplateMessage(openId, type, data, miniProgram);
            System.out.println(result);
            throw new WxErrorException(WxError.fromJson(result, WxType.MP));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }
}
