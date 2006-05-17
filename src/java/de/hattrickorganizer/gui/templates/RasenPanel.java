// %2560498359:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

/**
 * JPanel mit HintergrundGrafik für Fenster
 *
 * @author Volker Fischer
 * @version 0.2.1a 28.02.02
 */
public class RasenPanel extends javax.swing.JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static java.awt.Image background;

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
    public RasenPanel(java.awt.LayoutManager layout) {
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
    public RasenPanel(java.awt.LayoutManager layout, boolean forprint) {
        super(layout);
        init(forprint);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void paint(java.awt.Graphics g) {
        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        paintComponent(g2d);

        if (!m_bPrint) {
            //Hintergrundgrafik zeichnen -> 4 Grafiken!
            g2d.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null),
                          null);
            g2d.drawImage(background, background.getWidth(null), 0, background.getWidth(null),
                          background.getHeight(null), null);
            g2d.drawImage(background, 0, background.getHeight(null), background.getWidth(null),
                          background.getHeight(null), null);
            g2d.drawImage(background, background.getWidth(null), background.getHeight(null),
                          background.getWidth(null), background.getHeight(null), null);

            //g2d.drawImage(background,null,this);
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
            final java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);

            final java.net.URL resource = getClass().getClassLoader().getResource("gui/bilder/Rasen_mit_Streifen.jpg");
            background = getToolkit().createImage(resource);

            tracker.addImage(background, 1);

            //Der MediaTracker wartet, bis alle Grafiken als Image-Objekte verfügbar sind.
            try {
                tracker.waitForAll();
            } catch (InterruptedException ie) {
            }
        }

        setBackground(java.awt.Color.white);
    }
}
