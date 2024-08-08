package com.my.base.balancer;

import com.my.base.balancer.strategy.StrategyEnums;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xdx
 * @date: 2024/8/8
 * @description: 负载均衡器上下文，配置参数
 */
public class LoadBalancerContext<T> {

	// 负载策略
	private StrategyEnums strategy;
	// 轮询
	private final AtomicInteger roundRobinIndex = new AtomicInteger(0);
	// 最小链接
	private final AtomicInteger minConnections = new AtomicInteger(0);
	// 加权轮询
	private final AtomicInteger totalWeights = new AtomicInteger(0);
	// 随机
	private final Random random = new Random();
    // 服务列表
	private final List<T> servers = new ArrayList<>();

	// 开启定时检查
	private boolean enableScheduledChecks = true;
	// 服务健康检查间隔
	private int checksInterval = 10;


	public LoadBalancerContext() {

	}

	public LoadBalancerContext(boolean enableScheduledChecks, int checksInterval) {
		this.enableScheduledChecks = enableScheduledChecks;
		this.checksInterval = checksInterval;
	}

	public AtomicInteger roundRobinIndex() {
		return roundRobinIndex;
	}

	public AtomicInteger minConnections() {
		return minConnections;
	}

	public AtomicInteger totalWeights() {
		return totalWeights;
	}

	public Random random() {
		return random;
	}

	public List<T> servers() {
		return servers;
	}

	public StrategyEnums getStrategy() {
		return strategy;
	}

	public void setStrategy(StrategyEnums strategy) {
		this.strategy = strategy;
	}

	public boolean isEnableScheduledChecks() {
		return enableScheduledChecks;
	}

	public int getChecksInterval() {
		return checksInterval;
	}

}
