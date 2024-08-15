package com.my.base.balancer.server;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description:
 */
public class Server {

	private String name;
	private final String host;
	private final Integer port;
	// 状态
	private final AtomicBoolean healthy = new AtomicBoolean(false);
	// 平滑加权
	private int weight;
	private final AtomicInteger currentWeight = new AtomicInteger(0);
	// 最少连接
	private final AtomicInteger currentConnections = new AtomicInteger(0);

	public Server(String name, String host, Integer port, int weight) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.weight = weight;
	}

	public Server(String host, Integer port, int weight) {
		this.host = host;
		this.port = port;
		this.weight = weight;
	}

	public Server(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getName() {
		if (name == null || name.isEmpty()) {
			return host + ":" + port;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public boolean isHealthy() {
		return healthy.get();
	}

	public void setHealthy(boolean healthy) {
		this.healthy.set(healthy);
	}

	public int getWeight() {
		return weight;
	}

	public AtomicInteger currentWeight() {
		return currentWeight;
	}

	public int getCurrentWeight() {
		return currentWeight.get();
	}

	public AtomicInteger currentConnections() {
		return currentConnections;
	}

	public int getCurrentConnections() {
		return currentConnections.get();
	}
}
