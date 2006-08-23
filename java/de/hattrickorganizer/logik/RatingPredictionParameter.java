package de.hattrickorganizer.logik;

import plugins.IRatingPredictionParameter;


public class RatingPredictionParameter implements IRatingPredictionParameter{
	
	private float[] values;
	
	public RatingPredictionParameter(float[] fs) {
		this.values = fs;
	}

	public float getParam(int i) {		
		if (i == 0) {
			throw new RuntimeException("Not Supported");
		}
		
		if (i > values.length ) {
			throw new RuntimeException("Not Supported");			
		}
		
		return values[i-1];
	}

		
}
