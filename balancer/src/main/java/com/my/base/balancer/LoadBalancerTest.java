package com.my.base.balancer;

import com.my.base.balancer.checker.HttpServerHealthChecker;
import com.my.base.balancer.strategy.LeastConnectionsStrategy;
import com.my.base.balancer.strategy.WeightedRoundRobinStrategy;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/7
 * @description: 测试
 */
public class LoadBalancerTest {

	public static final Logger log = LoggerFactory.getLogger(LoadBalancerTest.class);

	private final static LoadBalancer loadBalancer = LoadBalancer.builder()
		.context(new LoadBalancerContext<>(true, 5))
		.loadBalanceStrategy(new WeightedRoundRobinStrategy())
		.healthChecker(new HttpServerHealthChecker("/actuator/health"))
		.addServer(new Server("127.0.0.1", 8080, 1))
		.addServer(new Server("127.0.0.1", 8090,"/health", 2))
		.addServer(new Server("127.0.0.1", 8088, 3))
		.addServer(new ArrayList<>())
		.build();

	private final static LoadBalancer loadBalancer2 = LoadBalancer.builder()
		.loadBalanceStrategy(new LeastConnectionsStrategy())
		.healthChecker(new HttpServerHealthChecker("/actuator/health"))
		.addServer(new Server("127.0.0.1", 8080, 1))
		.addServer(new Server("127.0.0.1", 8090,"/health" ,2))
		.addServer(new Server("127.0.0.1", 8088, 3))
		.addServer(new ArrayList<>())
		.build();

	public static void main(String[] args) throws InterruptedException {
		//loadBalancer.init();
		loadBalancer2.init();
		for (int i = 0; i < 100; i++) {
			//Server server = loadBalancer.nextHealthyServer();
			//log.info("server:{}", server.getName());
			Server server2 = loadBalancer2.nextHealthyServer();
			log.info("server2:{},{}", server2.getName(),server2.getCurrentConnections());
			Thread.sleep(1000);
		}
	}
}
