// %1073535026:hoplugins%
package de.hattrickorganizer.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hattrickorganizer.logik.MatchPlayerRetriever;
import de.hattrickorganizer.model.MatchPosition;

import plugins.IHOMiniModel;
import plugins.IMatchDetails;

public class MatchUpdaterFake {
	//~ Instance fields ----------------------------------------------------------------------------

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param model TODO Missing Method Parameter Documentation
	 * @param matchId TODO Missing Method Parameter Documentation
	 */
	public static void updateMatch(IHOMiniModel model, int matchId) {
		IMatchDetails matchDetail = model.getMatchDetails(matchId);

		if ((matchDetail.getHeimId() != model.getBasics().getTeamId()) && (matchDetail.getGastId() != model.getBasics().getTeamId())) {
			return;
		}

		MatchPlayerRetriever updater = new MatchPlayerRetriever(model);
		List matchPlayers = updater.getMatchData(matchId);	

		String query = "select ROLEID, SPIELERID,PositionCode from MATCHLINEUPPLAYER where MATCHID = " + matchId + "  and TEAMID = " + model.getBasics().getTeamId();
		ResultSet rs = model.getAdapter().executeQuery(query);
		List updates = new ArrayList();

		try {
			while (rs.next()) {
				try {
					int role = rs.getInt("ROLEID");
					int id = rs.getInt("SPIELERID");
					int pos = rs.getInt("PositionCode");

					if (pos == -1) {
						System.out.println("Player " + id + " " + role + " "+ pos);
						if (role < 12) {
							MatchPosition position = (MatchPosition) matchPlayers.get(role - 1);
							System.out.println("Position " + position.getName() + " " + position.getPlayerID() + " " + position.getPosition());
							updates.add(
								"UPDATE MATCHLINEUPPLAYER SET SPIELERID="
									+ position.getPlayerID()
									+ ", NAME='"
									+ position.getName()
									+ "', STATUS=1, FIELDPOS="
									+ position.getPosition()
									+ " WHERE MATCHID="
									+ matchId
									+ " AND ROLEID="
									+ role
									+ " and TEAMID = "
									+ model.getBasics().getTeamId());
							continue;
						}

						if (role > 18) {
							System.out.println("Injured!!!!") ;							
							for (Iterator iter = matchPlayers.iterator(); iter.hasNext();) {
								MatchPosition position = (MatchPosition) iter.next();																
								if (position.getPlayerID() == id) {
									System.out.println("Match");
									System.out.println("Position " + position.getName() + " " + position.getPlayerID() + " " + position.getPosition());									
									updates.add(
										"UPDATE MATCHLINEUPPLAYER SET SPIELERID="
											+ position.getPlayerID()
											+ ", NAME='"
											+ position.getName()
											+ "', STATUS=2, FIELDPOS="
											+ position.getPosition()
											+ " WHERE MATCHID="
											+ matchId
											+ " AND ROLEID="
											+ role
											+ " and TEAMID = "
											+ model.getBasics().getTeamId());
									continue;
								}
							}
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			for (Iterator iter = updates.iterator(); iter.hasNext();) {
				String queryUpdate = (String) iter.next();
				System.out.println(queryUpdate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
