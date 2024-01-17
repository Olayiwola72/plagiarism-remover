package com.plagiarism.plagiarismremover.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@Component
public class ChatMessageRequest {
	@NotBlank(message = "{NotBlank}")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}