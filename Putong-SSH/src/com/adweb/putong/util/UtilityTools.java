package com.adweb.putong.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UtilityTools {
	
	public static final String HASH_MD5 = "MD5";
	public static final String HASH_SHA1 = "SHA-1";
	public static final String HASH_SHA256 = "SHA-256";
	
	public static final String ENCODING = "UTF-8";
	
	public static final int ITERATE_TIMES = 1000;
	public static final int LITTLE_ITERATE_TIMES = 100;
	
	public static String generateMD5(String string) {
		return hashString(string, HASH_MD5, null, LITTLE_ITERATE_TIMES);
	}
	
	public static String generateSHA1(String string) {
		return hashString(string, HASH_SHA1, null, LITTLE_ITERATE_TIMES);
	}
	
	public static String generateTempPassword(String username) {
		StringBuilder salt = new StringBuilder();
		
		Random rand = new Random();
        byte[] bSalt = new byte[12];
        rand.nextBytes(bSalt);
        
        for (int j = 0; j < bSalt.length; j++) {
        	salt.append(Integer.toHexString((0x000000ff & bSalt[j]) | 0xffffff00)
                    .substring(6));	    		
        }
		return hashString(username, HASH_MD5, salt.toString(), LITTLE_ITERATE_TIMES);
	}

	public static String hashString(String string, String method, String salt, int times) {
		StringBuilder sb = new StringBuilder();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(method);
			digest.reset();
			
			// add salt if it's provided
			if(salt != null){
				digest.update(salt.getBytes(ENCODING));
			}
			
			byte[] input = digest.digest(string.getBytes(ENCODING));
			
			for (int i = 0; i < times; i++) {
				digest.reset();
				input = digest.digest(input);
		    }
			
	        for (int i = 0; i < input.length; i++) {
	            sb.append(Integer.toHexString((0x000000ff & input[i]) | 0xffffff00)
	                    .substring(6));	    		
	        }	        
	        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
	    return sb.toString();
	}
	
	public static String createHashPassword(String password) {
		
		String hashed = null;
		StringBuilder salt = null;
		
		for(int i = 0; i<3;i++){
			
			salt = new StringBuilder();
			
			Random rand = new Random();
	        byte[] bSalt = new byte[12];
	        rand.nextBytes(bSalt);
	        
	        for (int j = 0; j < bSalt.length; j++) {
	        	salt.append(Integer.toHexString((0x000000ff & bSalt[j]) | 0xffffff00)
	                    .substring(6));	    		
	        }
			
			hashed = salt+","+hashString(password, HASH_MD5, salt.toString(), ITERATE_TIMES);
		}
		
		return hashed;
	}
	
	public static boolean checkPassword(String password, String hashed){
		String[] parts = hashed.split(",");
		String salt = parts[0], hashPass = parts[1];
		
		String newHashed = hashString(password, HASH_MD5, salt, ITERATE_TIMES);
		
		return newHashed.equals(hashPass);
	}
	
	public static void main(String[] args){
		String hashed = createHashPassword("xiqi1990");
		System.out.println(hashed);
		boolean result = checkPassword("xiqi1990", hashed);
		System.out.println(result);
	}
	
}
