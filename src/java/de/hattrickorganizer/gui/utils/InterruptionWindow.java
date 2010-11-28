// %1876740819:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.TransparentFilter;

/**
 * Shows an info frame centered in the middle of the screen.
 *
 * @author Volker Fischer
 * @version 0.2a 28.08.01
 */
public class InterruptionWindow extends JFrame {
	private static final long serialVersionUID = -4948885175460734368L;
	private Image background;
	private String m_sInfotext = "";
	private String m_sVersionText = HOMainFrame.getVersionString();

    /**
     * Creates a new InterruptionWindow object.
     */
	public InterruptionWindow(Component component) {
		try {
			try {
				setTitle("HO! " + m_sVersionText);
				setIconImage(ImageIO.read(InterruptionWindow.class.getResourceAsStream("/gui/bilder/Logo-64px.png")));
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), "Error setting icon: " + e);
			}

			final URL resource = component.getClass().getClassLoader().getResource("gui/bilder/intro.jpg");
			background = ImageIO.read(resource);

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

			// background.getWidth ( null ), background.getHeight ( null ) );
			setSize(background.getWidth(null), background.getHeight(null));
			setLocation((getToolkit().getScreenSize().width / 2) - (getSize().width / 2), //
					(getToolkit().getScreenSize().height / 2) - (getSize().height / 2));

			setUndecorated(true);
			setVisible(true);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "InterruptionWindow.<init> : " + e);
			HOLogger.instance().log(getClass(), e);
		}
    }

    /**
     * Set text info (e.g. progress).
     */
    public final void setInfoText(String text) {
        m_sInfotext = text;
        repaint();
    }

    /**
     * Manually implemented paint() method.
     */
    @Override
	public final void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        //enable antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //draw background image
        g2d.drawImage(background, 0, 0, null);
		// border
        g2d.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g2d.setColor(Color.darkGray);
        g2d.drawRect(1, 1, getSize().width - 3, getSize().height - 3);
        g2d.setColor(Color.gray);
        g2d.drawRect(2, 2, getSize().width - 5, getSize().height - 5);
        g2d.setColor(Color.lightGray);
        g2d.drawRect(3, 3, getSize().width - 7, getSize().height - 7);
        //infotext / progress
        g2d.setColor(Color.black);
        g2d.setFont(new javax.swing.plaf.FontUIResource("SansSerif", Font.PLAIN, 10));
        g2d.drawString(m_sInfotext, 145, 25);
        g2d.drawString(m_sVersionText, 10, 147);
    }

    /**
     * Changes one color in the given image to transparent.
     */
	private Image makeColorTransparent(Image im, Color color) {
		final ImageProducer ip = new FilteredImageSource(im.getSource(), new TransparentFilter(color));
		return Toolkit.getDefaultToolkit().createImage(ip);
	}
}
