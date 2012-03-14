package ho.module.lineup2;

import ho.core.gui.model.SpielerCBItem;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.module.lineup.Lineup;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JToggleButton;


public class SwapPositionFeature {

	private final JToggleButton swapButton = new JToggleButton();
	private final SwapPositionsManager swapPositionsManager;
	private final int positionsID;

	public SwapPositionFeature(PlayerPositionPanel spielerPositionsPanel,
			SwapPositionsManager swapPositionsManager) {
		this.positionsID = spielerPositionsPanel.getPositionsID();
		this.swapPositionsManager = swapPositionsManager;
		initSwapButton(spielerPositionsPanel);
	}

	public JToggleButton getSwapButton() {
		return swapButton;
	}

	public void unpressButton() {
		swapButton.setSelected(false);
	}

	public int getPositionsID() {
		return positionsID;
	}

	private void initSwapButton(PlayerPositionPanel spielerPositionsPanel) {
		customizeSwapButton();
		addButtonToPanel(spielerPositionsPanel);
	}

	private void customizeSwapButton() {
		swapButton.setToolTipText(HOVerwaltung.instance().getLanguageString("Lineup.Swap.ToolTip"));
		swapButton.setIcon(ThemeManager.getIcon(HOIconName.SWAP));
		swapButton.setSelectedIcon(ThemeManager.getIcon(HOIconName.SWAPPRESSED));
		swapButton.setPreferredSize(new Dimension(18, 18));
		swapButton.setMaximumSize(new Dimension(18, 18));
		swapButton.setMinimumSize(new Dimension(18, 18));
		swapButton.addActionListener(new SwapPositionFeatureItemListener(this.swapPositionsManager));
	}

	private void addButtonToPanel(PlayerPositionPanel spielerPositionsPanel) {
		GridBagLayout layout = (GridBagLayout) spielerPositionsPanel.getSwapLayout();
		layout.setConstraints(getSwapButton(), createSwapButtonConstraints());
		spielerPositionsPanel.addSwapItem(getSwapButton());
		spielerPositionsPanel.getPlayerComboBox().addActionListener(new SpielerPositionSwapActionListener());
	}

	private GridBagConstraints createSwapButtonConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new java.awt.Insets(0, 0, 0, 4);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		return constraints;
	}

	private class SpielerPositionSwapActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (swapButton.isSelected()) {
				JComboBox source = (JComboBox) event.getSource();
				SpielerCBItem selectedItem = (SpielerCBItem) source.getSelectedItem();
				if ((selectedItem != null) && (selectedItem.getSpieler() == null)) {
					unpressButton();
					swapPositionsManager.unmarkSwapCandidate();
				}
			}
		}
	}

	private class SwapPositionFeatureItemListener implements ActionListener {

		private final SwapPositionsManager swapPositionsManager;

		private SwapPositionFeatureItemListener(SwapPositionsManager swapPositionsManager) {
			this.swapPositionsManager = swapPositionsManager;
		}

		public void actionPerformed(ActionEvent e) {
			if (swapButton.isSelected()) {
				handlePressedButton();
			} else {
				swapPositionsManager.unmarkSwapCandidate();
			}
		}

		private void handlePressedButton() {
			actOnLegalPressedButton();
		}

		private void actOnLegalPressedButton() {
			if (swapPositionsManager.hasSwapCandidate()) {
				swapPositionsManager.swapWithCandidate(SwapPositionFeature.this);
			} else {
				swapPositionsManager.markAsSwapCandidate(SwapPositionFeature.this);
			}
		}

		private boolean playerIsSelectedOnThisPosition() {
			Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
			if (lineup.getPlayerByPositionID(getPositionsID()) != null) {
				return true;
			}
			return false;
		}
	}
}
