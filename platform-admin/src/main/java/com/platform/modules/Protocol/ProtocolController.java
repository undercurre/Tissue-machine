package com.platform.modules.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.*;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.iot.ApiException;
import com.platform.modules.iot.IoTGatewayClient;
import com.platform.modules.iot.request.CommonJsonRequest;
import com.platform.modules.iot.response.CommonJsonResponse;
import com.platform.modules.protocol.entity.TissueProtocol;
import com.platform.modules.protocol.service.ProtocolRefoundService;
import com.platform.modules.sys.service.SysConfigService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("machineInter")
public class ProtocolController {
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachinePushService adMachinePushService;
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private ProtocolRefoundService protocolRefoundService;
    @Autowired
    private AdOrderTissueService adOrderTissueService;
    @Autowired
    private AdMachineOperateService adMachineOperateService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private AdMachineReportService adMachineReportService;
    @Autowired
    private AdMachineReportRecordService adMachineReportRecordService;

    @Value("${iot.appId}")
    private String appId;
    @Value("${iot.openId}")
    private String openId;
    @Value("${iot.secrect}")
    private String secrect;
    @Value("${iot.version}")
    private String version;

    //接收硬件传输数据
    @PostMapping("/machineInfo")
    public List<LinkedHashMap<String,Object>> receiveMachineInfo(@RequestBody JSONArray jsonParam) {
        System.out.println(jsonParam);
        List<TissueProtocol> tissueProtocolsList = JSONObject.parseArray(jsonParam.toJSONString(), TissueProtocol.class);
//        ArrayList<ResponProtocol> responProtocolList = new ArrayList<>();
        ArrayList<LinkedHashMap<String,Object>> responProtocolList = new ArrayList<>();
        System.out.println(tissueProtocolsList);
        String machineKey = sysConfigService.getValue(Constant.MACHINE_SEND_KEY);
        String serverResponKey = sysConfigService.getValue(Constant.SERVER_RESPON_KEY);
        String electricityWarning = sysConfigService.getValue(Constant.MACHINE_ELECTRICITY_WARING);
        int elecWarningInt = Integer.parseInt(electricityWarning);
        Date date = new Date();
        tissueProtocolsList.forEach(e->{
            if(e.getKY().equals(machineKey)) {

                // 将机柜上报信息写入机柜日志中
                AdMachineReportRecordEntity adMachineReportRecordEntity = new AdMachineReportRecordEntity();
                adMachineReportRecordEntity.setMachineSn(e.getID());
                adMachineReportRecordEntity.setReportTime(date);
                adMachineReportRecordEntity.setContent(e.toString());
                adMachineReportRecordService.add(adMachineReportRecordEntity);

                // 判断机柜是否注册在机柜表中，如果不在则跳过
                AdTissueMachineEntity adTissueMachineEntity = adTissueMachineService.getBySn(e.getID());
                if (adTissueMachineEntity == null) {
                    return;
                }
                // 更新机柜上报时间表
                String sn = e.getID();
                AdMachineReportEntity adMachineReportEntity = null;
                adMachineReportEntity = adMachineReportService.getOne(new QueryWrapper<AdMachineReportEntity>().eq("MACHINE_SN", sn));
                if (adMachineReportEntity == null) {
                    adMachineReportEntity = new AdMachineReportEntity();
                    adMachineReportEntity.setMachineSn(sn);
                    adMachineReportEntity.setReportTime(new Date());
                    adMachineReportService.add(adMachineReportEntity);
                } else {
                    adMachineReportEntity.setReportTime(new Date());
                    adMachineReportService.update(adMachineReportEntity);
                }
                switch (e.getCM()) {
                    case "03":
                        int hasChange = 0;
                        // 如果之前是离线状态、修改为正常状态
                        if(adTissueMachineEntity.getStatus().equals(Constant.MachineStatus.LX.getValue())){
                            hasChange=1;
                            adTissueMachineEntity.setStatus(Constant.MachineStatus.SBZC.getValue());
                        }
                        // 如果电量小于警戒值
                        if(e.getBT()<elecWarningInt){
                            adTissueMachineEntity.setStatus(Constant.MachineStatus.DLYJ.getValue());
                            adTissueMachineService.sendMessage(adTissueMachineEntity,Constant.MachineStatus.DLYJ.getValue());
                            hasChange=1;
                        }
                        //如果电量不一致，则更新电量
                        if(!adTissueMachineEntity.getElectricityQuantity().equals(e.getBT())){
                            adTissueMachineEntity.setElectricityQuantity(e.getBT());
                            hasChange=1;
                        }
                        if(hasChange==1){
                            adTissueMachineService.updateById(adTissueMachineEntity);
                        }
                        //按时间顺序拿列表中需要出纸巾的记录
                        AdMachinePushEntity machinePush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>().eq("MACHINE_SN", e.getID())
                                .eq("STATUS",0)
                                .orderByAsc("CREATED_TIME")
                                .last("LIMIT 1"));
                        System.out.println(machinePush);
                        if (machinePush != null && machinePush.getNumber() > 0) {
                            //如果有正在申请的记录，则不出纸
                            AdMachinePushEntity applyingPush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>().eq("MACHINE_SN", e.getID())
                                    .eq("STATUS",1)
                                    .last("LIMIT 1"));
                            System.out.println(StringUtils.isNullOrEmpty(applyingPush));
//                            if(StringUtils.isNullOrEmpty(applyingPush)) {
                                machinePush.setStatus(1);
                                adMachinePushService.update(machinePush);
                                //控制出纸
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(machinePush.getNumber());
                            buffer.append(adTissueMachineEntity.getLightStatus());
                            responProtocolList.add(pushTissue(e.getID(), "04", buffer.toString(), serverResponKey));
                                System.out.println("出纸~~~~~");
                                AdOrderEntity order = adOrderService.getById(machinePush.getOrderId());
                                if(order!=null) {
                                    order.setShipmentStatus(Constant.OrderShipmentStatus.SQCH.getValue());
                                    adOrderService.update(order);
                                }
//                            }
                        }
                        else {
                            //设备不用出纸，执行其他操作
                            AdMachineOperateEntity machineOperate = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("STATUS",Constant.ExecType.WZX.getValue())
                                    .orderByAsc("SORT_LEVEL")
                                    .last("LIMIT 1"));
                            if(machineOperate !=null) {
                                machineOperate.setStatus(Constant.ExecType.SQZX.getValue());
                                System.out.println(machineOperate);
                                //执行操作
                                responProtocolList.add(operateMachie(e.getID(), machineOperate.getOperateCode(), machineOperate.getContent(), serverResponKey));
                                adMachineOperateService.update(machineOperate);
                            }
                        }
                        break;
                    case "40":
                        //控制出纸成功;
                        AdMachinePushEntity newPush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>()
                                .eq("MACHINE_SN", e.getID())
                                .eq("STATUS",1)
                                .orderByAsc("CREATED_TIME")
                                .last("LIMIT 1"));
                        if(newPush==null){
                            break;
                        }
                        // DATA 首位是出纸数量
                        Integer factNumber = Integer.valueOf(e.getDATA().substring(0,1));
                        if (factNumber < newPush.getNumber()) {
                            //实际出货纸巾数量小于购买数量
                            AdOrderEntity order = adOrderService.getById(newPush.getOrderId());
                            //退款操作
                            System.out.println(factNumber);
                            protocolRefoundService.refund(order, factNumber);
                        }
                        else {
                            //出货正常 修改order表出货状态
                            AdOrderEntity order = adOrderService.getById(newPush.getOrderId());
                            order.setShipmentStatus(Constant.OrderShipmentStatus.CHCG.getValue());
                            adOrderService.update(order);
                            //将机柜改为未使用状态
                            adTissueMachineEntity.setIsUsed(Constant.MachineUseStatus.WSY.getValue());
                            adTissueMachineService.updateById(adTissueMachineEntity);
                            List<AdOrderTissueEntity> orderTissueList = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>()
                                    .eq("ORDER_ID", order.getId()));
                            orderTissueList.forEach(o -> {
                                o.setShipmentNumber(o.getGoodsCount());
                                o.setSellTime(date);
                                o.setStatus(Constant.ShipmentStatus.SUCCESS.getValue());
                                adOrderTissueService.update(o);
                            });
                            //操作表设置会出货成功状态
                            newPush.setStatus(Constant.OrderShipmentStatus.CHCG.getValue());
                            adMachinePushService.updateById(newPush);
                        }
                        break;
                    //更新设备语音成功返回
                    case "50":
                            AdMachineOperateEntity machineOperateEntity = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("OPERATE_CODE", Constant.MachineOperateType.VOICE.getValue())
                                    .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                    .orderByAsc("CREATE_TIME")
                                    .last("LIMIT 1")
                            );
                            if (e.getEC().equals("00")) {
                                machineOperateEntity.setStatus(Constant.ExecType.ZXCG.getValue());
                                machineOperateEntity.setFinishTime(date);
                            } else {
                                machineOperateEntity.setStatus(Constant.ExecType.ZXYC.getValue());
                            }
                            adMachineOperateService.update(machineOperateEntity);
                        break;
                    case "60":
                        //设备返回CCID;
                            AdMachineOperateEntity machineOperateCCID = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("OPERATE_CODE", Constant.MachineOperateType.CCID.getValue())
                                    .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                    .orderByAsc("CREATE_TIME")
                                    .last("LIMIT 1")
                            );
                        if(StringUtils.isNotBlank(e.getDATA())) {
                                HashMap<String, Object> map = new HashMap<>();
                                ArrayList<String> iccids = new ArrayList<>();
                                iccids.add(e.getDATA());
                                map.put("openId", openId);
                                map.put("version", version);
                                map.put("iccids",iccids);
                                map.put("messageId", "1");
                                System.out.println(map);
                                IoTGatewayClient client = new IoTGatewayClient("https://gwapi.10646.cn/api/", appId, secrect);
                                CommonJsonRequest request = new CommonJsonRequest();
                                // 完整的url：https://gwtest.10646.cn/api/GetAccountIdByAcctName_V1_0Main/vV1.0
                                request.setApiName("wsGetTerminalDetails/V1/1Main");
                                request.setApiVer("V1.1");
                                request.setParams(map);
                                client.setConnectTimeout(30000);
                                client.setRetryCount(0);
                                CommonJsonResponse response = null;
                                try {
                                    response = client.execute(request);
                                } catch (ApiException apiException) {
                                    apiException.printStackTrace();
                                }
                                System.out.println("发送请求流水号：" + request.getTransId());
                                System.out.println("请求报文：" + request.getReqText());
                                System.out.println("请求是否被成功处理:" + response.isSuccess());
                                Map data = response.getData();
                                System.out.println(data);
                                if (response.isSuccess()){
                                    //查询系统参数设置的SIM每月流量
//                                    String simMonthData = sysConfigService.getValue("SIM_MONTH_DATA");
//                                    BigDecimal simMonth = new BigDecimal(simMonthData);

                                    System.out.println("成功返回业务参数：" + data);
                                    JSONArray terminals = JSONArray.parseArray(JSON.toJSONString(data.get("terminals")));
                                    JSONObject  o = (JSONObject) terminals.get(0);
                                    String monthToDateDataUsage = o.getString("monthToDateDataUsage");
                                    System.out.println(monthToDateDataUsage);
                                    BigDecimal monthUseData = new BigDecimal(monthToDateDataUsage);
                                    //目前只记录已使用流量
                                    adTissueMachineEntity.setUsedFlow(monthUseData);
                                }
                                else
                                {
                                    System.out.println("处理失败返回消息：" + data);
                                }
                            }
                        machineOperateCCID.setStatus(Constant.ExecType.ZXCG.getValue());
                            machineOperateCCID.setFinishTime(date);
                            adMachineOperateService.update(machineOperateCCID);
                            //修改设备表
                            adTissueMachineEntity.setSimCcid(e.getDATA());
                            adTissueMachineService.update(adTissueMachineEntity);
                            break;
                    case "70":
                        //返回设备版本信息
                            AdMachineOperateEntity machineOperateVersion = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("OPERATE_CODE", Constant.MachineOperateType.VERSION.getValue())
                                    .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                    .orderByAsc("CREATE_TIME")
                                    .last("LIMIT 1")
                            );
                            machineOperateVersion.setStatus(Constant.ExecType.ZXCG.getValue());
                            machineOperateVersion.setFinishTime(date);
                            adMachineOperateService.update(machineOperateVersion);
                            //修改设备表
                            adTissueMachineEntity.setMachineVersion(e.getDATA());
                            adTissueMachineService.update(adTissueMachineEntity);
                            break;
                    case "80":
                        //    返回经纬度

                            String data = e.getDATA();
                            AdMachineOperateEntity machineOperateLocation = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("OPERATE_CODE", Constant.MachineOperateType.LOCATION.getValue())
                                    .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                    .orderByAsc("CREATE_TIME")
                                    .last("LIMIT 1")
                            );
                            if (StringUtils.isNotBlank(data)) {
                                String[] split = data.split(",");
                                System.out.println(split);
                                System.out.println(split[0]);
                                System.out.println(split[1].replace(" ", ""));
                                BigDecimal lat = new BigDecimal(split[0]);
                                BigDecimal lon = new BigDecimal(split[1].replace(" ", ""));
                                Double[] gcj02 = GpsUtil.BD09ToGCJ02(lon.doubleValue(), lat.doubleValue());
                                System.out.println(gcj02[0]);
                                System.out.println(gcj02[1]);
                                //获取系统参数-设备最大距离范围
                                String machine_max_distance = sysConfigService.getValue("MACHINE_MAX_DISTANCE");
                                //计算距离
                                double s = GpsUtil.getDistance(gcj02[1], gcj02[0], adTissueMachineEntity.getLatitude().doubleValue(), adTissueMachineEntity.getLongitude().doubleValue());
                                BigDecimal distance = new BigDecimal(s);
                                BigDecimal sys_max_distance = new BigDecimal(machine_max_distance);
                                System.out.println(distance);
                                System.out.println(sys_max_distance);
                                //如果当前距离大于系统设置的最大距离范围，则更新设备状态为位置异常
                                if (distance.compareTo(sys_max_distance) > 0 && adTissueMachineEntity.getIsOpenLocate() ==1) {
                                    adTissueMachineEntity.setStatus(Constant.MachineStatus.WZYC.getValue());
                                    adTissueMachineService.sendMessage(adTissueMachineEntity,Constant.MachineStatus.WZYC.getValue());
                                }
                                adTissueMachineEntity.setReportLatitude(lat);
                                adTissueMachineEntity.setReportLongitude(lon);
                                adTissueMachineService.updateById(adTissueMachineEntity);
                                machineOperateLocation.setFinishTime(date);
                                machineOperateLocation.setStatus(Constant.ExecType.ZXCG.getValue());
                                adMachineOperateService.update(machineOperateLocation);
                            }
                            break;
                    case "90":
                        //返回URL地址
                            AdMachineOperateEntity machineOperateServer = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                    .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                    .eq("OPERATE_CODE", Constant.MachineOperateType.SERVER_URL.getValue())
                                    .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                    .orderByAsc("CREATE_TIME")
                                    .last("LIMIT 1")
                            );
                            machineOperateServer.setFinishTime(date);
                            machineOperateServer.setStatus(Constant.ExecType.ZXCG.getValue());
                            adMachineOperateService.update(machineOperateServer);
                            adTissueMachineEntity.setServerUrl(e.getDATA());
                            adTissueMachineService.update(adTissueMachineEntity);
                            break;
                    case "A0":
                        //重启设备
                        AdMachineOperateEntity machineOperateRestart = adMachineOperateService.getOne(new QueryWrapper<AdMachineOperateEntity>()
                                .eq("MACHINE_ID", adTissueMachineEntity.getId())
                                .eq("OPERATE_CODE", Constant.MachineOperateType.RESTART.getValue())
                                .eq("STATUS", Constant.ExecType.SQZX.getValue())
                                .orderByAsc("CREATE_TIME")
                                .last("LIMIT 1")
                        );
                        machineOperateRestart.setFinishTime(date);
                        machineOperateRestart.setStatus(Constant.ExecType.ZXCG.getValue());
                        adMachineOperateService.update(machineOperateRestart);
                        break;
                }
            }
        });
        return responProtocolList;
    }
