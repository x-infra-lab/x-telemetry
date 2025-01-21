package io.github.xinfra.lab.telemetry.common;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.StringConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Beans {

	static class String2EnumConverter implements Converter {

		public static StringConverter stringConverter = new StringConverter();

		@Override
		public <T> T convert(Class<T> type, Object value) {
			try {
				return stringConverter.convert(type, value);
			}
			catch (Exception e) {
				if (type.isEnum()) {
					Class clazz = type;
					return (T) Enum.valueOf(clazz, value.toString());
				}
				throw e;
			}
		}

	}

	static {
		ConvertUtils.register(new String2EnumConverter(), String.class);
	}

	public static void populate(final Object bean, final Map<String, ? extends Object> properties)
			throws InvocationTargetException, IllegalAccessException {
		BeanUtilsBean2.getInstance().populate(bean, properties);
	}

}
