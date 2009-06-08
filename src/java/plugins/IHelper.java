// %2836861477:plugins%
/*
 * IHelper.java
 *
 * Created on 29. März 2004, 08:08
 */
package plugins;

import java.awt.Color;
import java.awt.Component;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;


/**
 * Interface to several Helper funcs, that make life/programming much easier
 *
 * @author thomas.werth
 */
public interface IHelper {
    //~ Methods ------------------------------------------------------------------------------------

	/**
	 * Returns a match helper instance
	 * @return	an instance of the match helper class 
	 */
	public IMatchHelper getMatchHelper();
	
    /**
     * Returns a (Text)Color for a Highlight
     *
     * @param typ Use constant values from IMatchHighlight
     * @param subtyp Use constant values from IMatchHighlight
     *
     * @return TODO Missing Return Method Documentation
     */
    public Color getColor4SpielHighlight(int typ, int subtyp);

    /**
     * TODO Missing Method Documentation
     *
     * @param string TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws ParseException TODO Missing Method Exception Documentation
     */
    public Date getHattrickDate(String string) throws ParseException;

    /**
     * Returns an Image represent the Position of a player
     *
     * @param posid PositionID from Hattrick, 1-16 are valid values, all others will result in an
     *        empty Image
     * @param taktik TakticID, is unused but for additional Players
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImage4Position(int posid, byte taktik);

    /**
     * Returns the Flag-Image for a player ( ISpieler.getNationalitaet() )
     *
     * @param country TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4Country(int country);

    /**
     * Returns an Image represent the special ablility of a player
     *
     * @param wert the hattrickvalue of the ability
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4Spezialitaet(int wert);

    /**
     * Returns an Image represent the Matchhighlight
     *
     * @param typ Use constant values from IMatchHighlight
     * @param subtyp Use constant values from IMatchHighlight
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4SpielHighlight(int typ, int subtyp);

    /**
     * Returns an Image für the Matchtyp
     *
     * @param spieltyp hattrickvalue for that Matchtyp Use static from IMatchConst
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4Spieltyp(int spieltyp);

    /**
     * Returns an ArrowImage, green up für pos values, red down für neg values and a - for 0 values
     *
     * @param wert TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4Veraenderung(int wert);

    /**
     * Returns an Image for the weather
     *
     * @param wert 0 for Rain to 3 for Sun
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4Wetter(int wert);

    /**
     * Returns an Image for the Effect of the weather for a player
     *
     * @param wert &lt;0 Bad Weathereffect, &gt;0 good Weathereffect, =0 no Effect (return null)
     *
     * @return TODO Missing Return Method Documentation
     */
    public ImageIcon getImageIcon4WetterEffekt(int wert);

    //--Languageinformations

    /**
     * ID of the actual used Language
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLanguageID();

    /**
     * Name of the actual used Language. Use this methode to find your own languagefiles.
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getLanguageName();

    /**
     * TODO Missing Method Documentation
     *
     * @param skillupDate TODO Missing Method Parameter Documentation
     * @param refTrainingDate TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Calendar getLastTrainingDate(Date skillupDate, Date refTrainingDate);

    //----Names-----------------------------------------------------------------

    /**
     * fiery, temperamental, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForAggressivness(int value);

    /**
     * like the methode above, but if isMatch is set the values goes from 1 to 80 instead of 1 to
     * 20 and the numbers shows sublevels. Use this to display the skills of a lineup (right
     * defence, left attack, ...<br>
     * isMatch = false : 1 = disastrous (1) isMatch = true  : 1 = disastrous (very low) (1.125)
     *
     * @param value the value of the skill
     * @param showNumber true, adds the value to the String in () :  passable (6), Defaultvalue is
     *        IHOMiniModel.getUserSettings().zahlenFuerSkill
     * @param isMatch use values from 1-80 for lineupskills instead of the 1-20 for playerskills
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForBewertung(int value, boolean showNumber, boolean isMatch);

    /**
     * righteous, dishonest, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForCharacter(int value);

    /**
     * wonderful, decent, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForConfidence(int value);

    /**
     * popular, controversial, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForGentleness(int value);

    /**
     * leaguematch, ...
     *
     * @param typ hattrickvalue for that Matchtyp Use static from IMatchConst
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForMatchTyp(int typ);

    /**
     * Positionnames use Const from ISpielerPosition
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForPosition(byte value);

    /**
     * inadequate, divine, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     * @param showNumber true, adds the value to the String in () :  passable (6)
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForSkill(int value, boolean showNumber);

    /**
     * Quick, Powerful, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForSpeciality(int value);

    /**
     * pressing, .. Use static from IMatchConst
     *
     * @param typ TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForTaktik(int typ);

    /**
     * pic, normal, mots Use static from IMatchConst
     *
     * @param typ TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForTeamorder(int typ);

    /**
     * Like the cold War, delirious, ...
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForTeamspirit(int value);

    /**
     * Name of Training
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNameForTraining(int value);

    /**
     * Get a list of all started Plugins ( vector with IPlugins ) This method should not be use by
     * any plugin but the PluginUpdate-Plugin
     *
     * @return TODO Missing Return Method Documentation
     *
     * @deprecated
     */
    @Deprecated
	public Vector<IPlugin> getPlugins();

