package com.my.tools.thread.policy;

import com.my.tools.base.LogUtils;
import com.my.tools.thread.ThreadUtils;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;

/**
 * 阻塞等待策略
 *
 * @author xdx
 */
public class BlockingPolicy implements RejectedExecutionHandler {

	private static final Logger log = LogUtils.get();

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(ThreadUtils.currentThreadName()+"  执行阻塞等待策略...");
		if(!executor.isShutdown()) {
			try {
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RejectedExecutionException("任务中断",e);
			}
		}
	}
}

