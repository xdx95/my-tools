package com.my.base.balancer;

import com.my.base.balancer.checker.HealthChecker;
import com.my.base.balancer.context.BalancerContext;
import com.my.base.balancer.server.Server;
import com.my.base.balancer.strategy.BalanceStrategy;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/7/12
 * @description: 负载均衡器
 */
public class LoadBalancer implements Balancer<Server> {

	private static final Logger log = LoggerFactory.getLogger(LoadBalancer.class);

	private final BalancerContext context;
	private final BalanceStrategy<Server,BalancerContext> balanceStrategy;
	private HealthChecker healthChecker;

	@Override
	public Server nextHealthyServer() {
		return balanceStrategy.nextHealthyServer();
	}

	public LoadBalancer(
		BalancerContext context,
		BalanceStrategy<Server,BalancerContext>  balanceStrategy,
		HealthChecker healthChecker,
		List<Server> servers) {

		this.context = (context == null ? new BalancerContext() : context);
		assert balanceStrategy != null;
		this.balanceStrategy = balanceStrategy;
		this.context.setStrategy(balanceStrategy.strategy());
		this.healthChecker = healthChecker;
		assert servers != null;
		this.context.servers().addAll(servers);
	}

	public void init() {
		log.info("load balancer start...");
		balanceStrategy.context(context);
	}

	public static LoadBalancerBuilder builder() {
		return new LoadBalancerBuilder();
	}

	public static class LoadBalancerBuilder {

		private BalancerContext  context;
		private BalanceStrategy<Server,BalancerContext>  balanceStrategy;
		private HealthChecker healthChecker;
		private List<Server> servers;

		public LoadBalancerBuilder context(BalancerContext context) {
			this.context = context;
			return this;
		}

		public LoadBalancerBuilder loadBalanceStrategy(
			BalanceStrategy<Server,BalancerContext>  balanceStrategy) {
			this.balanceStrategy = balanceStrategy;
			return this;
		}

		public LoadBalancerBuilder healthChecker(HealthChecker healthChecker) {
			this.healthChecker = healthChecker;
			return this;
		}

		public LoadBalancerBuilder addServer(Server server) {
			if (servers == null) {
				servers = new ArrayList<>();
			}
			servers.add(server);
			return this;
		}

		public LoadBalancerBuilder addServer(List<Server> severList) {
			if (servers == null) {
				servers = new ArrayList<>();
			}
			servers.addAll(severList);
			return this;
		}

		public LoadBalancer build() {
			return new LoadBalancer(
				this.context,
				this.balanceStrategy,
				this.healthChecker, this.servers);
		}
	}


}
