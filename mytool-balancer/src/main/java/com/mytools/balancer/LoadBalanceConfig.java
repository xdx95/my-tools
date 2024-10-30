package com.mytools.balancer;

/**
 * @author: xdx
 * @date: 2024/8/9
 * @description: 负载均衡配置
 */
public class LoadBalanceConfig {

	// 开启健康检查
	private boolean enableHealthCheck = true;
	// 健康检查间隔
	private int healthCheckInterval = 10000;
	// 健康检查超时时间
	private int healthCheckTimeout = 5000;

	public boolean isEnableHealthCheck() {
		return enableHealthCheck;
	}

	public void setEnableHealthCheck(boolean enableHealthCheck) {
		this.enableHealthCheck = enableHealthCheck;
	}

	public int getHealthCheckInterval() {
		return healthCheckInterval;
	}

	public void setHealthCheckInterval(int healthCheckInterval) {
		this.healthCheckInterval = healthCheckInterval;
	}

	public int getHealthCheckTimeout() {
		return healthCheckTimeout;
	}

	public void setHealthCheckTimeout(int healthCheckTimeout) {
		this.healthCheckTimeout = healthCheckTimeout;
	}
}
