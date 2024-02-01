package com.plagiarism.plagiarismremover.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {
	
	@Test
	void testDefaultConstructor() {
		User user = new User();
		assertTrue(user.isEnabled());
	}
	
	@Test
	void testGetSetId() {
		User user = new User();
		user.setId(123456789012345L);
		assertNotNull(user.getId());
		assertEquals(123456789012345L, user.getId());
	}
	
	@Test
	void testGetSetUsername() {
		User user = new User();
		user.setUsername("username");
		assertNotNull(user.getUsername());
		assertEquals("username", user.getUsername());
	}
		
	@Test
	void testGetSetPassword() {
		User user = new User();
		user.setPassword("password");
		assertNotNull(user.getPassword());
		assertEquals("password", user.getPassword());
	}
	
	@Test
	void testGetSetEnabled() {
		User user = new User();
		user.setEnabled(false);
		assertFalse(user.isEnabled());
	}
		
	@Test
	void testGetSetRoles() {
		User user = new User();
		user.setRoles("ADMIN");
		assertNotNull(user.getRoles());
		assertEquals("ADMIN", user.getRoles());
	}
}