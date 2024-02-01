package com.plagiarism.plagiarismremover.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatMessageTest {
	
	@Test
	public void testParameterizedConstructor() {
		testGetSetRole();
		testGetSetContent();
	}
	
	@Test
	public void testGetSetRole() {
		ChatMessage chatMessage = new ChatMessage("role", "content");
		assertNotNull(chatMessage.getRole());
		assertEquals("role", chatMessage.getRole());
	}
	
	@Test
	public void testGetSetContent() {
		ChatMessage chatMessage = new ChatMessage("role", "content");
		assertNotNull(chatMessage.getContent());
		assertEquals("content", chatMessage.getContent());
	}
}
