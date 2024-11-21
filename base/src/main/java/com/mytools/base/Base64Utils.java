package com.mytools.base;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * Base64工具类
 *
 * @author xdx
 */
public final class Base64Utils {

	// 标准 Base64 编码器和解码器
	public static final Encoder ENCODER = Base64.getEncoder();
	public static final Decoder DECODER = Base64.getDecoder();

	// 标准 Base64 编码方法
	public static String encode(byte[] input) {
		return ENCODER.encodeToString(input);
	}

	public static String encode(String input) {
		return encode(input.getBytes(StandardCharsets.UTF_8));
	}

	// 标准 Base64 解码方法
	public static byte[] decode(byte[] base64Encoded) {
		return DECODER.decode(base64Encoded);
	}

	public static byte[] decode(String base64Encoded) {
		return decode(base64Encoded.getBytes(StandardCharsets.UTF_8));
	}


}
