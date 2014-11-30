package com.sageburner.im.server.util;

import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Ryan on 11/7/2014.
 */
public class CryptoUtils {

    public static String hashPassword(String password) {
        String passwordHash = null;

        try {
            passwordHash = PasswordHash.createHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passwordHash;
    }

    public static boolean validatePassword(String password, String passwordHash) {
        boolean passwordValid = false;

        try {
            passwordValid = PasswordHash.validatePassword(password, passwordHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passwordValid;
    }
}
