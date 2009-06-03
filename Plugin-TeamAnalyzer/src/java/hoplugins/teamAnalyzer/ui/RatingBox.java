// %2624369625:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;
import hoplugins.commons.utils.RatingUtil;
import hoplugins.teamAnalyzer.SystemManager;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RatingBox extends JPanel {
	private static final long serialVersionUID = 7739872564097601073L;

	//~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
    JLabel arrow = new JLabel();

    /** TODO Missing Parameter Documentation */
    JLabel myValue = new JLabel();

    /** TODO Missing Parameter Documentation */
    JLabel opponentValue = new JLabel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RatingBox object.
     */
    public RatingBox() {
        super();
        jbInit();
    }

    /**
     * Creates a new RatingBox object.
     *
     * @param r1 TODO Missing Constructuor Parameter Documentation
     * @param r2 TODO Missing Constructuor Parameter Documentation
     */
    public RatingBox(int r1, int r2) {
        super();
        jbInit();
        reload(r1, r2);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param r1 TODO Missing Method Parameter Documentation
     * @param r2 TODO Missing Method Parameter Documentation
     */
    public void reload(int r1, int r2) {
        myValue.setText(RatingUtil.getRating(r1, SystemManager.getConfig().isNumericRating(),
                                             SystemManager.getConfig().isDescriptionRating(),
                                             Commons.getModel()));
        opponentValue.setText(RatingUtil.getRating(r2, SystemManager.getConfig().isNumericRating(),
                                                   SystemManager.getConfig().isDescriptionRating(),
                                                   Commons.getModel()));
        arrow.setIcon(Commons.getModel().getHelper().getImageIcon4Veraenderung(r1 - r2));
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        JPanel mainPanel = Commons.getModel().getGUI().createImagePanel();

        mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.add(myValue);
        mainPanel.add(arrow);
        mainPanel.add(opponentValue);
        add(mainPanel);

        myValue.setForeground(Color.BLACK);
        opponentValue.setForeground(Color.BLACK);
        setForeground(Color.BLACK);
        setOpaque(false);
        setFont(getFont().deriveFont(Font.BOLD, gui.UserParameter.instance().schriftGroesse + 3));
    }
}
