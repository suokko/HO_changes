package ho.module.lineup2;

import ho.core.util.GUIUtilities;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class FormationExperienceDialog extends JDialog {

	private static final long serialVersionUID = 259563833175074589L;

	public FormationExperienceDialog() {
		initComponents();
		pack();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(12, 6, 6, 6);
		gbc.fill = GridBagConstraints.NONE;
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(closeButton, gbc);

		add(new FormationExperienceView(), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		GUIUtilities.decorateWithDisposeOnESC(this);
	}

}
