package ho.module.nthrf;

import ho.core.file.ExampleFileFilter;
import ho.core.file.xml.XMLManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IDownloadHelper;
import plugins.IXMLParser;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.net.MyConnector;

class NthrfUtil {

    /**
     * TODO
     * @return success of the operation
     */
    static boolean createNthrf(long teamId) {
        try {
            IXMLParser xp = XMLManager.instance();
            IDownloadHelper dh = MyConnector.instance();
            NthrfConvertXml2Hrf x2h = new NthrfConvertXml2Hrf();
            x2h.createHrf(teamId, dh, xp);
            JFileChooser fileChooser = new JFileChooser();

            final String fname = "/nt_"+teamId+"_"+new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date())+".hrf";
            final File path = new File(gui.UserParameter.instance().hrfImport_HRFPath);
            File file = new File(gui.UserParameter.instance().hrfImport_HRFPath + File.separator + fname);
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser.setDialogTitle(HOVerwaltung.instance().getLanguageString("Speichern"));

            fileChooser.setFileFilter(new ExampleFileFilter("hrf"));
            try {
                if (path.exists() && path.isDirectory()) {
                    fileChooser.setCurrentDirectory(path);
                }
            } catch (Exception e) {
            }

            fileChooser.setSelectedFile(file);

            final int returnVal = fileChooser.showSaveDialog(MainPanel.getInstance());

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            } else {
                file = null;
            }

            // TODO: overwrite question on existing file:
//            if (file.exists()) {
//				...
//			}

            if (file != null) {
                x2h.writeHRF(file);
                debug("wrote file " + (file != null ? file.getAbsolutePath() : "null"));
                // save folder setting
                gui.UserParameter.instance().hrfImport_HRFPath = file.getParentFile().getAbsolutePath();
            } else {
                debug("Could not write file, nothing selected!");
                return false;
            }

            return true;
        } catch (Exception e) {
            debug("Error: " + e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the teamIDs and names of all national teams of the authenticated manager.
     */
    public static List<String[]> getNtTeams() {
    	List<String[]> ret = new ArrayList<String[]>();
        try {
            String xmldata = MyConnector.instance().getHattrickXMLFile("/chppxml.axd?file=team");
            final Document doc = XMLManager.instance().parseString(xmldata);
            Element ele = null;
            Element root = null;
            Element nt = null;

            if (doc == null) {
                return null;
            }
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("User").item(0);
            root = (Element) root.getElementsByTagName("NationalTeamCoach").item(0);
            try {
            	int length = root.getElementsByTagName("NationalTeam").getLength();
            	for (int m=0; m<length; m++) {
            		nt = (Element) root.getElementsByTagName("NationalTeam").item(m);
            		ele = (Element) nt.getElementsByTagName("NationalTeamID").item(0);
            		Element eName = (Element) nt.getElementsByTagName("NationalTeamName").item(0);
            		String tid =XMLManager.instance().getFirstChildNodeValue(ele);
            		String name = XMLManager.instance().getFirstChildNodeValue(eName);
            		if (tid != null && tid.length() > 0) {
						ret.add(new String[] { tid, name });
					}
            	}
            } catch (Exception x) {
                /* nothing */
            	x.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Get the trainer.
     */
    public static NtPlayer getTrainer(NtPlayersParser players) {
        try {
            for (Iterator<NtPlayer> i = players.getAllPlayers().iterator(); i.hasNext(); ) {
                NtPlayer pl = i.next();
                if (pl.isTrainer()) {
                    return pl;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the player in a lineup according to his position code.
     */
    public static NtPlayerPosition getPlayerPositionByRole(NtLineupParser lineup, int roleId) {
        for (Iterator<NtPlayerPosition> i=lineup.getAllPlayers().iterator(); i.hasNext(); ) {
            NtPlayerPosition pp = i.next();
            if (roleId == pp.getRoleId()) {
                return pp;
            }
        }
        return null;
    }

    /**
     * Get the countryId according to a nativeLeagueId of a player.
     */
    public static int getCountryId(int nativeLeagueId, HashMap<Integer, Integer> countryMapping) {
        int ret = countryMapping.get(Integer.valueOf(nativeLeagueId)).intValue();
        if (ret > 0) {
            return ret;
        }
        return nativeLeagueId;
    }

    private static void debug(String txt) {
        System.out.println("Nthrf: " + txt);
    }
}
