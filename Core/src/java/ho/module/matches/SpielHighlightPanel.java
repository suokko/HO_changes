// %4112883594:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchHighlight;
import ho.core.model.match.MatchHighlight;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 * Zeigt die Stärken eines Matches an.
 */
public class SpielHighlightPanel extends ImagePanel {
	private static final long serialVersionUID = -6491501224900464573L;

    private GridBagConstraints constraints = new GridBagConstraints();

    private GridBagLayout layout = new GridBagLayout();
    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private JPanel panel = new JPanel(layout);

    private Vector<Component> m_vHighlightLabels = new Vector<Component>();

    /**
     * Creates a new SpielHighlightPanel object.
     */
    public SpielHighlightPanel() {
        this(false);
    }

    /**
     * Creates a new SpielHighlightPanel object.
     *
     * @param print if true: use printer version (no colored background)
     */
    public SpielHighlightPanel(boolean print) {
        super(print);

        setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        final GridBagLayout mainlayout = new GridBagLayout();
        final GridBagConstraints mainconstraints = new GridBagConstraints();
        mainconstraints.anchor = GridBagConstraints.NORTH;
        mainconstraints.fill = GridBagConstraints.HORIZONTAL;
        mainconstraints.weighty = 0.1;
        mainconstraints.weightx = 1.0;
        mainconstraints.insets = new Insets(4, 6, 4, 6);

        setLayout(mainlayout);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0.0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(5, 3, 2, 2);

        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor(HOColorName.PANEL_BORDER)));
        panel.setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        //Platzhalter
        JLabel label = new JLabel("   ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridheight = 30;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gast"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        m_clHeimTeamName.setPreferredSize(new Dimension(140, 14));
        m_clHeimTeamName.setFont(m_clHeimTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamName, constraints);
        panel.add(m_clHeimTeamName);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 3;
        constraints.gridy = 2;
        m_clHeimTeamTore.setFont(m_clHeimTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamTore, constraints);
        panel.add(m_clHeimTeamTore);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 5;
        constraints.gridy = 2;
        m_clGastTeamName.setPreferredSize(new Dimension(140, 14));
        m_clGastTeamName.setFont(m_clGastTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamName, constraints);
        panel.add(m_clGastTeamName);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 6;
        constraints.gridy = 2;
        m_clGastTeamTore.setFont(m_clGastTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamTore, constraints);
        panel.add(m_clGastTeamTore);

        //Platzhalter
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 7;
        constraints.gridy = 2;
        label = new JLabel("    ");
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 3;
        label = new JLabel(" ");
        layout.setConstraints(label, constraints);
        panel.add(label);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Clear all highlights.
     */
    public final void clear() {
        //Alle Highlights löschen
        for (int i = 0; i < m_vHighlightLabels.size(); i++) {
            panel.remove(m_vHighlightLabels.get(i));
        }

        m_clHeimTeamName.setText(" ");
        m_clGastTeamName.setText(" ");
        m_clHeimTeamTore.setText(" ");
        m_clGastTeamTore.setText(" ");
        m_clHeimTeamName.setIcon(null);
        m_clGastTeamName.setIcon(null);
    }

    /**
     * Refresh the highlights from the short info.
     */
    public final void refresh(MatchKurzInfo info,Matchdetails details) {
        clear();

        //Teams
        final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();

        m_clHeimTeamName.setText(info.getHeimName());
        m_clGastTeamName.setText(info.getGastName());

        m_clHeimTeamTore.setText(info.getHeimTore() + " ("+details.getHomeHalfTimeGoals()+") ");
        m_clGastTeamTore.setText(info.getGastTore() + " ("+details.getGuestHalfTimeGoals()+") ");

        if (info.getHeimID() == teamid) {
            m_clHeimTeamName.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
        } else {
            m_clHeimTeamName.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
        }

        if (info.getGastID() == teamid) {
            m_clGastTeamName.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
        } else {
            m_clGastTeamName.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
        }

        //Alle Highlights löschen
        for (int i = 0; i < m_vHighlightLabels.size(); i++) {
            panel.remove(m_vHighlightLabels.get(i));
        }

        if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
            //Highlights anzeigen
            JLabel playerlabel = null;
            JLabel resultlabel = null;

            final Vector<MatchHighlight> vMatchHighlights = details.getHighlights();

            for (int i = 0; i < vMatchHighlights.size(); i++) {
                final MatchHighlight highlight =  vMatchHighlights.get(i);

                //Label vorbereiten
                final ImageIcon icon = SpielHighlightPanel.getImageIcon4SpielHighlight(highlight.getHighlightTyp(),
                                                                    highlight.getHighlightSubTyp());

                //Soll Highlight auch angezeigt werden? (Nur wenn Grafik vorhanden ist)
                if (icon != null) {
                    //Spielername
                    String spielername = highlight.getSpielerName();

                    if (spielername.length() > 30) {
                        spielername = spielername.substring(0, 29);
                    }

                    spielername += (" (" + highlight.getMinute() + ".)");
                    playerlabel = new JLabel(spielername, icon, SwingConstants.LEFT);
                    playerlabel.setForeground(SpielHighlightPanel.getColor4SpielHighlight(highlight.getHighlightTyp(),
                                                                       highlight.getHighlightSubTyp()));
                    if (highlight.isWeatherSEHighlight()) {
                    	playerlabel.setToolTipText(removeHtml(highlight.getEventText()));
                    } else {
                    	playerlabel.setToolTipText(MatchHighlight.getTooltiptext(highlight.getHighlightTyp(),
                                                                              highlight.getHighlightSubTyp()));
                    }

                    //Steht Müll drin!
                    if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
                        resultlabel = new JLabel(highlight.getHeimTore() + " : "
                                                 + highlight.getGastTore());
                    } else {
                        resultlabel = new JLabel("");
                    }

                    //Labels in den Highlightvector hinzufügen
                    m_vHighlightLabels.add(playerlabel);
                    m_vHighlightLabels.add(resultlabel);

                    //Heimaktion
                    if (highlight.getTeamID() == info.getHeimID()) {
                        constraints.anchor = GridBagConstraints.WEST;
                        constraints.fill = GridBagConstraints.HORIZONTAL;
                        constraints.weightx = 1.0;
                        constraints.gridx = 2;
                        constraints.gridy = i + 4;
                        constraints.gridwidth = 2;
                        layout.setConstraints(playerlabel, constraints);
                        panel.add(playerlabel);
                    } else {
                        constraints.anchor = GridBagConstraints.WEST;
                        constraints.fill = GridBagConstraints.HORIZONTAL;
                        constraints.weightx = 1.0;
                        constraints.gridx = 5;
                        constraints.gridy = i + 4;
                        constraints.gridwidth = 2;
                        layout.setConstraints(playerlabel, constraints);
                        panel.add(playerlabel);
                    }

                    constraints.anchor = GridBagConstraints.EAST;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.weightx = 1.0;
                    constraints.gridx = 8;
                    constraints.gridy = i + 4;
                    constraints.gridwidth = 1;
                    layout.setConstraints(resultlabel, constraints);
                    panel.add(resultlabel);
                }
            }

            //--updaten--
            //Sterne für Sieger!
            if (info.getMatchStatus() != MatchKurzInfo.FINISHED) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() > info.getGastTore()) {
                m_clHeimTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() < info.getGastTore()) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
            } else {
                m_clHeimTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
                m_clGastTeamName.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
            }
        } //Ende Finished

        //Spiel noch nicht gespielt
        else {
            //Alle Highlights löschen
            for (int i = 0; i < m_vHighlightLabels.size(); i++) {
                panel.remove(m_vHighlightLabels.get(i));
            }

            m_clHeimTeamName.setText(" ");
            m_clGastTeamName.setText(" ");
            m_clHeimTeamTore.setText(" ");
            m_clGastTeamTore.setText(" ");
            m_clHeimTeamName.setIcon(null);
            m_clGastTeamName.setIcon(null);
        }

        repaint();
    }

    /**
     * Strip HTML from text.
     */
    private String removeHtml(String in) {
    	if (in == null) 
    		return in;
    	else
    		return in.replaceAll("<.*?>", "");
    }

	/**
	 * Get the color for the given highlight type and subtype.
	 */
	public static Color getColor4SpielHighlight(int typ, int subtyp) {
		if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
			if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
				return ho.core.model.UserParameter.instance().FG_ZWEIKARTEN;
			} else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
				return ho.core.model.UserParameter.instance().FG_GESPERRT;
			}
		} else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
			return ThemeManager.getColor(HOColorName.LABEL_FG);
		} else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			return ThemeManager.getColor(HOColorName.MATCHHIGHLIGHT_FAILED_FG);
		} else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
			if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
				return ho.core.model.UserParameter.instance().FG_ANGESCHLAGEN;
			} else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_TORWART_FELDSPIELER)) {
				return ho.core.model.UserParameter.instance().FG_VERLETZT;
			}
		}
	
		return ThemeManager.getColor(HOColorName.LABEL_FG);
	}

	public static ImageIcon getImageIcon4SpielHighlight(int typ, int subtyp) {
	    ImageIcon icon = null;
	
	    if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
	        if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ)
	            || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
	            icon = ThemeManager.getIcon(HOIconName.YELLOWCARD);
	        } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
	            icon = ThemeManager.getIcon(HOIconName.REDCARD);
	        }
	    } else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
	        switch (subtyp) {
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_FREEKICK);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_MID);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_LEFT);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_RIGHT);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_PENALTY);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
	            case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2: {
	            	icon = ThemeManager.getIcon(HOIconName.GOAL_FREEKICK2);
	            	break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1: {
	            	icon = ThemeManager.getIcon(HOIconName.GOAL_LONGSHOT);
	            	break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_SPECIAL);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF: {
	                icon = ThemeManager.getIcon(HOIconName.GOAL_COUNTER);
	                break;
	            }
	
	            default:
	                icon = ThemeManager.getIcon(HOIconName.GOAL);
	        }
	    } else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
	        switch (subtyp) {
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_FREEKICK);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_MID);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_LEFT);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_RIGHT);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
	            case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_PENALTY);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
	            case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2: {
	            	icon = ThemeManager.getIcon(HOIconName.NOGOAL_FREEKICK2);
	            	break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1: {
	            	icon = ThemeManager.getIcon(HOIconName.NOGOAL_LONGSHOT);
	            	break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
	            case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR: 
	            case IMatchHighlight.HIGHLIGHT_SUB_QUICK_RUSH_STOPPED_BY_DEF: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_SPECIAL);
	                break;
	            }
	
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
	            case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF: {
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL_COUNTER);
	                break;
	            }
	
	            default:
	                icon = ThemeManager.getIcon(HOIconName.NOGOAL);
	        }
	    } else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
	        if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER)
	            || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
	            icon = ThemeManager.getIcon(HOIconName.PATCHSMALL);
	        } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
	                   || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)) {
	            icon = ThemeManager.getIcon(HOIconName.INJUREDSMALL);
	        }
	    } else if (typ == IMatchHighlight.HIGHLIGHT_SPEZIAL) {
	    	switch (subtyp) {
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY: 	// +
	    		icon = ThemeManager.getScaledIcon(HOIconName.WEATHER_RAIN_POS,16,10); break;
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY: 	// +
	    		icon = ThemeManager.getScaledIcon(HOIconName.WEATHER_SUN_POS,16,10); break;
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY: 	// -
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY:		// -
	    		icon = ThemeManager.getScaledIcon(HOIconName.WEATHER_SUN_NEG,16,10); break;
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY:		// -
	    	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY: 	// -
	    		icon = ThemeManager.getScaledIcon(HOIconName.WEATHER_RAIN_NEG,16,10); break;
	    	default:
	    		icon = null;
	    	}
	    }
	
	    return icon;
	}
}
