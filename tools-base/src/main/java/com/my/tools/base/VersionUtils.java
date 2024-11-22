package com.my.tools.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 版本工具
 */
public class VersionUtils {

	private static final String DEFAULT_VERSION = "unknown";

	/**
	 * 兼容本地发开和正式发布的 jar 包
	 */
	public static String getVersion(Class<?> clazz) {
		String version = getVersionFromPackage(clazz);
		if (DEFAULT_VERSION.equals(version)) {
			version = getVersionFromProperties();
		}
		return version;
	}

	/**
	 * META-INF/MANIFEST.MF 文件
	 */
	public static String getVersionFromPackage(Class<?> clazz) {
		Package pkg = clazz.getPackage();
		return (pkg != null && pkg.getImplementationVersion() != null)
			? pkg.getImplementationVersion() : DEFAULT_VERSION;
	}

	/**
	 * META-INF/maven/groupId/artifactId/pom.properties 文件
	 */
	public static String getVersionFromPom(Class<?> clazz, String groupId, String artifactId) {
		String resourcePath = String.format("/META-INF/maven/%s/%s/pom.properties", groupId,
			artifactId);
		try (InputStream input = clazz.getClassLoader().getResourceAsStream(resourcePath)) {
			if (input != null) {
				Properties props = new Properties();
				props.load(input);
				return props.getProperty("version", DEFAULT_VERSION);
			}
		} catch (IOException ignored) {
		}
		return DEFAULT_VERSION;
	}

	/**
	 * 自定义 version.properties
	 */
	public static String getVersionFromProperties() {
		try (InputStream input = VersionUtils.class.getResourceAsStream("/version.properties")) {
			if (input != null) {
				Properties props = new Properties();
				props.load(input);
				return props.getProperty("version", DEFAULT_VERSION);
			}
		} catch (IOException ignored) {
		}
		return DEFAULT_VERSION;
	}

	/**
	 * 系统启动参数
	 */
	public static String getVersionFromSystemProperty() {
		return System.getProperty("project.version", DEFAULT_VERSION);
	}
}
