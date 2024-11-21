package com.mytools.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 无锁环形缓冲区.
 * 当缓冲区满之后，允许覆盖旧数据
 */
public class RingBuffer<T> {

	// 缓冲区大小
	private static final int DEFAULT_CAPACITY = 256;

	private final AtomicReferenceArray<T> buffer;
	private final int capacity;
	private final AtomicInteger writeCursor = new AtomicInteger(0);
	private final AtomicInteger readCursor = new AtomicInteger(0);

	public RingBuffer() {
		this(DEFAULT_CAPACITY);
	}

	public RingBuffer(int capacity) {
		this.capacity = capacity;
		this.buffer = new AtomicReferenceArray<>(this.capacity);
	}

	public void write(T data) {
		int currentWriteCursor;
		int nextWriteCursor;
		do {
			currentWriteCursor = writeCursor.get();
			nextWriteCursor = (currentWriteCursor + 1) % capacity;
			// 如果写入指针追赶上读取指针，表示缓冲区已满，覆盖最旧数据，并移动读取指针
			if (nextWriteCursor == readCursor.get()) {
				readCursor.incrementAndGet();
			}
		} while (!writeCursor.compareAndSet(currentWriteCursor, nextWriteCursor));
		// 写入数据
		buffer.set(currentWriteCursor, data);
	}

	// 单个数据读取并移除元素
	public T read() {
		int currentReadCursor;
		int nextReadCursor;
		T result;
		do {
			currentReadCursor = readCursor.get();
			if (currentReadCursor == writeCursor.get()) {
				return null; // 缓冲区为空
			}
			nextReadCursor = (currentReadCursor + 1) % capacity;
		} while (!readCursor.compareAndSet(currentReadCursor, nextReadCursor));
		// 读取并移除数据
		result = buffer.getAndSet(currentReadCursor, null);
		return result;
	}


	// 读取所有可用数据并移除
	public List<T> readAll() {
		int currentWriteCursor = writeCursor.get();
		int currentReadCursor = readCursor.get();
		int availableData = (currentWriteCursor - currentReadCursor + capacity) % capacity;

		List<T> resultList = new ArrayList<>();
		for (int i = 0; i < availableData; i++) {
			resultList.add(buffer.getAndSet((currentReadCursor + i) % capacity, null));
		}
		// 原子性更新读取指针
		readCursor.compareAndSet(currentReadCursor, (currentReadCursor + availableData) % capacity);
		return resultList;
	}

	// 获取当前可读数据的数量
	public int availableData() {
		int currentWriteCursor = writeCursor.get();
		int currentReadCursor = readCursor.get();
		return (currentWriteCursor - currentReadCursor + capacity) % capacity;
	}

	// 获取当前可写入空间的大小
	public int availableSpace() {
		return capacity - availableData();
	}


}
