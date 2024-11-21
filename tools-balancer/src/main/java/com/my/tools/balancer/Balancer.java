package com.my.tools.balancer;

public interface Balancer<T> {

	T nextHealthyServer();
}
