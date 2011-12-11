// %2560498359:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import gui.HOIconName;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;

/**
 * JPanel mit HintergrundGrafik für Fenster
 *
 * @author Volker Fischer
 * @version 0.2.1a 28.02.02
 */
public class RasenPanel extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -8146276344087586861L;

//	/** TODO Missing Parameter Documentation */
    public static BufferedImage background;

    //~ Instance fields ----------------------------------------------------------------------------

    private boolean m_bPrint;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RasenPanel object.
     */
    public RasenPanel() {
        super();
        init(false);
    }

    /**
     * Creates a new RasenPanel object.
     *
     * @param layout TODO Missing Constructuor Parameter Documentation
     */
    public RasenPanel(LayoutManager layout) {
        super(layout);
        init(false);
    }

    /**
     * Creates a new RasenPanel object.
     *
     * @param forprint TODO Missing Constructuor Parameter Documentation
     */
    public RasenPanel(boolean forprint) {
        super();
        init(forprint);
    }

    /**
     * Creates a new RasenPanel object.
     *
     * @param layout TODO Missing Constructuor Parameter Documentation
     * @param forprint TODO Missing Constructuor Parameter Documentation
     */
    public RasenPanel(LayoutManager layout, boolean forprint) {
        super(layout);
        init(forprint);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    @Override
	public final void paint(Graphics g) {
        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        paintComponent(g2d);

        if (!m_bPrint) {
            Rectangle2D tr = new Rectangle2D.Double(0, 0, background.getWidth(), background.getHeight());
            TexturePaint tp = new TexturePaint(background, tr);
            g2d.setPaint(tp);
            g2d.fill(g2d.getClip());
        }

        paintChildren(g2d);
        paintBorder(g2d);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param printing TODO Missing Method Parameter Documentation
     */
    private void init(boolean printing) {
        m_bPrint = printing;

        if (background == null) {
            background = ImageUtilities.toBufferedImage(ThemeManager.getIcon(HOIconName.GRASSPANEL_BACKGROUND).getImage());
        }

        setBackground(java.awt.Color.white);
    }
}
