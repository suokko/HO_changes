package ho.core.file.extension;

import ho.core.file.xml.XMLManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.SpielerPosition;
import ho.core.util.HOLogger;
import ho.module.lineup.Lineup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LineupCreator extends XMLCreator {

	private static int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

	protected static void deleteLineup(String lineupName) {
		if (teamId == 0) {
			return;
		}
		File f = new File("Info/" + teamId + "/Lineups/" + lineupName + ".xml");
		f.delete();

	}

	protected static void extractLineup(String lineupName) {
		if (teamId == 0) {
			return;
		}
		Lineup lineup = HOVerwaltung.instance().getModel().getAufstellung();
		File dir = new File("Info/" + teamId + "/Lineups");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();
			Element root = doc.createElement("lineup");
			doc.appendChild(root);

			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.keeper));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.rightBack));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.leftCentralDefender));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.middleCentralDefender));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.rightCentralDefender));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.leftBack));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.rightWinger));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.rightInnerMidfield));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.centralInnerMidfield));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.leftInnerMidfield));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.leftWinger));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.rightForward));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.centralForward));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.leftForward));

			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.substKeeper));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.substDefender));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.substInnerMidfield));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.substWinger));
			addLineupSpot(root, lineup.getPositionById(ISpielerPosition.substForward));

			Element penaltyTakers = doc.createElement("penaltyTakers");
			root.appendChild(penaltyTakers);

			List<SpielerPosition> list = lineup.getPenaltyTakers();
					for (SpielerPosition pos : list) {
				penaltyTakers.appendChild(createNode(doc, "id", String.valueOf(pos.getSpielerId())));
			}
			root.appendChild(createNode(doc, "captain", String.valueOf(lineup.getKapitaen())));
			root.appendChild(createNode(doc, "kicker", String.valueOf(lineup.getKicker())));
			root.appendChild(createNode(doc, "tacticType", String.valueOf(lineup.getTacticType())));
			root.appendChild(createNode(doc, "matchType", String.valueOf(lineup.getAttitude())));

			File f = new File(dir, lineupName + ".xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(XMLManager.getXML(doc));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(LineupCreator.class, e);
		}
	}

	private static void addLineupSpot(Element root, SpielerPosition position) throws IOException {
		Document doc = root.getOwnerDocument();
		Element main = doc.createElement("position");
		root.appendChild(main);

		if (position == null) {
			main.appendChild(createNode(doc, "code", "0"));
			main.appendChild(createNode(doc, "player", "-1"));
			main.appendChild(createNode(doc, "tactic", "0"));

		} else {
			main.appendChild(createNode(doc, "code", position.getId() + ""));
			main.appendChild(createNode(doc, "player", position.getSpielerId() + ""));
			main.appendChild(createNode(doc, "tactic", position.getTaktik() + ""));
		}
	}

}
