package com.my.base.balancer.strategy;

import com.my.base.balancer.server.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: xdx
 * @date: 2024/8/2
 * @description:
 */
public abstract class AbstractStrategy implements LoadBalanceStrategy<Server> {

	private List<Server> serverList = new ArrayList<>();
	private final ConcurrentMap<String, Boolean> serverStatus = new ConcurrentHashMap<>();

	public AbstractStrategy() {
	}

	public AbstractStrategy(List<Server> serverList) {
		this.serverList = serverList;
	}

	@Override
	public LoadBalanceStrategy<Server> addServer(Server server) {
		serverList.add(server);
		return this;
	}

	@Override
	public void addServer(List<Server> list) {
		this.serverList = list;
	}

	@Override
	public List<Server> allServer() {
		return serverList;
	}

	@Override
	public Server nextServer() {
		return null;
	}

	@Override
	public Server nextHealthyServer() {
		return null;
	}

	@Override
	public void markServerUp(Server server) {
		serverStatus.put(server.getName(),true);
	}

	@Override
	public void markServerDown(Server server) {
		serverStatus.put(server.getName(),false);
	}



}
