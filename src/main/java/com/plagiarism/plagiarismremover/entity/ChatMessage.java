package com.plagiarism.plagiarismremover.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;

@Entity
@Schema(description = "Chat Message Information")
public class ChatMessage {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "role", example = "user")
	private String role;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "content", example = "test content")
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
