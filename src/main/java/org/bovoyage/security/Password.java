package org.bovoyage.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {

    public static String passwordHash(String plainPassword) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainPassword.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    public static boolean passwordVerify(String plainPassword, String encodedPassword) throws NoSuchAlgorithmException
    {
        return encodedPassword != null && encodedPassword.equals(Password.passwordHash(plainPassword));
    }
}
