package com.my.base.balancer.strategy;

import com.my.base.balancer.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description: 最少连接策略
 */
public class LeastConnectionsStrategy extends AbstractStrategy {

	@Override
	public StrategyEnums strategy() {
		return StrategyEnums.LEAST_CONNECTIONS;
	}

	@Override
	public Server nextHealthyServer() {
		List<Server> servers = healthyServers();
		if (isNoAvailableServers(servers)) {
			return null;
		}
		Server server = minConnectionsServer(servers);
		server.currentConnections().incrementAndGet();
		return server;
	}

}
