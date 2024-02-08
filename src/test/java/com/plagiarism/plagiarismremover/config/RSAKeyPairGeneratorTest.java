package com.plagiarism.plagiarismremover.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.KeyPair;

import org.junit.jupiter.api.Test;

class RSAKeyPairGeneratorTest {

	@Test
	public void testGenerateKeyPair() {
        // Arrange
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGeneratorImpl();

        // Act and Assert
        assertDoesNotThrow(() -> {
        	KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

            // Additional assertions
            assertNotNull(keyPair);
            assertNotNull(keyPair.getPublic());
            assertNotNull(keyPair.getPrivate());
        });
    }
	
	private static class RSAKeyPairGeneratorImpl implements RSAKeyPairGenerator {
        // Implementation of the interface
    }

}
