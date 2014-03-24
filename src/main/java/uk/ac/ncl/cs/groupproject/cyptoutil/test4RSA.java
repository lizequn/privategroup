package uk.ac.ncl.cs.groupproject.cyptoutil;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @Auther: Li Zequn
 * Date: 19/02/14
 */
public class test4RSA {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeySpecException {
        //get public key value pairs
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
       // SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyPairGenerator.initialize(2048);
        //keyPairGenerator.initialize(2048);
        KeyPair kp = keyPairGenerator.genKeyPair();

        PublicKey publicKey = kp.getPublic();
        //System.out.println(publicKey);

        PrivateKey privateKey = kp.getPrivate();
       // System.out.println(privateKey);
        //encrypt and decrypt
//        byte [] bytes = privateKey.getEncoded();
//
//        PrivateKey privateKey1 = KeyGenerator.unserializeedPrivateKey(bytes);
//        String test = "TestMessage";
//        String hashedTest = HashUtil.calHash(test);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
//        byte[] x = cipher.doFinal(hashedTest.getBytes());
//        String tranfer = new String(x);
//        cipher.init(Cipher.DECRYPT_MODE,privateKey);
//        byte[] y = cipher.doFinal(tranfer.getBytes());
//        System.out.println(new String(hashedTest.getBytes()));
//        System.out.println(new String(x));
//        System.out.println(new String(y));

        String test = "test";
        //hash
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        messageDigest.update(test.getBytes());
        byte [] hashCode = messageDigest.digest();
        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashCode.length; i++) {
            sb.append(Integer.toString((hashCode[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb);
        //System.out.println(sb.toString().getBytes().length);
        byte[] SigA = SignUtil.sign(privateKey, sb.toString().getBytes());
        byte[] SigB = SignUtil.sign(privateKey,SigA);
        byte[] SigAu = SignUtil.unSign(publicKey,SigB);
        byte[] origin = SignUtil.unSign(publicKey,SigAu);
        System.out.println(new String(origin).trim().length());



    }
}
