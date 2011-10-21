package hoplugins.feedback.model.rating;

import hoplugins.Commons;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import plugins.IHOMiniModel;
import plugins.ISpielerPosition;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class SpielerPosition implements java.io.Serializable, Comparable<SpielerPosition>, plugins.ISpielerPosition {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Array mit den Konstanten (CBItems) f�r die Positionen, Ohne Ausgewechselt */
//    public static final CBItem[] POSITIONEN = {
//                                                  new CBItem(SpielerPosition.getNameForPosition(UNBESTIMMT),
//                                                             UNBESTIMMT),
//                                                  new CBItem(SpielerPosition.getNameForPosition(TRAINER),
//                                                             TRAINER),
//                                                  new CBItem(SpielerPosition.getNameForPosition(TORWART),
//                                                             TORWART),
//                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER),
//                                                             INNENVERTEIDIGER),
//                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER_OFF),
//                                                             INNENVERTEIDIGER_OFF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(INNENVERTEIDIGER_AUS),
//                                                             INNENVERTEIDIGER_AUS),
//                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER),
//                                                             AUSSENVERTEIDIGER),
//                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_OFF),
//                                                             AUSSENVERTEIDIGER_OFF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_DEF),
//                                                             AUSSENVERTEIDIGER_DEF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(AUSSENVERTEIDIGER_IN),
//                                                             AUSSENVERTEIDIGER_IN),
//                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD),
//                                                             MITTELFELD),
//                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_OFF),
//                                                             MITTELFELD_OFF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_DEF),
//                                                             MITTELFELD_DEF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(MITTELFELD_AUS),
//                                                             MITTELFELD_AUS),
//                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL),
//                                                             FLUEGELSPIEL),
//                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_OFF),
//                                                             FLUEGELSPIEL_OFF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_DEF),
//                                                             FLUEGELSPIEL_DEF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(FLUEGELSPIEL_IN),
//                                                             FLUEGELSPIEL_IN),
//                                                  new CBItem(SpielerPosition.getNameForPosition(STURM),
//                                                             STURM),
//                                                  new CBItem(SpielerPosition.getNameForPosition(STURM_DEF),
//                                                             STURM_DEF),
//                                                  new CBItem(SpielerPosition.getNameForPosition(STURM_AUS),
//                                                             STURM_AUS)
//                                              };

	private static final long serialVersionUID = 6292902901162370275L;

    //Konstanten f�r TRAININGSEFFEKTE

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
     * Konstruktor l�dt die SpielerPosition aus einem InputStream
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
            //HOLogger.instance().log(getClass(),ioe);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt eine m�gliche HT-Positionsid f�r eine HO-Positionsid zur�ck. Nur zum Laden des
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
                //HOLogger.instance().log(SpielerPosition.class,"Unbestimmte Position: " + posId);
                return ausgewechselt;
            }
        }
    }

    /**
     * Gibt das K�rzel f�r den Namen zur�ck
     *
     * @param posId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getKurzNameForPosition(byte posId) {
    	final IHOMiniModel model = Commons.getModel();

        switch (posId) {
            case KEEPER:
                return model.getLanguageString("TORW");

            case CENTRAL_DEFENDER:
                return model.getLanguageString("IV");

            case CENTRAL_DEFENDER_TOWING:
                return model.getLanguageString("IVA");

            case CENTRAL_DEFENDER_OFF:
                return model.getLanguageString("IVO");

            case BACK:
                return model.getLanguageString("AV");

            case BACK_TOMID:
                return model.getLanguageString("AVI");

            case BACK_OFF:
                return model.getLanguageString("AVO");

            case BACK_DEF:
                return model.getLanguageString("AVD");

            case MIDFIELDER:
                return model.getLanguageString("MIT");

            case MIDFIELDER_OFF:
                return model.getLanguageString("MITO");

            case MIDFIELDER_DEF:
                return model.getLanguageString("MITD");

            case MIDFIELDER_TOWING:
                return model.getLanguageString("MITA");

            case WINGER:
                return model.getLanguageString("FLG");

            case WINGER_TOMID:
                return model.getLanguageString("FLGI");

            case WINGER_OFF:
                return model.getLanguageString("FLGO");

            case WINGER_DEF:
                return model.getLanguageString("FLGD");

            case FORWARD:
                return model.getLanguageString("STU");

            case FORWARD_TOWING:
                return model.getLanguageString("STUA");

            case FORWARD_DEF:
                return model.getLanguageString("STUD");

            case SUBSTITUTED1:
            case SUBSTITUTED2:
            case SUBSTITUTED3:
                return model.getLanguageString("Ausgewechselt");

            //HOLogger.instance().log(getClass(), "Unbestimmte Position: " + posId );
            default:
                return model.getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt zu einer Positionsid den Namen zur�ck
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
                return "insideBack1";

            case leftCentralDefender:
                return "insideBack2";

            case leftBack:
                return "leftBack";

            case rightWinger:
                return "rightWinger";

            case rightInnerMidfield:
                return "insideMid1";

            case leftInnerMidfield:
                return "insideMid2";

            case leftWinger:
                return "leftWinger";

            case rightForward:
                return "forward1";

            case leftForward:
                return "forward2";

            case substDefender:
                return "substBack";

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
     * Gibt zu einer Positionsid den Namen zur�ck
     *
     * @param posId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForPosition(byte posId) {
    	final IHOMiniModel model = Commons.getModel();

        switch (posId) {
            case KEEPER:
                return model.getLanguageString("Torwart");

            case CENTRAL_DEFENDER:
                return model.getLanguageString("Innenverteidiger");

            case CENTRAL_DEFENDER_TOWING:
                return model.getLanguageString("Innenverteidiger_Aus");

            case CENTRAL_DEFENDER_OFF:
                return model.getLanguageString("Innenverteidiger_Off");

            case BACK:
                return model.getLanguageString("Aussenverteidiger");

            case BACK_TOMID:
                return model.getLanguageString("Aussenverteidiger_In");

            case BACK_OFF:
                return model.getLanguageString("Aussenverteidiger_Off");

            case BACK_DEF:
                return model.getLanguageString("Aussenverteidiger_Def");

            case MIDFIELDER:
                return model.getLanguageString("Mittelfeld");

            case MIDFIELDER_OFF:
                return model.getLanguageString("Mittelfeld_Off");

            case MIDFIELDER_DEF:
                return model.getLanguageString("Mittelfeld_Def");

            case MIDFIELDER_TOWING:
                return model.getLanguageString("Mittelfeld_Aus");

            case WINGER:
                return model.getLanguageString("Fluegel");

            case WINGER_TOMID:
                return model.getLanguageString("Fluegelspiel_In");

            case WINGER_OFF:
                return model.getLanguageString("Fluegelspiel_Off");

            case WINGER_DEF:
                return model.getLanguageString("Fluegelspiel_Def");

            case FORWARD:
                return model.getLanguageString("Sturm");

            case FORWARD_DEF:
                return model.getLanguageString("Sturm_Def");

            case FORWARD_TOWING:
                return model.getLanguageString("Sturm_Aus");

            case SUBSTITUTED1:
            case SUBSTITUTED2:
            case SUBSTITUTED3:
                return model.getLanguageString("Ausgewechselt");

            case COACH:
                return model.getLanguageString("Trainer");

            //HOLogger.instance().log(getClass(), "Unbestimmte Position: " + posId );
            default:
                return model.getLanguageString("Unbestimmt");
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
                } else if (taktik == OLD_EXTRA_MIDFIELD) {
                    return MIDFIELDER;
                } else if (taktik == OLD_EXTRA_FORWARD) {
                    return FORWARD;
                } else if (taktik == OLD_EXTRA_DEFENDER) {
                    return CENTRAL_DEFENDER;
                } else if (taktik == OFFENSIVE) {
                    return BACK_OFF;
                } else if (taktik == DEFENSIVE) {
                    return BACK_DEF;
                } else {
                    return BACK;
                }
            }

            case rightCentralDefender:
            case leftCentralDefender: {
                if (taktik == TOWARDS_WING) {
                    return CENTRAL_DEFENDER_TOWING;
                } else if (taktik == OLD_EXTRA_MIDFIELD) {
                    return MIDFIELDER;
                } else if (taktik == OLD_EXTRA_FORWARD) {
                    return FORWARD;
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
                } else if (taktik == OLD_EXTRA_DEFENDER) {
                    return CENTRAL_DEFENDER;
                } else if (taktik == OLD_EXTRA_FORWARD) {
                    return FORWARD;
                } else if (taktik == OLD_EXTRA_MIDFIELD) {
                    return MIDFIELDER;
                } else if (taktik == OFFENSIVE) {
                    return WINGER_OFF;
                } else if (taktik == DEFENSIVE) {
                    return WINGER_DEF;
                } else {
                    return WINGER;
                }
            }

            case rightInnerMidfield:
            case leftInnerMidfield: {
                if (taktik == TOWARDS_WING) {
                    return MIDFIELDER_TOWING;
                } else if (taktik == OLD_EXTRA_DEFENDER) {
                    return CENTRAL_DEFENDER;
                } else if (taktik == OLD_EXTRA_FORWARD) {
                    return FORWARD;
                } else if (taktik == OFFENSIVE) {
                    return MIDFIELDER_OFF;
                } else if (taktik == DEFENSIVE) {
                    return MIDFIELDER_DEF;
                } else {
                    return MIDFIELDER;
                }
            }

            case rightForward:
            case leftForward: {
                if (taktik == OLD_EXTRA_DEFENDER) {
                    return CENTRAL_DEFENDER;
                } else if (taktik == OLD_EXTRA_MIDFIELD) {
                    return MIDFIELDER;
                } else if (taktik == DEFENSIVE) {
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
     * liefert eine ID nach der Sortiert werden kann ( z.B. Spierl�bersichtstabelle
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getSortId() {
        int id = this.getPosition();

		if (id == ISpielerPosition.FORWARD_TOWING)  {
			id = 18;
		}

        if (this.getId() >= ISpielerPosition.oldSubstKeeper)  {
            id += 20;
        }

        return id;
    }

    /**
     * liefert eine ID nach der Sortiert werden kann ( z.B. Spierl�bersichtstabelle
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
     * liefert den Trainingseffekt dieser Position f�r das eingestellte Training
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
    public final int compareTo(SpielerPosition obj) {
        
        final SpielerPosition position = obj;

        //Beide aufgestellt ?
        if ((this.getId() < ISpielerPosition.oldSubstKeeper)
            && (position.getId() < ISpielerPosition.oldSubstKeeper)) {
            if (this.getPosition() < position.getPosition()) {
                return -1;
            } else if (this.getPosition() == position.getPosition()) {
                return 0;
            } else {
                return 1;
            }
        }
        //this aufgestellt ?
        else if ((this.getId() < ISpielerPosition.oldSubstKeeper)
                 && (position.getId() >= ISpielerPosition.oldSubstKeeper)) {
            return -1;
        }
        //position aufgestellt
        else if ((this.getId() >= ISpielerPosition.oldSubstKeeper)
                 && (position.getId() < ISpielerPosition.oldSubstKeeper)) {
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
               //Hilfsstrom schlie�en
               das.close ();

               return data;*/
        } catch (IOException ioe) {
            //HOLogger.instance().log(getClass(),ioe);
        }

        //   return data;
    }
}
