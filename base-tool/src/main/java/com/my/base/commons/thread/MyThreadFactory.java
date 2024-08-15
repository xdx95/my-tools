package com.my.base.commons.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xdx
 * @date: 2024/8/7
 * @description:
 */
public class MyThreadFactory implements ThreadFactory {

	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	/**
	 *
	 * @param threadName 线程名
	 */
	public MyThreadFactory(String threadName) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		if (threadName == null || threadName.isEmpty()) {
			threadName = "pool";
		}
		namePrefix = threadName +"-"+poolNumber.getAndIncrement()+"-t-";
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r,
			namePrefix + threadNumber.getAndIncrement(),
			0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
