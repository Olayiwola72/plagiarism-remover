package com.plagiarism.plagiarismremover.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.plagiarism.plagiarismremover.config.PasswordConfig;
import com.plagiarism.plagiarismremover.config.SecurityConfig;
import com.plagiarism.plagiarismremover.config.TestConfig;
import com.plagiarism.plagiarismremover.entity.User;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.security.DelegatedBearerTokenAccessDeniedHandler;
import com.plagiarism.plagiarismremover.service.UserService;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@Import({ 
	SecurityConfig.class,  
	PasswordConfig.class,
	UserService.class, 
	DelegatedAuthenticationEntryPoint.class, 
	DelegatedBearerTokenAccessDeniedHandler.class
})

class UserRepositoryTest {

	@Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setRoles("USER");

        // Save the user to the repository
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("testpassword", foundUser.get().getPassword());
        assertEquals("USER", foundUser.get().getRoles());
    }

    @Test
    public void testFindByUsernameNotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // Then
        assertFalse(foundUser.isPresent());
    }

}
