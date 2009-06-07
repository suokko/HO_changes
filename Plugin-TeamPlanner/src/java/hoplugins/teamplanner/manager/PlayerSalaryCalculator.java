// %3282954443:hoplugins.teamplanner.manager%
package hoplugins.teamplanner.manager;

import plugins.ISpieler;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class PlayerSalaryCalculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param player Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static int getEstimatedSalary(ISpieler player) {
        double salary = 500;
        salary += getSalaryForSkill(player.getPasspiel(),
                                    player.getSubskill4SkillWithOffset(ISpieler.SKILL_PASSSPIEL));
        salary += getSalaryForSkill(player.getFluegelspiel(),
                                    player.getSubskill4SkillWithOffset(ISpieler.SKILL_FLUEGEL));
        salary += getSalaryForSkill(player.getVerteidigung(),
                                    player.getSubskill4SkillWithOffset(ISpieler.SKILL_VERTEIDIGUNG));

        // TODO PM depends on stamina
        salary += getSalaryForSkill(player.getSpielaufbau(),
                                    player.getSubskill4SkillWithOffset(ISpieler.SKILL_SPIELAUFBAU));

        // TODO Redo keeper salary
        salary += (getSalaryForSkill(player.getTorwart(),
                                     player.getSubskill4SkillWithOffset(ISpieler.SKILL_TORWART)) * 4);
        salary += getSalaryForSkill(player.getTorschuss(),
                                    player.getSubskill4SkillWithOffset(ISpieler.SKILL_TORSCHUSS));
        return (int) salary;
    }

    /**
     * Missing Method Documentation
     *
     * @param skill Missing Method Parameter Documentation
     * @param subskill Missing Constructuor Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private static int getSalaryForSkill(int skill, float subskill) {
        double l1 = getSalaryForSkill(skill);
        double l2 = getSalaryForSkill(skill + 1);
        double sal = l1 + ((l2 - l1) * subskill);
        return (int) sal;
    }

    /**
     * Missing Method Documentation
     *
     * @param skill Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private static int getSalaryForSkill(int skill) {
        switch (skill) {
            case 5:
                return 20;

            case 6:
                return 100;

            case 7:
                return 250;

            case 8:
                return 500;
        }

        if (skill < 5) {
            return 0;
        }

        return (int) Math.pow(2, skill - 8) * 500;
    }
}
