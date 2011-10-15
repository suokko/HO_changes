// %1283121044:hoplugins.trainingExperience.constants%
package hoplugins.trainingExperience.constants;

import hoplugins.Commons;

import plugins.ISpieler;

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

    //    /** Keeper Skill Constant */
    //    public static final int KEEPER = 0;
    //
    //    /** Playmaking Skill Constant */
    //    public static final int PLAYMAKING = 1;
    //
    //    /** Passing Skill Constant */
    //    public static final int PASSING = 2;
    //
    //    /** Winger Skill Constant */
    //    public static final int WING = 3;
    //
    //    /** Defense Skill Constant */
    //    public static final int DEFENSE = 4;
    //
    //    /** Scoring Skill Constant */
    //    public static final int ATTACK = 5;
    //
    //    /** SetPieces Skill Constant */
    //    public static final int SETPIECES = 6;
    //
    //    /** Stamina Skill Constant */
    //    public static final int STAMINA = 7;
    //
    //    /** Stamina Skill Constant */
    //    public static final int EXPERIENCE = 8;

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
     * Returns the String description of this skill
     *
     * @param skillIndex the skill code
     *
     * @return String description of the skill
     */
    public static String getSkillDescription(int skillIndex) {
		// Based on the code returns the proper ho property
		switch (skillIndex) {
			case ISpieler.SKILL_TORWART :
				return Commons.getModel().getLanguageString("skill.keeper"); //$NON-NLS-1$

			case ISpieler.SKILL_SPIELAUFBAU :
				return Commons.getModel().getLanguageString("skill.playmaking"); //$NON-NLS-1$

			case ISpieler.SKILL_PASSSPIEL :
				return Commons.getModel().getLanguageString("skill.passing"); //$NON-NLS-1$

			case ISpieler.SKILL_FLUEGEL :
				return Commons.getModel().getLanguageString("skill.winger"); //$NON-NLS-1$

			case ISpieler.SKILL_VERTEIDIGUNG :
				return Commons.getModel().getLanguageString("skill.defending"); //$NON-NLS-1$

			case ISpieler.SKILL_TORSCHUSS :
				return Commons.getModel().getLanguageString("skill.scoring"); //$NON-NLS-1$

			case ISpieler.SKILL_STANDARDS :
				return Commons.getModel().getLanguageString("skill.set_pieces"); //$NON-NLS-1$

			case ISpieler.SKILL_KONDITION :
				return Commons.getModel().getLanguageString("skill.stamina"); //$NON-NLS-1$

			case ISpieler.SKILL_EXPIERIENCE :
				return Commons.getModel().getLanguageString("skill.experience"); //$NON-NLS-1$
		}

		return ""; //$NON-NLS-1$
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
