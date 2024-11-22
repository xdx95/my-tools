package com.my.tools.thread;

import com.my.tools.base.LogUtils;
import com.my.tools.thread.core.EagerTaskQueue;
import com.my.tools.thread.core.NamedThreadFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
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
			.workQueue(new LinkedBlockingQueue<>(ExecutorConstant.QUEUE_CAPACITY))
			.threadFactory(newThreadFactory(namePrefix))
			.rejectedHandler(ExecutorConstant.DEFAULT_REJECT)
			.build();
	}

	public static ThreadPoolExecutor newFixedThreadPool(int nThread, String namePrefix) {
		return ThreadPoolBuilder.create()
			.corePoolSize(nThread)
			.maxPoolSize(nThread)
			.keepAliveTime(0L)
			.timeUnit(TimeUnit.SECONDS)
			.workQueue(new LinkedBlockingQueue<>(ExecutorConstant.QUEUE_CAPACITY))
			.threadFactory(newThreadFactory(namePrefix))
			.rejectedHandler(ExecutorConstant.DEFAULT_REJECT)
			.build();
	}

	public static ScheduledThreadPoolExecutor newScheduledThreadPool(int nThread,
		String namePrefix) {
		return ThreadPoolBuilder.create()
			.corePoolSize(nThread)
			.maxPoolSize(nThread)
			.keepAliveTime(0L)
			.timeUnit(TimeUnit.SECONDS)
			.threadFactory(newThreadFactory(namePrefix))
			.buildScheduled();
	}

	public static ThreadPoolExecutor newCachedThreadPool(String namePrefix) {
		return ThreadPoolBuilder.create()
			.corePoolSize(1)
			.maxPoolSize(ExecutorConstant.MAX_POOL_SIZE)
			.keepAliveTime(60L)
			.timeUnit(TimeUnit.SECONDS)
			.workQueue(new SynchronousQueue<>())
			.threadFactory(newThreadFactory(namePrefix))
			.rejectedHandler(ExecutorConstant.DEFAULT_REJECT)
			.buildScheduled();
	}

	public static ThreadPoolExecutor newEagerThreadPool(String namePrefix) {
		return ThreadPoolBuilder.create()
			.corePoolSize(1)
			.maxPoolSize(ExecutorConstant.MAX_POOL_SIZE)
			.keepAliveTime(60L)
			.timeUnit(TimeUnit.SECONDS)
			.workQueue(new EagerTaskQueue(ExecutorConstant.QUEUE_CAPACITY))
			.threadFactory(newThreadFactory(namePrefix))
			.rejectedHandler(ExecutorConstant.DEFAULT_REJECT)
			.buildEager();
	}


	public static void sleep(long millis) {
		if (millis > 0) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException ignored) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static String currentThreadName() {
		return Thread.currentThread().getName();
	}

	public static String threadPoolName(ThreadPoolExecutor executor) {
		ThreadFactory threadFactory = executor.getThreadFactory();
		if (threadFactory instanceof NamedThreadFactory) {
			NamedThreadFactory namedThreadFactory = (NamedThreadFactory) threadFactory;
			return namedThreadFactory.getNamePrefix();
		} else {
			log.warn("unsupported thread factory types:{}", threadFactory.toString());
		}
		return "";
	}

	private ThreadUtils() {

	}
}
