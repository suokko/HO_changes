package de.hattrickorganizer.gui.model;

import java.awt.Color;

import plugins.IMatchLineup;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.matches.MatchKurzInfo;

/**
 * column shows values from a matchKurzInfo
 * @author Thorsten Dietz
 * @since 1.36
 */
class MatchKurzInfoColumn extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name,int minWidth){
		this(id,name,name,minWidth);
		
	}
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name, String tooltip,int minWidth){
		super(id,name,tooltip);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	
	/**
	 * overwritten by created column
	 * @param match
	 * @return
	 */
	public TableEntry getTableEntry(MatchKurzInfo match){
		return null;
	}
	
	/**
	 * overwritten by created column
	 * @param spielerCBItem
	 * @return
	 */
	public TableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
		return null;
	}
	
	 /**
     * returns the Color for a matchtyp
     *
     * @param typ 
     * @return Color
     */
    protected Color getColor4Matchtyp(int typ) {
        switch (typ) {
            case IMatchLineup.LIGASPIEL:
                return MatchesColumnModel.LIGASPIEL;

            case IMatchLineup.POKALSPIEL:
                return MatchesColumnModel.POKALSPIEL;

            case IMatchLineup.QUALISPIEL:
                return MatchesColumnModel.QUALISPIEL;

            case IMatchLineup.LAENDERCUPSPIEL:
                return MatchesColumnModel.LAENDERCUPSPIEL;

            case IMatchLineup.INTCUPSPIEL:
                return MatchesColumnModel.INTCUPSPIEL;

            case IMatchLineup.LAENDERSPIEL:
                return MatchesColumnModel.LAENDERSPIEL;

            case IMatchLineup.INTSPIEL:
                return MatchesColumnModel.INTSPIEL;

            case IMatchLineup.INT_TESTCUPSPIEL:
                return MatchesColumnModel.INT_TESTCUPSPIEL;

            case IMatchLineup.INT_TESTSPIEL:
                return MatchesColumnModel.INT_TESTSPIEL;

            case IMatchLineup.TESTLAENDERSPIEL:
                return MatchesColumnModel.TESTLAENDERSPIEL;

            case IMatchLineup.TESTPOKALSPIEL:
                return MatchesColumnModel.TESTPOKALSPIEL;

            case IMatchLineup.TESTSPIEL:
                return MatchesColumnModel.TESTSPIEL;

            //Fehler?
            default:
                return Color.white;
        }
    }
    
    /**
     * return a String with shows the result
     *
     * @param home 
     * @param guesz 
     *
     * @return String
     */
    protected String createTorString(int home, int guest) {
        final StringBuffer buffer = new StringBuffer();

        if ((home < 0) || (guest < 0)) {
            return "-";
        }

        if (home < 10) {
            buffer.append(" ");
        }

        if (home >= 0) {
            buffer.append(home);
        }

        buffer.append(" : ");

        if (guest < 10) {
            buffer.append(" ");
        }

        if (guest >= 0) {
            buffer.append(guest);
        }

        return buffer.toString();
    }
}
