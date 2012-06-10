package ho.core.gui.model;

import ho.core.gui.comp.table.HOTableModel;
import ho.core.gui.comp.table.UserColumn;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineup;

import java.awt.Color;



public final class MatchesColumnModel extends HOTableModel {

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
    public static  Color getColor4Matchtyp(int typ) {
        switch (typ) {
            case MatchLineup.LIGASPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_LEAGUE_BG);

            case MatchLineup.POKALSPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_CUP_BG);

            case MatchLineup.QUALISPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_QUALIFIKATION_BG);

            case MatchLineup.INTCUPSPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_INT_BG);

            case MatchLineup.INTSPIEL:
                return  ThemeManager.getColor(HOColorName.MATCHTYPE_MASTERS_BG);

            case MatchLineup.INT_TESTCUPSPIEL:
             case MatchLineup.INT_TESTSPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_INTFRIENDLY_BG);

            case MatchLineup.LAENDERCUPSPIEL:
            case MatchLineup.LAENDERSPIEL:
            case MatchLineup.TESTLAENDERSPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_NATIONAL_BG);

            case MatchLineup.TESTPOKALSPIEL:
            case MatchLineup.TESTSPIEL:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_FRIENDLY_BG);

            case MatchLineup.TOURNAMENTGROUP:
            	return ThemeManager.getColor(HOColorName.MATCHTYPE_TOURNAMENT_GROUP_BG);
            case MatchLineup.TOURNAMENTPLAYOFF:
            	return ThemeManager.getColor(HOColorName.MATCHTYPE_TOURNAMENT_FINALS_BG);
            //Fehler?
            default:
                return ThemeManager.getColor(HOColorName.MATCHTYPE_BG);
        }
    }
}
