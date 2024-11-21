package com.my.tools.balancer.strategy;

import com.my.tools.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 轮询策略
 */
public class RoundRobinBalanceStrategy extends AbstractBalanceStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.ROUND_ROBIN;
	}

	@Override
	public Server select(List<Server> servers) {
		if (servers.isEmpty()) {
			return null;
		}
		int idx = context.roundRobinIndex().getAndIncrement() % servers.size();
		return servers.get(idx);
	}
}
