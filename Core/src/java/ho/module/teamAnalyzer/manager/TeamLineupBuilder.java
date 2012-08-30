// %3549957018:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.util.HOLogger;
import ho.module.teamAnalyzer.report.PositionReport;
import ho.module.teamAnalyzer.report.SpotReport;
import ho.module.teamAnalyzer.report.TacticReport;
import ho.module.teamAnalyzer.report.TeamReport;
import ho.module.teamAnalyzer.vo.PlayerAppearance;
import ho.module.teamAnalyzer.vo.SpotLineup;
import ho.module.teamAnalyzer.vo.TeamLineup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;




/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamLineupBuilder {
    //~ Instance fields ----------------------------------------------------------------------------

    private TeamLineup teamLineup;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamLineupBuilder object.
     *
     * @param teamReport TODO Missing Constructuor Parameter Documentation
     */
    public TeamLineupBuilder(TeamReport teamReport) {
        teamLineup = new TeamLineup();
        teamLineup.setRating(teamReport.getRating());
        teamLineup.setStars(teamReport.getStars());

        for (int spot = ISpielerPosition.startLineup; spot < ISpielerPosition.startReserves; spot++) {
        	
            SpotReport spotReport = teamReport.getSpotReport(spot);

            if (spotReport != null) {
                SpotLineup spotLineup = buildSpotLineup(spotReport);

                HOLogger.instance().log(getClass(), spotReport.toString() + " lineup: " + (spotLineup == null ? "null" : spotLineup.toString()) + " spot: " + spot);
                teamLineup.setSpotLineup(spotLineup, spot);
            }
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TeamLineup getLineup() {
        return teamLineup;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param positions TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Collection<TacticReport> getAllTactics(Collection<PositionReport> positions) {
        Collection<TacticReport> tactics = new ArrayList<TacticReport>();

        for (Iterator<PositionReport> iter = positions.iterator(); iter.hasNext();) {
            PositionReport positionReport = iter.next();

            tactics.addAll(positionReport.getTacticReports());
        }

        return tactics;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param collection TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private PlayerAppearance getPlayer(Collection<PlayerAppearance> collection) {
        PlayerAppearance[] appearances = getSortedAppearance(collection);
        
        for (int i = 0; i < appearances.length; i++) {
        	HOLogger.instance().log(getClass(), appearances[i].getName() + " " + appearances[i].getAppearance());
        }

        if (appearances.length == 1) {
            return appearances[0];
        }

        if (appearances[0].getAppearance() > appearances[1].getAppearance()) {
            return appearances[0];
        }

        PlayerAppearance app = new PlayerAppearance();

        if ((appearances.length > 2)
            && (appearances[2].getAppearance() == appearances[0].getAppearance())) {
            app.setName(HOVerwaltung.instance().getLanguageString("TeamLineupBuilder.Unknown")); //$NON-NLS-1$
        } else {
            //			String status1 = (appearances[0].getStatus()!=PlayerManager.AVAILABLE)? "*":"";			
            //			String status2 = (appearances[1].getStatus()!=PlayerManager.AVAILABLE)? "*":"";
            app.setName(appearances[0].getName() + "/" + appearances[1].getName());
        }

        app.setApperarence(appearances[0].getAppearance());

        return app;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tacticsReport TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int getPosition(TacticReport[] tacticsReport) {
        if (isSingle(tacticsReport)) {
            return tacticsReport[0].getTacticCode();
        }

        if (tacticsReport[0].getPosition() == tacticsReport[1].getPosition()) {
            return tacticsReport[0].getTacticCode();
        }

        return -1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tacticsReport TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private boolean isSingle(TacticReport[] tacticsReport) {
        if (tacticsReport.length == 1) {
            return true;
        }

        if (tacticsReport[0].getAppearance() > tacticsReport[1].getAppearance()) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param appearance TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private PlayerAppearance[] getSortedAppearance(Collection<PlayerAppearance> appearance) {
        Vector<PlayerAppearance> sorted = getSortedSet(appearance, new AppearanceComparator());
        int size = sorted.size();
        PlayerAppearance[] array = new PlayerAppearance[size];
        int i = 0;

        for (Iterator<PlayerAppearance> iter = sorted.iterator(); iter.hasNext();) {
            PlayerAppearance element = iter.next();

            array[i] = element;
            i++;
        }

        return array;
    }

    private static<T> Vector<T> getSortedSet(Collection<T> beans, Comparator<T> comparator) {
        final Vector<T> set = new Vector<T>();

        if ((beans != null) && (beans.size() > 0)) {
            set.addAll(beans);
            Collections.sort(set, comparator);
        }

        return set;
    }
    /**
     * TODO Missing Method Documentation
     *
     * @param tactics TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private TacticReport[] getSortedTactics(Collection<TacticReport> tactics) {
        Vector<TacticReport> sorted = getSortedSet(tactics, new PerformanceComparator());
        int size = sorted.size();
        TacticReport[] tacticsReport = new TacticReport[size];
        int i = 0;

        for (Iterator<TacticReport> iter = sorted.iterator(); iter.hasNext();) {
            TacticReport element = iter.next();

            tacticsReport[i] = element;
            i++;
        }

        return tacticsReport;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spotReport TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private SpotLineup buildSpotLineup(SpotReport spotReport) {
        SpotLineup spotLineup = new SpotLineup(spotReport);

        spotLineup.setSpot(spotReport.getSpot());

        PlayerAppearance appearance = getPlayer(spotReport.getPlayerAppearance());

        spotLineup.setName(appearance.getName());
        spotLineup.setPlayerId(appearance.getPlayerId());
        spotLineup.setAppearance(appearance.getAppearance());
        spotLineup.setStatus(appearance.getStatus());

        Collection<TacticReport> tacticsReports = getAllTactics(spotReport.getPositionReports());
        TacticReport[] tacticsReport = getSortedTactics(tacticsReports);

        spotLineup.setTactics(Arrays.asList(tacticsReport));
        spotLineup.setPosition(getPosition(tacticsReport));

        return spotLineup;
    }
}
