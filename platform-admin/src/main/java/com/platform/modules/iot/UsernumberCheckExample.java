package com.platform.modules.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.DateUtils;
import com.platform.modules.iot.request.CommonJsonRequest;
import com.platform.modules.iot.response.CommonJsonResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UsernumberCheckExample

{

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void main(String[] args)
        throws ApiException
    {

        IoTGatewayClient client = new IoTGatewayClient("https://gwapi.10646.cn/api/","LfipW3MuX1","w3C6IsfGsU5oIXva6FxVDsZqrStQzE");
        client.setConnectTimeout(30000);//连接超时时长设置（单位：毫秒），可以不设置，默认两秒
        //client.setReadTimeout(30000);//读取超时时长设置（单位：毫秒），可以不设置，默认三十秒
        client.setRetryCount(0);//连接超时后，重试次数  0：不重试  1：重试一次   以此类推。可以不设置，默认不重试

        CommonJsonRequest request = new CommonJsonRequest();
        // 完整的url：https://gwtest.10646.cn/api/GetAccountIdByAcctName_V1_0Main/vV1.0
//        request.setApiName("wsGetTerminalDetails/V1/1Main");
        request.setApiName("wsGetTerminalDetails/V1/1Main");
        request.setApiVer("V1.1");



        /**
         * 业务参数，需要发给能力提供者
         */
        Map params = new HashMap<String, Object>();
        params.put("openId", "29094oul3i0rFhK");
        params.put("version", "1.0");
        ArrayList<String> iccids = new ArrayList<>();
        iccids.add("89860620170028675066");
        params.put("iccids",iccids);
//        String iccids = "89860620170028675074";
//        params.put("iccid",iccids);
//        params.put("cycleStartDate","2020-12-01 17:35:55");
        request.setParams(params);
        System.out.println(request);
        CommonJsonResponse response = client.execute(request);
        System.out.println("发送请求流水号：" + request.getTransId());
        System.out.println("请求报文：" + request.getReqText());
        System.out.println(response.toString());
        System.out.println(response.getMessage());
        System.out.println(response.getBody());
        System.out.println(response.getStatus());
        System.out.println("请求是否被成功处理:" + response.isSuccess());
        Map data = response.getData();
//        Object monthToDateDataUsage = data.get("monthToDateDataUsage");
//        System.out.println(monthToDateDataUsage.toString());
//        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(data));
//        String monthToDateDataUsage = jsonObject.getString("monthToDateDataUsage");
//        System.out.println(monthToDateDataUsage);
        if (response.isSuccess()){
//            System.out.println("成功返回业务参数：" + data.get("checkresult"));
            System.out.println("成功返回业务参数：" + data);
            System.out.println(data.get("terminals").toString());
            JSONArray terminals = JSONArray.parseArray(JSON.toJSONString(data.get("terminals")));
            JSONObject  o = (JSONObject) terminals.get(0);
            String monthToDateDataUsage = o.getString("monthToDateDataUsage");
            System.out.println(monthToDateDataUsage);
        }
        else
        {
            System.out.println("处理失败返回消息：" + data);
        }
    }
}
