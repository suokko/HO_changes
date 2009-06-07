// %1876740819:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.templates.BackgroundImageContentPane;
import de.hattrickorganizer.tools.HOLogger;

/**
 * Creates a centered splash screen
 * Using BackgroundImageContentPane(String resName)
 *
 * @author flattermann <HO@flattermann.net>
 */
public class InterruptionWindow extends JFrame {

    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = -3080485740270436531L;

	//~ Instance fields ----------------------------------------------------------------------------

	private JLabel infoText;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new splash screen frame
     */
    public InterruptionWindow() {
    	super ("HO! is starting...");
        try {
            setUndecorated(true);
            
            infoText = new JLabel("", JLabel.CENTER);

            BackgroundImageContentPane contentPane = new BackgroundImageContentPane("gui/bilder/intro-new.png", true);
            setContentPane(contentPane);
            getContentPane().setLayout(null);

            infoText.setBounds(11, 20, contentPane.getWidth()-2*11, 30);
            infoText.setBackground(new Color(254, 249, 227));
            infoText.setOpaque(true);

            getContentPane().add(infoText);
            
            JLabel versionLabel = new JLabel("HO! " + HOMainFrame.getVersionString());

            versionLabel.setForeground(Color.WHITE);
            versionLabel.setBounds(contentPane.getWidth()-versionLabel.getPreferredSize().width-5, 
            		contentPane.getHeight()-versionLabel.getPreferredSize().height-5, 
            		versionLabel.getPreferredSize().width, 
            		versionLabel.getPreferredSize().height);
            getContentPane().add(versionLabel);

            setSize(contentPane.getWidth(), contentPane.getHeight());
            setLocation((getToolkit().getScreenSize().width / 2) - (getSize().width / 2),
                        (getToolkit().getScreenSize().height / 2) - (getSize().height / 2));

            setVisible(true);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"InterruptionWindow.<init> : " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the info text of the splash screen
     *
     * @param text info text to be shown
     */
    public final void setInfoText(String text) {
    	infoText.setText(text);
    }
}
