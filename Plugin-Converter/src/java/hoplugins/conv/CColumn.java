/*
 * Created on 10.01.2005
 *
 */
package hoplugins.conv;

import plugins.ISpieler;

/**
 * @author Thorsten Dietz
 *
 */
final class CColumn {
	private int index;
	private String display;
	private String defaultValue;
	
	protected CColumn(int newIndex, String newDisplay){
		index = newIndex;
		display = newDisplay;
		defaultValue = newDisplay;
	}
	protected CColumn(int newIndex, String newDisplay, String newDefaultValue){
		index = newIndex;
		display = newDisplay;
		defaultValue = newDefaultValue;
	}
	protected final String getDefaultValue() {
		return defaultValue;
	}

	protected final String getDisplay() {
		return display;
	}

	protected final int getIndex() {
		return index;
	}

	public String toString(){
		return display;
	}
	
	protected final String getPlayerValue(ISpieler spieler) {
		String tmp = "";
		switch (getIndex()) {
		case RSC.PLAYER_ID:
			tmp = "" + spieler.getSpielerID();
			break;
		case RSC.PLAYER_NAME:
			tmp = spieler.getName();
			break;
		case RSC.PLAYER_AGE:
			tmp = "" + spieler.getAlter();
			break;
		case RSC.PLAYER_SALARY:
			tmp = "" + spieler.getGehalt();
			break;
		case RSC.PLAYER_VERLETZT:
			tmp = "" + spieler.getVerletzt();
			break;

		case RSC.PLAYER_LEADERSHIP:
			tmp = "" + spieler.getFuehrung();
			break;
		case RSC.PLAYER_FORM:
			tmp = "" + spieler.getForm();
			break;
		case RSC.PLAYER_STAMINA:
			tmp = "" + spieler.getKondition();
			break;
		case RSC.PLAYER_EXPERIENCE:
			tmp = "" + spieler.getErfahrung();
			break;
		case RSC.PLAYER_KEEPER:
			tmp = "" + spieler.getTorwart();
			break;
		case RSC.PLAYER_DEFENCE:
			tmp = "" + spieler.getVerteidigung();
			break;
		case RSC.PLAYER_WING:
			tmp = "" + spieler.getFluegelspiel();
			break;
		case RSC.PLAYER_PLAYMAKING:
			tmp = "" + spieler.getSpielaufbau();
			break;
		case RSC.PLAYER_PASSING:
			tmp = "" + spieler.getPasspiel();
			break;
		case RSC.PLAYER_SCORING:
			tmp = "" + spieler.getTorschuss();
			break;
		case RSC.PLAYER_CHARACTER:
			tmp = spieler.getCharakterString();
			break;
		case RSC.PLAYER_GENTLESS:
			tmp = spieler.getAnsehenString();
			break;
		case RSC.PLAYER_SPECIAL:
			tmp = spieler.getSpezialitaetString();
			break;
		case RSC.PLAYER_NOTE:
			tmp = spieler.getNotiz();
			break;
		case RSC.PLAYER_NATIONALITY:
			tmp = ""+spieler.getNationalitaet();
			break;

		}
		return tmp;
	}
}
