package com.my.tools.thread;

import java.util.concurrent.ThreadFactory;

/**
 * @author: xdx
 * @date: 2024/8/16
 * @description: 线程工具类
 */
public class ThreadUtils {

	public static ThreadFactory getDefaultThreadFactory() {
		return ThreadFactoryBuilder.create().build();
	}

	public static ThreadFactory getThreadFactory(String namePrefix) {
		return ThreadFactoryBuilder.create().namePrefix(namePrefix).build();
	}

	public static ThreadFactory getThreadFactory(String namePrefix, boolean isDaemon,
		int priority) {
		return ThreadFactoryBuilder.create()
			.namePrefix(namePrefix)
			.isDaemon(isDaemon)
			.priority(priority)
			.build();
	}

}
