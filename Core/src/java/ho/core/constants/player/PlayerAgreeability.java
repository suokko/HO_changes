package ho.core.constants.player;

import ho.core.model.HOVerwaltung;

public final class PlayerAgreeability {
	private static final String[] languageKeys = {"nasty","controversial","pleasant","sympathetic","popular","beloved_team_member"};
	public static final int NASTY_FELLOW 			= 0;
	public static final int CONTROVERSIAL_PERSON 	= 1;
	public static final int PLEASANT_GUY 			= 2;
	public static final int SYMPATHETIC_GUY 		= 3;
	public static final int POPULAR_GUY 			= 4;
	public static final int BELOVED_TEAM_MEMBER 	= 5;

	private PlayerAgreeability(){};
	
	
	public static String toString(int agreeability){
		if(agreeability >= NASTY_FELLOW && agreeability <= BELOVED_TEAM_MEMBER)
			return HOVerwaltung.instance().getLanguageString(languageKeys[agreeability]);
		return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}
	
	
}
