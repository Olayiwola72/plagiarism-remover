package com.plagiarism.plagiarismremover.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public record ChatGPTConfigProperties (
	 String apiUrl,
	 String apiKey,
	 String model,
	 String role,
	 String instruction
){}