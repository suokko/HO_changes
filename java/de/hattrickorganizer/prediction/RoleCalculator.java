package de.hattrickorganizer.prediction;

import plugins.IEPVData;

// Referenced classes of package prediction:
//            Calculator, Net

public class RoleCalculator extends Calculator
{

    public RoleCalculator(Net net)
    {
        super(net);
    }

    protected final double[] normalizeSkills(IEPVData iepvdata, int i)
    {
        double ad[];
        (ad = new double[layer[0]])[0] = normalizeAge(iepvdata.getAge(), i);
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
        ad[11] = Math.pow((double)iepvdata.getExperience() / 20D, 2D);
        return ad;
    }
}
