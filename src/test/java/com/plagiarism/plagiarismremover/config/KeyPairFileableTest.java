package com.plagiarism.plagiarismremover.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KeyPairFileableTest {
	
	@Autowired
	public RSAKeyPairGenerator rSAKeyPairGenerator;

	@Test
	public void testAreKeyFilesExist() throws Exception {
		KeyPairFileableImpl keyPairFileableImpl = new KeyPairFileableImpl();
		
		// Arrange
		String publicKeyPath = "public_key.pem";
		String privateKeyPath = "private_key.pem";

		// Act
		boolean keyFilesExist = keyPairFileableImpl.areKeyFilesExist(publicKeyPath, privateKeyPath);

		// Assert
		assertFalse(keyFilesExist); // Assuming the files do not exist initially
	}

	@Test
	public void testPublicKeyAndPrivateKeyOperations() throws Exception {
		KeyPairFileableImpl keyPairFileableImpl = new KeyPairFileableImpl() {
			@Override
			public boolean areKeyFilesExist(String publicKeyPath, String privateKeyPath) {
				return false; // Assume files do not exist initially
			}
		};
		
		// Arrange
		String publicKeyPath = "public_key.pem";
		String privateKeyPath = "private_key.pem";
		
		// Generate key pair for testing
		RSAKeyPairGeneratorImpl rSAKeyPairGeneratorImpl = new RSAKeyPairGeneratorImpl();
		KeyPair keyPair = rSAKeyPairGeneratorImpl.generateKeyPair();	
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		// Act
		keyPairFileableImpl.writePublicKeyToPemFile(publicKey, publicKeyPath);
		keyPairFileableImpl.writePrivateKeyToPemFile(privateKey, privateKeyPath);
        PublicKey retrievedPublicKey = keyPairFileableImpl.getPublicKeyFromFile(publicKeyPath);
        PrivateKey retrievedPrivateKey = keyPairFileableImpl.getPrivateKeyFromFile(privateKeyPath);

		// Assert
        assertNotNull(retrievedPublicKey);
        assertNotNull(retrievedPrivateKey);
		assertTrue(Files.exists(Path.of(publicKeyPath)));
		assertTrue(Files.exists(Path.of(privateKeyPath)));

		// Verify content of generated files
		assertEquals(publicKey, retrievedPublicKey);
		assertEquals(privateKey, retrievedPrivateKey);

		// Clean up generated files
		Files.deleteIfExists(Path.of(publicKeyPath));
		Files.deleteIfExists(Path.of(privateKeyPath));
	}

	@Test
	public void testWritePemFile() throws Exception {
		KeyPairFileableImpl keyPairFileableImpl = new KeyPairFileableImpl();
		// Arrange
		String pemFilePath = "test_file.pem";
		String pemContent = "Test content";

		// Act
		keyPairFileableImpl.writePemFile(pemContent, pemFilePath);

		// Assert
		assertTrue(Files.exists(Path.of(pemFilePath)));

		// Verify content of generated file
		String savedContent = new String(Files.readAllBytes(Path.of(pemFilePath)));
		assertEquals(pemContent, savedContent.trim());

		// Clean up generated file
		Files.deleteIfExists(Path.of(pemFilePath));
	}

	private static class KeyPairFileableImpl implements KeyPairFileable {
		// rest of the implementation
	}
	
	private static class RSAKeyPairGeneratorImpl implements RSAKeyPairGenerator {
        // Implementation of the interface
    }
}
