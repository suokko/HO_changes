package de.hattrickorganizer.prediction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import plugins.ILineUp;
import plugins.IMatchDetails;
import plugins.IRatingPredictionConfig;
import plugins.IRatingPredictionManager;
import plugins.IRatingPredictionParameter;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITeam;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.HOLogger;

public class RatingPredictionManager implements IRatingPredictionManager
{
	//~ Class constants ----------------------------------------------------------------------------
	
    private static final int THISSIDE = IRatingPredictionParameter.THISSIDE;
    private static final int OTHERSIDE = IRatingPredictionParameter.OTHERSIDE;
    private static final int ALLSIDES = IRatingPredictionParameter.ALLSIDES;
    private static final int MIDDLE = IRatingPredictionParameter.MIDDLE;
    private static final int LEFT = IRatingPredictionParameter.LEFT;
    private static final int RIGHT = IRatingPredictionParameter.RIGHT;

    private static final int SIDEDEFENSE = 0; 
    private static final int CENTRALDEFENSE = 1; 
    private static final int MIDFIELD = 2; 
    private static final int SIDEATTACK = 3; 
    private static final int CENTRALATTACK = 4; 

    private static final int GOALKEEPING = ISpieler.SKILL_TORWART; // 0
    private static final int DEFENDING = ISpieler.SKILL_VERTEIDIGUNG; // 1
    private static final int WINGER = ISpieler.SKILL_FLUEGEL; // 2
    private static final int PLAYMAKING = ISpieler.SKILL_SPIELAUFBAU; // 3
    private static final int SCORING = ISpieler.SKILL_TORSCHUSS; // 4
    private static final int PASSING = ISpieler.SKILL_PASSSPIEL; // 5
	//private static final int STAMINA = ISpieler.SKILL_KONDITION; // 6
    //private static final int FORM = ISpieler.SKILL_FORM; // 7
    private static final int SETPIECES = ISpieler.SKILL_STANDARDS; // 8
    //private static final int EXPERIENCE = ISpieler.SKILL_EXPIERIENCE; // 9
    //private static final int LEADERSHIP = ISpieler.SKILL_LEADERSHIP; // 10
    
    public static final int SPEC_NONE = ISpieler.NO_SPECIALTY; // 0
    public static final int SPEC_TECHNICAL = ISpieler.BALLZAUBERER; // 1
    public static final int SPEC_QUICK = ISpieler.SCHNELL; // 2
    public static final int SPEC_POWERFUL = ISpieler.DURCHSETZUGNSSTARK; // 3
    public static final int SPEC_UNPREDICTABLE = ISpieler.UNBERECHENBAR; // 4
    public static final int SPEC_HEADER = ISpieler.KOPFBALLSTARK; // 5
    public static final int SPEC_REGAINER = ISpieler.REGAINER; // 6
    public static final int SPEC_ALL = ISpieler.NUM_SPECIALTIES; // 7
    public static final int NUM_SPEC = ISpieler.NUM_SPECIALTIES+1; // 8

    //~ Class fields -------------------------------------------------------------------------------

    // Initialize with default config
    private static IRatingPredictionConfig config = RatingPredictionConfig.getInstance();
	
    /** Cache for player strength (Hashtable<String, Float>) */
    private static Hashtable<String, Double> playerStrengthCache = new Hashtable<String, Double>();

    
    //~ Instance fields ----------------------------------------------------------------------------
    private short heimspiel;
    private short attitude;
    private short selbstvertrauen;
    private short stimmung;
    private short substimmung;
    private short taktikType;
    private short trainerType;
    private ILineUp lineup;
    private int pullBackMinute;
    private boolean pullBackOverride;

    public RatingPredictionManager () {
    	if (RatingPredictionManager.config == null)
    		RatingPredictionManager.config = RatingPredictionConfig.getInstance();
    }

    public RatingPredictionManager (IRatingPredictionConfig config) {
    	RatingPredictionManager.config = config;
    }
    
    public RatingPredictionManager(ILineUp lineup, int i, ITeam iteam, short trainerType, IRatingPredictionConfig config)
    {
        this.lineup = lineup;
        RatingPredictionManager.config = config;
        init(iteam, trainerType);
    }

    public RatingPredictionManager(ILineUp lineup, ITeam team, short trainerType, IRatingPredictionConfig config)
    {
        this(lineup, 0, team, trainerType, config);
    }
    
    private float calcRatings (int type) {
    	return calcRatings (type, ALLSIDES);
    }
    
    private float calcRatings (int type, int side2calc) {
//    	long startTime = new Date().getTime();
    	IRatingPredictionParameter params;
    	switch (type) {
		case SIDEDEFENSE:
			params = config.getSideDefenseParameters();
			break;
		case CENTRALDEFENSE:
			params = config.getCentralDefenseParameters();
			break;
		case MIDFIELD:
			params = config.getMidfieldParameters();
			break;
		case SIDEATTACK:
			params = config.getSideAttackParameters();
			break;
		case CENTRALATTACK:
			params = config.getCentralAttackParameters();
			break;
		default:
			return 0;
		}
    	Hashtable<String, Properties> allSections = params.getAllSections();
    	Enumeration<String> allKeys = allSections.keys();
    	double retVal = 0;
    	while (allKeys.hasMoreElements()) {
    		String sectionName = (String)allKeys.nextElement();
    		double curValue = calcPartialRating (params, sectionName, side2calc);
//    		HOLogger.instance().debug(this.getClass(), "PartRating for type "+type+", section "+sectionName+" is "+curValue);
    		retVal += curValue;
    	}
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
//    	HOLogger.instance().debug(this.getClass(), "Prediction ["+config.getPredictionName()+"] FullRating for type "+type+" is "+retVal);    	
//    	long endTime = new Date().getTime();
//    	HOLogger.instance().debug(RatingPredictionManager.class, "calcRatings (T=" + type + ",S=" + side2calc + ")"
//    			+ " took " + (endTime-startTime) + "ms");
    	return (float)retVal;
    }

