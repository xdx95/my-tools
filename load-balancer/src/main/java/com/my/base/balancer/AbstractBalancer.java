package com.my.base.balancer;

import com.my.base.balancer.server.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: 抽象类，提供默认实现和通用功能
 */
public abstract class AbstractBalancer implements LoadBalancer<Server> {

	private List<Server> serverList = new ArrayList<>();
	private final ConcurrentMap<String, Boolean> serverStatus = new ConcurrentHashMap<>();

	public AbstractBalancer() {
	}

	public AbstractBalancer(List<Server> serverList) {
		this.serverList = serverList;
	}

	@Override
	public LoadBalancer<Server> addServer(Server server) {
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
