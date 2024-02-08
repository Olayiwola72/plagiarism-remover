package com.plagiarism.plagiarismremover.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.plagiarism.plagiarismremover.model.User;
import com.plagiarism.plagiarismremover.repository.UserRepository;
import com.plagiarism.plagiarismremover.service.UserService;

@Configuration
public class DBAdminUserInitializer {
	private String username;
	private String password;
	private String roles;
	private UserService userService;
	private UserRepository userRepository;
	
	public DBAdminUserInitializer(
			@Value("${app.admin.username}") String username,
			@Value("${app.admin.password}") String password,
			@Value("${app.admin.user.roles}") String roles,
			UserService userService,
			UserRepository userRepository
			) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRoles(roles);
		this.setUserService(userService);
		this.setUserRepository(userRepository);
		
		this.createAdminUser();
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private void createAdminUser() {
		Optional<User> optionalAdminUser = this.userRepository.findByUsername(this.getUsername());
				
		if(optionalAdminUser.isEmpty()) {
			User admin1 = new  User();
			admin1.setUsername(this.getUsername());
			admin1.setPassword(this.getPassword());
			admin1.setRoles(this.getRoles());
			
			this.userService.create(admin1);
		}
	}
}
