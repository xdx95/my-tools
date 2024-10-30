package com.mytools.monitor.task;

/**
 * @author: xdx
 * @date: 2024/9/5
 * @description: 任务详情
 */
public class TaskInfo {

	private  long id;
	private  String name;
	private  String message;
	private  long start_time;
	private  long duration;

	public TaskInfo() {
	}

	public TaskInfo(long id, String name, long startTime) {
		this.id = id;
		this.name = name;
		this.start_time = startTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStarTime(long startTime) {
		this.start_time = startTime;
	}

	public void updateDuration(long endTime) {
		this.duration = endTime -this.start_time;
	}

	public long duration() {
		return duration;
	}
}
