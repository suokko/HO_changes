package ho.core.constants.player;

import ho.core.model.HOVerwaltung;

public final class PlayerHonesty {
	private static final String[] languageKeys = {"infamous","dishonest","honest","upright","righteous","saint_like"};

	public static final int INFAMOUS 	= 0;
	public static final int DISHONEST 	= 1;
	public static final int HONEST 		= 2;
	public static final int UPRIGHT 	= 3;
	public static final int RIGHTEOUS 	= 4;
	public static final int SAINTLY 	= 5;

	private PlayerHonesty(){};
	
	public static String toString(int honesty){
		if(honesty >= INFAMOUS && honesty <= SAINTLY)
			return HOVerwaltung.instance().getLanguageString(languageKeys[honesty]);
		return HOVerwaltung.instance().getLanguageString("Unbestimmt");
	}

}
