package com.my.tools;

import com.my.tools.log.LogUtils;
import com.my.tools.thread.ThreadUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: xdx
 * @date: 2024/8/16
 * @description:
 */
public class MyTools {


	private MyTools() {
	}

	/**
	 * ø所有的工具类
	 *
	 * @return 工具类名集合
	 */
	public static Set<Class<?>> getAllUtils() {
		Set<Class<?>> set = new HashSet<>();
		set.add(LogUtils.class);
		set.add(ThreadUtils.class);
		return set;
	}


}
