package com.sageburner.im.server.server.service;

import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;

/**
 * Created by Ryan on 10/17/2014.
 */
public interface CryptoService {

        public AsymmetricCipherKeyPair setup(int bitLength, int length);

        public Element[] map(CipherParameters publicKey, String... ids);

        public CipherParameters keyGen(AsymmetricCipherKeyPair masterKey, Element... ids);

        public CipherParameters delegate(AsymmetricCipherKeyPair masterKey, CipherParameters secretKey, Element id);

        public byte[][] encaps(CipherParameters publicKey, Element... ids);

        public byte[] decaps(CipherParameters secretKey, byte[] cipherText);
}