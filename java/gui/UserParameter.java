// %844311304:gui%
package gui;

import java.awt.Color;
import java.util.HashMap;

import plugins.ILineUp;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.Configuration;
import de.hattrickorganizer.tools.MyHelper;


/**
 * User configuration. Loaded when HO starts and saved when HO! exits.
 */
public final class UserParameter extends Configuration {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static UserParameter m_clUserParameter;

    //------Konstanten-----------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int SORT_NAME = 0;

    /** TODO Missing Parameter Documentation */
    public static final int SORT_BESTPOS = 1;

    /** TODO Missing Parameter Documentation */
    public static final int SORT_AUFGESTELLT = 2;

    /** TODO Missing Parameter Documentation */
    public static final int SORT_GRUPPE = 3;

    /** TODO Missing Parameter Documentation */
    public static final int SORT_BEWERTUNG = 4;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public Color FG_ANGESCHLAGEN = new Color(100, 0, 0);

    /** TODO Missing Parameter Documentation */
    public Color FG_GESPERRT = new Color(200, 20, 20);

    //Farben für Spielernamen

    /** TODO Missing Parameter Documentation */
    public Color FG_STANDARD = Color.black;

    /** TODO Missing Parameter Documentation */
    public Color FG_TRANSFERMARKT = new Color(0, 180, 0);

    /** TODO Missing Parameter Documentation */
    public Color FG_VERLETZT = new Color(200, 0, 0);

    /** TODO Missing Parameter Documentation */
    public Color FG_ZWEIKARTEN = new Color(100, 100, 0);

    /** TODO Missing Parameter Documentation */
    public String LoginName = "";

    /** TODO Missing Parameter Documentation */
    public String LoginPWD = "";

    /** TODO Missing Parameter Documentation */
    public String ProxyAuthName = "";

    /** TODO Missing Parameter Documentation */
    public String ProxyAuthPassword = "";

    //Proxy

    /** TODO Missing Parameter Documentation */
    public String ProxyHost = "";

    /** TODO Missing Parameter Documentation */
    public String ProxyPort = "";

    //AufstellungsAssistentPanel

    /** TODO Missing Parameter Documentation */
    public String aufstellungsAssistentPanel_gruppe = "";

    //Pfad

    /** TODO Missing Parameter Documentation */
    public String hrfImport_HRFPath = "";

    //IP von Hattrick

    /** TODO Missing Parameter Documentation */
    public String htip = "www1.hattrick.org";

    /** TODO Missing Parameter Documentation */
    public String matchLineupImport_Path = "";

    /** TODO Missing Parameter Documentation */
    public String spielPlanImport_Path = "";

    //Sprachdatei

    /** Name of language */
    public String sprachDatei = "English";

    /** is proxy activ */
    public boolean ProxyAktiv;

    /** is proxy authentification activ */
    public boolean ProxyAuthAktiv;

    /** is linup groupfilter activ */
    public boolean aufstellungsAssistentPanel_cbfilter;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_form = true;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_gesperrt;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_idealPosition;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_not;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_notLast;

    /** TODO Missing Parameter Documentation */
    public boolean aufstellungsAssistentPanel_verletzt;

    //Einzelnen Positionen in den Tabellen anzeigen

    /** @deprecated since HO! 1.36 columns configurable */
    public boolean einzelnePositionenAnzeigen = true;

    //Logout

    /** option parameter */
    public boolean logoutOnExit = true;

    //Dialog, wo mit welchem Namen das HRF gespeichert werden soll

    /** option parameter */
    public boolean showHRFSaveDialog = true;

    //Die Spieleranalyse wird vertikal untereinander gepackt oder nicht

    /** TODO Missing Parameter Documentation */
    public boolean spieleranalyseVertikal = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleBeschriftung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleErfahrung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleFluegel;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleForm = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleFuehrung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleHilfslinien = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleKondition = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAllePasspiel;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleSpielaufbau;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleStandards;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleTorschuss;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleTorwart;

    /** TODO Missing Parameter Documentation */
    public boolean statistikAlleVerteidigung;

    /** TODO Missing Parameter Documentation */
    public boolean statistikBeschriftung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikBewertung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikErfahrung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFananzahl;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFans;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFinanzenBeschriftung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFinanzenHilfslinien = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFluegel;

    /** TODO Missing Parameter Documentation */
    public boolean statistikForm = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikFuehrung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikGesamtAusgaben = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikGesamtEinnahmen = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikGewinnVerlust = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikHilfslinien = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikJugend;

    /** TODO Missing Parameter Documentation */
    public boolean statistikKondition = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikKontostand = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikMarktwert;

    /** TODO Missing Parameter Documentation */
    public boolean statistikPasspiel;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSonstigeAusgaben;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSonstigeEinnahmen;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpielaufbau;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleAbwehrzentrum = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleAngriffszentrum = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleBewertung = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleGesamt = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleLinkeAbwehr = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleLinkerAngriff = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleMittelfeld = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleRechteAbwehr = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleRechterAngriff = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleSelbstvertrauen;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpieleStimmung;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpielerFinanzenBeschriftung = true;

    /** TODO Missing Parameter Documentation */

    //Unused
    public boolean statistikSpielerFinanzenGehalt = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpielerFinanzenHilfslinien = true;

    /** TODO Missing Parameter Documentation */

    //Unused
    public boolean statistikSpielerFinanzenMarktwert = true;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSpielergehaelter;

    /** TODO Missing Parameter Documentation */
    public boolean statistikSponsoren;

    /** TODO Missing Parameter Documentation */
    public boolean statistikStadion;

