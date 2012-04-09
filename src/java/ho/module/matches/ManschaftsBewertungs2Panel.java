// %2517784300:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;



/**
 * Zeigt die Stärken eines Matches an
 */
class ManschaftsBewertungs2Panel extends ImagePanel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 1835093736247065469L;

    //~ Instance fields ----------------------------------------------------------------------------


    private JLabel m_clGastTeamName = new JLabel();
    private JLabel m_clHeimTeamName = new JLabel();
    private JLabel m_clHeimTeamTore = new JLabel();
    private JLabel m_clGastTeamTore = new JLabel();
    private JProgressBar[] bars = new JProgressBar[8];
    private JLabel[] homePercent = new JLabel[8];
    private JLabel[] awayPercent = new JLabel[8];
    private Color green = ThemeManager.getColor(HOColorName.MATCHDETAILS_PROGRESSBAR_GREEN);
    private Color red = ThemeManager.getColor(HOColorName.MATCHDETAILS_PROGRESSBAR_RED);
    private final GridBagLayout layout = new GridBagLayout();
    private final GridBagConstraints constraints = new GridBagConstraints();

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
			homePercent[i] = new JLabel(" ");
			awayPercent[i] = new JLabel(" ");
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

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 2;
        constraints.gridy = 4;
        m_clHeimTeamTore.setFont(m_clHeimTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clHeimTeamTore, constraints);
        panel.add(m_clHeimTeamTore);
        
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 5;
        constraints.gridy = 4;
        m_clGastTeamName.setPreferredSize(new Dimension(140, 14));
        m_clGastTeamName.setFont(m_clGastTeamName.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamName, constraints);
        panel.add(m_clGastTeamName);
        
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 4;
        m_clGastTeamTore.setFont(m_clGastTeamTore.getFont().deriveFont(Font.BOLD));
        layout.setConstraints(m_clGastTeamTore, constraints);
        panel.add(m_clGastTeamTore);

        //Platzhalter
        label = new JLabel(" ");
        add(panel,label,layout,constraints,0,5);

        //Bewertungen
        //Mittelfeld
        initRow(panel,"Gesamtstaerke","Gesamtstaerke",0, 6);
       

        //Platzhalter
        label = new JLabel(" ");
        add(panel,label,layout,constraints,0,7);

       
        initRow(panel,"MatchMittelfeld","MatchMittelfeld",1, 8);
        initRow(panel,"rechteAbwehrseite","linkeAngriffsseite",2, 9);
        initRow(panel,"Abwehrzentrum","Angriffszentrum",3, 10);
        initRow(panel,"linkeAbwehrseite","rechteAngriffsseite",4, 11);
        initRow(panel,"rechteAngriffsseite","linkeAbwehrseite",5, 12);
        initRow(panel,"Angriffszentrum","Abwehrzentrum",6, 13);
        initRow(panel,"linkeAngriffsseite","rechteAbwehrseite",7, 14);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(panel, mainconstraints);
        add(panel);

        clear();
    }
    
    private void initRow(JPanel panel , String txt1,String txt2, int index, int row){
         add(panel,new JLabel(HOVerwaltung.instance().getLanguageString(txt1)),layout,constraints,1,row);
         add(panel,homePercent[index],layout,constraints,2,row);
         add(panel,bars[index],layout,constraints,3,row);
         add(panel,awayPercent[index],layout,constraints,4,row);
         add(panel,new JLabel(HOVerwaltung.instance().getLanguageString(txt2)),layout,constraints,5,row);
        
    }
   
    private void add(JPanel panel,JComponent label,GridBagLayout layout,GridBagConstraints constraints, int x, int y){
    	if(x == 0){
            constraints.weightx = 0.0;
            constraints.gridwidth = 1;
    	} else {
            constraints.weightx = 1.0;
            constraints.gridwidth = 1;
    	}
    		
    	constraints.gridx = x;
        constraints.gridy = y;
    	constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    	layout.setConstraints(label, constraints);
    	panel.add(label);
    }
    //~ Methods ------------------------------------------------------------------------------------

    final void clear() {
        m_clHeimTeamName.setText(" ");
        m_clGastTeamName.setText(" ");
        m_clHeimTeamTore.setText(" ");
        m_clGastTeamTore.setText(" ");
        m_clHeimTeamName.setIcon(null);
        m_clGastTeamName.setIcon(null);

        for (int i = 0; i < bars.length; i++) {
			bars[i].setValue(0);
			homePercent[i].setText(" ");
			awayPercent[i].setText(" ");
		}

    }

    final void refresh(MatchKurzInfo info,Matchdetails details) {

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

        if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
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
        homePercent[index].setText(bars[index].getValue()+" %");
        awayPercent[index].setText((100-bars[index].getValue())+" %");
        bars[index].setForeground(bars[index].getValue()<50?red:green);
        bars[index].setBackground(bars[index].getValue()<50?green:red);
    }
    
    public float getPercent(float home, float opponnent) {
		return home * 100 / (home + opponnent);
	}
}
