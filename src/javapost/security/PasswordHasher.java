package javapost.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHasher {

    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[20];
        r.nextBytes(salt);
        BigInteger bigInt = new BigInteger(1, salt);
        return bigInt.toString();
    }

    public static String getHashWithSalt(String password, String salt) {

        String output = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt.getBytes("UTF-8"));
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
        return output;
    }

}
