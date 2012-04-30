// %3018375595:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.db.DBManager;
import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.util.HelperWrapper;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.ht.HattrickManager;
import ho.module.teamAnalyzer.vo.Match;
import ho.module.teamAnalyzer.vo.MatchDetail;
import ho.module.teamAnalyzer.vo.MatchRating;
import ho.module.teamAnalyzer.vo.PlayerPerformance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchPopulator {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static List<MatchDetail> analyzedMatch = new ArrayList<MatchDetail>();

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List<MatchDetail> getAnalyzedMatch() {
        return analyzedMatch;
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void clean() {
        analyzedMatch = new ArrayList<MatchDetail>();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matches TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public List<MatchDetail> populate(List<Match> matches) {
        List<MatchDetail> list = new ArrayList<MatchDetail>();

        analyzedMatch = new ArrayList<MatchDetail>();
        boolean bOK = true;
        for (Iterator<Match> iter = matches.iterator(); iter.hasNext();) {
        	bOK = true;
            Match element = iter.next();
            if (!DBManager.instance().isMatchVorhanden(element.getMatchId()) && HattrickManager.isDownloadAllowed(element)) {
                bOK = downloadMatch(element.getMatchId());
            }
            if (bOK) {
	            try {
	                MatchDetail md = populateMatch(element);
	                if (md != null) {
	                    list.add(md);
	                    analyzedMatch.add(md);
	                }
	            } catch (RuntimeException e) {
	                // DO NOTHING
	            }
            } else {
            	break;
            }
        }
        return list;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aMatchDetail TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int getTacticLevel(Matchdetails aMatchDetail) {
        if (isHome(aMatchDetail)) {
            return aMatchDetail.getHomeTacticSkill();
        } else {
            return aMatchDetail.getGuestTacticSkill();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aMatchDetail TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int getTacticType(Matchdetails aMatchDetail) {
        if (isHome(aMatchDetail)) {
            return aMatchDetail.getHomeTacticType();
        } else {
            return aMatchDetail.getGuestTacticType();
        }
    }

    /**
     * Gets a calculator for match ratings.
     *
     * @param aMatchDetail Match details
     *
     * @return Match ratings calculator
     */
    private MatchRating buildMatchRating(Matchdetails aMatchDetail) {
        MatchRating mr = new MatchRating();

        if (isHome(aMatchDetail)) {
            mr.setMidfield(aMatchDetail.getHomeMidfield());
            mr.setLeftAttack(aMatchDetail.getHomeLeftAtt());
            mr.setLeftDefense(aMatchDetail.getHomeLeftDef());
            mr.setCentralAttack(aMatchDetail.getHomeMidAtt());
            mr.setCentralDefense(aMatchDetail.getHomeMidDef());
            mr.setRightAttack(aMatchDetail.getHomeRightAtt());
            mr.setRightDefense(aMatchDetail.getHomeRightDef());
        } else {
            mr.setMidfield(aMatchDetail.getGuestMidfield());
            mr.setLeftAttack(aMatchDetail.getGuestLeftAtt());
            mr.setLeftDefense(aMatchDetail.getGuestLeftDef());
            mr.setCentralAttack(aMatchDetail.getGuestMidAtt());
            mr.setCentralDefense(aMatchDetail.getGuestMidDef());
            mr.setRightAttack(aMatchDetail.getGuestRightAtt());
            mr.setRightDefense(aMatchDetail.getGuestRightDef());
        }

        return mr;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private boolean downloadMatch(int matchId) {
        return HelperWrapper.instance().downloadMatchData(matchId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aMatch TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private MatchDetail populateMatch(Match aMatch) {
    	Matchdetails tmpMatch = DBManager.instance().getMatchDetails(aMatch.getMatchId());

        MatchDetail matchDetail = new MatchDetail(aMatch);

        MatchLineupTeam tmpLineupTeam = null;

        if (isHome(tmpMatch)) {
            tmpLineupTeam =  DBManager.instance().getMatchLineup(aMatch.getMatchId()).getHeim();
        } else {
            tmpLineupTeam =  DBManager.instance().getMatchLineup(aMatch.getMatchId()).getGast();
        }

        double totStars = 0;

        for (int spot = ISpielerPosition.startLineup; spot < ISpielerPosition.startReserves; spot++) {
            MatchLineupPlayer mlp = tmpLineupTeam.getPlayerByPosition(spot);

            if (mlp != null && mlp.getSpielerId() > 0) {
                totStars += mlp.getRating();

                PlayerPerformance pp = new PlayerPerformance(mlp);

                pp.setStatus(PlayerDataManager.getLatestPlayerInfo(mlp.getSpielerId()).getStatus());

                matchDetail.addMatchLineupPlayer(pp);
            }
        }

        MatchRating rating = buildMatchRating(tmpMatch);

        // Match is a WO skip it
        if (rating.getHatStats() == 9) {
            return null;
        }

        matchDetail.setRating(rating);
        matchDetail.setStars(totStars);
        matchDetail.setTacticCode(getTacticType(tmpMatch));
        matchDetail.setTacticLevel(getTacticLevel(tmpMatch));

        matchDetail.setFormation(tmpMatch.getFormation(isHome(tmpMatch)));
        NameManager.addNames(tmpMatch.getLineup(isHome(tmpMatch)));

        return matchDetail;
    }
    
    private boolean isHome(Matchdetails match) {
        boolean isHome = false;

        if (match.getHeimId() == SystemManager.getActiveTeamId()) {
            isHome = true;
        }

        return isHome;
    }
}
