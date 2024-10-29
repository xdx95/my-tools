package current;

import com.mytools.base.LogUtils;
import com.mytools.thread.ThreadUtils;
import java.util.concurrent.atomic.AtomicStampedReference;
import org.slf4j.Logger;

/**
 * 原子操作类测试
 *
 * @author xdx
 */
public class AtomicStampedTest {

	public static final Logger log = LogUtils.get();

	public static void main(String[] args) {
		String init = "init";
		String update = "update";
		String done = "done";
		// 带版本号引用类型
		AtomicStampedReference<String>  stampedRef= new AtomicStampedReference<>(init, 0);
		log.info("初始值:{}-{}", stampedRef.getReference(), stampedRef.getStamp());

		// 线程 1 进行 CAS 操作,将 init 更新为 update
		new Thread(() -> {
			int currentStamp = stampedRef.getStamp();
			boolean result = stampedRef.compareAndSet(init, update, currentStamp, currentStamp + 1);
			log.info("init 更新为 update 结果:{},{}-{}", result, stampedRef.getReference(),
				stampedRef.getStamp());
		}, "t-1").start();

		ThreadUtils.sleep(100); // 阻塞，让线程 1 执行完成

		// 线程 2 进行 CAS 操作，将 int 更新为 done
		new Thread(() -> {
			int currentStamp = stampedRef.getStamp();
			ThreadUtils.sleep(100); // 阻塞，让线程 3 执行完成
			// 不使用最新版本号
			boolean result2 = stampedRef.compareAndSet(init, done, currentStamp, currentStamp + 1);
			log.info("不是最新版本号，int 更新为 done 结果:{},{}-{}", result2, stampedRef.getReference(), stampedRef.getStamp());

			// 使用最新版本号
			currentStamp = stampedRef.getStamp();
			boolean result3 = stampedRef.compareAndSet(init, done, currentStamp, currentStamp + 1);
			log.info("最新版本号，int 更新为 done 结果:{},{}-{}", result3, stampedRef.getReference(),
				stampedRef.getStamp());
		}, "t-2").start();


		// 线程 3 模拟 ABA 问题
		new Thread(()->{
			int currentStamp = stampedRef.getStamp();
			// 将 update 恢复为 init
			boolean result = stampedRef.compareAndSet(update, init, currentStamp, currentStamp + 1);
			log.info("update 恢复为 init 结果:{},{}-{}", result, stampedRef.getReference(),
				stampedRef.getStamp());
		},"t-3").start();

	}

}
