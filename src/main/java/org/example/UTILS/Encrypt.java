package org.example.UTILS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase Encrypt encargada de encriptar, cuando se registra un admin o usuario su contraseñas
 * para almacenarlas en la base de datos
 */
public class Encrypt {
    public static String EncryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
