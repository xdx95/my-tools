package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @author xdx
 */
public class ThreadTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		Runnable runnableTask = new Runnable() {
			@Override
			public void run() {
				System.out.println("RunnableTask...");
			}
		};
		Runnable runnableTask2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("RunnableTask2...");
			}
		};
		Callable<String> callableTask = new Callable() {
			@Override
			public Object call() throws Exception {
				System.out.println("CallableTask...");
				return "CallableTask";
			}
		};
		RunnableFuture<String> futureTask = new FutureTask<>(callableTask);
		Thread runnableThread = new Thread(runnableTask, "runnable");
		Thread callableThread = new Thread(futureTask, "callable");
		runnableThread.start();
		callableThread.start();
		System.out.println(futureTask.get());
	}

}
