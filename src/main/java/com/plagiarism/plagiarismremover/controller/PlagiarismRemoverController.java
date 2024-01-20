package com.plagiarism.plagiarismremover.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;
import com.plagiarism.plagiarismremover.entity.ChatCompletion;
import com.plagiarism.plagiarismremover.entity.ChatMessageRequest;
import com.plagiarism.plagiarismremover.service.ChatGPTChatCompletionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${plagiarism-remover.base-path}")
@Validated
public class PlagiarismRemoverController {
	private final ChatGPTConfigProperties chatGPTconfig;
	private ChatGPTChatCompletionService chatGPTChatCompletionService;
	
	public PlagiarismRemoverController(ChatGPTConfigProperties chatGPTconfig) {
		this.chatGPTconfig = chatGPTconfig;
	}
	
	public ChatGPTConfigProperties getChatGPTconfig() {
		return this.chatGPTconfig;
	}
	
	@PostMapping("${plagiarism-remover.endpoint.remove}")
	public ResponseEntity<ChatCompletion> remove(@Valid @RequestBody ChatMessageRequest requestBody){
		try {
			// Process the received data
	        chatGPTChatCompletionService = new ChatGPTChatCompletionService(chatGPTconfig, requestBody.getMessage());	        

//            if (rows != 0) {
                return ResponseEntity.ok(chatGPTChatCompletionService.postChatCompletionRequest());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }	
		}catch (Exception e) {
            // Handle exceptions, log them, and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
}
