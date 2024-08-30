package com.my.tools.thread;

import com.my.tools.base.LogUtils;
import com.my.tools.monitor.MonitorManager;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
		MonitorManager.getInstance().start();
		ThreadPoolExecutor threadPoolExecutor = ThreadUtils.newFixedThreadPool(3, "fixed");
		ScheduledExecutorService executorService = ThreadUtils.newScheduledThreadPool(2, "scheduled");
		for (int i = 0; i < 20; i++) {
			threadPoolExecutor.execute(() -> {
				ThreadUtils.sleep(8000);
			});
			executorService.scheduleWithFixedDelay(() -> {
				ThreadUtils.sleep(8000);
			}, 5, 5, TimeUnit.SECONDS);

		}


		ThreadUtils.sleep(60000);
	}
}