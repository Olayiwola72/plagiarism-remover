package com.plagiarism.plagiarismremover.entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;

@Entity
public class ChatCompletion {
	private String model;
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

	@Override
	public String toString() {
		return "ChatCompletion [model=" + model + ", messages=" + messages + "]";
	}
}