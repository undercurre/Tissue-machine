package com.platform.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.vo.MachineStatusRemindVo;
import com.platform.modules.vo.NewOrderVo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 *
 * 模板消息接口
 *
 */
public interface WxMpSendMsgService{

  /**
   *
   * 发送模板消息
   *
   * @param type 发送模板类型(1：新工单提醒，2：智能设备状态提醒)
   * @param openId 发送用户openId
   * @param data 发送内容
   * @param miniPrograms 小程序
   * @return 发送结果
   */
  String sendTemplateMessage(String openId, Integer type, List<WxMpTemplateData> data, @RequestParam(required = false) WxMpTemplateMessage.MiniProgram miniPrograms) throws WxErrorException;

  /**
   *
   * 新工单提醒消息内容
   *
   * @param newOrderVo
   * @return 发送结果
   */
  List<WxMpTemplateData> newOrderDate(NewOrderVo newOrderVo);

  /**
   *
   * 智能设备状态提醒消息内容
   *
   * @param machineStatusRemindVo
   * @return 发送结果
   */
  List<WxMpTemplateData> machineStatusRemindDate(MachineStatusRemindVo machineStatusRemindVo);
}
