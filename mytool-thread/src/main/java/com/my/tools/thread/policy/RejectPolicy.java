package com.my.tools.thread.policy;

import com.my.tools.thread.policy.BlockingPolicy;
import com.my.tools.thread.policy.TimedBlockingPolicy;
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
	 * 阻塞等待
	 */
	Blocking(new BlockingPolicy()),

	/**
	 * 阻塞等待超时
	 */
	TimedBlocking(new TimedBlockingPolicy(1000, TimeUnit.SECONDS));

	private final RejectedExecutionHandler value;

	RejectPolicy(RejectedExecutionHandler handler) {
		this.value = handler;
	}

	/**
	 * 获取RejectedExecutionHandler枚举值
	 *
	 * @return RejectedExecutionHandler
	 */
	public RejectedExecutionHandler value() {
		return this.value;
	}

}
