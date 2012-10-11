package ho.core.db.backup;

import ho.core.db.User;
import ho.core.file.ExampleFileFilter;
import ho.core.file.ZipHelper;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.util.HOLogger;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Backupmanagement dialog
 * 
 * @author Thorsten Dietz
 * 
 */
public final class BackupDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8021487086770633938L;
	private JButton okButton = new JButton("restore");
	private JButton cancelButton = new JButton("cancel");
	private JList list;

	public BackupDialog() {
		super();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initialize();
	}

	private void initialize() {
		setTitle("Restore database");

		int dialogWidth = 320;
		int dialogHeight = 320;

		int with = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
				.getWidth();
		int height = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().getHeight();
		setLocation((with - dialogWidth) / 2, (height - dialogHeight) / 2);
		setSize(dialogWidth, dialogHeight);

		Container contenPane = getContentPane();
		contenPane.add(getTopPanel(), BorderLayout.NORTH);
		contenPane.add(getList(), BorderLayout.CENTER);
		contenPane.add(createButtons(), BorderLayout.SOUTH);
	}

	private JPanel getTopPanel() {
		JPanel panel = new ImagePanel();
		panel.add(new JLabel("Select a database .zip file!"));
		return panel;
	}

	private JPanel createButtons() {
		JPanel buttonPanel = new ImagePanel();
		((FlowLayout) buttonPanel.getLayout()).setAlignment(FlowLayout.RIGHT);

		okButton.addActionListener(this);

		cancelButton.addActionListener(this);

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		return buttonPanel;
	}

	private JScrollPane getList() {

		File dbDirectory = new File(User.getCurrentUser().getDBPath());
		ExampleFileFilter filter = new ExampleFileFilter("zip");
		filter.setIgnoreDirectories(true);
		File[] files = dbDirectory.listFiles(filter);
		list = new JList(files);

		JScrollPane scroll = new JScrollPane(list);
		return scroll;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			try {
				ZipHelper.unzip((File) list.getSelectedValue(), new File(User.getCurrentUser()
						.getDBPath()));
			} catch (Exception e1) {
				HOLogger.instance().log(getClass(), e1);
			}
		}

		setVisible(false);
		dispose();
	}

}
