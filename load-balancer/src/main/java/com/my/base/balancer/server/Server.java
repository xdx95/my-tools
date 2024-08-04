package com.my.base.balancer.server;

public interface Server {

	String getName();

	String getHost();

	Integer getPort();

	Integer getWeight();

	boolean isHealthy();

	void markHealth(boolean health);

	boolean checkHealth();



}
