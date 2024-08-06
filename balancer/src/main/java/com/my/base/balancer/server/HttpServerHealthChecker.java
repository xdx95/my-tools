package com.my.base.balancer.server;

import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: HttpServerHealthChecker
 */
public class HttpServerHealthChecker implements HealthChecker {

	public static final Logger log = LoggerFactory.getLogger(HttpServerHealthChecker.class);

	private String healthEndpoint = "/health";

	public HttpServerHealthChecker() {
	}

	public HttpServerHealthChecker(String healthEndpoint) {
		this.healthEndpoint = healthEndpoint;
	}

	@Override
	public boolean isHealthy(Server server) {
		boolean health = false;
		String serverUrl = "http://" + server.getHost() + ":" + server.getPort() + healthEndpoint;
		try {
			URL url = new URL(serverUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			int responseCode = connection.getResponseCode();
			health = responseCode == 200;
		} catch (Exception e) {
			log.error("服务健康检查异常:{}", serverUrl, e);
		}
		server.setHealthy(health);
		return health;
	}


}
