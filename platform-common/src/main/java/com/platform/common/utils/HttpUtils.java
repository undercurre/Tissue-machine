package com.platform.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.util.ArrayListWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HttpUtils {
    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url    发送请求的 URL
     * @return 远程资源的响应结果
     */

    @SuppressWarnings("unused")
    public static String sendGet(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

    @SuppressWarnings("unused")
    public static String sendPost(String url, String params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

    public static void main(String args[]) throws Exception {

        String result = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8531bbbbe17790a0&secret=09f6024d0606da28c09535e69f0bb339");
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");

        JSONObject params = new JSONObject();
        params.put("starttime", 1603814400);
        params.put("endtime", 1603900800);
        params.put("msgid", 1);
        params.put("number", 10000);
        result = HttpUtils.sendPost("https://api.weixin.qq.com/customservice/msgrecord/getmsglist?access_token=" + token,
        params.toString());
        jsonObject = JSON.parseObject(result);

        List<Object> objectList = JSON.parseArray(jsonObject.getString("recordlist"));
        String s = "1603937783000";
        System.out.println(new Date(Long.parseLong(s)));

    }
}
