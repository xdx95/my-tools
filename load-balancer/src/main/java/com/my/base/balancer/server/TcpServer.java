package com.my.base.balancer.server;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description:
 */
public class TcpServer  extends AbstractServer{

	public TcpServer(String host, int port) {
		super(host, port);
	}

	@Override
	public boolean checkHealth() {
		return false;
	}
}
