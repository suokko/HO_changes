// %1586804725:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import plugins.IMatchHighlight;
import plugins.IMatchLineup;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * Klasse mit Hilfsmethoden, die in mehreren Dialogen/Panels benutzt werden
 */
public class Helper extends LanguageFiles {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Componente zum Grafikenladen */
    public static ImagePanel LADECOMPONENTE = new de.hattrickorganizer.gui.templates.ImagePanel();

    /** yellow star */
    public static ImageIcon YELLOWSTARIMAGEICON;

    /** grey star */
    public static ImageIcon GREYSTARIMAGEICON;

    /** no icon */
    public static ImageIcon NOIMAGEICON;

    /** no match icon */
    public static ImageIcon NOMATCHICON;

    /** donload icon */
    public static ImageIcon DOWNLOADMATCHICON;

    /** show match icon */
    public static ImageIcon SHOWMATCHICON;

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

    /** goal - longshot */
    private static ImageIcon TOR_LONGSHOT;

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

    /** no goal - longshot */
    private static ImageIcon KEINTOR_LONGSHOT;

    /** no goal - SE */
    private static ImageIcon KEINTOR_SPECIAL;

    /** no goal - CA */
    private static ImageIcon KEINTOR_COUNTER;

    /** gear wheel */
    public static ImageIcon ZAHNRAD;

    /** manual */
    public static ImageIcon MANUELL;

    /** weather SEs */
    public static ImageIcon WEATHER_SE_RAIN_POS;
    public static ImageIcon WEATHER_SE_RAIN_NEG;
    public static ImageIcon WEATHER_SE_SUN_POS;
    public static ImageIcon WEATHER_SE_SUN_NEG;

