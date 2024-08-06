package com.my.base.balancer;

import com.my.base.balancer.server.HttpServerHealthChecker;
import com.my.base.balancer.server.Server;
import com.my.base.balancer.strategy.RoundRobinStrategy;
import java.util.ArrayList;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description:
 */
public class LoadBalancerContext {

	private final static LoadBalancer loadBalancer = LoadBalancer.builder()
		.loadBalanceStrategy(new RoundRobinStrategy())
		.addServer(new Server("127.0.0.1",8080))
		.addServer(new Server("127.0.0.1",8090))
		.addServer(new ArrayList<>())
		.build();

	static {
		Server server = loadBalancer.nextServer();
		server.getHost();
		Server healthyServer = loadBalancer.nextHealthyServer();
		healthyServer.getHost();
	}

}
