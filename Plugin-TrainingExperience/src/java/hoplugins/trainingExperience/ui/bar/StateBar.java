// %1126721451073:hoplugins.trainingExperience.ui.bar%
package hoplugins.trainingExperience.ui.bar;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;


/**
 * Statusbalken für Trefferpunkte, Ausdauer und Erfahrung The bar is composed of 3 different bars
 * one over the other, each one of 3 different colors
 *
 * @author Volker Fischer
 * @version 0.1a 12.02.01
 */
public class StateBar extends JComponent {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorModus bkgcolor = new ColorModus(Color.DARK_GRAY);
    private ColorModus color1;
    private ColorModus color2;
    private int breite = 100;
    private int hoehe = 24;
    private int level1;
    private int level2;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StateBar object, with the right levels
     *
     * @param lvl1
     * @param lvl2
     * @param c1
     * @param c2
     */
    public StateBar(int lvl1, int lvl2, Color c1, Color c2) {
        color1 = new ColorModus(c1);
        color2 = new ColorModus(c2);
        change(lvl1, lvl2);
        setPreferredSize(new Dimension(breite, hoehe));
        setOpaque(false);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Update the bar, with the new levels
     *
     * @param lvl1
     * @param lvl2
     */
    public void change(int lvl1, int lvl2) {
        level1 = lvl1;
        level2 = lvl2;

        String desc = ""; //$NON-NLS-1$

        if (lvl1 > 0) {
            desc = desc + Commons.getModel().getLanguageString("Aktuell") + lvl1 + "% "; //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (lvl2 > 0) {
            desc = desc + PluginProperty.getString("Final") + lvl2 + "% "; //$NON-NLS-1$ //$NON-NLS-2$
        }

        setToolTipText(desc);

        paintImmediately(getBounds());
    }

    /**
     * paint method
     *
     * @param g
     */
    public void paint(Graphics g) {
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        //Antialiasing einschalten
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - 10;
        int height = getHeight();

        //Set Background
        g2d.setColor(bkgcolor.dunkel);
        g2d.fillRect(5, (int) (height / 3), width, (int) (height / 3));
        g2d.setColor(bkgcolor.mittel);
        g2d.fillRect(6, (int) (height / 2.6f), width - 2, (int) (height / 5));
        g2d.setColor(bkgcolor.hell);
        g2d.fillRect(7, (int) (height / 2.3f), width - 3, (int) (height / 8));

        //Foreground			
        setLevelBar(g2d, level2, color2);
        setLevelBar(g2d, level1, color1);
    }

    /**
     * Paint each single bar that compose the main object
     *
     * @param g2d graphic
     * @param level length of the bar
     * @param cm Color of the bar
     */
    private void setLevelBar(java.awt.Graphics2D g2d, int level, ColorModus cm) {
        int height = getHeight();
        int width = getWidth() - 10;
        int laenge = (int) ((float) width * ((float) level / 100f));

        g2d.setColor(cm.dunkel);
        g2d.fillRect(5, (int) (height / 3), laenge, (int) (height / 3));
        g2d.setColor(cm.mittel);
        g2d.fillRect(6, (int) (height / 2.6f), laenge - 2, (int) (height / 5));
        g2d.setColor(cm.hell);
        g2d.fillRect(7, (int) (height / 2.3f), laenge - 3, (int) (height / 8));
    }
}
