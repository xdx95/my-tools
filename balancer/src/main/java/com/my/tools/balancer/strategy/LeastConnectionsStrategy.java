package com.my.tools.balancer.strategy;

import com.my.tools.balancer.server.Server;
import java.util.Comparator;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description: 最少连接策略
 */
public class LeastConnectionsStrategy extends AbstractBalanceStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.LEAST_CONNECTIONS;
	}

	@Override
	public Server select(List<Server> servers) {
		if (servers.isEmpty()) {
			return null;
		}
		return servers.stream().min(Comparator.comparingInt(Server::getCurrentConnections))
			.orElse(null);
	}

}
