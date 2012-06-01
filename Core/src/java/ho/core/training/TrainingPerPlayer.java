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
 * Holds and calculates how much skill training a player received
 *
 * @author
 */
public class TrainingPerPlayer  {
    private Spieler _Player;

    //Wing
    private double _Wing;

    //Stamina
    private double _Stamina;

    //Passing
    private double _Passing;

    //Playmaking
    private double _Playmaking;

    //Set Pieces
    private double _SetPieces;

    //Scoring
    private double _Scoring;

    //Goal Keeping
    private double _GoalKeeping;

    //Defending
    private double _Defending;
    
    // calculate training for this training date only 
    private Date _TrainingDate = null;
    
    private TrainingPerWeek _TrainingWeek;
    
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPerPlayer object.
     */
    public TrainingPerPlayer() {
    }

    /**
     * Creates a new TrainingPerPlayer object initialized with a specific player
     */
    public TrainingPerPlayer(Spieler oPlayer) {
    	_Player = oPlayer; 
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property wing.
     *
     * @param dWing New value of property wing.
     */
    public final void setWing(double dWing) {
        _Wing = dWing;
    }

    /**
     * Getter for property Wing.
     *
     * @return Value of property Wing.
     */
    public final double getWing() {
        return _Wing;
    }

    /**
     * Setter for Stamina
     *
     * @param dStamina New value for stamina
     */
    public final void setStamina(double dStamina) {
        _Stamina = dStamina;
    }

    /**
     * Getter for Stamina
     *
     * @return Value of Stamina
     */
    public final double getStamina() {
        return _Stamina;
    }

    /**
     * Setter for property Passing.
     *
     * @param dPassing New value of property Passing.
     */
    public final void setPassing(double dPassing) {
        _Passing = dPassing;
    }

    /**
     * Getter for property Passing.
     *
     * @return Value of property Passing.
     */
    public final double getPassing() {
        return _Passing;
    }

    /**
     * Setter for property Playmaking.
     *
     * @param dPlaymaking New value of property Playmaking.
     */
    public final void setPlaymaking(double dPlaymaking) {
        _Playmaking = dPlaymaking;
    }

    /**
     * Getter for property Playmaking.
     *
     * @return Value of property Playmaking.
     */
    public final double getPlaymaking() {
        return _Playmaking;
    }

    /**
     * Setter for property ST.
     *
     * @param ST New value of property ST.
     */
    public final void setSetPieces(double setPieces) {
        this._SetPieces = setPieces;
    }

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public final double getSetPieces() {
        return this._SetPieces;
    }

    /**
     * Setter for property spieler.
     *
     * @param spieler New value of property spieler.
     */
    public final void setPlayer(Spieler player) {
        this._Player = player;
    }

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public final Spieler getPlayer() {
        return this._Player;
    }

    /**
     * Setter for property TS.
     *
     * @param TS New value of property TS.
     */
    public final void setScoring(double scoring) {
        this._Scoring = scoring;
    }

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public final double getScoring() {
        return _Scoring;
    }

    /**
     * Setter for property TW.
     *
     * @param goalkeeping New value of property TW.
     */
    public final void setGoalKeeping(double goalkeeping) {
        this._GoalKeeping = goalkeeping;
    }

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public final double getGoalKeeping() {
        return _GoalKeeping;
    }

    /**
     * Setter for property VE.
     *
     * @param VE New value of property VE.
     */
    public final void setDefending(double defending) {
        this._Defending = defending;
    }

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public final double getDefending() {
        return this._Defending;
    }

    /**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this._TrainingDate;
	}

	/**
	 * Set the timestamp
	 * if not null, calculate sub increase for this training date only
	 * 
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this._TrainingDate = timestamp;
	}

	/**
     * DOCUMENT ME!
     *
     * @return
     */
    @Override
	public final String toString() {
        return _Player.getSpielerID() + ":" + _GoalKeeping + ":" + _Defending + ":" + _Playmaking + ":" + _Passing + ":" + _Wing + ":"
               + _Scoring + ":" + _SetPieces;
    }

    /**
     * add sub values of another ITrainingPerPlayer instance to this instance
     * @param values	the instance we take the values from
     */
    public void addValues (TrainingPerPlayer values) {
    	this._Wing += values.getWing();
    	this._Stamina += values.getStamina();
    	this._Passing += values.getPassing();
    	this._Playmaking += values.getPlaymaking();
    	this._SetPieces += values.getSetPieces();
    	this._Scoring += values.getScoring();
    	this._GoalKeeping += values.getGoalKeeping();
    	this._Defending += values.getDefending();
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
	    		skillValue = this._GoalKeeping;
	    	case PlayerSkill.DEFENDING:
	    		skillValue = this._Defending;
	    	case PlayerSkill.WINGER:
	    		skillValue = this._Wing;
	    	case PlayerSkill.PLAYMAKING:
	    		skillValue = this._Playmaking;
	    	case PlayerSkill.SCORING:
	    		skillValue = this._Scoring;
	    	case PlayerSkill.PASSING:
	    		skillValue = this._Passing;
	    	case PlayerSkill.SET_PIECES:
	    		skillValue = this._SetPieces;
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
    	if (_Player.hasTrainingBlock()) {
    		// Do nothing if the player has a training block
			if (TrainingManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"Ignoring train results for player " + _Player.getName() + " (" + _Player.getSpielerID() + ") at "
						+ trainingDate.getTime().toString() + " because of TrainingBlock!"); 
			}    		
    		return;
    	}
    	TrainingPoints trp = train.getTrainingPair();
    	if (trp != null) {
	    	//System.out.println (player.getName() + " final points="+trp.getPrimary());
	    	int trainType = train.getTrainingType();
	    	switch (trainType) {
			case TrainingType.PLAYMAKING:
				if (isAfterSkillup(trainingDate, PlayerSkill.PLAYMAKING)) {
					this._Playmaking = trp.getPrimary();
				}
				break;
			case TrainingType.DEFENDING:
			case TrainingType.DEF_POSITIONS:
				if (isAfterSkillup(trainingDate, PlayerSkill.DEFENDING)) {
					this._Defending = trp.getPrimary();
				}
				break;
			case TrainingType.CROSSING_WINGER:
			case TrainingType.WING_ATTACKS:
				if (isAfterSkillup(trainingDate, PlayerSkill.WINGER)) {
					this._Wing = trp.getPrimary();
				}
				break;
			case TrainingType.SHORT_PASSES:
			case TrainingType.THROUGH_PASSES:
				if (isAfterSkillup(trainingDate, PlayerSkill.PASSING)) {
					this._Passing= trp.getPrimary();
				}
				break;
			case TrainingType.GOALKEEPING:
				if (isAfterSkillup(trainingDate, PlayerSkill.KEEPER)) {
					this._GoalKeeping = trp.getPrimary();
				}
				break;
			case TrainingType.SCORING:
				if (isAfterSkillup(trainingDate, PlayerSkill.SCORING)) {
					this._Scoring = trp.getPrimary();
				}
				break;
			case TrainingType.SHOOTING:
				if (isAfterSkillup(trainingDate, PlayerSkill.SCORING)) {
					this._Scoring = trp.getPrimary();
				}
	            // Shooting gives some training in Set Pieces, too
				if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES)) {
		            this._SetPieces = trp.getSecondary();
				}
				break;
			case TrainingType.SET_PIECES:
				if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES)) {
					this._SetPieces = trp.getPrimary();
				}
				break;
			}
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
	public TrainingPerWeek getTrainingWeek() {
		return _TrainingWeek;
	}

	/**
	 * set the training point for this instance and
	 * calculate the sub skills for the player using 
	 * the training week from this training point
	 *  
	 * @param trainingPoint	training point
	 */
	public void setTrainingWeek(TrainingPerWeek trainingWeek) {
		this._TrainingWeek = trainingWeek;
		calculateTrainingResults(trainingWeek);
	}
    
}
