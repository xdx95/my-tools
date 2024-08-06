package com.my.base.balancer.strategy;

import java.util.List;

/**
 * @description:  负载均衡策略
 * @author: xdx
 * @date: 2024/8/2
 */
public interface LoadBalanceStrategy<T> {

	T nextServer(List<T> servers);

	T nextHealthyServer(List<T> servers);

}