    /** TODO Missing Parameter Documentation */
    public boolean statistikStandards;

    /** TODO Missing Parameter Documentation */
    public boolean statistikTorschuss;

    /** TODO Missing Parameter Documentation */
    public boolean statistikTorwart;

    /** TODO Missing Parameter Documentation */
    public boolean statistikTrainerstab;

    /** TODO Missing Parameter Documentation */
    public boolean statistikVerteidigung;

    /** TODO Missing Parameter Documentation */
    public boolean statistikZinsaufwendungen;

    /** TODO Missing Parameter Documentation */
    public boolean statistikZinsertraege;

    /** TODO Missing Parameter Documentation */
    public boolean statistikZuschauer;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabArenasizer = true;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabAufstellung;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabInformation = true;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabLigatabelle;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabSpiele;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabSpieleranalyse;

    //Temporäre Tabs

    /** TODO Missing Parameter Documentation */
    public boolean tempTabSpieleruebersicht;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabStatistik;

    /** TODO Missing Parameter Documentation */
    public boolean tempTabTransferscout = true;

    //Update
	public boolean newsCheck = true;
	
	public boolean userCheck = true;
    /** TODO Missing Parameter Documentation */
    public boolean updateCheck = true;
	
    //Zahlen hinter den Bewertungen anzeigen

    /** TODO Missing Parameter Documentation */
    public boolean zahlenFuerSkill = true;

    //Faktoren

    /** TODO Missing Parameter Documentation */
    public float AlterFaktor = 1.0f;

    /** TODO Missing Parameter Documentation */
    public float CoTrainerFaktor = 1.0f;

    /** TODO Missing Parameter Documentation */
    public float IntensitaetFaktor = 1.0f;

    /*gibt die mindeststärke für einen Spieler an für seine Idealpos um vom Aufstellungsassi bei idealpos gesetzt zu werden*/

    /** TODO Missing Parameter Documentation */
    public float MinIdealPosStk = 3.5f;

    /** TODO Missing Parameter Documentation */
    public float TrainerFaktor = 1.0f;

    //Prozentualer Abzug/Gewinn durch Wettereffekt

    /** TODO Missing Parameter Documentation */
    public float WetterEffektBonus = 0.2f;

    //Sonstiges
    //Faktor, durch den Geld geteilt werden muß für die Währung (Gleiche Währungsfaktoren zusammenfassen)

    /** TODO Missing Parameter Documentation */
    public float faktorGeld = 1f;

    //Faktor für Zeilenbreite in den Tabellen, Wird nicht gespeichert, sondern berechnet    

    /** TODO Missing Parameter Documentation */
    public float zellenbreitenFaktor = 1.0f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_ALLGEMEIN = 1.0f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_CHANCENVERWERTUNG = 5.2f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_FLUEGELSPIEL = 3.8f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_KONDITION = 1.0f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_PASSPIEL = 4.5f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_SPIELAUFBAU = 5.5f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_STANDARDS = 2.0f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_TORWART = 3.7f;

    /** TODO Missing Parameter Documentation */
    public float DAUER_VERTEIDIGUNG = 6.1f;

    //Timezone

    /** TODO Missing Parameter Documentation */
    public int TimeZoneDifference;

    //Anzahl Nachkommastellen

    /** TODO Missing Parameter Documentation */
    public int anzahlNachkommastellen = 1;

    /** TODO Missing Parameter Documentation */
    public int aufstellungsAssistentPanel_reihenfolge = ILineUp.AW_MF_ST;

    /** TODO Missing Parameter Documentation */
    public int aufstellungsPanel_horizontalLeftSplitPane = 450;

    /** TODO Missing Parameter Documentation */
    public int aufstellungsPanel_horizontalRightSplitPane = 200;

    /** TODO Missing Parameter Documentation */
    public int aufstellungsPanel_verticalSplitPane = 800;

    //AufstellungsPanel

    /** TODO Missing Parameter Documentation */
    public int aufstellungsPanel_verticalSplitPaneLow = 250;

    //Breite der BestPos-Spalte

    /** @deprecated column width configurable
     * @since 1.361 
     * */
    public int bestPostWidth = 140;

    //Wecker vor der Deadliniezeit

    /** TODO Missing Parameter Documentation */
    public int deadlineFrist = 300000;

    /** TODO Missing Parameter Documentation */
    public int hoMainFrame_PositionX;

    /** TODO Missing Parameter Documentation */
    public int hoMainFrame_PositionY;

    /** TODO Missing Parameter Documentation */
    public int hoMainFrame_height = 740;

    //------Werte----------------------------------------------------
    //HOMainFrame

    /** TODO Missing Parameter Documentation */
    public int hoMainFrame_width = 1024;

    //MiniScout

    /** TODO Missing Parameter Documentation */
    public int miniscout_PositionX = 50;

    /** TODO Missing Parameter Documentation */
    public int miniscout_PositionY = 50;

    //Textgroesse

    /** TODO Missing Parameter Documentation */
    public int schriftGroesse = 11;

    //Spiele

    /** TODO Missing Parameter Documentation */
    public int spieleFilter;

    //Spiele

    /** TODO Missing Parameter Documentation */
    public int spielePanel_horizontalLeftSplitPane = 400;

    /** TODO Missing Parameter Documentation */
    public int spielePanel_horizontalRightSplitPane = 310;

    /** TODO Missing Parameter Documentation */
    public int spielePanel_verticalSplitPane = 440;

    //SpielerAnalyse

    /** TODO Missing Parameter Documentation */
    public int spielerAnalysePanel_horizontalSplitPane = 400;

