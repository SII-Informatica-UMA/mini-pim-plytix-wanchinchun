package com.uma.wanchinchun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WanchinchunApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		System.out.println("Test for loading pim-schema.sql started...");
		Thread.sleep(5000);
		System.out.println("Test for loading pim-schema.sql finished.");
	}

}