    /**
     * Utility Method that returns the field position from the HO Position Code (hoposcode) It is
     * impossible to make difference between left and right so always the left position is
     * returned
     *
     * @param hoposcode
     *
     * @return the field position
     */
    public int getPosition(int hoposcode);

    /**
     * Returns a short Text for a Highlight
     *
     * @param typ Use constant values from IMatchHighlight
     * @param subtyp Use constant values from IMatchHighlight
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getTooltiptext4SpielHighlight(int typ, int subtyp);

    /**
     * checks if given matchID is a match from UserTeam
     *
     * @param matchID matchID Id of match to check
     *
     * @return true is usermatch else false
     */
    public boolean isUserMatch(String matchID);

    /**
     * Add a player to HO! temporary
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public void addTempSpieler(plugins.ISpieler spieler);

    /**
     * :)
     *
     * @param timewerte TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String[] convertTimeMillisToFormatString(double[] timewerte);

    /**
     * Copy the values for a arry to an vector
     *
     * @param src TODO Missing Constructuor Parameter Documentation
     * @param dest TODO Missing Constructuor Parameter Documentation
     */
    public void copyArray2Vector(Object[] src, Vector<Object> dest);

    /**
     * Copy the values for a vector to an array
     *
     * @param src TODO Missing Constructuor Parameter Documentation
     * @param dest TODO Missing Constructuor Parameter Documentation
     */
    public void copyVector2Array(Vector<Object> src, Object[] dest);

    /**
     * Puts the vectorelements in a ComboBoxModel
     *
     * @param vector TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public DefaultComboBoxModel createListModel(Vector<Object> vector);

    //--------------------------------------------------------------------------

    /**
     * Parse a text ( from HT-playerpage ) and create an ISpieler from it. If the text cannot be
     * parsed it throws an exception
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public plugins.ISpieler createSpielerFromText(String text) throws Exception;

    /**
     * Decode the string from the database Use this methode for all strings you receive directly
     * from the database # are replaced by '
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String decodeStringFromDatabase(String text);

    /**
     * undo replacing, after reading from DB
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String deleteEscapeSequences(String text);

    //-- Matchdownload

    /**
     * downloads all match related data and stores it in Database
     *
     * @param matchID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean downloadMatchData(int matchID);

    /**
     * Encode the string to put it in the database Use this methode on all strings you want to
     * store in the database via sql ' and " are replaced by #
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String encodeString4Database(String text);

    /**
     * checkes wheater matchdata is already stored in db or not
     *
     * @param matchID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean existsMatchInDB(int matchID);

    /**
     * Like generateStringArray, but returns an int[] and has a fix seperator ','
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int[] generateIntArray(String text);

    /**
     * Generate a Stringarray by seperate the string werte by the seperator trenner
     *
     * @param werte TODO Missing Constructuor Parameter Documentation
     * @param trenner TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String[] generateStringArray(String werte, char trenner);

    //--------------------------------------------------------------------------

    /**
     * Download the actual HRF
     */
    public void importHRF();

    /**
     * DB String Parser ' and other tokens are replaced to be db conform, before storing in DB
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String insertEscapeSequences(String text);

    /**
     * Load an image and cache it. The filepath datei should be like
     * "hoplugin/myplugin/image/imagename.png" Make sure, that the image is can be loaded by java!
     *
     * @param datei TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.awt.image.BufferedImage loadImage(String datei);

    /**
     * Returns an Image with transparent pixels, where color-pixels are found in im
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param color TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.awt.Image makeColorTransparent(java.awt.Image im, java.awt.Color color);

    /**
     * Returns an Image with transparent pixels, where the pixels has colorvalues between the
     * specified values
     *
     * @param im TODO Missing Constructuor Parameter Documentation
     * @param minr TODO Missing Constructuor Parameter Documentation
     * @param ming TODO Missing Constructuor Parameter Documentation
     * @param minb TODO Missing Constructuor Parameter Documentation
     * @param maxr TODO Missing Constructuor Parameter Documentation
     * @param maxg TODO Missing Constructuor Parameter Documentation
     * @param maxb TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.awt.Image makeColorTransparent(java.awt.Image im, int minr, int ming, int minb,
                                               int maxr, int maxg, int maxb);

    /**
     * opens given url in default System Browser
     *
     * @param url TODO Missing Constructuor Parameter Documentation
     */
    public void openUrlInUserBRowser(String url);

