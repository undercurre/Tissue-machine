package com.platform.modules.iot;

import com.platform.modules.iot.internal.parser.json.ObjectJsonParser;
import com.platform.modules.iot.internal.parser.xml.ObjectXmlParser;
import com.platform.modules.iot.internal.utils.ApaasSignUtil;
import com.platform.modules.iot.internal.utils.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class IoTGatewayClient implements IIoTGatewayClient{
	private final String serverUrl;
	private final String appId;
	private final String appSecret;
	
	private int connectTimeout = 2 * 1000 ;
	private int readTimeout = 30 * 1000;
	private int retryCount = 0;//连接超时后，重试次数  0：不重试  1：重试一次   以此类推。默认不重试

	public IoTGatewayClient(String serverUrl, String appId, String appSecret) {
		this.serverUrl = serverUrl;
		this.appId = appId;
		this.appSecret = appSecret;
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends IoTGatewayResponse> T execute(IoTGatewayRequest<T> request) throws ApiException {
		String rspMsg = doPost(request);
		if (rspMsg == null) {
			return null;
		}

		String contentType = request.getContentType();

		Class respClass = request.getResponseClass();
		IoTGatewayParser parser = (contentType != null)
				&& (contentType.contains("text/xml")) ? new ObjectXmlParser(
				respClass) : new ObjectJsonParser(respClass,true);

		IoTGatewayResponse tRsp = null;
		try {
			tRsp = (IoTGatewayResponse) parser.parse(rspMsg);
		} catch (Exception e) {
			tRsp.setSuccess(false);
		}
		return (T) tRsp;
	}

	@SuppressWarnings("rawtypes")
    private <T extends IoTGatewayResponse> String doPost(IoTGatewayRequest<T> request) throws ApiException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ApaasSignUtil._app_id, this.appId);
		map.put(ApaasSignUtil._app_secrect, this.appSecret);
		try{
			ApaasSignUtil.buildAppParams(map);
		}catch(Exception e){
			throw new ApiException(e);
		}
		
		Map params = request.getParams();
		map.put("data", params);
		request.execProcessBeforeReqSend(new Object[]{map});
		try {
			return WebUtils.doPost(this.serverUrl,request.getApiName(), request.getApiVer(), request.getReqText(), this.connectTimeout,
					this.readTimeout, this.retryCount);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	public int getReadTimeout() {
		return this.readTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}