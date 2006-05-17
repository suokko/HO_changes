// %2979723196:de.hattrickorganizer.logik%
/*
 * ratingPrediction.java
 *
 * Created on 20. November 2004, 11:31
 */
package de.hattrickorganizer.logik;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;
//import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Team;
import de.hattrickorganizer.tools.HOLogger;

/**
 * DOCUMENT ME!
 *
 * @author TheTom
 */
public class ratingPredictionManager {
	//~ Instance fields ----------------------------------------------------------------------------

	public final static Date LAST_CHANGE = new GregorianCalendar(2006, 0, 6).getTime();

	private float m_fErfahrung = 0.428f;
	private float m_fForm = 0.504f;
	private float m_fKF = 0.461f;
	private short m_sHome;
	private short m_sPM;
	private short m_sSelbstvertrauen;
	private short m_sStimmung;
	private short m_sSubStimmung;
	private short m_sTacticTyp;
	private short m_sTrainerTyp;
	private static RatingPredictionConfig config = RatingPredictionConfig.getInstance();
	private Aufstellung lineup;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new instance of ratingPrediction for historical simulation
	 */
	public ratingPredictionManager(Aufstellung lineup, int hrfId) {
		this.lineup = lineup;
		initBasics(hrfId);
	}

	/**
	 * Creates a new instance of ratingPrediction
	 */
	public ratingPredictionManager(Aufstellung lineup) {
		this(lineup, 0);
	}

	//~ Methods ------------------------------------------------------------------------------------

