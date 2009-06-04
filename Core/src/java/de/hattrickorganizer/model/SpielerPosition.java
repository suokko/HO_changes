// %743607116:de.hattrickorganizer.model%
/*
 * SpielerPosition.java
 *
 * Created on 20. März 2003, 10:21
 */
package de.hattrickorganizer.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class SpielerPosition implements java.io.Serializable, Comparable, plugins.ISpielerPosition {

	private static final long serialVersionUID = -4822360078242315135L;

	//~ Static fields/initializers -----------------------------------------------------------------

    /** Array mit den Konstanten (CBItems) für die Positionen, Ohne Ausgewechselt */
    public static final CBItem[] POSITIONEN = {
                                                  new CBItem(SpielerPosition.getNameForPosition(UNBESTIMMT),
                                                             UNBESTIMMT),
                                                  new CBItem(SpielerPosition.getNameForPosition(TRAINER),
                                                             TRAINER),
                                                  new CBItem(SpielerPosition.getNameForPosition(TORWART),
                                                             TORWART),
                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER),
                                                             INNENVERTEIDIGER),
                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER_OFF),
                                                             INNENVERTEIDIGER_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER_AUS),
                                                             INNENVERTEIDIGER_AUS),
                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER),
                                                             AUSSENVERTEIDIGER),
                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_OFF),
                                                             AUSSENVERTEIDIGER_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_DEF),
                                                             AUSSENVERTEIDIGER_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_IN),
                                                             AUSSENVERTEIDIGER_IN),
                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD),
                                                             MITTELFELD),
                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_OFF),
                                                             MITTELFELD_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_DEF),
                                                             MITTELFELD_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_AUS),
                                                             MITTELFELD_AUS),
                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL),
                                                             FLUEGELSPIEL),
                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_OFF),
                                                             FLUEGELSPIEL_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_DEF),
                                                             FLUEGELSPIEL_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_IN),
                                                             FLUEGELSPIEL_IN),
                                                  new CBItem(SpielerPosition.getNameForPosition(STURM),
                                                             STURM),
                                                  new CBItem(SpielerPosition.getNameForPosition(STURM_DEF),
                                                             STURM_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(STURM_AUS),
                                                             STURM_AUS)
                                              };

    //Konstanten für TRAININGSEFFEKTE

    /** TODO Missing Parameter Documentation */
    public static final byte KEIN_TE = 0;

    /** TODO Missing Parameter Documentation */
    public static final byte SCHWACHER_TE = 1;

    /** TODO Missing Parameter Documentation */
    public static final byte VOLLER_TE = 2;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TaktikAnweisungen */
    private byte m_bTaktik = -1;

    /** PositionsAngabe */

    //protected byte  m_bPosition     =   -1;

    /** ID */
    private int m_iId = -1;

    /** welcher Spieler besetzt diese Position */
    private int m_iSpielerId = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of SpielerPosition
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     * @param spielerId TODO Missing Constructuor Parameter Documentation
     * @param taktik TODO Missing Constructuor Parameter Documentation
     */

    /*byte position, */
    public SpielerPosition(int id, int spielerId, byte taktik) {
        //m_bPosition     =   position;
        m_iId = id;
        m_iSpielerId = spielerId;
        m_bTaktik = taktik;
    }

    /**
     * Creates a new instance of SpielerPosition
     *
     * @param sp TODO Missing Constructuor Parameter Documentation
     */
    public SpielerPosition(SpielerPosition sp) {
        //m_bPosition     =   position;
        m_iId = sp.getId();
        m_iSpielerId = sp.getSpielerId();
        m_bTaktik = sp.getTaktik();
    }

    ////////////////////Load/Save/////////////////

    /**
     * Konstruktor lädt die SpielerPosition aus einem InputStream
     *
     * @param dis Der InputStream aus dem gelesen wird
     */
    public SpielerPosition(DataInputStream dis) {
        //DataInputStream         dis         =   null;
        //byte                    data[]      =   null;        
        try {
            //Einzulesenden Strom konvertieren
            //bais = new ByteArrayInputStream(data);
            //  dis  = new DataInputStream (bais);            
            //Daten auslesen
            m_iId = dis.readInt();
            m_iSpielerId = dis.readInt();
            m_bTaktik = dis.readByte();

            //Und wieder schliessen
            //dis.close ();
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt eine mögliche HT-Positionsid für eine HO-Positionsid zurück. Nur zum Laden des
     * Positionsimage zu gebrauchen!!!
     *
     * @param posId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getHTPosidForHOPosition4Image(byte posId) {
        switch (posId) {
            case TORWART:
                return keeper;

            case INNENVERTEIDIGER:
            case INNENVERTEIDIGER_AUS:
            case INNENVERTEIDIGER_OFF:
                return insideBack1;

            case AUSSENVERTEIDIGER:
            case AUSSENVERTEIDIGER_IN:
            case AUSSENVERTEIDIGER_OFF:
            case AUSSENVERTEIDIGER_DEF:
                return rightBack;

            case MITTELFELD:
            case MITTELFELD_OFF:
            case MITTELFELD_DEF:
            case MITTELFELD_AUS:
                return insideMid1;

            case FLUEGELSPIEL:
            case FLUEGELSPIEL_IN:
            case FLUEGELSPIEL_OFF:
            case FLUEGELSPIEL_DEF:
                return rightWinger;

            case STURM:
            case STURM_AUS:
            case STURM_DEF:
                return forward1;

            case AUSGEWECHSELT1:
            case AUSGEWECHSELT2:
            case AUSGEWECHSELT3:
                return ausgewechselt;

            default: {
                HOLogger.instance().log(SpielerPosition.class,"Unbestimmte Position: " + posId);
                return ausgewechselt;
            }
        }
    }

    /**
     * Gibt das Kürzel für den Namen zurück
     *
     * @param posId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getKurzNameForPosition(byte posId) {

        switch (posId) {
            case TORWART:
                return HOVerwaltung.instance().getLanguageString("TORW");

            case INNENVERTEIDIGER:
                return HOVerwaltung.instance().getLanguageString("IV");

            case INNENVERTEIDIGER_AUS:
                return HOVerwaltung.instance().getLanguageString("IVA");

            case INNENVERTEIDIGER_OFF:
                return HOVerwaltung.instance().getLanguageString("IVO");

            case AUSSENVERTEIDIGER:
                return HOVerwaltung.instance().getLanguageString("AV");

            case AUSSENVERTEIDIGER_IN:
                return HOVerwaltung.instance().getLanguageString("AVI");

            case AUSSENVERTEIDIGER_OFF:
                return HOVerwaltung.instance().getLanguageString("AVO");

            case AUSSENVERTEIDIGER_DEF:
                return HOVerwaltung.instance().getLanguageString("AVD");

            case MITTELFELD:
                return HOVerwaltung.instance().getLanguageString("MIT");

            case MITTELFELD_OFF:
                return HOVerwaltung.instance().getLanguageString("MITO");

            case MITTELFELD_DEF:
                return HOVerwaltung.instance().getLanguageString("MITD");

            case MITTELFELD_AUS:
                return HOVerwaltung.instance().getLanguageString("MITA");

            case FLUEGELSPIEL:
                return HOVerwaltung.instance().getLanguageString("FLG");

            case FLUEGELSPIEL_IN:
                return HOVerwaltung.instance().getLanguageString("FLGI");

            case FLUEGELSPIEL_OFF:
                return HOVerwaltung.instance().getLanguageString("FLGO");

            case FLUEGELSPIEL_DEF:
                return HOVerwaltung.instance().getLanguageString("FLGD");

            case STURM:
                return HOVerwaltung.instance().getLanguageString("STU");

            case STURM_AUS:
                return HOVerwaltung.instance().getLanguageString("STUA");
                
            case STURM_DEF:
                return HOVerwaltung.instance().getLanguageString("STUD");

            case AUSGEWECHSELT1:
            case AUSGEWECHSELT2:
            case AUSGEWECHSELT3:
                return HOVerwaltung.instance().getLanguageString("Ausgewechselt");

            //HOLogger.instance().log(getClass(), "Unbestimmte Position: " + posId );
            default:
                return HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt zu einer Positionsid den Namen zurück
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForID(int id) {
        switch (id) {
            case keeper:
                return "keeper";

            case rightBack:
                return "rightBack";

            case insideBack1:
                return "insideBack1";

            case insideBack2:
                return "insideBack2";

            case leftBack:
                return "leftBack";

            case rightWinger:
                return "rightWinger";

            case insideMid1:
                return "insideMid1";

            case insideMid2:
                return "insideMid2";

            case leftWinger:
                return "leftWinger";

            case forward1:
                return "forward1";

            case forward2:
                return "forward2";

            case substBack:
                return "substBack";

            case substInsideMid:
                return "substInsideMid";

            case substWinger:
                return "substWinger";

            case substKeeper:
                return "substKeeper";

            case substForward:
                return "substForward";
        }

        return "";
    }

    /**
     * Gibt zu einer Positionsid den Namen zurück
     *
     * @param posId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForPosition(byte posId) {

        switch (posId) {
            case TORWART:
                return HOVerwaltung.instance().getLanguageString("Torwart");

            case INNENVERTEIDIGER:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger");

            case INNENVERTEIDIGER_AUS:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger_Aus");

            case INNENVERTEIDIGER_OFF:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger_Off");

            case AUSSENVERTEIDIGER:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger");

            case AUSSENVERTEIDIGER_IN:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_In");

            case AUSSENVERTEIDIGER_OFF:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Off");

            case AUSSENVERTEIDIGER_DEF:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Def");

            case MITTELFELD:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld");

            case MITTELFELD_OFF:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Off");

            case MITTELFELD_DEF:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Def");

            case MITTELFELD_AUS:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Aus");

            case FLUEGELSPIEL:
                return HOVerwaltung.instance().getLanguageString("Fluegel");

            case FLUEGELSPIEL_IN:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_In");

            case FLUEGELSPIEL_OFF:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_Off");

            case FLUEGELSPIEL_DEF:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_Def");

            case STURM:
                return HOVerwaltung.instance().getLanguageString("Sturm");

            case STURM_DEF:
                return HOVerwaltung.instance().getLanguageString("Sturm_Def");

            case STURM_AUS:
                return HOVerwaltung.instance().getLanguageString("Sturm_Aus");

            case AUSGEWECHSELT1:
            case AUSGEWECHSELT2:
            case AUSGEWECHSELT3:
                return HOVerwaltung.instance().getLanguageString("Ausgewechselt");

            case TRAINER:
                return HOVerwaltung.instance().getLanguageString("Trainer");

            //HOLogger.instance().log(getClass(), "Unbestimmte Position: " + posId );
            default:
                return HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getPosition() {
        return SpielerPosition.getPosition(m_iId, m_bTaktik);
    }

    /**
     * Getter for property m_bPosition.
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     * @param taktik TODO Missing Constructuor Parameter Documentation
     *
     * @return Value of property m_bPosition.
     */
    public static byte getPosition(int id, byte taktik) {
        switch (id) {
            case keeper:
                return TORWART;

            case rightBack:
            case leftBack: {
                if (taktik == ZUR_MITTE) {
                    return AUSSENVERTEIDIGER_IN;
                } else if (taktik == ZUS_MITTELFELD) {
                    return MITTELFELD;
                } else if (taktik == ZUS_STUERMER) {
                    return STURM;
                } else if (taktik == ZUS_INNENV) {
                    return INNENVERTEIDIGER;
                } else if (taktik == OFFENSIV) {
                    return AUSSENVERTEIDIGER_OFF;
                } else if (taktik == DEFENSIV) {
                    return AUSSENVERTEIDIGER_DEF;
                } else {
                    return AUSSENVERTEIDIGER;
                }
            }

            case insideBack1:
            case insideBack2: {
                if (taktik == NACH_AUSSEN) {
                    return INNENVERTEIDIGER_AUS;
                } else if (taktik == ZUS_MITTELFELD) {
                    return MITTELFELD;
                } else if (taktik == ZUS_STUERMER) {
                    return STURM;
                } else if (taktik == OFFENSIV) {
                    return INNENVERTEIDIGER_OFF;
                } else {
                    return INNENVERTEIDIGER;
                }
            }

            case rightWinger:
            case leftWinger: {
                if (taktik == ZUR_MITTE) {
                    return FLUEGELSPIEL_IN;
                } else if (taktik == ZUS_INNENV) {
                    return INNENVERTEIDIGER;
                } else if (taktik == ZUS_STUERMER) {
                    return STURM;
                } else if (taktik == ZUS_MITTELFELD) {
                    return MITTELFELD;
                } else if (taktik == OFFENSIV) {
                    return FLUEGELSPIEL_OFF;
                } else if (taktik == DEFENSIV) {
                    return FLUEGELSPIEL_DEF;
                } else {
                    return FLUEGELSPIEL;
                }
            }

            case insideMid1:
            case insideMid2: {
                if (taktik == NACH_AUSSEN) {
                    return MITTELFELD_AUS;
                } else if (taktik == ZUS_INNENV) {
                    return INNENVERTEIDIGER;
                } else if (taktik == ZUS_STUERMER) {
                    return STURM;
                } else if (taktik == OFFENSIV) {
                    return MITTELFELD_OFF;
                } else if (taktik == DEFENSIV) {
                    return MITTELFELD_DEF;
                } else {
                    return MITTELFELD;
                }
            }

            case forward1:
            case forward2: {
                if (taktik == ZUS_INNENV) {
                    return INNENVERTEIDIGER;
                } else if (taktik == ZUS_MITTELFELD) {
                    return MITTELFELD;
                } else if (taktik == DEFENSIV) {
                    return STURM_DEF;
                } else if (taktik == NACH_AUSSEN) {
                    return STURM_AUS;
                } else {
                    return STURM;
                }
            }

            case substBack:
                return INNENVERTEIDIGER;

            case substInsideMid:
                return MITTELFELD;

            case substWinger:
                return FLUEGELSPIEL;

            case substKeeper:
                return TORWART;

            case substForward:
                return STURM;
        }

        return UNBESTIMMT;
    }

    /**
     * Setter for property m_iId.
     *
     * @param m_iId New value of property m_iId.
     */
    public final void setId(int m_iId) {
        this.m_iId = m_iId;
    }

    /**
     * Setter for property m_bPosition.
     *
     * @return TODO Missing Return Method Documentation
     */

    /*    public void setPosition (byte m_bPosition)
       {
           this.m_bPosition = m_bPosition;
       }
     */

    /**
     * Getter for property m_iId.
     *
     * @return Value of property m_iId.
     */
    public final int getId() {
        return m_iId;
    }

    /**
     * liefert eine ID nach der Sortiert werden kann ( z.B. Spierlübersichtstabelle
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getSortId() {
        int id = this.getPosition();

		if (id == ISpielerPosition.STURM_AUS)  {
			id = 18;
		}

        if (this.getId() >= ISpielerPosition.beginnReservere)  {
            id += 20;
        }

        return id;
    }

    /**
     * liefert eine ID nach der Sortiert werden kann ( z.B. Spierlübersichtstabelle
     *
     * @param position TODO Missing Constructuor Parameter Documentation
     * @param reserve TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getSortId(byte position, boolean reserve) {
        int id = position;

        if (reserve) {
            id += 20;
        }

        return id;
    }

    /**
     * Setter for property m_iSpielerId.
     *
     * @param m_iSpielerId New value of property m_iSpielerId.
     */
    public final void setSpielerId(int m_iSpielerId) {
        this.m_iSpielerId = m_iSpielerId;
    }

    /**
     * Getter for property m_iSpielerId.
     *
     * @return Value of property m_iSpielerId.
     */
    public final int getSpielerId() {
        return m_iSpielerId;
    }

    /**
     * Setter for property m_bTaktik.
     *
     * @param m_bTaktik New value of property m_bTaktik.
     */
    public final void setTaktik(byte m_bTaktik) {
        this.m_bTaktik = m_bTaktik;
    }

    /**
     * Getter for property m_bTaktik.
     *
     * @return Value of property m_bTaktik.
     */
    public final byte getTaktik() {
        return m_bTaktik;
    }

    /**
     * liefert den Trainingseffekt dieser Position für das eingestellte Training
     *
     * @param obj TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    /*    public byte getTrainingsEffekt()
       {
           if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_ALLGEMEIN )
           {
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   return SpielerPosition.VOLLER_TE ;
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt ()== model.Team.TA_KONDITION )
           {
               return SpielerPosition.VOLLER_TE ;
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_STANDARD )
           {
               return SpielerPosition.VOLLER_TE ;
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt ()== model.Team.TA_VERTEIDIGUNG )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( ( pos == INNENVERTEIDIGER ) || ( pos == INNENVERTEIDIGER_OFF )
                   || ( pos == INNENVERTEIDIGER_AUS ) || ( pos == AUSSENVERTEIDIGER )
                   || ( pos == AUSSENVERTEIDIGER_OFF )  || ( pos == AUSSENVERTEIDIGER_DEF )
                   || ( pos == AUSSENVERTEIDIGER_IN ) )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt ()== model.Team.TA_CHANCEN )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( ( pos == STURM ) || ( pos == STURM_DEF )  )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt ()== model.Team.TA_FLANKEN )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( ( pos == FLUEGELSPIEL_IN )
                   || ( pos == FLUEGELSPIEL_OFF )  || ( pos == FLUEGELSPIEL_DEF )
                   || ( pos == FLUEGELSPIEL_IN )  )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
                   else if ( ( pos == AUSSENVERTEIDIGER )
                   || ( pos == AUSSENVERTEIDIGER_OFF )  || ( pos == AUSSENVERTEIDIGER_DEF )
                   || ( pos == AUSSENVERTEIDIGER_IN ) )
                   {
                       return SpielerPosition.SCHWACHER_TE;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_PASSSPIEL )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( ( pos == FLUEGELSPIEL_IN )
                   || ( pos == FLUEGELSPIEL_OFF )  || ( pos == FLUEGELSPIEL_DEF )
                   || ( pos == FLUEGELSPIEL_IN )
                   || ( pos == STURM ) || ( pos == STURM_DEF )
                   || ( pos == MITTELFELD )
                   || ( pos == MITTELFELD_OFF )  || ( pos == MITTELFELD_DEF )
                   || ( pos == MITTELFELD_AUS ) )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_SCHUSSTRAINING )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( pos != TORWART )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_SPIELAUFBAU)
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( ( pos == MITTELFELD )
                   || ( pos == MITTELFELD_OFF )  || ( pos == MITTELFELD_DEF )
                   || ( pos == MITTELFELD_AUS ) )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
                   else if ( ( pos == FLUEGELSPIEL_IN )
                   || ( pos == FLUEGELSPIEL_OFF )  || ( pos == FLUEGELSPIEL_DEF )
                   || ( pos == FLUEGELSPIEL_IN ) )
                   {
                       return SpielerPosition.SCHWACHER_TE ;
                   }
               }
           }
           else if ( HOVerwaltung.instance ().getModel ().getTeam ().getTrainingsArtAsInt () == model.Team.TA_TORWART )
           {
               byte pos    = getPosition();
    
               if ( m_iId < SpielerPosition.beginnReservere )
               {
                   if ( pos == TORWART )
                   {
                       return SpielerPosition.VOLLER_TE ;
                   }
               }
           }
    
           return SpielerPosition.KEIN_TE;
       }
     */
    public final int compareTo(Object obj) {
        if (obj instanceof SpielerPosition) {
            final SpielerPosition position = (SpielerPosition) obj;

            //Beide aufgestellt ?
            if ((this.getId() < ISpielerPosition.beginnReservere)
                && (position.getId() < ISpielerPosition.beginnReservere)) {
                if (this.getPosition() < position.getPosition()) {
                    return -1;
                } else if (this.getPosition() == position.getPosition()) {
                    return 0;
                } else {
                    return 1;
                }
            }
            //this aufgestellt ?
            else if ((this.getId() < ISpielerPosition.beginnReservere)
                     && (position.getId() >= ISpielerPosition.beginnReservere)) {
                return -1;
            }
            //position aufgestellt
            else if ((this.getId() >= ISpielerPosition.beginnReservere)
                     && (position.getId() < ISpielerPosition.beginnReservere)) {
                return 1;
            }
            //keiner aufgestellt
            else {
                if (this.getPosition() < position.getPosition()) {
                    return -1;
                } else if (this.getPosition() == position.getPosition()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }

        return 0;
    }

    /*
       saved den Serverspieler
       @param baos Der Outputstream in den gesaved werden soll
       @return Byte Array der Daten die in den Output geschireben wurden
     */
    public final void save(DataOutputStream das) {
        //ByteArrayOutputStream   baos    =  null;
        //DataOutputStream        das     =   null;
        //Byte Array
        //byte[]                  data    =   null;
        try {
            //Instanzen erzeugen
            //baos = new ByteArrayOutputStream();
            //        das = new DataOutputStream(baos);
            //Daten schreiben in Strom
            das.writeInt(m_iId);
            das.writeInt(m_iSpielerId);
            das.writeByte(m_bTaktik);

            /*//Strom konvertieren in Byte
               data = baos.toByteArray();
               //Hilfsstrom schließen
               das.close ();
            
               return data;*/
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }

        //   return data;
    }
}
