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
       String email = "aa.a@aaa.aaa";
       System.out.println(verify(email));



    }
    private static boolean verify(String email){
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(EMAIL_REGEX);
    }
}
