package security.utils;

import security.utils.Xifrar;
import javax.crypto.SecretKey;
import java.util.Base64;

public class probaMetodes {
    public static void main(String[] args) {
        String text = "Hola, aquest és un missatge xifrat!";
        System.out.println("Text original: " + text);

        // Generar clau aleatòria (1.1.1)
        SecretKey key1 = Xifrar.keygenKeyGeneration(128);
        if (key1 == null) {
            System.err.println("Error: No s'ha pogut generar la clau!");
            return;
        }

        byte[] encryptedData1 = Xifrar.encryptData(key1, text.getBytes());
        if (encryptedData1 == null) {
            System.err.println("Error: encryptData ha retornat null!");
            return;
        }

        byte[] decryptedData1 = Xifrar.decryptData(encryptedData1, key1);
        if (decryptedData1 != null) {
            System.out.println("Desxifrat amb clau generada: " + new String(decryptedData1));
        } else {
            System.err.println("Error desxifrant les dades.");
        }

        // Generar clau a partir d'una contrasenya (1.1.2)
        String password = "contrasenyaSegura";
        SecretKey key2 = Xifrar.passwordKeyGeneration(password, 128);
        if (key2 == null) {
            System.err.println("Error: No s'ha pogut generar la clau des de la contrasenya!");
            return;
        }

        byte[] encryptedData2 = Xifrar.encryptData(key2, text.getBytes());
        if (encryptedData2 == null) {
            System.err.println("Error: encryptData ha fallat amb la clau de contrasenya!");
            return;
        }

        byte[] decryptedData2 = Xifrar.decryptData(encryptedData2, key2);
        if (decryptedData2 != null) {
            System.out.println("Desxifrat amb clau de contrasenya: " + new String(decryptedData2));
        } else {
            System.err.println("Error desxifrant les dades amb la clau de contrasenya.");
        }

        // Provar desxifrat amb una clau incorrecta
        SecretKey wrongKey = Xifrar.passwordKeyGeneration("incorrecta", 128);
        try {
            byte[] decryptedWrong = Xifrar.decryptData(encryptedData2, wrongKey);
            if (decryptedWrong != null) {
                System.out.println("Desxifrat amb clau incorrecta: " + new String(decryptedWrong));
            } else {
                System.err.println("No s'ha pogut desxifrar amb la clau incorrecta.");
            }
        } catch (Exception e) {
            System.err.println("Error desxifrant amb clau incorrecta: " + e.getMessage());
        }
    }
}