package com.plagiarism.plagiarismremover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.plagiarism.plagiarismremover.config.ChatGPTConfigProperties;
import com.plagiarism.plagiarismremover.entity.User;
import com.plagiarism.plagiarismremover.service.UserService;

@SpringBootApplication
@EnableConfigurationProperties({ 
	ChatGPTConfigProperties.class
})
public class PlagiarismRemoverApplication implements CommandLineRunner{
	@Value("${app.admin.username}")
	private String username;
	
	@Value("${app.admin.password}")
	private String password;
	
	@Value("${app.admin.user.roles}")
	private String roles;
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(PlagiarismRemoverApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// Create Spring Boot defaut admin user
		User admin1 = new  User();
		admin1.setUsername(username);
		admin1.setPassword(password);
		admin1.setRoles(roles);
		
		userService.create(admin1);
	}
}