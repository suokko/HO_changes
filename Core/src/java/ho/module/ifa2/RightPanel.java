package ho.module.ifa2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = -5038012557489983903L;
	private JButton updateButton;
	private JRadioButton homeRadioButton;
	private JRadioButton awayRadioButton;
	private ImageDesignPanel imageDesignPanel;

	public RightPanel() {
		initComponents();
		addListeners();
	}

	public ImageDesignPanel getImageDesignPanel() {
		return this.imageDesignPanel;
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		this.updateButton = new JButton("Update");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		add(this.updateButton, gbc);

		this.awayRadioButton = new JRadioButton("Visited Countries", true);
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(this.awayRadioButton, gbc);

		this.homeRadioButton = new JRadioButton("Hosted Countries", false);
		gbc.gridx = 1;
		add(this.homeRadioButton, gbc);

		ButtonGroup group = new ButtonGroup();
		group.add(this.homeRadioButton);
		group.add(this.awayRadioButton);

		this.imageDesignPanel = new ImageDesignPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		add(this.imageDesignPanel, gbc);
	}

	private void addListeners() {
		this.awayRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					imageDesignPanel.setAway(true);
				}
			}
		});
		this.homeRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					imageDesignPanel.setAway(false);
				}
			}
		});
	}
}
