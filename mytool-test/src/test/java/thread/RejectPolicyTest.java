package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sheng
 */
public class RejectPolicyTest {

	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(
			1,
			1,
			0,
			TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1),
			new NamedThreadFactory("test"),
			new BlockingPolicy());

		// 第一个任务，占用线程
		executor.submit(() -> {
			try {
				System.out.println("-Task 1 运行...");
				Thread.sleep(3000);//保持线程占用
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		// 第二个任务，放入队列
		executor.submit(() -> System.out.println("-Task 2 运行..."));
		// 第三个任务，执行拒绝策略
		executor.submit(() -> System.out.println("-Task 3 运行..."));
		// 关闭线程池
		executor.shutdown();
	}

	/**
	 * 自定义拒绝策略
	 */
	public static class BlockingPolicy implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			System.out.println(Thread.currentThread().getName() + "-执行阻塞等待策略...");
			if (!executor.isShutdown()) {
				try {
					executor.getQueue().put(r);
				} catch (InterruptedException e) {
					throw new RejectedExecutionException("任务中断", e);
				}
			}
		}
	}

	/**
	 * 自定义线程工厂
	 */
	public static class NamedThreadFactory implements ThreadFactory {

		private final AtomicInteger thread_number = new AtomicInteger(1);
		private final ThreadGroup group;
		private final String name_prefix;

		public NamedThreadFactory(String namePrefix) {
			if (namePrefix == null || namePrefix.isEmpty()) {
				throw new IllegalArgumentException("thread name prefix cannot be empty");
			}
			this.name_prefix = namePrefix;
			this.group = new ThreadGroup(this.name_prefix);
			this.group.setDaemon(false);
			this.group.setMaxPriority(Thread.NORM_PRIORITY);
		}

		@Override
		public Thread newThread(Runnable runnable) {
			String threadName = this.name_prefix + "-" + thread_number.getAndIncrement();
			return new Thread(group, runnable, threadName);
		}
	}
}
