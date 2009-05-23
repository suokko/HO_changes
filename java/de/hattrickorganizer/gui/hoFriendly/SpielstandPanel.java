// %2465926262:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JPanel;


/**
 * Zeigt den Spielstand an
 */
public class SpielstandPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private Color GASTCOLOR = new Color(255, 0, 0);
    private Color HEIMCOLOR = new Color(0, 0, 255);
    private Color STANDARDCOLOR = new Color(255, 255, 0);
    private Font m_clKleineFont = new Font("SansSerife", Font.BOLD, 10);
    private Font m_clNormalFont = new Font("SansSerife", Font.BOLD, 16);

    //Spielstand
    private String SPIELSTAND = ". "
                                + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielminute");

    //Name der Gastmannschaft
    private String m_sGastmannschaft = "";

    //Name der Heimmannschaft
    private String m_sHeimmannschaft = "";

    //Vector mit CBItem ( Spielername + Spielminute ) für ein Tor der Gastmannschaft
    private Vector m_vGasttore = new Vector();

    //Vector mit CBItem ( Spielername + Spielminute ) für ein Tor der Heimmannschaft
    private Vector m_vHeimtore = new Vector();
    private int HORIZONTALOFFSET = 30;

    //Anzahl Tore Gastmannschaft
    private int m_iGasttore;

    //Anzahl Tore Heimmannschaft
    private int m_iHeimtore;

    //Spielminute
    private int m_iSpielminute;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielstandPanel object.
     */
    public SpielstandPanel() {
        setPreferredSize(new Dimension(300, 150));

        repaint();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_sGastmannschaft.
     *
     * @param m_sGastmannschaft New value of property m_sGastmannschaft.
     */
    public final void setGastmannschaft(java.lang.String m_sGastmannschaft) {
        this.m_sGastmannschaft = m_sGastmannschaft;
        repaint();
    }

    /**
     * Getter for property m_sGastmannschaft.
     *
     * @return Value of property m_sGastmannschaft.
     */
    public final java.lang.String getGastmannschaft() {
        return m_sGastmannschaft;
    }

    /**
     * Setter for property m_iGasttore.
     *
     * @param m_iGasttore New value of property m_iGasttore.
     */
    public final void setGasttorAnzahl(int m_iGasttore) {
        this.m_iGasttore = m_iGasttore;
        repaint();
    }

    /**
     * Getter for property m_iGasttore.
     *
     * @return Value of property m_iGasttore.
     */
    public final int getGasttorAnzahl() {
        return m_iGasttore;
    }

    /**
     * Setter for property m_vGasttore.
     *
     * @param m_vGasttore New value of property m_vGasttore.
     */
    public final void setGasttore(java.util.Vector m_vGasttore) {
        this.m_vGasttore = m_vGasttore;
        repaint();
    }

    /**
     * Getter for property m_vGasttore.
     *
     * @return Value of property m_vGasttore.
     */
    public final java.util.Vector getGasttore() {
        return m_vGasttore;
    }

    /**
     * Setter for property m_sHeimmannschaft.
     *
     * @param m_sHeimmannschaft New value of property m_sHeimmannschaft.
     */
    public final void setHeimmannschaft(java.lang.String m_sHeimmannschaft) {
        this.m_sHeimmannschaft = m_sHeimmannschaft;
        repaint();
    }

    /**
     * Getter for property m_sHeimmannschaft.
     *
     * @return Value of property m_sHeimmannschaft.
     */
    public final java.lang.String getHeimmannschaft() {
        return m_sHeimmannschaft;
    }

    /**
     * Setter for property m_iHeimtore.
     *
     * @param m_iHeimtore New value of property m_iHeimtore.
     */
    public final void setHeimtorAnzahl(int m_iHeimtore) {
        this.m_iHeimtore = m_iHeimtore;
        repaint();
    }

    /**
     * Getter for property m_iHeimtore.
     *
     * @return Value of property m_iHeimtore.
     */
    public final int getHeimtorAnzahl() {
        return m_iHeimtore;
    }

    /**
     * Setter for property m_vHeimtore.
     *
     * @param m_vHeimtore New value of property m_vHeimtore.
     */
    public final void setHeimtore(java.util.Vector m_vHeimtore) {
        this.m_vHeimtore = m_vHeimtore;
        repaint();
    }

    /**
     * Getter for property m_vHeimtore.
     *
     * @return Value of property m_vHeimtore.
     */
    public final java.util.Vector getHeimtore() {
        return m_vHeimtore;
    }

    /**
     * Setter for property m_iSpielminute.
     *
     * @param m_iSpielminute New value of property m_iGasttore.
     */
    public final void setSpielminute(int m_iSpielminute) {
        this.m_iSpielminute = m_iSpielminute;
        repaint();
    }

    /**
     * Getter for property m_iSpielminute.
     *
     * @return Value of property m_iSpielminute.
     */
    public final int getSpielminute() {
        return m_iSpielminute;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param gasttor TODO Missing Method Parameter Documentation
     */
    public final void addGasttor(String gasttor) {
        m_vGasttore.add(gasttor);
        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param heimtor TODO Missing Method Parameter Documentation
     */
    public final void addHeimtor(String heimtor) {
        m_vHeimtore.add(heimtor);
        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void paint(java.awt.Graphics g) {
        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        paintComponent(g2d);

        final int mitteHor = getWidth() / 2;
        final int mitteVert = getHeight() / 2;

        int posX = 0;
        int posY = 0;

        //Aussenrahmen
        g2d.setColor(Color.darkGray);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //Mittelrahmen
        g2d.setColor(Color.gray);
        g2d.fillRect(5, 5, getWidth() - 10, getHeight() - 10);

        //Innenrahmen
        g2d.setColor(Color.darkGray);
        g2d.fillRect(10, 10, getWidth() - 20, getHeight() - 20);

        //Hintergrund
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(15, 15, getWidth() - 30, getHeight() - 30);

        //Mannschaftnamen
        g2d.setFont(m_clNormalFont);
        g2d.setColor(STANDARDCOLOR);
        g2d.drawString("-", mitteHor - 3, 30);

        //Heimmannschaft
        posX = mitteHor - HORIZONTALOFFSET
               - (int) m_clNormalFont.getStringBounds(m_sHeimmannschaft,
                                                      new java.awt.font.FontRenderContext(null,
                                                                                          true,
                                                                                          false))
                                     .getWidth();
        g2d.setColor(HEIMCOLOR);
        g2d.drawString(m_sHeimmannschaft, posX, 30);

        //Gastmannschaft
        posX = mitteHor + HORIZONTALOFFSET;
        g2d.setColor(GASTCOLOR);
        g2d.drawString(m_sGastmannschaft, posX, 30);

        //TORE
        //Antialiasing an
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        final Font spielstandFont = new Font("SansSerife", Font.BOLD, getHeight() / 2);
        g2d.setFont(spielstandFont);
        g2d.setColor(STANDARDCOLOR);
        posY = mitteVert + (getHeight() / 5);
        g2d.drawString(":", mitteHor - 10, posY);

        //Heim
        posX = mitteHor - HORIZONTALOFFSET
               - (int) spielstandFont.getStringBounds(m_iHeimtore + "",
                                                      new java.awt.font.FontRenderContext(null,
                                                                                          true,
                                                                                          false))
                                     .getWidth();
        g2d.drawString(m_iHeimtore + "", posX, posY);

        //Gast
        posX = mitteHor + HORIZONTALOFFSET + 20;
        g2d.drawString(m_iGasttore + "", posX, posY);

        //Antialiasing aus
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);

        //Spielminute
        posX = mitteHor - 50;
        posY = getHeight() - 20;
        g2d.setFont(m_clNormalFont);
        g2d.drawString(m_iSpielminute + SPIELSTAND, posX, posY);

        //Torliste 
        g2d.setFont(m_clKleineFont);

        //Heim
        posX = 20;
        posY = 20;

        for (int i = 0; i < m_vHeimtore.size(); i++) {
            posY += 15;
            g2d.drawString(m_vHeimtore.get(i).toString(), posX, posY);
        }

        //Gast
        posX = getWidth() - 220;
        posY = 20;

        for (int i = 0; i < m_vGasttore.size(); i++) {
            posY += 15;
            g2d.drawString(m_vGasttore.get(i).toString(), posX, posY);
        }

        paintChildren(g2d);
        paintBorder(g2d);
    }
}
