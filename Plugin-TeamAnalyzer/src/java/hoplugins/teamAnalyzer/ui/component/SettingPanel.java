// %1193228360:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * A panel that allows the user to configure the plugin
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SettingPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JCheckBox checkName = new JCheckBox();
    private JCheckBox descRating = new JCheckBox();
    private JCheckBox loddarStats = new JCheckBox();
    private JCheckBox mixedLineup = new JCheckBox();
    private JCheckBox myLineup = new JCheckBox();
    private JCheckBox numberRating = new JCheckBox();
    private JCheckBox playerInfo = new JCheckBox();
    private JCheckBox smartSquad = new JCheckBox();
    private JCheckBox squad = new JCheckBox();
    private JCheckBox stars = new JCheckBox();
    private JCheckBox tacticDetail = new JCheckBox();
    private JCheckBox totalStrength = new JCheckBox();
    private JCheckBox unavailable = new JCheckBox();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public SettingPanel() {
        super();
        numberRating.setSelected(SystemManager.getConfig().isNumericRating());
        numberRating.setOpaque(false);
        descRating.setSelected(SystemManager.getConfig().isDescriptionRating());
        descRating.setOpaque(false);
        myLineup.setSelected(SystemManager.getConfig().isLineup());
        myLineup.setOpaque(false);
        tacticDetail.setSelected(SystemManager.getConfig().isTacticDetail());
        tacticDetail.setOpaque(false);
        unavailable.setSelected(SystemManager.getConfig().isShowUnavailable());
        unavailable.setOpaque(false);
        playerInfo.setSelected(SystemManager.getConfig().isShowPlayerInfo());
        playerInfo.setOpaque(false);
        mixedLineup.setSelected(SystemManager.getConfig().isMixedLineup());
        mixedLineup.setOpaque(false);
        stars.setSelected(SystemManager.getConfig().isStars());
        stars.setOpaque(false);
        smartSquad.setSelected(SystemManager.getConfig().isSmartSquad());
        smartSquad.setOpaque(false);
        loddarStats.setSelected(SystemManager.getConfig().isLoddarStats());
        loddarStats.setOpaque(false);
        squad.setSelected(SystemManager.getConfig().isSquad());
        squad.setOpaque(false);
        totalStrength.setSelected(SystemManager.getConfig().isTotalStrength());
        totalStrength.setOpaque(false);
        checkName.setSelected(SystemManager.getConfig().isCheckTeamName());
        checkName.setOpaque(false);
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Create a new Panel
     *
     * @param string Label text
     * @param checkBox CheckBox
     *
     * @return a panel
     */
    private JPanel createPanel(String string, JComponent checkBox) {
        JPanel panel = Commons.getModel().getGUI().createImagePanel();

        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        JPanel innerPanel = Commons.getModel().getGUI().createImagePanel();

        //innerPanel.setLayout(new BorderLayout());
        innerPanel.add(checkBox);
        innerPanel.add(new JLabel(string, JLabel.LEFT));
        innerPanel.setOpaque(false);
        panel.add(innerPanel, BorderLayout.WEST);

        return panel;
    }

    /**
     * Initialize listeners
     */
    private void initListeners() {
        numberRating.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (numberRating.isSelected() || descRating.isSelected()) {
                        SystemManager.getConfig().setNumericRating(numberRating.isSelected());
                        SystemManager.updateUI();
                    } else {
                        numberRating.setSelected(true);
                    }
                }
            });

        descRating.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (numberRating.isSelected() || descRating.isSelected()) {
                        SystemManager.getConfig().setDescriptionRating(descRating.isSelected());
                        SystemManager.updateUI();
                    } else {
                        descRating.setSelected(true);
                    }
                }
            });

        stars.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setStars(stars.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });
        totalStrength.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setTotalStrength(totalStrength.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });
        checkName.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setCheckTeamName(checkName.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });

        squad.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setSquad(squad.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });
        smartSquad.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setSmartSquad(smartSquad.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });

        loddarStats.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setLoddarStats(loddarStats.isSelected());
                    SystemManager.updateUI();
                    SystemManager.updateUI();
                }
            });

        myLineup.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setLineup(myLineup.isSelected());
                    SystemManager.updateUI();
                }
            });

        tacticDetail.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setTacticDetail(tacticDetail.isSelected());
                    SystemManager.updateUI();
                }
            });

        unavailable.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setShowUnavailable(unavailable.isSelected());
                    SystemManager.updateUI();
                }
            });
        playerInfo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setShowPlayerInfo(playerInfo.isSelected());
                    SystemManager.updateUI();
                }
            });
        mixedLineup.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SystemManager.getConfig().setMixedLineup(mixedLineup.isSelected());
                    SystemManager.updateUI();
                }
            });
    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        initListeners();

        JPanel mainPanel = Commons.getModel().getGUI().createImagePanel();

        mainPanel.setLayout(new GridLayout(12, 1));
        mainPanel.setOpaque(false);
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.MyLineup"), myLineup));
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.TacticDetail"),
                                  tacticDetail));
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.MixedLineup"), mixedLineup));
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.NumericRatings"),
                                  numberRating));
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.DescriptionRatings"),
                                  descRating));

        //mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.ShowUnavailable"), unavailable));
        mainPanel.add(createPanel(PluginProperty.getString("RecapPanel.Stars"), stars));
        mainPanel.add(createPanel(Commons.getModel().getLanguageString("Gesamtstaerke"),
                                  totalStrength));
        mainPanel.add(createPanel(PluginProperty.getString("Squad"), squad));
        mainPanel.add(createPanel(PluginProperty.getString("SmartSquad"), smartSquad));
        mainPanel.add(createPanel("LoddarStats", loddarStats));
        mainPanel.add(createPanel("Player Info", playerInfo));
        mainPanel.add(createPanel(PluginProperty.getString("SettingPanel.CheckName"), checkName));

        setLayout(new BorderLayout());
        setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
    }
}
