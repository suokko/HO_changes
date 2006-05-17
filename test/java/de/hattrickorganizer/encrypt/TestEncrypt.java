package de.hattrickorganizer.encrypt;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import de.hattrickorganizer.tools.HOEncrypter;
import de.hattrickorganizer.tools.ZipHelper;
import de.hattrickorganizer.tools.backup.HOZip;


public class TestEncrypt {

	public static void main(String[] args) {
		try {
			new TestEncrypt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TestEncrypt() throws Exception {
		// Here is an example that uses the class
		try {
			// Create encrypter/decrypter class
			final HOEncrypter encrypter = HOEncrypter.getInstance();
    
			// Encrypt
			String encrypted = encrypter.encrypt("Don't tell anybody!");
    
			// Decrypt
			String decrypted = encrypter.decrypt(encrypted);
			
			System.out.println(encrypted);
			System.out.println(decrypted);
		} catch (Exception e) {
		}		
		
		String fileName = "epv";
		ZipHelper zip = new ZipHelper(fileName+".zip");
		final HOEncrypter encrypter = HOEncrypter.getInstance();

		HOZip newZip = new HOZip(fileName+".test");		
		Enumeration enum2 = zip.getFileList();
		while (enum2.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) enum2.nextElement();
			String name = entry.getName();
			InputStream in = zip.getFile(name);
				
			String out = encrypter.encrypt(in);
			newZip.addStringEntry(name,out);			
		}
		try {
			newZip.closeArchive();
		} catch (RuntimeException e1) {
		}									
		try {
			zip.close();
		} catch (RuntimeException e1) {
		}

		zip = new ZipHelper(fileName+".test");
				
		enum2 = zip.getFileList();
		while (enum2.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) enum2.nextElement();
			String name = entry.getName();
			InputStream in = zip.getFile(name);
				 		
			String out = encrypter.decrypt(in);
			System.out.println(out);			
		}
		try {
			zip.close();
		} catch (RuntimeException e1) {
		}
		
	}
	
}
