package com.my.base.balancer.strategy;

import com.my.base.balancer.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 平滑加权轮询策略
 */
public class WeightedRoundRobinStrategy extends AbstractStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.WEIGHTED_ROUND_ROBIN;
	}

	@Override
	public Server nextHealthyServer() {
		List<Server> servers = healthyServers();
		if (isNoAvailableServers(servers)) {
			return null;
		}
		Server best = null;
		for (Server server : servers) {
			server.currentWeight().addAndGet(server.getWeight());
			if (best == null || server.getCurrentWeight() > best.getCurrentWeight()) {
				best = server;
			}
		}
		if (best != null) {
			best.currentWeight().addAndGet(-context.totalWeights().get());
		}
		return best;
	}
}