    /** Gesamteinstufung */
	public static final CBItem[] EINSTUFUNG = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend), ISpieler.hervorragend),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.grossartig), ISpieler.grossartig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.brilliant), ISpieler.brilliant),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.fantastisch), ISpieler.fantastisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.Weltklasse), ISpieler.Weltklasse),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.uebernatuerlich), ISpieler.uebernatuerlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gigantisch), ISpieler.gigantisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.ausserirdisch), ISpieler.ausserirdisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.mythisch), ISpieler.mythisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.maerchenhaft), ISpieler.maerchenhaft),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.galaktisch), ISpieler.galaktisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.goettlich), ISpieler.goettlich) };

    /** Form */
	public static final CBItem[] EINSTUFUNG_FORM = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut) };

    /** Kondition */
	public static final CBItem[] EINSTUFUNG_KONDITION = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend), ISpieler.hervorragend) };

    /** Form */
	public static final CBItem[] EINSTUFUNG_TRAINER = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut) };

    /** Speciality */
    public static final CBItem[] EINSTUFUNG_SPECIALITY = {
    	new CBItem("", 0),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Technical"), 1),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Quick"),2),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Powerful"), 3),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Unpredictable"), 4),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Head"), 5),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Regainer"), 6)
    };

    /** Spielerpositionen */
	public static final CBItem[] SPIELERPOSITIONEN = {
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.KEEPER), ISpielerPosition.KEEPER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER), ISpielerPosition.CENTRAL_DEFENDER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER_OFF), ISpielerPosition.CENTRAL_DEFENDER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER_TOWING), ISpielerPosition.CENTRAL_DEFENDER_TOWING),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK), ISpielerPosition.BACK),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_OFF), ISpielerPosition.BACK_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_DEF), ISpielerPosition.BACK_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_TOMID), ISpielerPosition.BACK_TOMID),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER), ISpielerPosition.MIDFIELDER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_OFF), ISpielerPosition.MIDFIELDER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_DEF), ISpielerPosition.MIDFIELDER_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_TOWING), ISpielerPosition.MIDFIELDER_TOWING),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER), ISpielerPosition.WINGER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_OFF), ISpielerPosition.WINGER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_DEF), ISpielerPosition.WINGER_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_TOMID), ISpielerPosition.WINGER_TOMID),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD), ISpielerPosition.FORWARD),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD_DEF), ISpielerPosition.FORWARD_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD_TOWING), ISpielerPosition.FORWARD_TOWING) };

    /** team categories */
    public static final String[] TEAMSMILIES = { "", "A-Team.png",
			"B-Team.png", "C-Team.png", "D-Team.png", "E-Team.png", "F-Team.png" };

    /** smilies */
    public static final String[] MANUELLSMILIES = { "", "1bigsmile.png",
			"2smile.png", "3normal.png", "4sad.png", "5verysad.png",
			"6clown.png", "7trainer.png", "8dollar.png", "9coach.png" };

    /** weather combo boxes */
	public static final CBItem[] WETTER = { new CBItem("", 1), new CBItem("", 2), new CBItem("", 3), new CBItem("", 4) };

    /** Images */
    public static ExtendedImageIcon EMPTYIMAGE;

    /** bad weather */
    public static ImageIcon WETTERSCHLECHT;

    /** good weather */
    public static ImageIcon WETTERGUT;

    /** sunny */
    public static ImageIcon SONNIG;

    /** clouds */
    public static ImageIcon WOLKIG;

    /** partial clouded */
    public static ImageIcon BEWOELKT;

    /** rain */
    public static ImageIcon REGEN;

    //14px
    /** empty 14px */
    public static ImageIcon LEER = new ImageIcon(new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB));

    //8px
    /** empty small 8px */
    public static ImageIcon MINILEER = new ImageIcon(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));

	public static NumberFormat CURRENCYFORMAT = java.text.NumberFormat.getCurrencyInstance();

    /** wird für das Parsen in parseFloat benötigt */
    public static DecimalFormat INTEGERFORMAT = new java.text.DecimalFormat("#0");

    /** decimal format - 1 fraction digit */
    public static DecimalFormat DEFAULTDEZIMALFORMAT = new java.text.DecimalFormat("#0.0");

    /** decimal format - 2 fraction digits */
    public static DecimalFormat DEZIMALFORMAT_2STELLEN = new java.text.DecimalFormat("#0.00");

    /** decimal format - 3 fraction digits */
    public static DecimalFormat DEZIMALFORMAT_3STELLEN = new java.text.DecimalFormat("#0.000");

    /** Schon eine Meldung angezeigt? */
    public static boolean paneShown;

    /** Hashtable mit Veränderungspfeilgrafiken nach Integer als Key */
    private static Hashtable<Integer,ExtendedImageIcon> m_clPfeilCache = new Hashtable<Integer,ExtendedImageIcon>();
    private static Hashtable<Integer,ExtendedImageIcon> m_clPfeilLightCache = new Hashtable<Integer,ExtendedImageIcon>();

    /** Hashtable mit Trikotnummern nach Integer als Key */
    private static Hashtable<Integer,ImageIcon> m_clTrickotnummerCache = new Hashtable<Integer,ImageIcon>();

    /** Cache für Bilder */
    private static HashMap<String,BufferedImage> m_clBilderCache = new HashMap<String,BufferedImage>();

    /** Cache für Transparent gemachte Bilder */
    private static HashMap<Image,Image> m_clTransparentsCache = new HashMap<Image,Image>();

    /** Cache für Trickots */
    private static HashMap<TrickotCacheKey,ImageIcon> m_clTrickotCache = new HashMap<TrickotCacheKey,ImageIcon>();

    /** Cache für Spezialitäten */
    private static HashMap<Integer,ExtendedImageIcon> m_clSpezialitaetCache = new HashMap<Integer,ExtendedImageIcon>();

    /** Cache für Gruppen */
    private static HashMap<String,ImageIcon> m_clGruppenCache = new HashMap<String,ImageIcon>();

    /** Cache für MiniGruppen */
    private static HashMap<String,ImageIcon> m_clMiniGruppenCache = new HashMap<String,ImageIcon>();

    /** Cache für Spieltypen */
    private static HashMap<Integer,ImageIcon> m_clSpieltypCache = new HashMap<Integer,ImageIcon>();

    //Initialisierung
    static {
        YELLOWSTARIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/star.gif"), Color.white));
        GREYSTARIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/star_grey.png"), Color.white));
        NOIMAGEICON = new ImageIcon(new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB));
        NOMATCHICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/NoMatch.png"), Color.white));
        DOWNLOADMATCHICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/DownloadMatch.png"), Color.white));
        SHOWMATCHICON = new ImageIcon(Helper.makeColorTransparent( Helper.loadImage("gui/bilder/ShowMatch.png"), Color.white));
        ROTEKARTE = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/disqualifiziert.png"), Color.white));
        GELBEKARTE = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/verwarnung_1.png"), Color.white));
        DOPPELGELB = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/verwarnung_2.png"), Color.white));
        ANGESCHLAGEN = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/angeschlagen.png"), Color.white));
        VERLETZT = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/verletzt.png"),  Color.white));
        ANGESCHLAGEN_KLEIN = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/angeschlagen_klein.png"), Color.red));
        VERLETZT_KLEIN = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/verletzt_klein.png"), Color.white));

        TOR = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball.png"), new Color(255,0, 0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_FREISTOSS = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Freistoss.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_MITTE = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Mitte.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_LINKS = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Links.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_RECHTS = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Rechts.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_ELFMETER = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Elfmeter.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_INDIRECT_FREEKICK = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_FreistossIndirekt.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_LONGSHOT = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Longshot.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_SPECIAL = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Spezial.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        TOR_COUNTER = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Fussball_Konter.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10,Image.SCALE_SMOOTH));

        KEINTOR = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_FREISTOSS = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Freistoss.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_MITTE = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Mitte.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_LINKS = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Links.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_RECHTS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Rechts.png"),
        		new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_ELFMETER = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/KeinFussball_Elfmeter.png"),
        		new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_INDIRECT_FREEKICK = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_FreistossIndirekt.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_LONGSHOT = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Longshot.png"), new Color(255, 0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));

        KEINTOR_SPECIAL = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Spezial.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        KEINTOR_COUNTER = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/KeinFussball_Konter.png"), new Color(255,0,0))
        		.getScaledInstance(16, 10, Image.SCALE_SMOOTH));

        ZAHNRAD = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/zahnrad.png"), new Color(255,255,255)));
        MANUELL = new ImageIcon(Helper.makeColorTransparent(
        		Helper.loadImage("gui/bilder/Manuell.png"), new Color(255, 255, 255)));

        WEATHER_SE_RAIN_POS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/se_rain_positive.png"),
                new Color(255, 0,0)).getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        WEATHER_SE_RAIN_NEG = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/se_rain_negative.png"),
                new Color(255, 0,0)).getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        WEATHER_SE_SUN_POS = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/se_sun_positive.png"),
                new Color(255, 0,0)).getScaledInstance(16, 10, Image.SCALE_SMOOTH));
        WEATHER_SE_SUN_NEG = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/se_sun_negative.png"),
                new Color(255, 0,0)).getScaledInstance(16, 10, Image.SCALE_SMOOTH));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the color for the given highlight type and subtype.
     */
	public static Color getColor4SpielHighlight(int typ, int subtyp) {
		if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
			if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
				return gui.UserParameter.instance().FG_ZWEIKARTEN;
			} else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
					|| (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
				return gui.UserParameter.instance().FG_GESPERRT;
			}
		} else if (typ == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
			return Color.BLACK;
		} else if (typ == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
			return Color.GRAY;
		} else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
			if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER) || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
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
     * Return ImageIcon for Position
     *
     */
    public static ImageIcon getImage4Position(de.hattrickorganizer.model.SpielerPosition position,
                                                          int trickotnummer) {
        if (position == null) {
            return getImage4Position(0, (byte) 0, trickotnummer);
        }

        return getImage4Position(position.getId(), position.getTaktik(), trickotnummer);
    }

    /**
     * Return ImageIcon for Position
     *
     */
    public static ImageIcon getImage4Position(int posid, byte taktik, int trickotnummer) {
		Color trickotfarbe = null;
		Image trickotImage = null;
		Image zusammenImage = null;
		ImageIcon komplettIcon = null;

		// Im Cache nachsehen
		komplettIcon = (ImageIcon) m_clTrickotCache.get(new TrickotCacheKey(posid, taktik));

		if (komplettIcon == null) {
			switch (posid) {
				case ISpielerPosition.keeper: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.keeper");
					break;
				}
	
				case ISpielerPosition.rightCentralDefender:
				case ISpielerPosition.leftCentralDefender:
				case ISpielerPosition.middleCentralDefender: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.centraldefence");
					break;
				}
	
				case ISpielerPosition.leftBack:
				case ISpielerPosition.rightBack: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.wingback");
					break;
				}
	
				case ISpielerPosition.rightInnerMidfield:
				case ISpielerPosition.leftInnerMidfield:
				case ISpielerPosition.centralInnerMidfield: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.midfield");
					break;
				}
	
				case ISpielerPosition.leftWinger:
				case ISpielerPosition.rightWinger: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.wing");
					break;
				}
	
				case ISpielerPosition.rightForward:
				case ISpielerPosition.leftForward:
				case ISpielerPosition.centralForward: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.forward");
					break;
				}
	
				case ISpielerPosition.substKeeper: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.subKeeper");
					break;
				}
	
				case ISpielerPosition.substDefender: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.subDefence");
					break;
				}
	
				case ISpielerPosition.substInnerMidfield: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.subMidfield");
					break;
				}
	
				case ISpielerPosition.substWinger: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.subWing");
					break;
				}
	
				case ISpielerPosition.substForward: {
					trickotfarbe = ThemeManager.getColor("ho.shirt.subForward");
					break;
				}
	
				default:
					trickotfarbe = ThemeManager.getColor("ho.shirt");
			}

			// Bild laden, transparenz hinzu, trikofarbe wechseln
			trickotImage = Helper.changeColor(Helper.changeColor(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Trickot.png"),
					Color.white), Color.black, trickotfarbe), new Color(100, 100, 100), trickotfarbe.brighter());

			zusammenImage = trickotImage;
			komplettIcon = new ImageIcon(zusammenImage);

			// In den Cache hinzufügen
			m_clTrickotCache.put(new TrickotCacheKey(posid, taktik), komplettIcon);
		} 

		// return new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
		// Trickotnummer
		if ((trickotnummer > 0) && (trickotnummer < 100)) {
			BufferedImage image = new BufferedImage(24, 14, BufferedImage.TYPE_INT_ARGB);

			// 5;
			int xPosText = 18;

			// Helper.makeColorTransparent( image, Color.white );
			final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

			// Wert eintragen
			// g2d.setComposite ( AlphaComposite.getInstance(
			// AlphaComposite.SRC_OVER, 1.0f ) );
			g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 10));

			// Position bei grossen Zahlen weiter nach vorne
			if (trickotnummer > 9) {
				xPosText = 12;
			}

			g2d.setColor(Color.black);
			g2d.drawString(trickotnummer + "", xPosText, 13);

			// Zusammenführen
			image = (BufferedImage) Helper.zusammenfuehren(komplettIcon.getImage(), image);

			// Icon erstellen und in den Cache packen
			komplettIcon = new ImageIcon(image);
		}

		return komplettIcon;
	}

    /**
     * paint a cross over an image
     */
    public static Image getImageDurchgestrichen(Image image,
                                                         Color helleFarbe,
                                                         Color dunkleFarbe) {
        try {
            final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

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

    public static ImageIcon getImageIcon4Color(Color color) {
        final BufferedImage bufferedImage = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);

        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) bufferedImage.getGraphics();

        g2d.setColor(color);
        g2d.fillRect(0, 0, 13, 13);

        return new ImageIcon(bufferedImage);
    }

    public static ImageIcon getImageIcon4Country(int country) {
        return new ImageIcon(Helper.loadImage("flags/"+ country + "flag.png"));
    }

    public static ImageIcon getImageIcon4GruppeSmilie(String gruppe) {
        ImageIcon gruppenicon = null;

        //Keine Gruppe / Smilie
        if (gruppe.trim().equals("")) {
            return LEER;
        }

        if (m_clGruppenCache.containsKey(gruppe)) {
            gruppenicon = (ImageIcon) m_clGruppenCache.get(gruppe);
        } else {
            gruppenicon = new ImageIcon(
            		Helper.makeColorTransparent(
            				de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/smilies/" + gruppe),
            				new Color(209, 41, 144)));
            m_clGruppenCache.put(gruppe, gruppenicon);
        }

        return gruppenicon;
    }

    /**
     * Gibt die Grafik für die Gruppe in 7px Grösse für die Aufstellung zurück
     *
     */
    public static ImageIcon getImageIcon4MiniGruppe(String gruppe) {
        ImageIcon gruppenicon = null;

        //Keine Gruppe / Smilie
        if (gruppe.trim().equals("")) {
            return MINILEER;
        }

        if (m_clMiniGruppenCache.containsKey(gruppe)) {
            gruppenicon = (ImageIcon) m_clMiniGruppenCache.get(gruppe);
        } else {
            gruppenicon = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/smilies/"
                                                                                                                      + gruppe)
                                                                                                           .getScaledInstance(8,
                                                                                                                              8,
                                                                                                                              Image.SCALE_SMOOTH),
                                                                          new Color(209,
                                                                                             41, 144)));
            m_clMiniGruppenCache.put(gruppe, gruppenicon);
        }

        return gruppenicon;
    }

    public static ImageIcon getImageIcon4Spezialitaet(int wert) {
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

    public static ImageIcon getImageIcon4SpielHighlight(int typ, int subtyp) {
        ImageIcon icon = null;

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

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2: {
                	icon = TOR_INDIRECT_FREEKICK;
                	break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1: {
                	icon = TOR_LONGSHOT;
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

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2: {
                	icon = KEINTOR_INDIRECT_FREEKICK;
                	break;
                }

                case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1: {
                	icon = KEINTOR_LONGSHOT;
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
        } else if (typ == IMatchHighlight.HIGHLIGHT_SPEZIAL) {
        	switch (subtyp) {
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY: 	// +
        		icon = WEATHER_SE_RAIN_POS; break;
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY: 	// +
        		icon = WEATHER_SE_SUN_POS; break;
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY: 	// -
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY:		// -
        		icon = WEATHER_SE_SUN_NEG; break;
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY:		// -
        	case IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY: 	// -
        		icon = WEATHER_SE_RAIN_NEG; break;
        	default:
        		icon = null;
        	}
        }

        return icon;
    }

    /**
     * Check, if the highlight is a weather SE highlight.
     */
    public static boolean isWeatherSEHighlight(int typ, int subtyp) {
    	return (typ == IMatchHighlight.HIGHLIGHT_SPEZIAL &&
    			(subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_RAINY ||
    			subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_SUNNY ||
    			subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_POWERFUL_SUNNY ||
    			subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_SUNNY ||
    			subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_QUICK_RAINY ||
    			subtyp == IMatchHighlight.HIGHLIGHT_SUB_PLAYER_TECHNICAL_RAINY));
    }

    /**
     * Gibt die Grafik für die Spieltypen zurück
     *
    */
    public static ImageIcon getImageIcon4Spieltyp(int spieltyp) {
        ImageIcon spieltypicon = null;
        final Integer key = new Integer(spieltyp);

        if (m_clSpieltypCache.containsKey(key)) {
            spieltypicon = (ImageIcon) m_clSpieltypCache.get(key);
        } else {
            switch (spieltyp) {
                case IMatchLineup.LIGASPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.POKALSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.QUALISPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/relegation.png"),
                                                                                  250, 250, 250,
                                                                                  255, 255, 255));
                    break;

                case IMatchLineup.LAENDERCUPSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.INTCUPSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.LAENDERSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Meisterschale2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  5, 240));
                    break;

                case IMatchLineup.INTSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/Pokal2.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.INT_TESTCUPSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/freunschaft_intern.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.INT_TESTSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/freunschaft_intern.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTLAENDERSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTPOKALSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                case IMatchLineup.TESTSPIEL:
                    spieltypicon = new ImageIcon(makeColorTransparent(loadImage("gui/bilder/freundschaft.png"),
                                                                                  230, 0, 135, 240,
                                                                                  10, 240));
                    break;

                //Fehler?
                default:
                    spieltypicon = new ImageIcon(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
                    break;
            }

            m_clSpieltypCache.put(key, spieltypicon);
        }

        return spieltypicon;
    }

    public static ImageIcon getImageIcon4Trickotnummer(int wert) {
        ImageIcon icon = null;
        final Integer keywert = new Integer(wert);
        int xPosText = 5;

        // Nicht im Cache
        if (!m_clTrickotnummerCache.containsKey(keywert)) {
            BufferedImage image = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);

            //Pfeil zeichnen
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

            //g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            if ((wert > 0) && (wert < 100)) {
                final Image trickotImage = Helper.changeColor(
                		Helper.makeColorTransparent(Helper.loadImage("gui/bilder/Trickot.png"),
                				Color.white), Color.black, new Color(200, 200, 200));

                //Wert eintragen
                //g2d.setComposite ( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1.0f ) );
                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Position bei grossen Zahlen weiter nach vorne
                if (wert > 9) {
                    xPosText = 1;
                }

                g2d.setColor(Color.darkGray);
                g2d.drawString(wert + "", xPosText, 11);
                g2d.setColor(Color.black);
                g2d.drawString(wert + "", xPosText - 1, 11);

                image = (BufferedImage) Helper.zusammenfuehren(trickotImage, image);
            }

            //Zusammenführen
            //Icon erstellen und in den Cache packen
            icon = new ImageIcon(image);
            m_clTrickotnummerCache.put(keywert, icon);
        }
        //Im Cache
        else {
            icon = (ImageIcon) m_clTrickotnummerCache.get(keywert);
        }

        return icon;
    }

    public static ImageIcon getImageIcon4Veraenderung(int wert) {
        return getImageIcon4Veraenderung(wert, true);
    }

    public static ImageIcon getImageIcon4Veraenderung(int wert, boolean aktuell) {
        ExtendedImageIcon icon = null;
        final Integer keywert = new Integer(wert);
        int xPosText = 3;

        // Nicht im Cache
        if ((!m_clPfeilCache.containsKey(keywert) && aktuell)
            || (!m_clPfeilLightCache.containsKey(keywert) && !aktuell)) {
            final BufferedImage image = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);

            //Pfeil zeichnen
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();

            //g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            if (wert == 0) {
                //                g2d.setColor ( Color.darkGray );
                //                g2d.drawLine ( 3, 8, 9, 8 );
            } else if (wert > 0) {
                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
                final int[] ypoints = {6, 0, 0, 6, 6, 13, 13, 6, 6};

                //Polygon füllen
                if (!aktuell) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                }

                int farbwert = Math.min(240, 90 + (50 * wert));
                g2d.setColor(new Color(0, farbwert, 0));
                g2d.fillPolygon(xpoints, ypoints, xpoints.length);

                //Polygonrahmen
                farbwert = Math.min(255, 105 + (50 * wert));
                g2d.setColor(new Color(40, farbwert, 40));
                g2d.drawPolygon(xpoints, ypoints, xpoints.length);

                //Wert eintragen
                if (!aktuell) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }

                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Für 1 und 2 Weisse Schrift oben
                if (wert < 3) {
                    g2d.setColor(Color.black);
                    g2d.drawString(wert + "", xPosText, 11);
                    g2d.setColor(Color.white);
                    g2d.drawString(wert + "", xPosText + 1, 11);
                }
                //Sonst Schwarze Schrift oben (nur bei Positiven Veränderungen)
                else {
                    //Position bei grossen Zahlen weiter nach vorne
                    if (wert > 9) {
                        xPosText = 0;
                    }

                    g2d.setColor(Color.white);
                    g2d.drawString(wert + "", xPosText, 11);
                    g2d.setColor(Color.black);
                    g2d.drawString(wert + "", xPosText + 1, 11);
                }
            } else if (wert < 0) {
                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
                final int[] ypoints = {7, 13, 13, 7, 7, 0, 0, 7, 7};

                //Polygon füllen
                if (!aktuell) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                }

                int farbwert = Math.min(240, 90 - (50 * wert));
                g2d.setColor(new Color(farbwert, 0, 0));
                g2d.fillPolygon(xpoints, ypoints, xpoints.length);

                //Polygonrahmen
                farbwert = Math.min(255, 105 - (50 * wert));
                g2d.setColor(new Color(farbwert, 40, 40));
                g2d.drawPolygon(xpoints, ypoints, xpoints.length);

                //Wert eintragen
                if (!aktuell) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }

                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));

                //Position bei grossen Zahlen weiter nach vorne
                if (wert < -9) {
                    xPosText = 0;
                }

                g2d.setColor(Color.black);
                g2d.drawString(Math.abs(wert) + "", xPosText, 11);
                g2d.setColor(Color.white);
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

    public static ImageIcon getImageIcon4Wetter(int wert) {
        ImageIcon icon = null;

        if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_SONNE) {
            if (SONNIG == null) {
                SONNIG = new ImageIcon(
                		Helper.makeColorTransparent(
                				Helper.loadImage("gui/bilder/wetter/sonnig.gif"), Color.white)
                				.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            }

            icon = SONNIG;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_WOLKIG) {
            if (WOLKIG == null) {
                WOLKIG = new ImageIcon(
                		Helper.makeColorTransparent(
                				Helper.loadImage("gui/bilder/wetter/leichtbewoelkt.gif"), Color.white)
                				.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            }

            icon = WOLKIG;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_BEWOELKT) {
            if (BEWOELKT == null) {
                BEWOELKT = new ImageIcon(
                		Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/bewoelkt.gif"), Color.white)
                		.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            }

            icon = BEWOELKT;
        } else if (wert == de.hattrickorganizer.model.matches.Matchdetails.WETTER_REGEN) {
            if (REGEN == null) {
                REGEN = new ImageIcon(
                		Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/regen.gif"), Color.white)
                		.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
            }

            icon = REGEN;
        }

        return icon;
    }

    /**
     * Get the icon for a certain weather effect.
     */
    public static ImageIcon getImageIcon4WetterEffekt(int wert) {
        ImageIcon icon = null;

        if (wert < 0) {
            if (WETTERSCHLECHT == null) {
                WETTERSCHLECHT = new ImageIcon(
                		Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/schlecht.png"),
                				Color.WHITE));
            }

            icon = WETTERSCHLECHT;
        } else if (wert > 0) {
            if (WETTERGUT == null) {
                WETTERGUT = new ImageIcon(
                		Helper.makeColorTransparent(Helper.loadImage("gui/bilder/wetter/gut.png"),
                				Color.WHITE));
            }

            icon = WETTERGUT;
        }

        return icon;
    }


    public static String getTooltiptext4SpielHighlight(int typ, int subtyp) {
        if (typ == IMatchHighlight.HIGHLIGHT_KARTEN) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_HARTER_EINSATZ)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_UNFAIR)) {
                return HOVerwaltung.instance().getLanguageString("highlight_yellowcard");
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_ROT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR)) {
                return HOVerwaltung.instance().getLanguageString("highlight_redcard");
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
                    return HOVerwaltung.instance().getLanguageString("highlight_freekick");

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_middle");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_links");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_rechts");

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_penalty");

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
                	return HOVerwaltung.instance().getLanguageString("highlight_freekick") + " " + HOVerwaltung.instance().getLanguageString("indirect");

                case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
                	return HOVerwaltung.instance().getLanguageString("Tactic.LongShots");

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
                    return HOVerwaltung.instance().getLanguageString("highlight_special");

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
                    return HOVerwaltung.instance().getLanguageString("highlight_counter");

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
                    return HOVerwaltung.instance().getLanguageString("highlight_freekick");

                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_2:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_3:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_4:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_5:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_6:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_7:
                case IMatchHighlight.HIGHLIGHT_SUB_DURCH_MITTE_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_middle");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_LINKS_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_links");

                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_2:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_3:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_4:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_5:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_6:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_7:
                case IMatchHighlight.HIGHLIGHT_SUB_UEBER_RECHTS_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_rechts");

                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_2:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_3:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_4:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_5:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_6:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_7:
                case IMatchHighlight.HIGHLIGHT_SUB_ELFMETER_8:
                    return HOVerwaltung.instance().getLanguageString("highlight_penalty");

                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_1:
                case IMatchHighlight.HIGHLIGHT_SUB_INDIRECT_FREEKICK_2:
                	return HOVerwaltung.instance().getLanguageString("highlight_freekick") + " " + HOVerwaltung.instance().getLanguageString("indirect");

                case IMatchHighlight.HIGHLIGHT_SUB_LONGHSHOT_1:
                	return HOVerwaltung.instance().getLanguageString("Tactic.LongShots");

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
                    return HOVerwaltung.instance().getLanguageString("highlight_special");

                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_EINS:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_ZWEI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_DREI:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_VIER:
                case IMatchHighlight.HIGHLIGHT_SUB_KONTERANGRIFF_FUENF:
                    return HOVerwaltung.instance().getLanguageString("highlight_counter");

                default:
                    return "";
            }
        } else if (typ == IMatchHighlight.HIGHLIGHT_INFORMATION) {
            if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER)
                || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_PFLASTER_BEHANDLUNG)) {
                return HOVerwaltung.instance().getLanguageString("Angeschlagen");
            } else if ((subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT)
                       || (subtyp == IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI)) {
                return HOVerwaltung.instance().getLanguageString("Verletzt");
            }
        }

        return "";
    }

    /**
     * Check, if the given match id belongs to the current HO user.
     */
    public static boolean isUserMatch(String matchID) {
        try {
//            if (!de.hattrickorganizer.net.MyConnector.instance().isAuthenticated()) {
//                final de.hattrickorganizer.gui.login.LoginDialog ld =
//                	new de.hattrickorganizer.gui.login.LoginDialog(HOMainFrame.instance());
//                ld.setVisible(true);
//            }

            final String input = de.hattrickorganizer.net.MyConnector.instance().getMatchdetails(Integer.parseInt(matchID));
            final de.hattrickorganizer.model.matches.Matchdetails mdetails = new de.hattrickorganizer.logik.xml.xmlMatchdetailsParser()
                                                                             .parseMachtdetailsFromString(input);
            final int teamID = HOVerwaltung.instance().getModel().getBasics().getTeamId();

            return ((mdetails.getHeimId() == teamID) || (mdetails.getGastId() == teamID));
        } catch (Exception e) {
        	HOLogger.instance().warning(Helper.class, "Err: " + e);
        }

        return false;
    }

    /**
     * Errechnet die Spaltenbreite für den User-Schriftgrösse
     *
     */
    public static int calcCellWidth(int width) {
        return (int) (((float) width) * gui.UserParameter.instance().zellenbreitenFaktor);
    }

    /**
     * Tauscht eine Farbe im Image durch eine andere
     *
     */
    public static Image changeColor(Image im, Color original,
                                             Color change) {
        final ImageProducer ip = new FilteredImageSource(im.getSource(),
        		new ColorChangeFilter(original, change));
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Macht aus einem double[] mit Timevalues einen formatierten String[]
     *
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
     */
    public static DefaultComboBoxModel createListModel(Vector<Object> vector) {
        final DefaultComboBoxModel model = new DefaultComboBoxModel();

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
     * Überprüft den Inhalt eines Textfields, ob der Wert aus ints mit , getrennt besteht,
     * ansonsten setzt er den Wert auf 0
     *
     */
	public static int[] generateIntArray(String text) {
		// String message = "";
		final int[] tempzahlen = new int[100];

		try {
			int index = 0;
			StringBuffer buffer = new StringBuffer();

			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) != ',') {
					buffer.append("" + text.charAt(i));
				} else { // Komma gefunden
					// buffer ist nicht leer
					if (!buffer.toString().trim().equals("")) {
						tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

						/*
						 * if ( !negativErlaubt && tempzahlen[index] < 0 ) {
						 * //message = "Keinen negativen Werte erlaubt!"; throw
						 * new NumberFormatException(); } //Groesser als
						 * Maximalwert if ( tempzahlen[index] > maxValue ) {
						 * //message = "Ein Wert ist zu hoch!"; throw new
						 * NumberFormatException(); }
						 */
						index++;
					}

					buffer = new StringBuffer();
				}
			}

			if (!buffer.toString().trim().equals("")) {
				// Es folgt am Ende kein , mehr ->
				tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

				/*
				 * if ( !negativErlaubt && tempzahlen[index] < 0 ) { //message =
				 * "Keinen negativen Werte erlaubt!"; throw new
				 * NumberFormatException(); } //Groesser als Maximalwert if (
				 * tempzahlen[index] > maxValue ) { //message =
				 * "Ein Wert ist zu hoch!"; throw new NumberFormatException(); }
				 */
				index++;
			}

			// Zahlen in passenden Array kopieren
			final int[] zahlen = new int[index];

			for (int i = 0; i < index; i++) {
				zahlen[i] = tempzahlen[i];
			}

			return zahlen;
		} catch (NumberFormatException nfe) {
			/*
			 * if (message.equals("") ) { message =
			 * "Eine Eingabe ist keine Zahl!"; } showMessage( parent, message,
			 * "Fehler", javax.swing.JOptionPane.ERROR_MESSAGE);
			 */
			return null;
		}
    }

    /**
     * Lädt Grafiken auch im jar-File
     *
     */
	public static BufferedImage loadImage(String datei) {
		BufferedImage image = null;

		// Cache durchsuchen
		image = (BufferedImage) m_clBilderCache.get(datei);

		// Nicht im Cache -> laden
		if (image == null) {
			try {
				URL resource = LADECOMPONENTE.getClass().getClassLoader().getResource(datei);

				// HOLogger.instance().log(Helper.class, datei );
				// Resource nicht gefunden!
				if (resource == null) {
					HOLogger.instance().log(Helper.class, datei + " Not Found!!!");
					resource = LADECOMPONENTE.getClass().getClassLoader().getResource("gui/bilder/Unknownflag.png");
				}

				// HOLogger.instance().log(Helper.class, resource.getPath() );
				// .component.getToolkit().createImage(resource);
				image = ImageIO.read(resource);

				final MediaTracker tracker = new MediaTracker(LADECOMPONENTE);
				tracker.addImage(image, 1);

				try {
					tracker.waitForAll();
				} catch (InterruptedException ie) {
				}

				// Bild in den Cache hinzufügen
				m_clBilderCache.put(datei, image);

				return image;
			} catch (Throwable e) {
				HOLogger.instance().log(Helper.class, e);
			}
		}

		return image;
	}

    /**
     * Macht eine Farbe in dem Bild transparent
     *
     */
    public static Image makeColorTransparent(Image im, Color color) {
        Image image = null;

        //Cache durchsuchen
        image = (Image) m_clTransparentsCache.get(im);

        //Nicht im Cache -> laden
        if (image == null) {
            final ImageProducer ip = new FilteredImageSource(im.getSource(), new TransparentFilter(color));
            image = Toolkit.getDefaultToolkit().createImage(ip);

            //Bild in den Cache hinzufügen
            m_clTransparentsCache.put(im, image);
        }

        return image;
    }

    /**
     * Macht eine Farbe in dem Bild transparent
     *
     */
    public static Image makeColorTransparent(Image im, int minred, int mingreen,
                                                      int minblue, int maxred, int maxgreen,
                                                      int maxblue) {
        final ImageProducer ip = new FilteredImageSource(im.getSource(),
        		new FuzzyTransparentFilter(minred, mingreen, minblue, maxred, maxgreen, maxblue));
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Tauscht eine Farbe im Image durch eine andere
     *
     */
    public static Image makeGray(Image im, float value) {
        final ImageProducer ip = new FilteredImageSource(im.getSource(), new LightGrayFilter(value));
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Markiert das Element mit der angegeben Id
     *
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
     * Überprüft den Inhalt eines Textfields, ob der Wert ein int ist, ansonsten setzt er den Wert
     * auf 0
     *
     */
    public static boolean parseInt(Window parent, JTextField field, boolean negativErlaubt) {
        String message = "";

        try {
            final int temp = Integer.parseInt(field.getText());

            if (!negativErlaubt && (temp < 0)) {
                message = HOVerwaltung.instance().getLanguageString("negativVerboten");
                throw new NumberFormatException();
            }

            field.setText(String.valueOf(temp));
            return true;
        } catch (NumberFormatException nfe) {
            if (message.equals("")) {
                message = HOVerwaltung.instance().getLanguageString("keineZahl");
            }

            showMessage(parent, message,
                        HOVerwaltung.instance().getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);

            field.setText(String.valueOf(0));
            return false;
        }
    }


    /**
     * Round to one fraction digit
     *
     */
    public static float round(float wert) {
        return Helper.round(wert, 1);
    }

    /**
     * Round a double value
     *
     */
    public static double round(double wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final double dwert = wert * Math.pow(10.0, nachkommastellen);

        //Nachkommastellen abschneiden
//        final long lwert = Math.round(dwert);
        final double lwert = (int) dwert;

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (lwert / Math.pow(10.0, nachkommastellen));
    }

    /**
     * Round a float value
     *
     */
    public static float round(float wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final float dwert = wert * (int)Math.pow(10.0, nachkommastellen);

        //Nachkommastellen abschneiden
//        final long lwert = Math.round(dwert);
        final float lwert = (int) dwert;

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (lwert / (int)Math.pow(10.0, nachkommastellen));
    }

    /**
     * Zeigt eine Meldung per JOptionPane an, aber immer nur eine!
     *
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
     */
    public static Image zusammenfuehren(Image background, Image foreground) {
        final BufferedImage image = new BufferedImage(
        		background.getWidth(null), background.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(background, 0, 0, null);
        image.getGraphics().drawImage(foreground, 0, 0, null);

        return image;
    }
    /**
     * proof if input is a word
     */
    public static boolean isAlpha(String input) {
        return Pattern.matches("\\w*", input);
    }
 
	/**
	 * Returns a NumberFormat based on the parameters
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
