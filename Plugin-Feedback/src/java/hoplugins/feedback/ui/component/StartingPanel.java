package hoplugins.feedback.ui.component;

import hoplugins.commons.ui.InfoPanel;
import hoplugins.commons.utils.PluginProperty;


/**
 * A Panel to inform the user of the new feature
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class StartingPanel extends InfoPanel {
	static final long serialVersionUID = 1;
	
    //~ Static fields/initializers -----------------------------------------------------------------

    private static String[] messages = {
    	PluginProperty.getString("StartingPanel.0"),
    	PluginProperty.getString("StartingPanel.1"),
    	PluginProperty.getString("StartingPanel.2"),
    	PluginProperty.getString("StartingPanel.3"),
    	PluginProperty.getString("StartingPanel.4"),
    	PluginProperty.getString("StartingPanel.5")
                                       };

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public StartingPanel() {
        super(messages);
    }
}
