// %1127326826790:plugins%
/*
 * IDownloadHelper.java
 *
 * Created on 29. März 2004, 07:41
 */
package plugins;

import java.io.IOException;
import java.io.InputStream;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * Helper to download any webpage or a specific hattrick xml file
 */
public interface IDownloadHelper {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * gets an Input Stream for a usal web file (not from hattrick )
     *
     * @param url url to file http://www.natom.de
     * @param displaysettingsScreen launch up Proxy Screen so user can set neceessary Options
     *
     * @return Inputstream to file
     *
     * @throws IOException TODO Missing Constructuor Exception Documentation
     */
    public InputStream getFileFromWeb(String url, boolean displaysettingsScreen)
      throws IOException;

    /**
     * downloads an xml File from hattrick. 
     * Syntax is updated with OAuth change, but old version should still work.
     *
     * @param file ex.: "?file=leaguedetails&leagueLevelUnitID=100"
     *
     * @return the complete file as String
     *
     * @throws IOException TODO Missing Constructuor Exception Documentation
     */
    public String getHattrickXMLFile(String file) throws IOException;

    /**
     * donwloads a usal web page (not from hattrick )
     *
     * @param ur url to webpage http://www.natom.de
     * @param displaysettingsScreen TODO Missing Constructuor Parameter Documentation
     *
     * @return website as String
     *
     * @throws IOException TODO Missing Constructuor Exception Documentation
     */
    public String getUsalWebPage(String ur, boolean displaysettingsScreen)
      throws IOException;

    public String getUsalWebPage(String ur, boolean displaysettingsScreen, boolean shortTimeOut)
    throws IOException;

    /**
     * gets regionID for a team by teamID
     *
     * @param teamID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String fetchRegionID(int teamID);
}
