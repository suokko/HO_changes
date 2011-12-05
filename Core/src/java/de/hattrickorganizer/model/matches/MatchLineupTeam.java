// %716628770:de.hattrickorganizer.model.matches%
/*
 * MatchLineupTeam.java
 *
 * Created on 20. Oktober 2003, 08:55
 */
package de.hattrickorganizer.model.matches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import plugins.ILineUp;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;
import plugins.ISpielerPosition;
import plugins.ISubstitution;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchLineupTeam implements IMatchLineupTeam {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String m_sTeamName;

    /** TODO Missing Parameter Documentation */
    protected Vector<IMatchLineupPlayer> m_vAufstellung = new Vector<IMatchLineupPlayer>();
    
    protected ArrayList<ISubstitution> m_vSubstitutions = new ArrayList<ISubstitution>();
  
    /** TODO Missing Parameter Documentation */
    protected int m_iErfahrung;

    /** TODO Missing Parameter Documentation */
    protected int m_iTeamID;
    // null player to fill empty spots
    private final static MatchLineupPlayer NULLPLAYER = new MatchLineupPlayer(-1, 0, -1, -1d, "", 0);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MatchLineupTeam
     *
     * @param teamName TODO Missing Constructuor Parameter Documentation
     * @param teamID TODO Missing Constructuor Parameter Documentation
     * @param erfahrung TODO Missing Constructuor Parameter Documentation
     */
    public MatchLineupTeam(String teamName, int teamID, int erfahrung) {
        m_sTeamName = teamName;
        m_iErfahrung = erfahrung;
        m_iTeamID = teamID;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_vAufstellung.
     *
     * @param m_vAufstellung New value of property m_vAufstellung.
     */
    public final void setAufstellung(Vector<IMatchLineupPlayer> m_vAufstellung) {
        this.m_vAufstellung = m_vAufstellung;
    }

    /**
     * Getter for property m_vAufstellung.
     *
     * @return Value of property m_vAufstellung.
     */
    public final Vector<IMatchLineupPlayer> getAufstellung() {
        return m_vAufstellung;
    }
    
    /**
     * Setter for property m_vSubstitutions.
     *
     * @param m_vSubstitution New value of property m_vSubstitutions.
     */
    public final void setSubstitutions(ArrayList<ISubstitution> m_vSubstitution) {
        
    	this.m_vSubstitutions = m_vSubstitution;
        
        // Make sure substitutions are sorted first on minute, then by ID.
        Collections.sort(m_vSubstitution, new Comparator<ISubstitution>(){
        		public int compare(ISubstitution o1, ISubstitution o2) {
        			
        			if (o1.getMatchMinuteCriteria() == o2.getMatchMinuteCriteria()) {
        				if (o1.getPlayerOrderId() == o2.getPlayerOrderId()) {
               				return 0;
        				}
        				if (o1.getPlayerOrderId() < o2.getPlayerOrderId()) {
        					return -1;
        				}
        				
        				return 1;
        				
        			}
        			
        			if (o1.getMatchMinuteCriteria() < o2.getMatchMinuteCriteria()) {
        				return -1;
        			}
        			
        			
        			// minutes in o1 is greater than o2
        			return 1;
        		}
        		
        		public boolean equals(ISubstitution o1, ISubstitution o2) {
        			// Lazy solution, a proper would compare all fields, but we don't need that.
        			if (o1 == o2) {
        				return true;
        			} else {
        				return false;
        			}
        		}
        	});
    }

    /**
     * Getter for property m_vSubstitutions.
     *
     * @return Value of property m_vSubstitutions.
     */
    public final ArrayList<ISubstitution> getSubstitutions() {
        return m_vSubstitutions;
    }
    
   /**
     * Setter for property m_iErfahrung.
     *
     * @param m_iErfahrung New value of property m_iErfahrung.
     */
    public final void setErfahrung(int m_iErfahrung) {
        this.m_iErfahrung = m_iErfahrung;
    }

    /**
     * Getter for property m_iErfahrung.
     *
     * @return Value of property m_iErfahrung.
     */
    public final int getErfahrung() {
        return m_iErfahrung;
    }

    /**
     * Returns a player by ID, players in captain and set piece
     * positions are ignored.
     * 
     * @param id The spielerId of the player
     *
     * @return The object matching the criteria, or null if none found
     */
    public final IMatchLineupPlayer getPlayerByID(int id) {
    	
        for (IMatchLineupPlayer player : m_vAufstellung) {
            if (player.getSpielerId() == id) {
            	if ((player.getId() == ISpielerPosition.captain)
            			|| (player.getId() == ISpielerPosition.setPieces)) {
            		// ignore
            	} else {
            		return player;
            	}
            }
        }

        return null;
    }

    /**
     * Liefert Einen Spieler per PositionsID aus der Aufstellung
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final IMatchLineupPlayer getPlayerByPosition(int id) {
        MatchLineupPlayer player = null;

        for (int i = 0; (m_vAufstellung != null) && (i < m_vAufstellung.size()); i++) {
            player = (MatchLineupPlayer) m_vAufstellung.elementAt(i);

            if (player.getId() == id) {
                return player;
            }
        }
        return NULLPLAYER;
    }

    /**
     * Setter for property m_iTeamID.
     *
     * @param m_iTeamID New value of property m_iTeamID.
     */
    public final void setTeamID(int m_iTeamID) {
        this.m_iTeamID = m_iTeamID;
    }

    /**
     * Getter for property m_iTeamID.
     *
     * @return Value of property m_iTeamID.
     */
    public final int getTeamID() {
        return m_iTeamID;
    }

    /**
     * Setter for property m_sTeamName.
     *
     * @param m_sTeamName New value of property m_sTeamName.
     */
    public final void setTeamName(java.lang.String m_sTeamName) {
        this.m_sTeamName = m_sTeamName;
    }

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public final java.lang.String getTeamName() {
        return m_sTeamName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param player TODO Missing Method Parameter Documentation
     */
    public final void add2Aufstellung(IMatchLineupPlayer player) {
        m_vAufstellung.add(player);
    }

    /**
     * Determinates the played formation.
     */
    public final byte determinateSystem() {
        short abw = 0;
        short mf = 0;
        short st = 0;

        MatchLineupPlayer player = null;

        for (int i = 0; i < m_vAufstellung.size(); i++) {
            player = (MatchLineupPlayer) m_vAufstellung.get(i);

            if ((player != null) && (player.getId() < ISpielerPosition.startReserves)) {
                switch (player.getPosition()) {
                    case ISpielerPosition.UNKNOWN:
                        break;

                    case ISpielerPosition.BACK:
                    case ISpielerPosition.BACK_TOMID:
                    case ISpielerPosition.BACK_OFF:
                    case ISpielerPosition.BACK_DEF:
                    case ISpielerPosition.CENTRAL_DEFENDER:
                    case ISpielerPosition.CENTRAL_DEFENDER_TOWING:
                    case ISpielerPosition.CENTRAL_DEFENDER_OFF:
                        abw++;
                        break;

                    case ISpielerPosition.MIDFIELDER:
                    case ISpielerPosition.MIDFIELDER_OFF:
                    case ISpielerPosition.MIDFIELDER_DEF:
                    case ISpielerPosition.MIDFIELDER_TOWING:
                    case ISpielerPosition.WINGER:
                    case ISpielerPosition.WINGER_TOMID:
                    case ISpielerPosition.WINGER_OFF:
                    case ISpielerPosition.WINGER_DEF:
                        mf++;
                        break;

                    case ISpielerPosition.FORWARD:
                    case ISpielerPosition.FORWARD_TOWING:
                    case ISpielerPosition.FORWARD_DEF:
                        st++;
                        break;
                }
            }
        }

        if (abw == 2) {
        	//253
        	if (mf == 5) {
        		return ILineUp.SYS_253;
        	}
        	//MURKS
            else {
                return ILineUp.SYS_MURKS;
            }
        } else if (abw == 3) {
        	//343
            if (mf == 4) {
                return ILineUp.SYS_343;
            } //352
            else if ((mf == 5) && (st == 2)) {
                return ILineUp.SYS_352;
            }
            //MURKS
            else {
                return ILineUp.SYS_MURKS;
            }
        } else if (abw == 4) {
            //433
            if ((mf == 3) && (st == 3)) {
                return ILineUp.SYS_433;
            } //442
            else if ((mf == 4) && (st == 2)) {
                return ILineUp.SYS_442;
            } //451
            else if ((mf == 5) && (st == 1)) {
                return ILineUp.SYS_451;
            }
            //MURKS
            else {
                return ILineUp.SYS_MURKS;
            }
        } else if (abw == 5) {
            //532
            if ((mf == 3) && (st == 2)) {
                return ILineUp.SYS_532;
            } //541
            else if ((mf == 4) && (st == 1)) {
                return ILineUp.SYS_541;
            } //523
            else if ((mf == 2) && (st == 3)) {
                return ILineUp.SYS_523;
            } //550
            else if ((mf == 5) && (st == 0)) {
                return ILineUp.SYS_550;
            }
            //MURKS
            else {
                return ILineUp.SYS_MURKS;
            }
        } //MURKS
        else {
            return ILineUp.SYS_MURKS;
        }
    }
}
