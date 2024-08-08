package com.my.base.balancer.checker;

import com.my.base.balancer.LoadBalancerContext;
import com.my.base.balancer.Server;
import com.my.base.balancer.strategy.StrategyEnums;
import com.my.base.commons.thread.MyThreadFactory;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/7
 * @description:
 */
public abstract class AbstractChecker implements HealthChecker<Server> {

	private static final Logger log = LoggerFactory.getLogger(AbstractChecker.class);

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
		new MyThreadFactory("b-check"));

	public LoadBalancerContext<Server> context;

	@Override
	public void start(LoadBalancerContext<Server> context) {
		this.context = context;
		log.info("Health-check,start.....");
		checkHealthy(context.servers());
		if (context.isEnableScheduledChecks()) {
			scheduleCheckHealthy(context.servers(), context.getChecksInterval());
		}
	}

	@Override
	public void checkHealthy(List<Server> servers) {
		servers.forEach(server -> {
			boolean health = checkHealthy(server);
			if (health != server.isHealthy()) {
				changesStatus(server, health);
			}
		});
	}

	@Override
	public void scheduleCheckHealthy(List<Server> servers, int delay) {
		executor.scheduleWithFixedDelay(() -> {
			checkHealthy(servers);
		}, 10, delay, TimeUnit.SECONDS);
	}

	/**
	 * @param server 服务
	 * @param health 健康状态
	 * @description: 加权轮询和最小连接数，需要根据服务状态，动态变更数值。 服务上线:避免短时间命中内大量请求，设置预热连接数为当前最小连接数。
	 * 服务上下线:保证加权轮询的均衡性，需要重新计算 total weights ;
	 * @author: xdx
	 * @date: 2024/8/8
	 */
	public void changesStatus(Server server, boolean health) {
		log.info("Health-check,changes server status,{},{}", server.getName(), health);
		// 服务上线，预热当前连接数 = 可用服务的最小链接数
		if (health && StrategyEnums.LEAST_CONNECTIONS == context.getStrategy()) {
			Server minServer = context.servers().stream().filter(Server::isHealthy).min(
				Comparator.comparingInt(Server::getCurrentConnections)
			).get();
			server.currentConnections().set(minServer.getCurrentConnections());
		}
		// 服务上下线，重新计算总权重
		if (StrategyEnums.WEIGHTED_ROUND_ROBIN == context.getStrategy()) {
			int newTotal = context.totalWeights()
				.addAndGet(health ? server.getWeight() : -server.getWeight());
			log.info("Health-check,server {} {} , total weights:{}", server.getName(), health,
				newTotal);
		}
		server.setHealthy(health);
	}


}
