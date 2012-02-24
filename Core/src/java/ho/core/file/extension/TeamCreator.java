package ho.core.file.extension;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.model.HOMiniModel;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IBasics;
import plugins.IMatchKurzInfo;
import plugins.ISpielePanel;
import plugins.IXtraData;

public class TeamCreator extends XMLCreator {

	private static int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

	protected static void extractActual() {
		if (teamId == 0) {
			return;
		}
		File dir = new File("Info/" + teamId);
		if (!dir.exists()) {
			dir.mkdirs();
		}


		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("team");
			doc.appendChild(root);			
			IBasics basics = HOMiniModel.instance().getBasics();
			IXtraData xtra = HOMiniModel.instance().getXtraDaten();
						
			root.appendChild(createNode(doc,"teamid", basics.getTeamId()+""));
			root.appendChild(createNode(doc,"teamname", basics.getTeamName()+""));
			root.appendChild(createNode(doc,"manager", basics.getManager()+""));
			root.appendChild(createNode(doc,"lastUpdate", basics.getDatum()+""));
			root.appendChild(createNode(doc,"economUpdate", xtra.getEconomyDate()+""));
			root.appendChild(createNode(doc,"trainingUpdate", xtra.getTrainingDate()+""));
			root.appendChild(createNode(doc,"seriesUpdate", xtra.getSeriesMatchDate()+""));
			root.appendChild(createNode(doc,"cupUpdate", getNextCupDate()+""));
									
			File playerFile = new File(dir, "team.xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(playerFile));
			bw.write(XMLManager.instance().getXML(doc));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(TeamCreator.class, e);
		}
	}

	public static Timestamp getNextCupDate() {
		IMatchKurzInfo[] cupMatches = DBManager.instance().getMatchesKurzInfo(teamId,
																			ISpielePanel.NUR_EIGENE_POKALSPIELE,
																			false);
																			
		Timestamp time = getFirstUnplayed(cupMatches);
		if (time!= null) {
			return time;																			
		}
		
		IMatchKurzInfo[] friendlyMatches = DBManager.instance().getMatchesKurzInfo(teamId,
																				 ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE,
																				 false);

		return getFirstUnplayed(friendlyMatches);
	}
	
	public static Timestamp getFirstUnplayed(IMatchKurzInfo[] matches) {

		for (int i = 0; i < matches.length; i++) {
			IMatchKurzInfo match = matches[i];

			if (match.getMatchStatus() != IMatchKurzInfo.FINISHED) {
				return match.getMatchDateAsTimestamp();
			}
		}
		return null;
	}
}
