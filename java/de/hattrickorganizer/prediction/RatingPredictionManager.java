package de.hattrickorganizer.prediction;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

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
    private short heimspiel;
    private short attitude;
    private short selbstvertrauen;
    private short stimmung;
    private short substimmung;
    private short taktikType;
    private short trainerType;
    private static IRatingPredictionConfig config;
    private ILineUp lineup;

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

    private static final int TORWART = ISpieler.SKILL_TORWART; // 0
    private static final int VERTEIDIGUNG = ISpieler.SKILL_VERTEIDIGUNG; // 1
    private static final int FLUEGEL = ISpieler.SKILL_FLUEGEL; // 2
    private static final int SPIELAUFBAU = ISpieler.SKILL_SPIELAUFBAU; // 3
    private static final int TORSCHUSS = ISpieler.SKILL_TORSCHUSS; // 4
    private static final int PASSSPIEL = ISpieler.SKILL_PASSSPIEL; // 5
    private static final int KONDITION = ISpieler.SKILL_KONDITION; // 6
    private static final int FORM = ISpieler.SKILL_FORM; // 7
    private static final int STANDARDS = ISpieler.SKILL_STANDARDS; // 8
    private static final int EXPIERIENCE = ISpieler.SKILL_EXPIERIENCE; // 9
    private static final int LEADERSHIP = ISpieler.SKILL_LEADERSHIP; // 10

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
    	Hashtable allSections = params.getAllSections();
    	Enumeration allKeys = allSections.keys();
    	double retVal = 0;
    	while (allKeys.hasMoreElements()) {
    		String sectionName = (String)allKeys.nextElement();
    		double curValue = calcPartialRating (params, sectionName, side2calc);
//    		HOLogger.instance().debug(this.getClass(), "PartRating for type "+type+", section "+sectionName+" is "+curValue);
    		retVal += curValue;
    	}
    	retVal = applyCommonProps (retVal, params, IRatingPredictionParameter.GENERAL);
//    	HOLogger.instance().debug(this.getClass(), "Prediction ["+config.getPredictionName()+"] FullRating for type "+type+" is "+retVal);    	
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
    	double[] allStk;
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
    	double[] allWeights = getAllPlayerWeights(params, sectionName);
