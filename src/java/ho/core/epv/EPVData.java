package ho.core.epv;

import ho.core.constants.player.PlayerSkill;
import plugins.ISpieler;


/**
 * Holder Class for data needed by EPV function
 *
 * @author draghetto
 */
public class EPVData implements Cloneable {
    
	public static final int KEEPER = 1;
	public static final int DEFENDER = 2;
	public static final int MIDFIELDER = 3;
	public static final int WINGER = 4;
	public static final int ATTACKER = 5;
	
	//~ Instance fields ----------------------------------------------------------------------------
	
	private String playerName;
	private int playerId;	

	private int age;
	private int ageDays;
	
	private int form;
	private int TSI;

	private int experience;
	private int leadership;

	private double attack;
	private double defense;
	private double goalKeeping;
	private double passing;
	private double playMaking;
	private double setPieces;
	private double stamina;
	private double wing;

	private int price;

	private int speciality;
	private int week;
	
	private int aggressivity;
	private int honesty;
	private int popularity;
	
	private double maxSkill;


    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new EPVData object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     */
    public EPVData(ISpieler s) {
        setPlayerName(s.getName());
        setAge(s.getAlter());
        setAgeDays(s.getAgeDays());
        setTSI(s.getTSI());
        setForm(s.getForm());
        setStamina(s.getKondition() + s.getSubskill4SkillWithOffset(PlayerSkill.STAMINA));
        setGoalKeeping(s.getTorwart() + s.getSubskill4SkillWithOffset(PlayerSkill.KEEPER));
        setPlayMaking(s.getSpielaufbau()
                      + s.getSubskill4SkillWithOffset(PlayerSkill.PLAYMAKING));
        setPassing(s.getPasspiel() + s.getSubskill4SkillWithOffset(PlayerSkill.PASSING));
        setWing(s.getFluegelspiel() + s.getSubskill4SkillWithOffset(PlayerSkill.WINGER));
        setDefense(s.getVerteidigung() + s.getSubskill4SkillWithOffset(PlayerSkill.DEFENDING));
        setAttack(s.getTorschuss() + s.getSubskill4SkillWithOffset(PlayerSkill.SCORING));
        setSetPieces(s.getStandards() + s.getSubskill4SkillWithOffset(PlayerSkill.SET_PIECES));

        setExperience(s.getErfahrung());
        setLeadership(s.getFuehrung());
        setAggressivity(s.getAgressivitaet());
        setHonesty(s.getCharakter());
        setPopularity(s.getAnsehen());
        setSpeciality(s.getSpezialitaet());
        
        setPlayerId(s.getSpielerID());
        
        normalizeSkill();
    }
    
  


	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setAge(int i) {
		age = i;
	}

	//	private String aggressivness;
	//	private String gentleness;
	//	private String character;
	public int getAge() {
		return age;
	}

	public int getAgeDays() {
		return ageDays;
	}

	public void setAgeDays(int ageDays) {
		this.ageDays = ageDays;
	}
	
	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setAttack(double i) {
		attack = i;
		checkMaxSkill(i);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getAttack() {
		return attack;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setDefense(double i) {
		defense = i;
		checkMaxSkill(i);		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getDefense() {
		return defense;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setExperience(int i) {
		experience = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setForm(int i) {
		form = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getForm() {
		return form;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setGoalKeeping(double i) {
		goalKeeping = i;
		checkMaxSkill(i);		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getGoalKeeping() {
		return goalKeeping;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setId(int i) {
		playerId = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getId() {
		return playerId;
	}


	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setLeadership(int i) {
		leadership = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getLeadership() {
		return leadership;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setPassing(double i) {
		passing = i;
		checkMaxSkill(i);		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getPassing() {
		return passing;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setPlayMaking(double i) {
		playMaking = i;
		checkMaxSkill(i);		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getPlayMaking() {
		return playMaking;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param string TODO Missing Method Parameter Documentation
	 */
	public void setPlayerName(String string) {
		playerName = string;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setPrice(int i) {
		price = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setStamina(double i) {
		stamina = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getStamina() {
		return stamina;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setSetPieces(double i) {
		setPieces = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getSetPieces() {
		return setPieces;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param string TODO Missing Method Parameter Documentation
	 */
	public void setSpeciality(int se) {
		speciality = se;
	}

	//	public String getAggressivness() {
	//		return aggressivness;
	//	}
	//
	//	public String getGentleness() {
	//		return gentleness;
	//	}
	//
	//	public String getCharacter() {
	//		return character;
	//	}
	//
	//	public void setAggressivness(String string) {
	//		aggressivness = string;
	//	}
	//
	//	public void setGentleness(String string) {
	//		gentleness = string;
	//	}
	//
	//	public void setCharacter(String string) {
	//		character = string;
	//	}
	public int getSpeciality() {
		return speciality;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setTSI(int i) {
		TSI = i;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getTSI() {
		return TSI;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param i TODO Missing Method Parameter Documentation
	 */
	public void setWing(double i) {
		wing = i;
		checkMaxSkill(i);		
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double getWing() {
		return wing;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int i) {
		playerId = i;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int i) {
		week = i;
	}

	public int getAggressivity() {
		return aggressivity;
	}

	public void setAggressivity(int aggressivity) {
		this.aggressivity = aggressivity;
	}

	public int getHonesty() {
		return honesty;
	}

	public void setHonesty(int honesty) {
		this.honesty = honesty;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	/**
	 * toString methode: creates a String representation of the object
	 * @return the String representation
	 * @author info.vancauwenberge.tostring plugin
	
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("EPVData[");
		buffer.append("playerName = "+playerName);
		buffer.append(", speciality = "+speciality);
		buffer.append(", TSI = "+TSI);
		buffer.append(", age = "+age);
		buffer.append(", attack = "+attack);
		buffer.append(", defense = "+defense);
		buffer.append(", experience = "+experience);
		buffer.append(", form = "+form);
		buffer.append(", goalKeeping = "+goalKeeping);
		buffer.append(", leadership = "+leadership);
		buffer.append(", passing = "+passing);
		buffer.append(", playMaking = "+playMaking);
		buffer.append(", playerId = "+playerId);
		buffer.append(", price = "+price);
		buffer.append(", setPieces = "+setPieces);
		buffer.append(", stamina = "+stamina);
		buffer.append(", wing = "+wing);
		buffer.append(", week = "+week);
		buffer.append("]");
		return buffer.toString();
	}

	private void checkMaxSkill(double value) {
		if (value>maxSkill) {
			maxSkill = value;		
		}
	}
	
	public int getPlayerType() {
		if ( (playMaking>=defense) && (playMaking>=wing) &&(playMaking>=attack) &&(playMaking>=goalKeeping) ){
			return MIDFIELDER;
		}
		if ( (defense>=wing) &&(defense>=attack) &&(defense>=goalKeeping) ){
			return DEFENDER;
		}
		if ( (wing>=attack) &&(wing>=goalKeeping) ){
			return WINGER;
		}
		if ( attack>=goalKeeping) {
			return ATTACKER;
		}
		return KEEPER;		
	}
	
	public double getMaxSkill() {
		return maxSkill;
	}


	public void normalizeSkill() {
		// Removed Disastrous since cause problems in calculation
		if (wing<2) { wing = 2; }
		if (passing<2) { passing = 2; }
		if (playMaking<2) { playMaking = 2; }
		if (defense<2) { defense = 2; }
		if (setPieces<2) { setPieces = 2; }
		if (attack<2) { attack = 2; }						
		if (goalKeeping<2) { goalKeeping = 2; }
		if (stamina<2) { stamina = 2; }				
	}

}
