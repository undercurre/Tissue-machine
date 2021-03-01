package com.platform.modules.iot;

import java.util.Map;

public interface IoTGatewayRequest<T extends IoTGatewayResponse> {
	
	public String getContentType();

	public String getApiName();
	
	public String getApiVer();
	
	public String getReqText();

	/**
	 * 获取所有的Key-Value形式的文本请求参数集合。其中：
	 * <ul>
	 * <li>Key: 请求参数名</li>
	 * <li>Value: 请求参数值</li>
	 * </ul>
	 * 
	 * @return 文本请求参数集合
	 */
	public Map<String, Object> getParams();


	/**
	 * 获取具体响应实现类的定义。
	 */
	public Class<T> getResponseClass();


	/**
	 * 客户端参数检查，减少服务端无效调用。
	 */
	public void check() throws ApiRuleException;
	
	public void execProcessBeforeReqSend(Object[] params);

}
