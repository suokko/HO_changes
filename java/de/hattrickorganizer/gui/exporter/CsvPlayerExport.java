package de.hattrickorganizer.gui.exporter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

import plugins.IHOMiniModel;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.utils.ExampleFileFilter;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;

/**
 * CsvPlayerExport
 * 
 * Export all players as CSV file
 * 
 * @author flattermann <HO@flattermann.net>
 */
public class CsvPlayerExport {
	private static String NAME = "CSV PlayerExport";
	private static IHOMiniModel miniModel;
	private static final String defaultFilename = "playerexport.csv";

	public CsvPlayerExport() {
		miniModel = HOMiniModel.instance();
	}

	public void showSaveDialog() {
		JWindow waitDialog = null;
		// File
		File file = new File(defaultFilename);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setDialogTitle(NAME); // TODO L10N

		ExampleFileFilter filter = new ExampleFileFilter();
		filter.addExtension("csv");
		filter.setDescription("CSV - Comma separated file");
		fileChooser.setFileFilter(filter);
		fileChooser.setSelectedFile(file);

		int returnVal = fileChooser.showSaveDialog(HOMiniModel.instance()
				.getGUI().getOwner4Dialog());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (file.exists() && JOptionPane.showConfirmDialog(
					HOMiniModel.instance().getGUI().getOwner4Dialog(), 
					HOVerwaltung.instance().getLanguageString("overwrite"), NAME,
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				// Cancel
				return;		
            }

			waitDialog =
				HOMiniModel.instance().getGUI().createWaitDialog(HOMiniModel.instance().getGUI().getOwner4Dialog());
			waitDialog.setVisible(true);
			doExport (file);
			waitDialog.setVisible(false);
		}
	}

	private void doExport (File file) {
		HOLogger.instance().info(getClass(),
				"Exporting all players as CSV to " + file.getName() + "...");
		List<Spieler> list = miniModel.getAllSpieler();
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("id,shirtno,name,age,agedays,form,stamina,"
					+ "skill_gk,skill_pm,skill_ps,skill_wi,skill_de,skill_sc,skill_setpieces,"
					+ "specialty,tsi,wage,xp,leadership,pop,agg,hon,cards,injury,"
					+ "GK,CD,CD_off,CD_tw,WB,WB_off,WB_def,WB_tm,IM,IM_off,IM_def,IM_tw,WI,WI_off,WI_def,WI_tm,FW,FW_def,FW_tw"
					+ "\n");
			Iterator<Spieler> iter = list.iterator();
			while (iter.hasNext()) {
				Spieler curPlayer = (Spieler)iter.next();
				String [] outCols = {
						"" + curPlayer.getSpielerID(),
						"" + curPlayer.getTrikotnummer(),
						"" + curPlayer.getName(),
						"" + curPlayer.getAlter(),
						"" + curPlayer.getAgeDays(),
						"" + curPlayer.getForm(),
						"" + curPlayer.getKondition(),
						"" + (curPlayer.getTorwart() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART)),
						"" + (curPlayer.getSpielaufbau() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU)),
						"" + (curPlayer.getPasspiel() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL)),
						"" + (curPlayer.getFluegelspiel() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL)),
						"" + (curPlayer.getVerteidigung() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG)),
						"" + (curPlayer.getTorschuss() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS)),
						"" + (curPlayer.getStandards() + curPlayer.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS)),
						"" + curPlayer.getSpezialitaet(),
						"" + curPlayer.getTSI(),
						"" + (int)(curPlayer.getGehalt() / miniModel.getXtraDaten().getCurrencyRate()),
						"" + curPlayer.getErfahrung(),
						"" + curPlayer.getFuehrung(),
						"" + curPlayer.getAnsehen(),
						"" + curPlayer.getAgressivitaet(),
						"" + curPlayer.getCharakter(),
						"" + curPlayer.getGelbeKarten(),
						"" + curPlayer.getVerletzt(),
						"" + curPlayer.calcPosValue(ISpielerPosition.TORWART, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.INNENVERTEIDIGER, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.INNENVERTEIDIGER_OFF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.INNENVERTEIDIGER_AUS, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.AUSSENVERTEIDIGER, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.AUSSENVERTEIDIGER_OFF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.AUSSENVERTEIDIGER_DEF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.AUSSENVERTEIDIGER_IN, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.MITTELFELD, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.MITTELFELD_OFF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.MITTELFELD_DEF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.MITTELFELD_AUS, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.FLUEGELSPIEL, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.FLUEGELSPIEL_OFF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.FLUEGELSPIEL_DEF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.FLUEGELSPIEL_IN, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.STURM, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.STURM_DEF, true),
						"" + curPlayer.calcPosValue(ISpielerPosition.STURM_AUS, true)
				};
				for (int col=0; col < outCols.length; col++) {
					if (col > 0)
						writer.write (",");
					writer.write ("\"");
					writer.write (outCols[col]);
					writer.write ("\"");
				}
				writer.write ("\n");
			}
			writer.close();
			HOLogger.instance().info(getClass(), "CSV Export complete.");
		} catch (Exception e) {
			HOLogger.instance().error(getClass(), "CSV Export error!");
			e.printStackTrace();
		}
	}
}
