package de.hattrickorganizer.gui.model;

import java.awt.Color;

import de.hattrickorganizer.model.matches.MatchKurzInfo;


public final class MatchesColumnModel extends HOColumnModel {

	 private MatchKurzInfo[] m_clMatches;

	/** TODO Missing Parameter Documentation */
	
	//	League match.	
	protected static final Color LIGASPIEL = new Color(255, 255, 200);

	/** TODO Missing Parameter Documentation */
	
	//	Qualification match.	
	protected static final Color QUALISPIEL = new Color(255, 200, 200);

	/** TODO Missing Parameter Documentation */
	
	//	Cup match (standard league cup).	
	protected static final Color POKALSPIEL = new Color(200, 255, 200);

	/** TODO Missing Parameter Documentation */
	
	//	Friendly (normal rules).	
	protected static final Color TESTSPIEL = Color.white;

	/** TODO Missing Parameter Documentation */
	
	//	Friendly (cup rules).	
	protected static final Color TESTPOKALSPIEL = Color.white;

	/** TODO Missing Parameter Documentation */
	
	//	Not currently in use, but reserved for international competition matches with normal rules (may or may not be implemented at some future point).	
	protected static final Color INTSPIEL = Color.lightGray;

	/** TODO Missing Parameter Documentation */
	
	//	Not currently in use, but reserved for international competition matches with cup rules (may or may not be implemented at some future point).	
	protected static final Color INTCUPSPIEL = Color.lightGray;

	/** TODO Missing Parameter Documentation */
	
	//	International friendly (normal rules).	
	protected static final Color INT_TESTSPIEL = Color.white;

	/** TODO Missing Parameter Documentation */
	
	//	International friendly (cup rules).	
	protected static final Color INT_TESTCUPSPIEL = Color.white;

	/** TODO Missing Parameter Documentation */
	
	//	National teams competition match (normal rules).	
	protected static final Color LAENDERSPIEL = new Color(220, 220, 255);

	/** TODO Missing Parameter Documentation */
	
	//	National teams competition match (cup rules).	
	protected static final Color LAENDERCUPSPIEL = new Color(220, 220, 255);

	/** TODO Missing Parameter Documentation */
	
	//	National teams friendly.
	protected static final Color TESTLAENDERSPIEL = new Color(220, 220, 255);
	 
	/** TODO Missing Parameter Documentation */
	protected static final java.awt.Color FG_EIGENESTEAM = new java.awt.Color(50, 50, 150);
	
	
	
	protected MatchesColumnModel(int id){
		super(id,"Matches");
		initialize();
	}

	private void initialize() {
		columns =  UserColumnFactory.createMatchesArray();
		
	
		if(m_clMatches != null)
			initData();
	}

	   /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
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
    /**
     * Matches neu setzen
     *
     * @param matches TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(MatchKurzInfo[] matches) {
        m_clMatches = matches;
        initData();
    }
    
    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    protected void initData() {
    	UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
    	m_clData = new Object[m_clMatches.length][tmpDisplayedColumns.length];
 
    	for (int i = 0; i < m_clMatches.length; i++) {
   
            for (int j = 0; j < tmpDisplayedColumns.length; j++) {
    			m_clData[i][j] = ((MatchKurzInfoColumn)tmpDisplayedColumns[j]).getTableEntry(m_clMatches[i]);
			}
            
        }
    }
}
