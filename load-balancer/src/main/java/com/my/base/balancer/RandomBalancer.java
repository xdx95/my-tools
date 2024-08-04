package com.my.base.balancer;

import com.my.base.balancer.server.Server;
import java.util.List;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: 随机
 */
public class RandomBalancer extends AbstractBalancer{

	public RandomBalancer() {
		super();
	}

	public RandomBalancer(List<Server> serverList) {
		super(serverList);
	}
}
