package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import de.hattrickorganizer.tools.GUIUtilities;

public class BehaviorDialog extends JDialog {

	private static final long serialVersionUID = 1875761460780943159L;

	public BehaviorDialog(Window parent) {
		super(parent);
		setModal(true);
		initComponents();
		pack();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JButton okButton = new JButton("Ok");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(12, 8, 8, 2);
		buttonPanel.add(okButton, gbc);

		JButton cancelButton = new JButton("Cancel");
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(12, 2, 8, 8);
		buttonPanel.add(cancelButton, gbc);

		getContentPane().add(new BehaviorView(), BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		ActionListener listener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		okButton.addActionListener(listener);
		cancelButton.addActionListener(listener);

		GUIUtilities.equalizeComponentSizes(okButton, cancelButton);
	}
}
