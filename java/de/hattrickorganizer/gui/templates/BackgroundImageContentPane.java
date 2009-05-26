package de.hattrickorganizer.gui.templates;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.JPanel;

/**
 * JPanel with Intro graphic, used as ContentPane
 *
 * @author flattermann <HO@flattermann.net>
 */
public class BackgroundImageContentPane extends JPanel {

	//~ Static fields/initializers -----------------------------------------------------------------
	
	private static final long serialVersionUID = 3862463405870676047L;

	//~ Instance fields ----------------------------------------------------------------------------
	
    private Image background;
    private boolean useBorder = false;

    //~ Constructors -------------------------------------------------------------------------------

    public BackgroundImageContentPane(String resLocation) {
    	this (resLocation, false);
    }

    /**
     * Creates a new BackgroundImageContentPane with or without border
     * 
     * @param useBorder create a border if true
     */
    public BackgroundImageContentPane(String resLocation, boolean useBorder) {
        super();
        this.useBorder = useBorder;
        URL resource = getClass().getClassLoader().getResource(resLocation);
        init(resource);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Paints the component (i.e. the background image)
     *
     * @param g the graphic to paint
     */
    public void paintComponent(java.awt.Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (background!= null) {
			g.drawImage(background,0,0,this);
			g.setColor(Color.BLACK);
			if (useBorder) {
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		}
    }

    /**
     * Init the components (i.e. load the background image)
     */
    private void init(URL resource) {
        if (background == null) {
            final java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);

            background = getToolkit().createImage(resource);
            
            tracker.addImage(background, 1);

            // Wait for all images to be loaded
            try {
                tracker.waitForAll();
            } catch (InterruptedException ie) {
            	ie.printStackTrace();
            }
            setSize(background.getWidth(null), background.getHeight(null));
        }
    }
}
