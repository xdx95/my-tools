package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xdx
 */
public class Interrupt2Test {

	public static void main(String[] args) {

		Thread t1 = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("interrupted");
						throw new InterruptedException();
					}
				}
			} catch (Exception e) {
				System.out.println(Thread.currentThread().isInterrupted());
				System.out.println("Thread was interrupted.");
			}
		});

		Thread int1 = new Thread(() -> {
			try {
				Thread.sleep(2000);
				t1.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t1.start();
		int1.start();

		//System.out.println(Thread.currentThread().isInterrupted());

	}


}
