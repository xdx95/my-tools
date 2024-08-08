package com.my.base.balancer.strategy;

import com.my.base.balancer.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 轮询策略
 */
public class RoundRobinStrategy extends AbstractStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.ROUND_ROBIN;
	}

	@Override
	public Server nextHealthyServer() {
		List<Server> servers = healthyServers();
		if (isNoAvailableServers(servers)) {
			return null;
		}
		int idx = context.roundRobinIndex().getAndIncrement() % servers.size();
		return servers.get(idx);
	}
}
