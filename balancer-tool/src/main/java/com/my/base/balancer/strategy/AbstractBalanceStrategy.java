package com.my.base.balancer.strategy;

import com.my.base.balancer.context.BalancerContext;
import com.my.base.balancer.server.Server;
import java.util.stream.Collectors;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description:
 */
public abstract class AbstractBalanceStrategy implements
	BalanceStrategy<Server, BalancerContext> {

	public BalancerContext context;

	@Override
	public void context(BalancerContext context) {
		this.context = context;
	}

	@Override
	public Server nextServer() {
		return select(context.servers());
	}

	@Override
	public Server nextHealthyServer() {
		return select(
			context.servers().stream().filter(Server::isHealthy).collect(Collectors.toList()));
	}

	public void onSelect(Server server) {
		switch (strategy()) {
			case LEAST_CONNECTIONS:
				server.currentConnections().incrementAndGet();
			case RANDOM:
			case ROUND_ROBIN:
			case WEIGHTED_ROUND_ROBIN:
			default:
		}
	}
}
