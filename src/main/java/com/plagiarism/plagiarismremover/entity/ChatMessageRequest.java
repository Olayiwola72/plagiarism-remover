package com.plagiarism.plagiarismremover.entity;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@Component
@Schema(description = "Chat Message Request Information")
public class ChatMessageRequest {
	@NotBlank(message = "{NotBlank}")
	@Schema(description = "message from the user", example = "remove plaigiarism from me")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}