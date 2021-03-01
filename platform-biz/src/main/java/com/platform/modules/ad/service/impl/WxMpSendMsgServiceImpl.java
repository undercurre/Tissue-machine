package com.platform.modules.ad.service.impl;



import com.google.gson.JsonParser;
import com.platform.modules.ad.service.WxMpSendMsgService;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.vo.MachineStatusRemindVo;
import com.platform.modules.vo.NewOrderVo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 模板消息接口
 *
 */
@PropertySource("classpath:application.properties")
@Service("WxMpSendMsgService")
public class WxMpSendMsgServiceImpl implements WxMpSendMsgService {

    @Value("${templateId.newWorkOrder}")
    private String newWorkOrder;
    @Value("${templateId.machineStatusRemind}")
    private String machineStatusRemind;
    @Autowired
    private WxMpTemplateMsgService wxMpTemplateMsgService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private WxMpService wxMpService;

    private static final JsonParser JSON_PARSER = new JsonParser();

    @Override
    public String sendTemplateMessage(String openId, Integer type, List<WxMpTemplateData> data, @RequestParam(required = false) WxMpTemplateMessage.MiniProgram miniPrograms) throws WxErrorException {
        WxMpTemplateMessage var1 = new WxMpTemplateMessage();
        // 发送用户
        var1.setToUser(openId);
        // 模板类型(1：新工单提醒，2：智能设备状态提醒)
        switch (type) {
            case 1:
                var1.setTemplateId(newWorkOrder);
                break;
            case 2:
                var1.setTemplateId(machineStatusRemind);
                break;
        }
        // 发送内容
        var1.setData(data);
        // 跳转小程序链接
        var1.setMiniProgram(miniPrograms);
        // 发送
        String sendResult = wxMpTemplateMsgService.sendTemplateMsg(var1);
        return sendResult;
    }

    @Override
    public List<WxMpTemplateData> newOrderDate(NewOrderVo newOrderVo) {
        List<WxMpTemplateData> wxMpTemplateDataList = new ArrayList<>();
        // 标题
        WxMpTemplateData first = this.newWxMpTemplateData("first", newOrderVo.getTitle());
        // 工单编号
        WxMpTemplateData keyword1 = this.newWxMpTemplateData("keyword1", newOrderVo.getWorkOrderId());
        // 服务类型
        WxMpTemplateData keyword2 = this.newWxMpTemplateData("keyword2", newOrderVo.getWorkType());
        // 下单时间
        WxMpTemplateData keyword3 = this.newWxMpTemplateData("keyword3", newOrderVo.getDate());
        // 客户姓名
        WxMpTemplateData keyword4 = this.newWxMpTemplateData("keyword4", newOrderVo.getName());
        // 客户地址
        WxMpTemplateData keyword5 = this.newWxMpTemplateData("keyword5", newOrderVo.getAddress());
        // 备注
        WxMpTemplateData remark = this.newWxMpTemplateData("remark", newOrderVo.getRemark());

        wxMpTemplateDataList.add(first);
        wxMpTemplateDataList.add(keyword1);
        wxMpTemplateDataList.add(keyword2);
        wxMpTemplateDataList.add(keyword3);
        wxMpTemplateDataList.add(keyword4);
        wxMpTemplateDataList.add(keyword5);
        wxMpTemplateDataList.add(remark);

        return wxMpTemplateDataList;
    }

    @Override
    public List<WxMpTemplateData> machineStatusRemindDate(MachineStatusRemindVo machineStatusRemindVo) {
        List<WxMpTemplateData> wxMpTemplateDataList = new ArrayList<>();
        // 设备编号
        WxMpTemplateData first = this.newWxMpTemplateData("first", machineStatusRemindVo.getMachineSn());
        // 设备状态
        WxMpTemplateData keyword1 = this.newWxMpTemplateData("keyword1", machineStatusRemindVo.getMachineStatus());
        // 时间
        WxMpTemplateData keyword2 = this.newWxMpTemplateData("keyword2", machineStatusRemindVo.getDate());
        // 备注
        WxMpTemplateData remark = this.newWxMpTemplateData("remark", machineStatusRemindVo.getRemark());

        wxMpTemplateDataList.add(first);
        wxMpTemplateDataList.add(keyword1);
        wxMpTemplateDataList.add(keyword2);
        wxMpTemplateDataList.add(remark);

        return wxMpTemplateDataList;
    }

    public WxMpTemplateData newWxMpTemplateData(String name, String value) {
        WxMpTemplateData wxMpTemplateData = new WxMpTemplateData();
        final String color = sysConfigService.getValue("MP_TEMPLATE_COLOR","#173177");
        wxMpTemplateData.setName(name);
        wxMpTemplateData.setValue(value);
        wxMpTemplateData.setColor(color);
        return wxMpTemplateData;
    }


}
