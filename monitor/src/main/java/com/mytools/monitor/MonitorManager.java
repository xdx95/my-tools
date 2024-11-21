package com.mytools.monitor;

import com.mytools.base.DateUtils;
import com.mytools.base.JsonUtils;
import com.mytools.base.LogUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
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
	private static final List<Monitor> MONITORS = new CopyOnWriteArrayList<>();
	private static final ScheduledThreadPoolExecutor SCHEDULER = initMonitorScheduler();

	private MonitorConfig config;

	/**
	 * 私有构造方法，只能单例访问
	 */
	private MonitorManager() {
		// 默认配置
		this.config = new MonitorConfig();
	}

	public static MonitorManager getInstance() {
		return INSTANCE;
	}

	public MonitorConfig getConfig() {
		return config;
	}

	public MonitorManager setConfig(MonitorConfig monitorConfig) {
		this.config = monitorConfig;
		return this;
	}

	// 初始化
	static {
		initialize();
	}

	private static void initialize() {
		// 注册 JVM 关闭钩子，在应用退出时清理资源
		Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE::stop));
	}

	private void register(Monitor monitor) {
		log.info("manager registered monitor type:{}", monitor.type().name().toLowerCase());
		MONITORS.add(monitor);
	}

	public void start() {
		// 注册监控器
		Arrays.stream(config.getTypes()).forEach(type -> {
			MonitorType monitorType = MonitorType.getMonitorType(type);
			if (monitorType != null) {
				Monitor monitor = monitorType.getMonitor();
				INSTANCE.register(monitor);
				monitor.start();
			}
		});
		// 数据收集
		collect();
	}

	public void collect() {
		log.info("manager collect monitor data:{}-{}", config.getAppCode(), config.getTypes());
		SCHEDULER.scheduleAtFixedRate(() -> {
			Map<String, Object> monitorData = new LinkedHashMap<>();
			monitorData.put("app_code", config.getAppCode());
			monitorData.put("date_time", DateUtils.getCurrentDateTime());
			MONITORS.forEach((monitor) -> {
				monitorData.putAll(monitor.collect());
			});
			// 模拟数据发送到监控平台
			log.info(JsonUtils.formatAndPretty(monitorData));
		}, 1, config.getPeriod(), TimeUnit.SECONDS);
	}

	public void stop() {
		MONITORS.forEach(Monitor::stop);
		SCHEDULER.shutdown();
		log.info("manager monitor stop");
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
