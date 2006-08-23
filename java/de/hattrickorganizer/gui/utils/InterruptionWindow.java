// %1876740819:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import de.hattrickorganizer.tools.HOLogger;


/**
 * Zentriertes Bild in der Bildschirmmitte
 *
 * @author Volker Fischer
 * @version 0.2a 28.08.01
 */
public class InterruptionWindow extends javax.swing.JWindow {
    //~ Instance fields ----------------------------------------------------------------------------

    private Image background;
    private String m_sInfotext = "";

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new InterruptionWindow object.
     *
     * @param component TODO Missing Constructuor Parameter Documentation
     */
    public InterruptionWindow(Component component) {
        try {
            final java.net.URL resource = component.getClass().getClassLoader().getResource("gui/bilder/intro.jpg");
            background = javax.imageio.ImageIO.read(resource);

            final MediaTracker tracker = new MediaTracker(component);
            tracker.addImage(background, 1);

            try {
                tracker.waitForAll();
            } catch (InterruptedException ie) {
            }

            background = makeColorTransparent(background, new Color(255, 0, 0));
            tracker.addImage(background, 1);

            try {
                tracker.waitForAll();
            } catch (InterruptedException ie) {
            }

            //background.getWidth ( null ), background.getHeight ( null ) );
            setSize(480, 160);
            setLocation((getToolkit().getScreenSize().width / 2) - (getSize().width / 2),
                        (getToolkit().getScreenSize().height / 2) - (getSize().height / 2));

            //repaint();
            setVisible(true);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"InterruptionWindow.<init> : " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setInfoText(String text) {
        m_sInfotext = text;
        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void paint(Graphics g) {
        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        //Antialiasing einschalten
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        //Hintergrundgrafik zeichnen
        g2d.drawImage(background, (getSize().width / 2) - (background.getWidth(null) / 2), 15, null);

        //Schriftzug
        //                g2d.setColor ( Color.black );
        //                g2d.setFont ( new javax.swing.plaf.FontUIResource("SansSerif",Font.BOLD,25) );
        //                g2d.drawString ( "Hattrick Organizer", 15, 125 );
        //Rahmen
        g2d.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g2d.setColor(Color.darkGray);
        g2d.drawRect(1, 1, getSize().width - 3, getSize().height - 3);
        g2d.setColor(Color.gray);
        g2d.drawRect(2, 2, getSize().width - 5, getSize().height - 5);
        g2d.setColor(Color.lightGray);
        g2d.drawRect(3, 3, getSize().width - 7, getSize().height - 7);

        //Infotext
        g2d.setColor(Color.black);
        g2d.setFont(new javax.swing.plaf.FontUIResource("SansSerif", Font.PLAIN, 10));
        g2d.drawString(m_sInfotext, 145, 25);
    }

    /**
     * Tauscht eine Farbe im Image durch eine andere
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param original TODO Missing Constructuor Parameter Documentation
     * @param change TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private java.awt.Image changeColor(java.awt.Image im, java.awt.Color original,
                                       java.awt.Color change) {
        final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im.getSource(),
                                                                                       new de.hattrickorganizer.tools.ColorChangeFilter(original,
                                                                                                                                        change));
        return java.awt.Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Macht eine Farbe in dem Bild transparent
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param color TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private java.awt.Image makeColorTransparent(java.awt.Image im, java.awt.Color color) {
        final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im.getSource(),
                                                                                       new de.hattrickorganizer.tools.TransparentFilter(color));
        return java.awt.Toolkit.getDefaultToolkit().createImage(ip);
    }
}
