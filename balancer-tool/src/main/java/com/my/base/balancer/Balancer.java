package com.my.base.balancer;

public interface Balancer<T> {

	T nextHealthyServer();
}
