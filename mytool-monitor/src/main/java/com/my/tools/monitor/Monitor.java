package com.my.tools.monitor;

import java.util.Map;

/**
 * @author: xdx
 * @date: 2024/8/27
 * @description: 监控器
 */
public interface Monitor {

	// 执行数据采集
	Map<String, Object> collect();

	String type();

	void start();

	void stop();
}
