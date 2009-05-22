// %127697663:de.hattrickorganizer.gui.matchprediction%
package de.hattrickorganizer.gui.matchprediction;

import gui.UserParameter;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plugins.IMatchResult;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.logik.matchengine.MatchPredictionManager;
import de.hattrickorganizer.logik.matchengine.TeamData;

import de.hattrickorganizer.logik.matchengine.engine.common.MatchResult;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchEnginePanel extends ImagePanel implements plugins.IMatchPredictionPanel,
                                                            ActionListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    JButton m_jbButton = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Simulate"));
                                                                            
	private JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,0);
    private MatchResultTable m_jtMatchResultTable;
	private MatchScoreDiffTable m_jtMatchScoreDiffTable;    
    private MatchScoreTable m_jtMatchScoreTable;
    private PredictPanel predictPanel;
    private TeamRatingPanel myTeamPanel;
    private TeamRatingPanel opponentTeamPanel;
    private boolean isHomeMatch = true;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchEnginePanel object.
     *
     * @param myTeamValues TODO Missing Constructuor Parameter Documentation
     * @param opponentTeamValues TODO Missing Constructuor Parameter Documentation
     */
    public MatchEnginePanel(plugins.IMPTeamData myTeamValues, plugins.IMPTeamData opponentTeamValues) {
    	if (opponentTeamValues.getTeamName().startsWith(HOVerwaltung.instance().getModel().getBasics().getTeamName())) {
    		isHomeMatch= false;
    	} 
        myTeamPanel = new TeamRatingPanel(myTeamValues);
        opponentTeamPanel = new TeamRatingPanel(opponentTeamValues);
        predictPanel = new PredictPanel(myTeamValues.getTeamName(), opponentTeamValues.getTeamName());

        m_jtMatchResultTable = new MatchResultTable(new MatchResult(),isHomeMatch);
        m_jtMatchScoreTable = new MatchScoreTable(new MatchResult(),isHomeMatch);
		m_jtMatchScoreDiffTable = new MatchScoreDiffTable(new MatchResult(),isHomeMatch);
		
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param guestteam TODO Missing Method Parameter Documentation
     */
    public final void setGuestteam(plugins.IMPTeamData guestteam) {
        opponentTeamPanel.setTeamData(guestteam);
        predictPanel.setGuestTeamName(guestteam.getTeamName());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hometeam TODO Missing Method Parameter Documentation
     */
    public final void setHometeam(plugins.IMPTeamData hometeam) {
        myTeamPanel.setTeamData(hometeam);
        predictPanel.setHomeTeamName(hometeam.getTeamName());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getNumberOfMatches() {
        return slider.getValue();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        calculateNMatches(getNumberOfMatches());
    }

    /**
     * Calculates numberOfMatches matches and show the results uses getNumberOfMatches() as
     * parameter
     *
     * @param numberOfMatches TODO Missing Constructuor Parameter Documentation
     */
    public final void calculateNMatches(int numberOfMatches) {
    	int match = (1 + numberOfMatches) * 1000;
    	
        final de.hattrickorganizer.gui.login.LoginWaitDialog waitDialog = new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                                                                             .instance(),
                                                                                                                             false);
        waitDialog.setVisible(true);

        MatchResult result = new MatchResult();

        for (int i = 0; i < match; i++) {
            final TeamData team1 = myTeamPanel.getTeamData();
            final TeamData team2 = opponentTeamPanel.getTeamData();
            result.addMatchResult(MatchPredictionManager.instance().calculateMatchResult(team1, team2));
            waitDialog.setValue((int) (((double) i * 100d) / (double) match));
        }

        waitDialog.setVisible(false);

        refresh(result);
    }

    ////////////////////////////////////////////////////////////////////////
    //Methoden f�r das Interface!

    /**
     * Use this methode, if you have created your own matchresults. To calculate n matches use
     * calculateNMatches
     *
     * @param matchresults TODO Missing Constructuor Parameter Documentation
     */
    public final void refresh(IMatchResult matchresults) {

        //Beide Tabellen anpassen
        m_jtMatchResultTable.refresh(matchresults,isHomeMatch);

        //Leeren, wird gef�llt durch Klick auf ein Spiel in der Anderen Tabelle
        m_jtMatchScoreTable.refresh(matchresults,isHomeMatch);
		m_jtMatchScoreDiffTable.refresh(matchresults,isHomeMatch);

        //Ergebnisauswertung
        predictPanel.refresh(matchresults);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(2, 2, 2, 2);
		constraints.gridwidth = 2;
		        
		final JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setOpaque(false);
		topPanel.setLayout(layout);		
		
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(predictPanel, BorderLayout.CENTER);

        final JPanel actionpanel = new JPanel(new BorderLayout());
        actionpanel.setOpaque(false);
        m_jbButton.addActionListener(this);
        actionpanel.add(m_jbButton, BorderLayout.EAST);
		

//		Turn on labels at major tick marks.
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(UserParameter.instance().simulatorMatches);   
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				UserParameter.instance().simulatorMatches = slider.getValue();
			}
		});     
        actionpanel.add(slider, BorderLayout.CENTER);

        panel.add(actionpanel, BorderLayout.SOUTH);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(panel, constraints);
        topPanel.add(panel);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.0;
        constraints.weightx = 0.0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.NONE;
        layout.setConstraints(myTeamPanel, constraints);
		topPanel.add(myTeamPanel);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weighty = 0.0;
        constraints.weightx = 0.0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.NONE;
        layout.setConstraints(opponentTeamPanel, constraints);
		topPanel.add(opponentTeamPanel);

		setLayout(new BorderLayout());
		add(topPanel,BorderLayout.NORTH);

        final JScrollPane scrollpane2 = new JScrollPane(m_jtMatchResultTable);
        layout.setConstraints(scrollpane2, constraints);
        add(scrollpane2,BorderLayout.WEST);

        final JScrollPane scrollPane = new JScrollPane(m_jtMatchScoreTable);
		final JScrollPane scrollPane1 = new JScrollPane(m_jtMatchScoreDiffTable);
		final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, scrollPane1);
		split.setDividerLocation(150);        
        add(split,BorderLayout.CENTER);        		        
    }
}