    private double calcPartialRating (IRatingPredictionParameter params, String sectionName, int side2calc) {
    	int skillType = sectionNameToSkillAndSide(sectionName)[0];
    	int sideType = sectionNameToSkillAndSide(sectionName)[1];
    	double retVal = 0;
    	if (skillType == -1 || sideType == -1) {
    		HOLogger.instance().debug(this.getClass(), "parseError: "+sectionName+" resolves to Skill "+skillType+", Side "+sideType);
    		return 0;
    	}
    	double[][] allStk;
    	switch (sideType) {
		case THISSIDE:
			if (side2calc == LEFT)
				allStk = getAllPlayerStrengthLeft(skillType);
			else
				allStk = getAllPlayerStrengthRight(skillType);
			break;
		case OTHERSIDE:
			if (side2calc == LEFT)
				allStk = getAllPlayerStrengthRight(skillType);
			else
				allStk = getAllPlayerStrengthLeft(skillType);
			break;
		case MIDDLE:
			allStk = getAllPlayerStrengthMiddle(skillType);
	   		break;
		default:
			allStk = getAllPlayerStrength(skillType);
			break;
    	}
    	double[][] allWeights = getAllPlayerWeights(params, sectionName);
//    	System.out.println ("calcPartRating: using sidetype="+sideType+", side2calc="+side2calc);
    	// FIXME
    	for (int effPos=0; effPos < allStk.length; effPos++) {
			double curAllSpecWeight = allWeights[effPos][SPEC_ALL];
    		for (int spec=0; spec < SPEC_ALL; spec++) {
    			double curStk = allStk[effPos][spec];
    			double curWeight = allWeights[effPos][spec];
    			if (curStk > 0) {
    				if (curWeight > 0) {
    					
    	  
//    					System.out.println ("addingPlayer (SPEC) @"+effPos+": (skill "+skillType+") stk="+curStk + " * weight="+ curWeight+" = "+curStk * curWeight);
    					
    					// blaghaid: I could not find a better spot to adjust for crowding. For instance the parameters are
    					// set in static method and can't check the number of central players in the lineup. Feel free to
    					// move to a better home.
    					
    					retVal += adjustForCrowding(curStk, effPos) * curWeight;
    				} else {
    	  
//    					System.out.println ("addingPlayer (ALL) @"+effPos+": (skill "+skillType+") stk="+curStk + " * weight="+ curAllSpecWeight +" = "+curStk * curAllSpecWeight);
    					
    					retVal += adjustForCrowding(curStk, effPos) * curAllSpecWeight;
    				}
    			}
    		}
    	}
    	retVal = applyCommonProps (retVal, params, sectionName);
    	return retVal;
    }
    
    private double adjustForCrowding(double stk, int pos) {
    	
    	double weight;
    	
    	switch (pos) {
	    	case ISpielerPosition.CENTRAL_DEFENDER :
	    	case ISpielerPosition.CENTRAL_DEFENDER_OFF :
	    	case ISpielerPosition.CENTRAL_DEFENDER_TOWING :
	    	{
	    		weight = getCrowdingPenalty(CENTRALDEFENSE);
	    		break;
	    	}
	    	case ISpielerPosition.MIDFIELDER :
	    	case ISpielerPosition.MIDFIELDER_DEF :
	    	case ISpielerPosition.MIDFIELDER_OFF :
	    	case ISpielerPosition.MIDFIELDER_TOWING :
	    	{
	    		weight = getCrowdingPenalty(MIDFIELD);
	    		break;
	    	}
	    	case ISpielerPosition.FORWARD :
	    	case ISpielerPosition.FORWARD_DEF :
	    	case ISpielerPosition.FORWARD_TOWING :
	    	{
	    		weight = getCrowdingPenalty(CENTRALATTACK);
	    		break;
	    	}
	    	default :
	    	{
	    		weight = 1;
	    	}
    	}
    	
    	if ( !(weight > 0)) {
    		// It is probably not set in the config
    		weight = 1;
    	}
    	
    	return stk * weight;
    }

