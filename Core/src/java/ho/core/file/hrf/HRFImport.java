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
import ho.core.training.TrainingManager;
import ho.core.util.Helper;
import ho.module.lineup.AufstellungsVergleichHistoryPanel;

import java.sql.Timestamp;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/**
 * Importiert eine angegebenen HRFDatei
 */
public class HRFImport {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HRFImport object.
     *
     * @param frame TODO Missing Constructuor Parameter Documentation
     */
    public HRFImport(HOMainFrame frame) {
    	
        //Filechooser
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle(HOVerwaltung.instance().getLanguageString("HRFImportieren"));

        final java.io.File pfad = new java.io.File(ho.core.model.UserParameter.instance().hrfImport_HRFPath);

        if (pfad.exists() && pfad.isDirectory()) {
            fileChooser.setCurrentDirectory(new java.io.File(ho.core.model.UserParameter.instance().hrfImport_HRFPath));
        }

        final ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("hrf");
        filter.setDescription("Hattrick Resource File");
        fileChooser.setFileFilter(filter);

        Timestamp olderHrf = new Timestamp(System.currentTimeMillis());
		
        final int returnVal = fileChooser.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	
            final java.io.File[] files = fileChooser.getSelectedFiles();
            HOModel homodel = null;

            for (int i = 0; i < files.length; i++) {
                if (files[i].getPath() != null) {
                    //Endung nicht hrf?
                    if (!files[i].getPath().endsWith(".hrf")) {
                        files[i] = new java.io.File(files[i].getAbsolutePath() + ".hrf");
                    }

                    //Datei schon vorhanden?
                    if (!files[i].exists()) {
                        //Info
                        frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("DateiNichtGefunden"),
                                                             InfoPanel.FEHLERFARBE);

                        //Fehler
                        Helper.showMessage(frame,HOVerwaltung.instance().getLanguageString("DateiNichtGefunden"),
                                                 HOVerwaltung.instance().getLanguageString("Fehler"),
                                                 JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Pfad speichern
                    ho.core.model.UserParameter.instance().hrfImport_HRFPath = files[i].getParentFile()
                                                                             .getAbsolutePath();

                    //Info
                    frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("StartParse"));

                    //HRFParser
                    homodel = new HRFFileParser().parse(files[i]);

                    if (homodel == null) {
                        //Info
                        frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("Importfehler")
                                                             + " : " + files[i].getName(),
                                                             InfoPanel.FEHLERFARBE);

                        //Fehler
                        Helper.showMessage(frame,HOVerwaltung.instance().getLanguageString("Importfehler"),
                                                 HOVerwaltung.instance().getLanguageString("Fehler"),
                                                 JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Info
                        frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("HRFSave"));

                        //Datei schon importiert worden?
                        final String oldHRFName = DBManager.instance()
                                                                                         .getHRFName4Date(homodel.getBasics()
                                                                                                                 .getDatum());
                        int value = JOptionPane.OK_OPTION;

                        //Erneut importieren
                        if (oldHRFName != null) {
                            value = JOptionPane.showConfirmDialog(frame,
                                                                  HOVerwaltung.instance().getLanguageString("ErneutImportieren")+ " " + oldHRFName,
                                                                  HOVerwaltung.instance().getLanguageString("Frage"),
                                                                  JOptionPane.YES_NO_OPTION);
                        }

                        //Speichern
                        if (value == JOptionPane.OK_OPTION) {
                            //Saven
                            homodel.saveHRF();

                            if (homodel.getBasics().getDatum().before(olderHrf)) {
                                olderHrf = new Timestamp(homodel.getBasics().getDatum().getTime());
                            }

                            //Info
                            frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("HRFErfolg"));
                        }
                        //Abbruch
                        else {
                            //Info
                            frame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("HRFAbbruch"),
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

            final HOModel hom = HOVerwaltung.instance().getModel();

            //Aufstellung in liste als Aktuelle Aufstellungsetzen und als Angezeigte Aufstellung
            AufstellungsVergleichHistoryPanel.setHRFAufstellung(hom.getAufstellung(),
                                                                hom.getLastAufstellung());
            AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(
            		new AufstellungCBItem(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"),
                                          hom.getAufstellung())
            		);

			HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
            //Refreshen aller Fenster
            RefreshManager.instance().doReInit();
        }
    }
}
