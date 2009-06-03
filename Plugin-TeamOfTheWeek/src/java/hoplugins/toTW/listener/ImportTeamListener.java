// %3021849267:hoplugins.toTW.listener%
/*
 * Created on 17-dic-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoplugins.toTW.listener;

import hoplugins.Commons;
import hoplugins.TotW;

import hoplugins.toTW.DBManager;
import hoplugins.toTW.dao.TeamColorDAO;
import hoplugins.toTW.vo.TeamDetail;

import org.w3c.dom.Document;

import plugins.IXMLParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImportTeamListener implements ActionListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param arg0 TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent arg0) {
        List teams = DBManager.getTeamList(TotW.getWeek(), TotW.getSeason());

        for (Iterator iter = teams.iterator(); iter.hasNext();) {
            String id = (String) iter.next();

            try {
                TeamColorDAO.store(downloadTeam(Integer.parseInt(id)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TotW.forceRefresh(true);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param teamId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Method Exception Documentation
     */
    public static TeamDetail downloadTeam(int teamId) throws Exception {
        TeamDetail td = new TeamDetail();
        String xml = Commons.getModel().getDownloadHelper().getHattrickXMLFile("/common/chppxml.axd?file=team&teamID="
                                                                               + teamId);
        IXMLParser parser = Commons.getModel().getXMLParser();

        Document dom = parser.parseString(xml);
        Document teamDocument = dom.getElementsByTagName("Team").item(0).getOwnerDocument();
        String teamName = teamDocument.getElementsByTagName("TeamName").item(0).getFirstChild()
                                      .getNodeValue();

        td.setTeamId(teamId);
        td.setName(teamName);

        String dress = teamDocument.getElementsByTagName("Dress").item(0).getFirstChild()
                                   .getNodeValue();

        td.setShirt(dress.charAt(0) + "" + dress.charAt(1) + dress.charAt(2));
        td.setPants("" + dress.charAt(3) + dress.charAt(4));
        td.setSocks("" + dress.charAt(5) + dress.charAt(6));
        return td;
    }
}
