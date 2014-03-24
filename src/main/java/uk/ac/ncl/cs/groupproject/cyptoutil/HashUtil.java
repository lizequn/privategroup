package uk.ac.ncl.cs.groupproject.cyptoutil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash util based on sha-256
 * @author ZequnLi
 *
 */
public class HashUtil {
    /**
     * get hash code by giving string
     * @param s
     * @return HEX HashCode
     */
    public static String calHash(String s){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(s.getBytes());
            byte [] hashCode = messageDigest.digest();
            //change to HEX
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hashCode.length; i++) {
                sb.append(Integer.toString((hashCode[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //never happens
            e.printStackTrace();
        }
        return null;
    }

    public static String convert2String(byte [] input){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < input.length; i++) {
            sb.append(Integer.toString((input[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String calHash(byte [] s){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(s);
            byte [] hashCode = messageDigest.digest();
            //change to HEX
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hashCode.length; i++) {
                sb.append(Integer.toString((hashCode[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //never happens
            e.printStackTrace();
        }
        return null;
    }
}
