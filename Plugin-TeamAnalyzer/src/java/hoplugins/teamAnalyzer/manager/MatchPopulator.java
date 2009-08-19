// %3018375595:hoplugins.teamAnalyzer.manager%
package hoplugins.teamAnalyzer.manager;

import hoplugins.Commons;

import hoplugins.commons.vo.MatchRating;

import hoplugins.teamAnalyzer.ht.CHPPManager;
import hoplugins.teamAnalyzer.util.MatchUtil;
import hoplugins.teamAnalyzer.vo.Match;
import hoplugins.teamAnalyzer.vo.MatchDetail;
import hoplugins.teamAnalyzer.vo.PlayerPerformance;

import plugins.IMatchDetails;
import plugins.IMatchLineupPlayer;
import plugins.IMatchLineupTeam;

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

        for (Iterator<Match> iter = matches.iterator(); iter.hasNext();) {
            Match element = iter.next();

            if (!isMatchAvailable(element.getMatchId()) && CHPPManager.isDownloadAllowed(element)) {
                downloadMatch(element.getMatchId());
            }

            try {
                MatchDetail md = populateMatch(element);

                if (md != null) {
                    list.add(md);
                    analyzedMatch.add(md);
                }
            } catch (RuntimeException e) {
                // DO NOTHING
            }
        }

        return list;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private boolean isMatchAvailable(int matchId) {
        return Commons.getModel().getHelper().existsMatchInDB(matchId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aMatchDetail TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static int getTacticLevel(IMatchDetails aMatchDetail) {
        if (MatchUtil.isHome(aMatchDetail)) {
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
    private static int getTacticType(IMatchDetails aMatchDetail) {
        if (MatchUtil.isHome(aMatchDetail)) {
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
    private MatchRating buildMatchRating(IMatchDetails aMatchDetail) {
        MatchRating mr = new MatchRating();

        if (MatchUtil.isHome(aMatchDetail)) {
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
        return Commons.getModel().getHelper().downloadMatchData(matchId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aMatch TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private MatchDetail populateMatch(Match aMatch) {
        IMatchDetails tmpMatch = Commons.getModel().getMatchDetails(aMatch.getMatchId());

        MatchDetail matchDetail = new MatchDetail(aMatch);

        IMatchLineupTeam tmpLineupTeam = null;

        if (MatchUtil.isHome(tmpMatch)) {
            tmpLineupTeam = Commons.getModel().getMatchLineup(aMatch.getMatchId()).getHeim();
        } else {
            tmpLineupTeam = Commons.getModel().getMatchLineup(aMatch.getMatchId()).getGast();
        }

        double totStars = 0;

        for (int spot = 1; spot < 12; spot++) {
            IMatchLineupPlayer mlp = tmpLineupTeam.getPlayerByPosition(spot);

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

        matchDetail.setFormation(tmpMatch.getFormation(MatchUtil.isHome(tmpMatch)));
        NameManager.addNames(tmpMatch.getLineup(MatchUtil.isHome(tmpMatch)));

        return matchDetail;
    }
}
