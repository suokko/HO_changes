// %3800124443:de.hattrickorganizer.gui.transferscout%
package de.hattrickorganizer.gui.transferscout;

/**
 * Player used for PlayerConverter (TransferScout)
 *
 * @author Marco Senn
 */
public class Player {
    //~ Instance fields ----------------------------------------------------------------------------

    private String expiryDate;
    private String expiryTime;
    private String playerName;
    private int age;
    private int attack;
    private int defense;
    private int experience;
    private int form;
    private int goalKeeping;
    private int injury;
    private int leadership;
    private int passing;
    private int playMaking;
    private int playerId;
    private int price;
    private int setPieces;
    private int speciality;
    private int stamina;
    private int tsi;
    private int wing;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Player object.
     */
    public Player() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for age
     *
     * @param i Set age to i
     */
    public final void setAge(int i) {
        age = i;
    }

    /**
     * Getter for age
     *
     * @return Returns age
     */
    public final int getAge() {
        return age;
    }

    /**
     * Setter for attack
     *
     * @param i Set attack to i
     */
    public final void setAttack(int i) {
        attack = i;
    }

    /**
     * Getter for attack
     *
     * @return Returns attack
     */
    public final int getAttack() {
        return attack + 1;
    }

    /**
     * Setter for defense
     *
     * @param i Set defense to i
     */
    public final void setDefense(int i) {
        defense = i;
    }

    /**
     * Getter for defense
     *
     * @return Returns defense
     */
    public final int getDefense() {
        return defense + 1;
    }

    /**
     * Setter for experience
     *
     * @param i Set experience to i
     */
    public final void setExperience(int i) {
        experience = i;
    }

    /**
     * Getter for experience
     *
     * @return Returns experience
     */
    public final int getExperience() {
        return experience + 1;
    }

    /**
     * Setter for expiryDate
     *
     * @param string Set expiryDate to string
     */
    public final void setExpiryDate(String string) {
        expiryDate = string;
    }

    /**
     * Getter for expiryDate
     *
     * @return Returns expiryDate
     */
    public final String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Setter for expiryTime
     *
     * @param string Set expiryTime to string
     */
    public final void setExpiryTime(String string) {
        expiryTime = string;
    }

    /**
     * Getter for expiryTime
     *
     * @return Returns expiryTime
     */
    public final String getExpiryTime() {
        return expiryTime;
    }

    /**
     * Setter for form
     *
     * @param i Set form to i
     */
    public final void setForm(int i) {
        form = i;
    }

    /**
     * Getter for form
     *
     * @return Returns form
     */
    public final int getForm() {
        return form + 1;
    }

    /**
     * Setter for goalKeeping
     *
     * @param i Set goalKeeping to i
     */
    public final void setGoalKeeping(int i) {
        goalKeeping = i;
    }

    /**
     * Getter for goalKeeping
     *
     * @return Returns goalKeeping
     */
    public final int getGoalKeeping() {
        return goalKeeping + 1;
    }

    /**
     * Get some informations about player
     *
     * @return Returns text with player informations
     */
    public final String getInfo() {
        String info = "";

        if (getLeadership() > 0) {
            final de.hattrickorganizer.tools.PlayerHelper ph = new de.hattrickorganizer.tools.PlayerHelper();
            info = info
                   + de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Fuehrung")
                   + ": " + ph.getNameForSkill(getLeadership()) + "\r\n";
        }

        if (getInjury() > 0) {
            final String tmp = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                      .getProperty("scout_injury");
            info = info + tmp.replaceAll("%weeks%", String.valueOf(getInjury())) + "\r\n";
        }

        return info;
    }

    /**
     * Setter for injury
     *
     * @param i Set injury to i
     */
    public final void setInjury(int i) {
        injury = i;
    }

    /**
     * Getter for injury
     *
     * @return Returns injury
     */
    public final int getInjury() {
        return injury;
    }

    /**
     * Setter for leadership
     *
     * @param i Set leadership to i
     */
    public final void setLeadership(int i) {
        leadership = i;
    }

