package com.josthi.web.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class Security {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey() throws NoSuchAlgorithmException, UnsupportedEncodingException 
    {
        MessageDigest sha = null;
        String myKey = "maragache123";
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw e;
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw e;
        }
    }
 
    public static String encrypt(String strToEncrypt) throws Exception 
    {
        try
        {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
            throw e;
        }
    }
 
    public static String decrypt(String strToDecrypt) throws Exception 
    {
        try
        {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
            throw e;
        }
    }
    
    
    public static void main(String[] args) throws Exception 
    {
        
         
        String originalString = "1234";
        String encryptedString = Security.encrypt(originalString) ;
        String decryptedString = Security.decrypt(encryptedString) ;
         
        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
