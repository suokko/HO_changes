// %3815340815:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.commons.ui.InfoPanel;

import hoplugins.teamAnalyzer.SystemManager;


/**
 * An about panel that shows info about the plugin
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class AboutPanel extends InfoPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 2793573846247148215L;
	private static String[] messages = new String[4];

    static {
        messages[0] = SystemManager.getPlugin().getPluginName();
        messages[1] = "Version: " + SystemManager.getPlugin().getVersion() + " - 01/01/2008";
        messages[2] = "By Draghetto";
        messages[3] = "Copyright (c) 2004-2008";
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public AboutPanel() {
        super(messages);
    }
}