    //Position SpielerDetails

    /** TODO Missing Parameter Documentation */
    public int spielerDetails_PositionX = 50;

    /** TODO Missing Parameter Documentation */
    public int spielerDetails_PositionY = 50;
	
    public int futureWeeks = 16;

    /** TODO Missing Parameter Documentation */
    public int spielerUebersichtsPanel_horizontalLeftSplitPane = 700;

    /** TODO Missing Parameter Documentation */
    public int spielerUebersichtsPanel_horizontalRightSplitPane = 750;

    /** TODO Missing Parameter Documentation */
    public int spielerUebersichtsPanel_verticalSplitPane = 400;

    //Standardsortierung

    /** TODO Missing Parameter Documentation */
    public int standardsortierung = SORT_BESTPOS;

    //AlleSpielerstatistik

    /** TODO Missing Parameter Documentation */
    public int statistikAlleAnzahlHRF = 50;

    //Spielerstatistik

    /** TODO Missing Parameter Documentation */
    public int statistikAnzahlHRF = 50;

    //Finanzstatistik

    /** TODO Missing Parameter Documentation */
    public int statistikFinanzenAnzahlHRF = 50;

    /** TODO Missing Parameter Documentation */

    //Alle eigenen
    public int statistikSpieleFilter = 11;

    //SpielerFinanzenStatistikPanel
    //Wird nun für SpieleStatistik verwendet!

    /** TODO Missing Parameter Documentation */
    public int statistikSpielerFinanzenAnzahlHRF = 50;

    //TransferScoutPanel

    /** TODO Missing Parameter Documentation */
    public int transferScoutPanel_horizontalSplitPane = 300;

    //Id der Währung

    /** TODO Missing Parameter Documentation */
	
	// Rating offset
	public float leftDefenceOffset = 0.0f;
	public float middleDefenceOffset = 0.0f;
	public float rightDefenceOffset = 0.0f;
	public float midfieldOffset = 0.0f;
	public float leftAttackOffset = 0.0f;
	public float middleAttackOffset = 0.0f;
	public float rightAttackfOffset = 0.0f;
    //veraltet!!
    public int waehrungsID = 3;

	public int simulatorMatches = 0;
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UserParameter object.
     */
    private UserParameter() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return singelton instance
     */
    public static UserParameter instance() {
        if (m_clUserParameter == null) {
            m_clUserParameter = new UserParameter();
        }

        return m_clUserParameter;
    }

