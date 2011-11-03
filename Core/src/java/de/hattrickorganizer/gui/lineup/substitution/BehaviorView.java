package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.hattrickorganizer.tools.StringUtilities;

public class BehaviorView extends JPanel {

	private static final long serialVersionUID = 6041242290064429972L;

	public BehaviorView() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		JLabel behaviorLabel = new JLabel("New behavior:");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 4, 2);
		add(behaviorLabel, gbc);

		JComboBox behaviorComboBox = new JComboBox();
		Dimension comboBoxSize = new Dimension(200,
				behaviorComboBox.getPreferredSize().height);
		behaviorComboBox.setMinimumSize(comboBoxSize);
		behaviorComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 2, 4, 10);
		add(behaviorComboBox, gbc);

		JLabel whenLabel = new JLabel("When:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(4, 10, 4, 2);
		add(whenLabel, gbc);

		final JTextField whenTextField = new JTextField();
		Dimension textFieldSize = new Dimension(100,
				whenTextField.getPreferredSize().height);
		whenTextField.setMinimumSize(textFieldSize);
		whenTextField.setPreferredSize(textFieldSize);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(whenTextField, gbc);

		final JSlider whenSlider = new JSlider(0, 119, 0);
		whenSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				whenTextField.setText(String.valueOf(whenSlider.getModel()
						.getValue()));
			}
		});
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 2, 8, 10);
		add(whenSlider, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(8, 4, 8, 4);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		add(new Divider("Advanced (optional)"), gbc);

		JLabel positionLabel = new JLabel("New position:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(4, 10, 4, 2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		add(positionLabel, gbc);

		JComboBox positionComboBox = new JComboBox();
		positionComboBox.setMinimumSize(comboBoxSize);
		positionComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(positionComboBox, gbc);
		
		JLabel redCardsLabel = new JLabel("Red cards:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(4, 10, 4, 2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		add(redCardsLabel, gbc);

		JComboBox redCardsComboBox = new JComboBox();
		redCardsComboBox.setMinimumSize(comboBoxSize);
		redCardsComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(redCardsComboBox, gbc);

		JLabel standingLabel = new JLabel("Standing:");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(4, 10, 4, 2);
		add(standingLabel, gbc);

		JComboBox stadingComboBox = new JComboBox();
		stadingComboBox.setMinimumSize(comboBoxSize);
		stadingComboBox.setPreferredSize(comboBoxSize);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(4, 2, 4, 10);
		add(stadingComboBox, gbc);

		whenTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				updateSlider();
			}

			public void insertUpdate(DocumentEvent e) {
				updateSlider();
			}

			public void changedUpdate(DocumentEvent e) {
				updateSlider();
			}

			private void updateSlider() {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						String str = whenTextField.getText();
						int value = 0;
						if (StringUtilities.isNumeric(str)) {
							int parsed = Integer.parseInt(str);
							if (parsed >= 0 && parsed <= 119) {
								value = parsed;
							}
						}
						whenSlider.setValue(value);
					}
				});
			}
		});
	}

}
