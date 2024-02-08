package com.plagiarism.plagiarismremover.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.plagiarism.plagiarismremover.model.User;
import com.plagiarism.plagiarismremover.repository.UserRepository;
import com.plagiarism.plagiarismremover.service.UserService;
import com.plagiarism.plagiarismremover.utils.TestConfig;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@Import({ 
	UserService.class,
	PasswordConfig.class,
})
class DBAdminUserInitializerTest {
	
	@Value("${app.admin.username}") 
	private String username;
	
	@Value("${app.admin.password}") 
	private String password;
	
	@Value("${app.admin.user.roles}")
	private String roles;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Test
	void testAdminUserCreation() {
		DBAdminUserInitializer dBAdminUserInitializer = new DBAdminUserInitializer(username, password, roles, userService, userRepository);
		
		assertNotNull(dBAdminUserInitializer.getUsername());
		assertEquals(username, dBAdminUserInitializer.getUsername());
		
		assertNotNull(dBAdminUserInitializer.getPassword());
		assertEquals(password, dBAdminUserInitializer.getPassword());
		
		assertNotNull(dBAdminUserInitializer.getRoles());
		assertEquals(roles, dBAdminUserInitializer.getRoles());
		
		assertNotNull(dBAdminUserInitializer.getUserService());
		
		assertNotNull(dBAdminUserInitializer.getUserRepository());
		
		Optional<User> optionalAdminUser = userRepository.findByUsername(username);
    	
    	// Perform assertions based on the expected data in the database
        assertTrue(optionalAdminUser.isPresent()); 
        assertTrue(passwordEncoder.matches(password, optionalAdminUser.get().getPassword())); // Password should remain unchanged
        assertEquals(roles, optionalAdminUser.get().getRoles());
	}
}
