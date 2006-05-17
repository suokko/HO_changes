package de.hattrickorganizer;



public class RoundingTest {

	public static void main(String[] args) {
		double curr_rate = 1.4;
		for(int i = 0; i < 50; i++) {
			double price = i * 10000 / curr_rate;
			double minvalue= 1000d / curr_rate;
			int minaccu = (int) Math.pow( 10, (int)(Math.log(minvalue)/Math.log(10d)+0.5) );         
			int accuracy = (int) Math.max(minaccu , Math.pow(10, (int)(Math.log(price) / Math.log(10d)) - 2));
			double price2 =  ( (int) (price / accuracy + 0.5d ) ) * accuracy;     
			System.out.println(price + " " + " " + price2 + " "+ minaccu + " " + accuracy);			
		}
	}


}
