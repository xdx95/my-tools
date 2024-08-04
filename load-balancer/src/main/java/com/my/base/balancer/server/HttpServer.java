package com.my.base.balancer.server;

import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xdx
 * @date: 2024/8/1
 * @description: checkHealthEndpoint 默认 /health
 */
public class HttpServer extends AbstractServer{

	public static final Logger log = LoggerFactory.getLogger(HttpServer.class);

	private static final String DEFAULT_HEALTH_ENDPOINT = "/health";

	public HttpServer(String host, int port) {
		super(host, port);
	}

	@Override
	public boolean checkHealth() {
		boolean health = false;
		try {
			URL url = new URL("http://" + getHost() + ":" + getPort() + DEFAULT_HEALTH_ENDPOINT);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			int responseCode = connection.getResponseCode();
			health= responseCode == 200;
		} catch (Exception ignored) {

		}
		markHealth(health);
		return health;
	}
}
