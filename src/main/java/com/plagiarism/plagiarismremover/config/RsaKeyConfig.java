package com.plagiarism.plagiarismremover.config;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RsaKeyConfig implements RSAKeyPairGenerator, KeyPairFileable {
	private URI publicKeyUri;
	private URI privateKeyUri;
	private boolean areKeyFilesExist;
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public RsaKeyConfig( 
			@Value("${rsa.public-key}") Resource publicKeyResource,
			@Value("${rsa.private-key}") Resource privateKeyResource
	) throws Exception{
		this.setPublicKeyUri(publicKeyResource.getURI());
		this.setPrivateKeyUri(privateKeyResource.getURI());
		
		this.setAreKeyFilesExist();
		
		if(!this.areKeyFilesExist) {
			KeyPair keyPair = generateKeyPair(); // from RSAKeyPairGenerator
			
			this.setPublicKey((RSAPublicKey) keyPair.getPublic());
			this.setPrivateKey((RSAPrivateKey) keyPair.getPrivate());
			
            writePrivateKeyToPemFile(this.getPrivateKey(), privateKeyUri); // KeyPairFileable
            writePublicKeyToPemFile(this.getPublicKey(), publicKeyUri); // KeyPairFileable
		}else {
			this.setPublicKey(publicKeyUri);
			this.setPrivateKey(privateKeyUri);
		}
	}
	
	public URI getPublicKeyUri() {
		return publicKeyUri;
	}


	public void setPublicKeyUri(URI publicKeyUri) {
		this.publicKeyUri = publicKeyUri;
	}

	public URI getPrivateKeyUri() {
		return privateKeyUri;
	}


	public void setPrivateKeyUri(URI privateKeyUri) {
		this.privateKeyUri = privateKeyUri;
	}

	public boolean isAreKeyFilesExist() throws IOException {
		return areKeyFilesExist;
	}
	
	public void setAreKeyFilesExist() {
		this.areKeyFilesExist = Files.exists(Paths.get(this.publicKeyUri)) &&
	               Files.exists(Paths.get(this.privateKeyUri));
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setPublicKey(URI publicKeyUri) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		this.publicKey = (RSAPublicKey) getPublicKeyFromFile(publicKeyUri);
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	/**
	 * @param privateKey the privateKey to set
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void setPrivateKey(URI privateKeyUri) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		this.privateKey = (RSAPrivateKey) getPrivateKeyFromFile(privateKeyUri);
	}
}
