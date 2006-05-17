package de.hattrickorganizer.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class HOEncrypter {
	
	private static HOEncrypter instance = null;
	
	Cipher ecipher;
	Cipher dcipher;
    
	// 8-byte Salt
	byte[] salt = {
		(byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
		(byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
	};
        
	// Iteration count
	int iterationCount = 19;
    
    public static HOEncrypter getInstance() {
    	if (instance==null) {
    		instance = new HOEncrypter("Data not loaded properly");
    	}
    	return instance;
    }

	public static HOEncrypter getInstance(String phrase) {
		if (instance==null) {
			instance = new HOEncrypter(phrase);
		}
		return instance;
	}
    
	private HOEncrypter(String passPhrase) {
		try {
			// Create the key
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance(
				"PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());
    
			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
    
			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (java.security.InvalidAlgorithmParameterException e) {
		} catch (java.security.spec.InvalidKeySpecException e) {
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}
    
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
    
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
    
			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}
	    
	public String encrypt(InputStream in) {
		String input = getStringFromStream(in);
		return encrypt(input);		
	 }
    
	public String decrypt(String str) {
		
		try {
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
    
			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);
    
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}
	    
	 public String decrypt(InputStream in) {
		String input = getStringFromStream(in);
		return decrypt(input);		
	 }	
	 
	 private String getStringFromStream(InputStream in) {
		int k;
		int aBuffSize = 1123123;
		String StringFromWS="";
		byte buff[] = new byte[aBuffSize];
		OutputStream xOutputStream = new ByteArrayOutputStream(aBuffSize);

		try {
			while ( (k=in.read(buff) ) != -1) {
				xOutputStream.write(buff,0,k);
			}			
		} catch (IOException e) {
			return "";
		}

//		   I can now grab the string I want
		return StringFromWS + xOutputStream.toString();	 	
	 }	
}
