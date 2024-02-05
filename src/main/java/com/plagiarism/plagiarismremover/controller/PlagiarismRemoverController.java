package com.plagiarism.plagiarismremover.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;
import com.plagiarism.plagiarismremover.dto.ChatCompletion;
import com.plagiarism.plagiarismremover.dto.ChatMessageRequest;
import com.plagiarism.plagiarismremover.service.ChatGPTChatCompletionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("${plagiarism-remover.base-path}")
@Validated
@Tag(name = "Plagiarism Remover", description = "Plagiarism Remover API")
public class PlagiarismRemoverController {
	
	private final ChatGPTConfigProperties chatGPTconfig;
	private ChatGPTChatCompletionService chatGPTChatCompletionService;

	public PlagiarismRemoverController(ChatGPTConfigProperties chatGPTconfig) {
		this.chatGPTconfig = chatGPTconfig;
	}

	public ChatGPTConfigProperties getChatGPTconfig() {
		return this.chatGPTconfig;
	}
	
	@Operation(summary = "Remove plagiarism request to OpenAPI's ChatGPT Chat Completion Service")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Removed plagiarism succesfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ChatCompletion.class)) }),
	})
	@SecurityRequirement(name = "Bearer Key")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("${plagiarism-remover.endpoint.remove}")
	public ResponseEntity<ChatCompletion> remove( @Valid @RequestBody ChatMessageRequest requestBody) {
		try {
			// Process the received data
			chatGPTChatCompletionService = new ChatGPTChatCompletionService(chatGPTconfig, requestBody.getMessage());

//            if (rows != 0) {
			return ResponseEntity.ok(chatGPTChatCompletionService.postChatCompletionRequest());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }	
		} catch (Exception e) {
			// Handle exceptions, log them, and return an appropriate response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
