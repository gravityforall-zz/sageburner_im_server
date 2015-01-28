package com.sageburner.im.server.test.jpbc;

import com.sageburner.im.server.jpbc.IBE;
import com.sageburner.im.server.jpbc.IBEParams;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IBETest {

	@Test
	public void testEncryptDecrypt() {

		IBEParams ibeParams = new IBEParams();
		ibeParams.setParamsString("type a\n" +
				"q 15395144596410194588212526809239258288053698882689112700879123127308216454058624125169848218156001486832831439076222867742441002432159967257568952165990471\n" +
				"r 1461501637330902918201208952637712259106134294527\n" +
				"h 10533785391117241877291560136702432360038107142333604139416314716198370432552752255793622979018968457412536\n" +
				"exp1 91\n" +
				"exp2 160\n" +
				"sign0 -1\n" +
				"sign1 -1");

		ibeParams.setpByteString("ARkDcGf8U6FrIdEYh6jIXcq4GqIImORbifkdNY0eRpY" +
				"FZGa8IItN9XrQlanyPndcf2iQofbKZ2ZaPXEmIv+jp" +
				"c8A7v8MKpoZkKRg8aF8gcjO29NDm8XJ4v4tcm0em/9" +
				"f1F/wHxXdpCm5ZlqC1QDE9kxbtD1Owgj5VHbqv0h922GecA==");

		ibeParams.setsByteString("g9pi2mwJjfOYuHunFObMgFwFVbk=");

		// Init the generator... (setup() called implicitly via the IBE constructor)
		//IBE ibe = new IBE(ibeParams.getParamsString(), ibeParams.getpByteString(), ibeParams.getsByteString());
		IBE ibe = new IBE();

		String message = "weak";
		System.out.println("Message: " + message);

		String userID = "bunPuncher69";
		System.out.println("userID: " + userID);

		// Encryption:
		System.out.println("Encryption.....");
		String encMsg = ibe.getEncFromID(message, userID);

		// Decryption:
		System.out.println("Decryption.....");
		String decMsg = ibe.getDecFromID(encMsg, userID);

		System.out.println("Decoded Message: " + decMsg);
	}

	@Test
	public void testDecrypt() {

		// get USER'S OWN facebook ID
		String facebookID = "";
		String encMsg = "";
		// System.out.println(encMsg);

		// Turn off print statements (turn off the System.out output stream)
		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		String decMsg = "weak";

		String paramsString = "type a\n" +
				"q 15395144596410194588212526809239258288053698882689112700879123127308216454058624125169848218156001486832831439076222867742441002432159967257568952165990471\n" +
				"r 1461501637330902918201208952637712259106134294527\n" +
				"h 10533785391117241877291560136702432360038107142333604139416314716198370432552752255793622979018968457412536\n" +
				"exp1 91\n" +
				"exp2 160\n" +
				"sign0 -1\n" +
				"sign1 -1";

		String pByteString = "ARkDcGf8U6FrIdEYh6jIXcq4GqIImORbifkdNY0eRpY" +
				"FZGa8IItN9XrQlanyPndcf2iQofbKZ2ZaPXEmIv+jp" +
				"c8A7v8MKpoZkKRg8aF8gcjO29NDm8XJ4v4tcm0em/9" +
				"f1F/wHxXdpCm5ZlqC1QDE9kxbtD1Owgj5VHbqv0h922GecA==";

		String sByteString = "g9pi2mwJjfOYuHunFObMgFwFVbk=";

		try {
			// encrypt the message with the friend's Facebook ID
			//IBE ibe = new IBE(paramsString, pByteString, sByteString);
			IBE ibe = new IBE();

			String priv = ibe.getPrivStr(facebookID);

			// Have to call this just to initialize some objects, just ignore
			// result
			ibe.getEncFromID("you are weak", "test");

			decMsg = ibe.getDecFromPriv(encMsg, priv);
		} finally {
			System.setOut(out);
		}
		System.out.println(decMsg);
	}

	@Test
	public void testEncrypt() {

		// get FRIEND'S facebook ID
		String facebookID = "";
		String msg = "";
		// get the plaintext message
//		for (int i = 1; i < args.length; i++) {
//			msg += args[i] + " ";
//		}
		msg = msg.substring(0, msg.length() - 1);
		// System.out.println()

		// Turn off print statements (turn off the System.out output stream)
		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		String encMsg = "weak";

		String paramsString = "type a\n" +
				"q 15395144596410194588212526809239258288053698882689112700879123127308216454058624125169848218156001486832831439076222867742441002432159967257568952165990471\n" +
				"r 1461501637330902918201208952637712259106134294527\n" +
				"h 10533785391117241877291560136702432360038107142333604139416314716198370432552752255793622979018968457412536\n" +
				"exp1 91\n" +
				"exp2 160\n" +
				"sign0 -1\n" +
				"sign1 -1";

		String pByteString = "ARkDcGf8U6FrIdEYh6jIXcq4GqIImORbifkdNY0eRpY" +
				"FZGa8IItN9XrQlanyPndcf2iQofbKZ2ZaPXEmIv+jp" +
				"c8A7v8MKpoZkKRg8aF8gcjO29NDm8XJ4v4tcm0em/9" +
				"f1F/wHxXdpCm5ZlqC1QDE9kxbtD1Owgj5VHbqv0h922GecA==";

		String sByteString = "g9pi2mwJjfOYuHunFObMgFwFVbk=";

		try {
			// encrypt the message with the friend's Facebook ID
			//IBE ibe = new IBE(paramsString, pByteString, sByteString);
			IBE ibe = new IBE();

			String pub = ibe.getPubStr(facebookID);
			encMsg = ibe.getEncFromPub(msg, pub);

		} finally {
			System.setOut(out);
		}
		System.out.println(encMsg);
	}

	@Test
	public void testInit() {

		// Init the generator...
		int rBits = 160;
		int qBits = 512;
		PairingParametersGenerator pairingParametersGenerator = new TypeACurveGenerator(rBits, qBits);

		// Generate the parameters...
		PairingParameters params = pairingParametersGenerator.generate();
		System.out.println(params);

		Pairing pairing = PairingFactory.getPairing(params);
		// pairing must be symmetric
		if (!pairing.isSymmetric()) {
			System.out.println("Pairing must be symmetric");
			System.exit(0);
		}

		// Setup
		Element P = pairing.getG1().newRandomElement();
		Element s = pairing.getZr().newRandomElement();
		// MUST duplicate element before multiplying it
		Element Ppub = P.duplicate();
		Ppub.mulZn(s);

		System.out.println("P: " + P);
		System.out.println("s: " + s);
		System.out.println("Ppub: " + Ppub);
		System.out.println();

		// System.out.println("Enter your Facebook ID:");
		// Scanner console = new Scanner(System.in);
		// Get private key
		// byte[] hash = toSHA1(console.nextLine());
		System.out.println("Extracting public/private keys......");
		byte[] hash = toSHA1("test");
		System.out.print("hash length: ");
		System.out.println(hash.length);
		System.out.println("hash: " + byteToHex(hash));
		System.out.println();

		// public key
		Element Qid = pairing.getG1().newElement()
				.setFromHash(hash, 0, hash.length);

		// multiply to get private key
		Element Sid = Qid.duplicate();
		Sid.mulZn(s);

		// print the stuff out
		System.out.printf("Private key Sid = %s\n", Sid);
		System.out.printf("Public key Qid = %s\n", Qid);
		System.out.println();

		// Encryption:
		System.out.println("Encryption.....");

		String message = "weak";
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
		System.out.println("test: " + test);

		System.out.println("M-in:" + byteToHex(shamessage));
		System.out.println("Mout: " + byteToHex(M));

		if (byteToHex(M).equals(byteToHex(shamessage))) {
			System.out.println("IT WORKED!!!!");
		}

		System.out.println("ENDED");
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
}
