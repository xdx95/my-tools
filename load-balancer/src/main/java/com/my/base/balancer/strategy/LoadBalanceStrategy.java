package com.my.base.balancer.strategy;

import java.util.List;

/**
 * @description:  负载均衡策略
 * @author: xdx
 * @date: 2024/8/2
 */
public interface LoadBalanceStrategy<T> {

	LoadBalanceStrategy<T> addServer(T t);

	void addServer(List<T> list);

	List<T> allServer();

	T nextServer();

	T nextHealthyServer();

	void markServerUp(T t);

	void markServerDown(T t);

}
