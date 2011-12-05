// %55184142:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.report.TacticReport;
import hoplugins.teamAnalyzer.vo.UserTeamSpotLineup;

import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class UserTeamPlayerPanel extends PlayerPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7445084726999652737L;
	//private UserTeamSpotLineup spotLineup;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public Color getBackGround() {
        return Color.LIGHT_GRAY;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public Dimension getDefaultSize() {
        return new Dimension(150, 60);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param lineup TODO Missing Method Parameter Documentation
     */
    public void reload(UserTeamSpotLineup lineup) {
        //spotLineup = lineup;

        if (lineup != null) {
        	
        	containsPlayer = true;
        	
            nameField.setText(lineup.getName());
            positionImage.setIcon(Commons.getModel().getHelper().getImage4Position(lineup.getSpot(),
                                                                                   (byte) lineup
                                                                                   .getTacticCode()));
            specialEventImage.setIcon(Commons.getModel().getHelper().getImageIcon4Spezialitaet(lineup
                                                                                               .getSpecialEvent()));
            positionField.setText(Commons.getModel().getHelper().getNameForPosition((byte) lineup
                                                                                    .getPosition()));
            updateRatingPanel(lineup.getRating());
            tacticPanel.reload(new ArrayList<TacticReport>());
        } else {
        	containsPlayer = false;
        	
            nameField.setText("");
            positionField.setText("");
            updateRatingPanel(0);
            positionImage.setIcon(Commons.getModel().getHelper().getImage4Position(0, (byte) 0));
            specialEventImage.setIcon(null);
            tacticPanel.reload(new ArrayList<TacticReport>());
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void updateSpecialEvent() {
        // DO NOTHING
    }
}
