/*
 * Created on 31.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.hrfExplorer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;

import plugins.IHOMiniModel;
import plugins.IHelper;
import plugins.IJDBCAdapter;
import hoplugins.HrfExplorer;

/**
 * @author KickMuck
 */
public class HrfDetails
{
	private IHelper m_helper = null;
	private IHOMiniModel m_model = null;
	IJDBCAdapter m_dbAdapter = null;
	
	private String m_Str_Datum;
	private String m_Str_DatumVorher;
	private String m_Str_DatumDanach;
	private String m_inDB;
	private Date m_Datum;
	private Timestamp m_Timestamp;
	private String m_Wochentag;
	private int m_Kw;
	private String m_TrArt;
	private int m_TrArtInt;
	private int m_TrInt;
	private int m_Saison;
	private String m_Liga;
	private int m_anzCoTrainer;
	private int m_anzTwTrainer;
	private int m_Spieltag;
	private int m_Punkte;
	private int m_ToreFuer;
	private int m_ToreGegen;
	private int m_Platz;
	private int m_anzSpieler = 0;
	private String m_Stimmung;
	private String m_Selbstvertrauen;
	private int m_Fans;
	private String m_TeamName;
	private int m_TeamID;
	private ImageIcon m_bild;
	
	private ResultSet m_rs = null;
	private GregorianCalendar gc = null;
	private Color white = new Color(255,255,255);
	private Color rot = new Color (255,200,200);
	
	public HrfDetails(IHOMiniModel model)
	{
		m_model = model;
		m_helper = model.getHelper();
		m_dbAdapter = model.getAdapter();
	}
	
	/*****************
	 * Berechnet das vorhergehende und das folgende Datum in der DB und prüft, ob das File/der Eintrag in DB angelegt ist
	 */
	public void createDates()
	{
		m_rs = m_dbAdapter.executeQuery("SELECT MAX(DATUM) FROM HRF WHERE DATUM < '" + m_helper.parseDate(getStr_Datum()) + "'");
		try
		{
			while(m_rs.next())
			{
				m_rs.getTimestamp(1);
				
				if( m_rs.wasNull())
				{
					setStr_DatumVorher("---");
				}
				else
				{
					setStr_DatumVorher((m_rs.getTimestamp(1)).toString().substring(0,19));
				}
			}			
		}
		catch(SQLException sexc)
		{
			HrfExplorer.appendText("" + sexc);
		}
		m_rs = m_dbAdapter.executeQuery("SELECT MIN(DATUM) FROM HRF WHERE DATUM > '" + m_helper.parseDate(getStr_Datum()) + "'");
		try
		{
			while(m_rs.next())
			{
				m_rs.getTimestamp(1);
				
				if( m_rs.wasNull())
				{
					setStr_DatumDanach("---");
				}
				else
				{
					setStr_DatumDanach((m_rs.getTimestamp(1)).toString().substring(0,19));
				}
			}			
		}
		catch(SQLException sexc)
		{
			HrfExplorer.appendText("" + sexc);
		}
		m_rs = m_dbAdapter.executeQuery("SELECT count(*) FROM HRF WHERE DATUM = '" + m_helper.parseDate(getStr_Datum()) + "'");
		try
		{
			while(m_rs.next())
			{
				if(m_rs.getInt(1) == 0)
				{
					//setinDB("Nein");
					ImageIcon ii = new ImageIcon("hoplugins/hrfExplorer/pics/nein.gif");
					Image tmp = m_helper.makeColorTransparent(ii.getImage(),rot);
					setBild(new ImageIcon(tmp));
				}
				else
				{
					//setinDB("Ja");
					ImageIcon ii = new ImageIcon("hoplugins/hrfExplorer/pics/ja.gif");
					Image tmp = m_helper.makeColorTransparent(ii.getImage(),rot);
					setBild(new ImageIcon(tmp));
				}
			}
		}
		catch(SQLException sexc)
		{
			HrfExplorer.appendText("" + sexc);
		}
	}
	
	/*****************
	 * Berechnet diverse Datumswerte aus einem Timestamp-String
	 * @param tStamp
	 */
	public void calcDatum(String tStamp)
	{ 
		String tStamp_datum = tStamp;
		int jahr = Integer.parseInt(tStamp_datum.substring(0,4));
		int monat = Integer.parseInt(tStamp_datum.substring(5,7));
		int tag = Integer.parseInt(tStamp_datum.substring(8,10));
		int stunde = Integer.parseInt(tStamp_datum.substring(11,13));
		int minute = Integer.parseInt(tStamp_datum.substring(14,16));
		//int sekunde = Integer.parseInt(tStamp_datum.substring(17));
		gc = new GregorianCalendar();
		gc.set(Calendar.DAY_OF_MONTH, tag);
		gc.set(Calendar.MONTH, monat -1);
		gc.set(Calendar.YEAR, jahr);
		/*gc.set(Calendar.HOUR, stunde);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, sekunde);*/
		setDatum(gc.get(Calendar.DAY_OF_MONTH),gc.get(Calendar.MONTH),gc.get(Calendar.YEAR));
		setKw(gc.get(Calendar.WEEK_OF_YEAR));
		
		int value = gc.get(Calendar.DAY_OF_WEEK);
		String[] tage = HrfExplorer.getTage();
		switch(value)
		{
			case Calendar.MONDAY:
				setWochentag(tage[0]);
			break;
			case Calendar.TUESDAY:
				setWochentag(tage[1]);
			break;
			case Calendar.WEDNESDAY:
				setWochentag(tage[2]);
			break;
			case Calendar.THURSDAY:
				setWochentag(tage[3]);
			break;
			case Calendar.FRIDAY:
				setWochentag(tage[4]);
			break;
			case Calendar.SATURDAY:
				setWochentag(tage[5]);
			break;
			case Calendar.SUNDAY:
				setWochentag(tage[6]);
			break;
		}
	}
	
