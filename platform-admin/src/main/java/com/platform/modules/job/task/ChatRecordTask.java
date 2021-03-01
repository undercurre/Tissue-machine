package com.platform.modules.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.entity.AdChatRecordEntity;
import com.platform.modules.ad.entity.AdChatWorkerEntity;
import com.platform.modules.ad.service.AdChatRecordService;
import com.platform.modules.ad.service.AdChatWorkerService;
import com.platform.modules.sys.entity.SysConfigEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.vo.AdChatRecordVo;
import com.platform.modules.vo.AdChatWorkerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component("chatRecordTask")
public class ChatRecordTask {

    private final AdChatRecordService adChatRecordService;
    private final AdChatWorkerService adChatWorkerService;
    private final SysConfigService sysConfigService;

    @Autowired
    public ChatRecordTask(AdChatRecordService adChatRecordService,AdChatWorkerService adChatWorkerService,
                          SysConfigService sysConfigService) {
        this.adChatRecordService = adChatRecordService;
        this.adChatWorkerService = adChatWorkerService;
        this.sysConfigService = sysConfigService;
    }
    /**
     * 过期自动取消订单
     */
    @SuppressWarnings(value = "unused")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getMsgList() {
        log.info("--------------------------开始执行查询微信客服聊天记录--------------------------");



        String result = null;
        try {
            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8531bbbbe17790a0&secret=09f6024d0606da28c09535e69f0bb339");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");
        String msgid = sysConfigService.getValue("MSGID");

        //获取当前时间的时间戳
        String nowTimeStamp = DateUtils.timeStamp();
        System.out.println(nowTimeStamp);
        String lastHourTimeStamp = DateUtils.LastHourTimeStamp();
        System.out.println(lastHourTimeStamp);
        System.out.println(msgid);
        JSONObject params = new JSONObject();
        params.put("starttime", lastHourTimeStamp);
        params.put("endtime", nowTimeStamp);
        params.put("msgid", Integer.parseInt(msgid));
        params.put("number", 10000);

        try {
            result = HttpUtils.sendPost("https://api.weixin.qq.com/customservice/msgrecord/getmsglist?access_token=" + token,
                    params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject = JSON.parseObject(result);
        List<AdChatRecordVo> adChatRecordVoList = JSON.parseArray(jsonObject.getString("recordlist"), AdChatRecordVo.class);
        List<AdChatRecordEntity> adChatRecordEntityList = new ArrayList<>();
        for (AdChatRecordVo vo : adChatRecordVoList) {
            AdChatRecordEntity adChatRecordEntity = new AdChatRecordEntity();
            adChatRecordEntity.setOpenId(vo.getOpenid());
            adChatRecordEntity.setOperCode(vo.getOpercode());
            adChatRecordEntity.setText(vo.getText());
            adChatRecordEntity.setCreatedTime(new Date(Long.parseLong(vo.getTime() + "000")));
            adChatRecordEntity.setWorkerId(vo.getWorker());
            adChatRecordService.add(adChatRecordEntity);
            adChatRecordEntityList.add(adChatRecordEntity);
        }
        //将最新msgId存入数据库
        String lastestMsgid = jsonObject.getString("msgid");
        sysConfigService.updateValueByKey("MSGID",lastestMsgid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("chatRecordList", adChatRecordEntityList);
        //添加客服人员到表
        List<AdChatWorkerEntity> adChatWorkerEntityList = this.getChatWorkerList();

        map.put("chatWorkerList", adChatWorkerEntityList);
        System.out.println(adChatRecordEntityList);
        System.out.println(adChatWorkerEntityList);
        log.info("--------------------------结束执行查询微信客服聊天记录--------------------------");
        return map;

    }

    public  List<AdChatWorkerEntity> getChatWorkerList () {
        String result = null;
        try {
            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8531bbbbe17790a0&secret=09f6024d0606da28c09535e69f0bb339");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");

        try {
            result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject = JSONObject.parseObject(result);
        List<AdChatWorkerVo> adChatWorkerVoList = JSON.parseArray(jsonObject.getString("kf_list"), AdChatWorkerVo.class);
        List<AdChatWorkerEntity> adChatWorkEntityList = new ArrayList<>();
        for (AdChatWorkerVo vo : adChatWorkerVoList) {
            AdChatWorkerEntity adChatWorkerEntity = new AdChatWorkerEntity();
            adChatWorkerEntity.setWorkerId(vo.getKf_account());
            adChatWorkerEntity.setNickname(vo.getKf_nick());
            adChatWorkEntityList.add(adChatWorkerEntity);
        }

        Map<String, Integer> map = new HashMap<>();
        for(AdChatWorkerEntity ad:adChatWorkerService.list()) {
            map.put(ad.getWorkerId(), 1);
        }
        System.out.println(map);
        System.out.println(adChatWorkEntityList);
        for(AdChatWorkerEntity ad: adChatWorkEntityList) {
            System.out.println(map.get(ad.getWorkerId()));
            if(map.get(ad.getWorkerId())==null ){
                adChatWorkerService.add(ad);
            } else {
                adChatWorkerService.update(ad);
            }
        }
        return adChatWorkEntityList;
    }
}
