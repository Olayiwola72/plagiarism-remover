package com.plagiarism.plagiarismremover.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.plagiarism.plagiarismremover.config.MessageSourceConfig;
import com.plagiarism.plagiarismremover.config.PasswordConfig;
import com.plagiarism.plagiarismremover.config.RsaKeyConfig;
import com.plagiarism.plagiarismremover.config.SecurityConfig;
import com.plagiarism.plagiarismremover.repository.UserRepository;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.security.DelegatedBearerTokenAccessDeniedHandler;
import com.plagiarism.plagiarismremover.service.TokenService;
import com.plagiarism.plagiarismremover.service.UserService;

@WebMvcTest(PlagiarismRemoverController.class)
@Import({ 
	SecurityConfig.class,  
	RsaKeyConfig.class,
	UserService.class, 
	PasswordConfig.class,
	TokenService.class,
	DelegatedAuthenticationEntryPoint.class, 
	DelegatedBearerTokenAccessDeniedHandler.class,
	MessageSourceConfig.class
})
class PlagiarismRemoverControllerTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	void testConstructorInjection() {

	}
	
	@Test
	void testRemovePlagiarismEndpoint() {
		
	}
}