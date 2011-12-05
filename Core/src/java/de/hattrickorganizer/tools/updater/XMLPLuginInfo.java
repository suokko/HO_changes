// %2049798631:de.hattrickorganizer.tools.updater%
/*
 * Created on 13.04.2005
 *
 */
package de.hattrickorganizer.tools.updater;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * Value-Object about the plugin.xml file
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
final class XMLPLuginInfo {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private static final String TAG_PLUGIN_ID = "pluginID";

    /** TODO Missing Parameter Documentation */
    private static final String TAG_MYPLUGIN_ID = "mypluginID";

    /** TODO Missing Parameter Documentation */
    private static final String TAG_DEPENDENCY = "dependent";

    //~ Instance fields ----------------------------------------------------------------------------

    private ArrayList<Integer> dependPluginIDs;
    private HPPluginInfo hppPluginInfo;
    private int pluginID;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new XMLPLuginInfo object.
     */
    private XMLPLuginInfo() {
        dependPluginIDs = new ArrayList<Integer>();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final ArrayList<Integer> getDependPluginIDs() {
        return dependPluginIDs;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hppPluginInfo TODO Missing Method Parameter Documentation
     */
    protected final void setHppPluginInfo(HPPluginInfo hppPluginInfo) {
        this.hppPluginInfo = hppPluginInfo;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final HPPluginInfo getHppPluginInfo() {
        return hppPluginInfo;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param elements TODO Missing Method Parameter Documentation
     * @param name TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static final XMLPLuginInfo getInstance(NodeList elements, String name) {
        XMLPLuginInfo tmpPluginInfo = new XMLPLuginInfo();

        try {
            for (int i = 0; i < elements.getLength(); i++) {
                if (elements.item(i) instanceof Element) {
                    Element element = (Element) elements.item(i);
                    Text txt = (Text) element.getFirstChild();

                    if (txt != null) {
                        if (element.getTagName().equals(TAG_MYPLUGIN_ID)) {
                            tmpPluginInfo.setPluginID(Integer.parseInt(txt.getData().trim()));
                        }

                        if (element.getTagName().equals(TAG_DEPENDENCY)) {
                            initDependencies(tmpPluginInfo, element.getChildNodes(), name);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Can´t parse XML-File in directory " + name);
        }

        return tmpPluginInfo;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pluginID TODO Missing Method Parameter Documentation
     */
    protected final void setPluginID(int pluginID) {
        this.pluginID = pluginID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final int getPluginID() {
        return pluginID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tmpPluginInfo TODO Missing Method Parameter Documentation
     * @param elements TODO Missing Method Parameter Documentation
     * @param name TODO Missing Method Parameter Documentation
     */
    private static final void initDependencies(XMLPLuginInfo tmpPluginInfo, NodeList elements,
                                               String name) {
        try {
            for (int i = 0; i < elements.getLength(); i++) {
                if (elements.item(i) instanceof Element) {
                    Element element = (Element) elements.item(i);
                    Text txt = (Text) element.getFirstChild();

                    if (txt != null) {
                        if (element.getTagName().equals(TAG_PLUGIN_ID)) {
                            tmpPluginInfo.getDependPluginIDs().add(new Integer(txt.getData().trim()));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Can´t parse XML-File in directory " + name);
        }
    }
}
