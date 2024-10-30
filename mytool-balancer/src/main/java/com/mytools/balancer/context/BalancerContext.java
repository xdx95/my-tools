package com.mytools.balancer.context;

import com.mytools.balancer.LoadBalanceConfig;
import com.mytools.balancer.server.Server;
import com.mytools.balancer.strategy.AbstractBalanceStrategy;
import com.mytools.balancer.strategy.StrategyEnums;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/8
 * @description: 负载均衡器上下文，配置参数
 */
public class BalancerContext extends Context{

	private static final Logger log = LoggerFactory.getLogger(AbstractBalanceStrategy.class);

	private LoadBalanceConfig config;
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
	private final List<Server> servers = new ArrayList<>();

	public List<Server> servers() {
		return servers;
	}

	public List<Server> healthyServers() {
		return servers.stream().filter(Server::isHealthy).collect(Collectors.toList());
	}

	public Server minConnectionsServer(List<Server> servers) {
		return servers.stream().min(Comparator.comparingInt(Server::getCurrentConnections)).get();
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

	public StrategyEnums getStrategy() {
		return strategy;
	}

	public void setStrategy(StrategyEnums strategy) {
		this.strategy = strategy;
	}

	public LoadBalanceConfig config() {
		return config;
	}


}
