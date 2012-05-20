package ho.core.constants;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;

public final class TrainingType {
	public static final int SET_PIECES 			= 2;
	public static final int DEFENDING 			= 3;
	public static final int SCORING 			= 4;
	public static final int CROSSING_WINGER 	= 5;
	public static final int SHOOTING 			= 6;
	public static final int SHORT_PASSES 		= 7;
	public static final int PLAYMAKING 			= 8;
	public static final int GOALKEEPING 		= 9;
	public static final int THROUGH_PASSES 		= 10;
	public static final int DEF_POSITIONS 		= 11;
    public static final int WING_ATTACKS 		= 12;
    
    public static CBItem[] ITEMS = { 
		new CBItem(HOVerwaltung.instance().getLanguageString("training.set_pieces"), SET_PIECES),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.defending"), DEFENDING),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.scoring"), SCORING),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.crossing"), CROSSING_WINGER),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.shooting"), SHOOTING),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.short_passes"), SHORT_PASSES),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.playmaking"), PLAYMAKING),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.goalkeeping"), GOALKEEPING),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.through_passes"), THROUGH_PASSES),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.defensive_positions"), DEF_POSITIONS),
		new CBItem(HOVerwaltung.instance().getLanguageString("training.wing_attacks"), WING_ATTACKS)
	};
    
    private TrainingType(){};
    
    public static String toString(int trainingType){
    	if(trainingType >= SET_PIECES && trainingType <= WING_ATTACKS)
    		return ITEMS[trainingType-SET_PIECES].getText();
    	else
    		return HOVerwaltung.instance().getLanguageString("Unbestimmt");
    }
}
