package com.my.base.balancer;

import com.my.base.balancer.checker.HealthChecker;
import com.my.base.balancer.strategy.LoadBalanceStrategy;
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

	private final LoadBalancerContext<Server> context;
	private final LoadBalanceStrategy<Server> loadBalanceStrategy;
	private final HealthChecker<Server> healthChecker;

	@Override
	public Server nextHealthyServer() {
		return loadBalanceStrategy.nextHealthyServer();
	}

	public LoadBalancer(
		LoadBalancerContext<Server> context,
		LoadBalanceStrategy<Server> loadBalanceStrategy,
		HealthChecker<Server> healthChecker,
		List<Server> servers) {
		assert context != null;
		this.context = context == null ? new LoadBalancerContext<>() : context;
		assert loadBalanceStrategy != null;
		this.loadBalanceStrategy = loadBalanceStrategy;
		this.context.setStrategy(loadBalanceStrategy.strategy());
		assert healthChecker != null;
		this.healthChecker = healthChecker;
		assert servers != null;
		this.context.servers().addAll(servers);
	}

	public void init() {
		loadBalanceStrategy.setContext(context);
		healthChecker.start(context);
	}

	public static LoadBalancerBuilder builder() {
		return new LoadBalancerBuilder();
	}

	public static class LoadBalancerBuilder {

		private LoadBalancerContext<Server> context;
		private LoadBalanceStrategy<Server> loadBalanceStrategy;
		private HealthChecker<Server> healthChecker;
		private List<Server> servers;

		public LoadBalancerBuilder context(LoadBalancerContext<Server> context) {
			this.context = context;
			return this;
		}

		public LoadBalancerBuilder loadBalanceStrategy(
			LoadBalanceStrategy<Server> loadBalanceStrategy) {
			this.loadBalanceStrategy = loadBalanceStrategy;
			return this;
		}

		public LoadBalancerBuilder healthChecker(HealthChecker<Server> healthChecker) {
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
				this.context, this.loadBalanceStrategy, this.healthChecker, this.servers);
		}
	}


}
