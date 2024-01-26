package com.plagiarism.plagiarismremover.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plagiarism.plagiarismremover.entity.TokenResponse;
import com.plagiarism.plagiarismremover.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${plagiarism-remover.base-path}")
@Tag(name = "Auth Controller", description = "Auth Controller API")
public class AuthController {
	private final TokenService tokenService;
	
	public AuthController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@Operation(summary = "Get Authorization Token")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Response token generated successfully", content = {
			@Content(schema = @Schema(implementation = TokenResponse.class)) }),
	})
	@SecurityRequirement(name = "Basic Auth")
	@PostMapping("${plagiarism-remover.endpoint.token}")
	public TokenResponse tokenResponse(Authentication authentication) {
		String token = tokenService.generateToken(authentication);
		return new TokenResponse(token);
	}
}