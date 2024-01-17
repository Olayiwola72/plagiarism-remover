package com.plagiarism.plagiarismremover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ChatGPTConfigProperties.class })
public class PlagiarismRemoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlagiarismRemoverApplication.class, args);
	}

}
