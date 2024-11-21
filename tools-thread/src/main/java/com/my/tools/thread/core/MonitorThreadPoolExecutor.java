package com.my.tools.thread.core;

import com.my.tools.monitor.task.TaskInfo;
import com.my.tools.monitor.task.TaskMonitor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 监控线程池
 */
public class MonitorThreadPoolExecutor extends ThreadPoolExecutor {

	private final ThreadLocal<TaskInfo> threadLocal = new ThreadLocal<>();

	public MonitorThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
		TimeUnit unit, BlockingQueue<Runnable> workQueue,
		ThreadFactory threadFactory,
		RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
			handler);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		TaskInfo taskInfo = new TaskInfo(t.getId(), t.getName(), System.currentTimeMillis());
		threadLocal.set(taskInfo);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		TaskInfo taskInfo = threadLocal.get();
		taskInfo.updateDuration(System.currentTimeMillis());
		TaskMonitor.getInstance().register(taskInfo);
		threadLocal.remove();
	}
}