	/*****************
	 * liefert einen Vector mit den Daten bezüglich des Teams
	 */
	public Vector getTeamData()
	{
		Vector teamData = new Vector();
		teamData.add(getTeamName());
		teamData.add(new Integer(getTeamID()));
		teamData.add(getStimmung());
		teamData.add(getSelbstvertrauen());
		teamData.add(new Integer(getAnzSpieler()));
		teamData.add(new Integer(getAnzCoTrainer()));
		teamData.add(new Integer(getAnzTwTrainer()));
		teamData.add(getTrArt());
		teamData.add(new Integer(getTrInt()));
		teamData.add(new Integer(getFans()));
		return teamData;
	}
	
	/*****************
	 * liefert einen Vector mit den Daten bezüglich der Liga
	 */
	public Vector getLigaData()
	{
		Vector ligaData = new Vector();
		ligaData.add(new Integer(getSaison()));
		ligaData.add(getLiga());
		ligaData.add(new Integer(getSpieltag()));
		ligaData.add(new Integer(getPunkte()));
		ligaData.add(new Integer(getToreFuer()));
		ligaData.add(new Integer(getToreGegen()));
		ligaData.add(new Integer(getPlatz()));
		return ligaData;
	}
	/**
	 * @return Returns the m_anzCoTrainer.
	 */
	public int getAnzCoTrainer() {
		return m_anzCoTrainer;
	}
	/**
	 * @return Returns the m_anzSpieler.
	 */
	public int getAnzSpieler() {
		return m_anzSpieler;
	}
	/**
	 * @return Returns the m_anzTwTrainer.
	 */
	public int getAnzTwTrainer() {
		return m_anzTwTrainer;
	}
	/**
	 * @return Returns the m_bild.
	 */
	public ImageIcon getBild() {
		return m_bild;
	}
	/**
	 * @return Returns the m_Datum.
	 */
	public Date getDatum() {
		return m_Datum;
	}
	/**
	 * @return Returns the m_Fans.
	 */
	public int getFans() {
		return m_Fans;
	}
	/**
	 * @return Returns the m_inDB.
	 */
	public String getinDB() {
		return m_inDB;
	}
	/**
	 * @return Returns the m_Kw.
	 */
	public int getKw() {
		return m_Kw;
	}
	/**
	 * @return Returns the m_Liga.
	 */
	public String getLiga() {
		return m_Liga;
	}
	/**
	 * @return Returns the m_Platz.
	 */
	public int getPlatz() {
		return m_Platz;
	}
	/**
	 * @return Returns the m_Punkte.
	 */
	public int getPunkte() {
		return m_Punkte;
	}
	/**
	 * @return Returns the m_Saison.
	 */
	public int getSaison() {
		return m_Saison;
	}
	/**
	 * @return Returns the m_Selbstvertrauen.
	 */
	public String getSelbstvertrauen() {
		return m_Selbstvertrauen;
	}
	/**
	 * @return Returns the m_Spieltag.
	 */
	public int getSpieltag() {
		return m_Spieltag;
	}
	/**
	 * @return Returns the m_Stimmung.
	 */
	public String getStimmung() {
		return m_Stimmung;
	}
	/**
	 * @return Returns the m_Str_Datum.
	 */
	public String getStr_Datum() {
		return m_Str_Datum;
	}
	/**
	 * @return Returns the m_Str_DatumDanach.
	 */
	public String getStr_DatumDanach() {
		return m_Str_DatumDanach;
	}
	/**
	 * @return Returns the m_Str_DatumVorher.
	 */
	public String getStr_DatumVorher() {
		return m_Str_DatumVorher;
	}
	/**
	 * @return Returns the m_TeamID.
	 */
	public int getTeamID() {
		return m_TeamID;
	}
	/**
	 * @return Returns the m_TeamName.
	 */
	public String getTeamName() {
		return m_TeamName;
	}
	/**
	 * @return Returns the m_Timestamp.
	 */
	public Timestamp getTimestamp() {
		return m_Timestamp;
	}
	/**
	 * @return Returns the m_ToreFuer.
	 */
	public int getToreFuer() {
		return m_ToreFuer;
	}
	/**
	 * @return Returns the m_ToreGegen.
	 */
	public int getToreGegen() {
		return m_ToreGegen;
	}
	/**
	 * @return Returns the m_TrArt.
	 */
	public String getTrArt() {
		return m_TrArt;
	}
	/**
	 * @return Returns the m_TrArtInt.
	 */
	public int getTrArtInt() {
		return m_TrArtInt;
	}
	/**
	 * @return Returns the m_TrInt.
	 */
	public int getTrInt() {
		return m_TrInt;
	}
	/**
	 * @return Returns the m_Wochentag.
	 */
	public String getWochentag() {
		return m_Wochentag;
	}
	/**
	 * @param coTrainer The m_anzCoTrainer to set.
	 */
	public void setAnzCoTrainer(int coTrainer) {
		m_anzCoTrainer = coTrainer;
	}
	/**
	 * @param spieler The m_anzSpieler to set.
	 */
	public void setAnzSpieler(int spieler) {
		m_anzSpieler = spieler;
	}
	/**
	 * @param twTrainer The m_anzTwTrainer to set.
	 */
	public void setAnzTwTrainer(int twTrainer) {
		m_anzTwTrainer = twTrainer;
	}
	/**
	 * @param iIcon The ImageIcon to set.
	 */
	public void setBild(ImageIcon iIcon) {
		m_bild = iIcon;
	}
	/**
	 * @param datum The m_Datum to set.
	 */
	public void setDatum(int gc_tag, int gc_monat, int gc_jahr) {
		this.m_Datum = new Date(gc_jahr-1900,gc_monat,gc_tag);
	}
	/**
	 * @param fans The m_Fans to set.
	 */
	public void setFans(int fans) {
		m_Fans = fans;
	}
	/**
	 * @param m_indb The m_inDB to set.
	 */
	public void setinDB(String m_indb) {
		m_inDB = m_indb;
	}
	/**
	 * @param kw The m_Kw to set.
	 */
	public void setKw(int kw) {
		m_Kw = kw;
	}
	/**
	 * @param liga The m_Liga to set.
	 */
	public void setLiga(String liga) {
		m_Liga = liga;
	}
	/**
	 * @param platz The m_Platz to set.
	 */
	public void setPlatz(int platz) {
		m_Platz = platz;
	}
	/**
	 * @param punkte The m_Punkte to set.
	 */
	public void setPunkte(int punkte) {
		m_Punkte = punkte;
	}
	/**
	 * @param saison The m_Saison to set.
	 */
	public void setSaison(int saison) {
		m_Saison = saison;
	}
	/**
	 * @param selbstvertrauen The m_Selbstvertrauen to set.
	 */
	public void setSelbstvertrauen(int selbstvertrauen) {
		m_Selbstvertrauen = m_helper.getNameForConfidence(selbstvertrauen);
	}
	/**
	 * @param spieltag The m_Spieltag to set.
	 */
	public void setSpieltag(int spieltag) {
		m_Spieltag = spieltag;
	}
	/**
	 * @param stimmung The m_Stimmung to set.
	 */
	public void setStimmung(int stimmung) {
		m_Stimmung = m_helper.getNameForTeamspirit(stimmung);
	}
	/**
	 * @param str_Datum The m_Str_Datum to set.
	 */
	public void setStr_Datum(String str_Datum) {
		m_Str_Datum = str_Datum.substring(0,19);
	}
	/**
	 * @param str_DatumDanach The m_Str_DatumDanach to set.
	 */
	public void setStr_DatumDanach(String str_DatumDanach) {
		m_Str_DatumDanach = str_DatumDanach;
	}
	/**
	 * @param str_DatumVorher The m_Str_DatumVorher to set.
	 */
	public void setStr_DatumVorher(String str_DatumVorher) {
		m_Str_DatumVorher = str_DatumVorher;
	}
	/**
	 * @param teamID The m_TeamID to set.
	 */
	public void setTeamID(int teamID) {
		m_TeamID = teamID;
	}
	/**
	 * @param teamName The m_TeamName to set.
	 */
	public void setTeamName(String teamName) {
		m_TeamName = teamName;
	}
	/**
	 * @param timestamp The m_Timestamp to set.
	 */
	public void setTimestamp(Timestamp timestamp) {
		m_Timestamp = timestamp;
	}
	/**
	 * @param toreFuer The m_ToreFuer to set.
	 */
	public void setToreFuer(int toreFuer) {
		m_ToreFuer = toreFuer;
	}
	/**
	 * @param toreGegen The m_ToreGegen to set.
	 */
	public void setToreGegen(int toreGegen) {
		m_ToreGegen = toreGegen;
	}
	/**
	 * @param trArt The m_TrArt to set.
	 */
	public void setTrArt(int trArt) {
		m_TrArt = m_helper.getNameForTraining(trArt);
	}
	/**
	 * @param trArtInt The m_TrArtInt to set.
	 */
	public void setTrArtInt(int trArtInt) {
		m_TrArtInt = trArtInt;
	}
	/**
	 * @param trInt The m_TrInt to set.
	 */
	public void setTrInt(int trInt) {
		m_TrInt = trInt;
	}
	/**
	 * @param wochentag The m_Wochentag to set.
	 */
	public void setWochentag(String wochentag) {
		m_Wochentag = wochentag;
	}
	
}
