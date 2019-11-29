package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static java.nio.file.StandardOpenOption.CREATE;


public class RsaEncryptor implements Encryptor {

    private String pathToKey;

    public RsaEncryptor(String pathToKey) {
        this.pathToKey = pathToKey;
    }

    private String generateKeysAndGetPaths() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            final KeyPair keyPair = keyPairGenerator.generateKeyPair();
            final PublicKey publicKey = keyPair.getPublic();
            final PrivateKey privateKey = keyPair.getPrivate();

            final Path publicKeyPath = Path.of("/publicKey.pub");
            final Path privateKeyPath = Path.of("/privateKey.key");

            Files.write(publicKeyPath, publicKey.getEncoded(), CREATE);
            Files.write(privateKeyPath, privateKey.getEncoded(), CREATE);

            return "Public key: " + publicKeyPath.toAbsolutePath()
                    + "\nPrivate key: " + privateKeyPath.toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String path) {
        return path.substring(path.length() - 3);
    }

    @Override
    public String encode(String text) {
        if (pathToKey.isEmpty()) {
            return generateKeysAndGetPaths();
        }

        if (!getFileExtension(pathToKey.trim()).equals("key")) {
            return null;
        }

        try {
            final PrivateKey privateKey = getPrivateKeyFromFile(pathToKey.trim());
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            final byte[] encodedBytes = cipher.doFinal(text.getBytes());

            final Path pathToEncodedFile = Path.of("/encodedFile.txt");
            Files.write(pathToEncodedFile, encodedBytes, CREATE);

            return pathToEncodedFile.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey getPrivateKeyFromFile(String path) {
        try {
            final byte[] bytes = Files.readAllBytes(Paths.get(path));
            final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(String text) {
        if (pathToKey.isEmpty()) {
            return generateKeysAndGetPaths();
        }

        if (!getFileExtension(pathToKey).equals("pub")) {
            return null;
        }

        try {
            final PublicKey publicKey = getPublicKeyFromFile(pathToKey.trim());
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            final Path pathToEncodedFile = Path.of(text.trim());
            final byte[] encodedBytes = Files.readAllBytes(pathToEncodedFile);

            final byte[] decodedBytes = cipher.doFinal(encodedBytes);

            return new String(decodedBytes, Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKeyFromFile(String path) {
        try {
            final byte[] bytes = Files.readAllBytes(Paths.get(path));
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPathToKey() {
        return pathToKey;
    }

    public void setPathToKey(String pathToKey) {
        this.pathToKey = pathToKey;
    }

}
