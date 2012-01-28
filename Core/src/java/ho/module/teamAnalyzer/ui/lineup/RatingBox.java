// %2624369625:hoplugins.teamAnalyzer.ui%
package ho.module.teamAnalyzer.ui.lineup;

import ho.module.ModuleConfig;
import ho.module.teamAnalyzer.ui.RatingUtil;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class RatingBox extends JPanel {
	private static final long serialVersionUID = 7739872564097601073L;

    private JLabel arrow = new JLabel();
    private JLabel myValue = new JLabel();
    private JLabel opponentValue = new JLabel();


    RatingBox() {
        super();
        jbInit();
    }

    void reload(int r1, int r2) {
        myValue.setText(RatingUtil.getRating(r1, ModuleConfig.instance().getBoolean(ModuleConfig.isNumericRating),
        		ModuleConfig.instance().getBoolean(ModuleConfig.isDescriptionRating)));
        opponentValue.setText(RatingUtil.getRating(r2, ModuleConfig.instance().getBoolean(ModuleConfig.isNumericRating),
        		ModuleConfig.instance().getBoolean(ModuleConfig.isDescriptionRating)));
        arrow.setIcon(HelperWrapper.instance().getImageIcon4Veraenderung(r1 - r2));
    }

    private void jbInit() {
        JPanel mainPanel = new ImagePanel();

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
