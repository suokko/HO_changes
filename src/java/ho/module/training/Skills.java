// %1283121044:hoplugins.trainingExperience.constants%
package ho.module.training;

import plugins.ISpieler;

import ho.core.model.HOVerwaltung;

import java.awt.Color;



/**
 * Class that manages all the relation of Skills
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Skills {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param position TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getSkillAtPosition(int position) {
        switch (position) {
            case 0:
                return ISpieler.SKILL_TORWART;

            case 1:
                return ISpieler.SKILL_SPIELAUFBAU;

            case 2:
                return ISpieler.SKILL_PASSSPIEL;

            case 3:
                return ISpieler.SKILL_FLUEGEL;

            case 4:
                return ISpieler.SKILL_VERTEIDIGUNG;

            case 5:
                return ISpieler.SKILL_TORSCHUSS;

            case 6:
                return ISpieler.SKILL_STANDARDS;

            case 7:
                return ISpieler.SKILL_KONDITION;

            case 8:
                return ISpieler.SKILL_EXPIERIENCE;
        }

        return 0;
    }

    /**
     * Returns the base training type for that skill
     *
     * @param skillIndex skill to train
     *
     * @return base training type for that skill
     */
    public static Color getSkillColor(int skillIndex) {
        switch (skillIndex) {
            case ISpieler.SKILL_TORWART:
                return Color.BLACK;

            case ISpieler.SKILL_SPIELAUFBAU:
                return Color.ORANGE.darker();

            case ISpieler.SKILL_PASSSPIEL:
                return Color.GREEN.darker();

            case ISpieler.SKILL_FLUEGEL:
                return Color.MAGENTA;

            case ISpieler.SKILL_VERTEIDIGUNG:
                return Color.BLUE;

            case ISpieler.SKILL_TORSCHUSS:
                return Color.RED;

            case ISpieler.SKILL_STANDARDS:
                return Color.CYAN.darker();

            case ISpieler.SKILL_KONDITION:
                return new Color(85, 26, 139);
        }

        return Color.BLACK;
    }

     /**
     * Returns the Skill value for the player
     *
     * @param spieler
     * @param skillIndex constant index value of the skill we want to see
     *
     * @return The Skill value or 0 if the index is incorrect
     */
    public static int getSkillValue(ISpieler spieler, int skillIndex) {
        switch (skillIndex) {
            case ISpieler.SKILL_TORWART:
                return spieler.getTorwart();

            case ISpieler.SKILL_SPIELAUFBAU:
                return spieler.getSpielaufbau();

            case ISpieler.SKILL_PASSSPIEL:
                return spieler.getPasspiel();

            case ISpieler.SKILL_FLUEGEL:
                return spieler.getFluegelspiel();

            case ISpieler.SKILL_VERTEIDIGUNG:
                return spieler.getVerteidigung();

            case ISpieler.SKILL_TORSCHUSS:
                return spieler.getTorschuss();

            case ISpieler.SKILL_STANDARDS:
                return spieler.getStandards();

            case ISpieler.SKILL_KONDITION:
                return spieler.getKondition();

            case ISpieler.SKILL_EXPIERIENCE:
                return spieler.getErfahrung();
        }

        return 0;
    }

    /**
     * Returns the base training type for that skill
     *
     * @param skillIndex skill to train
     *
     * @return base training type for that skill
     */
    public static int getTrainedSkillCode(int skillIndex) {
        switch (skillIndex) {
            case ISpieler.SKILL_TORWART:
                return ISpieler.TORWART;

            case ISpieler.SKILL_SPIELAUFBAU:
                return ISpieler.SPIELAUFBAU;

            case ISpieler.SKILL_PASSSPIEL:
                return ISpieler.PASSPIEL;

            case ISpieler.SKILL_FLUEGEL:
                return ISpieler.FLUEGELSPIEL;

            case ISpieler.SKILL_VERTEIDIGUNG:
                return ISpieler.VERTEIDIGUNG;

            case ISpieler.SKILL_TORSCHUSS:
                return ISpieler.CHANCENVERWERTUNG;

            case ISpieler.SKILL_STANDARDS:
                return ISpieler.STANDARDS;

          }

        return 0;
    }
}
