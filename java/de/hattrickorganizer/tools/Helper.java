// %1586804725:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Window;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import plugins.IMatchHighlight;
import plugins.IMatchLineup;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * Klasse mit Hilfsmethoden, die in mehreren Dialogen/Panels benutzt werden
 */
/**
 * Now Removed method: public static String[] getSprachDateien() This method is now in
 * LanguageFiles.java. Done by jailbird.
 */
public class Helper extends LanguageFiles {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Componente zum Grafikenladen */
    public static de.hattrickorganizer.gui.templates.ImagePanel LADECOMPONENTE = new de.hattrickorganizer.gui.templates.ImagePanel();

    /** yellow star */
    public static javax.swing.ImageIcon YELLOWSTARIMAGEICON;

    /** grey star */
    public static javax.swing.ImageIcon GREYSTARIMAGEICON;

    /** no icon */
    public static javax.swing.ImageIcon NOIMAGEICON;

    /** no match icon */
    public static javax.swing.ImageIcon NOMATCHICON;

    /** donload icon */
    public static javax.swing.ImageIcon DOWNLOADMATCHICON;

    /** show match icon */
    public static javax.swing.ImageIcon SHOWMATCHICON;

    /** red card */
    public static ImageIcon ROTEKARTE;

    /** yellow card */
    public static ImageIcon GELBEKARTE;

    /** double yellow */
    public static ImageIcon DOPPELGELB;

    /** bruised */
    public static ImageIcon ANGESCHLAGEN;

    /** bruised small */
    private static ImageIcon ANGESCHLAGEN_KLEIN;

    /** injured */
    public static ImageIcon VERLETZT;

    /** injured small */
    private static ImageIcon VERLETZT_KLEIN;

    /** default - goal */
    public static ImageIcon TOR;

    /** goal - direct free kick */
    private static ImageIcon TOR_FREISTOSS;

    /** goal through the middle */
    private static ImageIcon TOR_MITTE;

    /** goal over the left side */
    private static ImageIcon TOR_LINKS;

    /** goal over the right side */
    private static ImageIcon TOR_RECHTS;

    /** penalty goal */
    private static ImageIcon TOR_ELFMETER;

    /** goal - indirect free kick */
    private static ImageIcon TOR_INDIRECT_FREEKICK;

    /** Special event goal */
    private static ImageIcon TOR_SPECIAL;

    /** counter attack goal */
    private static ImageIcon TOR_COUNTER;

    /** default - no goal */
    public static ImageIcon KEINTOR;

    /** no goal - direct free kick */
    private static ImageIcon KEINTOR_FREISTOSS;

    /** no goal - though the middle */
    private static ImageIcon KEINTOR_MITTE;

    /** no goal - left */
    private static ImageIcon KEINTOR_LINKS;

    /** no goal - right */
    private static ImageIcon KEINTOR_RECHTS;

    /** no goal - penalty */
    private static ImageIcon KEINTOR_ELFMETER;

    /** no goal - indirect free kick */
    private static ImageIcon KEINTOR_INDIRECT_FREEKICK;

    /** no goal - SE */
    private static ImageIcon KEINTOR_SPECIAL;

    /** no goal - CA */
    private static ImageIcon KEINTOR_COUNTER;

    /** gear wheel */
    public static ImageIcon ZAHNRAD;

    /** manual */
    public static ImageIcon MANUELL;

