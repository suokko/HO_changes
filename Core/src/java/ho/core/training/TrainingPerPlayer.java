// %2751235623:de.hattrickorganizer.model%
package ho.core.training;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;
import ho.core.util.HelperWrapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author
 */
public class TrainingPerPlayer  {
    //~ Instance fields ----------------------------------------------------------------------------

    private Spieler player;

    //Wing
    private double wing;

    //Stamina
    private double stamina;

    //Passing
    private double passing;

    //Playmaking
    private double playmaking;

    //Set Pieces
    private double setpieces;

    //Scoring
    private double scoring;

    //Goal Keeping
    private double goalkeeping;

    //Defending
    private double defending;
    
    // calculate training for this training date only 
    private Date timestamp = null;
    
    //~ Constants ----------------------------------------------------------------------------------
    //variable for adjusting amount of set pieces when training scoring
    private static final float p_f_shooting_SetPieces = 0.6f;

    private TrainingPoint trainingPoint;
    
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPerPlayer object.
     */
    public TrainingPerPlayer() {
    }

    /**
     * Creates a new TrainingPerPlayer object initialized with a specific player
     */
    public TrainingPerPlayer(Spieler player) {
    	this.player = player; 
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property wing.
     *
     * @param wing New value of property wing.
     */
    public final void setWing(double wing) {
        this.wing = wing;
    }

    /**
     * Getter for property FL.
     *
     * @return Value of property FL.
     */
    public final double getWing() {
        return this.wing;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setStamina(double stamina) {
        this.stamina = stamina;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getStamina() {
        return this.stamina;
    }

    /**
     * Setter for property PS.
     *
     * @param PS New value of property PS.
     */
    public final void setPassing(double passing) {
        this.passing = passing;
    }

    /**
     * Getter for property PS.
     *
     * @return Value of property PS.
     */
    public final double getPassing() {
        return this.passing;
    }

    /**
     * Setter for property SA.
     *
     * @param SA New value of property SA.
     */
    public final void setPlaymaking(double playmaking) {
        this.playmaking = playmaking;
    }

    /**
     * Getter for property SA.
     *
     * @return Value of property SA.
     */
    public final double getPlaymaking() {
        return this.playmaking;
    }

    /**
     * Setter for property ST.
     *
     * @param ST New value of property ST.
     */
    public final void setSetPieces(double setPieces) {
        this.setpieces = setPieces;
    }

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public final double getSetPieces() {
        return this.setpieces;
    }

    /**
     * Setter for property spieler.
     *
     * @param spieler New value of property spieler.
     */
    public final void setPlayer(Spieler player) {
        this.player = player;
    }

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public final Spieler getPlayer() {
        return this.player;
    }

    /**
     * Setter for property TS.
     *
     * @param TS New value of property TS.
     */
    public final void setScoring(double scoring) {
        this.scoring = scoring;
    }

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public final double getScoring() {
        return scoring;
    }

    /**
     * Setter for property TW.
     *
     * @param goalkeeping New value of property TW.
     */
    public final void setGoalKeeping(double goalkeeping) {
        this.goalkeeping = goalkeeping;
    }

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public final double getGoalKeeping() {
        return goalkeeping;
    }

    /**
     * Setter for property VE.
     *
     * @param VE New value of property VE.
     */
    public final void setDefending(double defending) {
        this.defending = defending;
    }

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public final double getDefending() {
        return this.defending;
    }

    /**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Set the timestamp
	 * if not null, calculate sub increase for this training date only
	 * 
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
     * DOCUMENT ME!
     *
     * @return
     */
    @Override
	public final String toString() {
        return player.getSpielerID() + ":" + goalkeeping + ":" + defending + ":" + playmaking + ":" + passing + ":" + wing + ":"
               + scoring + ":" + setpieces;
    }

    /**
     * add sub values of another ITrainingPerPlayer instance to this instance
     * @param values	the instance we take the values from
     */
    public void addValues (TrainingPerPlayer values) {
    	this.wing += values.getWing();
    	this.stamina += values.getStamina();
    	this.passing += values.getPassing();
    	this.playmaking += values.getPlaymaking();
    	this.setpieces += values.getSetPieces();
    	this.scoring += values.getScoring();
    	this.goalkeeping += values.getGoalKeeping();
    	this.defending += values.getDefending();
    }

    /**
     * Returns the training (sub)skill for a specific skill type
     * 
     * @param skillType		skill type from ISpieler.SKILL_*
     * @return				the (sub)skill for this skill type
     */
    public double getSkillValue(int skillType) {
    	double skillValue = 0;
    	switch (skillType) {
	    	case PlayerSkill.KEEPER:
	    		skillValue = this.goalkeeping;
	    	case PlayerSkill.DEFENDING:
	    		skillValue = this.defending;
	    	case PlayerSkill.WINGER:
	    		skillValue = this.wing;
	    	case PlayerSkill.PLAYMAKING:
	    		skillValue = this.playmaking;
	    	case PlayerSkill.SCORING:
	    		skillValue = this.scoring;
	    	case PlayerSkill.PASSING:
	    		skillValue = this.passing;
	    	case PlayerSkill.SET_PIECES:
	    		skillValue = this.setpieces;
    	}
    	return skillValue;
    }
	/**
	 * Checks if trainingDate is after the last skill up in skillType
	 * 
	 * @param trainingDate
	 * @param skillType
	 * @return
	 */
	private boolean isAfterSkillup (Calendar trainingDate, int skillType) {
		if (getTimestamp() == null) {
			if (TrainingManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"isAfterSkillup: traindate NULL (" + skillType + ") is always after skillup");
			}
			return true;			
		}
		Date skillupTime = getLastSkillupDate(skillType, getTimestamp());
		if (trainingDate.getTimeInMillis() > skillupTime.getTime()) {
			if (TrainingManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"isAfterSkillup: traindate "+trainingDate.getTime().toString() 
						+ " (" + skillType + ") is after skillup");
			}
			return true;	
		} else {
			if (TrainingManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"isAfterSkillup: traindate "+trainingDate.getTime().toString() 
						+ " (" + skillType + ") is NOT after skillup");
			}
			return false;
		}
	}
	
