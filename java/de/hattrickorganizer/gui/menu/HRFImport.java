// %2363747329:de.hattrickorganizer.gui.menu%
package de.hattrickorganizer.gui.menu;

import java.sql.Timestamp;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


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
    public HRFImport(de.hattrickorganizer.gui.HOMainFrame frame) {
    	
        //Filechooser
        final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HRFImportieren"));

        final java.io.File pfad = new java.io.File(gui.UserParameter.instance().hrfImport_HRFPath);

        if (pfad.exists() && pfad.isDirectory()) {
            fileChooser.setCurrentDirectory(new java.io.File(gui.UserParameter.instance().hrfImport_HRFPath));
        }

        final de.hattrickorganizer.gui.utils.ExampleFileFilter filter = new de.hattrickorganizer.gui.utils.ExampleFileFilter();
        filter.addExtension("hrf");
        filter.setDescription("Hattrick Resource File");
        fileChooser.setFileFilter(filter);

        Timestamp olderHrf = new Timestamp(System.currentTimeMillis());
		
        final int returnVal = fileChooser.showOpenDialog(frame);

        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
        	
            final java.io.File[] files = fileChooser.getSelectedFiles();
            de.hattrickorganizer.model.HOModel homodel = null;

            for (int i = 0; i < files.length; i++) {
                if (files[i].getPath() != null) {
                    //Endung nicht hrf?
                    if (!files[i].getPath().endsWith(".hrf")) {
                        files[i] = new java.io.File(files[i].getAbsolutePath() + ".hrf");
                    }

                    //Datei schon vorhanden?
                    if (!files[i].exists()) {
                        //Info
                        frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("DateiNichtGefunden"),
                                                             de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);

                        //Fehler
                        de.hattrickorganizer.tools.Helper.showMessage(frame,
                                                                      de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("DateiNichtGefunden"),
                                                                      de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
                                                                      javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Pfad speichern
                    gui.UserParameter.instance().hrfImport_HRFPath = files[i].getParentFile()
                                                                             .getAbsolutePath();

                    //Info
                    frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("StartParse"));

                    //HRFParser
                    homodel = new de.hattrickorganizer.tools.HRFFileParser().parse(files[i]);

                    if (homodel == null) {
                        //Info
                        frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Importfehler")
                                                             + " : " + files[i].getName(),
                                                             de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);

                        //Fehler
                        de.hattrickorganizer.tools.Helper.showMessage(frame,
                                                                      de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Importfehler"),
                                                                      de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fehler"),
                                                                      javax.swing.JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Info
                        frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HRFSave"));

                        //Datei schon importiert worden?
                        final String oldHRFName = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                         .getHRFName4Date(homodel.getBasics()
                                                                                                                 .getDatum());
                        int value = javax.swing.JOptionPane.OK_OPTION;

                        //Erneut importieren
                        if (oldHRFName != null) {
                            value = javax.swing.JOptionPane.showConfirmDialog(frame,
                                                                              de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ErneutImportieren")
                                                                              + " " + oldHRFName,
                                                                              de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Frage"),
                                                                              javax.swing.JOptionPane.YES_NO_OPTION);
                        }

                        //Speichern
                        if (value == javax.swing.JOptionPane.OK_OPTION) {
                            //Saven
                            homodel.saveHRF();

                            if (homodel.getBasics().getDatum().before(olderHrf)) {
                                olderHrf = new Timestamp(homodel.getBasics().getDatum().getTime());
                            }

                            //Info
                            frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HRFErfolg"));
                        }
                        //Abbruch
                        else {
                            //Info
                            frame.getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HRFAbbruch"),
                                                                 de.hattrickorganizer.gui.InfoPanel.FEHLERFARBE);
                        }
                    }
                }
            }

			DBZugriff.instance().reimportSkillup();
			
            HOVerwaltung.instance().loadLatestHoModel();

            de.hattrickorganizer.logik.TrainingsManager.instance().calculateTrainings(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                                                             .getTrainingsVector());
            HOVerwaltung.instance().recalcSubskills(true, olderHrf);

            HOVerwaltung.instance().loadLatestHoModel();

			// Regenerate HOE Files
			HOLogger.instance().log(getClass(),"Regenerate HOE Files");
			FileExtensionManager.trainingUpdate();	
			FileExtensionManager.economyUpate();

            final HOModel hom = HOVerwaltung.instance().getModel();

            //Aufstellung in liste als Aktuelle Aufstellungsetzen und als Angezeigte Aufstellung
            de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel.setHRFAufstellung(hom
                                                                                                .getAufstellung(),
                                                                                                hom
                                                                                                .getLastAufstellung());
            de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel
            .setAngezeigteAufstellung(new de.hattrickorganizer.gui.model.AufstellungCBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"),
                                                                                           hom
                                                                                           .getAufstellung()));

			HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup("Actual");
			FileExtensionManager.extractLineup("Actual");
            //            //nur Anzeige aktualisieren wenn neues model aktueller ist
            //            if ( ( homodel != null ) && 
            //            ( ( model.HOVerwaltung.instance ().getModel () == null ) ||
            //            ( homodel.getBasics ().getDatum ().after ( model.HOVerwaltung.instance ().getModel ().getBasics ().getDatum () ) ) ) )
            //            {
            //                //Noch die alten Spieler hinzupacken
            //                homodel.setAllOldSpieler ( database.DBZugriff.instance ().getAllSpieler () );
            //                //Spielplan übernehmen!
            //                homodel.setSpielplan ( model.HOVerwaltung.instance().getModel ().getSpielplan () );  
            //                //Letzet Importierte Anzeigen
            //                model.HOVerwaltung.instance().setModel( homodel );
            //                //Subskills berechnen !
            //                homodel.calcSubskills();
            //                //Aufstellung in liste als Aktuelle Aufstellungsetzen und als Angezeigte Aufstellung
            //                gui.aufstellung.AufstellungsVergleichHistoryPanel.setHRFAufstellung ( homodel.getAufstellung (), homodel.getLastAufstellung () );
            //                gui.aufstellung.AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung ( new gui.model.AufstellungCBItem( model.HOVerwaltung.instance().getLanguageString("AktuelleAufstellung") , homodel.getAufstellung () ) );                
            //            }
            //Refreshen aller Fenster
            de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
        }
    }
}
