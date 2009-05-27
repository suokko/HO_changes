package de.hattrickorganizer.gui.model;

import java.util.Vector;

import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;

public class LineupColumnModel extends HOColumnModel {

	private Vector m_vPlayers;
	
	protected LineupColumnModel(int id){
		super(id,"Aufstellung");
		initialize();
		
	}
	
	private void initialize() {
		UserColumn[] basic =  UserColumnFactory.createPlayerBasicArray();
		columns = new UserColumn[47];
		columns[0] = basic[0];
		columns[46] = basic[1];
		
		UserColumn[] skills =  UserColumnFactory.createPlayerSkillArray();
		for (int i = 9; i < skills.length+9; i++) {
			columns[i] = skills[i-9];
		}
		
		UserColumn[] positions =  UserColumnFactory.createPlayerPositionArray();
		for (int i = 20; i < positions.length+20; i++) {
			columns[i] = positions[i-20];
		}
		
		UserColumn[] goals =  UserColumnFactory.createGoalsColumnsArray();
		for (int i = 40; i < goals.length+40; i++) {
			columns[i] = goals[i-40];
		}
		
		UserColumn[] add =  UserColumnFactory.createPlayerAdditionalArray();
		columns[1] = add[0];
		columns[2] = add[1];
		columns[3] = add[2];
		
		columns[4] = new BooleanColumn(UserColumnFactory.AUTO_LINEUP," ","AutoAufstellung",28){
								public boolean isEditable(){
											return false;
										}};
		columns[5] = add[3];
		columns[6] = add[4];
		columns[7] = add[5];
		columns[8] = add[6];
		columns[44] = add[7];
		columns[45] = add[8];
		columns[39] = add[9];
	}
	
    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isCellEditable(int row, int col) {
        if (getValueAt(row, col) instanceof Boolean) {
            return true;
        } 
        
        return false;
        
    }
    
    /**
     * Listener für die Checkboxen zum Autoaufstellen
     *
     * @param zeile TODO Missing Constructuor Parameter Documentation
     */
    public final void setSpielberechtigung() {
        try {
            for (int i = 0; i < this.getRowCount(); i++) {
                final int id = Integer.parseInt(((de.hattrickorganizer.gui.templates.ColorLabelEntry) getValueAt(i,getColumnIndexOfDisplayedColumn(UserColumnFactory.ID)))
                                                .getText());
                final Spieler spieler = getSpieler(id);

                if ((spieler != null)
                    && (spieler.isSpielberechtigt() != ((Boolean) getValueAt(i, getColumnIndexOfDisplayedColumn(UserColumnFactory.AUTO_LINEUP))).booleanValue())) {
                    spieler.setSpielberechtigt(((Boolean) getValueAt(i, getColumnIndexOfDisplayedColumn(UserColumnFactory.AUTO_LINEUP))).booleanValue());
                }

            }
        } catch (Exception e) {
        	HOLogger.instance().log(getClass(),e);
        }
    }
    
    
    
    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spieler getSpieler(int id) {
        if (id > 0) {
            for (int i = 0; i < m_vPlayers.size(); i++) {
                if (((Spieler) m_vPlayers.get(i)).getSpielerID() == id) {
                    return (Spieler) m_vPlayers.get(i);
                }
            }
        }

        return null;
    }
    
    /**
     * Spieler neu setzen
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void setValues(Vector player) {
    	m_vPlayers = player;
        initData();
    }
    
    /**
     * Fügt der Tabelle einen Spieler hinzu
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param index TODO Missing Constructuor Parameter Documentation
     */
    public final void addSpieler(Spieler player, int index) {
    	m_vPlayers.add(index, player);
        initData();
    }
    /**
     * create a data[][] from player-Vector
     */
    protected void initData() {
    	UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
    	m_clData = new Object[m_vPlayers.size()][tmpDisplayedColumns.length];
    	
    	for (int i = 0; i < m_vPlayers.size(); i++) {
    		final Spieler aktuellerSpieler = (Spieler) m_vPlayers.get(i);
    		
    		for (int j = 0; j < tmpDisplayedColumns.length; j++) {
    			if(tmpDisplayedColumns[j] instanceof PlayerColumn)
    				m_clData[i][j] = ((PlayerColumn)tmpDisplayedColumns[j]).getTableEntry(aktuellerSpieler,null);
    			if(tmpDisplayedColumns[j] instanceof BooleanColumn)
    				m_clData[i][j] = ((BooleanColumn)tmpDisplayedColumns[j]).getValue(aktuellerSpieler);
    		}
    	}
    }
    
    /**
     * Passt nur die Aufstellung an
     */
    public final void reInitData() {
    	UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
        for (int i = 0; i < m_vPlayers.size(); i++) {
            final Spieler aktuellerSpieler = (Spieler) m_vPlayers.get(i);
            
            for (int j = 0; j < tmpDisplayedColumns.length; j++) {
				if(tmpDisplayedColumns[j].getId() == UserColumnFactory.NAME
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.LINUP
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.BEST_POSITION
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.GROUP)
					m_clData[i][j] = ((PlayerColumn)tmpDisplayedColumns[j]).getTableEntry(aktuellerSpieler,null);
			}
        }   
    }
    
    /**
     * Entfernt den Spieler aus der Tabelle
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public final void removeSpieler(Spieler player) {
    	m_vPlayers.remove(player);
        initData();
    }
}
