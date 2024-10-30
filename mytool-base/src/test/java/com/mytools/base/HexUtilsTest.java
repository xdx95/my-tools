package com.mytools.base;


import com.mytools.base.HexUtils;
import com.mytools.base.LogUtils;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.slf4j.Logger;

public class HexUtilsTest {

	private static final Logger log = LogUtils.get();

	public static final String DATA = "12345";

	public static final String HEX_DATA = "3132333435";

	public static final String HEX_EXPAND_DATA = "31 32 33 34 35";

	@Test
	public void encodeToHex() {
		String hexStr = HexUtils.encodeToHex(DATA.getBytes(StandardCharsets.UTF_8));
		log.info("{}->{}",DATA,hexStr);
	}

	@Test
	public void decodeHex() {
		byte[] data = HexUtils.decodeHex(HEX_DATA);
		log.info("{}->{}",HEX_DATA,new String(data,StandardCharsets.UTF_8));
	}

	@Test
	public void expand() {
		String hexStr = HexUtils.expand(HEX_DATA," ");
		log.info("{}->{}",DATA,hexStr);
	}

	@Test
	public void fold() {
		String hexStr = HexUtils.fold(HEX_EXPAND_DATA);
		log.info("{}->{}",DATA,hexStr);
	}
}