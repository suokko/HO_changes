package hoplugins;

import hoplugins.commons.utils.PluginProperty;
import hoplugins.nthrf.ui.NthrfMenu;
import hoplugins.nthrf.util.NthrfUtil;

import java.io.File;

import javax.swing.JWindow;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

/**
 * HO! plugin to create an HRF file with data from a national team.
 * It can only be used by elected NT managers.
 */
public class Nthrf implements IPlugin, IRefreshable, IOfficialPlugin {
	// plugin name
	public static final String PLUGIN_NAME = "Nthrf";
	private static final String PLUGIN_PACKAGE = "Nthrf";
	private static IHOMiniModel miniModel;

	// plugin version constant
	public static final double PLUGIN_VERSION = 0.1;
	// plugin id
	private static final int PLUGIN_ID = 43;
	// debug mode
	public static boolean DEBUG = false;

	public final String getName() {
		return getPluginName() + " " + getVersion();
	}

	public final int getPluginID() {
		return PLUGIN_ID;
	}

	public final String getPluginName() {
		return PLUGIN_NAME;
	}

	public final File[] getUnquenchableFiles() {
		return null;
	}

	public final double getVersion() {
		return PLUGIN_VERSION;
	}

	/**
	 * This method is called when new data is available
	 * (i.e. after a download, after setting options, after sub skill recalculation...)
	 *
	 * It creates the FeedbackObject lists with the upload data
	 *
	 * The lists are rebuild only, if a new HRF is available
	 */
	public final void refresh() {
		System.out.println("Nthrf: refresh()");
		miniModel = Commons.getModel();
		final JWindow waitWindow = miniModel.getGUI().createWaitDialog(miniModel.getGUI().getOwner4Dialog());
		waitWindow.show();
		//Timestamp curHrfDate = miniModel.getBasics().getDatum();
		//System.out.println("Nthrf: " + curHrfDate);
		waitWindow.hide();
		waitWindow.dispose();
	}


	/**
	 * This method is called on HO startup
	 */
	public final void start(IHOMiniModel hoMiniModel) {
		setMiniModel(hoMiniModel);
		PluginProperty.loadPluginProperties(PLUGIN_PACKAGE);
		//hoMiniModel.getGUI().addOptionPanel(getPluginName(), new OptionPanel());
		hoMiniModel.getGUI().addMenu(NthrfMenu.createMenu(hoMiniModel));
		//hoMiniModel.getGUI().registerRefreshable(this);
	}

	public static IHOMiniModel getMiniModel() {
		return miniModel;
	}

	public static void setMiniModel(IHOMiniModel miniModel) {
		Nthrf.miniModel = miniModel;
	}

	public boolean createNthrf(long teamId) {
		return NthrfUtil.createNthrf(teamId, miniModel);
	}
}
