package com.my.tools.balancer.strategy;

import com.my.tools.balancer.server.Server;
import java.util.List;
import java.util.Random;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 随机策略
 */
public class RandomBalanceStrategy extends AbstractBalanceStrategy {

	private final Random random = new Random();

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.RANDOM;
	}

	@Override
	public Server select(List<Server> servers) {
		if (servers.isEmpty()) {
			return null;
		}
		int randomIndex = random.nextInt(servers.size());
		return servers.get(randomIndex);
	}
}
