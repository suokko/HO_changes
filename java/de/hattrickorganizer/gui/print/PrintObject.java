// %982440500:de.hattrickorganizer.gui.print%
package de.hattrickorganizer.gui.print;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Schmidt Alte Klasse muss noch ein bisschen angepasst werden
 */
public abstract class PrintObject extends JPanel implements Printable {
    //~ Instance fields ----------------------------------------------------------------------------

    // Drucker

    /** TODO Missing Parameter Documentation */
    protected PageFormat pf;

    // Druckbereich (obere linke Ecke, Breite, Höhe)

    /** TODO Missing Parameter Documentation */
    protected double dh;

    // Druckbereich (obere linke Ecke, Breite, Höhe)

    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    protected double dw;

    // Druckbereich (obere linke Ecke, Breite, Höhe)

    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    protected double dx;

    // Druckbereich (obere linke Ecke, Breite, Höhe)

    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    protected double dy;

    // Seitenformat

    /** TODO Missing Parameter Documentation */
    protected double ph;

    // Seitenformat

    /** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    protected double pw;

    // Seitenrand

    /** TODO Missing Parameter Documentation */
    protected int sr;

    //~ Constructors -------------------------------------------------------------------------------

    // Zoomfaktor
    //	protected double zoomfaktor = 1f;

    /**
     * Konstructor
     *
     * @param pf TODO Missing Constructuor Parameter Documentation
     */
    public PrintObject(PageFormat pf) {
        // Werte übernehmen
        this.pf = pf;

        // Papiergrösse
        pw = pf.getWidth();
        ph = pf.getHeight();

        // Druckbereich
        dx = pf.getImageableX() - sr;
        dy = pf.getImageableY();
        dh = pf.getImageableHeight();
        dw = pf.getImageableWidth();

        // Grösse des Panels festlegen
        setPreferredSize(new Dimension((int) ((pw + (2 * sr)) /*zoomfaktor*/),
                                       (int) ((ph + (2 * sr)) /*zoomfaktor*/)));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param graphics TODO Missing Method Parameter Documentation
     * @param pageFormat TODO Missing Method Parameter Documentation
     * @param pageIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws PrinterException TODO Missing Method Exception Documentation
     */
    public final int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
      throws PrinterException
    {
        final Graphics2D g2 = (Graphics2D) graphics;

        // Ausgabebereich festlegen
        g2.translate((int) dx, (int) dy);
        g2.setClip(0, 0, (int) dw, (int) dh);

        // Grafik ausgeben
        paintMe(g2);
        return Printable.PAGE_EXISTS;
    }

    //	/**
    //	 * @return double
    //	 */
    //	public double getZoomfaktor() {
    //		return zoomfaktor;
    //	}
    //
    //	/**
    //	 * @param d
    //	 */
    //	public void setZoomfaktor(double d) {
    //		if(d >= 0){
    //			zoomfaktor= d;
    //			setPreferredSize(new Dimension((int)((pw +2 * sr)*zoomfaktor),(int)((ph + 2 *sr)*zoomfaktor)));
    //			repaint();
    //		}
    //		
    //	}
    //	public void paintComponent(Graphics g){
    //		super.paintComponent(g);
    //		Graphics2D g2 = (Graphics2D)g;
    //		
    //		// Zoomfaktor einstellen
    //		g2.scale(zoomfaktor,zoomfaktor);
    //		
    //		// Ursprung in die linke obere Papierecke legen
    //		g2.translate(sr,sr);
    //		
    ////		// Papier zeichnen
    ////		g2.setPaint(Color.WHITE);
    ////		g2.fillRect(0,0,(int)pw,(int)ph);
    ////		
    ////		//Rand zeichnen
    ////		g2.setPaint(Color.DARK_GRAY);
    ////		g2.drawLine(0,(int)dy-1,(int)pw,(int)dy-1);
    ////		g2.drawLine(0,(int)(dy+dh+1),(int)pw,(int)(dy+dh+1));
    ////		g2.drawLine((int)dx-1,0,(int)dx-1,(int)ph);
    ////		g2.drawLine((int)(dx+dw+1),0,(int)(dx+dw+1),(int)ph);
    //		
    //		// Ursprung in die linke obere Ecke (Druckbereich) setzen
    //		g2.translate(dx,dy);
    //		
    //		// Ausgabebereich auf den druckbarenBereich begrenzen
    //		g2.setClip(0,0,(int)dw,(int)dh);
    //		
    //		paintMe(g2);
    //	}
    protected abstract void paintMe(Graphics2D g2);
}
