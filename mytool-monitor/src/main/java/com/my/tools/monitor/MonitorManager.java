package com.my.tools.monitor;

import com.my.tools.base.JsonUtils;
import com.my.tools.base.LogUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/27
 * @description: 监控管理器
 */
public class MonitorManager {

	public static final Logger log = LogUtils.get();

	private static final MonitorManager INSTANCE = new MonitorManager();
	private static final ScheduledThreadPoolExecutor MONITOR_SCHEDULER = initMonitorScheduler();
	private final ConcurrentHashMap<String, Monitor> monitors = new ConcurrentHashMap<>();

	/**
	 * 私有构造方法，只能单例访问
	 */
	private MonitorManager() {}

	// 静态初始化方法
	static {
		initialize();
	}

	private static void initialize() {
		// 注册监控器
		INSTANCE.register(ThreadPoolMonitor.getInstance());
		INSTANCE.register(JvmMonitor.getInstance());
		// 注册 JVM 关闭钩子，在应用退出时清理资源
		Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE::stop));
	}


	public static MonitorManager getInstance() {
		return INSTANCE;
	}

	public void register(Monitor monitor) {
		if (!monitors.containsKey(monitor.type())) {
			log.info("注册监控器:{}", monitor.type());
			monitors.put(monitor.type(), monitor);
		} else {
			log.info("重复注册监控器:{}", monitor.type());
		}

	}

	public void start() {
		monitors.forEach((type, monitor) -> {
			monitor.start();
		});
		MONITOR_SCHEDULER.scheduleAtFixedRate(() -> {
			Map<String,Object> monitorData = new HashMap<>();
			monitorData.put("app_code","test");

			monitorData.put("date_time", new Date());
			monitors.forEach((type, monitor) -> {
				monitorData.put(monitor.type(),monitor.collect());
			});
			// 模拟数据发送到监控平台
			log.info(JsonUtils.obj2StringPretty(monitorData));
		}, 0, 10, TimeUnit.SECONDS);
		log.info("监控管理器-start");
	}

	public void stop() {
		monitors.forEach((type, monitor) -> {
			monitor.stop();
		});
		MONITOR_SCHEDULER.shutdown();
		log.info("监控管理器-stop");
	}

	/**
	 * 创建监控任务调度器
	 */
	private static ScheduledThreadPoolExecutor initMonitorScheduler() {
		ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1,
			new MonitorThreadFactory(),
			new DiscardPolicy()
		);
		scheduledExecutor.setMaximumPoolSize(16);
		scheduledExecutor.setKeepAliveTime(30, TimeUnit.SECONDS);
		return scheduledExecutor;
	}

	/**
	 * 自定义线程工厂类
	 */
	private static class MonitorThreadFactory implements ThreadFactory {

		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final SecurityManager securityManager = System.getSecurityManager();
		private final ThreadGroup group = (securityManager != null)
			? securityManager.getThreadGroup()
			: Thread.currentThread().getThreadGroup();

		@Override
		public Thread newThread(Runnable runnable) {
			String threadName = "monitor-" + threadNumber.getAndIncrement();
			Thread thread = new Thread(group, runnable, threadName, 0);
			thread.setDaemon(true);
			thread.setPriority(Thread.NORM_PRIORITY);
			return thread;
		}
	}

}
