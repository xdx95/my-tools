package com.my.base.balancer.strategy;

import com.my.base.balancer.LoadBalancerContext;
import com.my.base.balancer.Server;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description:
 */
public abstract class AbstractStrategy implements LoadBalanceStrategy<Server> {

	private static final Logger log = LoggerFactory.getLogger(AbstractStrategy.class);

	public LoadBalancerContext<Server> context;

	public boolean isNoAvailableServers(List<Server> servers) {
		if (servers == null || servers.isEmpty()) {
			log.error("no available server...");
			return true;
		}
		return false;
	}

	public List<Server> healthyServers() {
		return context.servers().stream().filter(Server::isHealthy)
			.collect(Collectors.toList());
	}

	public Server minConnectionsServer(List<Server> servers) {
		return servers.stream().min(Comparator.comparingInt(Server::getCurrentConnections)).get();
	}

	@Override
	public void setContext(LoadBalancerContext<Server> context) {
		this.context = context;
	}
}
