package hoplugins.feedback.ui;

import hoplugins.feedback.dao.FeedbackSettingDAO;
import hoplugins.feedback.dao.FeedbackStatusDAO;
import hoplugins.feedback.ui.component.StartingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import plugins.IHOMiniModel;

/**
 * A simple menu for the feedback plugin.
 * 
 * @author aik
 */
public class FeedbackMenu {

	private static final long serialVersionUID = -638522802595715682L;

	/**
	 * Create a new Feedback menu.
	 */
	public static JMenu createMenu(final IHOMiniModel hoMiniModel) {
		JMenu menu = new JMenu("Feedback");
		JMenuItem about = new JMenuItem("About");
		JMenuItem reset = new JMenuItem("Reset");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(hoMiniModel.getGUI().getOwner4Dialog(),
						new StartingPanel(), "Info", JOptionPane.PLAIN_MESSAGE);
			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				hoMiniModel.getAdapter().executeUpdate("DROP TABLE IF EXISTS FEEDBACK_SETTINGS");
				hoMiniModel.getAdapter().executeUpdate("DROP TABLE IF EXISTS FEEDBACK_UPLOAD");
				FeedbackSettingDAO.checkTable();
				FeedbackStatusDAO.checkTable();
				FeedbackSettingDAO.setStarted();
			}
		});
		menu.add(about);
		menu.add(reset);
		return menu;
	}

}