	public HashMap getValues() {
		HashMap map = new HashMap();
		map.put("FG_ANGESCHLAGEN",String.valueOf(FG_ANGESCHLAGEN.getRGB()));
		map.put("FG_GESPERRT",String.valueOf(FG_GESPERRT.getRGB()));
		map.put("FG_STANDARD",String.valueOf(FG_STANDARD.getRGB()));
		map.put("FG_TRANSFERMARKT",String.valueOf(FG_TRANSFERMARKT.getRGB()));
		map.put("FG_VERLETZT",String.valueOf(FG_VERLETZT.getRGB()));
		map.put("FG_ZWEIKARTEN",String.valueOf(FG_ZWEIKARTEN.getRGB()));
		map.put("LoginName",String.valueOf(LoginName));
		map.put("LoginPWD",MyHelper.cryptString(String.valueOf(LoginPWD)));
		map.put("ProxyAuthName",String.valueOf(ProxyAuthName));
		map.put("ProxyAuthPassword",String.valueOf(ProxyAuthPassword));
		map.put("ProxyHost",String.valueOf(ProxyHost));
		map.put("ProxyPort",String.valueOf(ProxyPort));
		map.put("aufstellungsAssistentPanel_gruppe",String.valueOf(aufstellungsAssistentPanel_gruppe));
		map.put("hrfImport_HRFPath",DBZugriff.insertEscapeSequences(String.valueOf(hrfImport_HRFPath)));
		map.put("htip",String.valueOf(htip));
		map.put("matchLineupImport_Path",String.valueOf(matchLineupImport_Path));
		map.put("spielPlanImport_Path",String.valueOf(spielPlanImport_Path));
		map.put("sprachDatei",String.valueOf(sprachDatei));
		map.put("ProxyAktiv",String.valueOf(ProxyAktiv));
		map.put("ProxyAuthAktiv",String.valueOf(ProxyAuthAktiv));
		map.put("aufstellungsAssistentPanel_cbfilter",String.valueOf(aufstellungsAssistentPanel_cbfilter));
		map.put("aufstellungsAssistentPanel_form",String.valueOf(aufstellungsAssistentPanel_form));
		map.put("aufstellungsAssistentPanel_gesperrt",String.valueOf(aufstellungsAssistentPanel_gesperrt));
		map.put("aufstellungsAssistentPanel_idealPosition",String.valueOf(aufstellungsAssistentPanel_idealPosition));
		map.put("aufstellungsAssistentPanel_not",String.valueOf(aufstellungsAssistentPanel_not));
		map.put("aufstellungsAssistentPanel_notLast",String.valueOf(aufstellungsAssistentPanel_notLast));
		map.put("aufstellungsAssistentPanel_verletzt",String.valueOf(aufstellungsAssistentPanel_verletzt));
		map.put("einzelnePositionenAnzeigen",String.valueOf(einzelnePositionenAnzeigen));
		map.put("logoutOnExit",String.valueOf(logoutOnExit));
		map.put("showHRFSaveDialog",String.valueOf(showHRFSaveDialog));
		map.put("spieleranalyseVertikal",String.valueOf(spieleranalyseVertikal));
		map.put("statistikAlleBeschriftung",String.valueOf(statistikAlleBeschriftung));
		map.put("statistikAlleErfahrung",String.valueOf(statistikAlleErfahrung));
		map.put("statistikAlleFluegel",String.valueOf(statistikAlleFluegel));
		map.put("statistikAlleForm",String.valueOf(statistikAlleForm));
		map.put("statistikAlleFuehrung",String.valueOf(statistikAlleFuehrung));
		map.put("statistikAlleHilfslinien",String.valueOf(statistikAlleHilfslinien));
		map.put("statistikAlleKondition",String.valueOf(statistikAlleKondition));
		map.put("statistikAllePasspiel",String.valueOf(statistikAllePasspiel));
		map.put("statistikAlleSpielaufbau",String.valueOf(statistikAlleSpielaufbau));
		map.put("statistikAlleStandards",String.valueOf(statistikAlleStandards));
		map.put("statistikAlleTorschuss",String.valueOf(statistikAlleTorschuss));
		map.put("statistikAlleTorwart",String.valueOf(statistikAlleTorwart));
		map.put("statistikAlleVerteidigung",String.valueOf(statistikAlleVerteidigung));
		map.put("statistikBeschriftung",String.valueOf(statistikBeschriftung));
		map.put("statistikBewertung",String.valueOf(statistikBewertung));
		map.put("statistikErfahrung",String.valueOf(statistikErfahrung));
		map.put("statistikFananzahl",String.valueOf(statistikFananzahl));
		map.put("statistikFans",String.valueOf(statistikFans));
		map.put("statistikFinanzenBeschriftung",String.valueOf(statistikFinanzenBeschriftung));
		map.put("statistikFinanzenHilfslinien",String.valueOf(statistikFinanzenHilfslinien));
		map.put("statistikFluegel",String.valueOf(statistikFluegel));
		map.put("statistikForm",String.valueOf(statistikForm));
		map.put("statistikFuehrung",String.valueOf(statistikFuehrung));
		map.put("statistikGesamtAusgaben",String.valueOf(statistikGesamtAusgaben));
		map.put("statistikGesamtEinnahmen",String.valueOf(statistikGesamtEinnahmen));
		map.put("statistikGewinnVerlust",String.valueOf(statistikGewinnVerlust));
		map.put("statistikHilfslinien",String.valueOf(statistikHilfslinien));
		map.put("statistikJugend",String.valueOf(statistikJugend));
		map.put("statistikKondition",String.valueOf(statistikKondition));
		map.put("statistikKontostand",String.valueOf(statistikKontostand));
		map.put("statistikMarktwert",String.valueOf(statistikMarktwert));
		map.put("statistikPasspiel",String.valueOf(statistikPasspiel));
		map.put("statistikSonstigeAusgaben",String.valueOf(statistikSonstigeAusgaben));
		map.put("statistikSonstigeEinnahmen",String.valueOf(statistikSonstigeEinnahmen));
		map.put("statistikSpielaufbau",String.valueOf(statistikSpielaufbau));
		map.put("statistikSpieleAbwehrzentrum",String.valueOf(statistikSpieleAbwehrzentrum));
		map.put("statistikSpieleAngriffszentrum",String.valueOf(statistikSpieleAngriffszentrum));
		map.put("statistikSpieleBewertung",String.valueOf(statistikSpieleBewertung));
		map.put("statistikSpieleGesamt",String.valueOf(statistikSpieleGesamt));
		map.put("statistikSpieleLinkeAbwehr",String.valueOf(statistikSpieleLinkeAbwehr));
		map.put("statistikSpieleLinkerAngriff",String.valueOf(statistikSpieleLinkerAngriff));
		map.put("statistikSpieleMittelfeld",String.valueOf(statistikSpieleMittelfeld));
		map.put("statistikSpieleRechteAbwehr",String.valueOf(statistikSpieleRechteAbwehr));
		map.put("statistikSpieleRechterAngriff",String.valueOf(statistikSpieleRechterAngriff));
		map.put("statistikSpieleSelbstvertrauen",String.valueOf(statistikSpieleSelbstvertrauen));
		map.put("statistikSpieleStimmung",String.valueOf(statistikSpieleStimmung));
		map.put("statistikSpielerFinanzenBeschriftung",String.valueOf(statistikSpielerFinanzenBeschriftung));
		map.put("statistikSpielerFinanzenGehalt",String.valueOf(statistikSpielerFinanzenGehalt));
		map.put("statistikSpielerFinanzenHilfslinien",String.valueOf(statistikSpielerFinanzenHilfslinien));
		map.put("statistikSpielerFinanzenMarktwert",String.valueOf(statistikSpielerFinanzenMarktwert));
		map.put("statistikSpielergehaelter",String.valueOf(statistikSpielergehaelter));
		map.put("statistikSponsoren",String.valueOf(statistikSponsoren));
		map.put("statistikStadion",String.valueOf(statistikStadion));
		map.put("statistikStandards",String.valueOf(statistikStandards));
		map.put("statistikTorschuss",String.valueOf(statistikTorschuss));
		map.put("statistikTorwart",String.valueOf(statistikTorwart));
		map.put("statistikTrainerstab",String.valueOf(statistikTrainerstab));
		map.put("statistikVerteidigung",String.valueOf(statistikVerteidigung));
		map.put("statistikZinsaufwendungen",String.valueOf(statistikZinsaufwendungen));
		map.put("statistikZinsertraege",String.valueOf(statistikZinsertraege));
		map.put("statistikZuschauer",String.valueOf(statistikZuschauer));
		map.put("tempTabArenasizer",String.valueOf(tempTabArenasizer));
		map.put("tempTabAufstellung",String.valueOf(tempTabAufstellung));
		map.put("tempTabInformation",String.valueOf(tempTabInformation));
		map.put("tempTabLigatabelle",String.valueOf(tempTabLigatabelle));
		map.put("tempTabSpiele",String.valueOf(tempTabSpiele));
		map.put("tempTabSpieleranalyse",String.valueOf(tempTabSpieleranalyse));
		map.put("tempTabSpieleruebersicht",String.valueOf(tempTabSpieleruebersicht));
		map.put("tempTabStatistik",String.valueOf(tempTabStatistik));
		map.put("tempTabTransferscout",String.valueOf(tempTabTransferscout));
		map.put("newsCheck",String.valueOf(newsCheck));
		map.put("userCheck",String.valueOf(userCheck));
		map.put("updateCheck",String.valueOf(updateCheck));
		map.put("zahlenFuerSkill",String.valueOf(zahlenFuerSkill));
		map.put("AlterFaktor",String.valueOf(AlterFaktor));
		map.put("CoTrainerFaktor",String.valueOf(CoTrainerFaktor));
		map.put("IntensitaetFaktor",String.valueOf(IntensitaetFaktor));
		map.put("MinIdealPosStk",String.valueOf(MinIdealPosStk));
		map.put("TrainerFaktor",String.valueOf(TrainerFaktor));
		map.put("WetterEffektBonus",String.valueOf(WetterEffektBonus));
		map.put("faktorGeld",String.valueOf(faktorGeld));
		map.put("zellenbreitenFaktor",String.valueOf(zellenbreitenFaktor));
		map.put("DAUER_ALLGEMEIN",String.valueOf(DAUER_ALLGEMEIN));
		map.put("DAUER_CHANCENVERWERTUNG",String.valueOf(DAUER_CHANCENVERWERTUNG));
		map.put("DAUER_FLUEGELSPIEL",String.valueOf(DAUER_FLUEGELSPIEL));
		map.put("DAUER_KONDITION",String.valueOf(DAUER_KONDITION));
		map.put("DAUER_PASSPIEL",String.valueOf(DAUER_PASSPIEL));
		map.put("DAUER_SPIELAUFBAU",String.valueOf(DAUER_SPIELAUFBAU));
		map.put("DAUER_STANDARDS",String.valueOf(DAUER_STANDARDS));
		map.put("DAUER_TORWART",String.valueOf(DAUER_TORWART));
		map.put("DAUER_VERTEIDIGUNG",String.valueOf(DAUER_VERTEIDIGUNG));
		map.put("TimeZoneDifference",String.valueOf(TimeZoneDifference));
		map.put("anzahlNachkommastellen",String.valueOf(anzahlNachkommastellen));
		map.put("aufstellungsAssistentPanel_reihenfolge",String.valueOf(aufstellungsAssistentPanel_reihenfolge));
		map.put("aufstellungsPanel_horizontalLeftSplitPane",String.valueOf(aufstellungsPanel_horizontalLeftSplitPane));
		map.put("aufstellungsPanel_horizontalRightSplitPane",String.valueOf(aufstellungsPanel_horizontalRightSplitPane));
		map.put("aufstellungsPanel_verticalSplitPane",String.valueOf(aufstellungsPanel_verticalSplitPane));
		map.put("aufstellungsPanel_verticalSplitPaneLow",String.valueOf(aufstellungsPanel_verticalSplitPaneLow));
		map.put("bestPostWidth",String.valueOf(bestPostWidth));
		map.put("deadlineFrist",String.valueOf(deadlineFrist));
		map.put("hoMainFrame_PositionX",String.valueOf(hoMainFrame_PositionX));
		map.put("hoMainFrame_PositionY",String.valueOf(hoMainFrame_PositionY));
		map.put("hoMainFrame_height",String.valueOf(hoMainFrame_height));
		map.put("hoMainFrame_width",String.valueOf(hoMainFrame_width));
		map.put("miniscout_PositionX",String.valueOf(miniscout_PositionX));
		map.put("miniscout_PositionY",String.valueOf(miniscout_PositionY));
		map.put("schriftGroesse",String.valueOf(schriftGroesse));
		map.put("spieleFilter",String.valueOf(spieleFilter));
		map.put("spielePanel_horizontalLeftSplitPane",String.valueOf(spielePanel_horizontalLeftSplitPane));
		map.put("spielePanel_horizontalRightSplitPane",String.valueOf(spielePanel_horizontalRightSplitPane));
		map.put("spielePanel_verticalSplitPane",String.valueOf(spielePanel_verticalSplitPane));
		map.put("spielerAnalysePanel_horizontalSplitPane",String.valueOf(spielerAnalysePanel_horizontalSplitPane));
		map.put("spielerDetails_PositionX",String.valueOf(spielerDetails_PositionX));
		map.put("spielerDetails_PositionY",String.valueOf(spielerDetails_PositionY));
		map.put("futureWeeks",String.valueOf(futureWeeks));
		map.put("spielerUebersichtsPanel_horizontalLeftSplitPane",String.valueOf(spielerUebersichtsPanel_horizontalLeftSplitPane));
		map.put("spielerUebersichtsPanel_horizontalRightSplitPane",String.valueOf(spielerUebersichtsPanel_horizontalRightSplitPane));
		map.put("spielerUebersichtsPanel_verticalSplitPane",String.valueOf(spielerUebersichtsPanel_verticalSplitPane));
		map.put("standardsortierung",String.valueOf(standardsortierung));
		map.put("statistikAlleAnzahlHRF",String.valueOf(statistikAlleAnzahlHRF));
		map.put("statistikAnzahlHRF",String.valueOf(statistikAnzahlHRF));
		map.put("statistikFinanzenAnzahlHRF",String.valueOf(statistikFinanzenAnzahlHRF));
		map.put("statistikSpieleFilter",String.valueOf(statistikSpieleFilter));
		map.put("statistikSpielerFinanzenAnzahlHRF",String.valueOf(statistikSpielerFinanzenAnzahlHRF));
		map.put("transferScoutPanel_horizontalSplitPane",String.valueOf(transferScoutPanel_horizontalSplitPane));
		map.put("leftDefenceOffset",String.valueOf(leftDefenceOffset));
		map.put("middleDefenceOffset",String.valueOf(middleDefenceOffset));
		map.put("rightDefenceOffset",String.valueOf(rightDefenceOffset));
		map.put("midfieldOffset",String.valueOf(midfieldOffset));
		map.put("leftAttackOffset",String.valueOf(leftAttackOffset));
		map.put("middleAttackOffset",String.valueOf(middleAttackOffset));
		map.put("rightAttackfOffset",String.valueOf(rightAttackfOffset));
		map.put("waehrungsID",String.valueOf(waehrungsID));
		map.put("simulatorMatches",String.valueOf(simulatorMatches));		
		return map;
	}

