package com.plagiarism.plagiarismremover.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

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