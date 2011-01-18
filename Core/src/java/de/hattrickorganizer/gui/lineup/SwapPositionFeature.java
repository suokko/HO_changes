package de.hattrickorganizer.gui.lineup;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;

import de.hattrickorganizer.gui.model.SpielerCBItem;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;

public class SwapPositionFeature {

	private final class SpielerPositionSwapActionListener implements
			ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (swapButton.isSelected()) {
				JComboBox source = (JComboBox) event.getSource();
				SpielerCBItem selectedItem = (SpielerCBItem) source.getSelectedItem();
				if ((selectedItem != null)
						&& (selectedItem.getSpieler() == null)) {
					unpressButton();
					swapPositionsManager.unmarkSwapCandidate();
				}
			}
		}
	}

	private final class SwapPositionFeatureItemListener implements
			ActionListener {

		private final SwapPositionsManager swapPositionsManager;

		private SwapPositionFeatureItemListener(
				SwapPositionsManager swapPositionsManager) {
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
			// Check removed to allow the swap into empty positions.
//			if (playerIsSelectedOnThisPosition()) {
				actOnLegalPressedButton();
//			} else {
//				unpressButton();
//			}
		}

		private void actOnLegalPressedButton() {
			if (swapPositionsManager.hasSwapCandidate()) {
				swapPositionsManager
						.swapWithCandidate(SwapPositionFeature.this);
			} else {
				swapPositionsManager
						.markAsSwapCandidate(SwapPositionFeature.this);
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

	private final JToggleButton swapButton = new JToggleButton();
	private final SwapPositionsManager swapPositionsManager;
	private final int positionsID;

	public SwapPositionFeature(PlayerPositionPanel spielerPositionsPanel,
			final SwapPositionsManager swapPositionsManager) {
		this.positionsID = spielerPositionsPanel.getPositionsID();
		this.swapPositionsManager = swapPositionsManager;
		initSwapButton(spielerPositionsPanel);
	}

	private void initSwapButton(PlayerPositionPanel spielerPositionsPanel) {
		customizeSwapButton();
		addButtonToPanel(spielerPositionsPanel);
	}

	private void customizeSwapButton() {
		swapButton.setToolTipText(HOVerwaltung.instance().getLanguageString("Lineup.Swap.ToolTip"));
		swapButton.setIcon(new ImageIcon(Helper.loadImage("gui/bilder/swap.png")));
		swapButton.setSelectedIcon(new ImageIcon(Helper.loadImage("gui/bilder/swap-pressed.png")));
		swapButton.setPreferredSize(new Dimension(18, 18));
		swapButton.setMaximumSize(new Dimension(18, 18));
		swapButton.setMinimumSize(new Dimension(18, 18));

		swapButton.addActionListener(new SwapPositionFeatureItemListener(
				this.swapPositionsManager));
	}

	private void addButtonToPanel(PlayerPositionPanel spielerPositionsPanel) {
		GridBagLayout layout = (GridBagLayout) spielerPositionsPanel
				.getLayout();
		layout.setConstraints(getSwapButton(), createSwapButtonConstraints());
		spielerPositionsPanel.add(getSwapButton());
		spielerPositionsPanel.getPlayerComboBox().addActionListener(
				new SpielerPositionSwapActionListener());
	}

	private GridBagConstraints createSwapButtonConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		return constraints;
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

}
