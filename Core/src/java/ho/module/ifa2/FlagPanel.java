package ho.module.ifa2;

import ho.core.db.DBManager;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class FlagPanel extends JPanel {

	private static final long serialVersionUID = 6841405207630506680L;
	private int countriesPlayedIn;
	private JLabel[] flagLabels;
	private JLabel header;
	private JLabel footer;
	private JProgressBar percentState;

	public FlagPanel(boolean away, FlagDisplayModel flagDisplayModel) {
		initialize(away, flagDisplayModel);
	}

	private void initialize(boolean away, FlagDisplayModel flagDisplayModel) {
		WorldDetailLeague[] leagues = WorldDetailsManager.instance().getLeagues();
		this.flagLabels = new FlagLabel[leagues.length];
		for (int i = 0; i < leagues.length; i++) {
			FlagLabel flagLabel = new FlagLabel(flagDisplayModel);
			flagLabel.setCountryId(leagues[i].getCountryId());
			flagLabel.setCountryName(leagues[i].getCountryName());
			flagLabel.setIcon(ImageUtilities.getFlagIcon(flagLabel.getCountryId()));
			flagLabel.setToolTipText(flagLabel.getCountryName());
			int flagLeagueID = leagues[i].getLeagueId();
			if (flagLeagueID == HOVerwaltung.instance().getModel().getBasics().getLiga()) {
				flagLabel.setHomeCountry(true);
			} else {
				if (DBManager.instance().isIFALeagueIDinDB(flagLeagueID, away)) {
					this.countriesPlayedIn++;
					flagLabel.setEnabled(true);
				} else {
					flagLabel.setEnabled(false);
				}
			}
			this.flagLabels[i] = flagLabel;
		}
		Arrays.sort(this.flagLabels, new UniversalComparator(1));

		setLayout(new GridBagLayout());
		setBackground(Color.white);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = 2;
		constraints.anchor = 10;
		constraints.insets = new Insets(1, 1, 1, 1);
		constraints.weightx = 100.0D;
		constraints.weighty = 0.0D;

		this.header = new JLabel("");
		this.header.setForeground(new Color(2522928));
		this.header.setHorizontalTextPosition(0);
		add(this.header, constraints, 0, 0, flagDisplayModel.getFlagWidth(), 1);
		constraints.insets = new Insets(1, 1, 5, 1);
		Color selectionForeground = (Color) UIManager.get("ProgressBar.selectionForeground");
		Color selectionBackground = (Color) UIManager.get("ProgressBar.selectionBackground");
		UIManager.put("ProgressBar.selectionForeground", Color.black);
		UIManager.put("ProgressBar.selectionBackground", Color.black);
		this.percentState = new JProgressBar();
		this.percentState.setMaximum(WorldDetailsManager.instance().size());
		this.percentState.setValue(this.countriesPlayedIn);
		this.percentState.setPreferredSize(new Dimension(100, 10));
		this.percentState.setFont(new Font("Verdana", 1, 10));
		this.percentState.setForeground(new Color(15979011));
		this.percentState.setBackground(Color.lightGray);
		this.percentState.setString(this.countriesPlayedIn + "/"
				+ WorldDetailsManager.instance().size() + " ("
				+ (int) (100.0D * this.percentState.getPercentComplete()) + "%)");
		this.percentState.setStringPainted(true);
		this.percentState.setBorder(BorderFactory.createLineBorder(Color.black));

		add(this.percentState, constraints, 0, 1, flagDisplayModel.getFlagWidth(), 1);
		UIManager.put("ProgressBar.selectionForeground", selectionForeground);
		UIManager.put("ProgressBar.selectionBackground", selectionBackground);
		constraints.fill = 0;
		constraints.anchor = 10;
		constraints.insets = new Insets(1, 1, 1, 1);
		constraints.weightx = 0.0D;
		constraints.weighty = 0.0D;
		if (this.flagLabels != null)
			for (int i = 0; i < this.flagLabels.length; i++) {
				add(this.flagLabels[i], constraints, i % flagDisplayModel.getFlagWidth(), 2 + i
						/ flagDisplayModel.getFlagWidth(), 1, 1);
			}
		constraints.fill = 2;
		constraints.anchor = 13;
		constraints.insets = new Insets(1, 1, 1, 1);
		constraints.weightx = 100.0D;
		constraints.weighty = 0.0D;

		this.footer = new JLabel(new ImageIcon(FlagPanel.class.getResource("image/copyright.gif")),
				4);
		add(this.footer, constraints, 0,
				WorldDetailsManager.instance().size() / flagDisplayModel.getFlagWidth() + 3,
				flagDisplayModel.getFlagWidth(), 1);
	}

	private void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}

	void setHeader(String header) {
		this.header.setText(header);
	}

	void setHeaderVisible(boolean enable) {
		this.header.setVisible(enable);
		this.percentState.setVisible(enable);
	}

	void setFooterVisible(boolean enable) {
		this.footer.setVisible(enable);
	}
}
