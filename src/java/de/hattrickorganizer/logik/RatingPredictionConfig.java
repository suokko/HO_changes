package de.hattrickorganizer.logik;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import plugins.IRatingPredictionConfig;
import plugins.IRatingPredictionParameter;
import de.hattrickorganizer.tools.HOEncrypter;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.ZipHelper;


public class RatingPredictionConfig implements IRatingPredictionConfig{
	
	private static RatingPredictionConfig instance = null;
	private RatingPredictionParameter[] params = new RatingPredictionParameter[5];
		
	public static RatingPredictionConfig getInstance() {
		if (instance==null) {
			instance = new RatingPredictionConfig();		
		}
		return instance;
	}
	
	private RatingPredictionConfig() {
		ZipHelper zip = null;
		try {
			zip = new ZipHelper("ratings.dat");
		} catch (Exception e) {
			//HOLogger.instance().log(getClass(),e);
			HOLogger.instance().log(getClass(),"Ratings not loaded!!!");
		}	
		params[0] = new RatingPredictionParameter(extractParams(zip,"sd.dat"));
		params[1] = new RatingPredictionParameter(extractParams(zip,"cd.dat"));
		params[2] = new RatingPredictionParameter(extractParams(zip,"mf.dat"));
		params[3] = new RatingPredictionParameter(extractParams(zip,"sa.dat"));
		params[4] = new RatingPredictionParameter(extractParams(zip,"ca.dat"));
		
		try {
			zip.close();
		} catch (RuntimeException e1) {
		}
		 									
	}

	private float[] extractParams(ZipHelper zip,String name) {
		final HOEncrypter encrypter = HOEncrypter.getInstance();					
		try {					 			
			String out = encrypter.decrypt(zip.getFile(name));			
			BufferedReader br = new BufferedReader(new StringReader(out));		
			String line = null;
			List l = new ArrayList();
			br.readLine(); // Skip Version
			while ((line = br.readLine()) != null) {
				l.add(new Float(Float.parseFloat(line)));
			}
			float[] k = new float[l.size()];
			for(int i = 0; i < l.size(); i++) {
				k[i]=((Float)l.get(i)).floatValue();
			}
			return k;
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			return new float[30];
		}
	}
		
	
	public final IRatingPredictionParameter getCentralAttackParameters() {
		return params[4];
	}

	public final IRatingPredictionParameter getSideAttackParameters() {
		return params[3];
	}

	public final IRatingPredictionParameter getCentralDefenseParameters() {
		return params[1];
	}

	public final IRatingPredictionParameter getSideDefenseParameters() {
		return params[0];
	}

	public final IRatingPredictionParameter getMidfieldParameters() {
		return params[2];
	}


}
