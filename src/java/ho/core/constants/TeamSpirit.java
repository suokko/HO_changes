package ho.core.constants;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;

public final class TeamSpirit {

	public static final int LIKE_THE_COLD_WAR 	= 0;
	public static final int MURDEROUS 			= 1;
	public static final int FURIOUS 			= 2;
	public static final int IRRITATED 			= 3;
	public static final int COMPOSED 			= 4;
	public static final int CALM 				= 5;
	public static final int CONTENT 			= 6;
	public static final int SATISFIED 			= 7;
	public static final int DELIRIOUS 			= 8;
	public static final int WALKING_ON_CLOUDS 	= 9;
	public static final int PARADISE_ON_EARTH 	= 10;

	public static CBItem[] ITEMS = {
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_like_the_cold_War"),	LIKE_THE_COLD_WAR),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_murderous"),	MURDEROUS),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_furious"),	FURIOUS),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_irritated"),	IRRITATED),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_composed"), COMPOSED),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_calm"), CALM),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_content"), CONTENT),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_satisfied"),	SATISFIED),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_delirious"),	DELIRIOUS),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_Walking_on_Clouds"),	WALKING_ON_CLOUDS),
			new CBItem(HOVerwaltung.instance().getLanguageString("ts_Paradise_on_Earth"),	PARADISE_ON_EARTH) };

	private TeamSpirit() {
	};

	
	public static String toString(int teamSpirit){
		if(teamSpirit >= LIKE_THE_COLD_WAR && teamSpirit <= PARADISE_ON_EARTH)
			return ITEMS[teamSpirit].getText();
		else
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}
}
