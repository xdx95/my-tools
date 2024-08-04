package com.my.base.balancer.server;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 定义 server 属性，并提供默认实现和通用功能
 */
public abstract class AbstractServer implements Server {

	private String name;
	private final String host;
	private final Integer port;
	private Integer weight;
	private boolean healthy;

	public AbstractServer(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * @description: 提供 server.name 的默认逻辑
	 * @author: xdx
	 * @date: 2024/8/2
	 */
	@Override
	public String getName() {
		if (name == null || name.isEmpty()) {
			return host + ":" + port;
		}
		return name;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	@Override
	public Integer getWeight() {
		return weight;
	}

	@Override
	public boolean isHealthy() {
		return healthy;
	}

	@Override
	public void markHealth(boolean health) {
		healthy = health;
	}

	@Override
	public abstract boolean checkHealth();
}
