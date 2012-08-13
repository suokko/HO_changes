package ho.core.file.extension;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.XtraData;
import ho.core.model.misc.Basics;
import ho.core.model.misc.Finanzen;
import ho.core.training.TrainingPerWeek;
import ho.core.training.TrainingManager;
import ho.core.util.HOLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
			List<TrainingPerWeek> l = TrainingManager.instance().getTrainingWeekList();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();
			Element root = doc.createElement("historic");
			doc.appendChild(root);

			int latestId = DBManager.instance().getLatestHrfId();
			XtraData d1 = DBManager.instance().getXtraDaten(latestId);

			int lastTrainingId = ((TrainingPerWeek) l.get(l.size() - 1)).getHrfId();
			XtraData d2 = DBManager.instance().getXtraDaten(lastTrainingId);

			if (!d2.getEconomyDate().equals(d1.getEconomyDate())) {
				addEconomy(root, latestId);
			}

			for (int index = l.size(); index > 0; index--) {
				TrainingPerWeek tpw = (TrainingPerWeek) l.get(index - 1);
				addEconomy(root, tpw.getHrfId());
			}

			String xml = XMLManager.getXML(doc);
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

		Basics basics = DBManager.instance().getBasics(hrfId);
		Finanzen finance = DBManager.instance().getFinanzen(hrfId);
		XtraData xtradata = DBManager.instance().getXtraDaten(hrfId);

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
			Basics basics = DBManager.instance().getBasics(latestId);
			Finanzen finance = DBManager.instance().getFinanzen(latestId);
			XtraData xtradata = DBManager.instance().getXtraDaten(latestId);

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
			bw.write(XMLManager.getXML(doc));		
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(EconomyCreator.class, e);
		}

	}

}
