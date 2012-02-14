package ho.module.nthrf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.hattrickorganizer.gui.HOMainFrame;

/**
 * A simple menu for the nthrf plugin.
 *
 * @author aik
 */
public class NthrfMenu {

	/**
	 * Create a new Feedback menu.
	 */
	public static JMenu createMenu() {
		JMenu menu = new JMenu("Nthrf");
    	JMenuItem about = new JMenuItem("Download HRF");
        about.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		JOptionPane.showMessageDialog(HOMainFrame.instance(), MainPanel.getInstance(),
        				"Download", JOptionPane.PLAIN_MESSAGE);
        	}
        });
        menu.add(about);
        return menu;
	}

}
