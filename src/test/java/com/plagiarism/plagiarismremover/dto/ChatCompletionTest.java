package com.plagiarism.plagiarismremover.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatCompletionTest {
	@Test
	public void testDefaultConstructor() {
		ChatCompletion chatCompletion = new ChatCompletion();
		assertNotNull(chatCompletion.getMessages());
		assertEquals(0, chatCompletion.getMessages().size());
	}
	
	@Test 	
	public void testParameterizedConstructor() {
		String model = "TestModel";
	    String role = "TestRole";
	    String content = "TestContent";
	        
		ChatCompletion chatCompletion = new ChatCompletion(model, role, content);
		
		assertNotNull(chatCompletion.getModel());
		assertEquals(model, chatCompletion.getModel());
		
		assertNotNull(chatCompletion.getMessages());
		assertNotEquals(0, chatCompletion.getMessages().size());
	}
	
	@Test
	public void testGetAddMessages() {
		ChatMessage chatMessage = new ChatMessage("TestRole", "TestContent");
		
		ChatCompletion chatCompletion = new ChatCompletion();
		assertEquals(0, chatCompletion.getMessages().size());
		
		chatCompletion.addMessages(chatMessage);		
		assertEquals(1, chatCompletion.getMessages().size());
		
		assertEquals("TestRole", chatCompletion.getMessages().get(0).getRole());
		assertEquals("TestContent", chatCompletion.getMessages().get(0).getContent());
	}

	@Test
	public void testGetSetModel() {
		ChatCompletion chatCompletion = new ChatCompletion();
		chatCompletion.setModel("model");
		assertEquals("model", chatCompletion.getModel());
	}
}