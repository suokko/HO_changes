package ho.core.file.extension;

import ho.core.db.DBManager;
import ho.core.epv.EPVData;
import ho.core.file.xml.XMLManager;
import ho.core.model.Basics;
import ho.core.model.HOVerwaltung;
import ho.core.model.SpielerPosition;
import ho.core.model.XtraData;
import ho.core.training.FutureTrainingManager;
import ho.core.training.TrainingPerWeek;
import ho.core.training.TrainingsManager;
import ho.core.util.HOLogger;
import ho.core.util.PlayerHelper;
import ho.module.training.FutureTrainingWeek;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.ISkillup;
import plugins.ISpieler;

public class PlayerCreator extends XMLCreator {


	private static int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
	private static DecimalFormat df = new DecimalFormat("#,###");

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
			Element root = doc.createElement("info");
			doc.appendChild(root);
			addRoster(root, HOVerwaltung.instance().getModel().getID(),true,0);
			File playerFile = new File(dir, "players.xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(playerFile));
			bw.write(XMLManager.instance().getXML(doc));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(PlayerCreator.class, e);
		}

	}

	protected static void extractHistoric() {
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
			Element root = doc.createElement("historic");
			doc.appendChild(root);

			Vector<TrainingPerWeek> l = TrainingsManager.instance().getTrainingsVector();
			int oldWeek = 0;
			for (int index = l.size(); index > 0; index--) {
				TrainingPerWeek tpw = (TrainingPerWeek) l.get(index - 1);
				oldWeek = addRoster(root, tpw.getPreviousHrfId(), false, oldWeek);
			}


			File dbFile = new File(dir, "playersdb.xml");
			BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile));
			bw.write(XMLManager.instance().getXML(doc));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			HOLogger.instance().log(PlayerCreator.class, e);
		}

	}


	private static int addRoster(Element root, int hrfId, boolean extended, int oldWeek) throws IOException {

		List<ISpieler> players = DBManager.instance().getSpieler(hrfId);
		Basics basics = DBManager.instance().getBasics(hrfId);
		XtraData xtradata = DBManager.instance().getXtraDaten(hrfId);

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
		if (tmpWeek == oldWeek) {
			return tmpWeek;
		}

		Document doc = root.getOwnerDocument();
		Element roster = doc.createElement("roster");
		root.appendChild(roster);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(basics.getDatum().getTime());
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
		roster.appendChild(createNode(doc,"date", dayFormat.format(c.getTime())));
		roster.appendChild(createNode(doc,"time", timeFormat.format(c.getTime())));
		roster.appendChild(createNode(doc,"week", tmpWeek+""));

		Element playersTag = doc.createElement("players");
		roster.appendChild(playersTag);

		for (Iterator<ISpieler> iter = players.iterator(); iter.hasNext();) {
			ISpieler element = iter.next();
			addPlayer(playersTag, element, extended);
		}

		return tmpWeek;
	}

	private static void addPlayer(Element playersTag, ISpieler player, boolean extended) throws IOException {
		Document doc = playersTag.getOwnerDocument();

		Element playerTag = doc.createElement("player");
		playersTag.appendChild(playerTag);
		playerTag.appendChild(createNode(doc,"id", player.getSpielerID()+""));
		playerTag.appendChild(createNode(doc,"name", player.getName()+""));
		final int salary = (int) (player.getGehalt() / ho.core.model.UserParameter.instance().faktorGeld);
		playerTag.appendChild(createNode(doc,"salary", salary+""));
		playerTag.appendChild(createNode(doc,"nationality", player.getNationalitaet()+""));
		playerTag.appendChild(createNode(doc,"match", player.getBewertung()+""));
		playerTag.appendChild(createNode(doc,"lastmatch", player.getLetzteBewertung()+""));

		Element bestposition = doc.createElement("bestposition");
		playerTag.appendChild(bestposition);
		bestposition.appendChild(createNode(doc,"role", SpielerPosition.getNameForPosition(player.getIdealPosition())+""));
		bestposition.appendChild(createNode(doc,"value", player.calcPosValue(player.getIdealPosition(), true)+""));
		bestposition.appendChild(createNode(doc,"code", player.getIdealPosition() +""));

		EPVData data = HOVerwaltung.instance().getModel().getEPV().getEPVData(player);
		double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
		playerTag.appendChild(createNode(doc,"epv", df.format(price)));

		Element skill = doc.createElement("skill");
		playerTag.appendChild(skill);
		skill.appendChild(createNode(doc,"playmaking", (player.getSpielaufbau() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU))+""));
		skill.appendChild(createNode(doc,"passing", (player.getPasspiel() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL))+""));
		skill.appendChild(createNode(doc,"cross", (player.getFluegelspiel() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL))+""));
		skill.appendChild(createNode(doc,"defense", (player.getVerteidigung() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG))+""));
		skill.appendChild(createNode(doc,"attack", (player.getTorschuss() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS))+""));
		skill.appendChild(createNode(doc,"setpieces", (player.getStandards() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS))+""));
		skill.appendChild(createNode(doc,"keeper", (player.getTorwart() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART))+""));
		skill.appendChild(createNode(doc,"stamina", (player.getKondition() + player.getSubskill4SkillWithOffset(ISpieler.SKILL_KONDITION))+""));
		skill.appendChild(createNode(doc,"form", player.getForm()+""));
		skill.appendChild(createNode(doc,"experience", player.getErfahrung()+""));
		skill.appendChild(createNode(doc,"tsi", player.getTSI()+""));

		if (extended) {
			Element skillups = doc.createElement("skillups");
			playerTag.appendChild(skillups);

			int coTrainer = HOVerwaltung.instance().getModel().getVerein().getCoTrainer();
			int trainer = HOVerwaltung.instance().getModel().getTrainer().getTrainer();
			List<FutureTrainingWeek> futures =DBManager.instance().getFutureTrainingsVector();

			FutureTrainingManager ftm = new FutureTrainingManager(player, futures, coTrainer,  trainer);

			List<ISkillup> futureSkillups = ftm.getFutureSkillups();

			for (Iterator<ISkillup> iterator = futureSkillups.iterator(); iterator.hasNext();) {
				ISkillup skillup = iterator.next();
				Element skillupTag = doc.createElement("skillup");
				skillups.appendChild(skillupTag);
				skillupTag.appendChild(createNode(doc,"week", skillup.getHtSeason() + "/" + skillup.getHtWeek()));
				skillupTag.appendChild(createNode(doc,"skill", skillup.getType()+""));
				skillupTag.appendChild(createNode(doc,"skillDesc", getSkillDescription(skillup.getType())));
				skillupTag.appendChild(createNode(doc,"value", skillup.getValue()+""));
				skillupTag.appendChild(createNode(doc,"valueDesc", PlayerHelper.getNameForSkill(skillup.getValue(), false)));
			}
		}
	}

	public static String getSkillDescription(int skillIndex) {
		// Based on the code returns the proper ho property
		switch (skillIndex) {
			case ISpieler.SKILL_TORWART :
				return HOVerwaltung.instance().getLanguageString("skill.keeper"); //$NON-NLS-1$

			case ISpieler.SKILL_SPIELAUFBAU :
				return HOVerwaltung.instance().getLanguageString("skill.playmaking"); //$NON-NLS-1$

			case ISpieler.SKILL_PASSSPIEL :
				return HOVerwaltung.instance().getLanguageString("skill.passing"); //$NON-NLS-1$

			case ISpieler.SKILL_FLUEGEL :
				return HOVerwaltung.instance().getLanguageString("skill.winger"); //$NON-NLS-1$

			case ISpieler.SKILL_VERTEIDIGUNG :
				return HOVerwaltung.instance().getLanguageString("skill.defending"); //$NON-NLS-1$

			case ISpieler.SKILL_TORSCHUSS :
				return HOVerwaltung.instance().getLanguageString("skill.scoring"); //$NON-NLS-1$

			case ISpieler.SKILL_STANDARDS :
				return HOVerwaltung.instance().getLanguageString("skill.set_pieces"); //$NON-NLS-1$

			case ISpieler.SKILL_KONDITION :
				return HOVerwaltung.instance().getLanguageString("skill.stamina"); //$NON-NLS-1$

			case ISpieler.SKILL_EXPIERIENCE :
				return HOVerwaltung.instance().getLanguageString("skill.experience"); //$NON-NLS-1$
		}

		return ""; //$NON-NLS-1$
	}
}