    /**
     * Updates the training results
     */
    private void calculateTrainingResults(TrainingPerWeek train) {
		Calendar trainingDate = train.getTrainingDate();
    	if (player.hasTrainingBlock()) {
    		// Do nothing if the player has a training block
			if (TrainingManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"Ignoring train results for player " + player.getName() + " (" + player.getSpielerID() + ") at "
						+ trainingDate.getTime().toString() + " because of TrainingBlock!"); 
			}    		
    		return;
    	}
    	double d = trainingPoint.calcTrainingPoints(false);
    	int trainType = train.getTrainingType();
    	switch (trainType) {
		case TrainingType.PLAYMAKING:
			if (isAfterSkillup(trainingDate, PlayerSkill.PLAYMAKING)) {
				this.playmaking = d;
			}
			break;
		case TrainingType.DEFENDING:
		case TrainingType.DEF_POSITIONS:
			if (isAfterSkillup(trainingDate, PlayerSkill.DEFENDING)) {
				this.defending = d;
			}
			break;
		case TrainingType.CROSSING_WINGER:
		case TrainingType.WING_ATTACKS:
			if (isAfterSkillup(trainingDate, PlayerSkill.WINGER)) {
				this.wing = d;
			}
			break;
		case TrainingType.SHORT_PASSES:
		case TrainingType.THROUGH_PASSES:
			if (isAfterSkillup(trainingDate, PlayerSkill.PASSING)) {
				this.passing= d;
			}
			break;
		case TrainingType.GOALKEEPING:
			if (isAfterSkillup(trainingDate, PlayerSkill.KEEPER)) {
				this.goalkeeping = d;
			}
			break;
		case TrainingType.SCORING:
			if (isAfterSkillup(trainingDate, PlayerSkill.SCORING)) {
				this.scoring = d;
			}
			break;
		case TrainingType.SHOOTING:
			if (isAfterSkillup(trainingDate, PlayerSkill.SCORING)) {
				this.scoring = d;
			}
            // Shooting gives some training in Set Pieces, too
			if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES)) {
	            this.setpieces = (p_f_shooting_SetPieces * trainingPoint.calcTrainingPoints(true));
			}
			break;
		case TrainingType.SET_PIECES:
			if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES)) {
				this.setpieces = d;
			}
			break;
		}
    }

    /**
     * Calculates the last skillup for the player in the correct training
     *
     * @param trainskill Skill we are looking for a skillup
     * @param trainTime Trainingtime
     *
     * @return Last skillup Date, or Date(0) if no skillup was found
     */
    private Date getLastSkillupDate(int trainskill, Date trainTime) {
        //get relevant skillups for calculation period
        final Vector<Object[]> skillUps = getPlayer().getAllLevelUp(trainskill);
        Date skilluptime = new Date(0);
        for (Iterator<Object[]> it = skillUps.iterator(); it.hasNext();) {
            final Object[] aobj = it.next();
            final Boolean bLevel = (Boolean) aobj[1];

            if (bLevel.booleanValue() == true) {
                final Date tmpTime = new Date(((Timestamp) aobj[0]).getTime());
                if ((tmpTime.before(trainTime)) && (tmpTime.after(skilluptime))) {
                    skilluptime = HelperWrapper.instance().getLastTrainingDate(tmpTime, 
                    		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate()).getTime();
                }
            }
        }
        return skilluptime;
    }

    /**
     * get the training point for this instance
     * @return	training point
     */
	public TrainingPoint getTrainingPoint() {
		return trainingPoint;
	}

	/**
	 * set the training point for this instance and
	 * calculate the sub skills for the player using 
	 * the training week from this training point
	 *  
	 * @param trainingPoint	training point
	 */
	public void setTrainingPoint(TrainingPoint trainingPoint) {
		this.trainingPoint = trainingPoint;
		calculateTrainingResults(trainingPoint.getTrainWeek());
	}
    
}
