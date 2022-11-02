package com.example.cme.palindromechecker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER"})
class PalindromeCheckerApplicationTests {

	@Value("${spring.mvc.pathmatch.matching-strategy}")
	String strategy;

	@Test
	void contextLoads() {
		assertEquals("ANT_PATH_MATCHER", strategy);
	}

}
