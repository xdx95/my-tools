package com.my.base.balancer;

import java.util.List;

/**
 *
 * LoadBalancingStrategy
 * 负载均衡策略
 * @author: xdx
 * @date: 2024/7/12
 * @description: 定义负载均衡器功能
 */
public interface LoadBalancer<T> {

	LoadBalancer<T> addServer(T t);

	void addServer(List<T> list);

	List<T> allServer();

	T nextServer();

	T nextHealthyServer();

	void markServerUp(T t);

	void markServerDown(T t);

}
