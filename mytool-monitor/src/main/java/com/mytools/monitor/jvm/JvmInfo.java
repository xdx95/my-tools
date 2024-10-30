package com.mytools.monitor.jvm;

/**
 * @author: xdx
 * @date: 2024/9/5
 * @description: 任务详情
 */
public class JvmInfo {

	private long heap_memory_init;
	private long heap_memory_used;
	private long heap_memory_max;
	private long non_heap_memory_init;
	private long non_heap_memory_used;
	private long non_heap_memory_max;

	public long getHeap_memory_init() {
		return heap_memory_init;
	}

	public void setHeap_memory_init(long heap_memory_init) {
		this.heap_memory_init = heap_memory_init;
	}

	public long getHeap_memory_used() {
		return heap_memory_used;
	}

	public void setHeap_memory_used(long heap_memory_used) {
		this.heap_memory_used = heap_memory_used;
	}

	public long getHeap_memory_max() {
		return heap_memory_max;
	}

	public void setHeap_memory_max(long heap_memory_max) {
		this.heap_memory_max = heap_memory_max;
	}

	public long getNon_heap_memory_init() {
		return non_heap_memory_init;
	}

	public void setNon_heap_memory_init(long non_heap_memory_init) {
		this.non_heap_memory_init = non_heap_memory_init;
	}

	public long getNon_heap_memory_used() {
		return non_heap_memory_used;
	}

	public void setNon_heap_memory_used(long non_heap_memory_used) {
		this.non_heap_memory_used = non_heap_memory_used;
	}

	public long getNon_heap_memory_max() {
		return non_heap_memory_max;
	}

	public void setNon_heap_memory_max(long non_heap_memory_max) {
		this.non_heap_memory_max = non_heap_memory_max;
	}
}
