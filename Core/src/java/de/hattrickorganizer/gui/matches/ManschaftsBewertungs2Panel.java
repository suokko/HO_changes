// %2517784300:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import gui.HOColorName;
import gui.HOIconName;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import plugins.IMatchKurzInfo;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.gui.theme.ho.HOClassicSchema;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.Matchdetails;


/**
 * Zeigt die Stärken eines Matches an
 */
class ManschaftsBewertungs2Panel extends ImagePanel implements ActionListener {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 1835093736247065469L;

    //~ Instance fields ----------------------------------------------------------------------------


    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JProgressBar[] bars = new JProgressBar[8];
    private Color green = ThemeManager.getColor(HOColorName.MATCHDETAILS_PROGRESSBAR_GREEN);
    private Color red = ThemeManager.getColor(HOColorName.MATCHDETAILS_PROGRESSBAR_RED);
    private MatchKurzInfo m_clMatchKurzInfo;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ManschaftsBewertungsPanel object.
     */
    ManschaftsBewertungs2Panel() {
        this(false);
    }

    ManschaftsBewertungs2Panel(boolean print) {
        super(print);
        for (int i = 0; i < bars.length; i++) {
			bars[i] = new JProgressBar(0,100);
			bars[i].setStringPainted(true);
		}
        setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        final GridBagLayout mainlayout = new GridBagLayout();
        final GridBagConstraints mainconstraints = new GridBagConstraints();
        mainconstraints.anchor = GridBagConstraints.NORTH;
        mainconstraints.fill = GridBagConstraints.HORIZONTAL;
        mainconstraints.weighty = 0.1;
        mainconstraints.weightx = 1.0;
        mainconstraints.insets = new Insets(4, 6, 4, 6);

        setLayout(mainlayout);

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0.0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(5, 3, 2, 2);

        final JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor(HOColorName.PANEL_BORDER)));
        panel.setBackground(getBackground());

        JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
        label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 3;
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
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 4;
        m_clHeimTeamName.setPreferredSize(new Dimension(140, 14));
        m_clHeimTeamName.setFont(m_clHeimTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamName, constraints);
        panel.add(m_clHeimTeamName);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 5;
        constraints.gridy = 4;
        m_clGastTeamName.setPreferredSize(new Dimension(140, 14));
        m_clGastTeamName.setFont(m_clGastTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamName, constraints);
        panel.add(m_clGastTeamName);

        //Platzhalter
        label = new JLabel(" ");
