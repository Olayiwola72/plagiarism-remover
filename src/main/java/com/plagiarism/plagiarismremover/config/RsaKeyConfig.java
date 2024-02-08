package com.plagiarism.plagiarismremover.config;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RsaKeyConfig implements RSAKeyPairGenerator, KeyPairFileable {
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	
	public RsaKeyConfig( 
			@Value("${rsa.public-key}") String publicKeyPath,
			@Value("${rsa.private-key}") String privateKeyPath
	) throws Exception{
		if(areKeyFilesExist(publicKeyPath, privateKeyPath)) {
			this.setPublicKey(publicKeyPath);
			this.setPrivateKey(privateKeyPath);			
		}else {
			KeyPair keyPair = generateKeyPair();
			
			this.setPublicKey((RSAPublicKey) keyPair.getPublic());
			this.setPrivateKey((RSAPrivateKey) keyPair.getPrivate());
			
            writePublicKeyToPemFile(this.getPublicKey(), publicKeyPath);
			writePrivateKeyToPemFile(this.getPrivateKey(), privateKeyPath); 
		}
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setPublicKey(String publicKeyPath) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		this.publicKey = (RSAPublicKey) getPublicKeyFromFile(publicKeyPath);
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	public void setPrivateKey(String privateKeyPath) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		this.privateKey = (RSAPrivateKey) getPrivateKeyFromFile(privateKeyPath);
	}
}
