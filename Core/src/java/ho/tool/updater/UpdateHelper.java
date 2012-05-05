// %4175459228:de.hattrickorganizer.tools.updater%
package ho.tool.updater;

import ho.core.net.MyConnector;
import ho.core.util.HOLogger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;



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
     * Download contents of a url into a target file.
     */
    public boolean download(String urlName, File targetFile) {
        int data;
        try {
            FileOutputStream outStream = new FileOutputStream(targetFile);
            InputStream in = MyConnector.instance().getFileFromWeb(urlName, true);
            BufferedOutputStream out = new BufferedOutputStream(outStream);
            while (true) 
            {
                data = in.read();
                if (data == -1)
                    break;
                out.write(data);
            }
            out.flush();
            out.close();
            in.close();
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
        	HOLogger.instance().log(getClass(), "Error downloading from '" + urlName + "': " + e);
            return false;
        }
        return true;
    }
}