    /**
     * Parse a String an return a Timestamp for it
     *
     * @param date TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.sql.Timestamp parseDate(String date);

    /**
     * Round a double value
     *
     * @param wert value to round
     * @param nachkommastellen number of fraction digits
     *
     * @return rounded value
     */
    public double round(double wert, int nachkommastellen);

    /**
     * Round a float value
     *
     * @param wert value to round
     * @param nachkommastellen number of fraction digits
     *
     * @return rounded value
     */
    public float round(float wert, int nachkommastellen);

    /**
     * Shows an MessageDialog JOptionPane only, if there is not still an other MessageDialog shown
     * (can be a Deadlock!)
     *
     * @param parent TODO Missing Constructuor Parameter Documentation
     * @param message TODO Missing Constructuor Parameter Documentation
     * @param titel TODO Missing Constructuor Parameter Documentation
     * @param typ TODO Missing Constructuor Parameter Documentation
     */
    public void showMessage(Component parent, String message, String titel, int typ);

    /**
     * Returns an IMP3Player. Set the mp3-filename and path with setMP3File( String )
     * and start the playback with run(). You can end the playback with close()
     */
    public IMP3Player getMP3Player();
    
    //--------------------------------------------------------------------------

    /**
     * Sort an multiintarray
     *
     * @param toSort TODO Missing Constructuor Parameter Documentation
     * @param spaltenindex TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int[][] sortintArray(int[][] toSort, int spaltenindex);
    
    /**
     * Returns true if a development version
     * @return version type
     */
    public boolean isDevVersion();

    // ================== HT-Calendar helper methods ==================
    
    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createEconomyCalendar();

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param model HO data model
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createEconomyCalendar(IHOMiniModel model);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createEconomyCalendar(Date date);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param timestamp Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createEconomyCalendar(Timestamp timestamp);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param model HO data model
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createEconomyCalendar(IHOMiniModel model, Date date);

    /**
     * Creates a HTCalendar to calculate global (Swedish) values.
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createGlobalCalendar();

    /**
     * Creates a HTCalendar to calculate global (Swedish) values  and presets it with he specified
     * date a date.
     *
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createGlobalCalendar(Date date);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createTrainingCalendar();

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param model HO data model
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createTrainingCalendar(IHOMiniModel model);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createTrainingCalendar(Date date);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param timestamp Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createTrainingCalendar(Timestamp timestamp);

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param model HO data model
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public IHTCalendar createTrainingCalendar(IHOMiniModel model, Date date);

	/**
	 * Get HT-Season of a given date (using the economy calendar)
	 * 
	 * @param date					the date to convert
	 * @return	HT-Season
	 */
	public int getHTSeason (Date date);
	
	/**
	 * Get HT-Season of a given date
	 * 
	 * @param date					the date to convert
	 * @param useTrainingCalendar	use training calendar if true, else use economy calendar
	 * @return	HT-Season
	 */
	public int getHTSeason (Date date, boolean useTrainingCalendar);
	
	/**
	 * Get HT-Week of a given date (using the economy calendar)
	 * 
	 * @param date					the date to convert
	 * @return	HT-Week
	 */
	public int getHTWeek (Date date);
	
	/**
	 * Get HT-Week of a given date
	 * 
	 * @param date					the date to convert
	 * @param useTrainingCalendar	use training calendar if true, else use economy calendar
	 * @return	HT-Week
	 */
	public int getHTWeek (Date date, boolean useTrainingCalendar);

	/**
	 * Get HT-Season of a given date (using the economy calendar)
	 * 
	 * @param timestamp				the date to convert
	 * @return	HT-Season
	 */
	public int getHTSeason (Timestamp timestamp);
	
	/**
	 * Get HT-Season of a given date
	 * 
	 * @param timestamp				the date to convert
	 * @param useTrainingCalendar	use training calendar if true, else use economy calendar
	 * @return	HT-Season
	 */
	public int getHTSeason (Timestamp timestamp, boolean useTrainingCalendar);
	
	/**
	 * Get HT-Week of a given date (using the economy calendar)
	 * 
	 * @param timestamp				the date to convert
	 * @return	HT-Week
	 */
	public int getHTWeek (Timestamp timestamp);
	
	/**
	 * Get HT-Week of a given date
	 * 
	 * @param timestamp				the date to convert
	 * @param useTrainingCalendar	use training calendar if true, else use economy calendar
	 * @return	HT-Week
	 */
	public int getHTWeek (Timestamp timestamp, boolean useTrainingCalendar);

}
