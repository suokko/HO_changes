package de.hattrickorganizer.tools.extension;

import ho.core.file.xml.XMLManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.ISpielerPosition;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.HOLogger;

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

			for (int i = 0; i < lineup.getBestElferKicker().length; i++) {
				int id = lineup.getBestElferKicker()[i];
				penaltyTakers.appendChild(createNode(doc,"id", id+""));
			}
			root.appendChild(createNode(doc,"captain", lineup.getKapitaen()+""));
			root.appendChild(createNode(doc,"kicker", lineup.getKicker()+""));
			root.appendChild(createNode(doc,"tacticType", lineup.getTacticType()+""));
			root.appendChild(createNode(doc,"matchType", lineup.getAttitude()+""));
			
			File f = new File(dir, lineupName + ".xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));	
			bw.write(XMLManager.instance().getXML(doc));				
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
			main.appendChild(createNode(doc,"code", "0"));
			main.appendChild(createNode(doc,"player", "-1"));
			main.appendChild(createNode(doc,"tactic", "0"));

		} else {
			main.appendChild(createNode(doc,"code", position.getId()+""));
			main.appendChild(createNode(doc,"player", position.getSpielerId()+""));
			main.appendChild(createNode(doc,"tactic", position.getTaktik()+""));			
		}
	}
	
}
