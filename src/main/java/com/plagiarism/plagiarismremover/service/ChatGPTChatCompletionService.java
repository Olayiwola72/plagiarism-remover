package com.plagiarism.plagiarismremover.service;

import com.plagiarism.plagiarismremover.config.ChatGPTConfig;
import com.plagiarism.plagiarismremover.dto.ChatCompletion;

public class ChatGPTChatCompletionService {
	private final ChatCompletion chatCompletion;
	
	public ChatGPTChatCompletionService(ChatGPTConfig chatGPTconfig, String requestMessage){
		this.chatCompletion = new ChatCompletion(
									chatGPTconfig.getModel(),
									chatGPTconfig.getRole(),
									chatGPTconfig.getInstruction() +" "+ requestMessage
								);
	}
	
	public ChatCompletion getChatCompletion() {
		return this.chatCompletion;
	}
	
	public ChatCompletion postChatCompletionRequest() {
//		RestTemplate restTemplate = new RestTemplate();
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//	    headers.set("Authorization", "Bearer "+chatGPTconfig.getApiKey());  // Set the Authorization header


//	    HttpEntity<ChatCompletion> request = new HttpEntity<>(this.getChatCompletion(), headers);

//	    ResponseEntity<ChatCompletion> response = restTemplate.postForEntity(chatGPTconfig.getApiUrl(), request, ChatCompletion.class);


//	    return request.getBody();
		
		return chatCompletion;
	}
}