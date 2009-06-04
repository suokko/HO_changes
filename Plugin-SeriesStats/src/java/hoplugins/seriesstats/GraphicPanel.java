package hoplugins.seriesstats;

import java.awt.*;
import javax.swing.*;
/**
 * Zeigt Statistiken in Form eins Liniendiagrames an
 */
public class GraphicPanel extends JPanel
{
    //Seitenabstände bis zum Koordinatenkreuzanfang
    private int SL = 60; //Dynamisch berechnen
    public int SR = 25;
    public int SU = 50;
    public int SO = 25;
    public int SA = 50; //Abstand zwischen den Hilfslinien
    public  boolean     print           = false;
    
    private String      xBezeichner     = "";
    private String      yBezeichner     = "";

    private boolean     beschriftung    = false;
    private boolean     hilfslinien     = true;
    
    private boolean     m_bMaxMinBerechnen       = false;
    
    private GraphicData[]  m_clStatistikModel = null;
    
    private String[]          m_clYAchseBeschriftung = null;
    private java.text.NumberFormat m_clYAchseFormat  = null;

    public static final AlphaComposite DEFAULTALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);    
    public static final AlphaComposite REDUCEDALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);    
    
//##Konstruktoren######################################################	

/**
 * Konstruktor
 */
    public GraphicPanel( boolean maxminBerechnen )
    {
        m_bMaxMinBerechnen = maxminBerechnen;
        setDoubleBuffered(true);
    }
	


//--Set und Get-methoden------------------------------

        
    /**
     * Maximaler Konstruktor
     * 
     * @param xBezeichner Bezeichnung der x-Achse
     * @param yBezeichner Bezeichnung der y-Achse
     * @param beschriftung Beschriftung des Graphen
     * @param hilflinien Vertikale und Horizontale Linien durch den Graphen
     * @param startwert Wert, mit der die x-Achse zu zählen anfängt
     * @param abstand Der Wertabstand der x-Achse zwischen 2 Werten
     * @param farbe Farbe des Graphen
     */
    public void setAllValues ( GraphicData[] models, String[] yAchseBeschriftung, java.text.NumberFormat yAchseFormat, String xBezeichner, String yBezeichner, boolean beschriftung, boolean hilfslinien )
    {
        this.m_clStatistikModel=models;
        this.m_clYAchseBeschriftung=yAchseBeschriftung;
        this.xBezeichner=xBezeichner;
        this.yBezeichner=yBezeichner;
        this.beschriftung=beschriftung;
        this.hilfslinien=hilfslinien;
        this.m_clYAchseFormat=yAchseFormat;
        repaint();
        //update(this.getGraphics());
        
    }
        
    public void setModel( GraphicData[] models )
    {
        m_clStatistikModel = models;
        repaint();
    }
    
    public GraphicData[] getModel()
    {
        return m_clStatistikModel;
    }
    
    /** 
     * Ein bestimmtes Model holen
     */
    public GraphicData getModel( String name )
    {
        for ( int i = 0; m_clStatistikModel != null && m_clStatistikModel.length > i; i++ )
        {
            if ( m_clStatistikModel[i].getName ().equals ( name ) )
            {
                return m_clStatistikModel[i];
            }
        }
        return null;
    }
    
    /**
     * Einen bestimmten Graf sichtbar/unsichtbar machen
     */
    public void setShow( String name, boolean show )
    {
        for ( int i = 0; m_clStatistikModel != null && m_clStatistikModel.length > i; i++ )
        {
            if ( m_clStatistikModel[i].getName ().equals ( name ) )
            {
                m_clStatistikModel[i].setShow ( show );
                break;
            }
        }
        repaint();
    }
    

    /**
    * Ein- oder Ausschalten der Beschriftung des Graphen
    *
    * @param beschriftung true: an / false: aus
    */
    public void setBeschriftung(boolean beschriftung)
    {
        this.beschriftung=beschriftung;
        repaint();//update(this.getGraphics());
    }

    /**
    * Liefert den Zustand der Beschriftung (an/aus)
    */
    public boolean getBeschriftung()
    {
        return beschriftung;
    }

    /**
     * Ein- und Ausschalten der Hilfslinien
     *
     * @param hilfslinien true: an / false: aus
     */
    public void setHilfslinien(boolean hilfslinien)
    {
            this.hilfslinien=hilfslinien;
            repaint();//update(this.getGraphics());
    }

    /**
    * Liefert den Zustand der Hilfslinien (an/aus)
    */
    public boolean getHilfslinien()
    {
            return hilfslinien;
    }

            
//#####################################################################

        public void print( Graphics g )
        {
                update(g);
        }
    
