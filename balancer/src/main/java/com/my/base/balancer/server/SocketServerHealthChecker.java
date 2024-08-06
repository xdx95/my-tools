package com.my.base.balancer.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description:
 */
public class SocketServerHealthChecker implements HealthChecker {

	@Override
	public boolean isHealthy(Server server) {
		try (Socket socket = new Socket()) {
			// 创建一个Socket实例，并设置连接的超时时间
			socket.connect(new InetSocketAddress(server.getHost(), server.getPort()), 10000);
			// 如果连接成功，则认为服务可用
			return true;
		} catch (IOException e) {
			// 如果连接失败（如：连接超时、服务器不可达等），则认为服务不可用
			return false;
		}
	}
}
