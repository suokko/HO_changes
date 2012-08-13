// %3284418725:hoplugins.trainingExperience.vo%
package ho.module.training;

import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;


/**
 * This value object represent a change in skill for a player.
 *
 * @author NetHyperon
 */
public class SkillChange {
    //~ Instance fields ----------------------------------------------------------------------------

    private ISkillup skillup;
    private Spieler player;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillChange object.
     *
     * @param player Player
     * @param skillup Skillup
     */
    public SkillChange(Spieler player, ISkillup skillup) {
        this.player = player;
        this.skillup = skillup;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the player
     *
     * @return Player
     */
    public Spieler getPlayer() {
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
