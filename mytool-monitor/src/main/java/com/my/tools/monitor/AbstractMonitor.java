package com.my.tools.monitor;

import com.my.tools.base.LogUtils;
import org.slf4j.Logger;

/**
 * @author: xdx
 * @date: 2024/8/27
 * @description: 监控器服务
 */
public abstract class AbstractMonitor implements Monitor {

	private static final Logger log = LogUtils.get();

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		log.info("{}-监控-关闭",type());
	}

}
