package com.my.base.balancer.server;

/**
 * @description:  健康检查器
 * @author: xdx
 * @date: 2024/8/6 
 */
public interface HealthChecker {

	boolean isHealthy(Server server);

}
