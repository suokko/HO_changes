package hoplugins.feedback.ui.component;

import hoplugins.commons.utils.PluginProperty;
import hoplugins.feedback.dao.FeedbackSettingDAO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class OptionPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

//	private JTextArea jTextLabel = new JTextArea(PluginProperty.getString("StartingPanel.0") + " "
//										+ PluginProperty.getString("StartingPanel.1") + " "
//										+ PluginProperty.getString("StartingPanel.2") + " "
//										+ PluginProperty.getString("StartingPanel.3") + " "
//										+ PluginProperty.getString("StartingPanel.4"));
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3050637321398637183L;
	private JCheckBox[] automatic = new JCheckBox[3];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public OptionPanel() {
        super();
        for (int type=0; type<automatic.length; type++) {
        	automatic[type] = new JCheckBox();
        	automatic[type].setSelected(FeedbackSettingDAO.isAutomatic(type));
        	automatic[type].setOpaque(false);
        }
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
    private JPanel createPanel(String string, JCheckBox checkBox) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        final JPanel innerPanel = new JPanel();

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
    	for (int type=0; type < automatic.length; type++) {
    		automatic[type].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	for (int type=0; type < automatic.length; type++) {
                		if (e.getSource().equals(automatic[type]))
                			FeedbackSettingDAO.setAutomatic(type, automatic[type].isSelected());
                	}
                }
            });
    	}
    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        initListeners();

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(automatic.length+2, 1));
        mainPanel.setOpaque(false);
        mainPanel.add (new JLabel()); // empty
        for (int type=0; type < automatic.length; type++) {
        	mainPanel.add(createPanel(PluginProperty.getString("Option.Auto") + " "
        			+ PluginProperty.getString("FeedbackType."+type), 
        			automatic[type]));
        }
        mainPanel.add (new JLabel()); // empty
        setLayout(new BorderLayout());
        setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
    }
}
