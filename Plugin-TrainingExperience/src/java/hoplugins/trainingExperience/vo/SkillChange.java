// %3284418725:hoplugins.trainingExperience.vo%
package hoplugins.trainingExperience.vo;

import plugins.ISkillup;
import plugins.ISpieler;


/**
 * This value object represent a change in skill for a player.
 *
 * @author NetHyperon
 */
public class SkillChange {
    //~ Instance fields ----------------------------------------------------------------------------

    private ISkillup skillup;
    private ISpieler player;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillChange object.
     *
     * @param player Player
     * @param skillup Skillup
     */
    public SkillChange(ISpieler player, ISkillup skillup) {
        this.player = player;
        this.skillup = skillup;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the player
     *
     * @return Player
     */
    public ISpieler getPlayer() {
        return player;
    }

    /**
     * Get the skillup
     *
     * @return Skillup
     */
    public ISkillup getSkillup() {
        return skillup;
    }
}
