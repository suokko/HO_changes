package de.hattrickorganizer.prediction;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import plugins.IEPVData;

// Referenced classes of package prediction:
//            Calculator, HOEncrypter, KeeperCalculator, Net, 
//            RoleCalculator, ZipHelper

public class EPVCalculator
{

    private static Calculator calc[] = new Calculator[5];
    private static EPVCalculator epvCalculator = null;

    public static EPVCalculator getInstance()
    {
        if(epvCalculator == null)
            epvCalculator = new EPVCalculator();
        return epvCalculator;
    }

    private EPVCalculator()
    {
        ZipHelper ziphelper = null;
        try
        {
            ziphelper = new ZipHelper("epv.dat");
        }
        catch(Exception _ex) { }
        calc[0] = new RoleCalculator(parseFile(ziphelper, "defense.dat"));
        calc[1] = new RoleCalculator(parseFile(ziphelper, "mid.dat"));
        calc[2] = new RoleCalculator(parseFile(ziphelper, "wing.dat"));
        calc[3] = new RoleCalculator(parseFile(ziphelper, "attack.dat"));
        calc[4] = new KeeperCalculator(parseFile(ziphelper, "keeper.dat"));
        try
        {
            ZipHelper.close();
            return;
        }
        catch(RuntimeException _ex)
        {
            return;
        }
    }

    private static Net parseFile(ZipHelper ziphelper, String s)
    {
        HOEncrypter hoencrypter = HOEncrypter.getInstance();
        Net net = new Net();
        try
        {
            String s1 = hoencrypter.decrypt(ZipHelper.getFile(s));
            BufferedReader bufferedreader = new BufferedReader(new StringReader(s1));
            String s2 = null;
            ArrayList arraylist = new ArrayList();
            String s3 = bufferedreader.readLine();
            StringTokenizer stringtokenizer;
            (stringtokenizer = new StringTokenizer(s3, " ")).nextToken();
            stringtokenizer.nextToken();
            stringtokenizer.nextToken();
            ArrayList arraylist1 = new ArrayList();
            for(; stringtokenizer.hasMoreTokens(); arraylist1.add(stringtokenizer.nextToken()));
            int ai[] = new int[arraylist1.size() - 1];
            for(int i = 0; i < arraylist1.size() - 1; i++)
                ai[i] = Integer.parseInt((String)arraylist1.get(i));

            net.layer = ai;
            bufferedreader.readLine();
            while((s2 = bufferedreader.readLine()) != null) 
                arraylist.add(new Double(Double.parseDouble(s2)));
            double ad[] = new double[arraylist.size()];
            for(int j = 0; j < arraylist.size(); j++)
                ad[j] = ((Double)arraylist.get(j)).doubleValue();

            net.param = ad;
        }
        catch(Exception _ex)
        {
            net.param = new double[0];
        }
        return net;
    }

    public final double calculate(IEPVData iepvdata, int i)
    {
        double d = calcBySkills(iepvdata, i);
        if(isHighLeadershipXP(iepvdata))
        {
            double d1 = calcTrainerPotential(iepvdata);
            d = Math.max(d, d1);
        }
        return d;
    }

    private static double calcBySkills(IEPVData iepvdata, int i)
    {
        if(iepvdata.getMaxSkill() < 5D)
            return 0.0D;
        if(iepvdata.getMaxSkill() < 5.5D)
            return 1000D;
        if(iepvdata.getPlayerType() == 2)
            return calc[0].calculate(iepvdata, i);
        if(iepvdata.getPlayerType() == 3)
            return calc[1].calculate(iepvdata, i);
        if(iepvdata.getPlayerType() == 4)
            return calc[2].calculate(iepvdata, i);
        if(iepvdata.getPlayerType() == 5)
            return calc[3].calculate(iepvdata, i);
        else
            return calc[4].calculate(iepvdata, i);
    }

    private static boolean isHighLeadershipXP(IEPVData iepvdata)
    {
        return iepvdata.getExperience() > 4 && iepvdata.getLeadership() > 4;
    }

    private static double calcTrainerPotential(IEPVData iepvdata)
    {
        double d;
        double d1;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        if(iepvdata.getLeadership() == 5)
        {
            d = 4907.7653600000003D;
            d1 = 5100.3091800000002D;
            d2 = -2172.3750199999999D;
            d3 = 201.17491999999999D;
            d4 = 1.69414D;
            d5 = -0.19883999999999999D;
            d6 = 0.0091000000000000004D;
            d7 = 0.00020000000000000001D;
            d8 = 1.0000000000000001E-05D;
        } else
        if(iepvdata.getLeadership() == 6)
        {
            d = 26135.228210000001D;
            d1 = -27669.94543D;
            d2 = 12711.01758D;
            d3 = -2524.27117D;
            d4 = 177.26464000000001D;
            d5 = -0.36077999999999999D;
            d6 = 0.055719999999999999D;
            d7 = -0.0056499999999999996D;
            d8 = 0.00032000000000000003D;
        } else
        if(iepvdata.getLeadership() == 7)
        {
            d = 23778.934109999998D;
            d1 = -20864.022679999998D;
            d2 = 12076.840469999999D;
            d3 = -3275.3708999999999D;
            d4 = 310.50434000000001D;
            d5 = -0.29254000000000002D;
            d6 = 0.034700000000000002D;
            d7 = -0.0032100000000000002D;
            d8 = 0.00023000000000000001D;
        } else
        {
            return 0.0D;
        }
        double d9 = iepvdata.getExperience();
        double d10 = d + d1 * d9 + d2 * Math.pow(d9, 2D) + d3 * Math.pow(d9, 3D) + d4 * Math.pow(d9, 4D);
        double d11 = 1.0D + d5 * d9 + d6 * Math.pow(d9, 2D) + d7 * Math.pow(d9, 3D) + d8 * Math.pow(d9, 4D);
        return Math.max(d10 / d11, 0.0D);
    }

    public final double getPrice(IEPVData iepvdata, int i, double d)
    {
        double d1;
        d1 = ((d1 = calculate(iepvdata, i)) * 10D) / d;
        double d2 = 1000D / d;
        int j;
        int k = (int)Math.max(j = (int)Math.pow(10D, (int)(Math.log(d2) / Math.log(10D) + 0.5D)), Math.pow(10D, (int)(Math.log(d1) / Math.log(10D)) - 2));
        return d1 = (int)(d1 / (double)k + 0.5D) * k;
    }

}
