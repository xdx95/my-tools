package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description:
 */
public abstract class AbstractStrategy implements LoadBalanceStrategy<Server> {

	@Override
	public Server nextHealthyServer(List<Server> servers) {
		if (servers == null || servers.isEmpty()) {
			return null;
		}
		List<Server> healthyServers = servers.stream().filter(Server::isHealthy)
			.collect(Collectors.toList());
		return nextServer(healthyServers);
	}
}
