package de.hattrickorganizer.prediction;


public class Net
{

    public double param[];
    public int layer[];

    public Net()
    {
    }

    public final int[] getLayer()
    {
        return layer;
    }

    public final double[] getParam()
    {
        return param;
    }

    public final void setLayer(int layer[])
    {
        this.layer = layer;
    }

    public final void setParam(double param[])
    {
        this.param = param;
    }
}
