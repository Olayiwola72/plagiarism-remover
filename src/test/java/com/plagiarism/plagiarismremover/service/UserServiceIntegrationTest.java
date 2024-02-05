package com.plagiarism.plagiarismremover.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.plagiarism.plagiarismremover.adapter.UserPrincipal;
import com.plagiarism.plagiarismremover.config.TestConfig;
import com.plagiarism.plagiarismremover.config.PasswordConfig;
import com.plagiarism.plagiarismremover.config.RsaKeyConfig;
import com.plagiarism.plagiarismremover.config.SecurityConfig;
import com.plagiarism.plagiarismremover.entity.User;
import com.plagiarism.plagiarismremover.repository.UserRepository;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.security.DelegatedBearerTokenAccessDeniedHandler;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@Import({ 
	SecurityConfig.class,
	RsaKeyConfig.class,
	UserService.class,
	PasswordConfig.class,
	DelegatedAuthenticationEntryPoint.class, 
	DelegatedBearerTokenAccessDeniedHandler.class
})

public class UserServiceIntegrationTest {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    
    @Value("${app.admin.username}")
    private String adminUsername;
    
    @Value("${app.admin.password}")
    private String adminPassword;
    
    @Value("${app.admin.user.roles}")
    private String adminUserRoles;
    
    @Test
    void findAllShouldReturnAllUsers() {
    	List<User> allUsers = userService.findAll();
    	
    	// Perform assertions based on the expected data in the database
        assertEquals(1, allUsers.size()); // Adjust the expected size based on your test data

        // Optionally, perform more detailed assertions based on the specific data in your test scenario
        // For example, check the properties of each user in the list e.g. the admin user created by CommandLineRunner
        assertEquals(adminUsername, allUsers.get(0).getUsername());
        assertTrue(passwordEncoder.matches(adminPassword, allUsers.get(0).getPassword()));
        assertEquals(adminUserRoles, allUsers.get(0).getRoles());
    }
    
    @Test
    void findByIdShouldNotReturnNullWithValidUser() {
    	User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles("USER");

        User createdUser = userService.create(user);
        
    	User retrievedUser = userService.findById(createdUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
    }
    
    @Test
    void findByIdShouldThrowExceptionForNonexistentUser() {
        assertThrows(ObjectNotFoundException.class, () -> userService.findById(123456789L));
    }

    @Test
    void createShouldEncodePasswordAndSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles("USER");

        User createdUser = userService.create(user);

        assertEquals("testUser", createdUser.getUsername());
        assertTrue(passwordEncoder.matches("password", createdUser.getPassword()));
        assertEquals("USER", createdUser.getRoles());

        User retrievedUser = userService.findById(createdUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("testUser", retrievedUser.getUsername());
        assertTrue(passwordEncoder.matches("password", retrievedUser.getPassword()));
        assertEquals("USER", retrievedUser.getRoles());
    }

    @Test
    void updateShouldUpdateUser() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setPassword("existingPassword");
        existingUser.setRoles("USER");

        userService.create(existingUser);

        User updateUser = new User();
        updateUser.setId(existingUser.getId());
        updateUser.setUsername("updatedUser");
        updateUser.setRoles("ADMIN");

        User updatedUser = userService.update(updateUser);

        assertEquals("updatedUser", updatedUser.getUsername());
        assertTrue(passwordEncoder.matches("existingPassword", updatedUser.getPassword())); // Password should remain unchanged
        assertEquals("ADMIN", updatedUser.getRoles());

        User retrievedUser = userService.findById(updatedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("updatedUser", retrievedUser.getUsername());
        assertTrue(passwordEncoder.matches("existingPassword", retrievedUser.getPassword())); // Password should remain unchanged
        assertEquals("ADMIN", retrievedUser.getRoles());
    }
    
    @Test
    void updateShouldThrowExceptionForNonexistentUser() {
        assertThrows(ObjectNotFoundException.class, () -> userService.findById(123456789L));
    }
    

    @Test
    void deleteByIdShouldDeleteUser() {
        User existingUser = new User();
        existingUser.setUsername("userToDelete");
        existingUser.setPassword("password");
        existingUser.setRoles("USER");

        userService.create(existingUser);

        userService.deleteById(existingUser.getId());

        assertFalse(userRepository.findById(existingUser.getId()).isPresent());
        assertThrows(ObjectNotFoundException.class, () -> userService.findById(existingUser.getId()));
    }

    @Test
    void loadUserByUsernameShouldReturnUserPrincipal() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles("USER");

        userService.create(user);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof UserPrincipal);
        assertEquals("testUser", userDetails.getUsername());
        assertTrue(passwordEncoder.matches("password", userDetails.getPassword()));
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionForNonexistentUser() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistentUser"));
    }
  
}