    public double applyCommonProps (double inVal, IRatingPredictionParameter params, String sectionName) {
    	double retVal = inVal;
    	// TODO Reihenfolge ok?
        retVal += params.getParam(sectionName, "squareMod", 0) * Math.pow(retVal, 2); // Avoid if possible! 
        retVal += params.getParam(sectionName, "cubeMod", 0) * Math.pow(retVal, 3); // Avoid even more! 

    	if (taktikType == Matchdetails.TAKTIK_WINGS)
    		retVal *= params.getParam(sectionName, "tacticAOW", 1);
    	else if (taktikType == Matchdetails.TAKTIK_MIDDLE)
    		retVal *= params.getParam(sectionName, "tacticAIM", 1);
    	else if (taktikType == Matchdetails.TAKTIK_KONTER)
    		retVal *= params.getParam(sectionName, "tacticCounter", 1);
    	else if (taktikType == Matchdetails.TAKTIK_CREATIVE)
    		retVal *= params.getParam(sectionName, "tacticCreative", 1);
    	else if (taktikType == Matchdetails.TAKTIK_PRESSING)
    		retVal *= params.getParam(sectionName, "tacticPressing", 1);
    	else if (taktikType == Matchdetails.TAKTIK_LONGSHOTS)
    		retVal *= params.getParam(sectionName, "tacticLongshots", 1);

        double teamspirit = (double)stimmung + ((double)substimmung / 5);
        // Alternative 1: TS linear
        retVal *= (1 + params.getParam(sectionName, "teamSpiritMulti", 0)
        			*(teamspirit - 5.5));
        // Alternative 2: TS exponentiell
       	retVal *= Math.pow((teamspirit * params.getParam(sectionName, "teamSpiritPreMulti", 1/4.5)), 
       				params.getParam(sectionName, "teamSpiritPower", 0));
        
    	if (heimspiel == IMatchDetails.LOCATION_HOME)
    		retVal *= params.getParam(sectionName, "home", 1);
    	else if (heimspiel == IMatchDetails.LOCATION_AWAYDERBY)
    		retVal *= params.getParam(sectionName, "awayDerby", 1);
    	else
    		retVal *= params.getParam(sectionName, "away", 1);

    	if (attitude == Matchdetails.EINSTELLUNG_PIC)
    		retVal *= params.getParam(sectionName, "pic", 1);
    	else if (attitude == Matchdetails.EINSTELLUNG_MOTS)
    		retVal *= params.getParam(sectionName, "mots", 1);
    	else
    		retVal *= params.getParam(sectionName, "normal", 1);
    	
		retVal *= (1.0 + params.getParam(sectionName, "confidence", 0) * (float)(selbstvertrauen - 5));

        // off Trainer
        if(trainerType == 1)
        	retVal *= params.getParam(sectionName, "trainerOff", 1);
        else if(trainerType == 0)
        // def Trainer
   	       	retVal *= params.getParam(sectionName, "trainerDef", 1);
        else if(trainerType == 2)
        // neutral Trainer
   	       	retVal *= params.getParam(sectionName, "trainerNeutral", 1);

        // PullBack event
        int actualPullBackMinute = (pullBackOverride ? 0 : pullBackMinute);
        if (actualPullBackMinute >= 0 && actualPullBackMinute <= 90) {
        	retVal *= 1.0 + (90 - actualPullBackMinute) / 90.0
					* params.getParam(sectionName, "pullback", 0);
        }
        
        retVal *= params.getParam(sectionName, "multiplier", 1);
        retVal += params.getParam(sectionName, "delta", 0);
        
//    	System.out.println ("applyCommonProps: section "+sectionName+", before="+inVal+", after="+retVal);
    	return retVal;
    }
    
    private static String getSpecialtyName (int specialty, boolean withDot) {
    	String retVal = (withDot?".":"");
    	switch (specialty) {
		case SPEC_NONE:
			retVal += "none";
			break;
		case SPEC_TECHNICAL:
			retVal += "technical";
			break;
		case SPEC_QUICK:
			retVal += "quick";
			break;
		case SPEC_POWERFUL:
			retVal += "powerful";
			break;
		case SPEC_UNPREDICTABLE:
			retVal += "unpredictable";
			break;
		case SPEC_HEADER:
			retVal += "header";
			break;
		case SPEC_REGAINER:
			retVal += "regainer";
			break;
		case SPEC_ALL:
//			retVal += "all";
			retVal = "";
			break;
		default:
			return "";
		}
    	return retVal;
    }

//    private static int getSpecialtyByName (String specialtyName) {
//    	specialtyName = specialtyName.toLowerCase();
//    	if (specialtyName.equals("none"))
//    		return SPEC_NONE;
//    	else if (specialtyName.equals("technical"))
//    		return SPEC_TECHNICAL;
//    	else if (specialtyName.equals("quick"))
//    		return SPEC_QUICK;
//    	else if (specialtyName.equals("powerful"))
//    		return SPEC_POWERFUL;
//    	else if (specialtyName.equals("unpredictable"))
//    		return SPEC_UNPREDICTABLE;
//    	else if (specialtyName.equals("header"))
//    		return SPEC_HEADER;
//    	else if (specialtyName.equals("regainer"))
//    		return SPEC_REGAINER;
//    	else if (specialtyName.equals("all") || specialtyName.equals(""))
//    		return SPEC_ALL;
//    	else
//    		return -1;
//    }
    
    private static String getSkillName (int skill) {
    	switch (skill) {
		case GOALKEEPING:
			return "goalkeeping";
		case DEFENDING:
			return "defending";
		case WINGER:
			return "winger";
		case PLAYMAKING:
			return "playmaking";
		case SCORING:
			return "scoring";
		case PASSING:
			return "passing";
		case SETPIECES:
			return "setpieces";
		default:
			return "";
		}
    }

    private static int getSkillByName (String skillName) {
    	skillName = skillName.toLowerCase(java.util.Locale.ENGLISH);
    	if (skillName.equals("goalkeeping"))
    		return GOALKEEPING;
    	else if (skillName.equals("defending"))
    		return DEFENDING;
    	else if (skillName.equals("winger"))
    		return WINGER;
    	else if (skillName.equals("playmaking"))
    		return PLAYMAKING;
    	else if (skillName.equals("scoring"))
    		return SCORING;
    	else if (skillName.equals("passing"))
    		return PASSING;
    	else if (skillName.equals("setpieces"))
    		return SETPIECES;
    	else
    		return -1;
    }

    private static int getSideByName (String sideName) {
    	sideName = sideName.toLowerCase(java.util.Locale.ENGLISH);
    	if (sideName.equals("thisside"))
    		return THISSIDE;
    	else if (sideName.equals("otherside"))
    		return OTHERSIDE;
    	else if (sideName.equals("middle"))
    		return MIDDLE;
    	else if (sideName.equals("") || sideName.equals("allsides"))
    		return ALLSIDES;
    	else
    		return -1;
    }
    
    private static int[] sectionNameToSkillAndSide (String sectionName) {
    	// retArray[0] == skill
    	// retArray[1] == side
    	int[] retArray = new int[2];
    	String skillName = "";
    	String sideName = "";    	
    	if (sectionName.indexOf("_") > -1) {
    		String[] tmp = sectionName.split("_");
    		if (tmp.length == 2) {
    			skillName = tmp[0];
    			sideName = tmp[1];
    		}
    	} else {
    		skillName = sectionName;
    	}
    	retArray[0] = getSkillByName (skillName);
    	retArray[1] = getSideByName (sideName);
    	return retArray;
    }
    
    
    public float getCentralAttackRatings()
    {
    	return calcRatings(CENTRALATTACK);
    }


