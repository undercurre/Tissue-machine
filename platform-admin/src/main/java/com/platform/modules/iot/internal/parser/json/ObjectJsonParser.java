package com.platform.modules.iot.internal.parser.json;


import com.platform.modules.iot.ApiException;
import com.platform.modules.iot.IoTGatewayParser;
import com.platform.modules.iot.IoTGatewayResponse;
import com.platform.modules.iot.internal.mapping.Converter;

public class ObjectJsonParser<T extends IoTGatewayResponse> implements IoTGatewayParser<T> {

	private Class<T> clazz;
	private boolean simplify;

	public ObjectJsonParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	public ObjectJsonParser(Class<T> clazz, boolean simplify) {
		this.clazz = clazz;
		this.simplify = simplify;
	}

	public T parse(String rsp) throws ApiException {
		Converter converter;
		if (this.simplify) {
			converter = new SimplifyJsonConverter();
		} else {
			converter = new JsonConverter();
		}
		return converter.toResponse(rsp, clazz);
	}

	public Class<T> getResponseClass() {
		return clazz;
	}

}
