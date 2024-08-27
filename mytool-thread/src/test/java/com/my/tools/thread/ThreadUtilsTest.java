package com.my.tools.thread;

import com.my.tools.log.LogUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class ThreadUtilsTest {

	public static final Logger log = LogUtils.get();

	@Test
	void newThreadFactory() {
		new NamedThreadFactory("test");
		// 测试是否自动重命名
		new NamedThreadFactory("test");
		new NamedThreadFactory("test");
	}

	@Test
	void newSingleThreadPool() {
		ThreadPoolExecutor threadPoolExecutor = ThreadUtils.newSingleThreadPool("test");
		log.info(ThreadUtils.getThreadPoolName(threadPoolExecutor));
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		log.info(ThreadUtils.getThreadPoolName((ThreadPoolExecutor) executorService));
		executorService.shutdownNow();
		threadPoolExecutor.shutdownNow();
	}
}