// %1126721330166:hoplugins.transfers.ui.component%
package hoplugins.transfers.ui.component;

import hoplugins.commons.ui.InfoPanel;
import hoplugins.commons.utils.PluginProperty;


/**
 * A Panel to inform the user of disabling the feature effect
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DisablePanel extends InfoPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -8375936917139631915L;
	private static String[] messages = {
                                           PluginProperty.getString("DisablePanel.0"), //$NON-NLS-1$
    PluginProperty.getString("DisablePanel.1"), //$NON-NLS-1$
    PluginProperty.getString("DisablePanel.2"), //$NON-NLS-1$
    PluginProperty.getString("DisablePanel.3")
                                       }; 

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public DisablePanel() {
        super(messages);
    }
}
