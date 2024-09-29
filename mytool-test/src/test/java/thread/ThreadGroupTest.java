package thread;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ThreadGroupTest {

	public static void main(String[] args) {
		// 删除文件任务
		Runnable task = new Runnable() {
			@Override
			public void run() {
				String filePath = "/etc/test-password";
				new File(filePath).deleteOnExit();
				System.out.println(
					Thread.currentThread().getThreadGroup().getName() + "-删除文件成功!");
			}
		};
		Callable<Future> callable = new Callable() {
			@Override
			public Object call() throws Exception {
				return null;
			}
		};
		// 自定义线程组
		ThreadGroup custom = new ThreadGroup("custom");
		new Thread(custom, task).start();

		// 当前线程组
		ThreadGroup current = Thread.currentThread().getThreadGroup();
		new Thread(current, task).start();

		// 自定义安全管理器
		System.setSecurityManager(new TestSecurityManager());
		ThreadGroup security = System.getSecurityManager().getThreadGroup();
		new Thread(security, task).start();
	}

	public static class TestSecurityManager extends SecurityManager {
		@Override
		public void checkDelete(String file) {
			// 禁止读取文件
			if (file.contains("test-password")) {
				throw new SecurityException(Thread.currentThread().getThreadGroup().getName()+"-此文件不允许删除!");
			}
		}

	}

}