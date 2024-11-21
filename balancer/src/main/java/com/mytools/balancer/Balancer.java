package com.mytools.balancer;

public interface Balancer<T> {

	T nextHealthyServer();
}
