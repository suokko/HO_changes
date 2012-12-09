// %55184142:hoplugins.teamAnalyzer.ui%
package ho.module.teamAnalyzer.ui;

import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.player.SpielerPosition;
import ho.module.teamAnalyzer.report.TacticReport;
import ho.module.teamAnalyzer.vo.UserTeamSpotLineup;

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
    protected Color getBackGround() {
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
            setPlayerStatus(lineup.getStatus());
            positionImage.setIcon(ImageUtilities.getImage4Position(lineup.getSpot(),
                                                                                   (byte) lineup
                                                                                   .getTacticCode(),0));
            specialEventImage.setIcon(ThemeManager.getIcon(HOIconName.SPECIAL[lineup.getSpecialEvent()]));
            positionField.setText(SpielerPosition.getNameForPosition((byte) lineup
                                                                                    .getPosition()));
            updateRatingPanel(lineup.getRating());
            tacticPanel.reload(new ArrayList<TacticReport>());
        } else {
        	containsPlayer = false;
        	
            nameField.setText("");
            positionField.setText("");
            updateRatingPanel(0);
            positionImage.setIcon(ImageUtilities.getImage4Position(0, (byte) 0,0));
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
