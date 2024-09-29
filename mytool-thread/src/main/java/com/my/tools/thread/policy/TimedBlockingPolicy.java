package com.my.tools.thread.policy;

import com.my.tools.base.LogUtils;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 * 超时阻塞
 *
 * @author xdx
 */
public class TimedBlockingPolicy implements RejectedExecutionHandler {

	private static final Logger log = LogUtils.get();

	private final long timeout;
	private final TimeUnit timeUnit;

	public TimedBlockingPolicy(long timeout, TimeUnit timeUnit) {
		this.timeout = timeout;
		this.timeUnit = timeUnit;
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		if(!executor.isShutdown()) {
			try {
				if(!executor.getQueue().offer(r, timeout, timeUnit)){
					throw new RejectedExecutionException("the task waited to be queued for timeout");
				}
			} catch (InterruptedException e) {
				log.info("线程被中断",e);
				throw new RejectedExecutionException(e);
			}
		}
	}
}
