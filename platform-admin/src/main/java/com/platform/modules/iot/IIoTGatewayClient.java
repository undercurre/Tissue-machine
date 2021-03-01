package com.platform.modules.iot;

public interface IIoTGatewayClient {

	/**
	 * 执行公开API请求。
	 * 
	 * @param <T> 具体的API响应类
	 * @param request 具体的API请求类
	 * @return 具体的API响应
	 */
	public <T extends IoTGatewayResponse> T execute(IoTGatewayRequest<T> request) throws ApiException;


}