//        label.setBorder(BorderFactory.createLineBorder(red));
        add(panel,label,layout,constraints,0,5);

        //Bewertungen
        //Mittelfeld
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtstaerke"));
        add(panel,label,layout,constraints,1,6);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtstaerke"));
        add(panel,label,layout,constraints,5,6);
        add(panel,bars[0],layout,constraints,3,6);

        //Platzhalter
        label = new JLabel(" ");
        add(panel,label,layout,constraints,0,7);

        //Mittelfeld
        label = new JLabel(HOVerwaltung.instance().getLanguageString("MatchMittelfeld"));
        add(panel,label,layout,constraints,1,8);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("MatchMittelfeld"));
        add(panel,label,layout,constraints,5,8);
        
        add(panel,bars[1],layout,constraints,3,8);
 
        //rechte Abwehrseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAbwehrseite"));
        add(panel,label,layout,constraints,1,9);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAngriffsseite"));
        add(panel,label,layout,constraints,5,9);
        add(panel,bars[2],layout,constraints,3,9);
 
        //Abwehrzentrum
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Abwehrzentrum"));
        add(panel,label,layout,constraints,1,10);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Angriffszentrum"));
        add(panel,label,layout,constraints,5,10);

        add(panel,bars[3],layout,constraints,3,10);


        //Linke Abwehrseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAbwehrseite"));
        add(panel,label,layout,constraints,1,11);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAngriffsseite"));
        add(panel,label,layout,constraints,5,11);
        add(panel,bars[4],layout,constraints,3,11);


        //Rechte Angriffsseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAngriffsseite"));
        add(panel,label,layout,constraints,1,12);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAbwehrseite"));
        add(panel,label,layout,constraints,5,12);
        add(panel,bars[5],layout,constraints,3,12);
 

        //Angriffszentrum
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Angriffszentrum"));
        add(panel,label,layout,constraints,1,13);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Abwehrzentrum"));
        add(panel,label,layout,constraints,5,13);
        add(panel,bars[6],layout,constraints,3,13);
        

        //Linke Angriffsseite
        label = new JLabel(HOVerwaltung.instance().getLanguageString("linkeAngriffsseite"));
        add(panel,label,layout,constraints,1,14);
        label = new JLabel(HOVerwaltung.instance().getLanguageString("rechteAbwehrseite"));
        add(panel,label,layout,constraints,5,14);
        add(panel,bars[7],layout,constraints,3,14);


        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }

   
    private void add(JPanel panel,JComponent label,GridBagLayout layout,GridBagConstraints constraints, int x, int y){
    	if(x == 0){
            constraints.weightx = 0.0;
            constraints.gridwidth = 1;
    	} else {
            constraints.weightx = 1.0;
            constraints.gridwidth = 2;
    	}
    		
    	constraints.gridx = x;
        constraints.gridy = y;
    	constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    	layout.setConstraints(label, constraints);
    	panel.add(label);
    }
    //~ Methods ------------------------------------------------------------------------------------


    public final void actionPerformed(ActionEvent e) {
        final int matchid = m_clMatchKurzInfo.getMatchID();
        HOMainFrame.instance().getOnlineWorker().getMatchlineup(m_clMatchKurzInfo.getMatchID(),
                                                                                         m_clMatchKurzInfo.getHeimID(),
                                                                                         m_clMatchKurzInfo.getGastID());
        HOMainFrame.instance().getOnlineWorker().getMatchDetails(m_clMatchKurzInfo.getMatchID());
        RefreshManager.instance().doReInit();
        HOMainFrame.instance().showMatch(matchid);
    }

    final void clear() {
        m_clHeimTeamName.setText(" ");
        m_clGastTeamName.setText(" ");
        m_clHeimTeamName.setIcon(null);
        m_clGastTeamName.setIcon(null);

        for (int i = 0; i < bars.length; i++) {
			bars[i].setValue(0);
			bars[i].setString("");
		}

    }

    final void refresh(MatchKurzInfo info) {
        m_clMatchKurzInfo = info;

        final Matchdetails details = DBZugriff.instance().getMatchDetails(info.getMatchID());

        //Teams
        final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();

        m_clHeimTeamName.setText(info.getHeimName());
        m_clGastTeamName.setText(info.getGastName());

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

        if (info.getMatchStatus() == IMatchKurzInfo.FINISHED) {
            //Sterne für Sieger!
            if (info.getMatchStatus() != IMatchKurzInfo.FINISHED) {
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
            setBarValue(0,details.getHomeGesamtstaerke(false),details.getGuestGesamtstaerke(false));
            setBarValue(1,details.getHomeMidfield(),details.getGuestMidfield());
            setBarValue(2,details.getHomeRightDef(),details.getGuestLeftAtt());
            setBarValue(3,details.getHomeMidDef(),details.getGuestMidAtt());
            setBarValue(4,details.getHomeLeftDef(),details.getGuestRightAtt());
            setBarValue(5,details.getHomeRightAtt(),details.getGuestLeftDef());
            setBarValue(6,details.getHomeMidAtt(),details.getGuestMidDef());
            setBarValue(7,details.getHomeLeftAtt(),details.getGuestRightDef());
       } //Ende Finished

        //Spiel noch nicht gespielt
        else {
        	clear();
        }

        repaint();
    }
    
    private void setBarValue(int index,float home, float away){
        bars[index].setValue((int)getPercent(home,away));
        bars[index].setToolTipText(bars[index].getValue()+" %"+ " -- "+(100-bars[index].getValue())+" %");
        bars[index].setForeground(bars[index].getValue()<50?red:green);
        bars[index].setBackground(bars[index].getValue()<50?green:red);
    }
    
    public float getPercent(float home, float opponnent) {
		return home * 100 / (home + opponnent);
	}
}
