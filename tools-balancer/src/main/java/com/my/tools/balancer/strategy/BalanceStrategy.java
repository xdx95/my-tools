package com.my.tools.balancer.strategy;

import java.util.List;

/**
 * @description: 负载均衡策略
 * @author: xdx
 * @date: 2024/8/2
 */
public interface BalanceStrategy<T,C> {

	StrategyEnums strategy();

	 void context(C c);

	T nextServer();

	T nextHealthyServer();

	T select(List<T> tList);

}
