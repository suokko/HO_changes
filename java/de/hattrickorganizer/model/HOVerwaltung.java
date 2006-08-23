// %1206903388:de.hattrickorganizer.model%
/*
 * HOVerwaltung.java
 *
 * Created on 22. März 2003, 16:31
 */
package de.hattrickorganizer.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Vector;

import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author tom
 */
public class HOVerwaltung {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** singelton */
    protected static HOVerwaltung m_clInstance;

    //~ Instance fields ----------------------------------------------------------------------------

    /** das Model */
    protected HOModel m_clHoModel;

    /** Resource */
    protected java.util.Properties m_clResource;

    /** Parameter */
    protected String[] m_sArgs;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HOVerwaltung object.
     */
    private HOVerwaltung() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param args TODO Missing Method Parameter Documentation
     */
    public void setArgs(String[] args) {
        m_sArgs = args;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String[] getArgs() {
        return m_sArgs;
    }

    //-----------------Hilfsmethoden---------------------------------------------

    /**
     * Gibt den Durchschnittlichen Mannschaftswert zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getDurchschnittlichenMannschaftswert() {
        float summe = 0;
        final Vector vSpieler = getModel().getAllSpieler();

        for (int i = 0; i < vSpieler.size(); i++) {
            //Trainer nicht berücksichtigen
            if (!((Spieler) vSpieler.get(i)).isTrainer()) {
                summe += ((Spieler) vSpieler.get(i)).getMarkwert();
            }
        }

        //Trainer abziehen
        return de.hattrickorganizer.tools.Helper.round(summe / (vSpieler.size() - 1));
    }

    /**
     * Gibt das Durchschnittsalter zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getDurchschnittsAlter() {
        float summe = 0;
        final Vector vSpieler = getModel().getAllSpieler();

        for (int i = 0; i < vSpieler.size(); i++) {
            //Trainer nicht berücksichtigen
            if (!((Spieler) vSpieler.get(i)).isTrainer()) {
                summe += ((Spieler) vSpieler.get(i)).getAlter();
            }
        }

        //Trainer abziehen
        return de.hattrickorganizer.tools.Helper.round(summe / (vSpieler.size() - 1));
    }

    /**
     * Gibt das Durchschnittserfahrung zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getDurchschnittsErfahrung() {
        float summe = 0;
        final Vector vSpieler = getModel().getAllSpieler();

        for (int i = 0; i < vSpieler.size(); i++) {
            //Trainer nicht berücksichtigen
            if (!((Spieler) vSpieler.get(i)).isTrainer()) {
                summe += ((Spieler) vSpieler.get(i)).getErfahrung();
            }
        }

        //Trainer abziehen
        return de.hattrickorganizer.tools.Helper.round(summe / (vSpieler.size() - 1), 3);
    }

    /**
     * Gibt das Durchschnittsform zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getDurchschnittsForm() {
        float summe = 0;
        final Vector vSpieler = getModel().getAllSpieler();

        for (int i = 0; i < vSpieler.size(); i++) {
            //Trainer nicht berücksichtigen
            if (!((Spieler) vSpieler.get(i)).isTrainer()) {
                summe += ((Spieler) vSpieler.get(i)).getForm();
            }
        }

        //Trainer abziehen
        return de.hattrickorganizer.tools.Helper.round(summe / (vSpieler.size() - 1), 3);
    }

    /**
     * Gibt den gesamtmarktwert zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getGesamtMannschaftswert() {
        float summe = 0;
        final Vector vSpieler = getModel().getAllSpieler();

        for (int i = 0; i < vSpieler.size(); i++) {
            //Trainer nicht berücksichtigen
            if (!((Spieler) vSpieler.get(i)).isTrainer()) {
                summe += ((Spieler) vSpieler.get(i)).getMarkwert();
            }
        }

        return summe;
    }

    /**
     * Creates a new instance of HOVerwaltung
     *
     * @param model TODO Missing Constructuor Parameter Documentation
     */
    public void setModel(HOModel model) {
        m_clHoModel = model;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public HOModel getModel() {
        return m_clHoModel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static HOVerwaltung instance() {
        if (m_clInstance == null) {
            m_clInstance = new HOVerwaltung();

            //TODO : defaults für FaktorObjekte einladen
            de.hattrickorganizer.database.DBZugriff.instance().getFaktorenFromDB();

            //Krücke bisher
            //berechnung.FormulaFactors.instance ().init ();
        }

        return m_clInstance;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pfad TODO Missing Method Parameter Documentation
     * @param loader TODO Missing Method Parameter Documentation
     */
    public void setResource(String pfad, ClassLoader loader) {
        //Die Properies-Endung entfernen!
        //pfad = pfad.substring ( 0, pfad.indexOf ( ".properties" ) );

        /*
           //Protokoll entfernen
           pfad = pfad.replaceAll ( "file:",  "" );
           //! Aus Pfad entfernen
           pfad = pfad.replaceAll ( "!", "" );
        
        
           java.io.File sprachdatei = new java.io.File( pfad );
           HOLogger.instance().log(getClass(), "Sprachpfad " + pfad );
        
           if ( ! sprachdatei.exists () )
           {
               pfad = pfad.substring ( pfad.indexOf ( "sprache" ), pfad.length () );
               sprachdatei = new java.io.File( pfad );
               HOLogger.instance().log(getClass(), "2. Sprachpfad " + pfad );
           }
        
        
           if ( sprachdatei.exists () )
           {
         */
        m_clResource = new java.util.Properties();

        try {
            m_clResource.load(loader.getResourceAsStream("sprache/" + pfad + ".properties"));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        //m_clResource    =   java.util.ResourceBundle.getBundle ( pfad, java.util.Locale.getDefault (), loader );
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.util.Properties getResource() {
        return m_clResource;
    }

    /**
     * ersetzt das aktuelle model durch das aus der DB mit der angegebenen ID
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     */
    public void loadHoModel(int id) {
        m_clHoModel = loadModel(id);
    }

    /**
     * läadt das zuletzt importtiert model ein
     */
    public void loadLatestHoModel() {
        int id = de.hattrickorganizer.database.DBZugriff.instance().getLatestHrfId();
        m_clHoModel = loadModel(id);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param showWait TODO Missing Method Parameter Documentation
     * @param hrfDate TODO Missing Method Parameter Documentation
     */
    public void recalcSubskills(boolean showWait, Timestamp hrfDate) {
        if (hrfDate == null) {
            hrfDate = new Timestamp(0);
        }

        de.hattrickorganizer.gui.login.LoginWaitDialog waitDialog = null;

        if (showWait) {
            waitDialog = new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                            .instance(), false);
            waitDialog.setVisible(true);
        }

        final java.util.Vector hrfListe = new Vector();
        hrfListe.addAll(de.hattrickorganizer.database.DBZugriff.instance().getCBItemHRFListe(hrfDate));
        Collections.reverse(hrfListe);

        for (int i = 0; i < hrfListe.size(); i++) {
            try {
                if (showWait) {
                    waitDialog.setValue((int) (((double) i * 100d) / (double) hrfListe.size()));
                }

                final CBItem hrfItem = (de.hattrickorganizer.gui.model.CBItem) hrfListe.get(i);
                final HOModel model = this.loadModel(hrfItem.getId());
                model.calcSubskills();
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),"recalcSubskills : ");
                HOLogger.instance().log(getClass(),e);
            }
        }

        if (showWait) {
            waitDialog.setVisible(false);
        }

        //Erneut laden, da sich die Subskills geändert haben        
        loadLatestHoModel();

        de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
    }

    /**
     * interne Func die ein Model aus der DB lädt
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected HOModel loadModel(int id) {
        final HOModel model = new HOModel();
        model.setSpieler(de.hattrickorganizer.database.DBZugriff.instance().getSpieler(id));
        model.setAllOldSpieler(de.hattrickorganizer.database.DBZugriff.instance().getAllSpieler());

        model.setAufstellung(de.hattrickorganizer.database.DBZugriff.instance().getAufstellung(id,
                                                                                               Aufstellung.DEFAULT_NAME));
        model.setLastAufstellung(de.hattrickorganizer.database.DBZugriff.instance().getAufstellung(id,
                                                                                                   Aufstellung.DEFAULT_NAMELAST));
        model.setBasics(de.hattrickorganizer.database.DBZugriff.instance().getBasics(id));
        model.setFinanzen(de.hattrickorganizer.database.DBZugriff.instance().getFinanzen(id));
        model.setLiga(de.hattrickorganizer.database.DBZugriff.instance().getLiga(id));
        model.setStadium(de.hattrickorganizer.database.DBZugriff.instance().getStadion(id));
        model.setTeam(de.hattrickorganizer.database.DBZugriff.instance().getTeam(id));
        model.setVerein(de.hattrickorganizer.database.DBZugriff.instance().getVerein(id));
        model.setID(id);
        model.setSpielplan(de.hattrickorganizer.database.DBZugriff.instance().getSpielplan(-1, -1));
        model.setXtraDaten(de.hattrickorganizer.database.DBZugriff.instance().getXtraDaten(id));

        return model;
    }
}
