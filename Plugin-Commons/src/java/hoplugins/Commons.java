// %1897510634:hoplugins%
package hoplugins;

import java.io.File;

import plugins.IHOMiniModel;
import plugins.ILib;
import plugins.IOfficialPlugin;


/**
 * Common Utility Plugin
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Commons implements ILib, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** HO Model Reference */
    private static IHOMiniModel model;

    /** Plugin version identifier */
    private static final double PLUGIN_VERSION = 1.073d;

    /** TODO Missing Parameter Documentation */
    private static final String PLUGIN_NAME = "Commons";

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gets the HO data model
     *
     * @return IHOMiniModel instance
     */
    public static IHOMiniModel getModel() {
        return model;
    }

    /**
     * Return a name
     *
     * @return the name
     */
    public final String getName() {
        return PLUGIN_NAME + " " + PLUGIN_VERSION;
    }

    /**
     * Returns the PluginId
     *
     * @return the plugin id
     */
    public final int getPluginID() {
        return 24;
    }

    /**
     * Returns the plugin name
     *
     * @return the plugin name
     */
    public final String getPluginName() {
        return PLUGIN_NAME + " Library";
    }

    /**
     * {@inheritDoc}
     */
    public final File[] getUnquenchableFiles() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public final double getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    public final void start(IHOMiniModel hoModel) {
        model = hoModel;
    }
}
