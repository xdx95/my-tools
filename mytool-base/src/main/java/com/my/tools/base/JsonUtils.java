package com.my.tools.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author: xdx
 * @date: 2024/8/29
 * @description: Json 工具类
 */
public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	// 日起格式化
	private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static {
		// 对象的所有字段全部列入
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// 忽略空 Bean to Json 错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// Json to Bean 忽略 Bean 对象中不存在对应属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 取消默认转换 Timestamps 形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置日期统一格式，只支持 Date ，LocalDateTime 需要引入 jackson-datatype-jsr310
		objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
	}

	/**
	 * 对象转Json格式字符串
	 *
	 * @param obj 对象
	 * @return Json格式字符串
	 */
	public static <T> String obj2String(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 对象转Json格式字符串(格式化的Json字符串)
	 *
	 * @param obj 对象
	 * @return 美化的Json格式字符串
	 */
	public static <T> String obj2StringPretty(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj
				: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 字符串转换为自定义对象
	 *
	 * @param str   要转换的字符串
	 * @param clazz 自定义对象的class对象
	 * @return 自定义对象
	 */
	public static <T> T string2Obj(String str, Class<T> clazz) {
		if (str == null || str.isEmpty() || clazz == null) {
			return null;
		}
		try {
			return objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
		if (str == null || str.isEmpty() || typeReference == null) {
			return null;
		}
		try {
			return objectMapper.readValue(str, typeReference);
		} catch (IOException e) {
			return null;
		}
	}

	public static <T> T string2Obj(String str, Class<?> collectionClazz,
		Class<?>... elementClazz) {
		JavaType javaType = objectMapper.getTypeFactory()
			.constructParametricType(collectionClazz, elementClazz);
		try {
			return objectMapper.readValue(str, javaType);
		} catch (IOException e) {
			return null;
		}
	}
}
