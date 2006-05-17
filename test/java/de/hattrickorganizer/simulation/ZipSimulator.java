package de.hattrickorganizer.simulation;

import de.hattrickorganizer.tools.ZipHelper;




/**
 * Class that Generates the Action and contains the resaults
 */
public class ZipSimulator {

		
	public static void main(String[] a) {
		
		ZipHelper zip;
		try {
			zip = new ZipHelper("update.zip");
			zip.extractFile("ho.bat","D:\\porova23");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
	}
}
