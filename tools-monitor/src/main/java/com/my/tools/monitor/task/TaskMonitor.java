package com.my.tools.monitor.task;

import com.my.tools.base.LogUtils;
import com.my.tools.monitor.AbstractMonitor;
import com.my.tools.monitor.MonitorManager;
import com.my.tools.monitor.MonitorType;
import com.my.tools.monitor.RingBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/9/3
 * @description: 任务监控器
 */
public class TaskMonitor extends AbstractMonitor {

	public static final Logger log = LogUtils.get();

	private static final TaskMonitor INSTANCE = new TaskMonitor();

	private final RingBuffer<TaskInfo> ringBuffer = new RingBuffer<>();

	private static final int slowTaskTime = MonitorManager.getInstance().getConfig().getSlowTaskTime();

	private TaskMonitor() {

	}

	/**
	 * 获取单例实例
	 */
	public static TaskMonitor getInstance() {
		return INSTANCE;
	}


	/**
	 * 注册任务
	 */
	public void register(TaskInfo taskInfo) {
		if (taskInfo.duration() > slowTaskTime) {
			ringBuffer.write(taskInfo);
		}
	}

	@Override
	public Map<String, Object> collect() {
		Map<String, Object> taskMap = new LinkedHashMap<>();
		taskMap.put(type().name().toLowerCase(),ringBuffer.readAll());
		return taskMap;
	}

	@Override
	public MonitorType type() {
		return MonitorType.TASK;
	}

}
