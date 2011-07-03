// %2598623053:de.hattrickorganizer.model%
/*
 * Aufstellung.java
 *
 * Created on 20. Mï¿½rz 2003, 14:35
 */
package de.hattrickorganizer.model;

import gui.UserParameter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import plugins.ILineUp;
import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.LineupAssistant;
import de.hattrickorganizer.prediction.RatingPredictionConfig;
import de.hattrickorganizer.prediction.RatingPredictionManager;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * DOCUMENT ME!
 * 
 * Blaghaid moves it to a 553 model. Lots of changes.
 * This is the model responsible for holding the lineup used in predictions and other things.
 *
 * @author thomas.werth
 */
public  class Lineup implements plugins.ILineUp {
	//~ Static fields/initializers -----------------------------------------------------------------

    //Systeme

    public static final String DEFAULT_NAME = "HO!";
    public static final String DEFAULT_NAMELAST = "HO!LastLineup";
    public static final int NO_HRF_VERBINDUNG = -1;

    //~ Instance fields ----------------------------------------------------------------------------

    /** Aufstellungsassistent */
    private LineupAssistant m_clAssi = new LineupAssistant();

    /** positions */
    private Vector<ISpielerPosition> m_vPositionen = new Vector<ISpielerPosition>();

    /** Attitude */
    private int m_iAttitude;

    /** captain */
    private int m_iKapitaen = -1;

    /** set pieces take */
    private int m_iKicker = -1;

    /** TacticType */
    private int m_iTacticType;
    
    /** PullBackMinute **/
    private int pullBackMinute = 90; // no pull back

    /** Home/Away/AwayDerby */
    private short m_sLocation = -1;

