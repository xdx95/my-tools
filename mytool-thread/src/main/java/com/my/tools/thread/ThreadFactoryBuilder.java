package com.my.tools.thread;

/**
 * @author: xdx
 * @date: 2024/8/20
 * @description: 线程工厂构造器
 */
public class ThreadFactoryBuilder {

	private String namePrefix = "default";
	private boolean isDaemon = false;
	private int priority = Thread.NORM_PRIORITY;

	public static ThreadFactoryBuilder create() {
		return new ThreadFactoryBuilder();
	}

	public ThreadFactoryBuilder namePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
		return this;
	}

	public ThreadFactoryBuilder isDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
		return this;
	}

	/**
	 * @author: xdx
	 * @date: 2024/8/22
	 * @description:
	 * @param: priority
	 * @return: com.my.tools.thread.ThreadFactoryBuilder
	*/
	public ThreadFactoryBuilder priority(int priority) {
		this.priority = priority;
		return this;
	}

	public NamedThreadFactory build() {
		return new NamedThreadFactory(namePrefix, isDaemon, priority);
	}
}
