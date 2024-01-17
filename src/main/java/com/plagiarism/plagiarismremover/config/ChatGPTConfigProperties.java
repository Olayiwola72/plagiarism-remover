package com.plagiarism.plagiarismremover.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public class ChatGPTConfigProperties {
	private String apiUrl;
	private String apiKey;
	private String model;
	private String role;
	private String instruction;
	
	public String getApiUrl() {
		return this.apiUrl;
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getApiKey() {
		return this.apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getInstruction() {
		return this.instruction;
	}
	
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
