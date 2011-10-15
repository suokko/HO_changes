// %1117664849000:hoplugins.conv%
/*
 * Created on 17.11.2004
 *
 */
package hoplugins.conv;

import plugins.IHOMiniModel;

import java.io.File;

import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Dietz
 */
public final class RSC {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected static IHOMiniModel MINIMODEL = null;

    /** TODO Missing Parameter Documentation */
    protected static final byte TYPE_BUDDY = 0;

    /** TODO Missing Parameter Documentation */
    protected static final byte TYPE_HAM = 1;

    /** TODO Missing Parameter Documentation */
    protected static final byte TYPE_HTFOREVER = 2;

    /** TODO Missing Parameter Documentation */
    protected static final byte TYPE_HTCOACH = 3;
    private static Properties properties = null;

    /** TODO Missing Parameter Documentation */
    public static String NAME = "Converter";

    /** TODO Missing Parameter Documentation */
    public static String BUDDY_TO_HRF = "Tools => hrf";

    /** TODO Missing Parameter Documentation */
    public static String DB_TO_HRF = "DB => hrf";

    /** TODO Missing Parameter Documentation */
    protected static String[] BUDDY_EXTENSION = new String[]{
                                                    ".arenaDetails.xml", ".club.xml", ".economy.xml",
                                                    ".players.xml", ".teamDetails.xml",
                                                    ".training.xml"
                                                };

    /** TODO Missing Parameter Documentation */
    protected static String[] HTFOREVER_EXTENSION = new String[]{
                                                        " ArenaDetails.xml", " Club.xml",
                                                        " Economy.xml", " Players.xml",
                                                        " TeamDetails.xml", " Training.xml",
                                                        " WorldDetails.xml", " RegionDetails.xml",
                                                        " LeagueDetails.xml"
                                                    };

    /** TODO Missing Parameter Documentation */
    protected static String[] HTCOACH_EXTENSION = new String[]{
                                                      "arenadetails", "clubdetails",
                                                      "economydetails", "playerdetails",
                                                      "teamdetails", "trainingdetails",
                                                      "matchorders", "worlddetails"
                                                  };

    /** TODO Missing Parameter Documentation */
    protected static String HOPLUGINS_DIRECTORY = System.getProperty("user.dir") + File.separator
                                                  + "hoplugins";

    /** TODO Missing Parameter Documentation */
    protected static String ACT_CANCEL = "CANCEL";

    /** TODO Missing Parameter Documentation */
    protected static String PROP_ADD = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_CANCEL = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_ERROR = null;

    /** TODO Missing Parameter Documentation */
    public static String PROP_FILE = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_DEFAULT_ERROR_MESSAGE = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_NO_SERVER = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_FILE_NOT_FOUND = null;

    /** TODO Missing Parameter Documentation */
    protected static String PROP_NAME = null;

    /** TODO Missing Parameter Documentation */
    public static String PROP_PLAYERS = null;

    /** TODO Missing Parameter Documentation */
    protected static final String DEFAULT = "0";

    /** TODO Missing Parameter Documentation */
    protected static ImageIcon ICON_UP = null;

    /** TODO Missing Parameter Documentation */
    protected static ImageIcon ICON_DOWN = null;

    /** TODO Missing Parameter Documentation */
    protected static ImageIcon ICON_ADD = null;

    /** TODO Missing Parameter Documentation */
    protected static ImageIcon ICON_DELETE = null;

    /** TODO Missing Parameter Documentation */
    protected static CColumn[] playerColumns = null;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_ID = 0;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_NAME = 1;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_AGE = 2;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_SALARY = 3;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_VERLETZT = 4;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_LEADERSHIP = 5;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_EXPERIENCE = 6;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_FORM = 7;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_STAMINA = 8;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_KEEPER = 9;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_DEFENCE = 10;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_WING = 11;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_PLAYMAKING = 12;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_PASSING = 13;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_SCORING = 14;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_SETPIECES = 15;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_GENTLESS = 16;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_CHARACTER = 17;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_SPECIAL = 18;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_NOTE = 19;

    /** TODO Missing Parameter Documentation */
    protected static final int PLAYER_NATIONALITY = 20;

