package ho.core.constants.player;

import ho.core.model.HOVerwaltung;


public final class PlayerSkill {

	private static final String[] languageKeys = {
		"ls.player.skill.keeper","ls.player.skill.defending","ls.player.skill.winger","ls.player.skill.playmaking","ls.player.skill.scoring","ls.player.skill.passing","ls.player.skill.stamina","Form","ls.player.skill.setpieces","skill.experience","Fuehrung","Loyalty"
	};

	public static final int KEEPER = 0;
	public static final int DEFENDING = 1;
	public static final int WINGER = 2;
	public static final int PLAYMAKING = 3;
	public static final int SCORING = 4;
	public static final int PASSING = 5;
	public static final int STAMINA = 6;
	public static final int FORM = 7;
	public static final int SET_PIECES = 8;
	public static final int EXPERIENCE = 9;
	public static final int LEADERSHIP = 10;
	public static final int LOYALTY = 11;

    private PlayerSkill(){};

    public static String toString(int skill){
    	if( skill >= KEEPER && skill <= LOYALTY)
			return HOVerwaltung.instance().getLanguageString(languageKeys[skill]);
		else
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
    }
}
