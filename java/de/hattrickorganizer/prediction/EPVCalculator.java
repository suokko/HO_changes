package de.hattrickorganizer.prediction;

import java.util.HashMap;
import java.util.Map;

import plugins.IEPVData;
import de.hattrickorganizer.prediction.rjmlp.Net;
import de.hattrickorganizer.tools.Helper;

public class EPVCalculator
{

    private static EPVCalculator epvCalculator = null;
    private static String networkStructure = "@age,@fo,@xp,@lead,@st,@gk,@pm,@ps,@wi,@de,@sc,@sp,@spec,@agg,@pop,@hon,@week:20:20:price";
    private static String weightsFilename = "prediction/epvWeights.mlp";
    private Net neuronalNetwork;

    public static EPVCalculator getInstance() {
        if(epvCalculator == null)
            epvCalculator = new EPVCalculator();
        return epvCalculator;
    }

    private EPVCalculator () {
    	neuronalNetwork = new Net(networkStructure, weightsFilename);
//    	System.out.println(neuronalNetwork.toString());
    }

    private void normalize (Map inputMap) {
    	double age = ((Double)inputMap.get("age")).doubleValue();
    	inputMap.put("age", new Double(age-17));
    }

    public final double getPrice (IEPVData iepvdata, int week, double currencyRate) {
    	Map inputMap = new HashMap();
//        "@age,@fo,@xp,@lead,@st,@gk,@pm,@ps,@wi,@de,@sc,@sp,@spec,@agg,@pop,@hon,@week:20:20:price";
    	inputMap.put("age", new Double(iepvdata.getAge()));
    	inputMap.put("fo", new Double(iepvdata.getForm()));
    	inputMap.put("xp", new Double(iepvdata.getExperience()));
    	inputMap.put("lead", new Double(iepvdata.getLeadership()));
    	inputMap.put("st", new Double(iepvdata.getStamina()));
    	inputMap.put("gk", new Double(iepvdata.getGoalKeeping()));
    	inputMap.put("pm", new Double(iepvdata.getPlayMaking()));
    	inputMap.put("ps", new Double(iepvdata.getPassing()));
    	inputMap.put("wi", new Double(iepvdata.getWing()));
    	inputMap.put("de", new Double(iepvdata.getDefense()));
    	inputMap.put("sc", new Double(iepvdata.getAttack()));
    	inputMap.put("sp", new Double(iepvdata.getSetPieces()));
    	inputMap.put("spec", new Double(iepvdata.getSpeciality()));
    	inputMap.put("agg", new Double(iepvdata.getAggressivity()));
    	inputMap.put("pop", new Double(iepvdata.getPopularity()));
    	inputMap.put("hon", new Double(iepvdata.getHonesty()));
    	inputMap.put("week", new Double(week));

    	normalize (inputMap); // Normalize

    	double val = neuronalNetwork.calculate("price", inputMap);
    	double price = Math.pow(10, val*8);
    	// Round to thousands (in euro/dollar)
    	price = Helper.round(price, -3);
    	if (price < 1000)
    		price = 1000;
    	// TODO currencyRate ok?
    	return (price * 10/currencyRate);
    }

}
