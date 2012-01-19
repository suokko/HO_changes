package de.hattrickorganizer.tools.extension;

import gui.UserParameter;
import ho.core.db.DBManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IBasics;
import plugins.IFinanzen;
import plugins.ITrainingWeek;
import plugins.IXtraData;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.TrainingPerWeek;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;

public class EconomyCreator extends XMLCreator {

	private static int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

	protected static void extractHistoric() {
		if (teamId == 0) {
			return;
		}
		File dir = new File("Info/" + teamId);
		if (!dir.exists()) {
			dir.mkdirs();
		}


		try {
			Vector<ITrainingWeek> l = HOMiniModel.instance().getTrainingsManager().getTrainingsVector();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();
			Element root = doc.createElement("historic");
			doc.appendChild(root);

			int latestId = DBManager.instance().getLatestHrfId();
			IXtraData d1 = DBManager.instance().getXtraDaten(latestId);

			int lastTrainingId = ((TrainingPerWeek) l.get(l.size() - 1)).getHrfId();
			IXtraData d2 = DBManager.instance().getXtraDaten(lastTrainingId);

			if (!d2.getEconomyDate().equals(d1.getEconomyDate())) {
				addEconomy(root, latestId);
			}

			for (int index = l.size(); index > 0; index--) {
				TrainingPerWeek tpw = (TrainingPerWeek) l.get(index - 1);
				addEconomy(root, tpw.getHrfId());
			}

			String xml = XMLManager.instance().getXML(doc);
			File dbFile = new File(dir, "economydb.xml");			
			BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile));			
			bw.write(xml);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(EconomyCreator.class, e);
		}
	}

	private static void addEconomy(Element root, int hrfId) throws IOException {

		IBasics basics = DBManager.instance().getBasics(hrfId);
		IFinanzen finance = DBManager.instance().getFinanzen(hrfId);
		IXtraData xtradata = DBManager.instance().getXtraDaten(hrfId);

		double rate = UserParameter.instance().faktorGeld;
		int actualSeason = basics.getSeason();
		int actualWeek = basics.getSpieltag();
		// We are in the middle where season has not been updated!
		try {
			if (xtradata.getTrainingDate().after(xtradata.getSeriesMatchDate())) {
				actualWeek++;

				if (actualWeek == 17) {
					actualWeek = 1;
					actualSeason++;
				}
			}
		} catch (Exception e1) {
			// Null when first time HO is launched		
		}

		int tmpWeek = actualWeek + actualSeason * 16 - 1;
		Document doc = root.getOwnerDocument();
		Element main = doc.createElement("economy");
		root.appendChild(main);		
		
		main.appendChild(createNode(doc,"week",tmpWeek+""));			
		main.appendChild(createNode(doc,"money",(finance.getFinanzen() - finance.getLetzteGewinnVerlust()) / rate+""));		
		
		Element income = doc.createElement("income");
		main.appendChild(income);
		income.appendChild(createNode(doc,"crowd",finance.getLetzteEinnahmenZuschauer() / rate+""));
		income.appendChild(createNode(doc,"sponsor",finance.getLetzteEinnahmenSponsoren() / rate+""));
		income.appendChild(createNode(doc,"financial",finance.getLetzteEinnahmenZinsen() / rate+""));
		income.appendChild(createNode(doc,"temporary",finance.getLetzteEinnahmenSonstige() / rate+""));
		income.appendChild(createNode(doc,"total",finance.getLetzteEinnahmenGesamt() / rate+""));
		
		Element outcome = doc.createElement("outcome");
		main.appendChild(outcome);		
		outcome.appendChild(createNode(doc,"stadium",finance.getLetzteKostenStadion() / rate+""));
		outcome.appendChild(createNode(doc,"wages",finance.getLetzteKostenSpieler() / rate+""));
		outcome.appendChild(createNode(doc,"interest",finance.getLetzteKostenZinsen() / rate+""));
		outcome.appendChild(createNode(doc,"extra",finance.getLetzteKostenSonstige() / rate+""));
		outcome.appendChild(createNode(doc,"youth",finance.getLetzteKostenJugend() / rate+""));
		outcome.appendChild(createNode(doc,"staff",finance.getLetzteKostenTrainerstab() / rate+""));	
		outcome.appendChild(createNode(doc,"total",finance.getLetzteKostenGesamt() / rate+""));
		
	}

	public static void extractActual() {
		if (teamId == 0) {
			return;
		}
		File dir = new File("Info/" + teamId);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {

			int latestId = DBManager.instance().getLatestHrfId();
			IBasics basics = DBManager.instance().getBasics(latestId);
			IFinanzen finance = DBManager.instance().getFinanzen(latestId);
			IXtraData xtradata = DBManager.instance().getXtraDaten(latestId);

			double rate = UserParameter.instance().faktorGeld;
			int actualSeason = basics.getSeason();
			int actualWeek = basics.getSpieltag();
			// We are in the middle where season has not been updated!
			try {
				if (xtradata.getTrainingDate().after(xtradata.getSeriesMatchDate())) {
					actualWeek++;

					if (actualWeek == 17) {
						actualWeek = 1;
						actualSeason++;
					}
				}
			} catch (Exception e1) {
				// Null when first time HO is launched		
			}

			int tmpWeek = actualWeek + actualSeason * 16;
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();	
			Element main = doc.createElement("economy");
			doc.appendChild(main);		
			
			main.appendChild(createNode(doc,"week",tmpWeek+""));		
			main.appendChild(createNode(doc,"money",finance.getFinanzen() / rate+""));
			
		
			Element income = doc.createElement("income");
			main.appendChild(income);
			income.appendChild(createNode(doc,"crowd",finance.getEinnahmenZuschauer() / rate+""));
			income.appendChild(createNode(doc,"sponsor",finance.getEinnahmenSponsoren() / rate+""));
			income.appendChild(createNode(doc,"financial",finance.getEinnahmenZinsen() / rate+""));
			income.appendChild(createNode(doc,"temporary",finance.getEinnahmenSonstige() / rate+""));
			income.appendChild(createNode(doc,"total",finance.getEinnahmenGesamt() / rate+""));
		
			Element outcome = doc.createElement("outcome");
			main.appendChild(outcome);		
			outcome.appendChild(createNode(doc,"stadium",finance.getKostenStadion() / rate+""));
			outcome.appendChild(createNode(doc,"wages",finance.getKostenSpieler() / rate+""));
			outcome.appendChild(createNode(doc,"interest",finance.getKostenZinsen() / rate+""));
			outcome.appendChild(createNode(doc,"extra",finance.getKostenSonstige() / rate+""));
			outcome.appendChild(createNode(doc,"youth",finance.getKostenJugend() / rate+""));
			outcome.appendChild(createNode(doc,"staff",finance.getKostenTrainerstab() / rate+""));	
			outcome.appendChild(createNode(doc,"total",finance.getKostenGesamt() / rate+""));

			
			File dbFile = new File(dir, "economy.xml");			
			BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile));	
			bw.write(XMLManager.instance().getXML(doc));		
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(EconomyCreator.class, e);
		}

	}

}
