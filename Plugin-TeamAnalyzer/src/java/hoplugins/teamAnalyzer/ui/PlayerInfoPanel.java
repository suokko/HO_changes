// %1980238621:hoplugins.teamAnalyzer.ui%
/*
 * Created on 23.10.2005
 */
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.manager.PlayerDataManager;
import hoplugins.teamAnalyzer.vo.PlayerInfo;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class PlayerInfoPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5338615169669058973L;

	/** TODO Missing Parameter Documentation */
    protected JLabel ageLabel = new JLabel("");

    /** TODO Missing Parameter Documentation */
    protected JLabel expLabel = new JLabel("");

    /** TODO Missing Parameter Documentation */
    protected JLabel formLabel = new JLabel("");

    /** TODO Missing Parameter Documentation */
    protected JLabel tsiLabel = new JLabel("");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     *
     */
    public PlayerInfoPanel() {
        super();

        //this.setOpaque(false);
        this.setLayout(new GridBagLayout());

        //this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        addInfo(Commons.getModel().getLanguageString("Alter"), ageLabel, 0, 0);
        addInfo(PluginProperty.getString("TSI"), tsiLabel, 0, 1);
        addInfo(Commons.getModel().getLanguageString("Form"), formLabel, 1, 0);
        addInfo(PluginProperty.getString("EXPCode"), expLabel, 1, 1);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param oldPlayer TODO Missing Method Parameter Documentation
     */
    public void setValue(PlayerInfo oldPlayer) {
        clearData();
        expLabel.setText(Commons.getModel().getHelper().getNameForSkill(oldPlayer.getExperience(),
                                                                        false));
        tsiLabel.setText("" + oldPlayer.getTSI());
        ageLabel.setText("" + oldPlayer.getAge());
        formLabel.setText(Commons.getModel().getHelper().getNameForSkill(oldPlayer.getForm(), false));

        PlayerInfo actual = PlayerDataManager.getPlayerInfo(oldPlayer.getPlayerId());

        if (actual.getForm() == 0) {
            return;
        }

        if (Commons.getModel().getHelper().isDevVersion()) {
            expLabel.setIcon(Commons.getModel().getHelper().getImageIcon4Veraenderung(actual
                                                                                      .getExperience()
                                                                                      - oldPlayer
                                                                                        .getExperience()));
            formLabel.setIcon(Commons.getModel().getHelper().getImageIcon4Veraenderung(actual
                                                                                       .getForm()
                                                                                       - oldPlayer
                                                                                         .getForm()));

            int diff = actual.getTSI() - oldPlayer.getTSI();
            String desc = "";

            if (diff > 0) {
                desc = "+";
            }

            desc = desc + diff;
            tsiLabel.setToolTipText(desc);
        }
    }

    /**
     *
     */
    public void clearData() {
        ageLabel.setText("");
        formLabel.setText("");
        expLabel.setText("");
        tsiLabel.setText("");
        expLabel.setIcon(null);
        formLabel.setIcon(null);
        tsiLabel.setToolTipText("");
    }

    /**
     * DOCUMENT ME!
     *
     * @param gridx
     * @param gridy
     * @param gridwidth
     * @param gridheight
     *
     * @return constraint
     */
    private GridBagConstraints getConstraint(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = 0.5D;
        c.weighty = 0.5D;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;

        return c;
    }

    /**
     * DOCUMENT ME!
     *
     * @param title
     * @param label
     * @param x
     * @param y
     */
    private void addInfo(String title, JLabel label, int x, int y) {
        GridBagConstraints c = getConstraint(x, y, 1, 1);

        JLabel titleLabel = new JLabel(title);

        titleLabel.setOpaque(false);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setHorizontalTextPosition(SwingConstants.LEFT);

        label.setOpaque(false);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);

        JPanel panel = Commons.getModel().getGUI().createImagePanel();

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);

        this.add(panel, c);
    }
}
