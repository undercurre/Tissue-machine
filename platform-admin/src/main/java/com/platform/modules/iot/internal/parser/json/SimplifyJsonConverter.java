package com.platform.modules.iot.internal.parser.json;

import com.platform.modules.iot.ApiException;
import com.platform.modules.iot.internal.mapping.Converter;
import com.platform.modules.iot.internal.mapping.Converters;
import com.platform.modules.iot.internal.mapping.Reader;
import com.platform.modules.iot.internal.utils.json.ExceptionErrorListener;
import com.platform.modules.iot.internal.utils.json.JSONReader;
import com.platform.modules.iot.internal.utils.json.JSONValidatingReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * 精简版JSON格式转换器。
 */
public class SimplifyJsonConverter implements Converter {

	public <T> T toResponse(String rsp, Class<T> clazz) throws ApiException {
		JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
		Object rootObj = reader.read(rsp);
		if (rootObj instanceof Map<?, ?>) {
			Map<?, ?> rootJson = (Map<?, ?>) rootObj;
			return fromJson(rootJson, clazz);
		}
		return null;
	}

	/**
	 * 把JSON格式的数据转换为对象。
	 * 
	 * @param <T> 泛型领域对象
	 * @param json JSON格式的数据
	 * @param clazz 泛型领域类型
	 * @return 领域对象
	 */
	public <T> T fromJson(final Map<?, ?> json, Class<T> clazz) throws ApiException {
		return Converters.convert(clazz, new Reader() {
			public boolean hasReturnField(Object name) {
				return json.containsKey(name);
			}

			public Object getPrimitiveObject(Object name) {
				return json.get(name);
			}

			public Object getObject(Object name, Class<?> type) throws ApiException {
				Object tmp = json.get(name);
//				if (tmp instanceof Map<?, ?>) {
//					Map<?, ?> map = (Map<?, ?>) tmp;
//					return fromJson(map, type);
//				} else {
//					return tmp;
//				}
				return tmp;
			}

			public List<?> getListObjects(Object listName, Object itemName, Class<?> subType) throws ApiException {
				List<Object> listObjs = null;

				Object jsonList = json.get(listName);
				if (jsonList instanceof List<?>) {
					listObjs = new ArrayList<Object>();
					List<?> listObj = (List<?>) jsonList;
					for (Object tmp : listObj) {
						if (tmp instanceof Map<?, ?>) {// object
							Map<?, ?> subMap = (Map<?, ?>) tmp;
							Object subObj = fromJson(subMap, subType);
							if (subObj != null) {
								listObjs.add(subObj);
							}
						} else if (tmp instanceof List<?>) {// array
							// TODO not support yet
						} else {// boolean, long, double, string, null
							listObjs.add(tmp);
						}
					}
				}

				return listObjs;
			}
		});
	}

}