/**
 * Zeichnet die GComponent
 */
	public void paint(Graphics g)
	{
		update(g);
	}

/**
 * Zeichnet die GComponent
 */
	public void update(Graphics g)
	{
            if ( g != null )
            {
                ( (java.awt.Graphics2D)g ).setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            
		//Initialsierung des Fensters
		Rectangle r = getBounds();
		int b = r.width-1;
		int h = r.height-1;
//		g.setColor(Color.lightGray);
//		g.fill3DRect(1,1,b-1,h-1,false);
		g.setColor(Color.white);		
		g.fillRect(1,1,b-1,h-1);
		g.setColor(Color.darkGray);
                int schriftgroesse = gui.UserParameter.instance ().schriftGroesse+2;
                if ( print )
                {
                    schriftgroesse /=2;
                }
		g.setFont(new Font("SansSerif",Font.BOLD,schriftgroesse));
		g.drawString(yBezeichner,8,18);
		g.drawString(xBezeichner,b-150,h-8);
		
		//Höchster Wert in der Menge
		double max=20;
		double min=0;
                double maxohneFaktor=20;
                double minohneFaktor=0;
                //MaxMin berechnen
                if ( m_bMaxMinBerechnen )
                {
                    max = maxFinder( true );
                    min = minFinder( true );
                    if (max<0) max=0;
                    if (min>0) min=0;
                }
                maxohneFaktor = maxFinder( false );
                minohneFaktor = minFinder( false );
                
		//Höhe der x-Achse bestimmen
		int xHoehe=(int)((h-SU-SO)/2+SO+(max+min)*(((h-SU-SO)/2)/(max-min)));
		//#############SL abhängig von max###############################+++++++++++++
		
                
                if ( m_clStatistikModel != null && m_clStatistikModel.length > 0 && m_clStatistikModel[0] != null  )
                {
                    //Massangaben einzeichnen
                    //Menge der y-Striche bestimmen
                    int f = 1;
                    if ( print ) f = 2;
                    int yStriche=(int)((h-SU-SO)/(SA/f));
                    if (yStriche==0) yStriche=1;
                    //Abstand der einzelnen Striche voneinander
                    double yAbstand=(((double)(h-SU-SO))/yStriche);
                    int smallschriftgroesse = gui.UserParameter.instance ().schriftGroesse;
                    if ( print )
                    {
                        smallschriftgroesse /=2;
                    }
                    g.setFont(new Font("SansSerif",Font.BOLD,smallschriftgroesse));
                    //Seitenabstand berechnen
                    SL = smallschriftgroesse 
                         + Math.max( (int)( ( g.getFontMetrics ().stringWidth ( m_clYAchseFormat.format ( max ) ) + 10 )  ), 
                                     (int)( ( g.getFontMetrics ().stringWidth ( m_clYAchseFormat.format ( min ) ) + 10 )  ) );
                    for (int i=yStriche;i>=0;i--)
                    {
                            //Striche y zeichnen: Höhe - Abstand vom unteren Rand - Vielfaches des Strichabstandes
                            if (hilfslinien) 
                            {
                                    g.setColor(Color.lightGray);
                                    g.drawLine(SL+5,(int)(h-SU-yAbstand*i),b-SR,(int)(h-SU-yAbstand*i));

                            }
                            g.setColor(Color.black);
                            g.drawLine(SL-5,(int)(h-SU-yAbstand*i),SL+5,(int)(h-SU-yAbstand*i));
                            //Wert pro Strich
                            int ypos = (int)(h-SU+smallschriftgroesse/2-yAbstand*i);
                            g.drawString(m_clYAchseFormat.format(((double)(max-min)/(yStriche)*i+min)),smallschriftgroesse,ypos);
                    }

                     //Menge der x-Striche bestimmen - Wird jetzt bei der BeschriftungXAchse gemacht
                    
//                    int xStriche=(int)((b-SL-SR)/SA+1);
//                    if (xStriche>m_clStatistikModel[0].getWerte ().length) xStriche=m_clStatistikModel[0].getWerte ().length;
                    //Abstand der einzelnen Striche voneinander
//                    double xAbstand=((double)(b-SL-SR)/(xStriche));
//                    for (int i=0;i<=xStriche;i++)
//                    {
//                            //Striche x zeichnen: Abstand vom linken Rand + Vielfaches vom Abstand
////                            if (hilfslinien) 
////                            {
////                                    g.setColor(Color.lightGray);
////                                    g.drawLine((int)(SL+xAbstand*(i)),h-SU,(int)(SL+xAbstand*(i)),SO);
////                            }
////                            g.setColor(Color.black);
////                            g.drawLine((int)(SL+xAbstand*(i)),xHoehe-5,(int)(SL+xAbstand*(i)),xHoehe+5);
//                            //Wert pro Strich: Strichnummer * Wertemenge / Menge der Striche
//                            //###
//                            //g.drawString(m_clStatistikModel[0].getBezeichnungXAchse ()[i],(int)(SL+xAbstand*(i)-10),xHoehe+20);
//                    }

                    //Koordinatenkreuz
                    g.drawLine(SL,SO,SL,h-SU);
                    g.drawLine(SL,xHoehe,b-SR,xHoehe);
                    
                    int schriftbreite = 0;
                    if ( m_clStatistikModel[0].getWerte ().length > 0 )
                    {
                        schriftbreite = Math.max( (int)( ( g.getFontMetrics ().stringWidth ( m_clStatistikModel[0].getFormat ().format ( maxohneFaktor ) ) + 10 )  ), 
                                                  (int)( ( g.getFontMetrics ().stringWidth ( m_clStatistikModel[0].getFormat ().format ( minohneFaktor ) ) + 10 )  ) );
                    }
                    
                    //Beschriftung XAchse
                    showXAchseBeschriftung((Graphics2D)g,b,h);

                    //Wertelinie eintragen
                    for ( int i = 0; m_clStatistikModel != null && i < m_clStatistikModel.length; i ++ )
                    {
                        if ( m_clStatistikModel[i] != null && m_clStatistikModel[i].isShow () )
                        {
                            wertLinien((Graphics2D)g,b,h,m_clStatistikModel[i].getWerte (),m_clStatistikModel[i].getFaktor(),max,min,beschriftung,m_clStatistikModel[i].getColor (),m_clStatistikModel[i].getFormat (), schriftbreite);
                        }
                    }
                }//Ende Keine Werte
            }
	}

	//Zeichnen eines Liniendiagrammes
	private void wertLinien(Graphics2D g, int b, int h, double[] werte,double faktor, double max, double min, boolean beschriftung, Color farbe, java.text.NumberFormat format, int schriftbreite)
	{
            if ( werte.length > 0 )
            {
		int x1,x2,y1,y2;
		int mengeBeschriftung=(int)((b-SL-SR)/schriftbreite)-1;
		if (mengeBeschriftung==0) mengeBeschriftung=1;
		int abstandBeschriftung=(int)(werte.length/mengeBeschriftung);
		if (abstandBeschriftung==0) abstandBeschriftung=1;

		y2=(int)((h-SU-((double)(h-SU-SO)/(max-min)*(werte[werte.length-1]*faktor-min))));
		x2=SL;//(int)(((double)(b-SL-SR))/(werte.length)*(werte.length-1)+SL);
//		if (beschriftung && 0%abstandBeschriftung==0)
//		{
//			g.setColor(Color.darkGray);
//			g.drawString(format.format ( werte[werte.length-1] ),x2-10,y2-10);
//		}
		for(int i=1;i<werte.length;i++)
		{
			x1=x2;
			y1=y2;
			y2=(int)((h-SU-((double)(h-SU-SO)/(max-min)*(werte[werte.length-i-1]*faktor-min))));
			x2=(int)(((double)(b-SL-SR))/(werte.length)*(i)+SL);
//			y2=(int)((h-SU-((double)(h-SU-SO)/(max-min)*(werte[i]-min))));
//			x2=(int)(((double)(b-SL-SR))/(werte.length)*(i)+SL);
			g.setColor(farbe);
                        //g.setComposite( REDUCEDALPHA );
			g.drawLine(x1,y1,x2,y2);
                        g.drawLine(x1,y1+1,x2,y2+1);
                        //g.setComposite( DEFAULTALPHA );
			if (beschriftung && i%abstandBeschriftung==0)
			{
				g.setColor(Color.darkGray);
                                int xpos = x2 - g.getFontMetrics ().stringWidth ( format.format ( werte[werte.length-i-1] ) ) / 2;
                                int ypos = y2 - g.getFont().getSize()/2;
				g.drawString(format.format ( werte[werte.length-i-1] ),xpos,ypos);
			}
		}
            }
	}
    

	//Zeichnen eines Liniendiagrammes
	private void showXAchseBeschriftung(Graphics2D g, int b, int h)
	{
            if ( m_clYAchseBeschriftung.length > 0 )
            {
                int schriftbreite = (int)( ( g.getFontMetrics ().stringWidth ( m_clYAchseBeschriftung[0] ) + 10 ) * 1.5 );
                
                
		int x1,x2,y1,y2;
		int mengeBeschriftung=(int)((b-SL-SR)/schriftbreite);
		if (mengeBeschriftung==0) mengeBeschriftung=1;
		int abstandBeschriftung=(int)(m_clYAchseBeschriftung.length/mengeBeschriftung);
		if (abstandBeschriftung==0) abstandBeschriftung=1;

		y2=this.getHeight ()-SU+25;
		x2=(int)(((double)(b-SL-SR))/(m_clYAchseBeschriftung.length)*(m_clYAchseBeschriftung.length-1)+SL);
//		if (0%abstandBeschriftung==0)
//		{
//                        g.setColor(Color.lightGray);
//                        g.drawLine(x2,this.getHeight ()-SU,x2,SO);
//                    
//			g.setColor(Color.black);
//			g.drawString( m_clYAchseBeschriftung[0], x2-20, y2 );
//                        g.drawLine ( x2, this.getHeight ()-SU-8, x2, this.getHeight ()-SU+8 );
//		}
		for(int i=0;i<m_clYAchseBeschriftung.length;i++)
		{
			x1=x2;
			y1=y2;
			y2=this.getHeight ()-SU+15;
			x2=(int)(((double)(b-SL-SR))/(m_clYAchseBeschriftung.length)*(m_clYAchseBeschriftung.length-i-1)+SL);
//			y2=(int)((h-SU-((double)(h-SU-SO)/(max-min)*(werte[i]-min))));
//			x2=(int)(((double)(b-SL-SR))/(werte.length)*(i)+SL);
			g.setColor(Color.black);
                        //g.setComposite( REDUCEDALPHA );
                        //g.setComposite( DEFAULTALPHA );
			if (i%abstandBeschriftung==0)
			{
                            if ( hilfslinien )
                            {
                                g.setColor(Color.lightGray);
                                g.drawLine(x2,this.getHeight ()-SU,x2,SO);
                            }
                            g.setColor(Color.black);
                            int xpos = x2 - g.getFontMetrics ().stringWidth ( m_clYAchseBeschriftung[i] ) / 2;
                            int ypos = y2 + g.getFont().getSize();
                            g.drawString ( m_clYAchseBeschriftung[i], xpos, ypos );
                            g.drawLine ( x2, this.getHeight ()-SU-8, x2, this.getHeight ()-SU+10 );
			}
		}
            }
	}
        
        //Findet den Maximalwert in einem double-Array
	private double maxFinder( boolean usefaktor)
	{
		double max=1;
		for( int i=0; m_clStatistikModel != null && m_clStatistikModel.length > i && m_clStatistikModel[i] != null && i < m_clStatistikModel.length; i++ )
		{
                    if ( m_clStatistikModel[i].isShow () )
                    {
                        if ( usefaktor )
                        {
                            if( m_clStatistikModel[i].getMaxValue () * m_clStatistikModel[i].getFaktor() > max ) max = m_clStatistikModel[i].getMaxValue () * m_clStatistikModel[i].getFaktor();
                        }
                        else
                        {
                            if( m_clStatistikModel[i].getMaxValue () > max ) max = m_clStatistikModel[i].getMaxValue ();
                        }
                    }
		}
		return (max);
	}

	//Findet den Minimalwert in einem double-Array
	private double minFinder( boolean usefaktor )
	{
		double min=0;
		for( int i=0; m_clStatistikModel != null && m_clStatistikModel.length > i && m_clStatistikModel[i] != null && i < m_clStatistikModel.length; i++ )
		{
                    if ( m_clStatistikModel[i].isShow () )
                    {
                        if ( usefaktor )
                        {
                            if( m_clStatistikModel[i].getMinValue () * m_clStatistikModel[i].getFaktor() < min ) min = m_clStatistikModel[i].getMinValue () * m_clStatistikModel[i].getFaktor();
                        }
                        else
                        {
                            if( m_clStatistikModel[i].getMinValue () < min ) min = m_clStatistikModel[i].getMinValue ();
                        }
                    }
                }
		return (min);
	}

        
	// gibt ein String zurück, der einen double-Wert mit n Nachkommastellen repräsentiert
	public String formatedDouble(double d, byte n)
	{
		
		String sHilf=Double.toString(d);
		String sResultValue=new String();


		int i=0;
		while((i<sHilf.length()) && (!(sHilf.charAt(i)=='.')))
		{
			sResultValue+=sHilf.charAt(i);
			i++;
		}

		if(sHilf.charAt(i)=='.') 
		{
			sResultValue+=',';
			i++;

			for(int j=0; (i<sHilf.length()) && (j<=n);j++)
			{
				sResultValue+=sHilf.charAt(i);
				i++;
				j++;
			}
		}

		return (sResultValue);
	}

}
