package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description: 平滑加权轮询策略，平衡地分配负载，避免了某些服务器长时间被过度请求。
 */
public class WeightedRoundRobinStrategy extends AbstractStrategy {

	@Override
	public Server nextServer(List<Server> servers) {
		Server best = null;
		for (Server server : servers) {
			server.setCurrentWeight(server.getCurrentWeight() + server.getWeight());
			if (best == null || server.getCurrentWeight() > best.getCurrentWeight()) {
				best = server;
			}
		}

		if (best != null) {
			best.setCurrentWeight(best.getCurrentWeight() - best.getTotalWeight());
		}
		return best;
	}
}
