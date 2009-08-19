package de.hattrickorganizer.prediction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HOEncrypter
{

    private static HOEncrypter encrypter = null;
    Cipher cipherEnc;
    Cipher cipherDec;
    byte salt[] = {
        -87, -101, -56, 50, 86, 53, -29, 3
    };
    int iterCount;

    public static HOEncrypter getInstance()
    {
        if(encrypter == null)
            encrypter = new HOEncrypter("Data not loaded properly");
        return encrypter;
    }

    public static HOEncrypter getInstance(String s)
    {
        if(encrypter == null)
            encrypter = new HOEncrypter(s);
        return encrypter;
    }

    private HOEncrypter(String pass)
    {
        iterCount = 19;
        try
        {
            PBEKeySpec pbekeyspec = new PBEKeySpec(pass.toCharArray(), salt, iterCount);
            SecretKey secretkey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(pbekeyspec);
            cipherEnc = Cipher.getInstance(secretkey.getAlgorithm());
            cipherDec = Cipher.getInstance(secretkey.getAlgorithm());
            PBEParameterSpec pbeparameterspec = new PBEParameterSpec(salt, iterCount);
            cipherEnc.init(1, secretkey, pbeparameterspec);
            cipherDec.init(2, secretkey, pbeparameterspec);
            return;
        }
        catch(InvalidAlgorithmParameterException _ex)
        {
            return;
        }
        catch(InvalidKeySpecException _ex)
        {
            return;
        }
        catch(NoSuchPaddingException _ex)
        {
            return;
        }
        catch(NoSuchAlgorithmException _ex)
        {
            return;
        }
        catch(InvalidKeyException _ex)
        {
            return;
        }
    }

    public final String encrypt(String s)
    {
    	try {
            byte outBytes[];
            byte inBytes[] = s.getBytes("UTF8");
            outBytes = cipherEnc.doFinal(inBytes);
            return (new BASE64Encoder()).encode(outBytes);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }

    public final String encrypt(InputStream inputstream)
    {
        String s = inputStreamToString(inputstream);
        return encrypt(s);
    }

    public final String decrypt(String s)
    {
    	try {
            byte outBytes[];
            byte inBytes[] = (new BASE64Decoder()).decodeBuffer(s);
            outBytes = cipherDec.doFinal(inBytes);
            return new String(outBytes, "UTF8");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        return null;
    }

    public final String decrypt(InputStream inputstream)
    {
        String s = inputStreamToString(inputstream);
        return decrypt(s);
    }

    private static String inputStreamToString(InputStream inStream)
    {
        int j = 0x112333;
        String s = "";
        byte buffer[] = new byte[j];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(j);
        int i;
        try
        {
            while((i = inStream.read(buffer)) != -1) 
                outStream.write(buffer, 0, i);
        }
        catch(IOException _ex)
        {
            return "";
        }
        return s + outStream.toString();
    }

}
