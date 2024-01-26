package com.plagiarism.plagiarismremover.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenResponseTest {

	@Test
	public void testTokenConstructor() {
		testGetSetToken();
	}
	
	@Test
	public void testGetSetToken() {
		TokenResponse tokenResponse = new TokenResponse("eyJhbGciOiJSUzI1NiJ9");
		assertNotNull(tokenResponse.getToken());
		assertEquals("eyJhbGciOiJSUzI1NiJ9", tokenResponse.getToken());
	}
}
