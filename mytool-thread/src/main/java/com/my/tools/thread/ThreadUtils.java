package com.my.tools.thread;

import com.my.tools.log.LogUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/16
 * @description: 线程工具类
 */
public class ThreadUtils {

	public static final Logger log = LogUtils.get();


	public static ThreadFactory newThreadFactory(String namePrefix) {
		return new NamedThreadFactory(namePrefix);
	}

	public static ThreadPoolExecutor newSingleThreadPool(String namePrefix) {
		return ThreadPoolBuilder.create()
			.corePoolSize(1)
			.maxPoolSize(1)
			.keepAliveTime(0L)
			.timeUnit(TimeUnit.SECONDS)
			.threadFactory(newThreadFactory(namePrefix))
			.build();
	}

	public static String getThreadPoolName(ThreadPoolExecutor executor) {
		ThreadFactory threadFactory = executor.getThreadFactory();
		if (threadFactory instanceof NamedThreadFactory) {
			NamedThreadFactory namedThreadFactory = (NamedThreadFactory) threadFactory;
			return namedThreadFactory.getNamePrefix();
		} else {
			log.warn("unsupported thread factory types:{}",threadFactory.getClass());
		}
		return "";

	}

	private ThreadUtils() {
	}
}
