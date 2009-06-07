package hoplugins.nthrf.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import plugins.IHOMiniModel;

/**
 * A simple menu for the nthrf plugin.
 *
 * @author aik
 */
public class NthrfMenu {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new Feedback menu.
	 */
	public static JMenu createMenu(final IHOMiniModel hoMiniModel) {
		JMenu menu = new JMenu("Nthrf");
    	JMenuItem about = new JMenuItem("Download HRF");
        about.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		JOptionPane.showMessageDialog(hoMiniModel.getGUI().getOwner4Dialog(), MainPanel.getInstance(),
        				"Download", JOptionPane.PLAIN_MESSAGE);
        	}
        });
        menu.add(about);
        return menu;
	}

}