    /** Gesamteinstufung */
    public static final CBItem[] EINSTUFUNG = {
										    	 new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden),
										                 ISpieler.nicht_vorhanden),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal),
                                                             ISpieler.katastrophal),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich),
                                                             ISpieler.erbaermlich),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig),
                                                             ISpieler.armselig),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach),
                                                             ISpieler.schwach),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich),
                                                             ISpieler.durchschnittlich),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel),
                                                             ISpieler.passabel),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut),
                                                             ISpieler.gut),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut),
                                                             ISpieler.sehr_gut),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend),
                                                             ISpieler.hervorragend),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.grossartig),
                                                             ISpieler.grossartig),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.brilliant),
                                                             ISpieler.brilliant),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.fantastisch),
                                                             ISpieler.fantastisch),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.Weltklasse),
                                                             ISpieler.Weltklasse),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.uebernatuerlich),
                                                             ISpieler.uebernatuerlich),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.gigantisch),
                                                             ISpieler.gigantisch),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.ausserirdisch),
                                                             ISpieler.ausserirdisch),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.mythisch),
                                                             ISpieler.mythisch),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.maerchenhaft),
                                                             ISpieler.maerchenhaft),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.galaktisch),
                                                             ISpieler.galaktisch),
                                                  new CBItem(PlayerHelper.getNameForSkill(ISpieler.goettlich),
                                                             ISpieler.goettlich)
                                              };

    /** Form */
    public static final CBItem[] EINSTUFUNG_FORM = {
													new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden),
																	ISpieler.nicht_vorhanden),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal),
                                                                  ISpieler.katastrophal),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich),
                                                                  ISpieler.erbaermlich),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig),
                                                                  ISpieler.armselig),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach),
                                                                  ISpieler.schwach),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich),
                                                                  ISpieler.durchschnittlich),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel),
                                                                  ISpieler.passabel),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut),
                                                                  ISpieler.gut),
                                                       new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut),
                                                                  ISpieler.sehr_gut)
                                                   };

    /** Kondition */
    public static final CBItem[] EINSTUFUNG_KONDITION = {
														new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden),
																		ISpieler.nicht_vorhanden),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal),
                                                                       ISpieler.katastrophal),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich),
                                                                       ISpieler.erbaermlich),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig),
                                                                       ISpieler.armselig),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach),
                                                                       ISpieler.schwach),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich),
                                                                       ISpieler.durchschnittlich),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel),
                                                                       ISpieler.passabel),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut),
                                                                       ISpieler.gut),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut),
                                                                       ISpieler.sehr_gut),
                                                            new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend),
                                                                       ISpieler.hervorragend)
                                                        };

    /** Form */
    public static final CBItem[] EINSTUFUNG_TRAINER = {
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal),
                                                                     ISpieler.katastrophal),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich),
                                                                     ISpieler.erbaermlich),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig),
                                                                     ISpieler.armselig),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach),
                                                                     ISpieler.schwach),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich),
                                                                     ISpieler.durchschnittlich),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel),
                                                                     ISpieler.passabel),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut),
                                                                     ISpieler.gut),
                                                          new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut),
                                                                     ISpieler.sehr_gut)
                                                      };

    /** Speciality */
    public static final CBItem[] EINSTUFUNG_SPECIALITY = {
    	new CBItem("", 0),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Technical"), 1),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Quick"),2),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Powerful"), 3),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Unpredictable"), 4),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Head"), 5),
    	new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("sp_Regainer"), 6)
    };

    /** Spielerpositionen */
    public static final CBItem[] SPIELERPOSITIONEN = {
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.TORWART),
                                                                    ISpielerPosition.TORWART),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.INNENVERTEIDIGER),
                                                                    ISpielerPosition.INNENVERTEIDIGER),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.INNENVERTEIDIGER_OFF),
                                                                    ISpielerPosition.INNENVERTEIDIGER_OFF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.INNENVERTEIDIGER_AUS),
                                                                    ISpielerPosition.INNENVERTEIDIGER_AUS),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.AUSSENVERTEIDIGER),
                                                                    ISpielerPosition.AUSSENVERTEIDIGER),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.AUSSENVERTEIDIGER_OFF),
                                                                    ISpielerPosition.AUSSENVERTEIDIGER_OFF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.AUSSENVERTEIDIGER_DEF),
                                                                    ISpielerPosition.AUSSENVERTEIDIGER_DEF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.AUSSENVERTEIDIGER_IN),
                                                                    ISpielerPosition.AUSSENVERTEIDIGER_IN),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.MITTELFELD),
                                                                    ISpielerPosition.MITTELFELD),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.MITTELFELD_OFF),
                                                                    ISpielerPosition.MITTELFELD_OFF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.MITTELFELD_DEF),
                                                                    ISpielerPosition.MITTELFELD_DEF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.MITTELFELD_AUS),
                                                                    ISpielerPosition.MITTELFELD_AUS),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.FLUEGELSPIEL),
                                                                    ISpielerPosition.FLUEGELSPIEL),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.FLUEGELSPIEL_OFF),
                                                                    ISpielerPosition.FLUEGELSPIEL_OFF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.FLUEGELSPIEL_DEF),
                                                                    ISpielerPosition.FLUEGELSPIEL_DEF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.FLUEGELSPIEL_IN),
                                                                    ISpielerPosition.FLUEGELSPIEL_IN),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.STURM),
                                                                    ISpielerPosition.STURM),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.STURM_DEF),
                                                                    ISpielerPosition.STURM_DEF),
                                                         new CBItem(SpielerPosition
                                                                    .getNameForPosition(ISpielerPosition.STURM_AUS),
                                                                    ISpielerPosition.STURM_AUS)
                                                     };

    /** TODO Missing Parameter Documentation */
    public static final String[] TEAMSMILIES = {
                                                   "", "A-Team.png", "B-Team.png", "C-Team.png",
                                                   "D-Team.png", "E-Team.png"
                                               };

    /** TODO Missing Parameter Documentation */
    public static final String[] MANUELLSMILIES = {
                                                      "", "1bigsmile.png", "2smile.png",
                                                      "3normal.png", "4sad.png", "5verysad.png",
                                                      "6clown.png", "7trainer.png", "8dollar.png",
                                                      "9coach.png"
                                                  };

    /** TODO Missing Parameter Documentation */
    public static final CBItem[] WETTER = {
                                              new CBItem("", 1), new CBItem("", 2),
                                              new CBItem("", 3), new CBItem("", 4)
                                          };

    /** Trickotfarben */
    public static java.awt.Color TRICKOT_TORWART = java.awt.Color.black;

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_INNENVERTEIDIGER = new java.awt.Color(0, 0, 220);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_AUSSENVERTEIDIGER = new java.awt.Color(0, 220, 0);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_MITTELFELD = new java.awt.Color(220, 220, 0);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_FLUEGEL = new java.awt.Color(220, 140, 0);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_STURM = new java.awt.Color(220, 0, 0);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_RESERVE_TORWART = new java.awt.Color(200, 200, 200);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_RESERVE_INNENVERTEIDIGER = new java.awt.Color(200, 200, 255);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_RESERVE_MITTELFELD = new java.awt.Color(255, 255, 180);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_RESERVE_FLUEGEL = new java.awt.Color(255, 225, 180);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_RESERVE_STURM = new java.awt.Color(255, 200, 200);

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color TRICKOT_NONE = new java.awt.Color(230, 230, 230);

    /** Images */
    public static ExtendedImageIcon EMPTYIMAGE;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon WETTERSCHLECHT;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon WETTERGUT;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon SONNIG;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon WOLKIG;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon BEWOELKT;

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon REGEN;

    //14px

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon LEER = new javax.swing.ImageIcon(new java.awt.image.BufferedImage(14,
                                                                                                          14,
                                                                                                          java.awt.image.BufferedImage.TYPE_INT_ARGB));

    //8px

    /** TODO Missing Parameter Documentation */
    public static javax.swing.ImageIcon MINILEER = new javax.swing.ImageIcon(new java.awt.image.BufferedImage(8,
                                                                                                              8,
                                                                                                              java.awt.image.BufferedImage.TYPE_INT_ARGB));

	public static NumberFormat CURRENCYFORMAT = java.text.NumberFormat.getCurrencyInstance();

    /** wird für das Parsen in parseFloat benötigt */
    public static DecimalFormat INTEGERFORMAT = new java.text.DecimalFormat("#0");

    /** TODO Missing Parameter Documentation */
    public static DecimalFormat DEFAULTDEZIMALFORMAT = new java.text.DecimalFormat("#0.0");

    /** TODO Missing Parameter Documentation */
    public static DecimalFormat DEZIMALFORMAT_2STELLEN = new java.text.DecimalFormat("#0.00");

    /** TODO Missing Parameter Documentation */
    public static DecimalFormat DEZIMALFORMAT_3STELLEN = new java.text.DecimalFormat("#0.000");

    /** Schon eine Meldung angezeigt? */
    public static boolean paneShown;

    /** Hashtable mit Veränderungspfeilgrafiken nach Integer als Key */
    private static java.util.Hashtable m_clPfeilCache = new java.util.Hashtable();
    private static java.util.Hashtable m_clPfeilLightCache = new java.util.Hashtable();

    /** Hashtable mit Trikotnummern nach Integer als Key */
    private static java.util.Hashtable m_clTrickotnummerCache = new java.util.Hashtable();

    /** Cache für Bilder */
    private static java.util.HashMap m_clBilderCache = new java.util.HashMap();

    /** Cache für Transparent gemachte Bilder */
    private static java.util.HashMap m_clTransparentsCache = new java.util.HashMap();

    /** Cache für Trickots */
    private static java.util.HashMap m_clTrickotCache = new java.util.HashMap();

    /** Cache für Spezialitäten */
    private static java.util.HashMap m_clSpezialitaetCache = new java.util.HashMap();

    /** Cache für Gruppen */
    private static java.util.HashMap m_clGruppenCache = new java.util.HashMap();

    /** Cache für MiniGruppen */
    private static java.util.HashMap m_clMiniGruppenCache = new java.util.HashMap();

    /** Cache für Spieltypen */
    private static java.util.HashMap m_clSpieltypCache = new java.util.HashMap();

    //Initialisierung
    static {
        YELLOWSTARIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/star.gif"),
                                                                                                   Color.white));
        GREYSTARIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/star_grey.png"),
                                                                                                 Color.white));
        NOIMAGEICON = new ImageIcon(new java.awt.image.BufferedImage(14, 14,
                                                                     java.awt.image.BufferedImage.TYPE_INT_ARGB));
        NOMATCHICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/NoMatch.png"),
                                                                                           Color.white));
        DOWNLOADMATCHICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/DownloadMatch.png"),
                                                                                                 Color.white));
        SHOWMATCHICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/ShowMatch.png"),
                                                                                             Color.white));

        ROTEKARTE = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper
                                                                    .loadImage("gui/bilder/disqualifiziert.gif"),
                                                                    Color.white));
        GELBEKARTE = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper
                                                                     .loadImage("gui/bilder/verwarnung_1.gif"),
                                                                     Color.white));
        DOPPELGELB = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper
                                                                     .loadImage("gui/bilder/verwarnung_2.gif"),
                                                                     Color.white));
        ANGESCHLAGEN = new javax.swing.ImageIcon(Helper
                                                 .makeColorTransparent(Helper
                                                                       .loadImage("gui/bilder/angeschlagen.gif"),
                                                                       Color.white));
        VERLETZT = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/verletzt.gif"),
                                                                                                    Color.white));
        ANGESCHLAGEN_KLEIN = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper
                                                                             .loadImage("gui/bilder/angeschlagen_klein.png"),
                                                                             Color.red));
        VERLETZT_KLEIN = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper
                                                                         .loadImage("gui/bilder/verletzt_klein.png"),
                                                                         Color.white));

        TOR = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball.png"),
                                                                                   new Color(255,
                                                                                             0, 0))
                                                             .getScaledInstance(16, 10,
                                                                                Image.SCALE_SMOOTH));
        TOR_FREISTOSS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Freistoss.png"),
                                                                                             new Color(255,
                                                                                                       0,
                                                                                                       0))
                                                                       .getScaledInstance(16, 10,
                                                                                          Image.SCALE_SMOOTH));
        TOR_MITTE = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Mitte.png"),
                                                                                         new Color(255,
                                                                                                   0,
                                                                                                   0))
                                                                   .getScaledInstance(16, 10,
                                                                                      Image.SCALE_SMOOTH));
        TOR_LINKS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Links.png"),
                                                                                         new Color(255,
                                                                                                   0,
                                                                                                   0))
                                                                   .getScaledInstance(16, 10,
                                                                                      Image.SCALE_SMOOTH));
        TOR_RECHTS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Rechts.png"),
                                                                                          new Color(255,
                                                                                                    0,
                                                                                                    0))
                                                                    .getScaledInstance(16, 10,
                                                                                       Image.SCALE_SMOOTH));
        TOR_ELFMETER = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Elfmeter.png"),
                                                                                            new Color(255,
                                                                                                      0,
                                                                                                      0))
                                                                      .getScaledInstance(16, 10,
                                                                                         Image.SCALE_SMOOTH));
        TOR_INDIRECT_FREEKICK = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_FreistossIndirekt.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));

        TOR_SPECIAL = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Spezial.png"),
                                                                                           new Color(255,
                                                                                                     0,
                                                                                                     0))
                                                                     .getScaledInstance(16, 10,
                                                                                        Image.SCALE_SMOOTH));
        TOR_COUNTER = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Fussball_Konter.png"),
                                                                                           new Color(255,
                                                                                                     0,
                                                                                                     0))
                                                                     .getScaledInstance(16, 10,
                                                                                        Image.SCALE_SMOOTH));

        KEINTOR = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball.png"),
                                                                                       new Color(255,
                                                                                                 0,
                                                                                                 0))
                                                                 .getScaledInstance(16, 10,
                                                                                    Image.SCALE_SMOOTH));
        KEINTOR_FREISTOSS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Freistoss.png"),
                                                                                                 new Color(255,
                                                                                                           0,
                                                                                                           0))
                                                                           .getScaledInstance(16,
                                                                                              10,
                                                                                              Image.SCALE_SMOOTH));
        KEINTOR_MITTE = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Mitte.png"),
                                                                                             new Color(255,
                                                                                                       0,
                                                                                                       0))
                                                                       .getScaledInstance(16, 10,
                                                                                          Image.SCALE_SMOOTH));
        KEINTOR_LINKS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Links.png"),
                                                                                             new Color(255,
                                                                                                       0,
                                                                                                       0))
                                                                       .getScaledInstance(16, 10,
                                                                                          Image.SCALE_SMOOTH));
        KEINTOR_RECHTS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Rechts.png"),
                                                                                              new Color(255,
                                                                                                        0,
                                                                                                        0))
                                                                        .getScaledInstance(16, 10,
                                                                                           Image.SCALE_SMOOTH));
        KEINTOR_ELFMETER = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Elfmeter.png"),
                                                                                                new Color(255,
                                                                                                          0,
                                                                                                          0))
                                                                          .getScaledInstance(16,
                                                                                             10,
                                                                                             Image.SCALE_SMOOTH));
        KEINTOR_INDIRECT_FREEKICK = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_FreistossIndirekt.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));

        KEINTOR_SPECIAL = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Spezial.png"),
                                                                                               new Color(255,
                                                                                                         0,
                                                                                                         0))
                                                                         .getScaledInstance(16, 10,
                                                                                            Image.SCALE_SMOOTH));
        KEINTOR_COUNTER = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Konter.png"),
                                                                                               new Color(255,
                                                                                                         0,
                                                                                                         0))
                                                                         .getScaledInstance(16, 10,
                                                                                            Image.SCALE_SMOOTH));

        ZAHNRAD = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/zahnrad.png"),
                                                                                       new Color(255,
                                                                                                 255,
                                                                                                 255)));
        MANUELL = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Manuell.png"),
                                                                                       new Color(255,
                                                                                                 255,
                                                                                                 255)));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     * @param subtyp TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Color getColor4SpielHighlight(int typ, int subtyp) {
        if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
                return gui.UserParameter.instance().FG_ZWEIKARTEN;
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
                return gui.UserParameter.instance().FG_GESPERRT;
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
            return Color.BLACK;
        } else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
            return Color.GRAY;
        } else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
                return gui.UserParameter.instance().FG_ANGESCHLAGEN;
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)) {
                return gui.UserParameter.instance().FG_VERLETZT;
            }
        }

        return Color.BLACK;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param position TODO Missing Method Parameter Documentation
     * @param trickotnummer TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImage4Position(de.hattrickorganizer.model.SpielerPosition position,
                                                          int trickotnummer) {
        if (position == null) {
            return getImage4Position(0, (byte) 0, trickotnummer);
        }

        return getImage4Position(position.getId(), position.getTaktik(), trickotnummer);
    }

    /**
     * Gibt die Grafik zu der Position zurück
     *
     * @param posid TODO Missing Constructuor Parameter Documentation
     * @param taktik TODO Missing Constructuor Parameter Documentation
     * @param trickotnummer TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImage4Position(int posid, byte taktik, int trickotnummer) {
        java.awt.Color trickotfarbe = null;
        java.awt.Image trickotImage = null;

        //       java.awt.Image taktikImage = null;
        java.awt.Image zusammenImage = null;

        //       java.awt.Image scaleImage = null;
        javax.swing.ImageIcon komplettIcon = null;

        //Im Cache nachsehen
        komplettIcon = (javax.swing.ImageIcon) m_clTrickotCache.get(new TrickotCacheKey(posid,
                                                                                        taktik));

        //       if ( posid < 0 )
        //       {
        //           if ( EMPTYIMAGE == null )
        //           {
        //               EMPTYIMAGE = new javax.swing.ImageIcon( new java.awt.image.BufferedImage( 14, 14, java.awt.image.BufferedImage.TYPE_INT_ARGB ) );
        //           }
        //           komplettIcon = EMPTYIMAGE;
        //       }
        //Nicht gefunden
        if (komplettIcon == null) {
            switch (posid) {
                case ISpielerPosition.keeper: {
                    trickotfarbe = TRICKOT_TORWART;
                    break;
                }

                case ISpielerPosition.insideBack1:
                case ISpielerPosition.insideBack2: {
                    trickotfarbe = TRICKOT_INNENVERTEIDIGER;
                    break;
                }

                case ISpielerPosition.leftBack:
                case ISpielerPosition.rightBack: {
                    trickotfarbe = TRICKOT_AUSSENVERTEIDIGER;
                    break;
                }

                case ISpielerPosition.insideMid1:
                case ISpielerPosition.insideMid2: {
                    trickotfarbe = TRICKOT_MITTELFELD;
                    break;
                }

                case ISpielerPosition.leftWinger:
                case ISpielerPosition.rightWinger: {
                    trickotfarbe = TRICKOT_FLUEGEL;
                    break;
                }

                case ISpielerPosition.forward1:
                case ISpielerPosition.forward2: {
                    trickotfarbe = TRICKOT_STURM;
                    break;
                }

                case ISpielerPosition.substKeeper: {
                    trickotfarbe = TRICKOT_RESERVE_TORWART;
                    break;
                }

                case ISpielerPosition.substBack: {
                    trickotfarbe = TRICKOT_RESERVE_INNENVERTEIDIGER;
                    break;
                }

                case ISpielerPosition.substInsideMid: {
                    trickotfarbe = TRICKOT_RESERVE_MITTELFELD;
                    break;
                }

                case ISpielerPosition.substWinger: {
                    trickotfarbe = TRICKOT_RESERVE_FLUEGEL;
                    break;
                }

                case ISpielerPosition.substForward: {
                    trickotfarbe = TRICKOT_RESERVE_STURM;
                    break;
                }

                default:
                    trickotfarbe = TRICKOT_NONE;
            }

            switch (taktik) {
                //taktikImage = Helper.makeColorTransparent ( Helper.loadImage ( "gui/bilder/Taktik_Defensiv.png" ), java.awt.Color.white );
                case ISpielerPosition.DEFENSIV:
                    break;

                //taktikImage = Helper.makeColorTransparent ( Helper.loadImage ( "gui/bilder/Taktik_Offensiv.png" ), java.awt.Color.white );
                case ISpielerPosition.OFFENSIV:
                    break;

                //taktikImage = Helper.makeColorTransparent ( Helper.loadImage ( "gui/bilder/Taktik_NachAussen.png" ), java.awt.Color.white );
                case ISpielerPosition.NACH_AUSSEN:
                    break;

                //taktikImage = Helper.makeColorTransparent ( Helper.loadImage ( "gui/bilder/Taktik_ZurMitte.png" ), java.awt.Color.white );
                case ISpielerPosition.ZUR_MITTE:
                    break;

                case ISpielerPosition.ZUS_INNENV: {
                    trickotfarbe = TRICKOT_INNENVERTEIDIGER;
                    break;
                }

                case ISpielerPosition.ZUS_MITTELFELD: {
                    trickotfarbe = TRICKOT_MITTELFELD;
                    break;
                }

                case ISpielerPosition.ZUS_STUERMER: {
                    trickotfarbe = TRICKOT_STURM;
                    break;
                }
            }

            //Bild laden, transparenz hinzu, trikofarbe wechseln
            trickotImage = Helper.changeColor(Helper.changeColor(Helper.makeColorTransparent(Helper
                                                                                             .loadImage("gui/bilder/Trickot.png"),
                                                                                             java.awt.Color.white),
                                                                 java.awt.Color.black, trickotfarbe),
                                              new java.awt.Color(100, 100, 100),
                                              trickotfarbe.brighter());

            //trickotImage = Helper.loadImage( component, "gui/bilder/Trickot.png" );
            //Taktik, wenn vorhanden
            /* BUGGY!!
               if ( taktikImage != null )
               {
                   //Warten, bis die Grafik geladen worden ist
                    java.awt.MediaTracker tracker = new java.awt.MediaTracker(component);
                    tracker.addImage(taktikImage,1);
                    tracker.addImage(trickotImage,2);
                    try
                    {
                        tracker.waitForAll();
                    }
                    catch(InterruptedException ie)
                    {
                    }
                    zusammenImage = Helper.zusammenfuehren ( trickotImage, taktikImage );
               }
               else
               {
                   zusammenImage = trickotImage;
               }
               //Warten, bis die Grafik geladen worden ist
                java.awt.MediaTracker tracker = new java.awt.MediaTracker(component);
                tracker.addImage(zusammenImage,1);
                try
                {
                    tracker.waitForAll();
                }
                catch(InterruptedException ie)
                {
                }
             */
            zusammenImage = trickotImage;

            /* BUG in JVM!
               scaleImage = zusammenImage.getScaledInstance ( 14, 14, java.awt.Image.SCALE_SMOOTH );
               //Warten, bis die Grafik geladen worden ist
                java.awt.MediaTracker tracker = new java.awt.MediaTracker(component);
                tracker.addImage(scaleImage,1);
                try
                {
                    tracker.waitForAll();
                }
                catch(InterruptedException ie)
                {
                }
             */
            komplettIcon = new javax.swing.ImageIcon(zusammenImage);

            //In den Cache hinzufügen
            m_clTrickotCache.put(new TrickotCacheKey(posid, taktik), komplettIcon);

            //HOLogger.instance().log(Helper.class, "Laden Grafik: "+ position.getPosition () + "/" + position.getTaktik () );
        } else {
            //HOLogger.instance().log(Helper.class, "Cache Grafik: "+ position.getPosition () + "/" + position.getTaktik () );
        }

        //return new java.awt.image.BufferedImage( 1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB );
        //Trickotnummer
        if ((trickotnummer > 0) && (trickotnummer < 100)) {
            java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(24, 14,
                                                                                  java.awt.image.BufferedImage.TYPE_INT_ARGB);

            //5;
            int xPosText = 18;

            //Helper.makeColorTransparent( image, Color.white );
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

            //Wert eintragen
            //g2d.setComposite ( java.awt.AlphaComposite.getInstance( java.awt.AlphaComposite.SRC_OVER, 1.0f ) );
            g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 10));

            //Position bei grossen Zahlen weiter nach vorne
            if (trickotnummer > 9) {
                xPosText = 12;
            }

            g2d.setColor(Color.black);
            g2d.drawString(trickotnummer + "", xPosText, 13);

            //Zusammenführen
            image = (java.awt.image.BufferedImage) Helper.zusammenfuehren(komplettIcon.getImage(),
                                                                          image);

            //Icon erstellen und in den Cache packen
            komplettIcon = new javax.swing.ImageIcon(image);
        }

        return komplettIcon;
    }

    /**
     * Macht einen Kreuz durch das Image
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image getImageDurchgestrichen(java.awt.Image image) {
        return getImageDurchgestrichen(image, java.awt.Color.lightGray, java.awt.Color.darkGray);
    }

    /**
     * Macht einen Kreuz durch das Image
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     * @param helleFarbe TODO Missing Constructuor Parameter Documentation
     * @param dunkleFarbe TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image getImageDurchgestrichen(java.awt.Image image,
                                                         java.awt.Color helleFarbe,
                                                         java.awt.Color dunkleFarbe) {
        try {
            final java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(image
                                                                                                .getWidth(null),
                                                                                                image
                                                                                                .getHeight(null),
                                                                                                java.awt.image.BufferedImage.TYPE_INT_ARGB);

            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) bufferedImage.getGraphics();

            g2d.drawImage(image, 0, 0, null);

            //Kreuz zeichnen
            g2d.setColor(helleFarbe);
            g2d.drawLine(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight());
            g2d.drawLine(bufferedImage.getWidth() - 1, 0, 0, bufferedImage.getHeight());
            g2d.setColor(dunkleFarbe);
            g2d.drawLine(1, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            g2d.drawLine(bufferedImage.getWidth(), 0, 1, bufferedImage.getHeight());
            return bufferedImage;
        } catch (Exception e) {
            return image;
        }
    }

    //TODO Geht noch nicht richtig
    public static javax.swing.ImageIcon getImageIcon4Color(java.awt.Color color) {
        final java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(14, 14,
                                                                                            java.awt.image.BufferedImage.TYPE_INT_ARGB);

        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) bufferedImage.getGraphics();

        g2d.setColor(color);
        g2d.fillRect(0, 0, 13, 13);

        return new javax.swing.ImageIcon(bufferedImage);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param country TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static ImageIcon getImageIcon4Country(int country) {
        return new javax.swing.ImageIcon(Helper.loadImage("flags/"+ country + "flag.png"));
    }

    /**
     * Gibt die Grafik für die Gruppe oder Smilie zurück
     *
     * @param gruppe TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4GruppeSmilie(String gruppe) {
        javax.swing.ImageIcon gruppenicon = null;

        //Keine Gruppe / Smilie
        if (gruppe.trim().equals("")) {
            return LEER;
        }

        if (m_clGruppenCache.containsKey(gruppe)) {
            gruppenicon = (javax.swing.ImageIcon) m_clGruppenCache.get(gruppe);
        } else {
            gruppenicon = new javax.swing.ImageIcon(Helper.makeColorTransparent(de.hattrickorganizer.tools.Helper
                                                                          .loadImage("gui/bilder/smilies/"
                                                                                     + gruppe),
                                                                          new java.awt.Color(209,
                                                                                             41, 144)));
            m_clGruppenCache.put(gruppe, gruppenicon);
        }

        return gruppenicon;
    }

    /**
     * Gibt die Grafik für die Gruppe in 7px Grösse für die Aufstellung zurück
     *
     * @param gruppe TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4MiniGruppe(String gruppe) {
        javax.swing.ImageIcon gruppenicon = null;

        //Keine Gruppe / Smilie
        if (gruppe.trim().equals("")) {
            return MINILEER;
        }

        if (m_clMiniGruppenCache.containsKey(gruppe)) {
            gruppenicon = (javax.swing.ImageIcon) m_clMiniGruppenCache.get(gruppe);
        } else {
            gruppenicon = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/smilies/"
                                                                                                                      + gruppe)
                                                                                                           .getScaledInstance(8,
                                                                                                                              8,
                                                                                                                              Image.SCALE_SMOOTH),
                                                                          new java.awt.Color(209,
                                                                                             41, 144)));
            m_clMiniGruppenCache.put(gruppe, gruppenicon);
        }

        return gruppenicon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Spezialitaet(int wert) {
        ExtendedImageIcon icon = null;
        final Integer keywert = new Integer(wert);

        if (wert <= 0) {
            icon = EMPTYIMAGE;
        } else {
            // Nicht im Cache
            if (!m_clSpezialitaetCache.containsKey(keywert)) {
                final String bild = "gui/bilder/spec" + wert + ".png";

                try {
                    icon = new ExtendedImageIcon(Helper.loadImage(bild));
                    icon.setIconDescription(HelperWrapper.instance().getNameForSpeciality(wert));
                } catch (Exception e) {
                    icon = EMPTYIMAGE;
                }

                m_clSpezialitaetCache.put(keywert, icon);

                //HOLogger.instance().log(Helper.class, "Load SpezialCache: " + wert );
            }
            //Im Cache
            else {
                icon = (ExtendedImageIcon) m_clSpezialitaetCache.get(keywert);

                //HOLogger.instance().log(Helper.class, "Use SpezialCache: " + wert );
            }
        }

        return icon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     * @param subtyp TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4SpielHighlight(int typ, int subtyp) {
        javax.swing.ImageIcon icon = null;

        if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
                icon = GELBEKARTE;
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
                icon = ROTEKARTE;
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
            switch (subtyp) {
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8: {
                    icon = TOR_FREISTOSS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8: {
                    icon = TOR_MITTE;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8: {
                    icon = TOR_LINKS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8: {
                    icon = TOR_RECHTS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8: {
                    icon = TOR_ELFMETER;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_7:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_8: {
                	icon = TOR_INDIRECT_FREEKICK;
                	break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR: {
                    icon = TOR_SPECIAL;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF: {
                    icon = TOR_COUNTER;
                    break;
                }

                default:
                    icon = TOR;
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
            switch (subtyp) {
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8: {
                    icon = KEINTOR_FREISTOSS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8: {
                    icon = KEINTOR_MITTE;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8: {
                    icon = KEINTOR_LINKS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8: {
                    icon = KEINTOR_RECHTS;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8: {
                    icon = KEINTOR_ELFMETER;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_7:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_8: {
                	icon = KEINTOR_INDIRECT_FREEKICK;
                	break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR: {
                    icon = KEINTOR_SPECIAL;
                    break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF: {
                    icon = KEINTOR_COUNTER;
                    break;
                }

                default:
                    icon = KEINTOR;
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
                icon = ANGESCHLAGEN_KLEIN;
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)) {
                icon = VERLETZT_KLEIN;
            }
        }

        return icon;
    }

    /**
     * Gibt die Grafik für die Spieltypen zurück
     *
     * @param spieltyp TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Spieltyp(int spieltyp) {
        javax.swing.ImageIcon spieltypicon = null;
        final Integer key = new Integer(spieltyp);

        if (m_clSpieltypCache.containsKey(key)) {
            spieltypicon = (javax.swing.ImageIcon) m_clSpieltypCache.get(key);
        } else {
            switch (spieltyp) {
                case IMatchLineup.LIGASPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.POKALSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.QUALISPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/relegation.png"),
                                                                                  250, 250, 250,
                                                                                  255, 255, 255));
                    break;

                case IMatchLineup.LAENDERCUPSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.INTCUPSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.LAENDERSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.INTSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.INT_TESTCUPSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/freunschaft_intern.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.INT_TESTSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/freunschaft_intern.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTLAENDERSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTPOKALSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTSPIEL:
                    spieltypicon = new javax.swing.ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                //Fehler?
                default:
                    spieltypicon = new javax.swing.ImageIcon(new java.awt.image.BufferedImage(16,
                                                                                              16,
                                                                                              java.awt.image.BufferedImage.TYPE_INT_ARGB));
                    break;
            }

            m_clSpieltypCache.put(key, spieltypicon);
        }

        return spieltypicon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Trickotnummer(int wert) {
        javax.swing.ImageIcon icon = null;
        final Integer keywert = new Integer(wert);
        int xPosText = 5;

        // Nicht im Cache
        if (!m_clTrickotnummerCache.containsKey(keywert)) {
            java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(14, 14,
                                                                                  java.awt.image.BufferedImage.TYPE_INT_ARGB);

            //Pfeil zeichnen
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

            //g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            if ((wert > 0) && (wert < 100)) {
                final Image trickotImage = Helper.changeColor(Helper.makeColorTransparent(Helper
                                                                                          .loadImage("gui/bilder/Trickot.png"),
                                                                                          java.awt.Color.white),
                                                              java.awt.Color.black,
                                                              new java.awt.Color(200, 200, 200));

                //Wert eintragen
                //g2d.setComposite ( java.awt.AlphaComposite.getInstance( java.awt.AlphaComposite.SRC_OVER, 1.0f ) );
                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Position bei grossen Zahlen weiter nach vorne
                if (wert > 9) {
                    xPosText = 1;
                }

                g2d.setColor(java.awt.Color.darkGray);
                g2d.drawString(wert + "", xPosText, 11);
                g2d.setColor(java.awt.Color.black);
                g2d.drawString(wert + "", xPosText - 1, 11);

                image = (java.awt.image.BufferedImage) Helper.zusammenfuehren(trickotImage, image);
            }

            //Zusammenführen
            //Icon erstellen und in den Cache packen
            icon = new javax.swing.ImageIcon(image);
            m_clTrickotnummerCache.put(keywert, icon);
        }
        //Im Cache
        else {
            icon = (javax.swing.ImageIcon) m_clTrickotnummerCache.get(keywert);
        }

        return icon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Veraenderung(int wert) {
        return getImageIcon4Veraenderung(wert, true);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     * @param aktuell TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Veraenderung(int wert, boolean aktuell) {
        ExtendedImageIcon icon = null;
        final Integer keywert = new Integer(wert);
        int xPosText = 3;

        // Nicht im Cache
        if ((!m_clPfeilCache.containsKey(keywert) && aktuell)
            || (!m_clPfeilLightCache.containsKey(keywert) && !aktuell)) {
            final java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(14, 14,
                                                                                        java.awt.image.BufferedImage.TYPE_INT_ARGB);

            //Pfeil zeichnen
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

            //g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            if (wert == 0) {
                //                g2d.setColor ( java.awt.Color.darkGray );
                //                g2d.drawLine ( 3, 8, 9, 8 );
            } else if (wert > 0) {
                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
                final int[] ypoints = {6, 0, 0, 6, 6, 13, 13, 6, 6};

                //Polygon füllen
                if (!aktuell) {
                    g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                                                                         0.4f));
                }

                int farbwert = Math.min(240, 90 + (50 * wert));
                g2d.setColor(new java.awt.Color(0, farbwert, 0));
                g2d.fillPolygon(xpoints, ypoints, xpoints.length);

                //Polygonrahmen
                farbwert = Math.min(255, 105 + (50 * wert));
                g2d.setColor(new java.awt.Color(40, farbwert, 40));
                g2d.drawPolygon(xpoints, ypoints, xpoints.length);

                //Wert eintragen
                if (!aktuell) {
                    g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                                                                         1.0f));
                }

                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Für 1 und 2 Weisse Schrift oben
                if (wert < 3) {
                    g2d.setColor(java.awt.Color.black);
                    g2d.drawString(wert + "", xPosText, 11);
                    g2d.setColor(java.awt.Color.white);
                    g2d.drawString(wert + "", xPosText + 1, 11);
                }
                //Sonst Schwarze Schrift oben (nur bei Positiven Veränderungen)
                else {
                    //Position bei grossen Zahlen weiter nach vorne
                    if (wert > 9) {
                        xPosText = 0;
                    }

                    g2d.setColor(java.awt.Color.white);
                    g2d.drawString(wert + "", xPosText, 11);
                    g2d.setColor(java.awt.Color.black);
                    g2d.drawString(wert + "", xPosText + 1, 11);
                }
            } else if (wert < 0) {
                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
                final int[] ypoints = {7, 13, 13, 7, 7, 0, 0, 7, 7};

                //Polygon füllen
                if (!aktuell) {
                    g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                                                                         0.4f));
                }

                int farbwert = Math.min(240, 90 - (50 * wert));
                g2d.setColor(new java.awt.Color(farbwert, 0, 0));
                g2d.fillPolygon(xpoints, ypoints, xpoints.length);

                //Polygonrahmen
                farbwert = Math.min(255, 105 - (50 * wert));
                g2d.setColor(new java.awt.Color(farbwert, 40, 40));
                g2d.drawPolygon(xpoints, ypoints, xpoints.length);

                //Wert eintragen
                if (!aktuell) {
                    g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                                                                         1.0f));
                }

                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Position bei grossen Zahlen weiter nach vorne
                if (wert < -9) {
                    xPosText = 0;
                }

                g2d.setColor(java.awt.Color.black);
                g2d.drawString(Math.abs(wert) + "", xPosText, 11);
                g2d.setColor(java.awt.Color.white);
                g2d.drawString(Math.abs(wert) + "", xPosText + 1, 11);
            }

            //Icon erstellen und in den Cache packen
            icon = new ExtendedImageIcon(image);
            String desc = "(";
            if (wert>0) {
            	desc = desc + "+";
            }
            desc = desc + wert + ")";
            icon.setIconDescription(desc);

            if (aktuell) {
                m_clPfeilCache.put(keywert, icon);
            } else {
                m_clPfeilLightCache.put(keywert, icon);
            }

            //HOLogger.instance().log(Helper.class, "Create Pfeil: " + wert );
        }
        //Im Cache
        else {
            if (aktuell) {
                icon = (ExtendedImageIcon) m_clPfeilCache.get(keywert);
            } else {
                icon = (ExtendedImageIcon) m_clPfeilLightCache.get(keywert);
            }

            //HOLogger.instance().log(Helper.class, "Use Pfeilcache: " + wert );
        }

        return icon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4Wetter(int wert) {
        javax.swing.ImageIcon icon = null;

        if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_SONNE) {
            if (SONNIG == null) {
                SONNIG = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/sonnig.gif"),
                                                                                                          java.awt.Color.white)
                                                                                    .getScaledInstance(18,
                                                                                                       18,
                                                                                                       java.awt.Image.SCALE_SMOOTH));
            }

            icon = SONNIG;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_WOLKIG) {
            if (WOLKIG == null) {
                WOLKIG = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/leichtbewoelkt.gif"),
                                                                                                          java.awt.Color.white)
                                                                                    .getScaledInstance(18,
                                                                                                       18,
                                                                                                       java.awt.Image.SCALE_SMOOTH));
            }

            icon = WOLKIG;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_BEWOELKT) {
            if (BEWOELKT == null) {
                BEWOELKT = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/bewoelkt.gif"),
                                                                                                            java.awt.Color.white)
                                                                                      .getScaledInstance(18,
                                                                                                         18,
                                                                                                         java.awt.Image.SCALE_SMOOTH));
            }

            icon = BEWOELKT;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_REGEN) {
            if (REGEN == null) {
                REGEN = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/regen.gif"),
                                                                                                         java.awt.Color.white)
                                                                                   .getScaledInstance(18,
                                                                                                      18,
                                                                                                      java.awt.Image.SCALE_SMOOTH));
            }

            icon = REGEN;
        }

        return icon;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param wert TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.ImageIcon getImageIcon4WetterEffekt(int wert) {
        javax.swing.ImageIcon icon = null;

        if (wert < 0) {
            if (WETTERSCHLECHT == null) {
                WETTERSCHLECHT = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/schlecht.png"),
                                                                                 java.awt.Color.WHITE));
            }

            icon = WETTERSCHLECHT;
        } else if (wert > 0) {
            if (WETTERGUT == null) {
                WETTERGUT = new javax.swing.ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/gut.png"),
                                                                            java.awt.Color.WHITE));
            }

            icon = WETTERGUT;
        }

        return icon;
    }

    /////////////////////////Mathe
    public static float getLogarithmus(int value, int base) {
        return (float) (Math.log(value) / Math.log(base));
    }

    /**
     * Benötigte Breite für die Spalte in den Tabellen
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getMaxBewertungWidth(java.util.Vector spieler) {
        int bewertung = 0;

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            int aktuellebewertung = ((Spieler) spieler.get(i)).getBewertung();

            if (aktuellebewertung == 0) {
                aktuellebewertung = ((Spieler) spieler.get(i)).getLetzteBewertung();
            }

            bewertung = Math.max(bewertung, aktuellebewertung);
        }

        return (7 * bewertung) + 4;
    }

    /**
     * liefert Zufalls Zahl von 0 =&lt; maxZahl
     *
     * @param maxZahl TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getRandom(int maxZahl) {
        //a.charAt(5)
        return (int) Math.floor(Math.random() * (double) maxZahl);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     * @param subtyp TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getTooltiptext4SpielHighlight(int typ, int subtyp) {
    	final Properties properties = HOVerwaltung.instance().getResource();
        if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
                return properties.getProperty("highlight_yellowcard");
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
                return properties.getProperty("highlight_redcard");
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
            switch (subtyp) {
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
                    return properties.getProperty("highlight_freekick");

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8:
                    return properties.getProperty("highlight_middle");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8:
                    return properties.getProperty("highlight_links");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8:
                    return properties.getProperty("highlight_rechts");

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
                    return properties.getProperty("highlight_penalty");

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_7:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_8:
                	return properties.getProperty("highlight_freekick") + " " + properties.getProperty("indirect");

                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
                    return properties.getProperty("highlight_special");

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
                    return properties.getProperty("highlight_counter");

                default:
                    return "";
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
            switch (subtyp) {
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_FREISTOSS_8:
                    return properties.getProperty("highlight_freekick");

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8:
                    return properties.getProperty("highlight_middle");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8:
                    return properties.getProperty("highlight_links");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8:
                    return properties.getProperty("highlight_rechts");

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
                    return properties.getProperty("highlight_penalty");

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_7:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_8:
                	return properties.getProperty("highlight_freekick") + " " + properties.getProperty("indirect");

                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_VORLAGE_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_PASS_ABGEFANGEN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_WEITSCHUSS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALL_ERKAEMPFT_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNVORHERSEHBAR_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHNELLER_ANGREIFER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_SCHLECHTE_KONDITION_BALLVERLUST_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ECKBALL_KOPFTOR:
                case IMatchHighlight.HIGHLIGHT_SUB_ERFAHRENER_ANGREIFER_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_UNERFAHREN_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_QUERPASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_AUSSERGEWOEHNLICHER_PASS_TOR:
                case IMatchHighlight.HIGHLIGHT_SUB_TECHNIKER_ANGREIFER_TOR:
                    return properties.getProperty("highlight_special");

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
                    return properties.getProperty("highlight_counter");

                default:
                    return "";
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
                return properties.getProperty("Angeschlagen");
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)) {
                return properties.getProperty("Verletzt");
            }
        }

        return "";
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchID TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isUserMatch(String matchID) {
        try {
            if (!de.hattrickorganizer.net.MyConnector.instance().isAuthenticated()) {
                final de.hattrickorganizer.gui.login.LoginDialog ld = new de.hattrickorganizer.gui.login.LoginDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                                                                     .instance());
                ld.setVisible(true);
            }

            final String input = de.hattrickorganizer.net.MyConnector.instance().getMatchdetails(Integer
                                                                                                 .parseInt(matchID));

            final de.hattrickorganizer.model.matches.Matchdetails mdetails = new de.hattrickorganizer.logik.xml.xmlMatchdetailsParser()
                                                                             .parseMachtdetailsFromString(input);
            final int teamID = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                      .getBasics().getTeamId();

            return ((mdetails.getHeimId() == teamID) || (mdetails.getGastId() == teamID));
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * Errechnet die Spaltenbreite für den User-Schriftgrösse
     *
     * @param width TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int calcCellWidth(int width) {
        return (int) (((float) width) * gui.UserParameter.instance().zellenbreitenFaktor);
    }

    /**
     * Tauscht eine Farbe im Image durch eine andere
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param original TODO Missing Constructuor Parameter Documentation
     * @param change TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image changeColor(java.awt.Image im, java.awt.Color original,
                                             java.awt.Color change) {
        final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im.getSource(),
                                                                                       new ColorChangeFilter(original,
                                                                                                             change));
        return java.awt.Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Macht aus einem double[] mit Timevalues einen formatierten String[]
     *
     * @param timewerte TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String[] convertTimeMillisToFormatString(double[] timewerte) {
        final String[] returnwerte = new String[timewerte.length];

        for (int i = 0; i < returnwerte.length; i++) {
            returnwerte[i] = java.text.DateFormat.getDateInstance().format(new java.util.Date((long) timewerte[i]));
        }

        return returnwerte;
    }

    /**
     * Erzeugt ein ComboBoxModel aus einem Vector
     *
     * @param vector TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static javax.swing.DefaultComboBoxModel createListModel(java.util.Vector vector) {
        final javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel();

        for (int i = 0; i < vector.size(); i++) {
            model.addElement(vector.get(i));
        }

        return model;
    }

    ////Debug CacheTest------------------------------------------------

    /**
     * Debugausgaben Cache
     */
    public static void debugCacheTest() {
        HOLogger.instance().log(Helper.class,"-- Start CacheTest --");
        HOLogger.instance().log(Helper.class,"BilderCache        : " + m_clBilderCache.size());
        HOLogger.instance().log(Helper.class,"PfeilCache         : " + m_clPfeilCache.size());
        HOLogger.instance().log(Helper.class,"TransparentsCache  : " + m_clTransparentsCache.size());
        HOLogger.instance().log(Helper.class,"TrickotCache       : " + m_clTrickotCache.size());
        HOLogger.instance().log(Helper.class,"SpezialitaetCache  : " + m_clSpezialitaetCache.size());
        HOLogger.instance().log(Helper.class,"GruppenCache       : " + m_clGruppenCache.size());
        HOLogger.instance().log(Helper.class,"-- Ende  CacheTest --");
    }

    /**
     * Klappt den JTree komplett auf
     *
     * @param tree TODO Missing Constructuor Parameter Documentation
     */
    public static void expandTree(javax.swing.JTree tree) {
        final int count = tree.getRowCount();

        for (int i = 0; i < count; i++) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != count) {
            Helper.expandTree(tree);
        }
    }

    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert aus ints mit , getrennt besteht,
     * ansonsten setzt er den Wert auf 0
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int[] generateIntArray(String text) {
        //String message = "";
        final int[] tempzahlen = new int[100];

        try {
            int index = 0;
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) != ',') {
                    buffer.append("" + text.charAt(i));
                }
                //Komma gefunden
                else {
                    //buffer ist nicht leer
                    if (!buffer.toString().trim().equals("")) {
                        tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

                        /*
                           if ( !negativErlaubt && tempzahlen[index] < 0 )
                           {
                               //message = "Keinen negativen Werte erlaubt!";
                               throw new NumberFormatException();
                           }
                           //Groesser als Maximalwert
                           if ( tempzahlen[index] > maxValue )
                           {
                               //message = "Ein Wert ist zu hoch!";
                               throw new NumberFormatException();
                           }
                         */
                        index++;
                    }

                    buffer = new StringBuffer();
                }
            }

            if (!buffer.toString().trim().equals("")) {
                //Es folgt am Ende kein , mehr ->
                tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

                /*
                   if ( !negativErlaubt && tempzahlen[index] < 0 )
                   {
                       //message = "Keinen negativen Werte erlaubt!";
                       throw new NumberFormatException();
                   }
                   //Groesser als Maximalwert
                   if ( tempzahlen[index] > maxValue )
                   {
                       //message = "Ein Wert ist zu hoch!";
                       throw new NumberFormatException();
                   }
                 */
                index++;
            }

            //Zahlen in passenden Array kopieren
            final int[] zahlen = new int[index];

            for (int i = 0; i < index; i++) {
                zahlen[i] = tempzahlen[i];
            }

            return zahlen;
        } catch (NumberFormatException nfe) {
            /*
               if (message.equals("") )
               {
                   message = "Eine Eingabe ist keine Zahl!";
               }
               showMessage( parent, message, "Fehler", javax.swing.JOptionPane.ERROR_MESSAGE);
             */
            return null;
        }
    }

    /**
     * Lädt Grafiken auch im jar-File
     *
     * @param datei TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.image.BufferedImage loadImage(String datei) {
        java.awt.image.BufferedImage image = null;

        //Cache durchsuchen
        image = (java.awt.image.BufferedImage) m_clBilderCache.get(datei);

        //Nicht im Cache -> laden
        if (image == null) {
            try {
                java.net.URL resource = LADECOMPONENTE.getClass().getClassLoader().getResource(datei);

                //HOLogger.instance().log(Helper.class, datei );
                //Resource nicht gefunden!
                if (resource == null) {
                    HOLogger.instance().log(Helper.class,datei + " Not Found!!!");
                    resource = LADECOMPONENTE.getClass().getClassLoader().getResource("gui/bilder/Unknownflag.png");
                }

                //HOLogger.instance().log(Helper.class, resource.getPath() );
                //.component.getToolkit().createImage(resource);
                image = javax.imageio.ImageIO.read(resource);

                final java.awt.MediaTracker tracker = new java.awt.MediaTracker(LADECOMPONENTE);
                tracker.addImage(image, 1);

                try {
                    tracker.waitForAll();
                } catch (InterruptedException ie) {
                }

                //Bild in den Cache hinzufügen
                m_clBilderCache.put(datei, image);

                return image;
            } catch (Throwable e) {
                HOLogger.instance().log(Helper.class,e);
            }
        }

        return image;
    }

    /**
     * Macht eine Farbe in dem Bild transparent
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param color TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image makeColorTransparent(java.awt.Image im, java.awt.Color color) {
        java.awt.Image image = null;

        //Cache durchsuchen
        image = (java.awt.Image) m_clTransparentsCache.get(im);

        //Nicht im Cache -> laden
        if (image == null) {
            final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im
                                                                                           .getSource(),
                                                                                           new TransparentFilter(color));
            image = java.awt.Toolkit.getDefaultToolkit().createImage(ip);

            //Bild in den Cache hinzufügen
            m_clTransparentsCache.put(im, image);
        }

        return image;
    }

    /**
     * Macht eine Farbe in dem Bild transparent
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param minred TODO Missing Constructuor Parameter Documentation
     * @param mingreen TODO Missing Constructuor Parameter Documentation
     * @param minblue TODO Missing Constructuor Parameter Documentation
     * @param maxred TODO Missing Constructuor Parameter Documentation
     * @param maxgreen TODO Missing Constructuor Parameter Documentation
     * @param maxblue TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image makeColorTransparent(java.awt.Image im, int minred, int mingreen,
                                                      int minblue, int maxred, int maxgreen,
                                                      int maxblue) {
        final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im.getSource(),
                                                                                       new FuzzyTransparentFilter(minred,
                                                                                                                  mingreen,
                                                                                                                  minblue,
                                                                                                                  maxred,
                                                                                                                  maxgreen,
                                                                                                                  maxblue));
        return java.awt.Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Tauscht eine Farbe im Image durch eine andere
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image makeGray(java.awt.Image im, float value) {
        final java.awt.image.ImageProducer ip = new java.awt.image.FilteredImageSource(im.getSource(),
                                                                                       new LightGrayFilter(value));
        return java.awt.Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Markiert das Element mit der angegeben Id
     *
     * @param combobox TODO Missing Constructuor Parameter Documentation
     * @param id TODO Missing Constructuor Parameter Documentation
     */
    public static void markierenComboBox(javax.swing.JComboBox combobox, int id) {
        final javax.swing.ComboBoxModel model = combobox.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            if (((CBItem) (model.getElementAt(i))).getId() == id) {
                combobox.setSelectedItem(model.getElementAt(i));
                break;
            }
        }
    }

    /**
     * Parsen mit Defaultdezimaltformat
     *
     * @param parent TODO Missing Constructuor Parameter Documentation
     * @param field TODO Missing Constructuor Parameter Documentation
     * @param negativErlaubt TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean parseFloat(Window parent, JTextField field, boolean negativErlaubt) {
        return parseFloat(parent, field, negativErlaubt, DEFAULTDEZIMALFORMAT);
    }

    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert ein float ist, ansonsten setzt er den
     * Wert auf 0.0
     *
     * @param parent Window, in dem der Fehlerdialog angezeigt werden soll.
     * @param field Das Textfeld mit dem zu prüfenden Wert
     * @param negativErlaubt true, wenn negative Werte erlaubt sind
     * @param format das Dezimalformat
     *
     * @return true, wenn der Wert im Textfeld ein float ist, ansonsten false
     */
    public static boolean parseFloat(Window parent, JTextField field, boolean negativErlaubt,
                                     DecimalFormat format) {
        String message = "";

        try {
            final float temp = Float.parseFloat(kommaToPunkt(field.getText()));

            if (!negativErlaubt && (temp < 0.0f)) {
                message = "Keinen negativen Werte erlaubt!";
                throw new NumberFormatException();
            }

            field.setText(kommaToPunkt(String.valueOf(format.format(temp))));

            return true;
        } catch (NumberFormatException nfe) {
            if (message.equals("")) {
                message = "Eingabe ist keine Zahl!";
            }

            showMessage(parent, message, "Fehler", javax.swing.JOptionPane.ERROR_MESSAGE);
            field.setText(kommaToPunkt(String.valueOf(format.format(0.0))));
            return false;
        }
    }

    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert ein int ist, ansonsten setzt er den Wert
     * auf 0
     *
     * @param parent Window, in dem der Fehlerdialog angezeigt werden soll.
     * @param field Das Textfeld mit dem zu prüfenden Wert
     * @param negativErlaubt true, wenn negative Werte erlaubt sind
     *
     * @return true, wenn der Wert im Textfeld ein int ist, ansonsten false
     */
    public static boolean parseInt(Window parent, JTextField field, boolean negativErlaubt) {
        String message = "";

        try {
            final int temp = Integer.parseInt(field.getText());

            if (!negativErlaubt && (temp < 0)) {
                message = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("negativVerboten");
                throw new NumberFormatException();
            }

            field.setText(String.valueOf(temp));
            return true;
        } catch (NumberFormatException nfe) {
            if (message.equals("")) {
                message = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("keineZahl");
            }

            showMessage(parent, message,
                        de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                               .getProperty("Fehler"),
                        javax.swing.JOptionPane.ERROR_MESSAGE);

            field.setText(String.valueOf(0));
            return false;
        }
    }

    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert aus ints mit , getrennt besteht,
     * ansonsten setzt er den Wert auf 0
     *
     * @param parent Window, in dem der Fehlerdialog angezeigt werden soll.
     * @param field Das Textfeld mit dem zu prüfenden Wert
     * @param negativErlaubt true, wenn negative Werte erlaubt sind
     * @param maxValue der maximal erlaubte Wert
     *
     * @return Die Zahlen, wenn der Wert im Textfeld ints sind, ansonsten null
     *
     * @deprecated Keine Fehlermeldungen! Keine negativ/max Prüfung!
     */
    public static int[] parseMultiInt(Window parent, JTextField field, boolean negativErlaubt,
                                      int maxValue) {
        return generateIntArray(field.getText());
    }

    /**
     * Runden auf eine Nachkommastelle
     *
     * @param wert TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static float round(double wert) {
        return Helper.round(wert, 1);
    }

    /**
     * Rundet den übergeben wert auf eine bestimmte nachkommastellen-Anzahl
     *
     * @param wert Der zu rundene Wert
     * @param nachkommastellen Anzahl der Nachkommastellen
     *
     * @return TODO Missing Return Method Documentation
     */
    public static float round(double wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final double dwert = wert * Math.pow(10.0, (double) nachkommastellen);

        //Nachkommastellen abschneiden
        final long lwert = Math.round(dwert);

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (float) (lwert / Math.pow(10.0, (double) nachkommastellen));
    }

    /**
     * Zeigt eine Meldung per JOptionPane an, aber immer nur eine!
     *
     * @param parent TODO Missing Constructuor Parameter Documentation
     * @param message TODO Missing Constructuor Parameter Documentation
     * @param titel TODO Missing Constructuor Parameter Documentation
     * @param typ TODO Missing Constructuor Parameter Documentation
     */
    public static void showMessage(Component parent, String message, String titel, int typ) {
        //new gui.ShowMessageThread( parent, message, titel, typ );
        //Ignorieren, wenn schon ein Fehler angezeigt wird.
        if (!paneShown) {
            paneShown = true;
            javax.swing.JOptionPane.showMessageDialog(parent, message, titel, typ);
            paneShown = false;
        }
    }

    /*
     *sortieren eines doppelintArrays
     */
    public static int[][] sortintArray(int[][] toSort, int spaltenindex) {
        //Sicherheit!
        try {
            //Sicherheit!
            if ((toSort == null) || (toSort.length == 0) || (toSort[0].length == 0)) {
                return null;
            }

            final int[][] ergebnis = new int[toSort.length][toSort[0].length];
            final int[] sortSpalte = new int[toSort.length];

            //Spalte zum Sortieren holen
            for (int i = 0; i < toSort.length; i++) {
                sortSpalte[i] = toSort[i][spaltenindex];
            }

            //Spalte sortieren
            java.util.Arrays.sort(sortSpalte);

            //Alle Einträge durchlaufen und nach Wert im toSort suchen und den Wert dann in das Ergebnis kopieren
            for (int i = 0; i < toSort.length; i++) {
                for (int j = 0; j < toSort.length; j++) {
                    if (sortSpalte[i] == toSort[j][spaltenindex]) {
                        for (int k = 0; k < toSort[j].length; k++) {
                            ergebnis[i][k] = toSort[j][k];
                        }

                        break;
                    }
                }
            }

            //Referenz umbiegen
            // = ergebnis;
            return ergebnis;
        } catch (Exception e) {
            HOLogger.instance().log(Helper.class,"Helper.sortintArray:  " + e);
            return null;
        }
    }

    /**
     * Kopiert das zweite Image auf das erste
     *
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.awt.Image zusammenfuehren(java.awt.Image background,
                                                 java.awt.Image foreground) {
        final java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(background
                                                                                    .getWidth(null),
                                                                                    background
                                                                                    .getHeight(null),
                                                                                    java.awt.image.BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(background, 0, 0, null);
        image.getGraphics().drawImage(foreground, 0, 0, null);

        return image;
    }
    /**
     * proof if input is a word
     * @param input
     * @return
     */
    public static boolean isAlpha(String input) {
        return Pattern.matches("\\w*", input);
    }
    // -- private Methoden ----------------

    /**
     * Ersetzt Kommas durch Punkte
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static String kommaToPunkt(String text) {
        try {
            final StringBuffer temptext = new StringBuffer();

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ',') {
                    //,0 weglassen WORKAROUND #FIXME
                    if (((i + 2) == text.length()) && (text.charAt(i + 1) == '0')) {
                        return temptext.toString();
                    }

                    temptext.append(".");
                } else {
                    temptext.append(String.valueOf(text.charAt(i)));
                }
            }

            return temptext.toString();
        } catch (Throwable t) {
            return "0.0";
        }
    }

	/**
	 * Returns a NumberFormat based on the parameters
	 * @param currencyformat
	 * @param nachkommastellen
	 * @return
	 */
	public static NumberFormat getNumberFormat(boolean currencyformat, int nachkommastellen) {
		NumberFormat numFormat;
		if (currencyformat) {
			numFormat = Helper.CURRENCYFORMAT;
		} else {
			numFormat = NumberFormat.getNumberInstance();
		}
		numFormat.setMinimumFractionDigits(nachkommastellen);
		numFormat.setMaximumFractionDigits(nachkommastellen);
		return numFormat;

	}
}
