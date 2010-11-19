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
public class SpielerPosition implements java.io.Serializable, Comparable<ISpielerPosition>, plugins.ISpielerPosition {

	private static final long serialVersionUID = -4822360078242315135L;

	//~ Static fields/initializers -----------------------------------------------------------------

    /** Array mit den Konstanten (CBItems) für die Positionen, Ohne Ausgewechselt */
    public static final CBItem[] POSITIONEN = {
                                                  new CBItem(SpielerPosition.getNameForPosition(UNKNOWN),
                                                             UNKNOWN),
                                                  new CBItem(SpielerPosition.getNameForPosition(COACH),
                                                             COACH),
                                                  new CBItem(SpielerPosition.getNameForPosition(KEEPER),
                                                             KEEPER),
                                                  new CBItem(SpielerPosition.getNameForPosition(CENTRAL_DEFENDER),
                                                             CENTRAL_DEFENDER),
                                                  new CBItem(SpielerPosition.getNameForPosition(CENTRAL_DEFENDER_OFF),
                                                             CENTRAL_DEFENDER_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(CENTRAL_DEFENDER_TOWING),
                                                             CENTRAL_DEFENDER_TOWING),
                                                  new CBItem(SpielerPosition.getNameForPosition(BACK),
                                                             BACK),
                                                  new CBItem(SpielerPosition.getNameForPosition(BACK_OFF),
                                                             BACK_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(BACK_DEF),
                                                             BACK_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(BACK_TOMID),
                                                             BACK_TOMID),
                                                  new CBItem(SpielerPosition.getNameForPosition(MIDFIELDER),
                                                             MIDFIELDER),
                                                  new CBItem(SpielerPosition.getNameForPosition(MIDFIELDER_OFF),
                                                             MIDFIELDER_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(MIDFIELDER_DEF),
                                                             MIDFIELDER_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(MIDFIELDER_TOWING),
                                                             MIDFIELDER_TOWING),
                                                  new CBItem(SpielerPosition.getNameForPosition(WINGER),
                                                             WINGER),
                                                  new CBItem(SpielerPosition.getNameForPosition(WINGER_OFF),
                                                             WINGER_OFF),
                                                  new CBItem(SpielerPosition.getNameForPosition(WINGER_DEF),
                                                             WINGER_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(WINGER_TOMID),
                                                             WINGER_TOMID),
                                                  new CBItem(SpielerPosition.getNameForPosition(FORWARD),
                                                             FORWARD),
                                                  new CBItem(SpielerPosition.getNameForPosition(FORWARD_DEF),
                                                             FORWARD_DEF),
                                                  new CBItem(SpielerPosition.getNameForPosition(FORWARD_TOWING),
                                                             FORWARD_TOWING)
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
            case KEEPER:
                return keeper;

            case CENTRAL_DEFENDER:
            case CENTRAL_DEFENDER_TOWING:
            case CENTRAL_DEFENDER_OFF:
                return rightCentralDefender;

            case BACK:
            case BACK_TOMID:
            case BACK_OFF:
            case BACK_DEF:
                return rightBack;

            case MIDFIELDER:
            case MIDFIELDER_OFF:
            case MIDFIELDER_DEF:
            case MIDFIELDER_TOWING:
                return rightInnerMidfield;

            case WINGER:
            case WINGER_TOMID:
            case WINGER_OFF:
            case WINGER_DEF:
                return rightWinger;

            case FORWARD:
            case FORWARD_TOWING:
            case FORWARD_DEF:
                return rightForward;

            case SUBSTITUTED1:
            case SUBSTITUTED2:
            case SUBSTITUTED3:
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
            case KEEPER:
                return HOVerwaltung.instance().getLanguageString("TORW");

            case CENTRAL_DEFENDER:
                return HOVerwaltung.instance().getLanguageString("IV");

            case CENTRAL_DEFENDER_TOWING:
                return HOVerwaltung.instance().getLanguageString("IVA");

            case CENTRAL_DEFENDER_OFF:
                return HOVerwaltung.instance().getLanguageString("IVO");

            case BACK:
                return HOVerwaltung.instance().getLanguageString("AV");

            case BACK_TOMID:
                return HOVerwaltung.instance().getLanguageString("AVI");

            case BACK_OFF:
                return HOVerwaltung.instance().getLanguageString("AVO");

            case BACK_DEF:
                return HOVerwaltung.instance().getLanguageString("AVD");

            case MIDFIELDER:
                return HOVerwaltung.instance().getLanguageString("MIT");

            case MIDFIELDER_OFF:
                return HOVerwaltung.instance().getLanguageString("MITO");

            case MIDFIELDER_DEF:
                return HOVerwaltung.instance().getLanguageString("MITD");

            case MIDFIELDER_TOWING:
                return HOVerwaltung.instance().getLanguageString("MITA");

            case WINGER:
                return HOVerwaltung.instance().getLanguageString("FLG");

            case WINGER_TOMID:
                return HOVerwaltung.instance().getLanguageString("FLGI");

            case WINGER_OFF:
                return HOVerwaltung.instance().getLanguageString("FLGO");

            case WINGER_DEF:
                return HOVerwaltung.instance().getLanguageString("FLGD");

            case FORWARD:
                return HOVerwaltung.instance().getLanguageString("STU");

            case FORWARD_TOWING:
                return HOVerwaltung.instance().getLanguageString("STUA");
                
            case FORWARD_DEF:
                return HOVerwaltung.instance().getLanguageString("STUD");

            case SUBSTITUTED1:
            case SUBSTITUTED2:
            case SUBSTITUTED3:
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

            case rightCentralDefender:
                return "rightCentralDefender";

            case leftCentralDefender:
                return "leftCentralDefender";
                
            case middleCentralDefender:
            	return "middleCentralDefender";

            case leftBack:
                return "leftBack";

            case rightWinger:
                return "rightWinger";

            case rightInnerMidfield:
                return "rightInnerMidfield";
                
            case centralInnerMidfield:
            	return "centralInnerMidfield";

            case leftInnerMidfield:
                return "leftInnerMidfield";

            case leftWinger:
                return "leftWinger";

            case rightForward:
                return "rightForward";
                
            case centralForward:
            	return "centralForward";

            case leftForward:
                return "leftForward";

            case substDefender:
                return "substDefender";

            case substInnerMidfield:
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
            case KEEPER:
                return HOVerwaltung.instance().getLanguageString("Torwart");

            case CENTRAL_DEFENDER:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger");

            case CENTRAL_DEFENDER_TOWING:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger_Aus");

            case CENTRAL_DEFENDER_OFF:
                return HOVerwaltung.instance().getLanguageString("Innenverteidiger_Off");

            case BACK:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger");

            case BACK_TOMID:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_In");

            case BACK_OFF:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Off");

            case BACK_DEF:
                return HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Def");

            case MIDFIELDER:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld");

            case MIDFIELDER_OFF:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Off");

            case MIDFIELDER_DEF:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Def");

            case MIDFIELDER_TOWING:
                return HOVerwaltung.instance().getLanguageString("Mittelfeld_Aus");

            case WINGER:
                return HOVerwaltung.instance().getLanguageString("Fluegel");

            case WINGER_TOMID:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_In");

            case WINGER_OFF:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_Off");

            case WINGER_DEF:
                return HOVerwaltung.instance().getLanguageString("Fluegelspiel_Def");

            case FORWARD:
                return HOVerwaltung.instance().getLanguageString("Sturm");

            case FORWARD_DEF:
                return HOVerwaltung.instance().getLanguageString("Sturm_Def");

            case FORWARD_TOWING:
                return HOVerwaltung.instance().getLanguageString("Sturm_Aus");

            case SUBSTITUTED1:
            case SUBSTITUTED2:
            case SUBSTITUTED3:
                return HOVerwaltung.instance().getLanguageString("Ausgewechselt");

            case COACH:
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
                return KEEPER;

            case rightBack:
            case leftBack: {
                if (taktik == TOWARDS_MIDDLE) {
                    return BACK_TOMID;
                } else if (taktik == OFFENSIVE) {
                    return BACK_OFF;
                } else if (taktik == DEFENSIVE) {
                    return BACK_DEF;
                } else {
                    return BACK;
                }
            }

            case middleCentralDefender:
            case rightCentralDefender:
            case leftCentralDefender: {
                if (taktik == TOWARDS_WING) {
                    return CENTRAL_DEFENDER_TOWING;
                } else if (taktik == OFFENSIVE) {
                    return CENTRAL_DEFENDER_OFF;
                } else {
                    return CENTRAL_DEFENDER;
                }
            }

            case rightWinger:
            case leftWinger: {
                if (taktik == TOWARDS_MIDDLE) {
                    return WINGER_TOMID;
                } else if (taktik == OFFENSIVE) {
                    return WINGER_OFF;
                } else if (taktik == DEFENSIVE) {
                    return WINGER_DEF;
                } else {
                    return WINGER;
                }
            }

            case centralInnerMidfield:
            case rightInnerMidfield:
            case leftInnerMidfield: {
                if (taktik == TOWARDS_WING) {
                    return MIDFIELDER_TOWING;
                } else if (taktik == OFFENSIVE) {
                    return MIDFIELDER_OFF;
                } else if (taktik == DEFENSIVE) {
                    return MIDFIELDER_DEF;
                } else {
                    return MIDFIELDER;
                }
            }

            case centralForward:
            case rightForward:
            case leftForward: {
            	if (taktik == DEFENSIVE) {
                    return FORWARD_DEF;
                } else if (taktik == TOWARDS_WING) {
                    return FORWARD_TOWING;
                } else {
                    return FORWARD;
                }
            }

            case substDefender:
                return CENTRAL_DEFENDER;

            case substInnerMidfield:
                return MIDFIELDER;

            case substWinger:
                return WINGER;

            case substKeeper:
                return KEEPER;

            case substForward:
                return FORWARD;
        }

        return UNKNOWN;
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
     * 
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getSortId() {
        int id = this.getPosition();

		if (id == ISpielerPosition.FORWARD_TOWING)  {
			id = 18;
		}

        if (this.getId() >= ISpielerPosition.startReserves)  {
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
    public final int compareTo(ISpielerPosition obj) {
        if (obj instanceof SpielerPosition) {
            final SpielerPosition position = (SpielerPosition) obj;

            //Beide aufgestellt ?
            if ((this.getId() < ISpielerPosition.startReserves)
                && (position.getId() < ISpielerPosition.startReserves)) {
                if (this.getPosition() < position.getPosition()) {
                    return -1;
                } else if (this.getPosition() == position.getPosition()) {
                    return 0;
                } else {
                    return 1;
                }
            }
            //this aufgestellt ?
            else if ((this.getId() < ISpielerPosition.startReserves)
                     && (position.getId() >= ISpielerPosition.startReserves)) {
                return -1;
            }
            //position aufgestellt
            else if ((this.getId() >= ISpielerPosition.startReserves)
                     && (position.getId() < ISpielerPosition.startReserves)) {
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
