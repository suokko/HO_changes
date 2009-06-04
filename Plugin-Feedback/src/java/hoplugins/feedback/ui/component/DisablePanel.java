package hoplugins.feedback.ui.component;

import hoplugins.commons.ui.InfoPanel;
import hoplugins.commons.utils.PluginProperty;

public class DisablePanel extends InfoPanel {
	//~ Static fields/initializers -----------------------------------------------------------------

	private static String[] messages = {
			PluginProperty.getString("DisablePanel.0"),
			PluginProperty.getString("DisablePanel.1"),
			PluginProperty.getString("DisablePanel.2"),
			PluginProperty.getString("DisablePanel.3") };

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Constructs a new instance.
	 */
	public DisablePanel() {
		super(messages);
	}
}
