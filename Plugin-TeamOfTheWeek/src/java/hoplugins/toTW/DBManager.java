// %2490189340:hoplugins.toTW%
package hoplugins.toTW;

import hoplugins.Commons;
import hoplugins.TotW;

import hoplugins.toTW.vo.LigaItem;
import hoplugins.toTW.vo.MatchLineupPlayer;

import plugins.IJDBCAdapter;
import plugins.ISpielerPosition;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 553 changes by Blaghaid

/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class DBManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static List<LigaItem> getLeagues() {
        IJDBCAdapter db = TotW.getModel().getAdapter();
        String sqlstmt = "SELECT LIGAID, LIGANAME, SAISON FROM SPIELPLAN";
        ResultSet rs = db.executeQuery(sqlstmt);
        List<LigaItem> l = new ArrayList<LigaItem>();

        try {
            while (rs.next()) {
                LigaItem item = new LigaItem();
                item.setLigaId(rs.getInt("LIGAID"));
                item.setName(rs.getString("LIGANAME"));
                item.setSeason(rs.getInt("SAISON"));
                l.add(item);
            }
        } catch (SQLException e) {
        }

        return l;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param week TODO Missing Method Parameter Documentation
     * @param season TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List<String> getMatchList(int week, int season) {
        IJDBCAdapter db = TotW.getModel().getAdapter();
        String sql = "SELECT * FROM PAARUNG WHERE SAISON='" + season + "'";

        if (week > -1) {
            sql = sql + " AND SPIELTAG='" + week + "'";
        }

        ResultSet rs = db.executeQuery(sql);
        List<String> matchIDs = new ArrayList<String>();

        try {
            while (rs.next()) {
                matchIDs.add(rs.getString("MATCHID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchIDs;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Constructuor Parameter Documentation
     * @param season Missing Constructuor Parameter Documentation
     * @param isBest Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static Map<String,MatchLineupPlayer> getPlayers(int week, int season, boolean isBest) {
        IJDBCAdapter db = TotW.getModel().getAdapter();
        List<String> matchIDs = getMatchList(week, season);

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
     * TODO Missing Method Documentation
     *
     * @param week TODO Missing Method Parameter Documentation
     * @param season TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List<String> getTeamList(int week, int season) {
        List<String> ids = new ArrayList<String>();
        String sql = "SELECT * FROM PAARUNG WHERE SAISON='" + season + "'";
        sql += (" AND SPIELTAG='" + week + "'");

        ResultSet rs = Commons.getModel().getAdapter().executeQuery(sql);

        try {
            while (rs.next()) {
                ids.add("" + rs.getInt("HEIMID"));
                ids.add("" + rs.getInt("GASTID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
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
    private static List<MatchLineupPlayer> getPlayetAt(IJDBCAdapter db, List<String> matchIDs, int position, int number,
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
            String id = (String) matchIDs.get(i);
			matchClause += " MATCHID='" + id + "' ";
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
            ret.add(new MatchLineupPlayer(rs));
        }

        return ret;
    }
}
