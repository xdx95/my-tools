package com.my.tools.thread.factory;

import com.my.tools.log.LogUtils;
import com.my.tools.thread.RejectPolicy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class NamedThreadFactoryTest {

	public static final Logger log = LogUtils.get();
	public static final ExecutorService executor = Executors.newSingleThreadExecutor();


	@Test
	void newThread() throws ExecutionException, InterruptedException {

		RejectPolicy.ABORT.getValue();

		Runnable task = () -> {};
		Future<?> future = executor.submit(task);
		log.info("测试 submit runnable:{}" ,future.get());

		Future<?> future2 = executor.submit(task, "123456");
		log.info("测试 submit runnable result:{}" ,future2.get());

		Callable<String> task2 = () -> "123";
		Future<?> future3 = executor.submit(task2);
		log.info("测试 submit callable:{}" , future3.get());

		executor.shutdownNow();
	}
}