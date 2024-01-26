package com.plagiarism.plagiarismremover.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;
import com.plagiarism.plagiarismremover.config.MessageSourceConfig;

@WebMvcTest(PlagiarismRemoverController.class)
@Import(MessageSourceConfig.class)
@ExtendWith(MockitoExtension.class)
class PlagiarismRemoverControllerTest {
	
	@Mock
	private ChatGPTConfigProperties mockedChatGPTconfig;

	@Test
	void testConstructorInjection() {
		PlagiarismRemoverController plagiarismRemoverController = new PlagiarismRemoverController(mockedChatGPTconfig);
		assertNotNull(plagiarismRemoverController.getChatGPTconfig());
		assertEquals(mockedChatGPTconfig, plagiarismRemoverController.getChatGPTconfig());
	}
	
	@Test
	void testRemovePlagiarismEndpoint() {
		
	}
}