//    	System.out.println ("calcPartRating: using sidetype="+sideType+", side2calc="+side2calc);
    	for (int i=0; i < allStk.length; i++) {
//    		if (skillType == SPIELAUFBAU)
//    			System.out.println ("addingPlayer @"+i+": (skill "+skillType+") stk="+allStk[i] + " * weight="+ allWeights[i]+" = "+allStk[i] * allWeights[i]);
			retVal += allStk[i] * allWeights[i];
    	}
    	retVal = applyCommonProps (retVal, params, sectionName);
    	return retVal;
    }

    public double applyCommonProps (double inVal, IRatingPredictionParameter params, String sectionName) {
    	double retVal = inVal;
    	// TODO Reihenfolge ok?
        retVal *= params.getParam(sectionName, "multiplier", 1);
        retVal *= (1.0 + params.getParam(sectionName, "squareMod", 0) * retVal); // Avoid if possible! 

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

        retVal += params.getParam(sectionName, "delta", 0);
        
//    	System.out.println ("applyCommonProps: section "+sectionName+", before="+inVal+", after="+retVal);
    	return retVal;
    }
    
    private static String getSkillName (int skill) {
    	switch (skill) {
		case TORWART:
			return "goalkeeping";
		case VERTEIDIGUNG:
			return "defending";
		case FLUEGEL:
			return "winger";
		case SPIELAUFBAU:
			return "playmaking";
		case TORSCHUSS:
			return "scoring";
		case PASSSPIEL:
			return "passing";
		case STANDARDS:
			return "setpieces";
		default:
			return "";
		}
    }

    private static int getSkillByName (String skillName) {
    	skillName = skillName.toLowerCase();
    	if (skillName.equals("goalkeeping"))
    		return TORWART;
    	else if (skillName.equals("defending"))
    		return VERTEIDIGUNG;
    	else if (skillName.equals("winger"))
    		return FLUEGEL;
    	else if (skillName.equals("playmaking"))
    		return SPIELAUFBAU;
    	else if (skillName.equals("scoring"))
    		return TORSCHUSS;
    	else if (skillName.equals("passing"))
    		return PASSSPIEL;
    	else if (skillName.equals("setpieces"))
    		return STANDARDS;
    	else
    		return -1;
    }
    
    private static int getSideByName (String sideName) {
    	sideName = sideName.toLowerCase();
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


    public double[] getAllPlayerWeights (IRatingPredictionParameter params, String sectionName) {
    	double[] weights = new double[ISpielerPosition.NUM_POSITIONS];
		weights[ISpielerPosition.TORWART] = params.getParam(sectionName, "keeper");
		weights[ISpielerPosition.TORWART] += params.getParam(sectionName, "gk");	// alias for keeper
		double modCD = params.getParam(sectionName, "allCDs", 1);
		double modWB = params.getParam(sectionName, "allWBs", 1);
		double modIM = params.getParam(sectionName, "allIMs", 1);
		double modWI = params.getParam(sectionName, "allWIs", 1);
		double modFW = params.getParam(sectionName, "allFWs", 1);
		weights[ISpielerPosition.INNENVERTEIDIGER] = params.getParam(sectionName, "cd_norm") * modCD;
		weights[ISpielerPosition.INNENVERTEIDIGER] += params.getParam(sectionName, "cd") * modCD;	// alias for cd_norm
		weights[ISpielerPosition.INNENVERTEIDIGER_OFF] = params.getParam(sectionName, "cd_off") * modCD;
		weights[ISpielerPosition.INNENVERTEIDIGER_AUS] = params.getParam(sectionName, "cd_tw") * modCD;
		weights[ISpielerPosition.AUSSENVERTEIDIGER] = params.getParam(sectionName, "wb_norm") * modWB;
		weights[ISpielerPosition.AUSSENVERTEIDIGER] += params.getParam(sectionName, "wb") * modWB;	// alias for wb_norm
		weights[ISpielerPosition.AUSSENVERTEIDIGER_OFF] = params.getParam(sectionName, "wb_off") * modWB;
		weights[ISpielerPosition.AUSSENVERTEIDIGER_DEF] = params.getParam(sectionName, "wb_def") * modWB;
		weights[ISpielerPosition.AUSSENVERTEIDIGER_IN] = params.getParam(sectionName, "wb_tm") * modWB;
		weights[ISpielerPosition.MITTELFELD] = params.getParam(sectionName, "im_norm") * modIM;
		weights[ISpielerPosition.MITTELFELD] += params.getParam(sectionName, "im") * modIM;	// alias for im_norm
		weights[ISpielerPosition.MITTELFELD_OFF] = params.getParam(sectionName, "im_off") * modIM;
		weights[ISpielerPosition.MITTELFELD_DEF] = params.getParam(sectionName, "im_def") * modIM;
		weights[ISpielerPosition.MITTELFELD_AUS] = params.getParam(sectionName, "im_tw") * modIM;
		weights[ISpielerPosition.FLUEGELSPIEL] = params.getParam(sectionName, "wi_norm") * modWI;
		weights[ISpielerPosition.FLUEGELSPIEL] += params.getParam(sectionName, "wi") * modWI;	// alias for wi_norm
		weights[ISpielerPosition.FLUEGELSPIEL_OFF] = params.getParam(sectionName, "wi_off") * modWI;
		weights[ISpielerPosition.FLUEGELSPIEL_DEF] = params.getParam(sectionName, "wi_def") * modWI;
		weights[ISpielerPosition.FLUEGELSPIEL_IN] = params.getParam(sectionName, "wi_tm") * modWI;
		weights[ISpielerPosition.STURM] = params.getParam(sectionName, "fw_norm") * modFW;
		weights[ISpielerPosition.STURM] += params.getParam(sectionName, "fw") * modFW;	// alias for fw_norm
		weights[ISpielerPosition.STURM_DEF] = params.getParam(sectionName, "fw_def") * modFW;
		weights[ISpielerPosition.STURM_AUS] = params.getParam(sectionName, "fw_tw") * modFW;
		weights[ISpielerPosition.POS_ZUS_INNENV] = params.getParam(sectionName, "extra_cd") * modCD;
		weights[ISpielerPosition.POS_ZUS_MITTELFELD] = params.getParam(sectionName, "extra_im") * modIM;
		weights[ISpielerPosition.POS_ZUS_STUERMER] = params.getParam(sectionName, "extra_fw") * modFW;
		weights[ISpielerPosition.POS_ZUS_INNENV] += params.getParam(sectionName, "cd_xtra") * modCD;	// alias for extra_cd
		weights[ISpielerPosition.POS_ZUS_MITTELFELD] += params.getParam(sectionName, "im_xtra") * modIM;	// alias for extra_im
		weights[ISpielerPosition.POS_ZUS_STUERMER] += params.getParam(sectionName, "fw_xtra") * modFW;	// alias for extra_fw
    	return weights;
    }
    
    public double[] getAllPlayerStrength (int skillType) {
    	return getAllPlayerStrength(skillType, true, true, true);
    }

    public double[] getAllPlayerStrengthLeft (int skillType) {
    	return getAllPlayerStrength(skillType, true, false, false);
    }

    public double[] getAllPlayerStrengthRight (int skillType) {
    	return getAllPlayerStrength(skillType, false, false, true);
    }

    public double[] getAllPlayerStrengthMiddle (int skillType) {
    	return getAllPlayerStrength(skillType, false, true, false);
    }

    private int getNumIMs () {
    	int retVal = 0;
    	for(int pos = 1; pos < 12; pos++) {
    		byte taktik = lineup.getTactic4PositionID(pos);
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) { 
            	if (taktik == ISpielerPosition.ZUS_MITTELFELD)
            		retVal++;
            	else if (taktik != ISpielerPosition.ZUS_INNENV  &&
            			taktik != ISpielerPosition.ZUS_STUERMER &&
            			(pos == ISpielerPosition.insideMid1 || pos == ISpielerPosition.insideMid2))
            		retVal++;
            }
    	}
    	return retVal;
    }

    private int getNumFWs () {
    	int retVal = 0;
        for(int pos = 1; pos < 12; pos++) {
        	byte taktik = lineup.getTactic4PositionID(pos);
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) { 
            	if (taktik == ISpielerPosition.ZUS_STUERMER)
            		retVal++;
            	else if (taktik != ISpielerPosition.ZUS_INNENV  &&
        					taktik != ISpielerPosition.ZUS_MITTELFELD &&
        					(pos == ISpielerPosition.forward1 || pos == ISpielerPosition.forward2))
            		retVal++;
            }
    	}
    	return retVal;
    }

    private int getNumCDs () {
    	int retVal = 0;
    	for(int pos = 1; pos < 12; pos++) {
    		byte taktik = lineup.getTactic4PositionID(pos);
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            if (spieler != null) {     		
            	if (taktik == ISpielerPosition.ZUS_INNENV)
            		retVal++;
            	else if (taktik != ISpielerPosition.ZUS_MITTELFELD &&
            				taktik != ISpielerPosition.ZUS_STUERMER &&
            				(pos == ISpielerPosition.insideBack1 || pos == ISpielerPosition.insideBack2 ))
            		retVal++;
            }
    	}
    	return retVal;
    }
    

    private boolean isLeft (int pos, int taktik) {
    	if (isMiddle(pos, taktik))
    		return false;
    	else if (pos == ISpielerPosition.insideBack2 
				|| pos == ISpielerPosition.insideMid2
				|| pos == ISpielerPosition.leftBack 
				|| pos == ISpielerPosition.leftWinger
				|| pos == ISpielerPosition.forward2)
			return true;
		else
			return false;
    }

    private boolean isRight (int  pos, int taktik) {
    	if (isMiddle(pos, taktik))
    		return false;
    	else if (pos == ISpielerPosition.insideBack1 
				|| pos == ISpielerPosition.insideMid1
				|| pos == ISpielerPosition.rightBack 
				|| pos == ISpielerPosition.rightWinger
				|| pos == ISpielerPosition.forward1)
			return true;
		else
			return false;
    }
    
    private boolean isMiddle (int pos, int taktik) {
    	if (taktik == ISpielerPosition.ZUS_INNENV 
				|| taktik == ISpielerPosition.ZUS_MITTELFELD
				|| taktik == ISpielerPosition.ZUS_STUERMER 
				|| pos == ISpielerPosition.keeper)
			return true;
    	else if (taktik == ISpielerPosition.NACH_AUSSEN)
    		return false;
		else if (getNumCDs() == 1 && (pos == ISpielerPosition.insideBack1 || pos == ISpielerPosition.insideBack2))
    		return true;
    	else if (getNumIMs() == 1 && (pos == ISpielerPosition.insideMid1 || pos == ISpielerPosition.insideMid2))
    		return true;
    	else if (getNumFWs() == 1 && (pos == ISpielerPosition.forward1 || pos == ISpielerPosition.forward2))
    		return true;
    	else
    		return false;
    }

    public double[] getAllPlayerStrength (int skillType, boolean useLeft, boolean useMiddle, boolean useRight) {
    	double[] retArray = new double[ISpielerPosition.NUM_POSITIONS];
//    	System.out.println ("getAllPlayerStrength: st="+skillType+", l="+useLeft+", m="+useMiddle+", r="+useRight);
        for(int pos = 1; pos < 12; pos++)
        {
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            byte taktik = lineup.getTactic4PositionID(pos);
            if(spieler != null) {
//            	System.out.println ("getAllPlayerStrength."+pos+", id="+spieler.getSpielerID()+", taktik="+taktik);
            	// Check sides
            	if (!useLeft && isLeft(pos, taktik)
            			|| !useMiddle && isMiddle(pos, taktik)
            			|| !useRight && isRight(pos, taktik)) {
            		continue;
            	}
            	// Extra CD
            	else if (taktik == ISpielerPosition.ZUS_INNENV)
            		retArray[ISpielerPosition.POS_ZUS_INNENV] += calcPlayerStrength(spieler, skillType);
            	// Extra IM
            	else if (taktik == ISpielerPosition.ZUS_MITTELFELD)
            		retArray[ISpielerPosition.POS_ZUS_MITTELFELD] += calcPlayerStrength(spieler, skillType);
            	// Extra FW
            	else if (taktik == ISpielerPosition.ZUS_STUERMER)
            		retArray[ISpielerPosition.POS_ZUS_STUERMER] += calcPlayerStrength(spieler, skillType);
            	
            	else switch (pos) {
            	case ISpielerPosition.keeper:
            		retArray[ISpielerPosition.TORWART] += calcPlayerStrength(spieler, skillType);
            		break;
            	case ISpielerPosition.insideBack1:
            	case ISpielerPosition.insideBack2:
            		if (taktik == ISpielerPosition.NORMAL)
            			retArray[ISpielerPosition.INNENVERTEIDIGER] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.OFFENSIV)
            			retArray[ISpielerPosition.INNENVERTEIDIGER_OFF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.NACH_AUSSEN)
            			retArray[ISpielerPosition.INNENVERTEIDIGER_AUS] += calcPlayerStrength(spieler, skillType);
            		break;
            	case ISpielerPosition.rightBack:
            	case ISpielerPosition.leftBack:
            		if (taktik == ISpielerPosition.NORMAL)
            			retArray[ISpielerPosition.AUSSENVERTEIDIGER] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.OFFENSIV)
            			retArray[ISpielerPosition.AUSSENVERTEIDIGER_OFF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.DEFENSIV)
            			retArray[ISpielerPosition.AUSSENVERTEIDIGER_DEF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.ZUR_MITTE)
            			retArray[ISpielerPosition.AUSSENVERTEIDIGER_IN] += calcPlayerStrength(spieler, skillType);
            		break;
            	case ISpielerPosition.rightWinger:
            	case ISpielerPosition.leftWinger:
            		if (taktik == ISpielerPosition.NORMAL)
            			retArray[ISpielerPosition.FLUEGELSPIEL] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.OFFENSIV)
            			retArray[ISpielerPosition.FLUEGELSPIEL_OFF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.DEFENSIV)
            			retArray[ISpielerPosition.FLUEGELSPIEL_DEF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.ZUR_MITTE)
            			retArray[ISpielerPosition.FLUEGELSPIEL_IN] += calcPlayerStrength(spieler, skillType);
            		break;
            	case ISpielerPosition.insideMid1:
            	case ISpielerPosition.insideMid2:
            		if (taktik == ISpielerPosition.NORMAL)
            			retArray[ISpielerPosition.MITTELFELD] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.OFFENSIV)
            			retArray[ISpielerPosition.MITTELFELD_OFF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.DEFENSIV)
            			retArray[ISpielerPosition.MITTELFELD_DEF] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.NACH_AUSSEN)
            			retArray[ISpielerPosition.MITTELFELD_AUS] += calcPlayerStrength(spieler, skillType);
            		break;
            	case ISpielerPosition.forward1:
            	case ISpielerPosition.forward2:
            		if (taktik == ISpielerPosition.NORMAL)
            			retArray[ISpielerPosition.STURM] += calcPlayerStrength(spieler, skillType);
            		else if (taktik == ISpielerPosition.DEFENSIV) {
            			// TODO flattermann:
            			// PS bonus for def technical to parameter file
            			if (spieler.getSpezialitaet() == ISpieler.BALLZAUBERER && skillType == PASSSPIEL)
            				retArray[ISpielerPosition.STURM_DEF] += 1.5 * calcPlayerStrength(spieler, skillType);
            			else
            				retArray[ISpielerPosition.STURM_DEF] += calcPlayerStrength(spieler, skillType);
            		} else if (taktik == ISpielerPosition.NACH_AUSSEN)
            			retArray[ISpielerPosition.STURM_AUS] += calcPlayerStrength(spieler, skillType);
            		break;
            	}
            }
        }
        return retArray;
    }
    
    public float getMFRatings ()
    {
    	return calcRatings(MIDFIELD);
    }

    public float calcPlayerStrength(ISpieler spieler, int skillType) {
    	return calcPlayerStrength(spieler, skillType, true);
    }

    public float calcPlayerStrength(ISpieler spieler, int skillType, boolean useForm) {
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
              subSkill = getSubDeltaFromConfig (config.getPlayerStrenghParameters(), getSkillName(skillType), (int)skill);
            // subSkill>1, this should not happen
            if (subSkill > 1)
            	subSkill = 1;
            skill = skill + subSkill;
            retVal = calcPlayerStrength(config.getPlayerStrenghParameters(), 
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
            return;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
    

    /************************************************************************* 
     * 
     * Tactic Rating Functions
     * (AIW, AOW, Counter...)
     * 
     *************************************************************************/
    
    public float getAow_AimRatings()
    {
        float stkSum = 0.0F;
        float stkSum4Zusatz = 0.0F;
        for(int i = 2; i < 12; i++)
        {
            ISpieler ispieler = lineup.getPlayerByPositionID(i);
            byte taktik = lineup.getTactic4PositionID(i);
            if(ispieler != null)
            	// Zus. MF/IV/ST
                if(taktik == 7 || taktik == 6 || taktik == 5)
                    stkSum4Zusatz += calcPlayerStrength(ispieler, PASSSPIEL);
                else
                    stkSum += calcPlayerStrength(ispieler, PASSSPIEL);
        }

        return 0.20113F * (stkSum + 1.0275F * stkSum4Zusatz) + 1.3532F;
        // Why does the Extra player have a higher impact???
        // Alternative:
        // return 0.20113F * (stkSum * 1.0275F + stkSum4Zusatz) + 1.3532F;
    }

    public float getKonterRatings()
    {
        float deDefender = 0.0F;
        float deExtraCD = 0.0F;
        float psDefender = 0.0F;
        float psExtraCD = 0.0F;
        for(int pos = 2; pos < 12; pos++)
        {
            ISpieler spieler = lineup.getPlayerByPositionID(pos);
            byte taktik = lineup.getTactic4PositionID(pos);
            if(spieler != null)
            {
            	// CD/WB (NOT extra CD)
                if(pos >= 2 && pos <= 5 && taktik < 5)
                {
                    deDefender += calcPlayerStrength(spieler, VERTEIDIGUNG);
                    psDefender += calcPlayerStrength(spieler, PASSSPIEL);
                }
                // extra CD
                if(taktik == 7)
                {
                    deExtraCD += calcPlayerStrength(spieler, VERTEIDIGUNG);
                    psExtraCD += calcPlayerStrength(spieler, PASSSPIEL);
                }
            }
        }

        return 0.11558F * (deDefender + 1.2267F * deExtraCD + 2.0F * (psDefender + 1.2267F * psExtraCD)) + 1.1799F;
        // Why does the Extra player have a higher impact???
        // Alternative:
        // return 0.11558F * (deDefender * 1.2267F + deExtraCD + 2.0F * (psDefender * 1.2267F + psExtraCD)) + 1.1799F;
    }

}
