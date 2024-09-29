package thread;

import com.my.tools.thread.ThreadPoolBuilder;
import com.my.tools.thread.ThreadUtils;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xdx
 */
public class InterruptTest {

	public static void main(String[] args) {

		ThreadPoolExecutor executor = ThreadPoolBuilder.create()
			.corePoolSize(1)
			.maxPoolSize(1)
			.keepAliveTime(10L)
			.timeUnit(TimeUnit.SECONDS)
			.workQueue(new ArrayBlockingQueue<>(1))
			.threadFactory(ThreadUtils.newThreadFactory("test"))
			.rejectedHandler(new BlockingPolicy())
			.build();

        // 调用线程
		Thread callerThread = Thread.currentThread();
		// 中断调用线程
		Thread interruptThread = new Thread(() -> {
			ThreadUtils.sleep(1000);
			System.out.println(ThreadUtils.currentThreadName() + "-中断调用线程...");
			callerThread.interrupt();
		});
		interruptThread.setName("interrupt-1");
		interruptThread.start();

		try {
			for (int i = 0; i < 5; i++) {
				int id = i;
				Runnable runnable = new Runnable() {
					final String task = "Task " + id;
					@Override
					public void run() {
						System.out.println(task + " 运行...");
						ThreadUtils.sleep(1000);//保持线程占用
					}
					@Override
					public String toString() {
						return task;
					}
				};
				executor.execute(runnable);
			}
		} catch (Exception e) {
			System.out.println("执行异常:" + e.getMessage());
		}

		// 检查线程中断状态
		if (callerThread.isInterrupted()) {
			System.out.println(callerThread.getName() + "-线程处于中断状态...");
		}

		// 关闭线程池
		executor.shutdown();
	}

	public static class BlockingPolicy implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			if (!executor.isShutdown()) {
				try {
					executor.getQueue().put(r);
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().isInterrupted());
					System.out.println(r.toString() + " 被中断...");
					Thread.currentThread().interrupt();
					throw new RejectedExecutionException("任务中断", e);
				}
			}
		}
	}

}
