package de.hattrickorganizer.encrypt;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import de.hattrickorganizer.tools.HOEncrypter;
import de.hattrickorganizer.tools.ZipHelper;
import de.hattrickorganizer.tools.backup.HOZip;


public class EncryptZip {
	
	public EncryptZip(String fileName) throws Exception {
		ZipHelper zip = new ZipHelper(fileName+".zip");
		
		final HOEncrypter encrypter = HOEncrypter.getInstance();

		HOZip newZip = new HOZip(fileName+".dat");		
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
	}
	
}
