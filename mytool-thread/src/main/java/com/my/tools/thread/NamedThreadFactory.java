package com.my.tools.thread;

import com.my.tools.log.LogUtils;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/16
 * @description: 自定义线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

	public static final Logger log = LogUtils.get();

	//未捕获异常处理
	public static final UncaughtExceptionHandler exceptionHandler = new ExceptionHandler();
	// 全局线程组计数器
	private static final AtomicInteger groupNumber = new AtomicInteger(1);
	// 线程计数器
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	// 线程组
	private final ThreadGroup group;
	// 是否为守护线程，默认 false
	private final boolean isDaemon;
	// 线程优先级，默认为正常优先级,Thread.NORM_PRIORITY
	private final int priority;
	// 线程名前缀
	private final String namePrefix;


	public NamedThreadFactory(String namePrefix, boolean isDaemon, int priority) {
		this.isDaemon = isDaemon;
		// 检查线程优先级是否在合理范围内
		if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
			throw new IllegalArgumentException("线程优先级不合法: " + priority);
		}
		this.priority = priority;
		SecurityManager securityManager = System.getSecurityManager();
		this.group = (securityManager != null) ? securityManager.getThreadGroup()
			: Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-pool-" + groupNumber.getAndIncrement() + "-t-";
	}

	/**
	 * 创建一个新的线程
	 */
	@Override
	public Thread newThread(Runnable runnable) {
		String threadName = namePrefix + threadNumber.getAndIncrement();
		// 创建新线程,绑定任务runnable,并归属到线程组group
		Thread thread = new Thread(group, runnable, threadName, 0);
		if (thread.isDaemon() != isDaemon) {
			thread.setDaemon(isDaemon);
		}
		if (thread.getPriority() != priority) {
			thread.setPriority(priority);
		}
		//未捕获异常处理
		//thread.setUncaughtExceptionHandler(exceptionHandler);
		return thread;
	}

	/**
	 * @description: 处理线程内部未捕获异常，不会影响其他线程和主线程异常处理
	 * @author: xdx
	 * @date: 2024/8/20
	 */
	public static class ExceptionHandler implements UncaughtExceptionHandler {

		public static final Logger log = LogUtils.get();

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			log.error("thread:{},uncaught exception", t.getName(), e);
		}
	}

}