	private boolean pullBackOverride;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Aufstellung object.
     */
    public Lineup() {
        //initPositionen442();
    	initPositionen553();
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    Konstruktor
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new instance of Lineup
     * 
     * Probably up for change with new XML?
     */
	public Lineup(Properties properties) throws Exception {
		try {
			// Positionen erzeugen
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.keeper,
					Integer.parseInt(properties.getProperty("keeper", "0")), (byte) 0));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightBack,
					Integer.parseInt(properties.getProperty("rightback", "0")), 
					Byte.parseByte(properties.getProperty("behrightback", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightCentralDefender,
					Integer.parseInt(properties.getProperty("insideback1", "0")), 
					Byte.parseByte(properties.getProperty("behinsideback1","0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftCentralDefender,
					Integer.parseInt(properties.getProperty("insideback2", "0")),
					Byte.parseByte(properties.getProperty("behinsideback2", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.middleCentralDefender,
					Integer.parseInt(properties.getProperty("insideback3", "0")),
					Byte.parseByte(properties.getProperty("behinsideback3", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftBack, 
					Integer.parseInt(properties.getProperty("leftback", "0")), 
					Byte.parseByte(properties.getProperty("behleftback", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightWinger,
					Integer.parseInt(properties.getProperty("rightwinger", "0")), 
					Byte.parseByte(properties.getProperty("behrightwinger","0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightInnerMidfield,
					Integer.parseInt(properties.getProperty("insidemid1", "0")),
					Byte.parseByte(properties.getProperty("behinsidemid1", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftInnerMidfield,
					Integer.parseInt(properties.getProperty("insidemid2", "0")), 
					Byte.parseByte(properties.getProperty("behinsidemid2", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.centralInnerMidfield,
					Integer.parseInt(properties.getProperty("insidemid3", "0")), 
					Byte.parseByte(properties.getProperty("behinsidemid3", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftWinger,
					Integer.parseInt(properties.getProperty("leftwinger", "0")), 
					Byte.parseByte(properties.getProperty("behleftwinger", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightForward, 
					Integer.parseInt(properties.getProperty("forward1", "0")),
					Byte.parseByte(properties.getProperty("behforward1", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftForward, 
					Integer.parseInt(properties.getProperty("forward2", "0")),
					Byte.parseByte(properties.getProperty("behforward2", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.centralForward, 
					Integer.parseInt(properties.getProperty("forward3", "0")),
					Byte.parseByte(properties.getProperty("behforward3", "0"))));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.substDefender,
					Integer.parseInt(properties.getProperty("substback", "0")), (byte) 0));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.substInnerMidfield,
					Integer.parseInt(properties.getProperty("substinsidemid","0")), (byte) 0));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.substWinger,
					Integer.parseInt(properties.getProperty("substwinger", "0")), (byte) 0));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.substKeeper,
					Integer.parseInt(properties.getProperty("substkeeper", "0")), (byte) 0));
			m_vPositionen.add(new SpielerPosition(ISpielerPosition.substForward,
					Integer.parseInt(properties.getProperty("substforward", "0")), (byte) 0));
			m_iTacticType = Integer.parseInt(properties.getProperty("tactictype", "0"));
			m_iAttitude = Integer.parseInt(properties.getProperty("installning", "0"));
			
		} catch (Exception e) {
			HOLogger.instance().warning(getClass(), "Aufstellung.<init1>: " + e);
			HOLogger.instance().log(getClass(), e);
			m_vPositionen.removeAllElements();
			initPositionen553();
		}

		try { // captain + set pieces taker
			m_iKicker = Integer.parseInt(properties.getProperty("kicker1", "0"));
			m_iKapitaen = Integer.parseInt(properties.getProperty("captain", "0"));
		} catch (Exception e) {
			HOLogger.instance().warning(getClass(), "Aufstellung.<init2>: " + e);
			HOLogger.instance().log(getClass(), e);
		}
	}

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * get the tactic level for AiM/AoW
     *
     * @return tactic level
     */
    public final float getTacticLevelAimAow() {
        return Math.max(1,
                new RatingPredictionManager(this, 
                		HOVerwaltung.instance().getModel().getTeam(), 
                		(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), 
                		RatingPredictionConfig.getInstance() )
        		.getTacticLevelAowAim());
    }

    /**
     * get the tactic level for counter
     *
     * @return tactic level
     */
    public final float getTacticLevelCounter() {
        return Math.max(1,
        		new RatingPredictionManager(this, 
        				HOVerwaltung.instance().getModel().getTeam(), 
        				(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), 
        				RatingPredictionConfig.getInstance() )
        		.getTacticLevelCounter() );
    }

    /**
     * get the tactic level for pressing
     *
     * @return tactic level
     */
    public final float getTacticLevelPressing() {
        return Math.max(1,
        		new RatingPredictionManager(this, 
        				HOVerwaltung.instance().getModel().getTeam(), 
        				(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), 
        				RatingPredictionConfig.getInstance() )
        		.getTacticLevelPressing() );
    }

    /**
     * get the tactic level for Long Shots
     *
     * @return tactic level
     */
    public final float getTacticLevelLongShots() {
        return Math.max(1,
                new RatingPredictionManager(this, 
                		HOVerwaltung.instance().getModel().getTeam(), 
                		(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), 
                		RatingPredictionConfig.getInstance() )
        		.getTacticLevelLongShots());
    }

    //    /**
    //     * Calculate the HO-Index for playing creatively
    //     * @author Thorsten Dietz
    //     * @param Vector spieler
    //     * @return tacticStrength
    //     */
    //    public float getCreativeSTK(Vector spieler){
    //    	float strength = 0f;
    //    	boolean isHeadmanInLineUp	= 	false;
    //    	for (int i = ISpielerPosition.rightBack; i < ISpielerPosition.beginnReservere; i++) {
    //            ISpieler player = HOVerwaltung.instance().getModel().getAufstellung()
    //                                          .getPlayerByPositionID(i);
    //
    //            if(player != null && player.getSpezialitaet() == ISpieler.KOPFBALLSTARK)
    //            	isHeadmanInLineUp = true;
    //            byte tactic = HOVerwaltung.instance().getModel().getAufstellung().getTactic4PositionID(i);
    //            strength+=PlayerHelper.getSpecialEventEffect(player,i,tactic);
    //
    //
    //    	}
    //    	if(isHeadmanInLineUp)
    //    		strength++;
    //    	return strength;
    //    }

    /**
     * Calculates the total star rating for defense
     * This is CA-rating?
     */
    public final float getAWTeamStk(Vector<ISpieler> spieler, boolean mitForm) {
        float stk = 0.0f;
        stk += calcTeamStk(spieler, ISpielerPosition.CENTRAL_DEFENDER, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.CENTRAL_DEFENDER_OFF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.CENTRAL_DEFENDER_TOWING, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.BACK, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.BACK_OFF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.BACK_DEF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.BACK_TOMID, mitForm);

        return Helper.round(stk, 1);
    }

    /**
     * Setter for property m_iAttitude.
     *
     * @param m_iAttitude New value of property m_iAttitude.
     */
    public final void setAttitude(int m_iAttitude) {
        this.m_iAttitude = m_iAttitude;
    }

    /**
     * Getter for property m_iAttitude.
     *
     * @return Value of property m_iAttitude.
     */
    public final int getAttitude() {
        return m_iAttitude;
    }

    /**
     * Auto-select the set best captain.
     */
    public final void setAutoKapitaen(Vector<ISpieler> spieler) {
        Spieler player = null;
        float maxValue = -1;

        if (spieler == null) {
            spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        }

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            player = (Spieler) spieler.elementAt(i);

            if (m_clAssi.isSpielerInAnfangsElf(player.getSpielerID(), m_vPositionen)) {
            	int curPlayerId = player.getSpielerID();
            	float curCaptainsValue = HOVerwaltung.instance().getModel().getAufstellung().getAverageExperience(curPlayerId);
                if (maxValue < curCaptainsValue) {
                    maxValue = curCaptainsValue;
                    m_iKapitaen = curPlayerId;
                }
            }
        }
    }

    /**
     * Auto-select the set best pieces taker.
     */
    public final void setAutoKicker(Vector<ISpieler> spieler) {
        int maxStandard = -1;
        int form = -1;
        Spieler player = null;

        if (spieler == null) {
            spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        }

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            player = (Spieler) spieler.elementAt(i);

            if (m_clAssi.isSpielerInAnfangsElf(player.getSpielerID(), m_vPositionen)) {
                if (player.getStandards() > maxStandard) {
                    maxStandard = player.getStandards();
                    form = player.getForm();
                    m_iKicker = player.getSpielerID();
                } else if ((player.getStandards() == maxStandard) && (form < player.getForm())) {
                    maxStandard = player.getStandards();
                    form = player.getForm();
                    m_iKicker = player.getSpielerID();
                }
            }
        }
    }

    /**
     * Get the average experience of all players in lineup
     * using the formula from kopsterkespits:
     * teamxp = ((sum of teamxp + xp of captain)/12)*(1-(7-leadership of captain)*5%)
     */
    public final float getAverageExperience() {
    	return getAverageExperience(0);
    }
    
    /**
     * Get the average experience of all players in lineup using a specific captain
     * 
     * @param captainsId use this player as captain (<= 0 for current captain)
     */
    public final float getAverageExperience(int captainsId) {
    	float value = 0;
    	try {
    		Spieler pl = null;
    		Spieler captain = null;
    		Vector<ISpieler> players = HOVerwaltung.instance().getModel().getAllSpieler();

    		for (int i = 0; (players != null) && (i < players.size()); i++) {
    			pl = (Spieler) players.elementAt(i);
    			if (m_clAssi.isSpielerInAnfangsElf(pl.getSpielerID(), m_vPositionen)) {
    				value += pl.getErfahrung();
    				if (captainsId > 0) {
    					if (captainsId == pl.getSpielerID()) {
        					captain = pl;
    					}
    				} else if (m_iKapitaen == pl.getSpielerID()) {
    					captain = pl;
    				}
    			}
    		}
    		if (captain != null) {
    			value = ((float)(value + captain.getErfahrung())/12) * (1f-(float)(7-captain.getFuehrung())*0.05f);
    		} else {
    			HOLogger.instance().log(getClass(), "Can't calc average experience, captain not set.");
    			value = -1f;
    		}
    	} catch (Exception e) {
    		HOLogger.instance().error(getClass(), e);
    	}
        return value;
    }

    /**
     * Get the best players for penalty kicks.
     */
    public final int[] getBestElferKicker() {
		return m_clAssi.setElferKicker(HOVerwaltung.instance().getModel().getAllSpieler(), m_vPositionen);
    }

    /**
     * Predicts Central Attack-Rating
     */
    public final double getCentralAttackRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );
	        //ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
	        double value = Math.max(1, rpManager.getCentralAttackRatings());
	        if (value>1) {
	        	value += UserParameter.instance().middleAttackOffset;
	        }
	        return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Predicts cd-Rating
     */
    public final double getCentralDefenseRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );
	        //ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getCentralDefenseRatings());
			if (value>1) {
				value += UserParameter.instance().middleDefenceOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Total strength.
     */
    public final float getGesamtStaerke(Vector<ISpieler> spieler, boolean useForm) {
		return Helper.round(getTWTeamStk(spieler, useForm) + getAWTeamStk(spieler, useForm) // 
				+ getMFTeamStk(spieler, useForm) + getSTTeamStk(spieler, useForm), 1);
    }

    /**
     * Get the HT stats value for the lineup.
     */
    public final int getHATStats() {
		int sum;
		final int MFfactor = 3;

		sum = HTfloat2int(getMidfieldRating()) * MFfactor;

		sum += HTfloat2int(getLeftDefenseRating());
		sum += HTfloat2int(getCentralDefenseRating());
		sum += HTfloat2int(getRightDefenseRating());

		sum += HTfloat2int(getLeftAttackRating());
		sum += HTfloat2int(getCentralAttackRating());
		sum += HTfloat2int(getRightAttackRating());

		return sum;
    }

	public void updateRatingPredictionConfig() {
		int vt = atLeastOne(getAnzInnenverteidiger());
		int im = atLeastOne(getAnzInneresMittelfeld());
		int st = atLeastOne(getAnzSturm());
		String predictionName = vt + "D+" + im + "M+" + st + "F";
		RatingPredictionConfig.setInstancePredictionName(predictionName);
	}

	private int atLeastOne(int count) {
		return count == 0 ? 1 : count;
	}

	/**
     * Setter for property m_iKapitaen.
     *
     * @param m_iKapitaen New value of property m_iKapitaen.
     */
    public final void setKapitaen(int m_iKapitaen) {
        this.m_iKapitaen = m_iKapitaen;
    }

    /**
     * Getter for property m_iKapitaen.
     *
     * @return Value of property m_iKapitaen.
     */
    public final int getKapitaen() {
        return m_iKapitaen;
    }

    /**
     * Setter for property m_iKicker.
     *
     * @param m_iKicker New value of property m_iKicker.
     */
    public final void setKicker(int m_iKicker) {
        this.m_iKicker = m_iKicker;
    }

    /**
     * Getter for property m_iKicker.
     *
     * @return Value of property m_iKicker.
     */
    public final int getKicker() {
        return m_iKicker;
    }

    /**
     * Predicts LeftAttack-Rating
     */
    public final double getLeftAttackRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );

			//ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getLeftAttackRatings());
			if (value>1) {
				value += UserParameter.instance().leftAttackOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Predicts LeftDefense-Rating
     */
    public final double getLeftDefenseRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );

			//ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getLeftDefenseRatings());
			if (value>1) {
				value += UserParameter.instance().leftDefenceOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Get the Loddar stats value for the lineup.
     */
	public final float getLoddarStats() {
		LoddarStatsCalculator calculator = new LoddarStatsCalculator();
		calculator.setRatings(getMidfieldRating(), getRightDefenseRating(), getCentralDefenseRating(), getLeftDefenseRating(),
				getRightAttackRating(), getCentralAttackRating(), getLeftAttackRating());
		calculator.setTactics(getTacticType(), getTacticLevelAimAow(), getTacticLevelCounter());
		return calculator.calculate();
	}

	/**
	 * convert reduced float rating (1.00....20.99) to original integer HT rating (1...80)
	 * one +0.5 is because of correct rounding to integer
	 */
	public static final int HTfloat2int(double x) {
		return (int) (((x - 1.0f) * 4.0f) + 1.0f);
	}
	
    /**
     * Midfield and winger total star rating.
     */
    public final float getMFTeamStk(Vector<ISpieler> spieler, boolean mitForm) {
        float stk = 0.0f;
        stk += calcTeamStk(spieler, ISpielerPosition.MIDFIELDER, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.WINGER, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.MIDFIELDER_OFF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.WINGER_OFF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.MIDFIELDER_DEF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.WINGER_DEF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.MIDFIELDER_TOWING, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.WINGER_TOMID, mitForm);

        return Helper.round(stk, 1);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    Ratings
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Predicts MF-Rating
     */
    public final double getMidfieldRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
			final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(),
					(short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance());
			//ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getMFRatings());
			if (value>1) {
				value += UserParameter.instance().midfieldOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Get the short name for a fomation constant.
     */
    public static String getNameForSystem(byte system) {
        String name;

        switch (system) {
            case SYS_451:
                name = "4-5-1";
                break;

            case SYS_352:
                name = "3-5-2";
                break;

            case SYS_442:
                name = "4-4-2";
                break;

            case SYS_343:
                name = "3-4-3";
                break;

            case SYS_433:
                name = "4-3-3";
                break;

            case SYS_532:
                name = "5-3-2";
                break;

            case SYS_541:
                name = "5-4-1";
                break;
                
            case SYS_523:
                name = "5-2-3";
                break;
                
            case SYS_550:
                name = "5-5-0";
                break;

            case SYS_253:
                name = "2-5-3";
                break;

            default:
                name = HOVerwaltung.instance().getLanguageString("Unbestimmt");
                break;
        }

        return name;
    }

    /**
     * Get the position type (byte in ISpielerPosition).
     */
    public final byte getEffectivePos4PositionID(int positionsid) {
        try {
            return getPositionById(positionsid).getPosition();
        } catch (Exception e) {
        	HOLogger.instance().error(getClass(),"getEffectivePos4PositionID: " + e);
            return ISpielerPosition.UNKNOWN;
        }
    }

    /**
     * Setter for property m_sHeimspiel.
     * @param m_sHeimspiel New value of property m_sHeimspiel.
     */
    public final void setHeimspiel(short location) {
        this.m_sLocation = location;
    }

    /**
     * Get the location constant for the match (home/away/awayderby)
     * TODO: determine away derby (aik) !!!
     * @return the location constant for the match
     */
	public final short getHeimspiel() {
		if (m_sLocation < 0) {
			try {
				final int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
				final IMatchKurzInfo[] matches = DBZugriff.instance().getMatchesKurzInfo(teamId, IMatchKurzInfo.UPCOMING);
				IMatchKurzInfo match;
				
				if ((matches == null) || (matches.length < 1)) {
					m_sLocation = IMatchDetails.LOCATION_AWAY;
					HOLogger.instance().debug(getClass(), "no match to determine location");
					return m_sLocation;
				}
				
				if (matches.length > 1) {
					final List<IMatchKurzInfo> sMatches = orderMatches(matches);
					match = sMatches.get(0);
				} else {
					match = matches[0];
				}
				
				m_sLocation = (match.getHeimID() == teamId) ? IMatchDetails.LOCATION_HOME : IMatchDetails.LOCATION_AWAY;

				// To decide away derby we need hold of the region.
				// I think this is very annoying to get hold of 
				// (possible both download and db work needed) and
				// probably why aik has it as TODO (blaghaid).
				
			} catch (Exception e) {
				HOLogger.instance().error(getClass(), "getHeimspiel: " + e);
				m_sLocation = 0;
			}
		}
//		HOLogger.instance().debug(getClass(), "getHeimspiel: " + m_sLocation);
		return m_sLocation;
	}
	
	/**
	 * For some reason we have users with old "upcoming" matches which break the location determination.
	 * This method removes all matches that are more than 8 days older than the previous 'upcoming' match.
	 * 
	 * This method also returns the matches in order of the newest first.
	 */
	private List<IMatchKurzInfo> orderMatches(IMatchKurzInfo[] inMatches) {
		final List<IMatchKurzInfo> matches = new ArrayList<IMatchKurzInfo>();
		if (inMatches != null && inMatches.length > 1) {
			for (IMatchKurzInfo m : inMatches) {
				matches.add(m);
			}
			
			// Flipped the sign of the return value to get newest first - blaghaid
			Collections.sort(matches, new Comparator<IMatchKurzInfo>() {
				public int compare(IMatchKurzInfo o1, IMatchKurzInfo o2) {
					return (o1.getMatchDateAsTimestamp().compareTo(o2.getMatchDateAsTimestamp()));
				}
			});
			
			Timestamp checkDate = null;
			for (Iterator<IMatchKurzInfo> i=matches.iterator(); i.hasNext(); ) {
				IMatchKurzInfo m = i.next();
				if (checkDate == null) {
					checkDate = m.getMatchDateAsTimestamp();
					continue;
				}
				if (m.getMatchDateAsTimestamp().getTime() < (checkDate.getTime()-8*24*60*60*1000L)) { // older than 8 days
					HOLogger.instance().warning(getClass(), "Old match with status UPCOMING! " + m.getMatchID() + " from " + m.getMatchDate());
					i.remove();
				} else {
					checkDate = m.getMatchDateAsTimestamp();
				}
			}
		}
		return matches;
	}

    /* Umrechnung von double auf 1-80 int*/
    public final int getIntValue4Rating(double rating) {
        return (int) (((float) (rating - 1) * 4f) + 1);
    }

    /**
     * Get the player object by position id.
     */
    public ISpieler getPlayerByPositionID(int positionId) {
        try {
			return HOVerwaltung.instance().getModel().getSpieler(getPositionById(positionId).getSpielerId());
        } catch (Exception e) {
			HOLogger.instance().error(getClass(), "getPlayerByPositionID(" + positionId + "): " + e);
            return null;
        }
    }

    /**
     * Get the position object by position id. 
     */
    public final SpielerPosition getPositionById(int id) {
        for (int i = 0; i < m_vPositionen.size(); i++) {
            if (((SpielerPosition) m_vPositionen.get(i)).getId() == id) {
                return (SpielerPosition) m_vPositionen.get(i);
            }
        }
        return null;
    }

    /**
     * Get the position object by player id.
     */
	public final SpielerPosition getPositionBySpielerId(int playerid) {
		for (int i = 0; i < m_vPositionen.size(); i++) {
			if (((SpielerPosition) m_vPositionen.get(i)).getSpielerId() == playerid) {
				return (SpielerPosition) m_vPositionen.get(i);
			}
		}
		return null;
	}

    /**
     * Setter for property m_vPositionen.
     * All previous entries of the linup are cleared.
     *
     * @param m_vPositionen New value of property m_vPositionen.
     */
    public final void setPositionen(Vector<ISpielerPosition> posVec) {
    	// Replace the existing positions with the incoming on a one by one basis. Otherwise we will miss 3 positions when loading
    	// an old style lineup.
    	// We need to avoid the regular methods, as some required stuff like the Model may not be created yet.
    	
    	if (posVec != null) {
    		initPositionen553();
    		for (int i = 0; i < posVec.size(); i++) {
    			SpielerPosition spos = (SpielerPosition) posVec.get(i);
    			for (int j = 0; j < m_vPositionen.size(); j++) {
    				if (((SpielerPosition) m_vPositionen.get(j)).getId() == spos.getId()) {
    					m_vPositionen.setElementAt(spos, j);
    				}
    			}
    		}
    	}
    }

    /**
    * Clears all positions of content by creating a new, empty lineup.
    */
    public final void clearLineup() {
    	initPositionen553();
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    //    Accessor
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_vPositionen.
     *
     * @return Value of property m_vPositionen.
     */
    public final Vector<ISpielerPosition> getPositionen() {
        return m_vPositionen;
    }

    /**
     * Predicts Right-Attack-Rating
     */
    public final double getRightAttackRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );
			//ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getRightAttackRatings());
			if (value>1) {
				value += UserParameter.instance().rightAttackOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Predicts rd-Rating
     */
    public final double getRightDefenseRating() {
    	if (HOVerwaltung.instance().getModel() != null && HOVerwaltung.instance().getModel().getID() != -1) {
	        final RatingPredictionManager rpManager = new RatingPredictionManager(this, HOVerwaltung.instance().getModel().getTeam(), (short) HOVerwaltung.instance().getModel().getTrainer().getTrainerTyp(), RatingPredictionConfig.getInstance() );
			//ruft konvertiertes Plugin ( in Manager ) auf und returned den Wert
			double value = Math.max(1, rpManager.getRightDefenseRatings());
			if (value>1) {
				value += UserParameter.instance().rightDefenceOffset;
			}
			return value;
    	} else {
    		return 0.0d;
    	}
    }

    /**
     * Team star rating for attackers
     */
    public final float getSTTeamStk(Vector<ISpieler> spieler, boolean mitForm) {
        float stk = 0.0f;
        stk += calcTeamStk(spieler, ISpielerPosition.FORWARD, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.FORWARD_DEF, mitForm);
        stk += calcTeamStk(spieler, ISpielerPosition.FORWARD_TOWING, mitForm);

        return Helper.round(stk, 1);
    }

    /**
     * Place a player to a certain position and check/solve dependencies.
     */
    public final byte setSpielerAtPosition(int positionsid, int spielerid, byte tactic) {
        final SpielerPosition pos = getPositionById(positionsid);

        if (pos != null) {
            setSpielerAtPosition(positionsid, spielerid);
            pos.setTaktik(tactic);

            return pos.getPosition();
        }

        return ISpielerPosition.UNKNOWN;
    }

    /**
     * Place a player to a certain position and check/solve dependencies.
     */
	public final void setSpielerAtPosition(int positionsid, int spielerid) {
		if (this.isSpielerAufgestellt(spielerid)) {
			for (int i = 0; i < m_vPositionen.size(); i++) {
				if (((SpielerPosition) m_vPositionen.get(i)).getSpielerId() == spielerid) {
					((SpielerPosition) m_vPositionen.get(i)).setSpielerId(0, this);
				}
			}
		}
		final SpielerPosition position = getPositionById(positionsid);
		position.setSpielerId(spielerid, this);
		if (!isSpielerAufgestellt(m_iKapitaen)) {
			m_iKapitaen = 0;
		}
		if (!isSpielerAufgestellt(m_iKicker)) {
			m_iKicker = 0;
		}
	}

    /**
     * Check, if the player is in the lineup.
     */
    public final boolean isSpielerAufgestellt(int spielerId) {
        return m_clAssi.isSpielerAufgestellt(spielerId, m_vPositionen);
    }

    /**
     * Check, if the player is in the starting 11.
     */
    public final boolean isSpielerInAnfangsElf(int spielerId) {
        return m_clAssi.isSpielerInAnfangsElf(spielerId, m_vPositionen);
    }

    /**
     * Check, if the player is a substitute.
     */
    public final boolean isSpielerInReserve(int spielerId) {
        return (m_clAssi.isSpielerAufgestellt(spielerId, m_vPositionen)
               && !m_clAssi.isSpielerInAnfangsElf(spielerId, m_vPositionen));
    }

    /**
     * Get the system name.
     */
    public final String getSystemName(byte system) {
        return getNameForSystem(system);
    }

    /**
     * Star rating for the keeper.
     */
    public final float getTWTeamStk(Vector<ISpieler> spieler, boolean mitForm) {
        return calcTeamStk(spieler, ISpielerPosition.KEEPER, mitForm);
    }

    /**
     * Get tactic type for a position-id.
     */
    public final byte getTactic4PositionID(int positionsid) {
    	try {
            return getPositionById(positionsid).getTaktik();
        } catch (Exception e) {
        	HOLogger.instance().error(getClass(),"getTactic4PositionID: " + e);
            return plugins.ISpielerPosition.UNKNOWN;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    STK Funcs
    /////////////////////////////////////////////////////////////////////////////////
	public final float getTacticLevel(int type) {
		float value = 0.0f;

		switch (type) {
		case plugins.IMatchDetails.TAKTIK_PRESSING:
			value = getTacticLevelPressing();
			break;

		case plugins.IMatchDetails.TAKTIK_KONTER:
			value = getTacticLevelCounter();
			break;

		case plugins.IMatchDetails.TAKTIK_MIDDLE:
		case plugins.IMatchDetails.TAKTIK_WINGS:
			value = getTacticLevelAimAow();
			break;

		case plugins.IMatchDetails.TAKTIK_LONGSHOTS:
			value = getTacticLevelLongShots();
			break;
		}

		return value;
	}

    /**
     * Setter for property m_iTacticType.
     *
     * @param m_iTacticType New value of property m_iTacticType.
     */
    public final void setTacticType(int m_iTacticType) {
        this.m_iTacticType = m_iTacticType;
    }

    /**
     * Getter for property m_iTacticType.
     *
     * @return Value of property m_iTacticType.
     */
    public final int getTacticType() {
        return m_iTacticType;
    }

    /**
     * Get the formation xp for the current formation.
     */
    public final int getTeamErfahrung4AktuellesSystem() {
        int erfahrung = -1;

        switch (ermittelSystem()) {
            case SYS_MURKS:
                erfahrung = -1;
                break;

            case SYS_451:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung451();
                break;

            case SYS_352:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung352();
                break;

            case SYS_442:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getFormationExperience442();
                break;

            case SYS_343:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung343();
                break;

            case SYS_433:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung433();
                break;

            case SYS_532:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung532();
                break;

            case SYS_541:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getErfahrung541();
                break;
                
            case SYS_523:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getFormationExperience523();
                break;
                
            case SYS_550:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getFormationExperience550();
                break;
                
            case SYS_253:
                erfahrung = HOVerwaltung.instance().getModel().getTeam().getFormationExperience253();
                break;

        }

        return erfahrung;
    }

    /**
     * Delete lineup system.
     */
    public final void AufstellungsSystemLoeschen(String name) {
        DBZugriff.instance().deleteSystem(NO_HRF_VERBINDUNG, name);
    }

    /**
     * Check if the players are still in the team (not sold or fired).
     */
    public final void checkAufgestellteSpieler() {
        SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);
            //existiert Spieler noch ?
            if ((HOVerwaltung.instance().getModel() != null)
                && (HOVerwaltung.instance().getModel().getSpieler(pos.getSpielerId()) == null)) {
                //nein dann zuweisung aufheben
                pos.setSpielerId(0, this);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    Aktions Funcs
    /////////////////////////////////////////////////////////////////////////////////

	/**
	 * erstellt die automatische Aufstellung
	 */
	public final void doAufstellung(Vector<ISpieler> spieler, byte reihenfolge, boolean mitForm, boolean idealPosFirst,
			boolean ignoreVerletzung, boolean ignoreSperren, float wetterBonus, int wetter) {
		m_clAssi.doAufstellung(m_vPositionen, spieler, reihenfolge, mitForm, idealPosFirst, ignoreVerletzung, ignoreSperren, wetterBonus,
				wetter);
		setAutoKicker(null);
		setAutoKapitaen(null);
	}

    /////////////////////////////////////////////////////////////////////////////////
    //    Debug Funcs
    /////////////////////////////////////////////////////////////////////////////////
    public final void dump() {
        HOLogger.instance().log(getClass(),"Std-aufstellung");
        dumpValues();
        HOLogger.instance().log(getClass(),"idelaPos");

        //3-5-2
        initPositionen352();

        //353 mit idealpos first
        doAufstellung(HOVerwaltung.instance().getModel().getAllSpieler(),
                      ILineUp.MF_AW_ST, true, true, false,
                      false, 0.2f, ISpieler.LEICHTBEWOELKT);

        //dumpen
        dumpValues();
        HOLogger.instance().log(getClass(),"Ohne idelaPos");

        //3-5-2 aufgestellte SPieler leeren
        resetAufgestellteSpieler();

        //353 mit idealpos first
        doAufstellung(HOVerwaltung.instance().getModel().getAllSpieler(),
                      ILineUp.MF_AW_ST, true, false,
                      false, false, 0.2f, ISpieler.LEICHTBEWOELKT);
        dumpValues();
    }

    /**
     * Clone this lineup, creates and returns a new Lineup object.
     */
	public final Lineup duplicate() {
		final Properties properties = new Properties();
		Lineup clone = null;

		try {
			properties.setProperty("keeper", getPositionById(ISpielerPosition.keeper).getSpielerId() + "");
			properties.setProperty("rightback", getPositionById(ISpielerPosition.rightBack).getSpielerId() + "");
			properties.setProperty("insideback1", getPositionById(ISpielerPosition.rightCentralDefender).getSpielerId() + "");
			properties.setProperty("insideback2", getPositionById(ISpielerPosition.leftCentralDefender).getSpielerId() + "");
			properties.setProperty("insideback3", getPositionById(ISpielerPosition.middleCentralDefender).getSpielerId() + "");
			properties.setProperty("leftback", getPositionById(ISpielerPosition.leftBack).getSpielerId() + "");
			properties.setProperty("rightwinger", getPositionById(ISpielerPosition.rightWinger).getSpielerId() + "");
			properties.setProperty("insidemid1", getPositionById(ISpielerPosition.rightInnerMidfield).getSpielerId() + "");
			properties.setProperty("insidemid2", getPositionById(ISpielerPosition.leftInnerMidfield).getSpielerId() + "");
			properties.setProperty("insidemid3", getPositionById(ISpielerPosition.centralInnerMidfield).getSpielerId() + "");
			properties.setProperty("leftwinger", getPositionById(ISpielerPosition.leftWinger).getSpielerId() + "");
			properties.setProperty("forward1", getPositionById(ISpielerPosition.rightForward).getSpielerId() + "");
			properties.setProperty("forward2", getPositionById(ISpielerPosition.leftForward).getSpielerId() + "");
			properties.setProperty("forward3", getPositionById(ISpielerPosition.centralForward).getSpielerId() + "");
			properties.setProperty("substback", getPositionById(ISpielerPosition.substDefender).getSpielerId() + "");
			properties.setProperty("substinsidemid", getPositionById(ISpielerPosition.substInnerMidfield).getSpielerId() + "");
			properties.setProperty("substwinger", getPositionById(ISpielerPosition.substWinger).getSpielerId() + "");
			properties.setProperty("substkeeper", getPositionById(ISpielerPosition.substKeeper).getSpielerId() + "");
			properties.setProperty("substforward", getPositionById(ISpielerPosition.substForward).getSpielerId() + "");

			properties.setProperty("behrightback", getPositionById(ISpielerPosition.rightBack).getTaktik() + "");
			properties.setProperty("behinsideback1", getPositionById(ISpielerPosition.rightCentralDefender).getTaktik() + "");
			properties.setProperty("behinsideback2", getPositionById(ISpielerPosition.leftCentralDefender).getTaktik() + "");
			properties.setProperty("behinsideback3", getPositionById(ISpielerPosition.middleCentralDefender).getTaktik() + "");
			properties.setProperty("behleftback", getPositionById(ISpielerPosition.leftBack).getTaktik() + "");
			properties.setProperty("behrightwinger", getPositionById(ISpielerPosition.rightWinger).getTaktik() + "");
			properties.setProperty("behinsidemid1", getPositionById(ISpielerPosition.rightInnerMidfield).getTaktik() + "");
			properties.setProperty("behinsidemid2", getPositionById(ISpielerPosition.leftInnerMidfield).getTaktik() + "");
			properties.setProperty("behinsidemid3", getPositionById(ISpielerPosition.centralInnerMidfield).getTaktik() + "");
			properties.setProperty("behleftwinger", getPositionById(ISpielerPosition.leftWinger).getTaktik() + "");
			properties.setProperty("behforward1", getPositionById(ISpielerPosition.rightForward).getTaktik() + "");
			properties.setProperty("behforward2", getPositionById(ISpielerPosition.leftForward).getTaktik() + "");
			properties.setProperty("behforward3", getPositionById(ISpielerPosition.centralForward).getTaktik() + "");

			properties.setProperty("kicker1", getKicker() + "");
			properties.setProperty("captain", getKapitaen() + "");

			properties.setProperty("tactictype", getTacticType() + "");
			properties.setProperty("installning", getAttitude() + "");

			clone = new Lineup(properties);
			clone.setHeimspiel(getHeimspiel());
			clone.setPullBackMinute(getPullBackMinute());
			clone.setPullBackOverride(isPullBackOverride());
		} catch (Exception e) {
			HOLogger.instance().error(getClass(), "Aufstellung.duplicate: " + e);
		}
		return clone;
	}

    /**
     * Determinates the current formation.
     */
    public final byte ermittelSystem() {
        final int defenders = getAnzAbwehr();
        final int midfielders = getAnzMittelfeld();
        final int forwards  =   getAnzSturm();
        if (defenders == 2) {
        	//253
        	if (midfielders == 5) {
        		return ILineUp.SYS_253;
        	}
        	//MURKS
            else {
                return SYS_MURKS;
            }
        } else if (defenders == 3) {
        	//343
            if (midfielders == 4) {
                return SYS_343;
            } //352
            else if (midfielders == 5) {
                return SYS_352;
            }
            //MURKS
            else {
                return SYS_MURKS;
            }
        } else if (defenders == 4) {
            //433
            if (midfielders == 3) {
                return SYS_433;
            } //442
            else if (midfielders == 4) {
                return SYS_442;
            } //451
            else if (midfielders == 5) {
                return SYS_451;
            }
            //MURKS
            else {
                return SYS_MURKS;
            }
        } else if (defenders == 5) {
            //532
            if (midfielders == 3) {
                return SYS_532;
            } //541
            else if (midfielders == 4) {
                return SYS_541;
            } //523
            else if (midfielders == 2) {
                return ILineUp.SYS_523;
            } //550
            else if (midfielders == 5) {
                return ILineUp.SYS_550;
            }
            //MURKS
            else {
                return SYS_MURKS;
            }
        } //MURKS
        else {
            return SYS_MURKS;
        }
    }

    private final void swapContentAtPositions(int pos1, int pos2) {
    	int id1 = 0;
    	int id2 = 0;
    	byte tac1 = -1;
    	byte tac2 = -1;

    	tac1 = getTactic4PositionID(pos1);
    	tac2 = getTactic4PositionID(pos2);
    	
    	if (getPlayerByPositionID(pos1) != null) {
    	  id1 = getPlayerByPositionID(pos1).getSpielerID(); 
    	  setSpielerAtPosition(pos1, 0);
    	}
    	if (getPlayerByPositionID(pos2) != null) {
      	  id2 = getPlayerByPositionID(pos2).getSpielerID();
      	  setSpielerAtPosition(pos2, 0);
      	}
    	setSpielerAtPosition(pos2, id1, tac1);
    	setSpielerAtPosition(pos1, id2, tac2);
    }
    
    /**
     * Swap corresponding right/left players and orders.
     */
    public final void flipSide() {
    	swapContentAtPositions(ISpielerPosition.rightBack, ISpielerPosition.leftBack);
    	swapContentAtPositions(ISpielerPosition.rightCentralDefender, ISpielerPosition.leftCentralDefender);
    	swapContentAtPositions(ISpielerPosition.rightWinger, ISpielerPosition.leftWinger);
    	swapContentAtPositions(ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftInnerMidfield);
    	swapContentAtPositions(ISpielerPosition.rightForward, ISpielerPosition.leftForward);
    }

    /**
     * Load a lineup by name.
     */
    public final void load(String name) {
		final Lineup temp = DBZugriff.instance().getAufstellung(NO_HRF_VERBINDUNG, name);
		m_vPositionen = null;
		m_vPositionen = temp.getPositionen();
		m_iKicker = temp.getKicker();
		m_iKapitaen = temp.getKapitaen();
    }

    /**
     * Load a lineup from HRF.
     */
    public final void load4HRF() {
		final Lineup temp = DBZugriff.instance().getAufstellung(HOVerwaltung.instance().getModel().getID(), "HRF");
		m_vPositionen = null;
		m_vPositionen = temp.getPositionen();
		m_iKicker = temp.getKicker();
		m_iKapitaen = temp.getKapitaen();
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    Datenbank Funcs
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Load a system from the DB.
     */
    public final void loadAufstellungsSystem(String name) {
		m_vPositionen = DBZugriff.instance().getSystemPositionen(NO_HRF_VERBINDUNG, name);
		checkAufgestellteSpieler();
    }

    /////////////////////////////////////////////////////////////////////////////////
    //    Helper Funcs
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Remove a player.
     */
    public final void resetAufgestellteSpieler() {
        m_clAssi.resetPositionsbesetzungen(m_vPositionen);
    }

    /**
     * Remove a spare player.
     */
	public final void resetReserveBank() {
		// Nur Reservespieler
		final Vector<ISpielerPosition> vReserve = new Vector<ISpielerPosition>();
		for (int i = 0; i < m_vPositionen.size(); i++) {
			if (((SpielerPosition) m_vPositionen.get(i)).getId() >= ISpielerPosition.startReserves) {
				vReserve.add(m_vPositionen.get(i));
			}
		}
		m_clAssi.resetPositionsbesetzungen(vReserve);
	}

    /**
     * Save a lineup using the given name.
     */
    public final void save(final String name) {
		DBZugriff.instance().saveAufstellung(NO_HRF_VERBINDUNG, this, name);
    }

    /**
     * Save a lineup.
     */
    public final void save4HRF() {
		DBZugriff.instance().saveAufstellung(HOVerwaltung.instance().getModel().getID(), this, "HRF");
    }

    /**
     * Save the current system in the DB.
     */
    public final void saveAufstellungsSystem(String name) {
		DBZugriff.instance().saveSystemPositionen(NO_HRF_VERBINDUNG, m_vPositionen, name);
    }

    /**
     * Calculate the amount af defenders.
     */
    private int getAnzAbwehr() {
        int anzahl = 0;

        anzahl += getAnzPosImSystem(ISpielerPosition.BACK);
        anzahl += getAnzPosImSystem(ISpielerPosition.BACK_TOMID);
        anzahl += getAnzPosImSystem(ISpielerPosition.BACK_OFF);
        anzahl += getAnzPosImSystem(ISpielerPosition.BACK_DEF);

        return anzahl+getAnzInnenverteidiger();
    }

    /**
     * Calculate the amount of central defenders.
     */
    private int getAnzInnenverteidiger() {
    	int anzahl = 0;
    	
    	anzahl += getAnzPosImSystem(ISpielerPosition.CENTRAL_DEFENDER);
    	anzahl += getAnzPosImSystem(ISpielerPosition.CENTRAL_DEFENDER_TOWING);
    	anzahl += getAnzPosImSystem(ISpielerPosition.CENTRAL_DEFENDER_OFF);
    	
    	return anzahl;
    }
    
    /**
     * Get the total amount of midfielders in the lineup.
     */
    private int getAnzMittelfeld() {
        int anzahl = 0;
        anzahl += getAnzPosImSystem(ISpielerPosition.WINGER);
        anzahl += getAnzPosImSystem(ISpielerPosition.WINGER_TOMID);
        anzahl += getAnzPosImSystem(ISpielerPosition.WINGER_OFF);
        anzahl += getAnzPosImSystem(ISpielerPosition.WINGER_DEF);
        return anzahl + getAnzInneresMittelfeld();
    }

    /**
     * Get the amount of inner midfielders in the lineup.
     */
    private int getAnzInneresMittelfeld() {
    	int anzahl = 0;
    	anzahl += getAnzPosImSystem(ISpielerPosition.MIDFIELDER);
    	anzahl += getAnzPosImSystem(ISpielerPosition.MIDFIELDER_OFF);
    	anzahl += getAnzPosImSystem(ISpielerPosition.MIDFIELDER_DEF);
    	anzahl += getAnzPosImSystem(ISpielerPosition.MIDFIELDER_TOWING);
    	return anzahl;
    }

    /**
     * Get the amount of strikers in the lineup.
     */
	private int getAnzSturm() {
		int anzahl = 0;
		anzahl += getAnzPosImSystem(ISpielerPosition.FORWARD);
		anzahl += getAnzPosImSystem(ISpielerPosition.FORWARD_DEF);
		anzahl += getAnzPosImSystem(ISpielerPosition.FORWARD_TOWING);
		return anzahl;
	}

    /**
     * Generic "counter" for the given position in the current lineup.
     */
    private int getAnzPosImSystem(byte position) {
		SpielerPosition pos = null;
		int anzahl = 0;

		for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
			pos = (SpielerPosition) m_vPositionen.elementAt(i);

			if ((position == pos.getPosition()) && (pos.getId() < ISpielerPosition.startReserves) && (pos.getSpielerId() > 0)) {
				++anzahl;
			}
		}

		return anzahl;
	}
    
    /**
     * @return true if less than 11 players on field, false if 11 (or more) on field
     */
    public boolean hasFreePosition() {
    	SpielerPosition pos = null;
    	int numPlayers = 0;
    	for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
    		pos = (SpielerPosition) m_vPositionen.elementAt(i);
    		
    		if ((pos.getId() < ISpielerPosition.KEEPER) || (pos.getId() >= ISpielerPosition.startReserves)) {
    			continue; // We are not interested in reserves, captain, set piece taker.
    		}
    		
    		// SpielerID of 0 indicates the position is empty. -1 is the first temp player. This is bug prone.
    		// At some time, clean up by adding some boolean value to Spieler instead (isTemp or something).
    		
    		if (pos.getSpielerId() != 0) {
    			numPlayers++;
    			if (numPlayers == 11) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

    /**
     * Calculate player strength for the given position.
     */
    private float calcPlayerStk(Vector<ISpieler> spieler, int spielerId, byte position, boolean mitForm) {
        Spieler player = null;

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            player = (Spieler) spieler.elementAt(i);

            if (player.getSpielerID() == spielerId) {
                return player.calcPosValue(position, mitForm);
            }
        }

        return 0.0f;
    }

    /**
     * Calculate team strength for the given position.
     */
    private float calcTeamStk(Vector<ISpieler> spieler, byte position, boolean useForm) {
        float stk = 0.0f;
        SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (spieler != null) && (i < m_vPositionen.size());
             i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((pos.getPosition() == position) && (pos.getId() < ISpielerPosition.startReserves)) {
                stk += calcPlayerStk(spieler, pos.getSpielerId(), position, useForm);
            }
        }

        return Helper.round(stk, 1);
    }

    /**
     * Debug log lineup.
     */
    private void dumpValues() {
		for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
			final Spieler temp = HOVerwaltung.instance().getModel()
					.getSpieler(((SpielerPosition) m_vPositionen.elementAt(i)).getSpielerId());
			String name = "";
			float stk = 0.0f;

			if (temp != null) {
				name = temp.getName();
				stk = temp.calcPosValue(((SpielerPosition) m_vPositionen.elementAt(i)).getPosition(), true);
			}

			HOLogger.instance().log(getClass(),
					"PosID: " + SpielerPosition.getNameForID(((SpielerPosition) m_vPositionen.elementAt(i)).getId()) // 
							+ ", Player :" + name + " , Stk : " + stk);
		}

		if (m_iKapitaen > 0) {
			HOLogger.instance().log(getClass(), "Captain: " + HOVerwaltung.instance().getModel().getSpieler(m_iKapitaen).getName());
		}

		if (m_iKicker > 0) {
			HOLogger.instance().log(getClass(), "SetPieces: " + HOVerwaltung.instance().getModel().getSpieler(m_iKicker).getName());
		}
		
		if (m_sLocation > -1) {
			HOLogger.instance().log(getClass(), "Location: " + m_sLocation);
		}

		HOLogger.instance().log(
				getClass(),
				"GK: " + getTWTeamStk(HOVerwaltung.instance().getModel().getAllSpieler(), true) + " DF: "
						+ getAWTeamStk(HOVerwaltung.instance().getModel().getAllSpieler(), true) + " MF : "
						+ getMFTeamStk(HOVerwaltung.instance().getModel().getAllSpieler(), true) + " ST : "
						+ getSTTeamStk(HOVerwaltung.instance().getModel().getAllSpieler(), true));
    }

    /**
     * stellt das 4-4-2 Grundsystem ein
     */
    private void initPositionen352() {
        if (m_vPositionen != null) {
            m_vPositionen.removeAllElements();
        } else {
            m_vPositionen = new Vector<ISpielerPosition>();
        }

        m_vPositionen.add(new SpielerPosition(ISpielerPosition.keeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightBack, 0, (byte) 0));
//        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightCentralDefender, 0,
//                                              ISpielerPosition.ZUS_MITTELFELD));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftBack, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substKeeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substForward, 0, (byte) 0));
    }

    /////////////////////////////////////////////////////////////////////////////////
    //  INIT
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * stellt das 4-4-2 Grundsystem ein
     */
    private void initPositionen442() {
        if (m_vPositionen != null) {
            m_vPositionen.removeAllElements();
        } else {
            m_vPositionen = new Vector<ISpielerPosition>();
        }

        m_vPositionen.add(new SpielerPosition(ISpielerPosition.keeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightBack, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftBack, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substKeeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substForward, 0, (byte) 0));
    }
    
    /**
     * Initializes the 553 lineup
     */
    private void initPositionen553 () {
    	if (m_vPositionen != null) {
            m_vPositionen.removeAllElements();
        } else {
            m_vPositionen = new Vector<ISpielerPosition>();
        }

        m_vPositionen.add(new SpielerPosition(ISpielerPosition.keeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightBack, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.middleCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftCentralDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftBack, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.centralInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.rightForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.centralForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.leftForward, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substDefender, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substInnerMidfield, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substWinger, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substKeeper, 0, (byte) 0));
        m_vPositionen.add(new SpielerPosition(ISpielerPosition.substForward, 0, (byte) 0));
    }

    /**
     * Swap 2 players.
     */
    private SpielerPosition swap(Object object, Object object2) {
        final SpielerPosition sp = (SpielerPosition) object;
        final SpielerPosition sp2 = (SpielerPosition) object2;
        return new SpielerPosition(sp.getId(), sp2.getSpielerId(), sp2.getTaktik());
    }

	/**
	 * @return the pullBackMinute
	 */
	public int getPullBackMinute() {
		return pullBackMinute;
	}

	/**
	 * @param pullBackMinute the pullBackMinute to set
	 */
	public void setPullBackMinute(int pullBackMinute) {
		this.pullBackMinute = pullBackMinute;
	}

	/**
	 * @return if the pull back should be overridden.
	 */
	public boolean isPullBackOverride() {
		return pullBackOverride;
	}
	
	/**
	 * @param pullBackOverride the override flag to set.
	 */
	public void setPullBackOverride(boolean pullBackOverride) {
		this.pullBackOverride = pullBackOverride;
	}
			
    /**
     * Debug logging.
     */
//    private static void debug(String txt) {
//    	HOLogger.instance().debug(Aufstellung.class, txt);
//    }

}
