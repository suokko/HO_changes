package de.hattrickorganizer.prediction;

import plugins.IEPVData;

// Referenced classes of package prediction:
//            Calculator, Net

public class KeeperCalculator extends Calculator
{

    public KeeperCalculator(Net net)
    {
        super(net);
    }

    protected final double[] normalizeSkills(IEPVData iepvdata, int i)
    {
        double ad[];
        (ad = new double[layer[0]])[0] = normalizeAge(iepvdata.getAge(), i);
        ad[1] = (double)iepvdata.getForm() / 8D;
        ad[2] = iepvdata.getGoalKeeping() / 20D;
        ad[3] = iepvdata.getSetPieces() / 20D;
        ad[4] = (double)i / 16D;
        ad[5] = Math.pow((double)iepvdata.getExperience() / 20D, 2D);
        return ad;
    }
}
