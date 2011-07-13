package de.hattrickorganizer.gui.model;

import gui.HOColorName;

import java.awt.Color;

import plugins.IMatchLineup;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.matches.MatchKurzInfo;


public final class MatchesColumnModel extends HOColumnModel {

	   //	League match.
    static final Color LIGASPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_LEAGUE_BG);//new Color(255, 255, 200);
    //	Qualification match.
    static final Color QUALISPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_QUALIFIKATION_BG);//new Color(255, 200, 200);
    //	Cup match (standard league cup).
    static final Color POKALSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_CUP_BG);//new Color(200, 255, 200);
    //	Friendly (normal rules).
    static final Color TESTSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_FRIENDLY_BG);//Color.white;
    //	Not currently in use, but reserved for international competition matches with normal rules (may or may not be implemented at some future point).
    static final Color INTSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_INT_BG);//Color.lightGray;
    //	Not currently in use, but reserved for international competition matches with cup rules (may or may not be implemented at some future point).
    static final Color INTCUPSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_MASTERS_BG);//Color.lightGray;
    //	International friendly (normal rules).
    static final Color INT_TESTSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_INTFRIENDLY_BG);//Color.white;
    //	National teams competition match (normal rules).
    static final Color LAENDERSPIEL = ThemeManager.getColor(HOColorName.MATCHTYPE_NATIONAL_BG);//new Color(220, 220, 255);
 
	private static final long serialVersionUID = -2148644586671286752L;

	private MatchKurzInfo[] m_clMatches;
	
	protected MatchesColumnModel(int id){
		super(id,"Matches");
		initialize();
	}

	private void initialize() {
		columns =  UserColumnFactory.createMatchesArray();
		
	
		if(m_clMatches != null)
			initData();
	}


    public final MatchKurzInfo getMatch(int id) {
        if (id > 0) {
            for (int i = 0; i < m_clMatches.length; i++) {
                if (m_clMatches[i].getMatchID() == id) {
                    return m_clMatches[i];
                }
            }
        }
        return null;
    }
	
    public boolean isEditable(){
		return false;
	}

    public final void setValues(MatchKurzInfo[] matches) {
        m_clMatches = matches;
        initData();
    }
    
    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    @Override
	protected void initData() {
    	UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
    	m_clData = new Object[m_clMatches.length][tmpDisplayedColumns.length];
 
    	for (int i = 0; i < m_clMatches.length; i++) {
   
            for (int j = 0; j < tmpDisplayedColumns.length; j++) {
    			m_clData[i][j] = ((MatchKurzInfoColumn)tmpDisplayedColumns[j]).getTableEntry(m_clMatches[i]);
			}
            
        }
    }
    
    /**
     * Get the color for a certain match type.
     */
    static  Color getColor4Matchtyp(int typ) {
        switch (typ) {
            case IMatchLineup.LIGASPIEL:
                return LIGASPIEL;

            case IMatchLineup.POKALSPIEL:
                return POKALSPIEL;

            case IMatchLineup.QUALISPIEL:
                return QUALISPIEL;

            case IMatchLineup.INTCUPSPIEL:
                return INTCUPSPIEL;

            case IMatchLineup.INTSPIEL:
                return INTSPIEL;

            case IMatchLineup.INT_TESTCUPSPIEL:
             case IMatchLineup.INT_TESTSPIEL:
                return INT_TESTSPIEL;

            case IMatchLineup.LAENDERCUPSPIEL:
            case IMatchLineup.LAENDERSPIEL:
            case IMatchLineup.TESTLAENDERSPIEL:
                return LAENDERSPIEL;

            case IMatchLineup.TESTPOKALSPIEL:
            case IMatchLineup.TESTSPIEL:
                return TESTSPIEL;

            //Fehler?
            default:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_BG);
        }
    }
}
