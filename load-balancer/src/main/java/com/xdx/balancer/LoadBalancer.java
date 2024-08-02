package com.xdx.balancer;

import java.util.List;

/**
 * @author: xdx
 * @date: 2024/7/12
 * @description: 定义负载均衡器功能
 */
public interface LoadBalancer<T> {

	LoadBalancer<T> addServer(T t);

	void addServer(List<T> list);

	List<T> allServer();

	T getServer();

	T getHealthyServer();

	void markServerUp(T t);

	void markServerDown(T t);

}
