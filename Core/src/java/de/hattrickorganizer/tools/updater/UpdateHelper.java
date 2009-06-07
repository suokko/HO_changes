// %4175459228:de.hattrickorganizer.tools.updater%
package de.hattrickorganizer.tools.updater;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.hattrickorganizer.net.MyConnector;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class UpdateHelper {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static UpdateHelper m_clInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UpdateHelper object.
     */
    private UpdateHelper() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Basic functions for updating anything
     *
     * @return UpdateHelper
     */
    public static UpdateHelper instance() {
        if (m_clInstance == null) {
            m_clInstance = new UpdateHelper();
        }

        return m_clInstance;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param file TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws ParserConfigurationException TODO Missing Method Exception Documentation
     * @throws IOException TODO Missing Method Exception Documentation
     * @throws SAXException TODO Missing Method Exception Documentation
     */
    public final Document getDocument(File file)
      throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param elements TODO Missing Method Parameter Documentation
     * @param list TODO Missing Method Parameter Documentation
     * @param nonVisibles TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected ArrayList getWebPlugins(NodeList elements, ArrayList list, ArrayList nonVisibles) {
        HPPluginInfo tmp = null;

        for (int i = 0; i < elements.getLength(); i++) {
            if (elements.item(i) instanceof Element) {
                Element element = (Element) elements.item(i);

                if (element.getTagName().equals("plugin")) {
                    tmp = HPPluginInfo.getInstance(element.getChildNodes());

                    if (tmp.isVisible()) {
                        list.add(tmp);
                    } else {
                        nonVisibles.add(tmp);
                    }
                }
                 // if PLUGIN
            }
             // elements
        }
         // for

        return list;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param urlName TODO Missing Method Parameter Documentation
     * @param targetFile TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean download(String urlName, File targetFile) {
        int data;
        boolean showDialog = false;

        try {
            FileOutputStream outStream = new FileOutputStream(targetFile);

            if ((gui.UserParameter.instance().LoginName == null)
                || (gui.UserParameter.instance().LoginName.length() == 0)) {
                showDialog = true;
            }

            InputStream in = MyConnector.instance().getFileFromWeb(urlName, showDialog, true);
            BufferedOutputStream out = new BufferedOutputStream(outStream);

            while (true) {
                data = in.read();

                if (data == -1) {
                    break;
                }

                out.write(data);
            }

            out.flush();
            out.close();
            in.close();
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            //handleException(e,"Can´t download "+urlName+"\n ");
            return false;
        }

        return true;
    }


}
