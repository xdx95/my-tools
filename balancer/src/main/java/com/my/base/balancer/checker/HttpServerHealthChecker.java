package com.my.base.balancer.checker;

import com.my.base.balancer.Server;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: http 服务健康检查器
 */
public class HttpServerHealthChecker extends AbstractChecker {

	private static final Logger log = LoggerFactory.getLogger(HttpServerHealthChecker.class);

	private String healthEndpoint = "/health";

	public HttpServerHealthChecker() {

	}

	public HttpServerHealthChecker(String healthEndpoint) {
		this.healthEndpoint = healthEndpoint;
	}

	@Override
	public boolean checkHealthy(Server server) {
		boolean health = false;
		// 优先使用服务配置的 HealthEndpoint
		String healthEndpoint =
			(server.getHealthEndpoint() == null ||
				server.getHealthEndpoint().isEmpty()) ? this.healthEndpoint
				: server.getHealthEndpoint();
		String serverUrl = "http://" + server.getHost() + ":" + server.getPort() + healthEndpoint;
		try {
			URL url = new URL(serverUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			int responseCode = connection.getResponseCode();
			log.error("HTTP-服务健康检查:{}, {}", serverUrl, responseCode);
			health = responseCode == 200;
		} catch (Exception e) {
			log.error("HTTP-服务健康检查异常:{}, {}", serverUrl, e.getMessage());
		}
		return health;
	}
}
