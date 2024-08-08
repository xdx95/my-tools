package com.my.base.balancer.strategy;

import com.my.base.balancer.LoadBalancerContext;
import java.util.List;

/**
 * @description:  负载均衡策略
 * @author: xdx
 * @date: 2024/8/2
 */
public interface LoadBalanceStrategy<T> {

	StrategyEnums strategy();

	void setContext(LoadBalancerContext<T> context);

	T nextHealthyServer();

}
