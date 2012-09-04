// %3190202247:hoplugins.trainingExperience%
package ho.module.training;



import ho.core.constants.player.PlayerSkill;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;
import ho.core.util.HTCalendar;
import ho.core.util.HTCalendarFactory;
import ho.core.util.HelperWrapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Class that keeps track of the past skillup for the active user
 */
public class OldTrainingManager {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of all skill up */
    private List<ISkillup> allSkillups = new ArrayList<ISkillup>();

    /** List of trained skill up */
    private List<ISkillup> trainSkillups = new ArrayList<ISkillup>();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Calculates data for the player
     *
     * @param player
     */
    public OldTrainingManager(Spieler player) {
        if (player == null) {
            return;
        }

        //TreeMap mapTrained = new TreeMap();
        //TreeMap mapAll = new TreeMap();
        allSkillups = new ArrayList<ISkillup>();
        trainSkillups = new ArrayList<ISkillup>();

        for (int skill = 0; skill < 10; skill++) {
        	// Skip Form ups
        	if (skill == PlayerSkill.FORM)
        		continue;

            Vector<Object[]> v = player.getAllLevelUp(skill);
            int count = 0;

            for (Iterator<Object[]> iter = v.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                PastSkillup su = null;

                try {
                    Date htDate = HelperWrapper.instance().getHattrickDate("" //$NON-NLS-1$
                                                                                 + element[0]);
                    Date trainingDate = HelperWrapper.instance()
                                               .getLastTrainingDate(htDate,
                                                                    HOVerwaltung.instance().getModel().getXtraDaten()
                                                                           .getTrainingDate())
                                               .getTime();

                    su = getSkillup(trainingDate);
                    su.setValue(Skills.getSkillValue(player, skill) - count);
                    su.setType(skill);
                    su.setTrainType(ISkillup.SKILLUP_REAL);
                    allSkillups.add(su);

                    if (skill == PlayerSkill.KEEPER ||
                    		skill == PlayerSkill.DEFENDING ||
                    		skill == PlayerSkill.WINGER ||
                    		skill == PlayerSkill.PLAYMAKING ||
                    		skill == PlayerSkill.SCORING ||
                    		skill == PlayerSkill.PASSING ||
                    		skill == PlayerSkill.SET_PIECES) {
                        trainSkillups.add(su);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                count++;
            }
        }

        SkillupComperator comp = new SkillupComperator();

        Collections.sort(allSkillups, comp);
        Collections.sort(trainSkillups, comp);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the list of all calculated Skillups for the active player.
     *
     * @return list of all skillups
     */
    public List<ISkillup> getAllSkillups() {
        return allSkillups;
    }

    /**
     * Returns the list of calculated Skillups for the active player as a result of training.
     *
     * @return list of trained skillups
     */
    public List<ISkillup> getTrainedSkillups() {
        return trainSkillups;
    }

    /**
     * Calculates the HT Week and Season from the SkillupDate and initialize the Skillup Object
     *
     * @param skillupDate Skillup Date
     *
     * @return a skillup object with season and week value
     */
    private PastSkillup getSkillup(Date skillupDate) {

        PastSkillup skillup = new PastSkillup();

        HTCalendar cal = HTCalendarFactory.createEconomyCalendar(skillupDate);
        skillup.setHtSeason(cal.getHTSeason());
        skillup.setHtWeek(cal.getHTWeek());
        skillup.setDate(skillupDate);

        return skillup;
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private class SkillupComperator implements Comparator<ISkillup> {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(ISkillup o1, ISkillup o2) {
            ISkillup skillup1 = o1;
            ISkillup skillup2 = o2;

            if (skillup1.getDate().before(skillup2.getDate())) {
                return -1;
            } else if (skillup1.getDate().after(skillup2.getDate())) {
                return 1;
            } else {
                if (skillup1.getType() == skillup2.getType()) {
                    if (skillup1.getValue() > skillup2.getValue()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                return 0;
            }
        }
    }
}
