package com.plagiarism.plagiarismremover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;
import com.plagiarism.plagiarismremover.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ChatGPTConfigProperties.class, RsaKeyProperties.class })
public class PlagiarismRemoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlagiarismRemoverApplication.class, args);
	}

}
