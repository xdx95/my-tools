package com.mytools.balancer.checker;

/**
 * @author: xdx
 * @date: 2024/8/16
 * @description:
 */
public abstract class AbstractHealthChecker implements HealthChecker {

	@Override
	public boolean healthCheck(String host, int port) {
		return healthCheck(host, port, 5000);
	}

	@Override
	public CheckerEnums type() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean healthCheck(String host, int port, int timeout) {
		throw new UnsupportedOperationException();
	}
}
