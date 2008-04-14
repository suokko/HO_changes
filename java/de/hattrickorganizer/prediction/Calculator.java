package de.hattrickorganizer.prediction;

import plugins.IEPVData;

// Referenced classes of package prediction:
//            Net

public class Calculator
{

    protected int layer[];
    private double param[];

    public Calculator(Net net)
    {
        layer = null;
        param = net.param;
        layer = net.layer;
    }

    public final double calculate(IEPVData iepvdata, int i)
    {
        double skills[] = normalizeSkills(iepvdata, i);
        double ad1[] = null;
        double ad2[] = skills;
        int j = 0;
        for(int k = 1; k < layer.length; k++)
        {
            ad1 = new double[layer[k]];
            for(int l = 0; l < ad1.length; l++)
            {
                ad1[l] = scaleValue(applyParam(ad2, layer[k - 1], j));
                j += layer[k - 1] + 1;
            }

            ad2 = ad1;
        }

        double d;
        d = applyParam(ad1, layer[layer.length - 1], j);
        return scaleEndValue(d);
    }

    protected double[] normalizeSkills(IEPVData iepvdata, int i)
    {
        double ad[];
        (ad = new double[layer[0]])[0] = ((double)iepvdata.getAge() - 16D) / 20D;
        ad[1] = (double)iepvdata.getForm() / 8D;
        ad[2] = iepvdata.getStamina() / 9D;
        ad[3] = iepvdata.getGoalKeeping() / 20D;
        ad[4] = iepvdata.getPlayMaking() / 20D;
        ad[5] = iepvdata.getPassing() / 20D;
        ad[6] = iepvdata.getWing() / 20D;
        ad[7] = iepvdata.getDefense() / 20D;
        ad[8] = iepvdata.getAttack() / 20D;
        ad[9] = iepvdata.getSetPieces() / 20D;
        ad[10] = (double)i / 16D;
        ad[11] = (double)iepvdata.getExperience() / 20D;
        ad[12] = (double)iepvdata.getLeadership() / 8D;
        return ad;
    }

    protected static double scaleEndValue(double d)
    {
        if(d < 0.0D)
            return 5000D;
        else
            return d * 10000000D;
    }

    private double applyParam(double ad[], int num, int start)
    {
        if(layer[1] == 0)
            return 0.0D;
        double d = param[start];
        for(int k = 0; k < num; k++)
            d += ad[k] * param[start + 1 + k];

        return d;
    }

    private static double scaleValue(double d)
    {
        if(d > 37D)
            return 1.0D;
        if(d < -37D)
            return 0.0D;
        else
            return 1.0D / (1.0D + Math.exp(-d));
    }

    protected static double log10(double d)
    {
        return Math.log(d) / Math.log(10D);
    }

    protected static double normalizeAge(int i, int j)
    {
        double d = j + (i - 17) * 16;
        double d1 = 291554.20000000001D;
        double d2 = 138957.60000000001D;
        double d3 = 117654.89999999999D;
        double d4 = -1198.1559999999999D;
        double d5 = 4.5596709999999998D;
        double d6 = 0.38156820000000002D;
        double d7 = -0.34029870000000001D;
        double d8 = 0.32500240000000002D;
        double d9 = 0.031705280000000002D;
        double d10 = -0.00053846220000000004D;
        double d11 = 5.2265410000000003E-06D;
        double d12 = d1 + d2 * d + d3 * Math.pow(d, 2D) + d4 * Math.pow(d, 3D) + d5 * Math.pow(d, 4D) + d6 * Math.pow(d, 5D);
        double d13 = 1.0D + d7 * d + d8 * Math.pow(d, 2D) + d9 * Math.pow(d, 3D) + d10 * Math.pow(d, 4D) + d11 * Math.pow(d, 5D);
        return d12 / d13 / 350000D;
    }
}
