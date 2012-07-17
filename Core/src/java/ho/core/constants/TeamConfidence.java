package ho.core.constants;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;

public class TeamConfidence {

	public static final int NON_EXISTENT 			= 0;
	public static final int DISASTROUS 				= 1;
	public static final int WRETCHED 				= 2;
	public static final int POOR 					= 3;
	public static final int DECENT 					= 4;
	public static final int STRONG 					= 5;
	public static final int WONDERFUL 				= 6;
	public static final int SLIGHTLY_EXAGGERATED 	= 7;
	public static final int EXAGGERATED 			= 8;
	public static final int COMPLETELY_EXAGGERATED 	= 9;

	public static CBItem[] ITEMS = {
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.non-existent"), NON_EXISTENT),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.disastrous"), DISASTROUS),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.wretched"), WRETCHED),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.poor"), POOR),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.decent"), DECENT),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.strong"), STRONG),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.wonderful"), WONDERFUL),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.slightlyexaggerated"), SLIGHTLY_EXAGGERATED),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.exaggerated"), EXAGGERATED),
		new CBItem(HOVerwaltung.instance().getLanguageString("ls.team.teamconfidence.completelyexaggerated"), COMPLETELY_EXAGGERATED)
	};


	public static String toString(int teamConfidence){
		if(teamConfidence >= NON_EXISTENT && teamConfidence <= COMPLETELY_EXAGGERATED)
			return ITEMS[teamConfidence].getText();
		else
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}

}
