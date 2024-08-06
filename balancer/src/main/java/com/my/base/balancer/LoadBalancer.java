package com.my.base.balancer;

import com.my.base.balancer.server.HealthChecker;
import com.my.base.balancer.server.Server;
import com.my.base.balancer.strategy.LoadBalanceStrategy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * LoadBalancingStrategy 负载均衡策略
 *
 * @author: xdx
 * @date: 2024/7/12
 * @description: 负载均衡器
 */
public class LoadBalancer {

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private final HealthChecker healthChecker;
	private final LoadBalanceStrategy<Server> loadBalanceStrategy;
	private final List<Server> severs;


	public Server nextServer() {
		return loadBalanceStrategy.nextServer(severs);
	}

	public Server nextHealthyServer() {
		return loadBalanceStrategy.nextHealthyServer(severs);
	}


	public LoadBalancer(LoadBalanceStrategy<Server> loadBalanceStrategy,
		HealthChecker healthChecker,
		List<Server> severs) {
		this.loadBalanceStrategy = loadBalanceStrategy;
		this.healthChecker = healthChecker;
		this.severs = severs;
		// 启动服务健康检查任务
		checkHealthy();
	}

	//
	private void checkHealthy(){
		executor.scheduleWithFixedDelay(()->{
			severs.forEach(server -> {
				if(!server.isHealthy()){
					healthChecker.isHealthy(server);
				}
			});
		},10,10, TimeUnit.SECONDS);

	}


	public static LoadBalancerBuilder builder() {
		return new LoadBalancerBuilder();
	}

	public static class LoadBalancerBuilder {

		private LoadBalanceStrategy<Server> loadBalanceStrategy;
		private HealthChecker healthChecker;
		private final List<Server> severs = new CopyOnWriteArrayList<>();

		public LoadBalancerBuilder loadBalanceStrategy(LoadBalanceStrategy<Server> loadBalanceStrategy) {
			this.loadBalanceStrategy = loadBalanceStrategy;
			return this;
		}

		public LoadBalancerBuilder healthChecker(HealthChecker healthChecker) {
			this.healthChecker = healthChecker;
			return this;
		}

		public LoadBalancerBuilder addServer(Server server) {
			severs.add(server);
			return this;
		}

		public LoadBalancerBuilder addServer(List<Server> severList) {
			severs.addAll(severList);
			return this;
		}

		public LoadBalancer build() {
			return new LoadBalancer(this.loadBalanceStrategy,healthChecker, this.severs);
		}
	}


}
