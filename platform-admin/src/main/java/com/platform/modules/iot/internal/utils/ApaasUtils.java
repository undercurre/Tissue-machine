package com.platform.modules.iot.internal.utils;

import com.platform.modules.iot.ApiException;
import com.platform.modules.iot.IoTGatewayResponse;
import com.platform.modules.iot.internal.parser.json.ObjectJsonParser;
import com.platform.modules.iot.internal.utils.json.JSONReader;
import com.platform.modules.iot.internal.utils.json.JSONValidatingReader;
import com.platform.modules.iot.internal.utils.json.JSONWriter;

import java.net.InetAddress;




public abstract class ApaasUtils {

	private static String intranetIp;

	private ApaasUtils() {}


	/**
	 * 把JSON字符串转化为对象结构。
	 * 
	 * @param json JSON字符串
	 * @return 对象结构，一般为Map
	 */
	public static Object jsonToObject(String json) {
		JSONReader jr = new JSONValidatingReader();
		return jr.read(json);
	}

	/**
	 * 把对象结构转换为JSON字符串。
	 * 
	 * @param object 对象结构
	 * @return JSON字符串
	 */
	public static String objectToJson(Object object) {
		JSONWriter writer = new JSONWriter(false, true);
		return writer.write(object);
	}

	/**
	 * 把对象结构转换为XML字符串。
	 * 
	 * @param object 对象结构
	 * @return XML字符串
	 */
	public static String objectToXml(Object object) {
		XmlWriter writer = new XmlWriter();
		return writer.write(object);
	}

	/**
	 * 把JSON字符串解释为对象结构。
	 * 
	 * @param <T> API响应类型
	 * @param json JSON字符串
	 * @param clazz API响应类
	 * @return API响应对象
	 */
	public static <T extends IoTGatewayResponse> T parseResponse(String json, Class<T> clazz) throws ApiException {
		ObjectJsonParser<T> parser = new ObjectJsonParser<T>(clazz);
		T rsp = parser.parse(json);
		rsp.setBody(json);
		return rsp;
	}

	/**
	 * 获取本机的局域网IP。
	 */
	public static String getIntranetIp() {
		if (intranetIp == null) {
			try {
				intranetIp = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				intranetIp = "127.0.0.1";
			}
		}
		return intranetIp;
	}

}
