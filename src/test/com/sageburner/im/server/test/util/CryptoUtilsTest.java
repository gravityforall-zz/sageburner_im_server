package com.sageburner.im.server.test.util;

import com.sageburner.im.server.util.CryptoUtils;
import com.sageburner.im.server.util.PasswordHash;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 11/7/2014.
 */
public class CryptoUtilsTest {

    @Test
    public void hashPasswordTest() {
        String testPass1 = "password";
        String testPass2 = "Password";
        String testPassHash1;
        String testPassHash2;

        testPassHash1 = CryptoUtils.hashPassword(testPass1);
        System.out.println("Password: " + testPass1 + ", Hash: " + testPassHash1);
        testPassHash2 = CryptoUtils.hashPassword(testPass1);
        System.out.println("Password: " + testPass1 + ", Hash: " + testPassHash2);

        assertTrue(CryptoUtils.validatePassword(testPass1, testPassHash1));
        assertFalse(CryptoUtils.validatePassword(testPass2, testPassHash1));

        assertNotEquals(testPassHash1, testPassHash2);
    }
}