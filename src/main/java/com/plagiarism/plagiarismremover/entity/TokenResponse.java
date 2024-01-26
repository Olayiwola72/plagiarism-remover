package com.plagiarism.plagiarismremover.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;

@Entity
@Schema(description = "Token Response Information")
public class TokenResponse {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "auth token", example = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoi")
	private String token;
	
	public TokenResponse(String token) {
		this.setToken(token);
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}