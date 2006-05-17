// %3987013374:hoplugins.stt%
package de.hattrickorganizer.logik;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import plugins.IEPVData;
import de.hattrickorganizer.logik.price.Calculator;
import de.hattrickorganizer.logik.price.KeeperCalculator;
import de.hattrickorganizer.logik.price.Net;
import de.hattrickorganizer.logik.price.RoleCalculator;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.tools.HOEncrypter;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.ZipHelper;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class EPVCalculator {
	private static Calculator[] plain = new Calculator[5];
	
	private static EPVCalculator instance = null;
	//~ Constructors -------------------------------------------------------------------------------

	//~ Methods ------------------------------------------------------------------------------------

	public static EPVCalculator getInstance() {
		if (instance == null) {
			instance = new EPVCalculator();
		}
		return instance;
	}

	private EPVCalculator() {
		ZipHelper zip = null;
		try {
			zip = new ZipHelper("epv.dat");
		} catch (Exception e) {
			//HOLogger.instance().log(getClass(),e);
			HOLogger.instance().log(getClass(),"EPV not loaded!!!");
		}

		plain[0] = new RoleCalculator(extractParams(zip,"defense.dat"));
		plain[1] = new RoleCalculator(extractParams(zip,"mid.dat"));
		plain[2] = new RoleCalculator(extractParams(zip,"wing.dat"));
		plain[3] = new RoleCalculator(extractParams(zip,"attack.dat"));
		plain[4] = new KeeperCalculator(extractParams(zip,"keeper.dat"));									
		try {
			zip.close();
		} catch (RuntimeException e1) {
		}
		 									
	}

	private Net extractParams(ZipHelper zip,String name) {

		final HOEncrypter encrypter = HOEncrypter.getInstance();
		Net net = new Net();
				 									
		try {
			String out = encrypter.decrypt(zip.getFile(name));			
			BufferedReader br = new BufferedReader(new StringReader(out));
			String line = null;
			List l = new ArrayList();
			String structure = br.readLine(); // Read Network structure
			StringTokenizer st = new StringTokenizer(structure, " ");
			st.nextToken();
			st.nextToken();
			st.nextToken();
			List p = new ArrayList();
			while(st.hasMoreTokens()) {
				p.add(st.nextToken());			
			}
			int[] str = new int[p.size()-1];
			for(int i = 0; i < p.size()-1; i++) {
				str[i]=Integer.parseInt((String) p.get(i));
			}

			net.setLayer(str);
			br.readLine(); // Skip Epoch			
			
			while ((line = br.readLine()) != null) {
				l.add(new Double(Double.parseDouble(line)));
			}
			double[] k = new double[l.size()];
			for(int i = 0; i < l.size(); i++) {
				k[i]=((Double)l.get(i)).doubleValue();
			}
			net.setParam(k);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			net.setParam(new double[0]);
		}
		return net;
	}

	public double calculate(IEPVData epvData, int week) {
		double price = getPlayerPrice(epvData, week);
		if (isTrainer(epvData)) {
			double trainerPrice = getTrainerPrice(epvData);
			price = Math.max(price,trainerPrice);
		}
		return price;
	}

	private double getPlayerPrice(IEPVData epvData, int week) {		
		if (epvData.getMaxSkill() < 5) {
			return 0;
		}
		if (epvData.getMaxSkill() < 5.5) {
			return 1000;
		}
		if (epvData.getPlayerType() == EPVData.DEFENDER) {
			return plain[0].calculate(epvData, week);
		}
		if (epvData.getPlayerType() == EPVData.MIDFIELDER) {
			return plain[1].calculate(epvData, week);
		}
		if (epvData.getPlayerType() == EPVData.WINGER) {
			return plain[2].calculate(epvData, week);
		}
		if (epvData.getPlayerType() == EPVData.ATTACKER) {
			return plain[3].calculate(epvData, week);
		}
		return plain[4].calculate(epvData, week);
	}

	private boolean isTrainer(IEPVData epvData) {
		if ((epvData.getExperience()>4) && (epvData.getLeadership() > 4)) {
			return true;
		}
		return false;
	}

	private double getTrainerPrice(IEPVData epvData) {
		double A, B, C, D, E, F, G, H, A2;

		if (epvData.getLeadership() == 5) {
			A = 4907.76536;
			B = 5100.30918;
			C = -2172.37502;
			D = 201.17492;
			E = 1.69414;
			F = -0.19884;
			G = 0.00910;
			H = 0.00020;
			A2 = 0.00001;
		} else if (epvData.getLeadership() == 6) {
			A = 26135.22821;
			B = -27669.94543;
			C = 12711.01758;
			D = -2524.27117;
			E = 177.26464;
			F = -0.36078;
			G = 0.05572;
			H = -0.00565;
			A2 = 0.00032;
		} else if (epvData.getLeadership() == 7) {
			A = 23778.93411;
			B = -20864.02268;
			C = 12076.84047;
			D = -3275.37090;
			E = 310.50434;
			F = -0.29254;
			G = 0.03470;
			H = -0.00321;
			A2 = 0.00023;
		} else {
			return 0;
		}

		double exp = epvData.getExperience();
		double t1 = A + B * exp + C * Math.pow(exp, 2) + D * Math.pow(exp, 3) + E * Math.pow(exp, 4);
		double t2 = 1 + F * exp + G * Math.pow(exp, 2) + H * Math.pow(exp, 3) + A2 * Math.pow(exp, 4);		
		return Math.max(t1 / t2,0);
	}
		
}