    public float getCentralDefenseRatings()
    {
    	return calcRatings(CENTRALDEFENSE);
    }

    public float getRightAttackRatings()
    {
        return getSideAttackRatings(RIGHT);
    }

    public float getLeftAttackRatings()
    {
        return getSideAttackRatings(LEFT);
    }

    public float getSideAttackRatings (int side) {
    	return calcRatings(SIDEATTACK, side);
    }

    public float getRightDefenseRatings()
    {
        return getSideDefenseRatings(RIGHT);
    }

    public float getLeftDefenseRatings()
    {
        return getSideDefenseRatings(LEFT);
    }

    public float getSideDefenseRatings (int side) {
    	return calcRatings(SIDEDEFENSE, side);
    }

    
    private double getCrowdingPenalty(int pos) {
    	double penalty;
    	IRatingPredictionParameter  para = config.getPlayerStrengthParameters();
    	
//    	HOLogger.instance().debug(getClass(), "Parameter file used: " + config.getPredictionName());
    	
    	switch (pos) {
    	case CENTRALDEFENSE :
    		// Central Defender
    		penalty = para.getParam(IRatingPredictionParameter.GENERAL, getNumCDs() + "CdMulti");
    		break;
    	case MIDFIELD :
    		// Midfielder
    		penalty = para.getParam(IRatingPredictionParameter.GENERAL, getNumIMs() + "MfMulti");
    		break;
    	case CENTRALATTACK :
    		// Forward
    		penalty = para.getParam(IRatingPredictionParameter.GENERAL, getNumFWs() + "FwMulti");
    		break;
    	default :
    		penalty = 1;
    	}
    	return penalty;
    }
    

    public static double[][] getAllPlayerWeights (IRatingPredictionParameter params, String sectionName) {
    	double[][] weights = new double[ISpielerPosition.NUM_POSITIONS][NUM_SPEC];
		double extraMulti = params.getParam(IRatingPredictionParameter.GENERAL, "extraMulti", 0);
		double modCD = params.getParam(sectionName, "allCDs", 1);
		double modWB = params.getParam(sectionName, "allWBs", 1);
		double modIM = params.getParam(sectionName, "allIMs", 1);
		double modWI = params.getParam(sectionName, "allWIs", 1);
		double modFW = params.getParam(sectionName, "allFWs", 1);
    	for (int specialty=0; specialty<NUM_SPEC; specialty++) {
    		String specialtyName = getSpecialtyName(specialty, true);
    		weights[ISpielerPosition.KEEPER][specialty] = params.getParam(sectionName, "keeper" + specialtyName);
    		weights[ISpielerPosition.KEEPER][specialty] += params.getParam(sectionName, "gk" + specialtyName);	// alias for keeper
    		weights[ISpielerPosition.CENTRAL_DEFENDER][specialty] = params.getParam(sectionName, "cd_norm" + specialtyName) * modCD;
    		weights[ISpielerPosition.CENTRAL_DEFENDER][specialty] += params.getParam(sectionName, "cd" + specialtyName) * modCD;	// alias for cd_norm
    		weights[ISpielerPosition.CENTRAL_DEFENDER_OFF][specialty] = params.getParam(sectionName, "cd_off" + specialtyName) * modCD;
    		weights[ISpielerPosition.CENTRAL_DEFENDER_TOWING][specialty] = params.getParam(sectionName, "cd_tw" + specialtyName) * modCD;
    		weights[ISpielerPosition.BACK][specialty] = params.getParam(sectionName, "wb_norm" + specialtyName) * modWB;
    		weights[ISpielerPosition.BACK][specialty] += params.getParam(sectionName, "wb" + specialtyName) * modWB;	// alias for wb_norm
    		weights[ISpielerPosition.BACK_OFF][specialty] = params.getParam(sectionName, "wb_off" + specialtyName) * modWB;
    		weights[ISpielerPosition.BACK_DEF][specialty] = params.getParam(sectionName, "wb_def" + specialtyName) * modWB;
    		weights[ISpielerPosition.BACK_TOMID][specialty] = params.getParam(sectionName, "wb_tm" + specialtyName) * modWB;
    		weights[ISpielerPosition.MIDFIELDER][specialty] = params.getParam(sectionName, "im_norm" + specialtyName) * modIM;
    		weights[ISpielerPosition.MIDFIELDER][specialty] += params.getParam(sectionName, "im" + specialtyName) * modIM;	// alias for im_norm
    		weights[ISpielerPosition.MIDFIELDER_OFF][specialty] = params.getParam(sectionName, "im_off" + specialtyName) * modIM;
    		weights[ISpielerPosition.MIDFIELDER_DEF][specialty] = params.getParam(sectionName, "im_def" + specialtyName) * modIM;
    		weights[ISpielerPosition.MIDFIELDER_TOWING][specialty] = params.getParam(sectionName, "im_tw" + specialtyName) * modIM;
    		weights[ISpielerPosition.WINGER][specialty] = params.getParam(sectionName, "wi_norm" + specialtyName) * modWI;
    		weights[ISpielerPosition.WINGER][specialty] += params.getParam(sectionName, "wi" + specialtyName) * modWI;	// alias for wi_norm
    		weights[ISpielerPosition.WINGER_OFF][specialty] = params.getParam(sectionName, "wi_off" + specialtyName) * modWI;
    		weights[ISpielerPosition.WINGER_DEF][specialty] = params.getParam(sectionName, "wi_def" + specialtyName) * modWI;
    		weights[ISpielerPosition.WINGER_TOMID][specialty] = params.getParam(sectionName, "wi_tm" + specialtyName) * modWI;
    		weights[ISpielerPosition.FORWARD][specialty] = params.getParam(sectionName, "fw_norm" + specialtyName) * modFW;
    		weights[ISpielerPosition.FORWARD][specialty] += params.getParam(sectionName, "fw" + specialtyName) * modFW;	// alias for fw_norm
    		weights[ISpielerPosition.FORWARD_DEF][specialty] = params.getParam(sectionName, "fw_def" + specialtyName) * modFW;
    		weights[ISpielerPosition.FORWARD_TOWING][specialty] = params.getParam(sectionName, "fw_tw" + specialtyName) * modFW;
//    		weights[ISpielerPosition.POS_ZUS_INNENV][specialty] = params.getParam(sectionName, "extra_cd" + specialtyName) * modCD;
//    		weights[ISpielerPosition.POS_ZUS_MITTELFELD][specialty] = params.getParam(sectionName, "extra_im" + specialtyName) * modIM;
//			weights[ISpielerPosition.POS_ZUS_STUERMER][specialty] = params.getParam(sectionName, "extra_fw" + specialtyName) * modFW;
//			weights[ISpielerPosition.POS_ZUS_STUERMER_DEF][specialty] = params.getParam(sectionName, "fw_def" + specialtyName) * modFW;
//			weights[ISpielerPosition.POS_ZUS_INNENV][specialty] += params.getParam(sectionName, "cd_xtra" + specialtyName) * modCD;	// alias for extra_cd
//			weights[ISpielerPosition.POS_ZUS_MITTELFELD][specialty] += params.getParam(sectionName, "im_xtra" + specialtyName) * modIM;	// alias for extra_im
//			weights[ISpielerPosition.POS_ZUS_STUERMER][specialty] += params.getParam(sectionName, "fw_xtra" + specialtyName) * modFW;	// alias for extra_fw
//			if (extraMulti > 0) {
//				weights[ISpielerPosition.POS_ZUS_INNENV][specialty] = weights[ISpielerPosition.CENTRAL_DEFENDER][specialty] * extraMulti; // if extraMulti is defined, use extraMulti*CD
//				weights[ISpielerPosition.POS_ZUS_MITTELFELD][specialty] = weights[ISpielerPosition.MIDFIELDER][specialty] * extraMulti; // if extraMulti is defined, use extraMulti*IM
//				weights[ISpielerPosition.POS_ZUS_STUERMER][specialty] = weights[ISpielerPosition.FORWARD][specialty] * extraMulti; // if extraMulti is defined, use extraMulti*FW
//			}
    	}
    	return weights;
    }
    
