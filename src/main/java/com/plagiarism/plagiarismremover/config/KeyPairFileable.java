package com.plagiarism.plagiarismremover.config;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public interface KeyPairFileable {
	default PublicKey getPublicKeyFromFile(URI uri) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(uri));
        String keyString = new String(keyBytes);

        // Decode the Base64 encoded key
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        // Generate the public key object
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
	
	default void writePublicKeyToPemFile(PublicKey publicKey, URI fileName) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        String pem = Base64.getEncoder().encodeToString(keySpec.getEncoded());
        writePemFile(pem, fileName);
    }
	
	default PrivateKey getPrivateKeyFromFile(URI uri) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(uri));
        String keyString = new String(keyBytes);

        // Decode the Base64 encoded key
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        // Generate the private key object
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

	default void writePrivateKeyToPemFile(PrivateKey privateKey, URI uri) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        String pem = Base64.getEncoder().encodeToString(keySpec.getEncoded());
        writePemFile(pem, uri);
    }

	default void writePemFile(String pem, URI uri) throws Exception {
        File file = new File(uri);
        FileUtils.writeStringToFile(file, pem, "UTF-8");
    }
}
