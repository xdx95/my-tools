package com.my.tools.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: xdx
 * @date: 2024/8/20
 * @description: 线程池执行器构造
 */
public class ExecutorBuilder {

	/** 默认的等待队列容量 */
	public static final int DEFAULT_QUEUE_CAPACITY = 1024;

	/**
	 * 初始池大小
	 */
	private int corePoolSize;
	/**
	 * 最大池大小（允许同时执行的最大线程数）
	 */
	private int maxPoolSize = Integer.MAX_VALUE;
	/**
	 * 线程存活时间，即当池中线程多于初始大小时，多出的线程保留的时长
	 */
	private long keepAliveTime = TimeUnit.SECONDS.toNanos(60);
	/**
	 * 队列，用于存放未执行的线程
	 */
	private BlockingQueue<Runnable> workQueue;
	/**
	 * 线程工厂，用于自定义线程创建
	 */
	private ThreadFactory threadFactory;
	/**
	 * 当线程阻塞（block）时的异常处理器，所谓线程阻塞即线程池和等待队列已满，无法处理线程时采取的策略
	 */
	private RejectedExecutionHandler handler;
	/**
	 * 线程执行超时后是否回收线程
	 */
	private Boolean allowCoreThreadTimeOut;


}
