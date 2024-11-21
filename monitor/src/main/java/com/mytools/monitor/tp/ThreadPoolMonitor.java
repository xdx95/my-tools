package com.mytools.monitor.tp;

import com.mytools.base.LogUtils;
import com.mytools.monitor.AbstractMonitor;
import com.mytools.monitor.MonitorType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/27
 * @description: 线程池监控器
 */
public class ThreadPoolMonitor extends AbstractMonitor {

	public static final Logger log = LogUtils.get();
	private static final ThreadPoolMonitor INSTANCE = new ThreadPoolMonitor();
	private final ConcurrentHashMap<String, ThreadPoolExecutor> executors = new ConcurrentHashMap<>();

	/**
	 * 私有化构造方法，确保只能通过 getInstance() 获取实例
	 */
	private ThreadPoolMonitor() {
	}

	/**
	 * 获取单例实例
	 */
	public static ThreadPoolMonitor getInstance() {
		return INSTANCE;
	}


	@Override
	public void stop() {
		executors.forEach((key, executor) -> {
			log.info("{}-线程池实例关闭:{}", type(), key);
			executor.shutdown();
		});
	}

	@Override
	public MonitorType type() {
		return MonitorType.THREAD;
	}

	@Override
	public Map<String, Object> collect() {
		Map<String, Object> threadPoolDataMap = new HashMap<>();
		executors.forEach((name, executor) -> {
			if (executor != null) {
				threadPoolDataMap.put(name, threadPoolData(executor));
			}
		});
		return threadPoolDataMap;
	}

	private ThreadPoolInfo threadPoolData(ThreadPoolExecutor executor) {
		ThreadPoolInfo threadPoolInfo = new ThreadPoolInfo();
		threadPoolInfo.setState(threadPoolState(executor));
		threadPoolInfo.setCore_pool_size(executor.getCorePoolSize());
		threadPoolInfo.setPool_size(executor.getPoolSize());
		threadPoolInfo.setLargest_pool_size(executor.getLargestPoolSize());
		threadPoolInfo.setMax_pool_size(executor.getMaximumPoolSize());
		threadPoolInfo.setCompleted_task(executor.getCompletedTaskCount());
		threadPoolInfo.setActive_task(executor.getActiveCount());
		threadPoolInfo.setQueue_task(executor.getQueue().size());
		threadPoolInfo.setTask(executor.getTaskCount());
		return threadPoolInfo;
	}

	private String threadPoolState(ThreadPoolExecutor executor) {
		if (executor.isTerminated()) {
			return "TERMINATED";// shutdown() 或 shutdownNow() 方法完成执行，线程池完全终止
		} else if (executor.isTerminating()) {
			return "TERMINATING";//线程池已经调用了 shutdown() 或 shutdownNow()，正在终止但未完全终止
		} else if (executor.isShutdown()) {
			// 如果线程池被 shutdown() 不再接受新任务，但会继续处理队列中的任务，直到没有执行任务，为 STOP
			return (executor.getQueue().isEmpty() && executor.getActiveCount() == 0) ? "STOP" : "SHUTDOWN";
		} else {
			return "RUNNING";
		}
	}

	/**
	 * 注册线程池
	 *
	 * @param executor 线程池
	 */
	public void register(String name, ThreadPoolExecutor executor) {
		log.info("{}-线程池实例注册:{}", type(), name);
		executors.put(name, executor);
	}
}
