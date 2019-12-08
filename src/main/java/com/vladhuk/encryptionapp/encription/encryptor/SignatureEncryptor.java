package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

import static java.nio.file.StandardOpenOption.CREATE;


public class SignatureEncryptor implements Encryptor {

    private String pathToKey;

    public SignatureEncryptor(String pathToKey) {
        this.pathToKey = pathToKey;
    }

    private String generateKeysAndGetPaths() {
        try {
            final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
            final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            keyPairGen.initialize(1024, secureRandom);

            final KeyPair keyPair = keyPairGen.genKeyPair();
            final PrivateKey privateKey = keyPair.getPrivate();
            final PublicKey publicKey = keyPair.getPublic();

            final Path publicKeyPath = Path.of("/publicKey.pub");
            final Path privateKeyPath = Path.of("/privateKey.key");

            saveKey(publicKeyPath, publicKey);
            saveKey(privateKeyPath, privateKey);

            return "Public key: " + publicKeyPath.toAbsolutePath()
                    + "\nPrivate key: " + privateKeyPath.toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveKey(Path path, Object key) {
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String path) {
        return path.substring(path.length() - 3);
    }

    private Object readKeyFromFile(String path) throws Exception {
        final FileInputStream fis = new FileInputStream(path);
        final ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
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
            final Signature signature = Signature.getInstance("SHA1withDSA");
            final PrivateKey privateKey = (PrivateKey) readKeyFromFile(pathToKey);
            signature.initSign(privateKey);

            signature.update(text.getBytes(StandardCharsets.UTF_8));
            final byte[] realSignature = signature.sign();

            final Path pathToEncodedFile = Path.of("/signature.txt");
            Files.write(pathToEncodedFile, realSignature, CREATE);

            return pathToEncodedFile.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(String textAndSignaturePath) {
        if (pathToKey.isEmpty()) {
            return generateKeysAndGetPaths();
        }

        if (!getFileExtension(pathToKey).equals("pub")) {
            return null;
        }

        final String[] textAndPath = textAndSignaturePath.split("\n");
        final String text = textAndPath[0];
        final Path signaturePath = Path.of(textAndPath[1]);

        try {
            final Signature signature = Signature.getInstance("SHA1withDSA");
            final PublicKey publicKey = (PublicKey) readKeyFromFile(pathToKey);
            signature.initVerify(publicKey);
            signature.update(text.getBytes());

            byte[] bytesSignature = Files.readAllBytes(signaturePath);

            return signature.verify(bytesSignature)
                    ? "Verified"
                    : "Not verified";
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
