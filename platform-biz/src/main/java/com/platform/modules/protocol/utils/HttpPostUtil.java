package com.platform.modules.protocol.utils;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.ObjectToMapUtils;
import com.platform.modules.protocol.entity.TissueProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpPostUtil {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 生成post请求的JSON请求参数
     * 请求示例:
     * {
     * "id":1,
     * "name":"Joe"
     * }
     *
     * @return
     */
    public static HttpEntity <ArrayList<Map<String,String>>> generatePostJson(ArrayList<Map<String,String>> list) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        HttpEntity<ArrayList<Map<String, String>>> httpEntity = new HttpEntity<>(list, httpHeaders);

        return httpEntity;
    }


    /**
     * 发送HTTP请求控制设备出货
     *
     * @return
     */
    public List<TissueProtocol> sendPost(String sn) {
        String uri = "http://www.nolan.net.cn:10023/ls/post/";
        Map<String,String> map = new HashMap<>();
        map.put("ID",sn);
        map.put("TP","1");
        map.put("CM","04");
        map.put("KY","2222");
        map.put("DATA","1");
        ArrayList<Map<String,String>> list = new ArrayList<>();
        list.add(map);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        uri,
                        HttpPostUtil.generatePostJson(list),
                        String.class
                );
        String s = apiResponse.getBody();
        List<TissueProtocol> res = JSONObject.parseArray(s, TissueProtocol.class);
        return res;
    }
}

