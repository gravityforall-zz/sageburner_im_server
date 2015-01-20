package com.sageburner.im.server.jpbc;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import org.bouncycastle.util.encoders.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IBE {
	Pairing pairing;
	Element P;
	Element s;
	Element Ppub;
	Element U;
	byte[] V;
	IBEParams ibeParams;

	public IBE() {
		setup();
	}

	private void setup() {
		// Init the generator...
		int rBits = 160;
		int qBits = 512;
		PairingParametersGenerator pairingParametersGenerator = new TypeACurveGenerator(rBits, qBits);

		// Generate the parameters...
		PairingParameters params = pairingParametersGenerator.generate();

		pairing = PairingFactory.getPairing(params);

		// System.out.println(params);

		// pairing must be symmetric
		if (!pairing.isSymmetric()) {
			System.out.println("Pairing must be symmetric");
			System.exit(0);
		}

		// Setup
		P = pairing.getG1().newRandomElement();
		s = pairing.getZr().newRandomElement();

		// MUST duplicate element before multiplying it
		// Ppub depends solely on P and s so do not need to save Ppub
		Ppub = P.duplicate();
		Ppub.mulZn(s);

		//IBEParams
		ibeParams = new IBEParams();

		StringBuffer sb = new StringBuffer();
		sb.append(params);
		ibeParams.setParamsString(sb.toString());
		ibeParams.setpByteString(this.toHexString(P.toBytes()));
		ibeParams.setsByteString(this.toHexString(s.toBytes()));

//		System.out.println("paramsString: " + ibeParams.getParamsString());
//		System.out.println("PpByteString " + ibeParams.getpByteString());
//		System.out.println("sByteString: " + ibeParams.getsByteString());
//		System.out.println("Ppub: " + Ppub);
//		System.out.println();
	}

	private Element extractPublic(String in) {
		// System.out.println("Enter your Facebook ID:");
		// Scanner console = new Scanner(System.in);
		// Get private key
		// byte[] hash = toSHA1(console.nextLine());
		System.out.println("Extracting public key......");
		byte[] hash = toSHA1(in);

		// public key
		Element Qid = pairing.getG1().newElement()
				.setFromHash(hash, 0, hash.length);
		System.out.printf("Public key Qid = %s\n", Qid);
		System.out.println();

		return Qid;
	}

	private Element extractPriv(String in) {
		System.out.println("Extracting private key......");
		Element Qid = extractPublic(in);
		// multiply to get private key
		Element Sid = Qid.duplicate();
		Sid.mulZn(s);

		// print the stuff out
		System.out.printf("Private key Sid = %s\n", Sid);
		return Sid;
	}

	private void encrypt(String s, Element Qid) {
		// Encryption:
		System.out.println("Encryption.....");

		String message = s;
		System.out.println("Message: " + message);

		System.out.println("Message length: " + message.getBytes().length);

		byte[] fakeShaMsg = new byte[message.length()];
		for (int i = 0; i < message.getBytes().length; i++) {
			fakeShaMsg[i] = message.getBytes()[i];
		}

		byte[] shamessage = toSHA1(message); // Get the message digest

		shamessage = fakeShaMsg;

		System.out.println("Message hash length: " + shamessage.length);
		System.out.println("The message hash: " + byteToHex(shamessage));

		// Choose a random r
		Element r = pairing.getZr().newRandomElement();

		Element U = P.duplicate();
		U.mulZn(r);
		System.out.println("U: " + U);

		Element gid = pairing.pairing(Qid, Ppub);
		gid.powZn(r);
		String sgid = gid.toString();
		byte[] shagid = toSHA1(sgid);

		// need to XOR shamessage an shagid
		byte[] V = new byte[shamessage.length];
		for (int i = 0; i < shamessage.length; i++) {
			V[i] = (byte) (shamessage[i] ^ shagid[i % 20]);
		}
		System.out.println();
		this.U = U;
		this.V = V;

	}

	private String decrypt(Element Sid) {
		// Decryption:

		System.out.println("Decryption.....");

		Element rgid = pairing.pairing(Sid, U);
		String sgid_receiver = rgid.toString();
		byte[] shagid_receiver = toSHA1(sgid_receiver);

		// Decrypted message
		byte[] M = new byte[V.length];

		for (int i = 0; i < V.length; i++) {
			M[i] = (byte) (V[i] ^ shagid_receiver[i % 20]);
		}

		String test = new String(M);
		System.out.println("decrypted: " + test);
		return test;

	}

	private String getEncrypted(String s, Element Qid) {
		encrypt(s, Qid);
		// save U and V and send to recipient
		byte[] Ubyte = this.U.toBytes();
		// ibe.U.setFromBytes(Ubyte);
		String Ustr = this.toHexString(Ubyte);
		String Vstr = this.toHexString(this.V);
		System.out.println("U: " + Ustr);
		System.out.println("V: " + Vstr);
		return Ustr + "-" + Vstr;

	}

	private String getDecrypted(String s, Element Sid) {
		String[] strs = s.split("-");
		String U = strs[0];
		String V = strs[1];
		this.U.setFromBytes(toByteArray(U));
		this.V = toByteArray(V);
		return this.decrypt(Sid);
	}

	// get the string representation of a PRIVATE key
	public String getPrivStr(String s) {
		Element priv = extractPriv(s);
		byte[] privbytes = priv.toBytes();
		return this.toHexString(privbytes);
	}

	// get the string representation of a PUBLIC key
	public String getPubStr(String s) {
		Element pub = extractPublic(s);
		byte[] pubbytes = pub.toBytes();
		return this.toHexString(pubbytes);
	}

	public String getEncFromID(String msg, String FacebookID) {
		String s2 = this.getPubStr(FacebookID);
		Element pub = this.pairing.getG1().newElement();
		pub.setFromBytes(this.toByteArray(s2));
		// get encrypted message
		String encMsg = this.getEncrypted(msg, pub);
		return encMsg;
	}

	public String getDecFromID(String msg, String FacebookID) {
		String s1 = this.getPrivStr(FacebookID);
		Element priv = this.pairing.getG1().newElement();
		priv.setFromBytes(this.toByteArray(s1));
		// get encrypted message
		String decMsg = this.getDecrypted(msg, priv);
		return decMsg;
	}

	public String getDecFromPriv(String msg, String privStr) {
		Element priv = this.pairing.getG1().newElement();
		priv.setFromBytes(this.toByteArray(privStr));
		// get encrypted message
		String decMsg = this.getDecrypted(msg, priv);
		return decMsg;
	}

	public String getEncFromPub(String msg, String pubStr) {
		Element pub = this.pairing.getG1().newElement();
		pub.setFromBytes(this.toByteArray(pubStr));
		// get encrypted message
		String encMsg = this.getEncrypted(msg, pub);
		return encMsg;
	}

	// convert between byte array and hex string. Used to save public and
	// private keys, and encrypted message
	public String toHexString(byte[] array) {
		//return DatatypeConverter.printHexBinary(array);
		byte[] hexBytes = Base64.encode(array);
		return new String(hexBytes);
	}

	public byte[] toByteArray(String s) {
		//return DatatypeConverter.parseHexBinary(s);
		return Base64.decode(s.getBytes());
	}

	// do the hashing
	public static byte[] toSHA1(String convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md.digest(convertme.getBytes());
	}

	// convert hash to hex to display
	public static String byteToHex(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public IBEParams getIbeParams() {
		return ibeParams;
	}
}