	///////////////////////////////////////////////////////////////////////////////
	//Taktiken
	/////////////////////////////////////////////////////////////////////////////
	public final float getAow_AimRatings() {
		float psOut = 0.0f;
		float zPsOut = 0.0f;
		float rating = 0.0f;

		/* now also based on extended saison 25 statistics
		   FCN=   262.5080     FROM MINOS     STATUS=SUCCESSFUL   230 CALLS      307 TOTAL
		                EDM=  0.21E-03    STRATEGY= 1      ERROR MATRIX ACCURATE
		   EXT PARAMETER                  PARABOLIC         MINOS ERRORS
		   NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
		    1      P1       0.20113       0.41050E-02  -0.37607E-02   0.37760E-02
		    2      P2        1.0275       0.40495E-01  -0.38559E-01   0.38957E-01
		    3      P3        1.3532       0.13456      -0.12572       0.12523
		   CHISQUARE = 0.8579E+00  NPFIT =   309
		 */
		final float P1 = 0.20113f;
		final float P2 = 1.0275f;
		final float P3 = 1.3532f;

		for (int i = ISpielerPosition.rightBack; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//Zus Player
				if ((tactic == ISpielerPosition.ZUS_INNENV)
					|| (tactic == ISpielerPosition.ZUS_MITTELFELD)
					|| (tactic == ISpielerPosition.ZUS_STUERMER)) {
					zPsOut += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);

					//norm Player
				} else {
					psOut += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}
			}
		}

		// passing of all outfielders
		rating = (P1 * (psOut + (P2 * zPsOut))) + P3;

		return rating;
	}

	/*
	   private float getEffectivePA( int erf, float skill, int form )
	    {
	        float   skillEff   =   0.0f;
	        float   ff      =   0.0f;
	        float   eb      =   0.0f;
	
	        eb      =   (float) (2.0f*m_fErfahrung* (Math.log( erf )/Math.log(10)));
	        ff      =   (float) Math.pow( ( form -1 ) / 7.0f, m_fForm*7.0f/ 8.0f );
	        skillEff   =   ff  * skill + eb;
	
	        return skillEff;
	    }
	 */
	public final float getCentralAttackRatings() {
		float st = 0.0f;
		float stDef = 0.0f;
		float stNa = 0.0f;
		float zSt = 0.0f;
		float stPa = 0.0f;
		float stDefPa = 0.0f;
		float stNaPa = 0.0f;
		float zStPa = 0.0f;
		float im = 0.0f;
		float imOff = 0.0f;
		float imDef = 0.0f;
		float zIm = 0.0f;
		float midAtt = 0.0f;

		RatingPredictionParameter params = config.getCentralAttackParameters();
		final float P1 = params.getParam(1);
		final float P2 = params.getParam(2);
		final float P3 = params.getParam(3);
		final float P4 = params.getParam(4);
		final float P5 = params.getParam(5);
		final float P6 = params.getParam(6);
		// malus for defensive trainer
		final float P7 = params.getParam(7);
		final float P8 = params.getParam(8);
		final float P9 = params.getParam(9);
		// bonus for offensive trainer
		final float P10 = params.getParam(10);
		// overall non-linearity in rating dependencies
		final float P11 = params.getParam(11);
		// reduced scoring skill influence of forward towards wing!
		// JUST A GUESS! --> HALF WAY between normal and defensive scorer... ;-)
		// NO, after first match seems to be even lower than def. FW!!! ==> YES, it's 0.5!
		final float P12 = params.getParam(12);
		//STdefPa parameters 
		final float P13 = params.getParam(13);
		final float P14 = params.getParam(14);

		// filter positions which contribute to rating
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//st
				if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
					&& (tactic == ISpielerPosition.NORMAL)) {
					st += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
					stPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//ST_DEV
				if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
					// bonus for technical?
					if (player.getSpezialitaet() == ISpieler.BALLZAUBERER)
						stDefPa += (P14 * getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL));
					else
						stDefPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}
				//ST_nA (winger skill NOT important for central attack)
				if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
					&& (tactic == ISpielerPosition.NACH_AUSSEN)) {
					stNa += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
					stNaPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//zus ST
				if (tactic == ISpielerPosition.ZUS_STUERMER) {
					zSt += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
					zStPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//Inner MF + IMnA
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& ((tactic == ISpielerPosition.NORMAL)
						|| (tactic == ISpielerPosition.NACH_AUSSEN))) {
					im += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//Zus MF
				if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
					zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//Im OFF
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//IM-Def
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}
			}
		}

		//Torschuss ST
		midAtt = (st + (P2 * zSt) + (P3 * stDef) + (P12 * stNa));

		//Passing ST (STdef/STnA have same influence of passing than normal ST, like in the rules!!!)
		midAtt += (P4 * (stPa + (P2 * zStPa) + P13 * stDefPa + stNaPa));

		//MF Passing
		midAtt += (P5 * (im + ((1 + P6) * imOff) + ((1 - P6) * imDef) + (P2 * zIm)));

		//Trainer + Selbstvertrauen
		if (m_sTrainerTyp == 1) {
			midAtt *= (1 + P10);
		} else if (m_sTrainerTyp == 0) {
			midAtt *= (1 - P7);
		}
		midAtt *= (P1 * (1 + (P8 * (m_sSelbstvertrauen - 5))));

		// non-linearity
		midAtt *= (1 + P11 * midAtt);

		midAtt += P9;

		return midAtt;
	}

	/////////////////////////////////////////////////////////////////
	//central Defense
	/////////////////////////////////////////////////////////////////

	public final float getCentralDefenseRatings() {
		float iv = 0.0f;
		float ivOff = 0.0f;
		float ivNa = 0.0f;
		float tw = 0.0f;
		float av = 0.0f;
		float avDef = 0.0f;
		float avOff = 0.0f;
		float avZm = 0.0f;
		float im = 0.0f;
		float zIm = 0.0f;
		float imOff = 0.0f;
		float imDef = 0.0f;
		float flZm = 0.0f;
		float fl = 0.0f;
		float flOff = 0.0f;
		float flDef = 0.0f;
		float zIv = 0.0f;
		float rating = 0.0f;

		RatingPredictionParameter params = config.getCentralDefenseParameters();
		final float P1 = params.getParam(1);
		final float P2 = params.getParam(2);
		final float P3 = params.getParam(3);
		final float P4 = params.getParam(4);
		final float P5 = params.getParam(5);
		final float P6 = params.getParam(6);
		final float P7 = params.getParam(7);
		final float P8 = params.getParam(8);
		final float P9 = params.getParam(9);
		final float P10 = params.getParam(10);
		final float P11 = params.getParam(11);
		final float P12 = params.getParam(12);
		final float P13 = params.getParam(13);
		// bonus for defensive trainer
		final float P14 = params.getParam(14);
		final float P15 = params.getParam(15);
		final float P16 = params.getParam(16);
		// malus for offensive trainer
		final float P17 = params.getParam(17);

		// estimated from 11 matches... HT rating is (-5.68+-0.38)% lower than HO prediction if "PlayingCreatively"!
		final float P18 = params.getParam(18);

		// overall non-linearity in rating dependencies
		final float P19 = params.getParam(19);

		// AVdef parameter
		final float P20 = params.getParam(20);

		// filter positions wich contribute to rating
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//TW                
				//tw
				if (i == ISpielerPosition.keeper) {
					tw += getEffectiveSkillvalue(player, ISpieler.SKILL_TORWART);
				}

				//IV                
				//iv
				if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
					&& (tactic == ISpielerPosition.NORMAL)) {
					iv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//ivOff
				if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//ivnA
				if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
					&& (tactic == ISpielerPosition.NACH_AUSSEN)) {
					ivNa += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//Zus iv
				if (tactic == ISpielerPosition.ZUS_INNENV) {
					zIv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//AV                
				//av
				if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
					&& (tactic == ISpielerPosition.NORMAL)) {
					av += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//avOff
				if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//avZm
				if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
					&& (tactic == ISpielerPosition.ZUR_MITTE)) {
					avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//av Def
				if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//FL                
				//fl
				if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
					&& (tactic == ISpielerPosition.NORMAL)) {
					fl += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//flOff
				if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//flZm
				if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
					&& (tactic == ISpielerPosition.ZUR_MITTE)) {
					flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//fl def
				if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//MF                
				//Inner MF + IMnA
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& ((tactic == ISpielerPosition.NORMAL)
						|| (tactic == ISpielerPosition.NACH_AUSSEN))) {
					im += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//Zus MF
				if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
					zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//Im OFF
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}

				//IM-Def
				if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
				}
			}
		}

		//TW+IV
		//rating = tw + (1.08*P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (1.1f*P5 * ivNa)));
		rating = tw + (P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (P5 * ivNa)));

		//AV (use 2 indep. def/off parameters!!!)
		//rating += (P6 * (av + (0.8f*(1 + P7) * avDef) + ((1 - P7) * avOff) + (1.2f*P8 * avZm)));
		rating += (P6 * (av + (P20 * avDef) + (P7 * avOff) + (P8 * avZm)));

		//IM
		//rating += (1.1f*P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));
		rating += (P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));

		//FL
		rating += (P11 * (fl + ((1 + P12) * flDef) + ((1 - P12) * flOff) + (P13 * flZm)));

		//Trainer def/off ==> increase/decrease rating
		if (m_sTrainerTyp == 1) {
			rating *= (P1 * (1 - P17));
		} else if (m_sTrainerTyp == 0) {
			rating *= (P1 * (1 + P14));
		} else {
			rating *= P1;
		}

		// AoW? ==> reduce rating...
		if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_WINGS) {
			rating *= (1 - P15);
		}

		// non-linearity
		rating *= (1 + P19 * rating);

		rating += P16;

		// playing creative
		// reduce rating in the END because its estimated independently as (HO-HT)/HO
		if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_CREATIVE) {
			rating *= (1 - P18);
		}

		return rating;
	}



	/**
	 * eingehen tun wieder die korrigierten/redusierten VE und PS skillsummen der verteidiger
	 * (incl. subskills):
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final float getKonterRatings() {
		float veDef = 0.0f;
		float zVeDef = 0.0f;
		float psDef = 0.0f;
		float zPsDef = 0.0f;
		float rating = 0.0f;

		/* now also based on extended saison 25 statistics!
		   FCN=   112.3612     FROM MINOS     STATUS=SUCCESSFUL   253 CALLS      314 TOTAL
		                EDM=  0.72E-03    STRATEGY= 1      ERROR MATRIX ACCURATE
		   EXT PARAMETER                  PARABOLIC         MINOS ERRORS
		   NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
		    1      P1       0.11558       0.42441E-02  -0.29957E-02   0.29960E-02
		    2      P2        1.2267       0.85305E-01  -0.66677E-01   0.69692E-01
		    3      P3        1.1799       0.18278      -0.13134       0.13114
		   CHISQUARE = 0.9604E+00  NPFIT =   120
		 */
		final float P1 = 0.11558f;
		final float P2 = 1.2267f;
		final float P3 = 1.1799f;

		for (int i = ISpielerPosition.rightBack; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//Verteidiger
				if (((i == ISpielerPosition.insideBack1)
					|| (i == ISpielerPosition.insideBack2)
					|| (i == ISpielerPosition.leftBack)
					|| (i == ISpielerPosition.rightBack))
					&& (tactic < ISpielerPosition.ZUS_STUERMER)) {
					veDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					psDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}

				//zIv
				if (tactic == ISpielerPosition.ZUS_INNENV) {
					zVeDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					zPsDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
				}
			}
		}

		//Regel konform (passing counts twice!)
		rating = (P1 * (veDef + (P2 * zVeDef) + (2 * (psDef + (P2 * zPsDef))))) + P3;

		return rating;
	}

	/////////////////////////////////////////////////////////////////
	//Side Attacks
	/////////////////////////////////////////////////////////////////
	public final float getRightAttackRatings() {
		// just for backward compatibility!
		return getSideAttackRatings("Right");
	}
	public final float getLeftAttackRatings() {
		// just for backward compatibility!
		return getSideAttackRatings("Left");
	}
	public final float getSideAttackRatings(String Side) {

		// NOW calculation of Right/1 & Left/2 Attack Ratings are merged!
		int ISP_forward;
		int ISP_forward_otherside;
		int ISP_insideMid;
		int ISP_insideMid_otherside;
		int ISP_winger;
		int ISP_wingBack;
		int ISP_insideBack;
		if (Side == "Right") {
			ISP_forward = ISpielerPosition.forward1;
			ISP_forward_otherside = ISpielerPosition.forward2;
			ISP_insideMid = ISpielerPosition.insideMid1;
			ISP_insideMid_otherside = ISpielerPosition.insideMid2;
			ISP_winger = ISpielerPosition.rightWinger;
			ISP_wingBack = ISpielerPosition.rightBack;
			ISP_insideBack = ISpielerPosition.insideBack1;
		} else if (Side == "Left") {
			ISP_forward = ISpielerPosition.forward2;
			ISP_forward_otherside = ISpielerPosition.forward1;
			ISP_insideMid = ISpielerPosition.insideMid2;
			ISP_insideMid_otherside = ISpielerPosition.insideMid1;
			ISP_winger = ISpielerPosition.leftWinger;
			ISP_wingBack = ISpielerPosition.leftBack;
			ISP_insideBack = ISpielerPosition.insideBack2;
		} else {
			// some error condition?  
			return 0.0f;
		}

		//st's
		float st = 0.0f;
		float stDef = 0.0f;
		float stDefPa = 0.0f;
		float stNa = 0.0f;
		float stNa_otherside = 0.0f;
		float stNaF = 0.0f;
		float zSt = 0.0f;

		//fl's
		float fl = 0.0f;
		float flOff = 0.0f;
		float flDef = 0.0f;
		float flZm = 0.0f;
		float flPa = 0.0f;
		float flOffPa = 0.0f;
		float flDefPa = 0.0f;
		float flZmPa = 0.0f;

		//im's
		float im = 0.0f;
		float imOff = 0.0f;
		float imDef = 0.0f;
		float im_otherside = 0.0f;
		float imOff_otherside = 0.0f;
		float imDef_otherside = 0.0f;
		float zIm = 0.0f;
		float imNa = 0.0f;
		float imNaF = 0.0f;

		//av's
		float av = 0.0f;
		float avOff = 0.0f;
		float avDef = 0.0f;
		float avZm = 0.0f;

		//iv's (I forgot this before,... but it was always in the rules!)
		float ivNaF = 0.0f;

		//rating
		float rating = 0.0f;

		RatingPredictionParameter params = config.getSideAttackParameters();
		float P1 = params.getParam(1);
		float P2 = params.getParam(2);
		float P3 = params.getParam(3);
		float P4 = params.getParam(4);
		final float P5 = params.getParam(5);
		final float P6 = params.getParam(6);
		final float P7 = params.getParam(7);
		final float P8 = params.getParam(8);
		final float P9 = params.getParam(9);
		final float P10 = params.getParam(10);
		final float P11 = params.getParam(11);
		final float P12 = params.getParam(12);
		final float P13 = params.getParam(13);
		final float P14 = params.getParam(14);
		final float P15 = params.getParam(15);
		final float P16 = params.getParam(16);
		final float P17 = params.getParam(17);
		final float P18 = params.getParam(18);
		final float P19 = params.getParam(19);
		final float P20 = params.getParam(20);
		final float P21 = params.getParam(21);
		final float P22 = params.getParam(22);
		// "small" STdefPa influence on wing attack ==> JUST A GUESS!
		final float P23 = params.getParam(23);

		//P1*=wing_increase; 
		//P2/=(wing_increase/scoring_decrease);
		//P3/=wing_increase;
		//P4/=wing_increase;

		//balanced is default
		float trainer = 0.0f;
		//Trainertyp umrechnen
		if (m_sTrainerTyp == 1) {
			//off
			trainer = P6;
		} else if (m_sTrainerTyp == 0) {
			//def
			trainer = -P7;
		}

		// filter positions which contribute to rating
		int numIm = 0;
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//forwards
				if ((i == ISP_forward) || (i == ISP_forward_otherside)) {
					//st normal
					if (tactic == ISpielerPosition.NORMAL) {
						st += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
					}
					//ST_DEV
					else if (tactic == ISpielerPosition.DEFENSIV) {
						stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
						// is Passing of technical STdef really not dependent on side?
						if (player.getSpezialitaet() == ISpieler.BALLZAUBERER)
							stDefPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
						else
							stDefPa += 0.0f;
					} else if (tactic == ISpielerPosition.NACH_AUSSEN) {
						//ST_nA 
						if (i == ISP_forward) {
							stNa = getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
							stNaF = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
						}
						// what about the scoring skill of the ST_nA from the other side???
						else if (i == ISP_forward_otherside) {
							stNa_otherside =
								getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
						}
					}
				}
				//zus ST
				else if (tactic == ISpielerPosition.ZUS_STUERMER) {
					zSt += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
				}

				//IVnA (the forgotten position...;-))
				if ((i == ISP_insideBack) && (tactic == ISpielerPosition.NACH_AUSSEN)) {
					ivNaF = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
				}

				// IMs
				if (i == ISP_insideMid) {
					//normal Inner MF 
					if (tactic == ISpielerPosition.NORMAL) {
						numIm++;
						im = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//Inner MF off
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIm++;
						imOff = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//IM-Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						numIm++;
						imDef = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//IMnA (this position will NOT be centralized due to HT-Tjiecken!)
					else if (tactic == ISpielerPosition.NACH_AUSSEN) {
						numIm++;
						imNa = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
						imNaF = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
					}
				} else if (i == ISP_insideMid_otherside) {
					// IMs from OTHER side needed if centralization!
					//normal Inner MF 
					if (tactic == ISpielerPosition.NORMAL) {
						numIm++;
						im_otherside = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//Inner MF off
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIm++;
						imOff_otherside = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//IM-Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						numIm++;
						imDef_otherside = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
				} else
					//Zus MF (contributes always to both sides half!)
					if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
						numIm++;
						zIm += (0.5f * getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL));
					}

				//Wingers
				if (i == ISP_winger) {
					// normal FL  
					if (tactic == ISpielerPosition.NORMAL) {
						fl = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
						flPa = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//FL DEF
					else if (tactic == ISpielerPosition.DEFENSIV) {
						flDef = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
						flDefPa = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//FL OFF
					else if (tactic == ISpielerPosition.OFFENSIV) {
						flOff = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
						flOffPa = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
					//Fl Zur Mitt
					else if (tactic == ISpielerPosition.ZUR_MITTE) {
						flZm = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
						flZmPa = getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
					}
				}

				// WingBacks
				if (i == ISP_wingBack) {
					// normal av
					if (tactic == ISpielerPosition.NORMAL) {
						av = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
					}
					//avOff
					else if (tactic == ISpielerPosition.OFFENSIV) {
						avOff = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
					}
					//avZm
					else if (tactic == ISpielerPosition.ZUR_MITTE) {
						avZm = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
					}
					//av Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						avDef = getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
					}
				}

			}
		}

		// centralization of inner midfielders?
		if (numIm == 1) {
			// just add up, even if only one (or none) of them is >0 (no need for all those if's)!  
			im = 0.5f * (im + im_otherside);
			imOff = 0.5f * (imOff + imOff_otherside);
			imDef = 0.5f * (imDef + imDef_otherside);
		}

		//flWinger
		rating = (fl + ((1 + P10) * flOff) + ((1 - P10) * flDef) + (P11 * flZm));
		// 10'2005: according to HT-Bjoern ALL positons playing towards wing NEED winger skill!
		// ==> recalc parameters with winger skill for IMnA (again) AND IVnA (new) AND STnA (of course)!!!
		//     (maybe passing of IMnAs should now not count more than for normal IMs (old P11=1.0f)?
		rating += ((P18 * imNaF) + (P19 * ivNaF) + (P20 * stNaF));

		//Torschuss ST
		rating += (P2 * (st + (P12 * zSt) + (P13 * stDef) + (P21 * stNa) + (P22 * stNa_otherside)));
		// JUST A GUESS: passing influence of technical def. forward should be smaller than winger/midfield? 
		rating += (P23 * stDefPa);

		// FL passing (individual influence of diff. wingers? ==> was never really significant (1 sigma))
		//rating += (P3 * (flPa + ((1 + P10) * flOffPa) + ((1 - P10) * flDefPa) + (P12 * flZmPa)));
		rating += (P3 * (flPa + flOffPa + flDefPa + flZmPa));

		//MF Passing (zIM same as zST!!!)
		rating += (P4 * (im + ((1 + P14) * imOff) + ((1 - P14) * imDef) + (P12 * zIm) + imNa));

		//av Winger
		rating += (P5 * (av + ((1 + P15) * avOff) + Math.max(0, (1 - P15) * avDef) + (P17 * avZm)));

		//trainer + sv
		rating = (P1 * rating * (1 + trainer) * (1 + (P8 * (m_sSelbstvertrauen - 5))));

		// non-linearity
		rating *= (1 + P16 * rating);

		rating += P9;

		return rating;
	}

	/////////////////////////////////////////////////////////////////
	//Side Defenses
	/////////////////////////////////////////////////////////////////
	public final float getRightDefenseRatings() {
		// just for backward compatibility!
		return getSideDefenseRatings("Right");
	}
	public final float getLeftDefenseRatings() {
		// just for backward compatibility!
		return getSideDefenseRatings("Left");
	}
	public final float getSideDefenseRatings(String Side) {

		// NOW calculation of Right/1 & Left/2 Defense Ratings are merged!
		int ISP_insideMid;
		int ISP_insideMid_otherside;
		int ISP_winger;
		int ISP_wingBack;
		int ISP_insideBack;
		int ISP_insideBack_otherside;
		if (Side == "Right") {
			ISP_insideMid = ISpielerPosition.insideMid1;
			ISP_insideMid_otherside = ISpielerPosition.insideMid2;
			ISP_winger = ISpielerPosition.rightWinger;
			ISP_wingBack = ISpielerPosition.rightBack;
			ISP_insideBack = ISpielerPosition.insideBack1;
			ISP_insideBack_otherside = ISpielerPosition.insideBack2;
		} else if (Side == "Left") {
			ISP_insideMid = ISpielerPosition.insideMid2;
			ISP_insideMid_otherside = ISpielerPosition.insideMid1;
			ISP_winger = ISpielerPosition.leftWinger;
			ISP_wingBack = ISpielerPosition.leftBack;
			ISP_insideBack = ISpielerPosition.insideBack2;
			ISP_insideBack_otherside = ISpielerPosition.insideBack1;
		} else {
			// some error condition?  
			return 0.0f;
		}

		float iv = 0.0f;
		float ivOff = 0.0f;
		float iv_otherside = 0.0f;
		float ivOff_otherside = 0.0f;
		float ivNa = 0.0f;
		float tw = 0.0f;
		float av = 0.0f;
		float avDef = 0.0f;
		float avOff = 0.0f;
		float avZm = 0.0f;
		float im = 0.0f;
		float imOff = 0.0f;
		float imDef = 0.0f;
		float im_otherside = 0.0f;
		float imOff_otherside = 0.0f;
		float imDef_otherside = 0.0f;
		float imNa = 0.0f;
		float zIm = 0.0f;
		float flZm = 0.0f;
		float fl = 0.0f;
		float flOff = 0.0f;
		float flDef = 0.0f;
		float zIv = 0.0f;
		float rating = 0.0f;

		RatingPredictionParameter params = config.getSideDefenseParameters();
		final float P1 = params.getParam(1);
		final float P2 = params.getParam(2);
		final float P3 = params.getParam(3);
		final float P4 = params.getParam(4);
		final float P5 = params.getParam(5);
		final float P6 = params.getParam(6);
		final float P7 = params.getParam(7);
		final float P8 = params.getParam(8);
		final float P9 = params.getParam(9);
		final float P10 = params.getParam(10);
		final float P11 = params.getParam(11);
		final float P12 = params.getParam(12);
		final float P13 = params.getParam(13);
		// bonus for defensive trainer
		final float P14 = params.getParam(14);
		final float P15 = params.getParam(15);
		final float P16 = params.getParam(16);
		// malus for offensive trainer
		final float P17 = params.getParam(17);

		// estimated from 11 CP matches... HT rating is (-5.68+-0.38)% lower than HO prediction! ;-)
		final float P18 = params.getParam(18);

		// overall non-linearity in rating dependencies
		final float P19 = params.getParam(19);

		// new IMnA parameter
		final float P20 = params.getParam(20);
		// 2nd AVoff/def parameter
		final float P21 = params.getParam(21);

		// filter positions which contribute to rating
		int numIv = 0;
		int numIm = 0;
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {
				//TW                
				if (i == ISpielerPosition.keeper) {
					tw = getEffectiveSkillvalue(player, ISpieler.SKILL_TORWART);
				}

				// central defenders
				if (i == ISP_insideBack) {
					//normal IV                
					if (tactic == ISpielerPosition.NORMAL) {
						numIv++;
						iv = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//ivOff
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIv++;
						ivOff = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//ivnA
					else if (tactic == ISpielerPosition.NACH_AUSSEN) {
						numIv++;
						ivNa = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
				} else if (i == ISP_insideBack_otherside) {
					// central defenders of other side in case of centralization!
					//normal IV                
					if (tactic == ISpielerPosition.NORMAL) {
						numIv++;
						iv_otherside = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//ivOff
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIv++;
						ivOff_otherside =
							getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					// towards wing positions are NOT centralized (HT-Tjiecken)!
				} else
					//Zus iv (don't check side!!! repositioned players ALWAYS play central!)
					if (tactic == ISpielerPosition.ZUS_INNENV) {
						numIv++;
						zIv += (0.5f * getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG));
					}

				// wing backs
				if (i == ISP_wingBack) {
					//av
					if (tactic == ISpielerPosition.NORMAL) {
						av = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//avOff
					else if (tactic == ISpielerPosition.OFFENSIV) {
						avOff = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//avZm
					else if (tactic == ISpielerPosition.ZUR_MITTE) {
						avZm = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//av Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						avDef = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
				}

				// wingers
				if (i == ISP_winger) {
					//fl
					if (tactic == ISpielerPosition.NORMAL) {
						fl = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//flOff
					else if (tactic == ISpielerPosition.OFFENSIV) {
						flOff = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//flZm
					else if (tactic == ISpielerPosition.ZUR_MITTE) {
						flZm = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//fl def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						flDef = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
				}

				//inner midfielders
				if (i == ISP_insideMid) {
					//normal MF 
					if (tactic == ISpielerPosition.NORMAL) {
						numIm++;
						im = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//Im OFF
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIm++;
						imOff = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//IM-Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						numIm++;
						imDef = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//IMnA
					else if (tactic == ISpielerPosition.NACH_AUSSEN) {
						numIm++;
						imNa = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
				} else if (i == ISP_insideMid_otherside) {
					//normal MF 
					if (tactic == ISpielerPosition.NORMAL) {
						numIm++;
						im_otherside = getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//Im OFF
					else if (tactic == ISpielerPosition.OFFENSIV) {
						numIm++;
						imOff_otherside =
							getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					//IM-Def
					else if (tactic == ISpielerPosition.DEFENSIV) {
						numIm++;
						imDef_otherside =
							getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
					}
					// towards wing positions are NOT centralized (HT-Tjiecken)!
				} else
					//Zus MF (side doesn't matter anymore!!!)
					if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
						numIm++;
						zIm += (0.5f * getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG));
					}

			}
		}

		// centralization of inner midfielders?
		if (numIm == 1) {
			// just add up, even if only one (or none) of them is >0!  
			im = 0.5f * (im + im_otherside);
			imOff = 0.5f * (imOff + imOff_otherside);
			imDef = 0.5f * (imDef + imDef_otherside);
		}
		// centralization of central defenders?
		if (numIv == 1) {
			// just add up, even if only one (or none) of them is >0! 
			iv = 0.5f * (iv + iv_otherside);
			ivOff = 0.5f * (ivOff + ivOff_otherside);
		}

		//TW+IV
		//rating  =   P2 *  tw + iv  + P3 * zIv + P4 * ivOff + P5 * ivNa;
		//rating = 0.99f*tw + 1.01f*(0.92f*P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (0.9f*P5 * ivNa)));
		rating = tw + (P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (P5 * ivNa)));

		//AV
		//rating += (1.01f*P6 * (av + ((1 + P7) * avDef) + ((1 - P7) * avOff) + (P8 * avZm)));
		rating += (P6 * (av + ((1 + P7) * avDef) + ((1 - P7) * avOff) + (P8 * avZm)));

		//IM
		//rating += (0.7f*P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));
		//rating += (P9 * imNa);
		rating
			+= (P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm) + (P20 * imNa)));

		//FL (make 2 def/off parameters in next version!!!)
		//rating += (1.4*P11 * (fl + (1.3f/1.4*(1 + P12) * flDef) + ((1 - P12)/1.4 * flOff) + (P13/1.4 * flZm)));
		rating += (P11 * (fl + (P12 * flDef) + (P21 * flOff) + (P13 * flZm)));

		//Trainer def/off ==> increase/decrease rating
		if (m_sTrainerTyp == 1) {
			rating *= (P1 * (1 - P17));
		} else if (m_sTrainerTyp == 0) {
			rating *= (P1 * (1 + P14));
		} else {
			rating *= P1;
		}

		// AiM? ==> reduce rating...
		if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_MIDDLE) {
			rating *= (1 - P15);
		}

		// non-linearity
		rating *= (1 + P19 * rating);

		rating += P16;

		// playing creative
		// reduce rating in the END because its estimated indemendently as (HO-HT)/HO
		if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_CREATIVE) {
			rating *= (1 - P18);
		}

		return rating;
	}


	/*
	 *
	                   MFrating = P1 * (IM + P2 * zIM + P3 * FL) + P4 * HA + P5 * PM + P6*(ST-5) + P7
	
	                   IM,zIM,FL sind die Kondi/Form/Erfahrungskorrigierten und DANN aufaddierten
	                   Spielaufbauwerte auf diesen Positionen.
	
	                   HA ist Home(=1)/Away(=0), WOBEI HA=0 gesetzt ist wenn DERBY=1 (!!!)
	                   PM ist PIC/normal/MOTS (-1/0/1)
	                   ST ist die Stimmung (also ST-5, da dann symmetrich zu 0)
	 */
	public final float getMFRatings() {
		float im = 0.0f;
		float imNa = 0.0f;
		float zIM = 0.0f;
		float fl = 0.0f;
		float flZM = 0.0f;
		float ivOff = 0.0f;
		float avOff = 0.0f;
		float stDef = 0.0f;
		float rating = 0.0f;

		RatingPredictionParameter params = config.getMidfieldParameters();
		final float P1 = params.getParam(1);
		final float P2 = params.getParam(2);
		final float P3 = params.getParam(3);
		final float P4 = params.getParam(4);
		final float P5 = params.getParam(5);
		final float P6 = params.getParam(6);
		final float P7 = params.getParam(7);
		final float P8 = params.getParam(8);
		final float P9 = params.getParam(9);
		final float P10 = params.getParam(10);
		final float P11 = params.getParam(11);
		final float P12 = params.getParam(12);
		// non-linear MF enhancement for higher ratings!		
		final float P13 = params.getParam(13);

		// filter positions which contribute to MF-rating
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
			final ISpieler player = lineup.getPlayerByPositionID(i);
			final byte tactic = lineup.getTactic4PositionID(i);

			if (player != null) {

				// inner midfielders
				if ((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2)) {
					//Inner MF
					if (tactic < ISpielerPosition.NACH_AUSSEN) {
						im += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
					}
					//ImNa
					else if (tactic == ISpielerPosition.NACH_AUSSEN) {
						imNa += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
					}
				}
				//Zus MF
				else if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
					zIM += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
				}

				// wingers
				if ((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger)) {
					//FL
					if (tactic < ISpielerPosition.ZUR_MITTE) {
						fl += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
					}
					//Fl Zur Mitt
					else if (tactic == ISpielerPosition.ZUR_MITTE) {
						flZM += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
					}
				}

				//IvOFF
				if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
				}

				//AV-Off
				if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
					&& (tactic == ISpielerPosition.OFFENSIV)) {
					avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
				}

				//ST_DEV
				if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
					&& (tactic == ISpielerPosition.DEFENSIV)) {
					stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
				}
			}
		}

		//P8-P12 sind die beitrage von FLzM, IVoff, STdef, AVoff, IMnA!!!
		rating = im + (P2 * zIM) + (P3 * fl);
		rating += (P8 * flZM) + (P9 * ivOff) + (P10 * stDef) + (P11 * avOff) + (P12 * imNa);

		double ts = m_sStimmung + (0.1 + m_sSubStimmung / 5.0);
		rating *= P1 * (1 + (P4 * m_sHome)) * (1 + (P5 * m_sPM)) * (1 + (P6 * (ts - 5.5d)));

		// decrease MF if counter tactics (NOT yet fittet/analyzed ==> CHECK!!!)
		if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_KONTER) {
			rating *= 0.93f;
		}

		// non-linear over all correction!
		rating *= (1 + P13 * rating);

		rating += P7;

		return rating;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param player TODO Missing Method Parameter Documentation
	 * @param skill TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected final float getEffectiveSkillvalue(ISpieler player, int skill) {
		//gucken ob Skillup da war
		Object[] skillup = null;
		float subskill = 0.5f;
		float effective = 0.0f;
		float skillvalue = 0.0f;

		try {
			skillup = player.getLastLevelUp(skill);

			if ((skillup != null)
				&& ((Timestamp) skillup[0] != null)
				&& (((Boolean) skillup[1]).booleanValue())) {
				subskill = player.getSubskill4SkillWithOffset(skill);

				//nachberechnung
			} else {
				subskill = (1 + player.getSubskill4SkillWithOffset(skill)) / 2.0f;
			}

			switch (skill) {
				case ISpieler.SKILL_TORWART :
					skillvalue = player.getTorwart();
					break;

				case ISpieler.SKILL_SPIELAUFBAU :
					skillvalue = player.getSpielaufbau();
					break;

				case ISpieler.SKILL_VERTEIDIGUNG :
					skillvalue = player.getVerteidigung();
					break;

				case ISpieler.SKILL_PASSSPIEL :
					skillvalue = player.getPasspiel();
					break;

				case ISpieler.SKILL_FLUEGEL :
					skillvalue = player.getFluegelspiel();
					break;

				case ISpieler.SKILL_TORSCHUSS :
					skillvalue = player.getTorschuss();
					break;

				case ISpieler.SKILL_STANDARDS :
					skillvalue = player.getStandards();
					break;

				default :
					skillvalue = 0f;
					break;
			}

			//korrektur
			skillvalue = skillvalue - 1 + subskill;

			//mit Kondi
			if (skill == ISpieler.SKILL_SPIELAUFBAU) {
				effective =
					getEffectiveSkillUsingKondi(
						player.getKondition(),
						player.getErfahrung(),
						skillvalue,
						player.getForm());

				//ohne Kondi
			} else {
				effective =
					getEffectiveWithoutKondi(player.getErfahrung(), skillvalue, player.getForm());
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "catcht");
			HOLogger.instance().log(getClass(), e);
		}

		return effective;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MITTELFELD    
	////////////////////////////////////////////////////////////////////////////////////////////////////////    
	private float getEffectiveSkillUsingKondi(int kondi, int erf, float sa, int form) {
		float saEff = 0.0f;
		float kf = 0.0f;
		float ff = 0.0f;
		float eb = 0.0f;

		kf = (float) Math.pow((((float) kondi - 1.) / 7.0f), (m_fKF * 7.0f) / 8.0f);
		eb = (float) (2.0f * m_fErfahrung * (Math.log(erf) / Math.log(10)));
		ff = (float) Math.pow((form - 1) / 7.0f, (m_fForm * 7.0f) / 8.0f);
		saEff = (ff * kf * sa) + eb;

		return saEff;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ANGRIFFsZENTRUM
	////////////////////////////////////////////////////////////////////////////////////////////////////////    
	private float getEffectiveWithoutKondi(int erf, float skill, int form) {
		float skillEff = 0.0f;
		float ff = 0.0f;
		float eb = 0.0f;

		eb = (float) (2.0f * m_fErfahrung * (Math.log(erf) / Math.log(10)));
		ff = (float) Math.pow((form - 1) / 7.0f, (m_fForm * 7.0f) / 8.0f);
		skillEff = (ff * skill) + eb;

		return skillEff;
	}

	private void initBasics(int hrfId) {
		try {

			Team team = null;
			if (hrfId == 0) {
				team = HOVerwaltung.instance().getModel().getTeam();

				//TrainerTyp
				m_sTrainerTyp =
					(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp();

			} else {
				team = DBZugriff.instance().getTeam(hrfId);
				m_sTrainerTyp = (short) DBZugriff.instance().getTrainerType(hrfId);
			}

			m_sPM = (short) lineup.getAttitude();

			//Heim/Ausw abfrage lass ich grad mal sein ;)
			m_sHome = lineup.getHeimspiel();

			//Taktik
			m_sTacticTyp = (short) lineup.getTacticType();

			m_sStimmung = (short) team.getStimmungAsInt();
			m_sSubStimmung = (short) team.getSubStimmung();

			//Selbstvertrauien
			m_sSelbstvertrauen = (short) team.getSelbstvertrauenAsInt();

		} catch (Exception e) {
			//normal
			m_sPM = 0;
			m_sStimmung = 4;
			m_sSubStimmung = 2;
			m_sSelbstvertrauen = 4;

			//kein Bonus
			m_sHome = 0;

			//Balanced   
			m_sTrainerTyp = 2;
			m_sTacticTyp = plugins.IMatchDetails.TAKTIK_NORMAL;
		}
	}

}