// %2712983001:de.hattrickorganizer.tools.updater%
/*
 * Created on 21.07.2004
 *
 */
package de.hattrickorganizer.tools.updater;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import plugins.IOfficialPlugin;


/**
 * Value-Object of information about the plugins on hoplugins.de
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
final class HPPluginInfo {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static String TAG_VERSION = "version";
    private static String TAG_FILE = "datei";
    private static String TAG_NAME = "name";
    private static String TAG_PLUGIN_ID = "plugin_id";
    private static String TAG_HOVERSION = "hoversion";
    private static String TAG_VISIBLE = "v";
    private static String TAG_UPDATETEXT = "updatetext";

    //~ Instance fields ----------------------------------------------------------------------------

    private IOfficialPlugin officialPlugin;
    private String className;
    private String hoversion = "";
    private String name;
    private String updateText = "";
    private String zipFileName;
    private boolean visible = true;
    private double version;
    private int pluginId;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String toString() {
        if (name != null) {
            return name;
        }

        return "?";
    }

    /**
     * DOCUMENT ME!
     *
     * @return String
     */
    protected String getClassName() {
        return className;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the hoversion.
     */
    protected String getHoversion() {
        return hoversion;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the hoversion as double.
     */
    protected double getHoversionAsDouble() {
        if (hoversion.equals("-")) {
            return 0.0d;
        }

        return Double.parseDouble(hoversion);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param elements TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static HPPluginInfo getInstance(NodeList elements) {
        HPPluginInfo hpPluginInfo = new HPPluginInfo();

        try {
            for (int i = 0; i < elements.getLength(); i++) {
                if (elements.item(i) instanceof Element) {
                    Element element = (Element) elements.item(i);
                    Text txt = (Text) element.getFirstChild();

                    if (txt != null) {
                        if (element.getTagName().equals(TAG_PLUGIN_ID)) {
                            hpPluginInfo.setPluginId(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_NAME)) {
                            hpPluginInfo.setName(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_FILE)) {
                            hpPluginInfo.setZipFileName(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_VERSION)) {
                            hpPluginInfo.setVersion(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_HOVERSION)) {
                            hpPluginInfo.setHoversion(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_UPDATETEXT)) {
                            hpPluginInfo.setUpdateText(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_VISIBLE)) {
                            hpPluginInfo.setVisible(txt.getData().trim());
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }

        return hpPluginInfo;
    }

    /**
     * DOCUMENT ME!
     *
     * @return String
     */
    protected String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param officialPlugin The officialPlugin to set.
     */
    protected void setOfficialPlugin(IOfficialPlugin officialPlugin) {
        this.officialPlugin = officialPlugin;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the officialPlugin.
     */
    protected IOfficialPlugin getOfficialPlugin() {
        return officialPlugin;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the pluginId.
     */
    protected int getPluginId() {
        return pluginId;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the updateText.
     */
    protected String getUpdateText() {
        return updateText;
    }

    /**
     * DOCUMENT ME!
     *
     * @param version The version to set.
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected void setVersion(String version) throws Exception {
        this.version = Double.parseDouble(version);
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the version.
     */
    protected double getVersion() {
        return version;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setVisible(String value) {
        visible = value.equals("0") ? true : false;
    }

    /**
     * Returns if the plugin displays in the dialogs
     *
     * @return boolean
     */
    protected boolean isVisible() {
        return visible;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    protected String getZipFileName() {
        return zipFileName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param hoversion The hoversion to set.
     */
    private void setHoversion(String hoversion) {
        this.hoversion = hoversion;
    }

    /**
     * DOCUMENT ME!
     *
     * @param string
     */
    private void setName(String string) {
        name = string;
    }

    /**
     * DOCUMENT ME!
     *
     * @param pluginId The pluginId to set.
     */
    private void setPluginId(String pluginId) {
        this.pluginId = Integer.parseInt(pluginId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param updateText The updateText to set.
     */
    private void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    /**
     * DOCUMENT ME!
     *
     * @param string
     */
    private void setZipFileName(String string) {
        zipFileName = string;
    }
}
