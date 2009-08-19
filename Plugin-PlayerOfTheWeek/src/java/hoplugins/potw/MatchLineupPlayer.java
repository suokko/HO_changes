package hoplugins.potw;
import java.sql.*;

public class MatchLineupPlayer {

	
	public int TeamID,RoleID,PositionCode,SpielerID,nom;
	public float Rating;
	public String vname,nname,teamname;	
	
	public MatchLineupPlayer(ResultSet rs,String teamname) throws SQLException {
		TeamID = rs.getInt("TEAMID");
		SpielerID = rs.getInt("SPIELERID");
		PositionCode = rs.getInt("HOPOSCODE");
		RoleID = rs.getInt("ROLEID");
		Rating = rs.getFloat("RATING");
		String temp2 = rs.getString("NAME");
		//String[] temp = temp2.split(" ");
		//int takefrom;		
		
//		if (temp[1].equals("")) { nname = temp[2]; } else { nname = temp[1]; }				
				
	
		//vname = temp[0];
		nname = temp2;
		vname = "";
		//nname = temp[1];
		nom = 1;
		this.teamname = teamname;
	}
	
	public MatchLineupPlayer() {
		//fake
		TeamID = -1;
		RoleID = 0;
		PositionCode = 0;
		Rating = -1;
		nom = 1;
		vname = "";
		nname = "";
	}
	
}
