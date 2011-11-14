package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import plugins.ISubstitution;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.GUIUtilities;

public class BehaviourDialog extends JDialog {

	private static final long serialVersionUID = 1875761460780943159L;
	private BehaviourView behaviourView;
	private boolean canceled = true;

	public BehaviourDialog(Dialog parent) {
		super(parent, true);
		initComponents();
		pack();
	}
	
	public BehaviourDialog(Frame parent) {
		super(parent, true);
		initComponents();
		pack();
	}

	public void init(ISubstitution sub) {
		this.behaviourView.init(sub);
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public ISubstitution getSubstitution() {
		return this.behaviourView.getSubstitution();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JButton okButton = new JButton(HOVerwaltung.instance().getLanguageString("subs.Ok"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(12, 8, 8, 2);
		buttonPanel.add(okButton, gbc);

		JButton cancelButton = new JButton(HOVerwaltung.instance().getLanguageString("subs.Cancel"));
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(12, 2, 8, 8);
		buttonPanel.add(cancelButton, gbc);

		this.behaviourView = new BehaviourView();
		getContentPane().add(this.behaviourView, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				canceled = false;
				dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				canceled = true;
				dispose();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				canceled = true;
			}
		});

		GUIUtilities.equalizeComponentSizes(okButton, cancelButton);
	}
}