    /** TODO Missing Parameter Documentation */
    protected static final String[][] clubValueDefault = new String[][]{
                                                             {"[club]", ""},
                                                             {"hjTranare=", DEFAULT},
                                                             {"psykolog=", DEFAULT},
                                                             {"presstalesman=", DEFAULT},
                                                             {"massor=", DEFAULT},
                                                             {"lakare=", DEFAULT},
                                                             {"juniorverksamhet=", DEFAULT},
                                                             {"undefeated=", DEFAULT},
                                                             {"victories=", DEFAULT},
                                                             {"fanclub=", DEFAULT}
                                                         };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] basicsValueDefault = new String[][]{
                                                               {"[basics]", ""},
                                                               {"application=", "HO"},
                                                               {"appversion=", ""},
                                                               {"date=", DEFAULT},
                                                               {"season=", DEFAULT},
                                                               {"matchround=", DEFAULT},
                                                               {"teamID=", DEFAULT},
                                                               {"teamName=", ""},
                                                               {"owner=", ""},
                                                               {"ownerEmail=", ""},
                                                               {"ownerICQ=", ""},
                                                               {"ownerHomepage=", ""},
                                                               {"countryID=", ""},
                                                               {"leagueID=", DEFAULT}
                                                           };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] arenaValueDefault = new String[][]{
                                                              {"[arena]", ""},
                                                              {"arenaname=", DEFAULT},
                                                              {"antalStaplats=", DEFAULT},
                                                              {"antalSitt=", DEFAULT},
                                                              {"antalTak=", DEFAULT},
                                                              {"antalVIP=", RSC.DEFAULT},
                                                              {"seatTotal=", DEFAULT},
                                                              {"expandingStaplats=", DEFAULT},
                                                              {"expandingSitt=", DEFAULT},
                                                              {"expandingTak=", DEFAULT},
                                                              {"expandingVIP=", DEFAULT},
                                                              {"expandingSseatTotal=", DEFAULT},
                                                              {"isExpanding=", DEFAULT},
                                                              {"ExpansionDate=", DEFAULT}
                                                          };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] teamValueDefault = new String[][]{
                                                             {"[team]", ""},
                                                             {"trLevel=", DEFAULT},
                                                             {"trTypeValue=", DEFAULT},
                                                             {"trType=", DEFAULT},
                                                             {"stamningValue=", DEFAULT},
                                                             {"stamning=", DEFAULT},
                                                             {"sjalvfortroendeValue=", DEFAULT},
                                                             {"sjalvfortroende=", DEFAULT},
                                                             {"exper433=", DEFAULT},
                                                             {"exper451=", DEFAULT},
                                                             {"exper352=", DEFAULT},
                                                             {"exper532=", DEFAULT},
                                                             {"exper343=", DEFAULT},
                                                             {"exper541=", DEFAULT},
                                                         };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] leagueValueDefault = new String[][]{
                                                               {"[league]", ""},
                                                               {"serie=", DEFAULT},
                                                               {"spelade=", DEFAULT},
                                                               {"gjorda=", DEFAULT},
                                                               {"inslappta=", DEFAULT},
                                                               {"poang=", DEFAULT},
                                                               {"placering=", DEFAULT},
                                                           };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] linupValueDefault = new String[][]{
                                                              {"[lineup]", ""},
                                                              {"trainer=", DEFAULT},
                                                              {"installning=", DEFAULT},
                                                              {"tactictype=", DEFAULT},
                                                              {"keeper=", DEFAULT},
                                                              {"rightBack=", DEFAULT},
                                                              {"insideBack1=", DEFAULT},
                                                              {"insideBack2=", DEFAULT},
                                                              {"leftBack=", DEFAULT},
                                                              {"rightWinger=", DEFAULT},
                                                              {"insideMid1=", DEFAULT},
                                                              {"insideMid2=", DEFAULT},
                                                              {"leftWinger=", DEFAULT},
                                                              {"forward1=", DEFAULT},
                                                              {"forward2=", DEFAULT},
                                                              {"substBack=", DEFAULT},
                                                              {"substInsideMid=", DEFAULT},
                                                              {"substWinger=", DEFAULT},
                                                              {"substKeeper=", DEFAULT},
                                                              {"substForward=", DEFAULT},
                                                              {"captain=", DEFAULT},
                                                              {"kicker1=", DEFAULT},
                                                              {"behRightBack=", DEFAULT},
                                                              {"behInsideBack1=", DEFAULT},
                                                              {"behInsideBack2=", DEFAULT},
                                                              {"behLeftBack=", DEFAULT},
                                                              {"behRightWinger=", DEFAULT},
                                                              {"behInsideMid1=", DEFAULT},
                                                              {"behInsideMid2=", DEFAULT},
                                                              {"behLeftWinger=", DEFAULT},
                                                              {"behForward1=", DEFAULT},
                                                              {"behForward2=", DEFAULT}
                                                          };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] lastlinupValueDefault = new String[][]{
                                                                  {"[lastlineup]", ""},
                                                                  {"trainer=", DEFAULT},
                                                                  {"installning=", DEFAULT},
                                                                  {"tactictype=", DEFAULT},
                                                                  {"keeper=", DEFAULT},
                                                                  {"rightBack=", DEFAULT},
                                                                  {"insideBack1=", DEFAULT},
                                                                  {"insideBack2=", DEFAULT},
                                                                  {"leftBack=", DEFAULT},
                                                                  {"rightWinger=", DEFAULT},
                                                                  {"insideMid1=", DEFAULT},
                                                                  {"insideMid2=", DEFAULT},
                                                                  {"leftWinger=", DEFAULT},
                                                                  {"forward1=", DEFAULT},
                                                                  {"forward2=", DEFAULT},
                                                                  {"substBack=", DEFAULT},
                                                                  {"substInsideMid=", DEFAULT},
                                                                  {"substWinger=", DEFAULT},
                                                                  {"substKeeper=", DEFAULT},
                                                                  {"substForward=", DEFAULT},
                                                                  {"captain=", DEFAULT},
                                                                  {"kicker1=", DEFAULT},
                                                                  {"behRightBack=", DEFAULT},
                                                                  {"behInsideBack1=", DEFAULT},
                                                                  {"behInsideBack2=", DEFAULT},
                                                                  {"behLeftBack=", DEFAULT},
                                                                  {"behRightWinger=", DEFAULT},
                                                                  {"behInsideMid1=", DEFAULT},
                                                                  {"behInsideMid2=", DEFAULT},
                                                                  {"behLeftWinger=", DEFAULT},
                                                                  {"behForward1=", DEFAULT},
                                                                  {"behForward2=", DEFAULT}
                                                              };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] xtraValueDefault = new String[][]{
                                                             {"[xtra]", ""},
                                                             {"TrainingDate=", DEFAULT},
                                                             {"EconomyDate=", DEFAULT},
                                                             {"SeriesMatchDate=", DEFAULT},
                                                             {"CurrencyName=", ""},
                                                             {"CurrencyRate=", DEFAULT},
                                                             {"LogoURL=", ""},
                                                             {"HasPromoted=", ""},
                                                             {"TrainerID=", DEFAULT},
                                                             {"TrainerName=", ""},
                                                             {"ArrivalDate=", DEFAULT},
                                                             {"LeagueLevelUnitID=", DEFAULT}
                                                         };

    /** TODO Missing Parameter Documentation */
    protected static final String[][] economyValue = new String[][]{
                                                         {"[economy]", ""},
                                                         {"supporters=", DEFAULT},
                                                         {"sponsors=", DEFAULT},
                                                         {"cash=", DEFAULT},
                                                         {"IncomeSponsorer=", DEFAULT},
                                                         {"incomePublik=", DEFAULT},
                                                         {"incomeFinansiella=", DEFAULT},
                                                         {"incomeTillfalliga=", DEFAULT},
                                                         {"incomeSumma=", DEFAULT},
                                                         {"costsSpelare=", DEFAULT},
                                                         {"costsPersonal=", DEFAULT},
                                                         {"costsArena=", DEFAULT},
                                                         {"costsJuniorverksamhet=", DEFAULT},
                                                         {"costsRantor=", DEFAULT},
                                                         {"costsTillfalliga=", DEFAULT},
                                                         {"costsSumma=", DEFAULT},
                                                         {"total=", DEFAULT},
                                                         {"lastIncomeSponsorer=", DEFAULT},
                                                         {"lastIncomePublik=", DEFAULT},
                                                         {"lastIncomeFinansiella=", DEFAULT},
                                                         {"lastIncomeTillfalliga=", DEFAULT},
                                                         {"lastIncomeSumma=", DEFAULT},
                                                         {"lastCostsSpelare=", DEFAULT},
                                                         {"lastCostsPersonal=", DEFAULT},
                                                         {"lastCostsArena=", DEFAULT},
                                                         {"lastCostsJuniorverksamhet=", DEFAULT},
                                                         {"lastCostsRantor=", DEFAULT},
                                                         {"lastCostsTillfalliga=", DEFAULT},
                                                         {"lastCostsSumma=", DEFAULT},
                                                         {"lastTotal=", DEFAULT}
                                                     };

    /** TODO Missing Parameter Documentation */
    protected static final String[] TEAM = new String[]{
                                               "No-Team.png", "A-Team.png", "B-Team.png",
                                               "C-Team.png", "D-Team.png", "E-Team.png"
                                           };

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param key TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param miniModel TODO Missing Method Parameter Documentation
     */
    public static void initializeStrings(IHOMiniModel miniModel) {
        MINIMODEL = miniModel;
        PROP_FILE = MINIMODEL.getResource().getProperty("Datei");
        PROP_ADD = MINIMODEL.getResource().getProperty("Speichern");
        PROP_CANCEL = MINIMODEL.getResource().getProperty("Abbrechen");
        PROP_ERROR = MINIMODEL.getResource().getProperty("Fehler");
        PROP_DEFAULT_ERROR_MESSAGE = "see pluginUpdaterErr.log for details";
        PROP_NO_SERVER = MINIMODEL.getResource().getProperty("KeinServer");
        PROP_FILE_NOT_FOUND = MINIMODEL.getResource().getProperty("DateiNichtGefunden");
        PROP_NAME = MINIMODEL.getResource().getProperty("Name");
        PROP_PLAYERS = MINIMODEL.getResource().getProperty("Spieleruebersicht");
        ICON_UP = new javax.swing.ImageIcon(MINIMODEL.getHelper().makeColorTransparent(MINIMODEL.getHelper()
                                                                                                .loadImage("gui/bilder/Taktik_Defensiv.png"),
                                                                                       210, 210,
                                                                                       185, 255,
                                                                                       255, 255));
        ICON_DOWN = new javax.swing.ImageIcon(MINIMODEL.getHelper().makeColorTransparent(MINIMODEL.getHelper()
                                                                                                  .loadImage("gui/bilder/Taktik_Offensiv.png"),
                                                                                         210, 210,
                                                                                         185, 255,
                                                                                         255, 255));
        ICON_ADD = new javax.swing.ImageIcon(MINIMODEL.getHelper().makeColorTransparent(MINIMODEL.getHelper()
                                                                                                 .loadImage("gui/bilder/verletzt.gif"),
                                                                                        210, 210,
                                                                                        185, 255,
                                                                                        255, 255));
        ICON_DELETE = new javax.swing.ImageIcon(MINIMODEL.getHelper().makeColorTransparent(MINIMODEL.getHelper()
                                                                                                    .loadImage("gui/bilder/Reload.png"),
                                                                                           210,
                                                                                           210,
                                                                                           185,
                                                                                           255,
                                                                                           255, 255));
        initRessourcen();
        initPlayerColumns();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static final boolean isBuddyFile(File f) {
        if (f.getName().endsWith(RSC.BUDDY_EXTENSION[1].substring(1, RSC.BUDDY_EXTENSION[1].length()))) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static final boolean isHAMFile(File f) {
        if (f.getName().startsWith("HAM") && f.getName().endsWith(".xml")) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static final boolean isHTCoachFile(File f) {
        String name = f.getName();

        for (int i = 0; i < RSC.HTCOACH_EXTENSION.length; i++) {
            if (name.startsWith(RSC.HTCOACH_EXTENSION[i]) && !f.isDirectory()) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected static final boolean isHTForverFile(File f) {
        if (f.getName().endsWith(RSC.HTFOREVER_EXTENSION[1].substring(1,
                                                                      RSC.HTFOREVER_EXTENSION[1]
                                                                      .length()))) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     * @param txt TODO Missing Method Parameter Documentation
     */
    protected static void handleException(Exception e, String txt) {
        if (e != null) {
            JOptionPane.showMessageDialog(null, e.getMessage(), RSC.NAME, JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, txt, RSC.NAME, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    protected static void initPlayerColumns() {
        playerColumns = new CColumn[20];
        playerColumns[0] = new CColumn(PLAYER_ID,
                                       MINIMODEL.getResource().getProperty("Spieler")
                                       + MINIMODEL.getResource().getProperty("ID"));
        playerColumns[1] = new CColumn(PLAYER_NAME, PROP_NAME);
        playerColumns[2] = new CColumn(PLAYER_AGE,
                                       MINIMODEL.getResource().getProperty("FaktorSpieleralter"));
        playerColumns[3] = new CColumn(PLAYER_SALARY, MINIMODEL.getResource().getProperty("Gehalt"));
        playerColumns[4] = new CColumn(PLAYER_VERLETZT,
                                       MINIMODEL.getResource().getProperty("Verletzt"));

        playerColumns[5] = new CColumn(PLAYER_LEADERSHIP,
                                       MINIMODEL.getResource().getProperty("Fuehrung"));
        playerColumns[6] = new CColumn(PLAYER_EXPERIENCE,
                                       MINIMODEL.getResource().getProperty("Erfahrung"));
        playerColumns[7] = new CColumn(PLAYER_FORM, MINIMODEL.getResource().getProperty("Form"));
        playerColumns[8] = new CColumn(PLAYER_STAMINA,
                                       MINIMODEL.getResource().getProperty("Kondition"));
        playerColumns[9] = new CColumn(PLAYER_KEEPER, MINIMODEL.getResource().getProperty("Torwart"));

        playerColumns[10] = new CColumn(PLAYER_DEFENCE,
                                        MINIMODEL.getResource().getProperty("Verteidigung"));
        playerColumns[11] = new CColumn(PLAYER_WING,
                                        MINIMODEL.getResource().getProperty("Fluegelspiel"));
        playerColumns[12] = new CColumn(PLAYER_PLAYMAKING,
                                        MINIMODEL.getResource().getProperty("Spielaufbau"));
        playerColumns[13] = new CColumn(PLAYER_PASSING,
                                        MINIMODEL.getResource().getProperty("Passpiel"));
        playerColumns[14] = new CColumn(PLAYER_SCORING,
                                        MINIMODEL.getResource().getProperty("Torschuss"));

        playerColumns[15] = new CColumn(PLAYER_GENTLESS,
                                        MINIMODEL.getResource().getProperty("Ansehen"));
        playerColumns[16] = new CColumn(PLAYER_CHARACTER,
                                        MINIMODEL.getResource().getProperty("Charakter"));
        playerColumns[17] = new CColumn(PLAYER_SPECIAL,
                                        MINIMODEL.getResource().getProperty("Spezialitaet"));
        playerColumns[18] = new CColumn(PLAYER_GENTLESS,
                                        MINIMODEL.getResource().getProperty("Notizen"));
        playerColumns[19] = new CColumn(PLAYER_CHARACTER,
                                        MINIMODEL.getResource().getProperty("Nationalitaet"));
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void initRessourcen() {
        properties = new java.util.Properties();

        // Standardressourcen:
        // Spezifische Ressourcen:
        File languagefile = new File("hoPlugins/conv/Sprache/"
                                     + MINIMODEL.getHelper().getLanguageName() + ".properties");

        if (!languagefile.exists()) {
            languagefile = new File("hoPlugins/conv/Sprache/English.properties");
        }

        if (languagefile.exists()) {
            java.util.Properties props = new java.util.Properties();

            try {
                props.load(new java.io.FileInputStream(languagefile));
                properties.putAll(props);
            } catch (java.io.IOException e) {
            }
        }
    }
}
