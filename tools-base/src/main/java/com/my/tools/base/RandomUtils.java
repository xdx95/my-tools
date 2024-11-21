package com.my.tools.base;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: xdx
 * @date: 2024/9/7
 * @description: 随机工具类
 */
public class RandomUtils {

	/**
	 * 用于随机选的数字
	 */
	public static final String BASE_NUMBER = "0123456789";
	/**
	 * 用于随机选的字符
	 */
	public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * 用于随机选的字符和数字（小写）
	 */
	public static final String BASE_CHAR_NUMBER_LOWER = BASE_CHAR + BASE_NUMBER;
	/**
	 * 用于随机选的字符和数字（包括大写和小写字母）
	 */
	public static final String BASE_CHAR_NUMBER = BASE_CHAR.toUpperCase() + BASE_CHAR_NUMBER_LOWER;


	/**
	 * 获取随机数生成器对象<br> ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
	 **/
	public static ThreadLocalRandom getRandom() {
		return ThreadLocalRandom.current();
	}

	/**
	 * 获得随机Boolean值
	 *
	 * @return true or false
	 * @since 4.5.9
	 */
	public static boolean randomBoolean() {
		return 0 == randomInt(0, 2);
	}

	/**
	 * 随机bytes
	 *
	 * @param length 长度
	 * @return bytes
	 */
	public static byte[] randomBytes(final int length) {
		final byte[] bytes = new byte[length];
		getRandom().nextBytes(bytes);
		return bytes;
	}


	/**
	 * 获得随机数int值
	 *
	 * @return 随机数
	 * @see Random#nextInt()
	 */
	public static int randomInt() {
		return getRandom().nextInt();
	}

	/**
	 * 获得指定范围内的随机数 [0,limit) * @return 随机数
	 */
	public static int randomInt(final int min, int max) {
		return getRandom().nextInt(min, max);
	}


	/**
	 * 获得随机数
	 */
	public static long randomLong() {
		return getRandom().nextLong();
	}

	/**
	 * 获得指定范围内的随机数 [0,limit) * @return 随机数
	 */
	public static long randomLong(final int min, int max) {
		return getRandom().nextLong(min, max);
	}


	/**
	 * 获得一个随机的字符串（只包含数字和字符）
	 *
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(final int length) {
		return randomString(BASE_CHAR_NUMBER, length);
	}

	/**
	 * 获得一个随机的字符串（只包含数字和大写字符）
	 *
	 * @param length 字符串的长度
	 * @return 随机字符串
	 * @since 4.0.13
	 */
	public static String randomStringUpper(final int length) {
		return randomString(BASE_CHAR_NUMBER, length).toUpperCase();
	}


	/**
	 * 获得一个只包含数字的字符串
	 *
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String randomNumbers(final int length) {
		return randomString(BASE_NUMBER, length);
	}

	/**
	 * 获得一个随机的字符串
	 *
	 * @param baseString 随机字符选取的样本
	 * @param length     字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(final String baseString, int length) {
		if (baseString == null || baseString.isEmpty()) {
			return "";
		}
		if (length < 1) {
			length = 1;
		}

		final StringBuilder sb = new StringBuilder(length);
		final int baseLength = baseString.length();
		for (int i = 0; i < length; i++) {
			final int number = randomInt(0, baseLength);
			sb.append(baseString.charAt(number));
		}
		return sb.toString();
	}

}
