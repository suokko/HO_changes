// %1126721330229:hoplugins.transfers.ui.component%
package hoplugins.transfers.ui.component;

import hoplugins.commons.ui.InfoPanel;
import hoplugins.commons.utils.PluginProperty;


/**
 * A Panel to inform the user of the new feature
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class StartingPanel extends InfoPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 2856979077539371969L;
	private static String[] messages = {
                                           PluginProperty.getString("StartingPanel.0"), //$NON-NLS-1$
    PluginProperty.getString("StartingPanel.1"), //$NON-NLS-1$
    PluginProperty.getString("StartingPanel.2"), //$NON-NLS-1$
    PluginProperty.getString("StartingPanel.3"), //$NON-NLS-1$
    PluginProperty.getString("StartingPanel.4") //$NON-NLS-1$
                                       };

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public StartingPanel() {
        super(messages);
    }
}
