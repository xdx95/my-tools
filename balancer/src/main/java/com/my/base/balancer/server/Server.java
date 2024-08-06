package com.my.base.balancer.server;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 定义 server 属性，并提供默认实现和通用功能
 */
public class Server {

	private String name;
	private final String host;
	private final Integer port;
	private boolean healthy;

	// 平滑加权轮询相关
	private int weight;
	private int currentWeight = 0;// 初始化值
	private int totalWeight = 10;//默认为 10

	public Server(String host, Integer port, Integer weight, Integer totalWeight) {
		this.host = host;
		this.port = port;
		this.weight = weight;
		this.totalWeight = totalWeight;
	}

	public Server(String host, Integer port, Integer weight) {
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
		return healthy;
	}

	public void setHealthy(boolean healthy) {
		this.healthy = healthy;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Integer currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Integer getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}
}
