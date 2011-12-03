package de.hattrickorganizer.tools;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.Basics;
import de.hattrickorganizer.model.Finanzen;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.Liga;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.Stadium;
import de.hattrickorganizer.model.Team;
import de.hattrickorganizer.model.Verein;
import de.hattrickorganizer.model.XtraData;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;
public class HRFStringParser {
	/** TODO Missing Parameter Documentation */
    private static final String ENTITY = "Entity";

    /** TODO Missing Parameter Documentation */
    private static final String BASICS = "[basics]";

    /** TODO Missing Parameter Documentation */
    private static final String LEAGUE = "[league]";

    /** TODO Missing Parameter Documentation */
    private static final String CLUB = "[club]";

    /** TODO Missing Parameter Documentation */
    private static final String TEAM = "[team]";

    /** TODO Missing Parameter Documentation */
    private static final String LINEUP = "[lineup]";

    /** TODO Missing Parameter Documentation */
    private static final String ECONOMY = "[economy]";

    /** TODO Missing Parameter Documentation */
    private static final String ARENA = "[arena]";

    /** TODO Missing Parameter Documentation */
    private static final String PLAYER = "[player]";

    /** TODO Missing Parameter Documentation */
    private static final String XTRA = "[xtra]";

    /** TODO Missing Parameter Documentation */
    private static final String LASTLINEUP = "[lastlineup]";

    //~ Instance fields ----------------------------------------------------------------------------

