// %556564099:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

/**
 * JPanel mit HintergrundGrafik für Fenster
 *
 * @author Volker Fischer
 * @version 0.2.1a 28.02.02
 */
public class ImagePanel extends javax.swing.JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 8960221838823071903L;

	/** TODO Missing Parameter Documentation */
    public static java.awt.Image background;

    //~ Instance fields ----------------------------------------------------------------------------

    private boolean m_bPrint;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ImagePanel object.
     */
    public ImagePanel() {
        super();
        init(false);
    }

    /**
     * Creates a new ImagePanel object.
     *
     * @param layout TODO Missing Constructuor Parameter Documentation
     */
    public ImagePanel(java.awt.LayoutManager layout) {
        super(layout);
        init(false);
    }

    /**
     * Creates a new ImagePanel object.
     *
     * @param forprint TODO Missing Constructuor Parameter Documentation
     */
    public ImagePanel(boolean forprint) {
        super();
        init(forprint);
    }

    /**
     * Creates a new ImagePanel object.
     *
     * @param layout TODO Missing Constructuor Parameter Documentation
     * @param forprint TODO Missing Constructuor Parameter Documentation
     */
    public ImagePanel(java.awt.LayoutManager layout, boolean forprint) {
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
	public final void paint(java.awt.Graphics g) {
        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

        paintComponent(g2d);

        if (!m_bPrint) {
            //Hintergrundgrafik zeichnen -> 6 Grafiken!
            g2d.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null),
                          null);
            g2d.drawImage(background, background.getWidth(null), 0, background.getWidth(null),
                          background.getHeight(null), null);
            g2d.drawImage(background, background.getWidth(null) * 2, 0, background.getWidth(null),
                          background.getHeight(null), null);
            g2d.drawImage(background, 0, background.getHeight(null), background.getWidth(null),
                          background.getHeight(null), null);
            g2d.drawImage(background, background.getWidth(null), background.getHeight(null),
                          background.getWidth(null), background.getHeight(null), null);
            g2d.drawImage(background, background.getWidth(null) * 2, background.getHeight(null),
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

            final java.net.URL resource = getClass().getClassLoader().getResource("gui/bilder/Background.jpg");
            background = getToolkit().createImage(resource);

            tracker.addImage(background, 1);

            //Der MediaTracker wartet, bis alle Grafiken als Image-Objekte verfügbar sind.
            try {
                tracker.waitForAll();
            } catch (InterruptedException ie) {
            }
        }
    }
}
