package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 随机策略
 */
public class RandomStrategy extends AbstractStrategy {

	@Override
	public Server nextServer(List<Server> servers) {
		if (servers == null || servers.isEmpty()) {
			return null;
		}
		int randomIndex = (int) (Math.random() * servers.size());
		return servers.get(randomIndex);
	}
}
