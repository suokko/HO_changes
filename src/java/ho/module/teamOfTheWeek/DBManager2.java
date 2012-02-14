// %2490189340:hoplugins.toTW%
package ho.module.teamOfTheWeek;

import ho.core.db.DBManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import plugins.IJDBCAdapter;
import plugins.IPaarung;
import plugins.ISpielerPosition;
import de.hattrickorganizer.model.matchlist.Spielplan;

public class DBManager2 {

    /**
     * Missing Method Documentation
     *
     * @param week Missing Constructuor Parameter Documentation
     * @param season Missing Constructuor Parameter Documentation
     * @param isBest Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static Map<String,MatchLineupPlayer> getPlayers(int week, Spielplan plan, boolean isBest) {
        IJDBCAdapter db = DBManager.instance().getAdapter();
        Vector<IPaarung> matchIDs =plan.getPaarungenBySpieltag(week);
        // TODO For match of year attention of doubles
        Map<String,MatchLineupPlayer> spieler = new HashMap<String,MatchLineupPlayer>();
        List<MatchLineupPlayer> players = getPlayetAt(db, matchIDs, ISpielerPosition.KEEPER, 1, isBest);
        spieler.put("1", players.get(0));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.BACK, 2, isBest);
        spieler.put("2", players.get(0));
        spieler.put("5", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.CENTRAL_DEFENDER, 2, isBest);
        spieler.put("3", players.get(0));
        spieler.put("4", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.WINGER, 2, isBest);
        spieler.put("6", players.get(0));
        spieler.put("9", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.MIDFIELDER, 2, isBest);
        spieler.put("7", players.get(0));
        spieler.put("8", players.get(1));
        players = getPlayetAt(db, matchIDs, ISpielerPosition.FORWARD, 2, isBest);
        spieler.put("10", players.get(0));
        spieler.put("11", players.get(1));
        return spieler;
    }

    /**
     * Missing Method Documentation
     *
     * @param db Missing Method Parameter Documentation
     * @param matchIDs Missing Method Parameter Documentation
     * @param position Missing Method Parameter Documentation
     * @param number Missing Method Parameter Documentation
     * @param isBest Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private static List<MatchLineupPlayer> getPlayetAt(IJDBCAdapter db, Vector<IPaarung> matchIDs, int position, int number,
                                    boolean isBest) {
        ResultSet rs;
        String posClase = "";

		switch (position) {
			case ISpielerPosition.KEEPER: {
				posClase += " FIELDPOS=" + ISpielerPosition.keeper + " ";
				break;
			}

			case ISpielerPosition.CENTRAL_DEFENDER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftCentralDefender + " OR FIELDPOS=" + 
						ISpielerPosition.middleCentralDefender + " OR FIELDPOS=" + ISpielerPosition.rightCentralDefender + ") ";
				break;
			}

			case ISpielerPosition.BACK: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftBack + " OR FIELDPOS=" + ISpielerPosition.rightBack + ") ";
				break;
			}

			case ISpielerPosition.WINGER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftWinger + " OR FIELDPOS=" + ISpielerPosition.rightWinger + ") ";
				break;
			}

			case ISpielerPosition.MIDFIELDER: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftInnerMidfield + " OR FIELDPOS=" + 
						ISpielerPosition.centralInnerMidfield + " OR FIELDPOS=" + ISpielerPosition.rightInnerMidfield + ") ";
				break;
			}

			case ISpielerPosition.FORWARD: {
				posClase += " (FIELDPOS=" + ISpielerPosition.leftForward + " OR FIELDPOS=" + 
						ISpielerPosition.centralForward + " OR FIELDPOS=" + ISpielerPosition.rightForward + ") ";
				break;
			}
		}

		String matchClause = "";
        for (int i = 0; i < matchIDs.size(); i++) {
            if (matchClause.length() > 1) {
                matchClause += " OR ";
            }
			matchClause += " MATCHID=" + matchIDs.get(i).getMatchId();
        }

		String sql = "SELECT DISTINCT SPIELERID, NAME, RATING, HOPOSCODE, TEAMID FROM MATCHLINEUPPLAYER WHERE "+posClase;
		if (matchClause.length()>1) {
			sql += " AND (" + matchClause + ") ";
		}
        sql += "ORDER BY RATING ";

        if (isBest) {
            sql += " DESC";
        } else {
            sql += " ASC";
        }

        rs = db.executeQuery(sql);

        List<MatchLineupPlayer> ret = new ArrayList<MatchLineupPlayer>();

        for (int i = 0; i < number; i++) {
            ret.add(new MatchLineupPlayer(rs, matchIDs));
        }

        return ret;
    }
}
