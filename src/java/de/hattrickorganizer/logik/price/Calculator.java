// %3987013374:hoplugins.stt%
package de.hattrickorganizer.logik.price;

import plugins.IEPVData;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class Calculator {
	//~ Static fields/initializers -----------------------------------------------------------------

	protected int[] LAYER = null;		 
	private double[] params;

	public Calculator(Net netData) {
		this.params = netData.getParam();	
		LAYER = netData.getLayer();
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param player TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double calculate(IEPVData player, int WEEK) {
		
		double[] normalizedParams = normalizeParameter(player, WEEK);

		double[] output = null;
		double[] input = normalizedParams;
		int count = 0;
		for(int hiddenLayer = 1; hiddenLayer<LAYER.length; hiddenLayer++) {
			output = new double[LAYER[hiddenLayer]];
			for(int i = 0; i < output.length; i++) {				
				output[i]=sigmoid(calculateLayer(input,LAYER[hiddenLayer-1],count));
				count += LAYER[hiddenLayer-1] + 1;					
			}	
			input = output;		
		}			
		double pawmlp = calculateLayer(output,LAYER[LAYER.length-1],count);
				
		return getPrice(pawmlp);
	}

	protected double[] normalizeParameter(IEPVData player, int WEEK) {		
		
		double[] normalizedParams = new double[LAYER[0]];			
		normalizedParams[0] = (player.getAge() - 16d) / 20d;		
		normalizedParams[1] = player.getForm() / 8d;		
		normalizedParams[2] = player.getStamina() / 9d;
		normalizedParams[3] = player.getGoalKeeping() / 20d;
		normalizedParams[4] = player.getPlayMaking() / 20d;
		normalizedParams[5] = player.getPassing() / 20d;
		normalizedParams[6] = player.getWing() / 20d;
		normalizedParams[7] = player.getDefense() / 20d;
		normalizedParams[8] = player.getAttack() / 20d;
		normalizedParams[9] = player.getSetPieces() / 20d;
		normalizedParams[10] = WEEK / 16d;
		normalizedParams[11] = player.getExperience() / 20d;
		normalizedParams[12] = player.getLeadership() / 8d;
		return normalizedParams;
	}
	
	protected double getPrice(double val) {
		//I normalized the price to 10 Mio Euro,
		if (val<0) {
			return 5000;		
		}
		return val * 1e7;
	}

	private double calculateLayer(double[] inputParams, int loop, int index) {
		if (LAYER[1]==0) {
			return 0;
		}				
		double value = params[index];
		for(int i = 0; i < loop; i++) {
			value = value + inputParams[i]*params[index+1+i];
		}			
		return value;
	}

	private double sigmoid(double value) {
		if (value > 37) {
			return 1;
		} else if (value < -37) {
			return 0;
		} else {
			return 1 / (1 + Math.exp(-value));
		}
	}

	protected double log10(double i) {		
		return Math.log(i)/Math.log(10);
	}
	
	protected double poly2linear(double x) {
		return (1.34943 -0.53468 * x + 0.20058 * Math.pow(x,2) -7.76373E-03 * Math.pow(x,2) )/13d;
	}

	protected double exp2linear(int x,int base) {		
		return Math.exp(-Math.pow((x-base)/6d,2d));
	}	
	protected double power2linear(double x,double limit) {
		return Math.pow(x,2) / Math.pow(limit,2);
	}	
	
	protected double log2linear(double x,double base) {
		return Math.log(base)/Math.log(x-15d);
	}	
	
	protected double getFullAge(int age, int week) {
		double X = week + (age-17)*16;
		double A0 = 291554.2;
		double A1 = 138957.6;
		double A2 = 117654.9;
		double A3 = -1198.156;
		double A4 = 4.559671;
		double A5 = 0.3815682;
		double B1 = -0.3402987;
		double B2 = 0.3250024;
		double B3 = 3.170528E-02;
		double B4 = -5.384622E-04;
		double B5 = 5.226541E-06;
		double fac1 = A0 + A1 * X + A2 * Math.pow(X,2) + A3 * Math.pow(X,3) + A4 * Math.pow(X,4) + A5 * Math.pow(X,5);
		double fac2 = 1 + B1 * X + B2 * Math.pow(X,2) + B3 * Math.pow(X,3) + B4 * Math.pow(X,4) + B5 * Math.pow(X,5); 
		return fac1 / fac2 / 350000.0;
	}
	
}
