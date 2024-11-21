package com.my.tools.thread;

import com.my.tools.thread.policy.RejectPolicy;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程常量
 */
public final class ExecutorConstant {

	public static final RejectedExecutionHandler DEFAULT_REJECT = RejectPolicy.CallerRuns.value();

	public static final int MAX_POOL_SIZE = 200;

	public static final int QUEUE_CAPACITY = 200;

	private ExecutorConstant() {
	}
}
