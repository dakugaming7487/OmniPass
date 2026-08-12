package core.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EncryptionManager {

    public static String encrypt(String text, SecretKey key) {

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            String ivString = Base64.getEncoder().encodeToString(iv);
            String encryptedString = Base64.getEncoder().encodeToString(encrypted);

            return ivString + ":" + encryptedString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedText, SecretKey key) {

        try {

            String[] parts = encryptedText.split(":");

            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encrypted = Base64.getDecoder().decode(parts[1]);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SecretKey deriveKey(String password, byte[] salt) {

        try {

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] key = factory.generateSecret(spec).getEncoded();

            return new SecretKeySpec(key, "AES");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToHex(byte[] bytes) {

        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }

    public static byte[] hexToBytes(String hex) {

        byte[] bytes = new byte[hex.length() / 2];

        for (int i = 0; i < bytes.length; i++) {

            int index = i * 2;

            bytes[i] = (byte) Integer.parseInt(hex.substring(index, index + 2), 16);
        }

        return bytes;
    }

    public static byte[] generateSalt() {

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];

        random.nextBytes(salt);

        return salt;
    }

    public static String pbkdf2Hash(String password, byte[] salt) {

        try {

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return bytesToHex(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(text.getBytes());

            return bytesToHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
