package thread;

import com.my.tools.base.LogUtils;
import com.my.tools.base.RandomUtils;
import com.my.tools.monitor.MonitorConfig;
import com.my.tools.monitor.MonitorManager;
import com.my.tools.thread.ThreadUtils;
import com.my.tools.thread.core.NamedThreadFactory;
import com.my.tools.thread.policy.RejectPolicy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
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
	void interruptedExceptionExample() throws InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,10,TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1),
			new NamedThreadFactory("test"),
			RejectPolicy.Blocking.value());

		// 提交两个任务，第一个任务占用线程，第二个任务放入队列
		executor.submit(() -> {
				ThreadUtils.sleep(10000); // 模拟任务执行，保持线程占用
				log.info("Task 1 完成...");
		});

		// 提交第二个任务，占用队列
		executor.submit(() -> log.info("Task 2 完成..."));

		// 使用另一个线程在短暂延迟后，中断主线程
		Thread submitThread = Thread.currentThread();
		Thread interruptThread  = new Thread(()->{
			ThreadUtils.sleep(2000);
			log.info("中断任务调用线程...");
			submitThread.interrupt();
		});
		interruptThread.setName("interrupt-1");
		interruptThread.start();

		// 提交第三个任务，触发拒绝策略
		try {
			executor.submit(() -> log.info("Task 3 完成..."));
		}catch (RejectedExecutionException e){
			log.error("Task 3 异常...",e);
		}
		// 提交第四个任务
		if(Thread.currentThread().isInterrupted()) {
			log.info("任务调用线程已被中断，不再执行后续任务...");
		}else {
			executor.submit(() -> log.info("Task 4 完成..."));
		}
		// 关闭线程池
		executor.shutdown();
	}


	@Test
	void newSingleThreadPool() {
		MonitorManager.getInstance()
			.setConfig(new MonitorConfig())
			.start();
		ThreadPoolExecutor threadPoolExecutor = ThreadUtils.newFixedThreadPool(3, "fixed");
		for (int i = 0; i < 20; i++) {
			threadPoolExecutor.submit(() -> {
				ThreadUtils.sleep(1000);
			});

		}
		ThreadUtils.sleep(6000);
	}

	@Test
	void newEagerThreadPool() {
		MonitorManager.getInstance()
			.start();
		ThreadPoolExecutor threadPoolExecutor = ThreadUtils.newEagerThreadPool("eager");
		for (int i = 0; i < 50; i++) {
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					ThreadUtils.sleep(RandomUtils.randomLong(0, 20000));
				}
			});
		}

		ThreadUtils.sleep(30000);
		threadPoolExecutor.shutdown();
	}
}