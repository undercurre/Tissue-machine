package com.platform.modules.iot.internal.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtils {
    @SuppressWarnings("rawtypes")
	public static Type getActualType(Class cls, Class interfaceCls) {
		return getActualType(cls, interfaceCls, 0);
	}

    @SuppressWarnings("rawtypes")
	public static Type getActualType(Class cls, Class interfaceCls, int pos) {
		Type[] genericInterfaces = cls.getGenericInterfaces();
		for (Type genericInterface : genericInterfaces) {
			if ((genericInterface instanceof ParameterizedType)) {
				ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
				if (parameterizedType.getRawType() == interfaceCls) {
					Type[] actualTypeArguments = parameterizedType
							.getActualTypeArguments();
					if (actualTypeArguments.length > pos) {
						return actualTypeArguments[pos];
					}
				}
			}
		}
		return null;
	}
}
