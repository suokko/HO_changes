package de.hattrickorganizer.tools.extension;

import ho.core.file.xml.XMLManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.ILigaTabellenEintrag;
import plugins.ISpielplan;

import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;

public class StandingCreator extends XMLCreator{

	private static int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

	public static void extractActual() {
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
			Element root = doc.createElement("standing");
			doc.appendChild(root);
						
			for (int i = 0; i < HOMiniModel.instance().getSpielplaene().length; i++) {
				ISpielplan spielPlan = HOMiniModel.instance().getSpielplaene()[i];
				if (spielPlan.getLigaName().equalsIgnoreCase(HOMiniModel.instance().getLiga().getLiga())
					&& spielPlan.getSaison() == HOMiniModel.instance().getBasics().getSeason()) {
					extractTabelle(root,spielPlan.getTabelle().getEintraege());
				}
			}

			File dbFile = new File(dir, "standings.xml");			
			BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile));	
			bw.write(XMLManager.instance().getXML(doc));				
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(StandingCreator.class, e);
		}

	}

	private static void extractTabelle(Element root, Vector<ILigaTabellenEintrag> vector)
		throws IOException {
			
		Document doc = root.getOwnerDocument();
						
		for (int i = 0; i < vector.size(); i++) {
			final ILigaTabellenEintrag eintrag = vector.get(i);

			if (eintrag.getPunkte() > -1) {

				Element team = doc.createElement("team");
				root.appendChild(team);					
				team.appendChild(createNode(doc,"position", eintrag.getPosition()+""));			
				team.appendChild(createNode(doc,"oldPosition", eintrag.getAltePosition()+""));
				team.appendChild(createNode(doc,"teamname", eintrag.getTeamName()+""));
				team.appendChild(createNode(doc,"serie", eintrag.getSerieAsString()+""));
				team.appendChild(createNode(doc,"played", eintrag.getAnzSpiele()+""));
				team.appendChild(createNode(doc,"teamid", eintrag.getTeamId()+""));				
				
				Element total = doc.createElement("total");
				team.appendChild(total);	
				total.appendChild(createNode(doc,"won", eintrag.getG_Siege()+""));
				total.appendChild(createNode(doc,"lost", eintrag.getG_Un()+""));
				total.appendChild(createNode(doc,"draw", eintrag.getG_Nied()+""));
				total.appendChild(createNode(doc,"scored", eintrag.getToreFuer()+""));
				total.appendChild(createNode(doc,"allowed", eintrag.getToreGegen()+""));
				total.appendChild(createNode(doc,"difference", eintrag.getGesamtTorDiff()+""));
				total.appendChild(createNode(doc,"points", eintrag.getPunkte()+""));
				
				Element home = doc.createElement("home");
				team.appendChild(home);	
				home.appendChild(createNode(doc,"won", eintrag.getH_Siege()+""));
				home.appendChild(createNode(doc,"lost", eintrag.getH_Un()+""));
				home.appendChild(createNode(doc,"draw", eintrag.getH_Nied()+""));
				home.appendChild(createNode(doc,"scored", eintrag.getH_ToreFuer()+""));
				home.appendChild(createNode(doc,"allowed", eintrag.getH_ToreGegen()+""));
				home.appendChild(createNode(doc,"difference", eintrag.getHeimTorDiff()+""));
				home.appendChild(createNode(doc,"points", eintrag.getH_Punkte()+""));
				
				Element away = doc.createElement("away");
				team.appendChild(away);	
				away.appendChild(createNode(doc,"won", eintrag.getA_Siege()+""));
				away.appendChild(createNode(doc,"lost", eintrag.getA_Un()+""));
				away.appendChild(createNode(doc,"draw", eintrag.getA_Nied()+""));
				away.appendChild(createNode(doc,"scored", eintrag.getA_ToreFuer()+""));
				away.appendChild(createNode(doc,"allowed", eintrag.getA_ToreGegen()+""));
				away.appendChild(createNode(doc,"difference", eintrag.getAwayTorDiff()+""));
				away.appendChild(createNode(doc,"points", eintrag.getA_Punkte()+""));				

			}
		}

	}

}
