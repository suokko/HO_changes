// %2684565945:hoplugins.toTW%
package ho.module.teamOfTheWeek;

import ho.core.db.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import plugins.IPaarung;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchLineupPlayer {
    //~ Instance fields ----------------------------------------------------------------------------

    private String nname;
    private float Rating;
    private int PositionCode;
    private int SpielerID;
    private int TeamID;
    private String teamName;

    //~ Constructors -------------------------------------------------------------------------------

    public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
     * Creates a new MatchLineupPlayer object.
     */
    public MatchLineupPlayer() {
        TeamID = -1;
        PositionCode = 0;
        Rating = -1F;

        //        nname = "";
    }

    /**
     * Creates a new MatchLineupPlayer object.
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     */
    public MatchLineupPlayer(ResultSet rs,Vector<IPaarung> matches) {
        try {
            rs.next();
            TeamID = rs.getInt("TEAMID");
            SpielerID = rs.getInt("SPIELERID");
            PositionCode = rs.getInt("HOPOSCODE");
            Rating = rs.getFloat("RATING");
            teamName = getTeamName(matches,TeamID);
            
            nname =DBManager.deleteEscapeSequences(rs.getString("NAME"));
        } catch (SQLException e) {
            TeamID = -1;
            PositionCode = 0;
            Rating = -1F;
        }
    }

    
    private String getTeamName(Vector<IPaarung> matches,int teamId){
    	for (Iterator<IPaarung> iterator = matches.iterator(); iterator.hasNext();) {
			IPaarung iPaarung = iterator.next();
			if(teamId == iPaarung.getHeimId())
            	return iPaarung.getHeimName();
            else if(teamId == iPaarung.getGastId())
            	return iPaarung.getGastName();
		}
    	return "";
    }
    
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getNname() {
        return nname;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getPositionCode() {
        return PositionCode;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getRating() {
        return Rating;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSpielerID() {
        return SpielerID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTeamID() {
        return TeamID;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("MatchLineupPlayer[");
        buffer.append("TeamID = ").append(TeamID);
        buffer.append(", PositionCode = ").append(PositionCode);
        buffer.append(", SpielerID = ").append(SpielerID);
        buffer.append(", Rating = ").append(Rating);
        buffer.append(", nname = ").append(nname);
        buffer.append("]");
        return buffer.toString();
    }
}
