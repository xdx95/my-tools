package com.my.base.balancer.checker;

import com.my.base.balancer.Server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: socket 服务健康检查器
 */
public class SocketServerHealthChecker extends AbstractChecker {

	private static final Logger log = LoggerFactory.getLogger(SocketServerHealthChecker.class);

	@Override
	public boolean checkHealthy(Server server) {
		boolean health = false;
		try (Socket socket = new Socket()) {
			// 创建一个Socket实例，并设置连接的超时时间
			socket.connect(new InetSocketAddress(server.getHost(), server.getPort()), 3000);
			// 如果连接成功，则认为服务可用
			health = true;
		} catch (IOException e) {
			log.error("SOCKET-服务健康检查异常:{}, {}", server.getName(), e.getMessage());
		}
		return health;
	}
}
