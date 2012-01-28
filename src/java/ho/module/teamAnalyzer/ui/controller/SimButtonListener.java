// %3491987323:hoplugins.teamAnalyzer.ui.controller%
package ho.module.teamAnalyzer.ui.controller;

import ho.core.plugins.GUIPluginWrapper;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.ui.RecapPanel;
import ho.module.teamAnalyzer.ui.TeamLineupData;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import plugins.IMPTeamData;
import plugins.IMPTeamRatings;
import plugins.IMatchDetails;
import plugins.IMatchPredictionManager;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.logik.matchengine.MatchPredictionManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * Action listener for the simulation Button
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SimButtonListener implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The match prediction Manager */
    IMatchPredictionManager manager = MatchPredictionManager.instance();

    /** The user team */
    TeamLineupData myTeam;

    /** The opponent team */
    TeamLineupData opponentTeam;
    private RecapPanel recapPanel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SimButtonListener object.
     *
     * @param panel user team
     * @param panel2 opponent team
     */
    public SimButtonListener(TeamLineupData myTeam, TeamLineupData opponentTeam, RecapPanel recapPanel) {
        this.myTeam = myTeam;
        this.opponentTeam = opponentTeam;
        this.recapPanel = recapPanel;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener PRepares the date for the 2 teams and lauch the
     * MatchRatingPredictor
     *
     * @param e the event
     */
    public void actionPerformed(ActionEvent e) {
    	System.out.println("SimButton");
        if ((myTeam.getMidfield() < 1) || (opponentTeam.getMidfield() < 1)) {
            JPanel pan = new JPanel();

            pan.add(new JLabel(HOVerwaltung.instance().getLanguageString("Error.SetFormation")));
            JOptionPane.showMessageDialog(SystemManager.getPlugin(), pan,
            		HOVerwaltung.instance().getLanguageString("Error"),
                                          JOptionPane.PLAIN_MESSAGE);

            return;
        }

        IMPTeamRatings myTeamRatings = manager.generateTeamRatings(myTeam.getMidfield(),
                                                                   myTeam.getLeftDefence(),
                                                                   myTeam.getMiddleDefence(),
                                                                   myTeam.getRightDefence(),
                                                                   myTeam.getLeftAttack(),
                                                                   myTeam.getMiddleAttack(),
                                                                   myTeam.getRightAttack());

        IMPTeamData myTeamValues = manager.generateTeamData(myTeam.getTeamPanel().getText(),
        		myTeamRatings, HOVerwaltung.instance().getModel().getAufstellung().getTacticType(), getTacticLevel());

        IMPTeamRatings opponentTeamRatings = manager.generateTeamRatings(opponentTeam.getMidfield(),
                                                                         opponentTeam.getLeftDefence(),
                                                                         opponentTeam.getMiddleDefence(),
                                                                         opponentTeam.getRightDefence(),
                                                                         opponentTeam.getLeftAttack(),
                                                                         opponentTeam.getMiddleAttack(),
                                                                         opponentTeam.getRightAttack());

        IMPTeamData opponentTeamValues = manager.generateTeamData(opponentTeam.getTeamPanel().getText(),
        		opponentTeamRatings, getTacticType(recapPanel.getSelectedTacticType()),
        		getTacticSkill(recapPanel.getSelectedTacticSkill()));

        JPanel matchPredictionPanel;
        String match = "";

        if (HOVerwaltung.instance().getModel().getAufstellung().getHeimspiel() == 1) {
            matchPredictionPanel =
            	GUIPluginWrapper.instance().createMatchPredictionPanel(myTeamValues, opponentTeamValues);
            match = myTeamValues.getTeamName() + " - " + opponentTeamValues.getTeamName();
        } else {
            matchPredictionPanel =
            		GUIPluginWrapper.instance().createMatchPredictionPanel(opponentTeamValues, myTeamValues);
            match = opponentTeamValues.getTeamName() + " - " + myTeamValues.getTeamName();
        }

        JDialog d = new JDialog(HOMainFrame.instance());

        d.getContentPane().setLayout(new BorderLayout());
        d.getContentPane().add(matchPredictionPanel, BorderLayout.CENTER);
        d.setResizable(true);
        d.setSize(900, 600);
        Dimension screenSize = HOMainFrame.instance().getSize();  
 	   int x = (screenSize.width - d.getWidth()) / 2;  
 	   int y = (screenSize.height - d.getHeight()) / 2;  
 	    
 	   d.setLocation(HOMainFrame.instance().getX()+x, HOMainFrame.instance().getY()+y); 
        d.setTitle(match);
        d.setVisible(true);
    }

    /**
     * Returns the tactic level for the user team defined formation in HO Lineup
     *
     * @return the actual tactic level as shown in HO LIneup tab
     */
    private int getTacticLevel() {
        double tacticLevel = 1d;

        switch (HOVerwaltung.instance().getModel().getAufstellung().getTacticType()) {
            case IMatchDetails.TAKTIK_KONTER:
                tacticLevel = HOVerwaltung.instance().getModel().getAufstellung().getTacticLevelCounter();
                break;

            case IMatchDetails.TAKTIK_MIDDLE:
                tacticLevel = HOVerwaltung.instance().getModel().getAufstellung().getTacticLevelAimAow();
                break;

            case IMatchDetails.TAKTIK_PRESSING:
                tacticLevel = HOVerwaltung.instance().getModel().getAufstellung().getTacticLevelPressing();
                break;

            case IMatchDetails.TAKTIK_WINGS:
                tacticLevel = HOVerwaltung.instance().getModel().getAufstellung().getTacticLevelAimAow();
                break;

            case IMatchDetails.TAKTIK_LONGSHOTS:
                tacticLevel = HOVerwaltung.instance().getModel().getAufstellung().getTacticLevelLongShots();
                break;
        }
        // Re-Scale to HT ratings (...,solid=6,...,divine=19)
        tacticLevel -= 1;
        return (int)Math.max(tacticLevel, 0);
    }

    /**
     * Get the tactic constant for a i18n'ed string.
     */
    private int getTacticType(String strTactic) {
    	try {
	    	if (strTactic == null || RecapPanel.VALUE_NA.equals(strTactic)) {
	    		return IMatchDetails.TAKTIK_NORMAL;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_KONTER))) {
	    		return IMatchDetails.TAKTIK_KONTER;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_MIDDLE))) {
	    		return IMatchDetails.TAKTIK_MIDDLE;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_WINGS))) {
	    		return IMatchDetails.TAKTIK_WINGS;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_PRESSING))) {
	    		return IMatchDetails.TAKTIK_PRESSING;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_CREATIVE))) {
	    		return IMatchDetails.TAKTIK_CREATIVE;
	    	} else if (strTactic.equals(HelperWrapper.instance().getNameForTaktik(IMatchDetails.TAKTIK_LONGSHOTS))) {
	    		return IMatchDetails.TAKTIK_LONGSHOTS;
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return IMatchDetails.TAKTIK_NORMAL;
    }

    /**
     * Get the tactic skill for a i18n'ed string.
     */
    private int getTacticSkill(String strSkill) {
    	try {
    		if (strSkill == null || RecapPanel.VALUE_NA.equals(strSkill)) {
    			return 0;
    		}
    		for (int m=0; m<21; m++) {
    			if (strSkill.equals(HelperWrapper.instance().getNameForSkill(m, false))) {
    				return (m>0?m-1:m); // TeamRatingPanel doesnt handle 'non existant', index 0=disastrous
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
}
