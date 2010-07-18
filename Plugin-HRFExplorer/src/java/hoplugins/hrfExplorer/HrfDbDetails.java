/*
 * Created on 10.05.2005
 */
package hoplugins.hrfExplorer;

import plugins.IHOMiniModel;

import hoplugins.HrfExplorer;

import java.util.Vector;
import java.sql.*;
import java.sql.SQLException;

/**
 * @author KickMuck
 */

public class HrfDbDetails extends HrfDetails
{
	String m_name;
	int m_hrf_ID;
	private ResultSet m_result = null;
	
	public HrfDbDetails(int id, IHOMiniModel model)
	{
		super(model);
		
		m_result = m_dbAdapter.executeQuery("SELECT NAME,DATUM,LIGANAME,PUNKTE,TOREFUER,TOREGEGEN,PLATZ,TEAMID,TEAMNAME,SPIELTAG,SAISON,TRAININGSINTENSITAET,TRAININGSART,ISTIMMUNG,ISELBSTVERTRAUEN,COTRAINER,TWTRAINER,FANS,HRF_ID,(SELECT COUNT(*) FROM SPIELER WHERE HRF_ID = '" + id + "') AS \"ANZAHL\" FROM HRF a, LIGA b, BASICS c, TEAM d, VEREIN e WHERE a.HRF_ID = '" + id + "' AND b.HRF_ID=a.HRF_ID AND c.HRF_ID=a.HRF_ID AND d.HRF_ID=a.HRF_ID AND e.HRF_ID=a.HRF_ID");
		try
		{
			while(m_result.next())
			{
				m_result.getObject(1);
				
				if( m_result.wasNull())
				{
					//HrfExplorer.appendText("Query war NULL");
				}
				else
				{
					HrfExplorer.appendText("Query war nicht NULL");
					setName("---");
					setStr_Datum(m_result.getObject("DATUM").toString().substring(0,19));
					createDates();
					setLiga(m_result.getString("LIGANAME"));
					setPunkte(m_result.getInt("PUNKTE"));
					setToreFuer(m_result.getInt("TOREFUER"));
					setToreGegen(m_result.getInt("TOREGEGEN"));
					setPlatz(m_result.getInt("PLATZ"));
					setTeamID(m_result.getInt("TEAMID"));
					setTeamName(m_result.getString("TEAMNAME"));
					setSpieltag(m_result.getInt("SPIELTAG"));
					setSaison(m_result.getInt("SAISON"));
					setTrInt(m_result.getInt("TRAININGSINTENSITAET"));
					setTrArtInt(m_result.getInt("TRAININGSART"));
					setStimmung(m_result.getInt("ISTIMMUNG"));
					setSelbstvertrauen(m_result.getInt("ISELBSTVERTRAUEN"));
					setAnzCoTrainer(m_result.getInt("COTRAINER"));
					setAnzTwTrainer(m_result.getInt("TWTRAINER"));
					setFans(m_result.getInt("FANS"));
					setHrf_ID(m_result.getInt("HRF_ID"));
					setTrArt(getTrArtInt());
					setAnzSpieler(m_result.getInt("ANZAHL"));
				}
			}
		}
		catch(SQLException sexc)
		{
			
		}
		calcDatum(getStr_Datum());
	}
	
	/**
	 * @return Returns all needed Values as Vector
	 */
	public Vector getDatenVector()
	{
		Vector daten = new Vector();
		daten.add(new Boolean(false));
		daten.add(getName());
		daten.add(getStr_Datum());
		daten.add(getWochentag());
		daten.add(new Integer(getKw()));
		daten.add(new Integer(getSaison()));
		daten.add(getLiga());
		daten.add(getTrArt());
		daten.add(new Integer(getTrInt()));
		daten.add(getBild());
		daten.add(new Integer(getHrf_ID()));
		
		return daten;
	}
	/**
	 * @return Returns the m_hrf_ID.
	 */
	public int getHrf_ID() {
		return m_hrf_ID;
	}
	/**
	 * @return Returns the m_name.
	 */
	public String getName() {
		return m_name;
	}
	/**
	 * @param m_hrf_id The m_hrf_ID to set.
	 */
	public void setHrf_ID(int m_hrf_id) {
		m_hrf_ID = m_hrf_id;
	}
	/**
	 * @param m_name The m_name to set.
	 */
	public void setName(String m_name) {
		this.m_name = m_name;
	}
}
