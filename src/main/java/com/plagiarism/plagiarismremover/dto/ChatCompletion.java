package com.plagiarism.plagiarismremover.dto;

import java.util.ArrayList;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;

@Schema(description = "Chat Completion Information")
public class ChatCompletion {
	@Id
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "model", example = "gpt-3.5-turbo")
	private String model;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "messages")
	private ArrayList<ChatMessage> messages;
	
	public ChatCompletion() {
		this.messages = new ArrayList<>();
	}
	
	public ChatCompletion(String model, String role, String content) {
		this();
		this.setModel(model);
		ChatMessage chatMessage = new ChatMessage(role, content);
		this.addMessages(chatMessage);
	}
	
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * @return the messages
	 */
	public ArrayList<ChatMessage> getMessages(){
		return this.messages;
	}
	
	/**
	 * @param messages the messages to set
	 */
	public void addMessages(ChatMessage message) {
		this.messages.add(message);
	}
}