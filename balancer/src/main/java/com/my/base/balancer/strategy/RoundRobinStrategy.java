package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 轮询策略
 */
public class RoundRobinStrategy extends AbstractStrategy {

	private final AtomicInteger index = new AtomicInteger(0);

	@Override
	public Server nextServer(List<Server> servers) {
		if (servers == null || servers.isEmpty()) {
			return null;
		}
		int idx = index.getAndIncrement() % servers.size();
		return servers.get(idx);
	}
}