//控制设备出纸
    public LinkedHashMap<String,Object> pushTissue(String sn,String CM,String data,String key){
        LinkedHashMap<String, Object> stringObjectLinkedHashMap = new LinkedHashMap<>();
        stringObjectLinkedHashMap.put("ID",sn);
        stringObjectLinkedHashMap.put("TP",1);
        stringObjectLinkedHashMap.put("CM",CM);
        stringObjectLinkedHashMap.put("KY",key);
        stringObjectLinkedHashMap.put("DATA",data);
        return stringObjectLinkedHashMap;
    }


    //控制设备执行操作,需要按顺序返回
    public LinkedHashMap<String,Object> operateMachie(String sn,Integer operate,String data,String key){
        LinkedHashMap<String, Object> stringObjectLinkedHashMap = new LinkedHashMap<>();
        stringObjectLinkedHashMap.put("ID",sn);
        stringObjectLinkedHashMap.put("TP",1);
        switch (operate){
            case 1:
                //更新语音
                stringObjectLinkedHashMap.put("CM","05");
                break;
            case 2:
                //获取SIM卡CCID
                stringObjectLinkedHashMap.put("CM","06");
                break;
            case 3:
                //获取设备版本
                stringObjectLinkedHashMap.put("CM","07");
                break;
            case 4:
                //获取设备位置信息
                stringObjectLinkedHashMap.put("CM","08");
                break;
            case 5:
                //获取服务器URL地址
                stringObjectLinkedHashMap.put("CM","09");
                break;
            case 6:
                //设备重启
                stringObjectLinkedHashMap.put("CM","0A");
                break;
            case 7:
                //控制灯条开关
                stringObjectLinkedHashMap.put("CM","04");
        }
        stringObjectLinkedHashMap.put("KY",key);
        stringObjectLinkedHashMap.put("DATA",data);
        //如果是更新语音，需要转成GBK编码
        if(operate==1){
            stringObjectLinkedHashMap.put("DATA",StringUtils.stringToUnicode(data));
        }
        System.out.println(stringObjectLinkedHashMap);
        return stringObjectLinkedHashMap;
    }
}