    public final HOModel parse(String hrf) {
    	HOModel modelReturn = null;
        Timestamp hrfdate = null;
        BufferedReader hrfReader = null;
        if (hrf == null || hrf.length() == 0) {
            HOLogger.instance().log(getClass(),"HRF string is empty");
            return null;
        }
        try
        {
            final Vector<Properties> propertiesVector = new Vector<Properties>();
            Properties properties = null;

            //Load hrf string into a stream
            final ByteArrayInputStream bis = new ByteArrayInputStream(hrf.getBytes("UTF-8"));
            final InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
            hrfReader = new BufferedReader(isr);
            String lineString = "";
            Object entity = null; 
            String datestring = "";
            int indexEqualsSign = -1;
            // While there is still data to process
            while (hrfReader.ready()) {
                // Read a line
                lineString = hrfReader.readLine();

                // Ignore empty lines
                if ((lineString == null) || lineString.trim().equals(""))
                    continue;
                //New Properties
                if (lineString.startsWith("[")) {
                    //Old Property found, add to the Vector
                    if (properties != null) {
                        //HRF date
                        entity = properties.get(ENTITY);
                        if (entity != null && entity.toString().equalsIgnoreCase(BASICS)) 
                        {
                        	datestring = properties.getProperty("date");
                            hrfdate = getDateFromString(datestring);
                        }
                        propertiesVector.add(properties);
                    }

                    //Create a new Property
                    properties = new Properties();
                    // Player?
                    if (lineString.startsWith("[player")) {
                        properties.setProperty(ENTITY, PLAYER);
                        properties.setProperty("id", lineString.substring(7, lineString.lastIndexOf(']')));
                    }
                    else 
                        properties.setProperty(ENTITY, lineString);
                }
                // Fill actual Properties
                else 
                {
                    indexEqualsSign = lineString.indexOf('=');
                    if (indexEqualsSign > 0) {
                    	if(properties == null) 
                    		properties = new Properties();
                        properties.setProperty(lineString.substring(0, indexEqualsSign)
                                                    .toLowerCase(java.util.Locale.ENGLISH),
                                               lineString.substring(indexEqualsSign + 1));
                    }
                }
            }

            // Add the last property
            if (properties != null) 
                propertiesVector.add(properties);

            // Close the reader
            try 
            {
                hrfReader.close();
            } 
            catch (IOException ioe) {}
            // Create HOModel
            modelReturn = createHOModel(propertiesVector, hrfdate);
        } catch (Exception e) {
            HOLogger.instance().error(getClass(),"Error while parsing hrf");
            HOLogger.instance().error(getClass(),e);
        }
        return modelReturn;
    }
    private Timestamp getDateFromString(String date) {
    	SimpleDateFormat simpleFormat = new SimpleDateFormat(
         		"yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMANY);
        try {
            //Hattrick
            return new Timestamp(simpleFormat.parse(date).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                simpleFormat = new SimpleDateFormat("yyyy-MM-dd", 
                		java.util.Locale.GERMANY);

                return new Timestamp(simpleFormat.parse(date).getTime());
            } catch (Exception expc) {
                HOLogger.instance().log(getClass(),e);
                return new Timestamp(System.currentTimeMillis());
            }
        }
    }

    /**
     * Erzeugt aus dem Vector mit Properties ein HOModel
     *
     * @param propertiesVector TODO Missing Constructuor Parameter Documentation
     * @param hrfdate TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    private HOModel createHOModel(Vector<Properties> propertiesVector, Timestamp hrfdate)
      throws Exception
    {
        final HOModel hoModel = new HOModel();
        int trainerID = -1;

        for (int i = 0; i < propertiesVector.size(); i++) {
            final Properties properties = (Properties) propertiesVector.get(i);
            final Object entity = properties.get(ENTITY);

            if (entity != null) {
                //basics
                if (entity.toString().equalsIgnoreCase(BASICS)) {
                    hoModel.setBasics(new Basics(properties));
                }
                //league
                else if (entity.toString().equalsIgnoreCase(LEAGUE)) {
                    hoModel.setLiga(new Liga(properties));
                }
                //club
                else if (entity.toString().equalsIgnoreCase(CLUB)) {
                    hoModel.setVerein(new Verein(properties));
                }
                //team
                else if (entity.toString().equalsIgnoreCase(TEAM)) {
                    hoModel.setTeam(new Team(properties));
                }
                //lineup
                else if (entity.toString().equalsIgnoreCase(LINEUP)) {
                    hoModel.setAufstellung(new Lineup(properties));
                }
                //economy
                else if (entity.toString().equalsIgnoreCase(ECONOMY)) {
                    hoModel.setFinanzen(new Finanzen(properties));
                }
                //arena
                else if (entity.toString().equalsIgnoreCase(ARENA)) {
                    hoModel.setStadium(new Stadium(properties));
                }
                //player
                else if (entity.toString().equalsIgnoreCase(PLAYER)) {
                    hoModel.addSpieler(new Spieler(properties, hrfdate));
                }
                //Xtra
                else if (entity.toString().equalsIgnoreCase(XTRA)) {
                    hoModel.setXtraDaten(new XtraData(properties));
                    trainerID = Integer.parseInt(properties.getProperty("trainerid", "-1").toString());
                } else if (entity.toString().equalsIgnoreCase(LASTLINEUP)) {
                    hoModel.setLastAufstellung(new Lineup(properties));
                }
                //Unbekannt
                else {
                    //Ignorieren!
                    HOLogger.instance().log(getClass(),"Unbekannte Entity: " + entity.toString());
                }
            } else {
                HOLogger.instance().log(getClass(),"Fehlerhafte Datei / Keine Entity gefunden");
                return null;
            }
        }

        //Only keep trainerinformation for player equal to trainerID, rest is resetted . So later trainer could be found by searching for player having trainerType != -1
        if (trainerID > -1) {
            for (int i = 0;
                 (hoModel.getAllSpieler() != null) && (i < hoModel.getAllSpieler().size()); i++) {
                if (((Spieler) hoModel.getAllSpieler().elementAt(i)).isTrainer()
                    && ((((Spieler) hoModel.getAllSpieler().elementAt(i))).getSpielerID() != trainerID)) {
                    (((Spieler) hoModel.getAllSpieler().elementAt(i))).setTrainer(-1);
                    (((Spieler) hoModel.getAllSpieler().elementAt(i))).setTrainerTyp(-1);
                }
            }
        }

        return hoModel;
    }
}
