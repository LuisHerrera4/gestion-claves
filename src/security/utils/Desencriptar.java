package security.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;

public class Desencriptar{

    public static SecretKey passwordKeyGeneration(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            byte[] key = new byte[16]; // AES-128
            System.arraycopy(hash, 0, key, 0, key.length);
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            System.err.println("Error generating key: " + e.getMessage());
        }
        return null;
    }

    public static byte[] decryptData(byte[] data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            // No mostramos error para cada intento fallido
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // Cargar el archivo cifrado
            byte[] encryptedData = Files.readAllBytes(Paths.get("textamagat.crypt"));

            // Leer la lista de contraseñas
            List<String> passwords = Files.readAllLines(Paths.get("clausA4.txt"));

            for (String password : passwords) {
                SecretKey key = passwordKeyGeneration(password);
                byte[] decryptedData = decryptData(encryptedData, key);

                if (decryptedData != null) {
                    System.out.println("Contraseña correcta: " + password);
                    System.out.println("Mensaje descifrado: " + new String(decryptedData));
                    return;
                }
            }

            System.out.println("No se encontró la contraseña correcta.");
        } catch (Exception e) {
            System.err.println("Error en el proceso: " + e.getMessage());
        }
    }
}
