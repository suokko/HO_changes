// %4112883594:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

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

import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.Helper;


/**
 * Zeigt die Stärken eines Matches an.
 */
public class SpielHighlightPanel extends ImagePanel {
    //~ Static fields/initializers -----------------------------------------------------------------
	private static final long serialVersionUID = -6491501224900464573L;
	/** default foreground color own team */
    public static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);

    //~ Instance fields ----------------------------------------------------------------------------

    private GridBagConstraints constraints = new GridBagConstraints();

    //müssen global sein, da sie auch im refresh benutzt werden
    private GridBagLayout layout = new GridBagLayout();
    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private JPanel panel = new JPanel(layout);
//    private MatchKurzInfo m_clMatchKurzInfo;
    private Vector<Component> m_vHighlightLabels = new Vector<Component>();

    //~ Constructors -------------------------------------------------------------------------------

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

        setBackground(Color.WHITE);

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

        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        panel.setBackground(Color.white);

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

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Heim"));
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

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gast"));
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
    public final void refresh(MatchKurzInfo info) {
        clear();
        //m_clMatchKurzInfo = info;

        final Matchdetails details = de.hattrickorganizer.database.DBZugriff.instance()
                                                                            .getMatchDetails(info
                                                                                             .getMatchID());

        //Teams
        final int teamid = de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getBasics()
                                                                  .getTeamId();

        m_clHeimTeamName.setText(info.getHeimName());
        m_clGastTeamName.setText(info.getGastName());

        m_clHeimTeamTore.setText(info.getHeimTore() + " ");
        m_clGastTeamTore.setText(info.getGastTore() + " ");

        if (info.getHeimID() == teamid) {
            m_clHeimTeamName.setForeground(FG_EIGENESTEAM);
        } else {
            m_clHeimTeamName.setForeground(java.awt.Color.black);
        }

        if (info.getGastID() == teamid) {
            m_clGastTeamName.setForeground(FG_EIGENESTEAM);
        } else {
            m_clGastTeamName.setForeground(java.awt.Color.black);
        }

        //Alle Highlights löschen
        for (int i = 0; i < m_vHighlightLabels.size(); i++) {
            panel.remove(m_vHighlightLabels.get(i));
        }

        if (info.getMatchStatus() == IMatchKurzInfo.FINISHED) {
            //Highlights anzeigen
            JLabel playerlabel = null;
            JLabel resultlabel = null;

            final Vector<IMatchHighlight> vMatchHighlights = details.getHighlights();

            for (int i = 0; i < vMatchHighlights.size(); i++) {
                final IMatchHighlight highlight =  vMatchHighlights.get(i);

                //Label vorbereiten
                final ImageIcon icon = Helper.getImageIcon4SpielHighlight(highlight.getHighlightTyp(),
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
                    playerlabel.setForeground(Helper.getColor4SpielHighlight(highlight.getHighlightTyp(),
                                                                       highlight.getHighlightSubTyp()));
                    if (Helper.isWeatherSEHighlight(highlight.getHighlightTyp(),
                                                                               highlight.getHighlightSubTyp())) {
                    	playerlabel.setToolTipText(removeHtml(highlight.getEventText()));
                    } else {
                    	playerlabel.setToolTipText(Helper.getTooltiptext4SpielHighlight(highlight.getHighlightTyp(),
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
            if (info.getMatchStatus() != IMatchKurzInfo.FINISHED) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() > info.getGastTore()) {
                m_clHeimTeamName.setIcon(Helper.YELLOWSTARIMAGEICON);
                m_clGastTeamName.setIcon(null);
            } else if (info.getHeimTore() < info.getGastTore()) {
                m_clHeimTeamName.setIcon(null);
                m_clGastTeamName.setIcon(Helper.YELLOWSTARIMAGEICON);
            } else {
                m_clHeimTeamName.setIcon(Helper.GREYSTARIMAGEICON);
                m_clGastTeamName.setIcon(Helper.GREYSTARIMAGEICON);
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
}
