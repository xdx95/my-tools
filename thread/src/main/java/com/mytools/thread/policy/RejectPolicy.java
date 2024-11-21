package com.mytools.thread.policy;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略
 */
public enum RejectPolicy {

	/**
	 * 抛出异常
	 */
	Abort(new ThreadPoolExecutor.AbortPolicy()),
	/**
	 * 放弃当前任务
	 */
	Discard(new ThreadPoolExecutor.DiscardPolicy()),
	/**
	 * 抛弃最旧任务
	 */
	DiscardOldest(new ThreadPoolExecutor.DiscardOldestPolicy()),
	/**
	 * 调用者执行
	 */
	CallerRuns(new ThreadPoolExecutor.CallerRunsPolicy()),

	/**
	 * 阻塞
	 */
	Blocking(new BlockingPolicy()),

	/**
	 * 定时阻塞
	 */
	TimedBlocking(new TimedBlockingPolicy(1000, TimeUnit.SECONDS));

	private final RejectedExecutionHandler value;

	RejectPolicy(RejectedExecutionHandler handler) {
		this.value = handler;
	}

	public RejectedExecutionHandler value() {
		return this.value;
	}

}
