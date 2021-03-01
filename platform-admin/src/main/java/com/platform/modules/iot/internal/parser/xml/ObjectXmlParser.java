package com.platform.modules.iot.internal.parser.xml;


import com.platform.modules.iot.ApiException;
import com.platform.modules.iot.IoTGatewayParser;
import com.platform.modules.iot.IoTGatewayResponse;
import com.platform.modules.iot.internal.mapping.Converter;

/**
 * 单个JSON对象解释器。
 * 
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public class ObjectXmlParser<T extends IoTGatewayResponse> implements IoTGatewayParser<T> {

	private Class<T> clazz;

	public ObjectXmlParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T parse(String rsp) throws ApiException {
		Converter converter = new XmlConverter();
		return converter.toResponse(rsp, clazz);
	}

	public Class<T> getResponseClass() {
		return clazz;
	}

}
