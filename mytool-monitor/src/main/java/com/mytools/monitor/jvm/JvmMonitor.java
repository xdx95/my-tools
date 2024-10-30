package com.mytools.monitor.jvm;

import com.mytools.base.LogUtils;
import com.mytools.monitor.AbstractMonitor;
import com.mytools.monitor.MonitorType;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/28
 * @description: Jvm监控器
 */
public class JvmMonitor extends AbstractMonitor {

	public static final Logger log = LogUtils.get();
	private static final JvmMonitor INSTANCE = new JvmMonitor();

	/**
	 * 私有化构造方法，确保只能通过 getInstance() 获取实例
	 */
	private JvmMonitor() {

	}

	/**
	 * 获取单例实例
	 */
	public static JvmMonitor getInstance() {
		return INSTANCE;
	}


	@Override
	public MonitorType type() {
		return MonitorType.JVM;
	}

	@Override
	public Map<String, Object> collect() {
		Map<String, Object> jvmMap = new LinkedHashMap<>();
		// 获取内存管理 Bean
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
		JvmInfo jvmInfo = new JvmInfo();
		jvmInfo.setHeap_memory_init(heapMemoryUsage.getInit());
		jvmInfo.setHeap_memory_used(heapMemoryUsage.getUsed());
		jvmInfo.setHeap_memory_max(heapMemoryUsage.getMax());
		jvmInfo.setNon_heap_memory_init(nonHeapMemoryUsage.getInit());
		jvmInfo.setNon_heap_memory_used(nonHeapMemoryUsage.getUsed());
		jvmInfo.setNon_heap_memory_max(nonHeapMemoryUsage.getMax());
		jvmMap.put(type().name().toLowerCase(), jvmInfo);
		return jvmMap;
	}
}