	public void setValues(HashMap values) {
		FG_ANGESCHLAGEN = getColorValue(values,"FG_ANGESCHLAGEN");
		FG_GESPERRT = getColorValue(values,"FG_GESPERRT");
		FG_STANDARD = getColorValue(values,"FG_STANDARD");
		FG_TRANSFERMARKT = getColorValue(values,"FG_TRANSFERMARKT");
		FG_VERLETZT = getColorValue(values,"FG_VERLETZT");
		FG_ZWEIKARTEN = getColorValue(values,"FG_ZWEIKARTEN");
		
		LoginName = getStringValue(values,"LoginName");
		LoginPWD = MyHelper.decryptString(getStringValue(values,"LoginPWD"));
		ProxyAuthName = getStringValue(values,"ProxyAuthName");
		ProxyAuthPassword = getStringValue(values,"ProxyAuthPassword");
		ProxyHost = getStringValue(values,"ProxyHost");
		ProxyPort = getStringValue(values,"ProxyPort");
		aufstellungsAssistentPanel_gruppe = getStringValue(values,"aufstellungsAssistentPanel_gruppe");
		hrfImport_HRFPath = DBZugriff.deleteEscapeSequences(getStringValue(values,"hrfImport_HRFPath"));
		htip = getStringValue(values,"htip");
		matchLineupImport_Path = getStringValue(values,"matchLineupImport_Path");
		spielPlanImport_Path = getStringValue(values,"spielPlanImport_Path");
		sprachDatei = getStringValue(values,"sprachDatei");
		
		ProxyAktiv = getBooleanValue(values,"ProxyAktiv");
		ProxyAuthAktiv = getBooleanValue(values,"ProxyAuthAktiv");
		aufstellungsAssistentPanel_cbfilter = getBooleanValue(values,"aufstellungsAssistentPanel_cbfilter");
		aufstellungsAssistentPanel_form = getBooleanValue(values,"aufstellungsAssistentPanel_form");
		aufstellungsAssistentPanel_gesperrt = getBooleanValue(values,"aufstellungsAssistentPanel_gesperrt");
		aufstellungsAssistentPanel_idealPosition = getBooleanValue(values,"aufstellungsAssistentPanel_idealPosition");
		aufstellungsAssistentPanel_not = getBooleanValue(values,"aufstellungsAssistentPanel_not");
		aufstellungsAssistentPanel_notLast = getBooleanValue(values,"aufstellungsAssistentPanel_notLast");
		aufstellungsAssistentPanel_verletzt = getBooleanValue(values,"aufstellungsAssistentPanel_verletzt");
		einzelnePositionenAnzeigen = getBooleanValue(values,"einzelnePositionenAnzeigen");
		logoutOnExit = getBooleanValue(values,"logoutOnExit");
		showHRFSaveDialog = getBooleanValue(values,"showHRFSaveDialog");
		spieleranalyseVertikal = getBooleanValue(values,"spieleranalyseVertikal");
		statistikAlleBeschriftung = getBooleanValue(values,"statistikAlleBeschriftung");
		statistikAlleErfahrung = getBooleanValue(values,"statistikAlleErfahrung");
		statistikAlleFluegel = getBooleanValue(values,"statistikAlleFluegel");
		statistikAlleForm = getBooleanValue(values,"statistikAlleForm");
		statistikAlleFuehrung = getBooleanValue(values,"statistikAlleFuehrung");
		statistikAlleHilfslinien = getBooleanValue(values,"statistikAlleHilfslinien");
		statistikAlleKondition = getBooleanValue(values,"statistikAlleKondition");
		statistikAllePasspiel = getBooleanValue(values,"statistikAllePasspiel");
		statistikAlleSpielaufbau = getBooleanValue(values,"statistikAlleSpielaufbau");
		statistikAlleStandards = getBooleanValue(values,"statistikAlleStandards");
		statistikAlleTorschuss = getBooleanValue(values,"statistikAlleTorschuss");
		statistikAlleTorwart = getBooleanValue(values,"statistikAlleTorwart");
		statistikAlleVerteidigung = getBooleanValue(values,"statistikAlleVerteidigung");
		statistikBeschriftung = getBooleanValue(values,"statistikBeschriftung");
		statistikBewertung = getBooleanValue(values,"statistikBewertung");
		statistikErfahrung = getBooleanValue(values,"statistikErfahrung");
		statistikFananzahl = getBooleanValue(values,"statistikFananzahl");
		statistikFans = getBooleanValue(values,"statistikFans");
		statistikFinanzenBeschriftung = getBooleanValue(values,"statistikFinanzenBeschriftung");
		statistikFinanzenHilfslinien = getBooleanValue(values,"statistikFinanzenHilfslinien");
		statistikFluegel = getBooleanValue(values,"statistikFluegel");
		statistikForm = getBooleanValue(values,"statistikForm");
		statistikFuehrung = getBooleanValue(values,"statistikFuehrung");
		statistikGesamtAusgaben = getBooleanValue(values,"statistikGesamtAusgaben");
		statistikGesamtEinnahmen = getBooleanValue(values,"statistikGesamtEinnahmen");
		statistikGewinnVerlust = getBooleanValue(values,"statistikGewinnVerlust");
		statistikHilfslinien = getBooleanValue(values,"statistikHilfslinien");
		statistikJugend = getBooleanValue(values,"statistikJugend");
		statistikKondition = getBooleanValue(values,"statistikKondition");
		statistikKontostand = getBooleanValue(values,"statistikKontostand");
		statistikMarktwert = getBooleanValue(values,"statistikMarktwert");
		statistikPasspiel = getBooleanValue(values,"statistikPasspiel");
		statistikSonstigeAusgaben = getBooleanValue(values,"statistikSonstigeAusgaben");
		statistikSonstigeEinnahmen = getBooleanValue(values,"statistikSonstigeEinnahmen");
		statistikSpielaufbau = getBooleanValue(values,"statistikSpielaufbau");
		statistikSpieleAbwehrzentrum = getBooleanValue(values,"statistikSpieleAbwehrzentrum");
		statistikSpieleAngriffszentrum = getBooleanValue(values,"statistikSpieleAngriffszentrum");
		statistikSpieleBewertung = getBooleanValue(values,"statistikSpieleBewertung");
		statistikSpieleGesamt = getBooleanValue(values,"statistikSpieleGesamt");
		statistikSpieleLinkeAbwehr = getBooleanValue(values,"statistikSpieleLinkeAbwehr");
		statistikSpieleLinkerAngriff = getBooleanValue(values,"statistikSpieleLinkerAngriff");
		statistikSpieleMittelfeld = getBooleanValue(values,"statistikSpieleMittelfeld");
		statistikSpieleRechteAbwehr = getBooleanValue(values,"statistikSpieleRechteAbwehr");
		statistikSpieleRechterAngriff = getBooleanValue(values,"statistikSpieleRechterAngriff");
		statistikSpieleSelbstvertrauen = getBooleanValue(values,"statistikSpieleSelbstvertrauen");
		statistikSpieleStimmung = getBooleanValue(values,"statistikSpieleStimmung");
		statistikSpielerFinanzenBeschriftung = getBooleanValue(values,"statistikSpielerFinanzenBeschriftung");
		statistikSpielerFinanzenGehalt = getBooleanValue(values,"statistikSpielerFinanzenGehalt");
		statistikSpielerFinanzenHilfslinien = getBooleanValue(values,"statistikSpielerFinanzenHilfslinien");
		statistikSpielerFinanzenMarktwert = getBooleanValue(values,"statistikSpielerFinanzenMarktwert");
		statistikSpielergehaelter = getBooleanValue(values,"statistikSpielergehaelter");
		statistikSponsoren = getBooleanValue(values,"statistikSponsoren");
		statistikStadion = getBooleanValue(values,"statistikStadion");
		statistikStandards = getBooleanValue(values,"statistikStandards");
		statistikTorschuss = getBooleanValue(values,"statistikTorschuss");
		statistikTorwart = getBooleanValue(values,"statistikTorwart");
		statistikTrainerstab = getBooleanValue(values,"statistikTrainerstab");
		statistikVerteidigung = getBooleanValue(values,"statistikVerteidigung");
		statistikZinsaufwendungen = getBooleanValue(values,"statistikZinsaufwendungen");
		statistikZinsertraege = getBooleanValue(values,"statistikZinsertraege");
		statistikZuschauer = getBooleanValue(values,"statistikZuschauer");
		tempTabArenasizer = getBooleanValue(values,"tempTabArenasizer");
		tempTabAufstellung = getBooleanValue(values,"tempTabAufstellung");
		tempTabInformation = getBooleanValue(values,"tempTabInformation");
		tempTabLigatabelle = getBooleanValue(values,"tempTabLigatabelle");
		tempTabSpiele = getBooleanValue(values,"tempTabSpiele");
		tempTabSpieleranalyse = getBooleanValue(values,"tempTabSpieleranalyse");
		tempTabSpieleruebersicht = getBooleanValue(values,"tempTabSpieleruebersicht");
		tempTabStatistik = getBooleanValue(values,"tempTabStatistik");	
		tempTabTransferscout = getBooleanValue(values,"tempTabTransferscout");
		newsCheck = getBooleanValue(values,"newsCheck");
		userCheck = getBooleanValue(values,"userCheck");
		updateCheck = getBooleanValue(values,"updateCheck");
		zahlenFuerSkill = getBooleanValue(values,"zahlenFuerSkill");
		
		AlterFaktor = getFloatValue(values,"AlterFaktor");
		CoTrainerFaktor = getFloatValue(values,"CoTrainerFaktor");
		IntensitaetFaktor = getFloatValue(values,"IntensitaetFaktor");
		MinIdealPosStk = getFloatValue(values,"MinIdealPosStk");
		TrainerFaktor = getFloatValue(values,"TrainerFaktor");
		WetterEffektBonus = getFloatValue(values,"WetterEffektBonus");
		faktorGeld = getFloatValue(values,"faktorGeld");
		zellenbreitenFaktor = getFloatValue(values,"zellenbreitenFaktor");
		leftDefenceOffset = getFloatValue(values,"leftDefenceOffset");
		middleDefenceOffset = getFloatValue(values,"middleDefenceOffset");
		rightDefenceOffset = getFloatValue(values,"rightDefenceOffset");
		midfieldOffset = getFloatValue(values,"midfieldOffset");
		leftAttackOffset = getFloatValue(values,"leftAttackOffset");
		middleAttackOffset = getFloatValue(values,"middleAttackOffset");
		rightAttackfOffset = getFloatValue(values,"rightAttackfOffset");			
		DAUER_ALLGEMEIN = getFloatValue(values,"DAUER_ALLGEMEIN");
		DAUER_CHANCENVERWERTUNG = getFloatValue(values,"DAUER_CHANCENVERWERTUNG");
		DAUER_FLUEGELSPIEL = getFloatValue(values,"DAUER_FLUEGELSPIEL");
		DAUER_KONDITION = getFloatValue(values,"DAUER_KONDITION");
		DAUER_PASSPIEL = getFloatValue(values,"DAUER_PASSPIEL");
		DAUER_SPIELAUFBAU = getFloatValue(values,"DAUER_SPIELAUFBAU");
		DAUER_STANDARDS = getFloatValue(values,"DAUER_STANDARDS");
		DAUER_TORWART = getFloatValue(values,"DAUER_TORWART");
		DAUER_VERTEIDIGUNG = getFloatValue(values,"DAUER_VERTEIDIGUNG");

		TimeZoneDifference = getIntValue(values,"TimeZoneDifference");
		anzahlNachkommastellen = getIntValue(values,"anzahlNachkommastellen");
		aufstellungsAssistentPanel_reihenfolge = getIntValue(values,"aufstellungsAssistentPanel_reihenfolge");
		aufstellungsPanel_horizontalLeftSplitPane = getIntValue(values,"aufstellungsPanel_horizontalLeftSplitPane");
		aufstellungsPanel_horizontalRightSplitPane = getIntValue(values,"aufstellungsPanel_horizontalRightSplitPane");
		aufstellungsPanel_verticalSplitPane = getIntValue(values,"aufstellungsPanel_verticalSplitPane");
		aufstellungsPanel_verticalSplitPaneLow = getIntValue(values,"aufstellungsPanel_verticalSplitPaneLow");
		bestPostWidth = getIntValue(values,"bestPostWidth");
		deadlineFrist = getIntValue(values,"deadlineFrist");
		hoMainFrame_PositionX = getIntValue(values,"hoMainFrame_PositionX");
		hoMainFrame_PositionY = getIntValue(values,"hoMainFrame_PositionY");
		hoMainFrame_height = getIntValue(values,"hoMainFrame_height");
		hoMainFrame_width = getIntValue(values,"hoMainFrame_width");
		miniscout_PositionX = getIntValue(values,"miniscout_PositionX");
		miniscout_PositionY = getIntValue(values,"miniscout_PositionY");
		schriftGroesse = getIntValue(values,"schriftGroesse");
		spieleFilter = getIntValue(values,"spieleFilter");
		spielePanel_horizontalLeftSplitPane = getIntValue(values,"spielePanel_horizontalLeftSplitPane");
		spielePanel_horizontalRightSplitPane = getIntValue(values,"spielePanel_horizontalRightSplitPane");
		spielePanel_verticalSplitPane = getIntValue(values,"spielePanel_verticalSplitPane");
		spielerAnalysePanel_horizontalSplitPane = getIntValue(values,"spielerAnalysePanel_horizontalSplitPane");
		spielerDetails_PositionX = getIntValue(values,"spielerDetails_PositionX");
		spielerDetails_PositionY = getIntValue(values,"spielerDetails_PositionY");
		futureWeeks = getIntValue(values,"futureWeeks");
		spielerUebersichtsPanel_horizontalLeftSplitPane = getIntValue(values,"spielerUebersichtsPanel_horizontalLeftSplitPane");
		spielerUebersichtsPanel_horizontalRightSplitPane = getIntValue(values,"spielerUebersichtsPanel_horizontalRightSplitPane");
		spielerUebersichtsPanel_verticalSplitPane = getIntValue(values,"spielerUebersichtsPanel_verticalSplitPane");
		standardsortierung = getIntValue(values,"standardsortierung");
		statistikAlleAnzahlHRF = getIntValue(values,"statistikAlleAnzahlHRF");
		statistikAnzahlHRF = getIntValue(values,"statistikAnzahlHRF");
		statistikFinanzenAnzahlHRF = getIntValue(values,"statistikFinanzenAnzahlHRF");
		statistikSpieleFilter = getIntValue(values,"statistikSpieleFilter");
		statistikSpielerFinanzenAnzahlHRF = getIntValue(values,"statistikSpielerFinanzenAnzahlHRF");
		transferScoutPanel_horizontalSplitPane = getIntValue(values,"transferScoutPanel_horizontalSplitPane");
		waehrungsID = getIntValue(values,"waehrungsID");
		simulatorMatches = getIntValue(values,"simulatorMatches");
	}

}
