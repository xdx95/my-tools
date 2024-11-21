package com.mytools.balancer.checker;

/**
 * @description: 健康检查器
 * @author: xdx
 * @date: 2024/8/6
 */
public interface HealthChecker {

	CheckerEnums type();

	boolean healthCheck(String host, int port);

	boolean healthCheck(String host, int port, int timeout);

}
