package com.mytools.thread.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 命名线程工厂
 * @author sheng
 */
public class NamedThreadFactory implements ThreadFactory {

	// 线程池计数器
	private static final AtomicInteger pool_number = new AtomicInteger(1);
	// 线程计数器
	private final AtomicInteger thread_number = new AtomicInteger(1);
	// 线程组
	private ThreadGroup group;
	// 线程名前缀
	private String name_prefix;

	public NamedThreadFactory(String namePrefix) {
		this(namePrefix, null);
	}

	public NamedThreadFactory(String namePrefix, ThreadGroup threadGroup) {
		init(namePrefix, threadGroup);
	}

	private void init(String namePrefix, ThreadGroup threadGroup) {
		if (namePrefix == null || namePrefix.isEmpty()) {
			throw new IllegalArgumentException("thread name prefix cannot be empty");
		}
		this.name_prefix = namePrefix + "-" + pool_number.getAndIncrement();
		if (threadGroup == null) {
			threadGroup = new ThreadGroup(this.name_prefix);
			threadGroup.setDaemon(false);
			threadGroup.setMaxPriority(Thread.NORM_PRIORITY);
		}
		this.group = threadGroup;
	}


	@Override
	public Thread newThread(Runnable runnable) {
		String threadName = this.name_prefix + "-" + thread_number.getAndIncrement();
		return new Thread(group, runnable, threadName);
	}

	public String getNamePrefix() {
		return name_prefix;
	}
}

