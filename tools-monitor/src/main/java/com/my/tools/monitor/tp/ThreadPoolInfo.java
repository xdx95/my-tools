package com.my.tools.monitor.tp;

/**
 * @author: xdx
 * @date: 2024/9/5
 * @description: 任务详情
 */
public class ThreadPoolInfo {

	    private String state;
		private long core_pool_size;
		private long pool_size;
		private long largest_pool_size;
		private long max_pool_size;
		private long completed_task;
		private long active_task;
		private long queue_task;
		private long task;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getCore_pool_size() {
		return core_pool_size;
	}

	public void setCore_pool_size(long core_pool_size) {
		this.core_pool_size = core_pool_size;
	}

	public long getPool_size() {
		return pool_size;
	}

	public void setPool_size(long pool_size) {
		this.pool_size = pool_size;
	}

	public long getLargest_pool_size() {
		return largest_pool_size;
	}

	public void setLargest_pool_size(long largest_pool_size) {
		this.largest_pool_size = largest_pool_size;
	}

	public long getMax_pool_size() {
		return max_pool_size;
	}

	public void setMax_pool_size(long max_pool_size) {
		this.max_pool_size = max_pool_size;
	}

	public long getCompleted_task() {
		return completed_task;
	}

	public void setCompleted_task(long completed_task) {
		this.completed_task = completed_task;
	}

	public long getActive_task() {
		return active_task;
	}

	public void setActive_task(long active_task) {
		this.active_task = active_task;
	}

	public long getQueue_task() {
		return queue_task;
	}

	public void setQueue_task(long queue_task) {
		this.queue_task = queue_task;
	}

	public long getTask() {
		return task;
	}

	public void setTask(long task) {
		this.task = task;
	}
}
