package hoplugins.seriesstats;


/**
 * Model für das StatistikPanel
 */

public class GraphicData
{
    private double[]        m_clWerte               =   null;
    private String          m_sName                 =   "";
    private boolean         m_bShow                 =   true;
    private java.awt.Color  m_clColor               =   java.awt.Color.blue;
    private java.text.NumberFormat m_clFormat       =   null;
    private double          m_dFaktor               =   1;
    
    public GraphicData( double[] werte, String name, boolean show, java.awt.Color farbe, java.text.NumberFormat format )
    {
        this( werte, name, show, farbe, format, 1 );
    }
    
    public GraphicData( double[] werte, String name, boolean show, java.awt.Color farbe, java.text.NumberFormat format, double faktor )
    {
        m_clWerte               =   werte;
        m_sName                 =   name;
        m_bShow                 =   show;
        m_clColor               =   farbe;
        m_clFormat              =   format;
        m_dFaktor               =   faktor;
    }

    /** Getter for property m_clWerte.
     * @return Value of property m_clWerte.
     */
    public double[] getWerte ()
    {
        return this.m_clWerte;
    }
    
    /** Setter for property m_clWerte.
     * @param m_clWerte New value of property m_clWerte.
     */
    public void setWerte (double[] m_clWerte)
    {
        this.m_clWerte = m_clWerte;
    }
    
    /** Getter for property m_bShow.
     * @return Value of property m_bShow.
     */
    public boolean isShow ()
    {
        return m_bShow;
    }
    
    /** Setter for property m_bShow.
     * @param m_bShow New value of property m_bShow.
     */
    public void setShow (boolean m_bShow)
    {
        this.m_bShow = m_bShow;
    }
    
    /** Getter for property m_clColor.
     * @return Value of property m_clColor.
     */
    public java.awt.Color getColor ()
    {
        return m_clColor;
    }
    
    /** Setter for property m_clColor.
     * @param m_clColor New value of property m_clColor.
     */
    public void setColor (java.awt.Color m_clColor)
    {
        this.m_clColor = m_clColor;
    }
    
    /** Getter for property m_sName.
     * @return Value of property m_sName.
     */
    public java.lang.String getName ()
    {
        return m_sName;
    }
    
    /** Setter for property m_sName.
     * @param m_sName New value of property m_sName.
     */
    public void setName (java.lang.String m_sName)
    {
        this.m_sName = m_sName;
    }
    
    //-----------------------------------
    
    public double getMaxValue()
    {
        double max = 0;
        for(int i=0; m_clWerte != null && i < m_clWerte.length; i++)
        {
                if ( m_clWerte[i] > max ) max = m_clWerte[i];
        }
        return (max);        
    }
    
    public double getMinValue()
    {
        double min = 0;
        for(int i=0; m_clWerte != null && i < m_clWerte.length; i++)
        {
                if ( m_clWerte[i] < min ) min = m_clWerte[i];
        }
        return (min);        
    }
    
    /** Getter for property m_clFormat.
     * @return Value of property m_clFormat.
     */
    public java.text.NumberFormat getFormat ()
    {
        return m_clFormat;
    }
    
    /** Setter for property m_clFormat.
     * @param m_clFormat New value of property m_clFormat.
     */
    public void setFormat (java.text.NumberFormat m_clFormat)
    {
        this.m_clFormat = m_clFormat;
    }
    
    /** Getter for property m_iFaktor.
     * @return Value of property m_iFaktor.
     *
     */
    public double getFaktor ()
    {
        return m_dFaktor;
    }
    
    /** Setter for property m_iFaktor.
     * @param m_iFaktor New value of property m_iFaktor.
     *
     */
    public void setFaktor (double m_dFaktor)
    {
        this.m_dFaktor = m_dFaktor;
    }
    
}