    /**
     * Getter for leadership
     *
     * @return Returns leadership
     */
    public final int getLeadership() {
        return leadership + 1;
    }

    /**
     * Setter for passing
     *
     * @param i Set passing to i
     */
    public final void setPassing(int i) {
        passing = i;
    }

    /**
     * Getter for passing
     *
     * @return Returns passing
     */
    public final int getPassing() {
        return passing + 1;
    }

    /**
     * Setter for playmaking
     *
     * @param i Set playmaking to i
     */
    public final void setPlayMaking(int i) {
        playMaking = i;
    }

    /**
     * Getter for playmaking
     *
     * @return Returns playmaking
     */
    public final int getPlayMaking() {
        return playMaking + 1;
    }

    /**
     * Setter for playerId
     *
     * @param i Set playerId to i
     */
    public final void setPlayerID(int i) {
        playerId = i;
    }

    /**
     * Getter for playerId
     *
     * @return Returns playerId
     */
    public final int getPlayerID() {
        return playerId;
    }

    /**
     * Setter for playerName
     *
     * @param string Set playerName to string
     */
    public final void setPlayerName(String string) {
        playerName = string;
    }

    /**
     * Getter for playerName
     *
     * @return Returns playerName
     */
    public final String getPlayerName() {
        return playerName;
    }

    /**
     * Setter for price
     *
     * @param i Set price to i
     */
    public final void setPrice(int i) {
        price = i;
    }

    /**
     * Getter for price
     *
     * @return Returns price
     */
    public final int getPrice() {
        return price;
    }

    /**
     * Setter for setPieces
     *
     * @param i Set setPieces to i
     */
    public final void setSetPieces(int i) {
        setPieces = i;
    }

    /**
     * Getter for setPieces
     *
     * @return Returns setPieces
     */
    public final int getSetPieces() {
        return setPieces + 1;
    }

    /**
     * Setter for speciality
     *
     * @param i Set speciality to i
     */
    public final void setSpeciality(int i) {
        speciality = i;
    }

    /**
     * Getter for speciality
     *
     * @return Returns speciality
     */
    public final int getSpeciality() {
        return speciality;
    }

    /**
     * Setter for stamina
     *
     * @param i Set stamina to i
     */
    public final void setStamina(int i) {
        stamina = i;
    }

    /**
     * Getter for stamina
     *
     * @return Returns stamina
     */
    public final int getStamina() {
        return stamina + 1;
    }

    /**
     * Setter for tsi
     *
     * @param i Set tsi to i
     */
    public final void setTSI(int i) {
        tsi = i;
    }

    /**
     * Getter for tsi
     *
     * @return Returns tsi
     */
    public final int getTSI() {
        return tsi;
    }

    /**
     * Setter for wing
     *
     * @param i Set wing to i
     */
    public final void setWing(int i) {
        wing = i;
    }

    /**
     * Getter for wing
     *
     * @return Returns wing
     */
    public final int getWing() {
        return wing + 1;
    }

    /**
     * Creates a string representation of the object
     *
     * @return Returns a string representation of the object
     */
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("playerName = " + playerName);
        buffer.append(", price = " + price);
        buffer.append(", playerId = " + playerId);
        buffer.append(", speciality = " + speciality);
        buffer.append(", TSI = " + tsi);
        buffer.append(", expiryDate = " + expiryDate + " " + expiryTime);
        buffer.append(", experience = " + experience);
        buffer.append(", form = " + form);
        buffer.append(", stamina = " + stamina);
        buffer.append(", goalKeeping = " + goalKeeping);
        buffer.append(", playMaking = " + playMaking);
        buffer.append(", passing = " + passing);
        buffer.append(", wing = " + wing);
        buffer.append(", defense = " + defense);
        buffer.append(", attack = " + attack);
        buffer.append(", setPieces = " + setPieces);
        buffer.append(", info = " + getInfo());
        return buffer.toString();
    }
}
