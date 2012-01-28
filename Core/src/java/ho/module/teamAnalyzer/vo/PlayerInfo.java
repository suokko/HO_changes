// %2690198073:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

import ho.module.teamAnalyzer.manager.PlayerDataManager;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class PlayerInfo {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    String name = "";

    /** TODO Missing Parameter Documentation */
    int age;

    /** TODO Missing Parameter Documentation */
    int experience;

    /** TODO Missing Parameter Documentation */
    int form;

    /** TODO Missing Parameter Documentation */
    int playerId;

    /** TODO Missing Parameter Documentation */
    int specialEvent;

    /** TODO Missing Parameter Documentation */
    int status = PlayerDataManager.SOLD;

    /** TODO Missing Parameter Documentation */
    int tSI;

    /** TODO Missing Parameter Documentation */
    int teamId;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setAge(int i) {
        age = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getAge() {
        return age;
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
     * @param string TODO Missing Method Parameter Documentation
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return name;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setPlayerId(int i) {
        playerId = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setSpecialEvent(int i) {
        specialEvent = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSpecialEvent() {
        return specialEvent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setStatus(int i) {
        status = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getStatus() {
        return status;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setTSI(int i) {
        tSI = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTSI() {
        return tSI;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setTeamId(int i) {
        teamId = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("PlayerInfo[");
        buffer.append("name = " + name);
        buffer.append(", age = " + age);
        buffer.append(", experience = " + experience);
        buffer.append(", form = " + form);
        buffer.append(", playerId = " + playerId);
        buffer.append(", specialEvent = " + specialEvent);
        buffer.append(", status = " + status);
        buffer.append(", tSI = " + tSI);
        buffer.append(", teamId = " + teamId);
        buffer.append("]");
        return buffer.toString();
    }
}
