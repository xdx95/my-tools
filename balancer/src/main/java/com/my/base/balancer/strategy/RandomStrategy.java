package com.my.base.balancer.strategy;

import com.my.base.balancer.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 随机策略
 */
public class RandomStrategy extends AbstractStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.RANDOM;
	}

	@Override
	public Server nextHealthyServer() {
		List<Server> servers = healthyServers();
		if (isNoAvailableServers(servers)) {
			return null;
		}
		int randomIndex =context.random().nextInt(servers.size());
		return servers.get(randomIndex);
	}
}
