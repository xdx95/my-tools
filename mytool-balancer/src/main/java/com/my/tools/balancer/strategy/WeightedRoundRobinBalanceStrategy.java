package com.my.tools.balancer.strategy;

import com.my.tools.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 平滑加权轮询策略
 */
public class WeightedRoundRobinBalanceStrategy extends AbstractBalanceStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.WEIGHTED_ROUND_ROBIN;
	}

	@Override
	public Server select(List<Server> servers) {
		if (servers.isEmpty()) {
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
