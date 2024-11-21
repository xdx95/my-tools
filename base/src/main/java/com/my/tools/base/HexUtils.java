package com.my.tools.base;

import java.util.Locale;

/**
 * 十六进制工具类
 *
 * @author xdx
 */
public class HexUtils {

	/**
	 * 将字节数组编码为十六进制字符串。
	 *
	 * @param bytes 字节数组
	 * @return 十六进制字符串
	 */
	public static String encodeToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hexString.append('0'); // 保持两位
			}
			hexString.append(hex);
		}
		return hexString.toString().toUpperCase(Locale.ROOT);
	}

	/**
	 * 将十六进制字符串解码为字节数组。
	 *
	 * @param hexStr 十六进制字符串
	 * @return 字节数组
	 * @throws IllegalArgumentException 如果输入格式无效
	 */
	public static byte[] decodeHex(String hexStr) {
		if (hexStr.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid hex string");
		}
		byte[] bytes = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length(); i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4) + Character.digit(
				hexStr.charAt(i + 1), 16));
		}
		return bytes;
	}

	/**
	 * 展开十六进制字符串，在每个字节后插入指定的分隔符。
	 *
	 * @param hexStr       十六进制字符串
	 * @param delimiter 分隔符
	 * @return 展开的十六进制字符串
	 */
	public static String expand(String hexStr, String delimiter) {
		StringBuilder expandedHex = new StringBuilder();
		for (int i = 0; i < hexStr.length(); i += 2) {
			expandedHex.append(hexStr, i, i + 2).append(delimiter);
		}
		// 移除最后一个分隔符
		return expandedHex.length() > 0 ? expandedHex.substring(0,
			expandedHex.length() - delimiter.length()) : "";
	}

	/**
	 * 合拢十六进制字符串，移除所有空格和分隔符。
	 *
	 * @param hexExpandStr 十六进制字符串（含分隔符）
	 * @return 合拢后的十六进制字符串
	 */
	public static String fold(String hexExpandStr) {
		return hexExpandStr.replaceAll("[^0-9A-Fa-f]", "");
	}

}
