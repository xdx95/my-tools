package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/5
 * @description: 最少连接策略
 */
public class LeastConnectionsStrategy extends AbstractStrategy{

	@Override
	public Server nextServer(List<Server> servers) {
		return null;
	}

}
