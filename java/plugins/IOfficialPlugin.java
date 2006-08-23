// %1127326956525:plugins%
/*
 * Created on 28.09.2004
 *
 */
package plugins;

import java.io.File;


/**
 * DOCUMENT ME!
 *
 * @author tdietz This is the interface all official hoplugins needs to implement.
 */
public interface IOfficialPlugin {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * A unique pluginID. You will get the ID from www.hoplugins.de The ID identify your plugin for
     * HO!
     *
     * @return int
     */
    public int getPluginID();

    /**
     * The original pluginname without version. The pluginname will display on the updatedialog
     *
     * @return String
     */
    public String getPluginName();

    /**
     * The files the PluginUpdater is not allowed to delete. for example config.xml.
     *
     * @return java.io.File []
     */
    public File[] getUnquenchableFiles();

    /**
     * The version of the plugin
     *
     * @return double
     */
    public double getVersion();
}
