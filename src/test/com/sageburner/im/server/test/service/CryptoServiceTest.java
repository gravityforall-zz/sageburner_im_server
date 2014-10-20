package com.sageburner.im.server.test.service;

import com.sageburner.im.server.server.service.CryptoServiceImpl;
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ryan on 10/17/2014.
 */
public class CryptoServiceTest {

    @Test
    public void mainTest() {
        CryptoServiceImpl engine = new CryptoServiceImpl();

        // Setup
        AsymmetricCipherKeyPair keyPair = engine.setup(new SecureRandom().nextInt(), 10);

        // KeyGen
        Element[] ids = engine.map(keyPair.getPublic(), "angelo", "de caro", "unisa");

        CipherParameters sk0 = engine.keyGen(keyPair, ids[0]);
        CipherParameters sk01 = engine.keyGen(keyPair, ids[0], ids[1]);
        CipherParameters sk012 = engine.keyGen(keyPair, ids[0], ids[1], ids[2]);

        CipherParameters sk1 = engine.keyGen(keyPair, ids[1]);
        CipherParameters sk10 = engine.keyGen(keyPair, ids[1], ids[0]);
        CipherParameters sk021 = engine.keyGen(keyPair, ids[0], ids[2], ids[1]);

        // Encryption/Decryption
        byte[][] ciphertext0 = engine.encaps(keyPair.getPublic(), ids[0]);
        byte[][] ciphertext01 = engine.encaps(keyPair.getPublic(), ids[0], ids[1]);
        byte[][] ciphertext012 = engine.encaps(keyPair.getPublic(), ids[0], ids[1], ids[2]);

        // Decrypt
        assertEquals(true, Arrays.equals(ciphertext0[0], engine.decaps(sk0, ciphertext0[1])));
        assertEquals(true, Arrays.equals(ciphertext01[0], engine.decaps(sk01, ciphertext01[1])));
        assertEquals(true, Arrays.equals(ciphertext012[0], engine.decaps(sk012, ciphertext012[1])));

        assertEquals(false, Arrays.equals(ciphertext0[0], engine.decaps(sk1, ciphertext0[1])));
        assertEquals(false, Arrays.equals(ciphertext01[0], engine.decaps(sk10, ciphertext01[1])));
        assertEquals(false, Arrays.equals(ciphertext012[0], engine.decaps(sk021, ciphertext012[1])));

        // Delegate/Decrypt
        assertEquals(true, Arrays.equals(ciphertext01[0], engine.decaps(engine.delegate(keyPair, sk0, ids[1]), ciphertext01[1])));
        assertEquals(true, Arrays.equals(ciphertext012[0], engine.decaps(engine.delegate(keyPair, sk01, ids[2]), ciphertext012[1])));
        assertEquals(true, Arrays.equals(ciphertext012[0], engine.decaps(engine.delegate(keyPair, engine.delegate(keyPair, sk0, ids[1]), ids[2]), ciphertext012[1])));

        assertEquals(false, Arrays.equals(ciphertext01[0], engine.decaps(engine.delegate(keyPair, sk0, ids[0]), ciphertext01[1])));
        assertEquals(false, Arrays.equals(ciphertext012[0], engine.decaps(engine.delegate(keyPair, sk01, ids[1]), ciphertext012[1])));
        assertEquals(false, Arrays.equals(ciphertext012[0], engine.decaps(engine.delegate(keyPair, engine.delegate(keyPair, sk0, ids[2]), ids[1]), ciphertext012[1])));
    }
}