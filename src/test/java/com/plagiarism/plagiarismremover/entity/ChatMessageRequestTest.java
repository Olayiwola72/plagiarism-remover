package com.plagiarism.plagiarismremover.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ChatMessageRequestTest {

	@Test
	void testGetSetMessage() {
		ChatMessageRequest chatMessageRequest = new ChatMessageRequest();
		
		chatMessageRequest.setMessage("message");
		assertNotNull(chatMessageRequest.getMessage());
		assertEquals("message", chatMessageRequest.getMessage());
	}

}
