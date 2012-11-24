// %3094578472:de.hattrickorganizer.tools.updater%
/*
 * Created on 21.07.2004
 *
 */
package ho.tool.updater;

import ho.core.util.HOLogger;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;



/**
 * Value-Object about Language-files on hoplugins.de
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
final class HPLanguageInfo {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private static final String TAG_FILE = "datei";

    /** TODO Missing Parameter Documentation */
    private static final String TAG_ID = "lang_id";

    /** TODO Missing Parameter Documentation */
    private static final String TAG_VERSION = "version";

    //~ Instance fields ----------------------------------------------------------------------------

    private String filename;
    private String version;
    private int id;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public String toString() {
        if (version == null) {
            return "-";
        }

        return version;
    }

    /**
     * DOCUMENT ME!
     *
     * @return String
     */
    protected String getFilename() {
        return filename;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected int getId() {
        return id;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected String getVersion() {
        return version;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param elements TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static HPLanguageInfo instance(NodeList elements) {
        HPLanguageInfo hpPluginInfo = new HPLanguageInfo();

        try {
            for (int i = 0; i < elements.getLength(); i++) {
                if (elements.item(i) instanceof Element) {
                    Element element = (Element) elements.item(i);
                    Text txt = (Text) element.getFirstChild();

                    if (txt != null) {
                        if (element.getTagName().equals(TAG_FILE)) {
                            hpPluginInfo.setFilename(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_ID)) {
                            hpPluginInfo.setId(txt.getData().trim());
                        }

                        if (element.getTagName().equals(TAG_VERSION)) {
                            hpPluginInfo.setVersion(txt.getData().trim());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            HOLogger.instance().log(HPLanguageInfo.class,ex);
        }

        return hpPluginInfo;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param filename TODO Missing Method Parameter Documentation
     */
    private void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param newId TODO Missing Method Parameter Documentation
     *
     * @throws Exception TODO Missing Method Exception Documentation
     */
    private void setId(String newId) throws Exception {
        id = Integer.parseInt(newId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param version TODO Missing Method Parameter Documentation
     */
    private void setVersion(String version) {
        this.version = version;
    }
}
