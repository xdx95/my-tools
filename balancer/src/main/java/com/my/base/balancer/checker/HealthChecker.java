package com.my.base.balancer.checker;

import com.my.base.balancer.LoadBalancerContext;
import java.util.List;

/**
 * @description: 健康检查器
 * @author: xdx
 * @date: 2024/8/6
 */
public interface HealthChecker<T> {

	void start(LoadBalancerContext<T> context);

	boolean checkHealthy(T t);

	void checkHealthy(List<T> list);

	void scheduleCheckHealthy(List<T> list,int delay);


}
