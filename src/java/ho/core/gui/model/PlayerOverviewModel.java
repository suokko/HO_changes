package ho.core.gui.model;

import ho.core.gui.comp.table.HOColumnModel;
import ho.core.gui.comp.table.UserColumn;
import ho.core.model.Spieler;
import ho.module.playerOverview.SpielerTrainingsVergleichsPanel;

import java.util.Vector;

import plugins.ISpieler;


/**
 * 
 * @author Thorsten Dietz
 * @since 1.36
 */
public final  class PlayerOverviewModel extends HOColumnModel {
	
	private static final long serialVersionUID = 5149408240369536138L;
	
	/** all players **/
	private Vector<ISpieler> m_vPlayers;
	
	/**
	 * constructor
	 *
	 */
	protected PlayerOverviewModel(int id ){
		super(id,"Spieleruebersicht");
		initialize();
	}
	
	/**
	 * initialize all columns
	 *
	 */
	private void initialize() {
		UserColumn[] basic =  UserColumnFactory.createPlayerBasicArray();
		columns = new UserColumn[49];
		columns[0] =basic[0];
		columns[48] = basic[1];
		
		UserColumn[] skills =  UserColumnFactory.createPlayerSkillArray();
		for (int i = 9; i < skills.length+9; i++) {
			columns[i] = skills[i-9];
		}
		
		UserColumn[] positions =  UserColumnFactory.createPlayerPositionArray();
		for (int i = 21; i < positions.length+21; i++) {
			columns[i] = positions[i-21];
		}
		
		UserColumn[] goals =  UserColumnFactory.createGoalsColumnsArray();
		for (int i = 41; i < goals.length+41; i++) {
			columns[i] = goals[i-41];
		}
		UserColumn[] add =  UserColumnFactory.createPlayerAdditionalArray();
		columns[1] = add[0];
		columns[2] = add[1];
		columns[3] = add[2];
		columns[4] = add[3];
		columns[5] = add[4];
		columns[6] = add[5];
		columns[7] = add[6];
		columns[8] = add[11]; // Motherclub
		columns[45] = add[7];
		columns[46] = add[8];
		columns[40] = add[9];
		columns[47] = add[10];
	}
	
	/**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spieler getSpieler(int id) {
        //Kann < 0 sein für TempSpieler if ( id > 0 )
        if (id != 0) {
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
    public final void setValues(Vector<ISpieler> player) {
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
     * Passt alle Spalten an, die Verändungen bei einem HRF-Vergleich anzeigen
     */
    public final void reInitDataHRFVergleich() {
        initData();
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
    
    /**
     * Gibt den Spieler mit der gleichen ID, wie die übergebene, zurück, oder null
     *
     * @param vorlage TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Spieler getVergleichsSpieler(Spieler vorlage) {
        final int id = vorlage.getSpielerID();

        for (int i = 0;
             (SpielerTrainingsVergleichsPanel.getVergleichsSpieler() != null)
             && (i < SpielerTrainingsVergleichsPanel.getVergleichsSpieler().size()); i++) {
            final Spieler vergleichsSpieler = (Spieler) SpielerTrainingsVergleichsPanel.getVergleichsSpieler()
                                                                                       .get(i);

            if (vergleichsSpieler.getSpielerID() == id) {
                //Treffer
                return vergleichsSpieler;
            }
        }

        if (SpielerTrainingsVergleichsPanel.isVergleichsMarkierung()) {
            return getVergleichsSpielerFirstHRF(vorlage);
        }

        return null;
    }
    
    /**
     * Gibt den Spieler aus dem ersten HRF, wo der Spieler aufgetauch ist, zurück
     *
     * @param vorlage TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Spieler getVergleichsSpielerFirstHRF(Spieler vorlage) {
        return ho.core.db.DBManager.instance().getSpielerFirstHRF(vorlage
                                                                                     .getSpielerID());
    }
    
//  -----initialisierung-----------------------------------------

    /**
     * create a data[][] from player-Vector
     */
    @Override
	protected void initData() {
    	UserColumn [] tmpDisplayedColumns = getDisplayedColumns();
    	m_clData = new Object[m_vPlayers.size()][tmpDisplayedColumns.length];
    	
    	for (int i = 0; i < m_vPlayers.size(); i++) {
    		final Spieler aktuellerSpieler = (Spieler) m_vPlayers.get(i);
    		final Spieler vergleichsSpieler = getVergleichsSpieler(aktuellerSpieler);
    		
    		for (int j = 0; j < tmpDisplayedColumns.length; j++) {
    			m_clData[i][j] = ((PlayerColumn)tmpDisplayedColumns[j]).getTableEntry(aktuellerSpieler,vergleichsSpieler);
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
}
