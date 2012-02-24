// %1193228360:hoplugins.teamAnalyzer.ui.component%
package ho.module.playeranalysis;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



class SettingPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------
	public static ModuleConfig config = ModuleConfig.instance();
    /**
	 * 
	 */
	private static final long serialVersionUID = -5721035453587068724L;
	//private JCheckBox gamesCheckbox = new JCheckBox();
    private JCheckBox skillsCheckbox = new JCheckBox();
    private JCheckBox experienceCheckbox = new JCheckBox();


    SettingPanel() {
        super();
        skillsCheckbox.setSelected(config.getBoolean(PlayerAnalysisModule.SHOW_PLAYERCOMPARE));
        skillsCheckbox.setOpaque(false);
        experienceCheckbox.setSelected(config.getBoolean(PlayerAnalysisModule.SHOW_EXPERIENCE));
        experienceCheckbox.setOpaque(false);
//        gamesCheckbox.setSelected(config.getBoolean(PlayerAnalysisModule.SHOW_GAMEANALYSIS));
//        gamesCheckbox.setOpaque(false);
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
        JPanel panel = new ImagePanel();

        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        JPanel innerPanel = new ImagePanel();

        //innerPanel.setLayout(new BorderLayout());
        innerPanel.add(checkBox);
        innerPanel.add(new JLabel(string, SwingConstants.LEFT));
        innerPanel.setOpaque(false);
        panel.add(innerPanel, BorderLayout.WEST);

        return panel;
    }

    /**
     * Initialize listeners
     */
    private void initListeners() {
        skillsCheckbox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    	config.setBoolean(PlayerAnalysisModule.SHOW_PLAYERCOMPARE,skillsCheckbox.isSelected());
                }
            });

//        gamesCheckbox.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                	config.setBoolean(PlayerAnalysisModule.SHOW_GAMEANALYSIS,gamesCheckbox.isSelected());
//                }
//            });


        experienceCheckbox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	config.setBoolean(PlayerAnalysisModule.SHOW_EXPERIENCE,experienceCheckbox.isSelected());
                }
            });

    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        initListeners();

        JPanel mainPanel = new ImagePanel();

        mainPanel.setLayout(new GridLayout(12, 1));
        mainPanel.setOpaque(false);
        mainPanel.add(createPanel(HOVerwaltung.instance().getLanguageString("PlayerCompare"),
                                  skillsCheckbox));
        mainPanel.add(createPanel("Erfahrung", experienceCheckbox));
//        mainPanel.add(createPanel(HOVerwaltung.instance().getLanguageString("Spiele"), gamesCheckbox));

        setLayout(new BorderLayout());
        setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
    }
}
