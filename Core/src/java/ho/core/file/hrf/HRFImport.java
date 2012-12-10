// %2363747329:de.hattrickorganizer.gui.menu%
package ho.core.file.hrf;

import ho.core.db.DBManager;
import ho.core.file.ExampleFileFilter;
import ho.core.gui.HOMainFrame;
import ho.core.gui.InfoPanel;
import ho.core.gui.RefreshManager;
import ho.core.gui.model.AufstellungCBItem;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.training.TrainingManager;
import ho.core.util.Helper;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;

import java.awt.Frame;
import java.io.File;
import java.sql.Timestamp;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Importiert eine angegebenen HRFDatei
 */
public class HRFImport {

	public HRFImport(HOMainFrame frame) {

		File[] files = getHRFFiles(frame);
		if (files != null) {
			Timestamp olderHrf = new Timestamp(System.currentTimeMillis());
			HOModel homodel = null;

			for (int i = 0; i < files.length; i++) {
				if (files[i].getPath() != null) {
					// Endung nicht hrf?
					if (!files[i].getPath().endsWith(".hrf")) {
						files[i] = new File(files[i].getAbsolutePath() + ".hrf");
					}

					// Datei schon vorhanden?
					if (!files[i].exists()) {
						// Info
						frame.getInfoPanel().setLangInfoText(getLangStr("DateiNichtGefunden"),
								InfoPanel.FEHLERFARBE);

						// Fehler
						Helper.showMessage(frame, getLangStr("DateiNichtGefunden"),
								getLangStr("Fehler"), JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Pfad speichern
					UserParameter.instance().hrfImport_HRFPath = files[i].getParentFile()
							.getAbsolutePath();

					// Info
					frame.getInfoPanel().setLangInfoText(getLangStr("StartParse"));

					// HRFParser
					homodel = new HRFFileParser().parse(files[i]);

					if (homodel == null) {
						// Info
						frame.getInfoPanel().setLangInfoText(
								getLangStr("Importfehler") + " : " + files[i].getName(),
								InfoPanel.FEHLERFARBE);

						// Fehler
						Helper.showMessage(frame, getLangStr("Importfehler"), getLangStr("Fehler"),
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Info
						frame.getInfoPanel().setLangInfoText(getLangStr("HRFSave"));

						// Datei schon importiert worden?
						String oldHRFName = DBManager.instance().getHRFName4Date(
								homodel.getBasics().getDatum());
						int value = JOptionPane.OK_OPTION;

						// Erneut importieren
						if (oldHRFName != null) {
							value = JOptionPane.showConfirmDialog(frame, HOVerwaltung.instance()
									.getLanguageString("ErneutImportieren") + " " + oldHRFName,
									getLangStr("Frage"), JOptionPane.YES_NO_OPTION);
						}

						// Speichern
						if (value == JOptionPane.OK_OPTION) {
							// Saven
							homodel.saveHRF();

							if (homodel.getBasics().getDatum().before(olderHrf)) {
								olderHrf = new Timestamp(homodel.getBasics().getDatum().getTime());
							}

							// Info
							frame.getInfoPanel().setLangInfoText(getLangStr("HRFErfolg"));
						}
						// Abbruch
						else {
							// Info
							frame.getInfoPanel().setLangInfoText(getLangStr("HRFAbbruch"),
									InfoPanel.FEHLERFARBE);
						}
					}
				}
			}

			DBManager.instance().reimportSkillup();
			HOVerwaltung.instance().loadLatestHoModel();
			TrainingManager.instance().refreshTrainingWeeks();
			HOVerwaltung.instance().recalcSubskills(true, olderHrf);
			HOVerwaltung.instance().loadLatestHoModel();

			HOModel hom = HOVerwaltung.instance().getModel();

			// Aufstellung in liste als Aktuelle Aufstellungsetzen und als
			// Angezeigte Aufstellung
			AufstellungsVergleichHistoryPanel.setHRFAufstellung(hom.getAufstellung(),
					hom.getLastAufstellung());
			AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(new AufstellungCBItem(
					getLangStr("AktuelleAufstellung"), hom.getAufstellung()));

			HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel()
					.exportOldLineup("Actual");
			// Refreshen aller Fenster
			RefreshManager.instance().doReInit();
		}
	}

	private File[] getHRFFiles(Frame parent) {
		// Filechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setDialogTitle(getLangStr("HRFImportieren"));

		File pfad = new File(UserParameter.instance().hrfImport_HRFPath);

		if (pfad.exists() && pfad.isDirectory()) {
			fileChooser.setCurrentDirectory(new File(UserParameter.instance().hrfImport_HRFPath));
		}

		ExampleFileFilter filter = new ExampleFileFilter();
		filter.addExtension("hrf");
		filter.setDescription("Hattrick Resource File");
		fileChooser.setFileFilter(filter);

		Timestamp olderHrf = new Timestamp(System.currentTimeMillis());

		if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFiles();
		}
		return null;
	}

	private String getLangStr(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}
