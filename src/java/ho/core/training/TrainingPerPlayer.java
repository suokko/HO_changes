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

    private Spieler spieler;

    //Flügespiel
    private double FL;

    //Stamania
    private double KO;

    //Passspiel
    private double PS;

    //Spielaufbau
    private double SA;

    //Standards
    private double ST;

    //Torschuss
    private double TS;

    //Torwart
    private double TW;

    //Verteidigung
    private double VE;
    
    /** calculate training for this training date only */ 
    private Date timestamp = null;
    
    //~ Constants ----------------------------------------------------------------------------------
    //variable for adjusting amount of set pieces when training scoring
    private static final float p_f_schusstraining_Standard = 0.6f;

    private TrainingPoint trainPoint;
    
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
    	this.spieler = player; 
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property FL.
     *
     * @param FL New value of property FL.
     */
    public final void setFL(double FL) {
        this.FL = FL;
    }

    /**
     * Getter for property FL.
     *
     * @return Value of property FL.
     */
    public final double getFL() {
        return FL;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setKO(double d) {
        KO = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getKO() {
        return KO;
    }

    /**
     * Setter for property PS.
     *
     * @param PS New value of property PS.
     */
    public final void setPS(double PS) {
        this.PS = PS;
    }

    /**
     * Getter for property PS.
     *
     * @return Value of property PS.
     */
    public final double getPS() {
        return PS;
    }

    /**
     * Setter for property SA.
     *
     * @param SA New value of property SA.
     */
    public final void setSA(double SA) {
        this.SA = SA;
    }

    /**
     * Getter for property SA.
     *
     * @return Value of property SA.
     */
    public final double getSA() {
        return SA;
    }

    /**
     * Setter for property ST.
     *
     * @param ST New value of property ST.
     */
    public final void setST(double ST) {
        this.ST = ST;
    }

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public final double getST() {
        return ST;
    }

    /**
     * Setter for property spieler.
     *
     * @param spieler New value of property spieler.
     */
    public final void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public final Spieler getSpieler() {
        return spieler;
    }

    /**
     * Setter for property TS.
     *
     * @param TS New value of property TS.
     */
    public final void setTS(double TS) {
        this.TS = TS;
    }

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public final double getTS() {
        return TS;
    }

    /**
     * Setter for property TW.
     *
     * @param TW New value of property TW.
     */
    public final void setTW(double TW) {
        this.TW = TW;
    }

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public final double getTW() {
        return TW;
    }

    /**
     * Setter for property VE.
     *
     * @param VE New value of property VE.
     */
    public final void setVE(double VE) {
        this.VE = VE;
    }

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public final double getVE() {
        return VE;
    }

    /**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
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
        return spieler.getSpielerID() + ":" + TW + ":" + VE + ":" + SA + ":" + PS + ":" + FL + ":"
               + TS + ":" + ST;
    }

    /**
     * add sub values of another ITrainingPerPlayer instance to this instance
     * @param values	the instance we take the values from
     */
    public void addValues (TrainingPerPlayer values) {
    	setFL(this.getFL() + values.getFL());
    	setKO(this.getKO() + values.getKO());
    	setPS(this.getPS() + values.getPS());
    	setSA(this.getSA() + values.getSA());
    	setST(this.getST() + values.getST());
    	setTS(this.getTS() + values.getTS());
    	setTW(this.getTW() + values.getTW());
    	setVE(this.getVE() + values.getVE());
    }

    /**
     * Returns the training (sub)skill for a specific skill type
     * 
     * @param skillType		skill type from ISpieler.SKILL_*
     * @return				the (sub)skill for this skill type
     */
    public double getSkillValue (int skillType) {
    	switch (skillType) {
    	case PlayerSkill.KEEPER:
    		return this.getTW();
    	case PlayerSkill.DEFENDING:
    		return this.getVE();
    	case PlayerSkill.WINGER:
    		return this.getFL();
    	case PlayerSkill.PLAYMAKING:
    		return this.getSA();
    	case PlayerSkill.SCORING:
    		return this.getTS();
    	case PlayerSkill.PASSING:
    		return this.getPS();
    	case PlayerSkill.SET_PIECES:
    		return this.getST();
    	}
    	return 0;
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
			if (TrainingsManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"isAfterSkillup: traindate NULL (" + skillType + ") is always after skillup");
			}
			return true;			
		}
		Date skillupTime = getLastSkillupDate(skillType, getTimestamp());
		if (trainingDate.getTimeInMillis() > skillupTime.getTime()) {
			if (TrainingsManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"isAfterSkillup: traindate "+trainingDate.getTime().toString() 
						+ " (" + skillType + ") is after skillup");
			}
			return true;	
		} else {
			if (TrainingsManager.TRAININGDEBUG) {
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
    	if (spieler.hasTrainingBlock()) {
    		// Do nothing if the player has a training block
			if (TrainingsManager.TRAININGDEBUG) {
				HOLogger.instance().debug(getClass(), 
						"Ignoring train results for player " + spieler.getName() + " (" + spieler.getSpielerID() + ") at "
						+ trainingDate.getTime().toString() + " because of TrainingBlock!"); 
			}    		
    		return;
    	}
    	double d = trainPoint.calcTrainingPoints(false);
    	int trainType = train.getTyp();
    	switch (trainType) {
		case TrainingType.PLAYMAKING:
	        //Spielaufbau // playmaking
			if (isAfterSkillup(trainingDate, PlayerSkill.PLAYMAKING))
				setSA (d);
			break;
		case TrainingType.DEFENDING:
            //Verteidigung //defense
		case TrainingType.DEF_POSITIONS:
            //defensive positions
			if (isAfterSkillup(trainingDate, PlayerSkill.DEFENDING))
				setVE (d);
			break;
		case TrainingType.CROSSING_WINGER:
            //Flankenlaeufe //wing
		case TrainingType.WING_ATTACKS:
			// Fluegelangriff //Lateral offensive            
			if (isAfterSkillup(trainingDate, PlayerSkill.WINGER))
				setFL (d);
			break;
		case TrainingType.SHORT_PASSES:
            //Passspiel //passing
		case TrainingType.THROUGH_PASSES:
            // through passes
			if (isAfterSkillup(trainingDate, PlayerSkill.PASSING))
				setPS (d);
			break;
		case TrainingType.GOALKEEPING:
            //Torwart //keeper
			if (isAfterSkillup(trainingDate, PlayerSkill.KEEPER))
				setTW (d);
			break;
		case TrainingType.SCORING:
            //Chancenverwertung //Torschuss //scoring
			if (isAfterSkillup(trainingDate, PlayerSkill.SCORING))
				setTS (d);
			break;
		case TrainingType.SHOOTING:
            //Schusstraining //shooting
			if (isAfterSkillup(trainingDate, PlayerSkill.SCORING))
				setTS (d);
            // Shooting gives some training in Set Pieces, too
			if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES))
	            setST(p_f_schusstraining_Standard * trainPoint.calcTrainingPoints(true));
			break;
		case TrainingType.SET_PIECES:
            //Standardsituationen //set pieces
			if (isAfterSkillup(trainingDate, PlayerSkill.SET_PIECES))
				setST (d);
			break;
		}
    }

    /**
     * Calculates the lask skillup for the player in the corrct train
     *
     * @param trainskill Skill we are looking for a skillup
     * @param trainTime Trainingtime
     *
     * @return Last skillup Date, or Date(0) if no skillup were found
     */
    private Date getLastSkillupDate(int trainskill, Date trainTime) {
        //Feststellung wann die relevanten Skillupswaren
        //get relevant skillups for calculation period
        final Vector<Object[]> levelups = getSpieler().getAllLevelUp(trainskill);
        Date skilluptime = new Date(0);

        for (Iterator<Object[]> it = levelups.iterator(); it.hasNext();) {
            final Object[] aobj = it.next();
            final Boolean bLevel = (Boolean) aobj[1];

            if (bLevel.booleanValue() == true) {
                final Date tmpTime = new Date(((Timestamp) aobj[0]).getTime());

                if ((tmpTime.before(trainTime)) && (tmpTime.after(skilluptime))) {
                    skilluptime = HelperWrapper.instance()
                    					.getLastTrainingDate(tmpTime,
                    							HOVerwaltung.instance().getModel().getXtraDaten()
                    							.getTrainingDate())
                    							.getTime();
                }
            }
        }

        return skilluptime;
    }

    /**
     * get the training point for this instance
     * @return	training point
     */
	public TrainingPoint getTrainPoint() {
		return trainPoint;
	}

	/**
	 * set the training point for this instance and
	 * calculate the sub skills for the player using 
	 * the training week from this training point
	 *  
	 * @param trainPoint	training point
	 */
	public void setTrainPoint(TrainingPoint trainPoint) {
		this.trainPoint = trainPoint;
		calculateTrainingResults(trainPoint.getTrainWeek());
	}
    
}
