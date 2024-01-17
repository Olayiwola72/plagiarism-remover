package com.plagiarism.plagiarismremover.entity;

import jakarta.persistence.Entity;

@Entity
public class ChatMessage {
	private String role;
	private String content;
	
	public ChatMessage(String role, String content) {
		this.setRole(role);
		this.setContent(content);
	}
	
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
