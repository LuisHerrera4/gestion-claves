package security.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Xifrar {

    public static SecretKey keygenKeyGeneration(int keySize) {
        try {
            if (keySize == 128 || keySize == 192 || keySize == 256) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(keySize);
                return keyGenerator.generateKey();
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error generating key: " + e.getMessage());
        }
        return null;
    }

    public static SecretKey passwordKeyGeneration(String password, int keySize) {
        try {
            byte[] data = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data);
            byte[] key = Arrays.copyOf(hash, keySize / 8);
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            System.err.println("Error generating password key: " + e.getMessage());
        }
        return null;
    }

    public static byte[] encryptData(SecretKey sKey, byte[] data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades: " + ex.getMessage());
        }
        return encryptedData;
    }


    public static byte[] decryptData(byte[] data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (javax.crypto.BadPaddingException e) {
            System.err.println("Error: Clave incorrecta o datos corruptos. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general al descifrar los datos: " + e.getMessage());
        }
        return null;
    }

}