    public double[][] getAllPlayerStrength (int skillType) {
    	return getAllPlayerStrength(skillType, true, true, true);
    }

    public double[][] getAllPlayerStrengthLeft (int skillType) {
    	return getAllPlayerStrength(skillType, true, false, false);
    }

    public double[][] getAllPlayerStrengthRight (int skillType) {
    	return getAllPlayerStrength(skillType, false, false, true);
    }

    public double[][] getAllPlayerStrengthMiddle (int skillType) {
    	return getAllPlayerStrength(skillType, false, true, false);
    }

    public int getNumIMs () {
    	int retVal = 0;
    	for(int pos = ISpielerPosition.startLineup; pos < ISpielerPosition.startReserves; pos++) {
    		ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) { 
            	if (pos == ISpielerPosition.rightInnerMidfield || pos == ISpielerPosition.leftInnerMidfield || 
            			pos == ISpielerPosition.centralInnerMidfield)
            		retVal++;
            }
    	}
    	return retVal;
    }

    public int getNumFWs () {
    	int retVal = 0;
    	for(int pos = ISpielerPosition.startLineup; pos < ISpielerPosition.startReserves; pos++) {
    		ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) { 
            	if (pos == ISpielerPosition.rightForward || pos == ISpielerPosition.leftForward || 
            			pos == ISpielerPosition.centralForward)
            		retVal++;
            }
    	}
    	return retVal;
    }

    public int getNumCDs () {
    	int retVal = 0;
    	for(int pos = ISpielerPosition.startLineup; pos < ISpielerPosition.startReserves; pos++) {
    		ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) { 
            	if (pos == ISpielerPosition.rightCentralDefender || pos == ISpielerPosition.leftCentralDefender || 
            			pos == ISpielerPosition.middleCentralDefender)
            		retVal++;
            }
    	}
    	return retVal;
    }
    

    public boolean isLeft (int pos) {
    	// Blaghaid - Taktik removed as parameter, no longer needed in 553
    	
    	if (pos == ISpielerPosition.leftCentralDefender 
				|| pos == ISpielerPosition.leftInnerMidfield
				|| pos == ISpielerPosition.leftBack 
				|| pos == ISpielerPosition.leftWinger
				|| pos == ISpielerPosition.leftForward)
			return true;
		else
			return false;
    }

    public boolean isRight (int  pos) {
    	// Blaghaid - Taktik removed as parameter, no longer needed in 553
    	
    	if (pos == ISpielerPosition.rightCentralDefender 
				|| pos == ISpielerPosition.rightInnerMidfield
				|| pos == ISpielerPosition.rightBack 
				|| pos == ISpielerPosition.rightWinger
				|| pos == ISpielerPosition.rightForward)
			return true;
		else
			return false;
    }
    
    public boolean isMiddle (int pos) {
    	// Blaghaid - Taktik removed as parameter, no longer needed in 553
    	// A bunch of logic on single forward being central, etc used to be here. Will not be missed.
    	
    	if (pos == ISpielerPosition.centralForward 
				|| pos == ISpielerPosition.centralInnerMidfield
				|| pos == ISpielerPosition.middleCentralDefender) 
			return true;
		else
			return false;
    }

    public static float getLoyaltyHomegrownBonus(ISpieler spieler) {
    	float bonus = 0f;
    	 if (spieler.isHomeGrown()) {
         	bonus += config.getPlayerStrengthParameters().getParam(IRatingPredictionParameter.GENERAL, "homegrownbonus");
         }
         
         // Loyalty bonus
         bonus += (float)config.getPlayerStrengthParameters().getParam(IRatingPredictionParameter.GENERAL, "loyaltyMax") 
         			* spieler.getLoyalty() 
         			/ (float)config.getPlayerStrengthParameters().getParam(IRatingPredictionParameter.GENERAL, "loyaltySkillMax");
    	
    	
    	return bonus;
    }
    
    public double[][] getAllPlayerStrength (int skillType, boolean useLeft, boolean useMiddle, boolean useRight) {
    	double[][] retArray = new double[ISpielerPosition.NUM_POSITIONS][ISpieler.NUM_SPECIALTIES];
//    	System.out.println ("getAllPlayerStrength: st="+skillType+", l="+useLeft+", m="+useMiddle+", r="+useRight);
        for(int pos = ISpielerPosition.startLineup; pos < ISpielerPosition.startReserves; pos++) {
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            byte taktik = lineup.getTactic4PositionID(pos);
            if(spieler != null) {
//            	System.out.println ("getAllPlayerStrength."+pos+", id="+spieler.getSpielerID()+", taktik="+taktik);
            	// Check sides
            	if (!useLeft && isLeft(pos)
            			|| !useMiddle && isMiddle(pos) // XXXX Could this bomb side contribution for the central ones?
            			|| !useRight && isRight(pos)) {
            		continue;
            	} else {
            		int specialty = spieler.getSpezialitaet();
            		// To avoid ArrayOutOfBound exception for unsupported/new specialties
            		if (specialty < SPEC_NONE || specialty >= SPEC_ALL)
            			specialty = SPEC_NONE;

            		// Old code had lots of checks for extras here (extra central defender, etc)
            		
            		else switch (pos) {
            		case ISpielerPosition.keeper:
            			retArray[ISpielerPosition.KEEPER][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		case ISpielerPosition.rightCentralDefender:
            		case ISpielerPosition.leftCentralDefender:
            		case ISpielerPosition.middleCentralDefender:
            			if (taktik == ISpielerPosition.NORMAL)
            				retArray[ISpielerPosition.CENTRAL_DEFENDER][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.OFFENSIVE)
            				retArray[ISpielerPosition.CENTRAL_DEFENDER_OFF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.TOWARDS_WING)
            				retArray[ISpielerPosition.CENTRAL_DEFENDER_TOWING][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		case ISpielerPosition.rightBack:
            		case ISpielerPosition.leftBack:
            			if (taktik == ISpielerPosition.NORMAL)
            				retArray[ISpielerPosition.BACK][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.OFFENSIVE)
            				retArray[ISpielerPosition.BACK_OFF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.DEFENSIVE)
            				retArray[ISpielerPosition.BACK_DEF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.TOWARDS_MIDDLE)
            				retArray[ISpielerPosition.BACK_TOMID][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		case ISpielerPosition.rightWinger:
            		case ISpielerPosition.leftWinger:
            			if (taktik == ISpielerPosition.NORMAL)
            				retArray[ISpielerPosition.WINGER][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.OFFENSIVE)
            				retArray[ISpielerPosition.WINGER_OFF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.DEFENSIVE)
            				retArray[ISpielerPosition.WINGER_DEF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.TOWARDS_MIDDLE)
            				retArray[ISpielerPosition.WINGER_TOMID][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		case ISpielerPosition.rightInnerMidfield:
            		case ISpielerPosition.leftInnerMidfield:
            		case ISpielerPosition.centralInnerMidfield:
            			if (taktik == ISpielerPosition.NORMAL)
            				retArray[ISpielerPosition.MIDFIELDER][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.OFFENSIVE)
            				retArray[ISpielerPosition.MIDFIELDER_OFF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.DEFENSIVE)
            				retArray[ISpielerPosition.MIDFIELDER_DEF][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.TOWARDS_WING)
            				retArray[ISpielerPosition.MIDFIELDER_TOWING][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		case ISpielerPosition.rightForward:
            		case ISpielerPosition.leftForward:
            		case ISpielerPosition.centralForward:
            			if (taktik == ISpielerPosition.NORMAL)
            				retArray[ISpielerPosition.FORWARD][specialty] += calcPlayerStrength(spieler, skillType);
            			else if (taktik == ISpielerPosition.DEFENSIVE) {
            				retArray[ISpielerPosition.FORWARD_DEF][specialty] += calcPlayerStrength(spieler, skillType);
            			} else if (taktik == ISpielerPosition.TOWARDS_WING)
            				retArray[ISpielerPosition.FORWARD_TOWING][specialty] += calcPlayerStrength(spieler, skillType);
            			break;
            		}
            	}
            }
        }
        return retArray;
    }
    
    public float getMFRatings ()
    {
    	return calcRatings(MIDFIELD);
    }

    public static float calcPlayerStrength(ISpieler spieler, int skillType) {
    	return calcPlayerStrength(spieler, skillType, true);
    }

    public static float calcPlayerStrength(ISpieler spieler, int skillType, boolean useForm) {
        double retVal = 0.0F;
        try
        {
            Object lastLvlUp[];
            float skill;
            float subSkill;
            skill = spieler.getValue4Skill4(skillType);
            float manualOffset = (float)spieler.getTrainingOffset4Skill(skillType);
            float subskillFromDB = (float)spieler.getSubskill4Pos(skillType);
//            System.out.println ("t="+skillType+", o="+manualOffset+", s="+subskillFromDB);
            /**
             * If we know the last level up date from this player or
             * the user has set an offset manually -> use this sub/offset
             */
            if (manualOffset > 0 || subskillFromDB > 0 || 
            		(lastLvlUp = spieler.getLastLevelUp(skillType)) != null && (Timestamp)lastLvlUp[0] != null && ((Boolean)lastLvlUp[1]).booleanValue())
                subSkill = spieler.getSubskill4SkillWithOffset(skillType);
            else
            	/**
            	 * Try to guess the sub based on the skill level
            	 */
              subSkill = getSubDeltaFromConfig (config.getPlayerStrengthParameters(), getSkillName(skillType), (int)skill);
            // subSkill>1, this should not happen
            if (subSkill > 1)
            	subSkill = 1;
            skill = skill + subSkill;
            
            // Add loyalty and homegrown bonuses
            skill += getLoyaltyHomegrownBonus(spieler);
               
            retVal = calcPlayerStrength(config.getPlayerStrengthParameters(), 
            		getSkillName(skillType), spieler.getKondition(), spieler.getErfahrung(), skill, spieler.getForm(), useForm);
//            System.out.println("calcPlayerStrength for "+spieler.getSpielerID()
//            		+", st="+skillType+", s="+skill+", k="+spieler.getKondition()
//            		+", xp="+spieler.getErfahrung()+", f="+spieler.getForm()+": "+retVal);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return (float)retVal;    	
    }

    private static float getSubDeltaFromConfig (IRatingPredictionParameter params, String sectionName, int skill) {
    	String useSection = sectionName;
    	if (!params.hasSection(sectionName))
    		useSection = IRatingPredictionParameter.GENERAL;
    	float delta = (float)params.getParam(useSection, "skillSubDeltaForLevel"+skill, 0);
//    	System.out.println(delta);
    	return delta;    	
    }
    
    public static double calcPlayerStrength (IRatingPredictionParameter params, 
    		String sectionName, double stamina, double xp, double skill, double form, boolean useForm) {
//    	long startTime = new Date().getTime();
    	// If config changed, we have to clear the cache
    	if (!playerStrengthCache.containsKey("lastRebuild") 
    			|| playerStrengthCache.get("lastRebuild") < config.getLastParse() ) {
    		HOLogger.instance().debug(RatingPredictionManager.class, "RPM tainted, clearing cache!");
    		playerStrengthCache.clear();
    		playerStrengthCache.put ("lastRebuild", new Double(new Date().getTime()));
    	}
    	String key = params.toString() + "|" + sectionName + "|" + stamina + "|" + xp + "|" + skill + "|" + form + "|" + useForm;
    	if (playerStrengthCache.containsKey(key)) {
//    		HOLogger.instance().debug(RatingPredictionManager.class, "Using from cache: " + key);
    		return playerStrengthCache.get(key);
    	}
    	double stk = 0;
    	String useSection = sectionName;
    	if (!params.hasSection(sectionName))
    		useSection = IRatingPredictionParameter.GENERAL;
    	
    	skill += params.getParam(useSection, "skillDelta", 0);
    	stamina += params.getParam(useSection, "staminaDelta", 0);
    	form += params.getParam(useSection, "formDelta", 0);
    	xp += params.getParam(useSection, "xpDelta", 0);

    	skill = Math.max(skill, params.getParam(useSection, "skillMin", 0));
    	stamina = Math.max(stamina, params.getParam(useSection, "staminaMin", 0));
    	form = Math.max(form, params.getParam(useSection, "formMin", 0));
    	xp = Math.max(xp, params.getParam(useSection, "xpMin", 0));

    	skill = Math.min(skill, params.getParam(useSection, "skillMax", 99999));
    	stamina = Math.min(stamina, params.getParam(useSection, "staminaMax", 99999));
    	form = Math.min(form, params.getParam(useSection, "formMax", 99999));
    	xp = Math.min(xp, params.getParam(useSection, "xpMax", 99999));

    	skill *= params.getParam(useSection, "skillMultiplier", 1);
    	stamina *= params.getParam(useSection, "staminaMultiplier", 1);
    	form *= params.getParam(useSection, "formMultiplier", 1);
    	xp *= params.getParam(useSection, "xpMultiplier", 1);

    	skill = Math.pow(skill, params.getParam(useSection, "skillPower", 1));
    	stamina = Math.pow(stamina, params.getParam(useSection, "staminaPower", 1));
    	form = Math.pow(form, params.getParam(useSection, "formPower", 1));
    	xp = Math.pow(xp, params.getParam(useSection, "xpPower", 1));

    	if (params.getParam(useSection, "skillLog", 0) > 0)
    		skill = Math.log(skill)/Math.log(params.getParam(useSection, "skillLog", 0));
    	if (params.getParam(useSection, "staminaLog", 0) > 0)
    		stamina = Math.log(stamina)/Math.log(params.getParam(useSection, "staminaLog", 0));
    	if (params.getParam(useSection, "formLog", 0) > 0)
    		form = Math.log(form)/Math.log(params.getParam(useSection, "formLog", 0));
    	if (params.getParam(useSection, "xpLog", 0) > 0)
    		xp = Math.log(xp)/Math.log(params.getParam(useSection, "xpLog", 0));

    	skill *= params.getParam(useSection, "finalSkillMultiplier", 1);
    	stamina *= params.getParam(useSection, "finalStaminaMultiplier", 1);
    	form *= params.getParam(useSection, "finalFormMultiplier", 1);
    	xp *= params.getParam(useSection, "finalXpMultiplier", 1);
    	
    	skill += params.getParam(useSection, "finalSkillDelta", 0);
    	stamina += params.getParam(useSection, "finalStaminaDelta", 0);
    	form += params.getParam(useSection, "finalFormDelta", 0);
    	xp += params.getParam(useSection, "finalXpDelta", 0);
    	
    	stk = skill;
    	if (params.getParam(useSection, "resultMultiStamina", 0) > 0)
    		stk *= params.getParam(useSection, "resultMultiStamina", 0) * stamina;
    	if (useForm && params.getParam(useSection, "resultMultiForm", 0) > 0)
    		stk *= params.getParam(useSection, "resultMultiForm", 0) * form;
    	if (params.getParam(useSection, "resultMultiXp", 0) > 0)
    		stk *= params.getParam(useSection, "resultMultiXp", 0) * xp;

   		stk += params.getParam(useSection, "resultAddStamina", 0) * stamina;
   		if (useForm)
   			stk += params.getParam(useSection, "resultAddForm", 0) * form;
   		stk += params.getParam(useSection, "resultAddXp", 0) * xp;
   		
//		HOLogger.instance().debug(RatingPredictionManager.class, "Adding to cache: " + key + "=" + stk);
   		playerStrengthCache.put(key, new Double(stk));
   		
//    	long endTime = new Date().getTime();
//    	HOLogger.instance().debug(RatingPredictionManager.class, "calcPlayerStrength (" 
//    			+ "SN=" + sectionName + ",ST" + stamina + ",XP" + xp + ",SK" + skill + ",FO" + form + ",uF" + useForm+ ") took " + (endTime-startTime) + "ms");
    	return stk;
    }

    private void init(ITeam team, short trainerType)
    {
        try
        {
            this.trainerType = trainerType;
            this.attitude = (short)lineup.getAttitude();
            this.heimspiel = lineup.getHeimspiel();
            this.taktikType = (short)lineup.getTacticType();
            this.stimmung = (short)team.getStimmungAsInt();
            this.substimmung = (short)team.getSubStimmung();
            this.selbstvertrauen = (short)team.getSelbstvertrauenAsInt();
            this.pullBackMinute = lineup.getPullBackMinute();
            this.pullBackOverride = lineup.isPullBackOverride();
            return;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
    

    /************************************************************************* 
     * 
     * TacticLevel Functions
     * (AIW, AOW, Counter...)
     * 
     *************************************************************************/
    
    /**
     * get the tactic level for AiM / AoW
     *
     * @return tactic level
     */
    public float getTacticLevelAowAim()
    {
    	IRatingPredictionParameter params = config.getTacticsParameters();
    	double retVal = 0;
    	float passing = 0;
        for(int i = ISpielerPosition.startLineup +1; i < ISpielerPosition.startReserves; i++)
        {
            ISpieler ispieler = lineup.getPlayerByPositionID(i);
            byte taktik = lineup.getTactic4PositionID(i);
            if(ispieler != null) {
            	passing =  calcPlayerStrength(ispieler, PASSING);
            	// Zus. MF/IV/ST
                if(taktik == 7 || taktik == 6 || taktik == 5)
                    passing *= params.getParam("extraMulti", 1.0);
                retVal += passing;
            }
        }

        retVal *= params.getParam("aim_aow", "postMulti", 1.0);
    	retVal = applyCommonProps (retVal, params, "aim_aow");
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
    	return (float)retVal;
    }

    /**
     * get the tactic level for counter
     *
     * @return tactic level
     */
    public float getTacticLevelCounter()
    {
        float deDefender = 0.0F;
        float psDefender = 0.0F;
    	IRatingPredictionParameter params = config.getTacticsParameters();
    	double retVal = 0;
        for(int pos = ISpielerPosition.rightBack; pos <= ISpielerPosition.leftBack; pos++)
        {
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if(spieler != null) {
            		deDefender += calcPlayerStrength(spieler, DEFENDING);
            		psDefender += calcPlayerStrength(spieler, PASSING);
            }
        }
        deDefender *= params.getParam("counter", "multiDe", 1.0);
        psDefender *= params.getParam("counter", "multiPs", 1.0);
        retVal += deDefender + psDefender;
        retVal *= params.getParam("counter", "postMulti", 1.0);
        retVal += params.getParam("counter", "postDelta", 0);
    	retVal = applyCommonProps (retVal, params, "counter");
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
    	return (float)retVal;
    }

    /**
     * get the tactic level for pressing
     *
     * @return tactic level
     */
    public final float getTacticLevelPressing() {
    	IRatingPredictionParameter params = config.getTacticsParameters();
    	double retVal = 0;
        for(int pos = ISpielerPosition.startLineup + 1; pos < ISpielerPosition.startReserves; pos++)
        {
            float defense = 0.0F;
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if(spieler != null) {
            	defense = calcPlayerStrength(spieler, DEFENDING);
                if (spieler.getSpezialitaet() == ISpieler.DURCHSETZUGNSSTARK) {
                	defense *= 2;
                }
                retVal += defense;
            }
        }

        retVal *= params.getParam("pressing", "postMulti", 1.0);
    	retVal = applyCommonProps (retVal, params, "pressing");
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
    	return (float)retVal;
    }

    /**
     * get the tactic level for long shots
     *
     * @return tactic level
     */
    public final float getTacticLevelLongShots() {
       	IRatingPredictionParameter params = config.getTacticsParameters();
    	double retVal = 0;
        for(int pos = ISpielerPosition.startLineup +1; pos < ISpielerPosition.startReserves; pos++)
        {
            float scoring = 0.0F;
            float setpieces = 0.0F;
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if(spieler != null) {
            	scoring = 3*calcPlayerStrength(spieler, SCORING);
            	setpieces = calcPlayerStrength(spieler, SETPIECES);
                retVal += scoring;
                retVal += setpieces;
            }
        }
        
        retVal *= params.getParam("longshots", "postMulti", 1.0);
    	retVal = applyCommonProps (retVal, params, "longshots");
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
    	return (float)retVal;
    }
}
