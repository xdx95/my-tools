package com.my.tools.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class LogUtilsTest {

	public static final Logger log = LogUtils.get();

	@BeforeEach
	void setUp() {
		log.info("before...");
	}

	@Test
	public void getLog() {
		log.info("test get logger...");
	}
}