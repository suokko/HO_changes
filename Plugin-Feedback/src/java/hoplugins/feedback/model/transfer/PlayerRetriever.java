package hoplugins.feedback.model.transfer;

import hoplugins.Commons;

import java.util.Iterator;
import java.util.List;

import plugins.ISpieler;


/**
 * Utility to retrieve a player by an id, even if it is an old-player.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public final class PlayerRetriever {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation. 
     */
    private PlayerRetriever() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Retrieve a player by his ID.
     *
     * @param id Player ID
     *
     * @return ISpieler interface representing the foun player or <code>null</code> if no player
     *         could be found.
     */
    public static ISpieler getPlayer(int id) {
        final ISpieler player = Commons.getModel().getSpieler(id);

        if (player == null) {
            final List<ISpieler> oldPlayers = Commons.getModel().getAllOldSpieler();

            for (final Iterator<ISpieler> iter = oldPlayers.iterator(); iter.hasNext();) {
                final ISpieler oldPlayer = iter.next();

                if (oldPlayer.getSpielerID() == id) {
                    return oldPlayer;
                }
            }

            return null;
        } else {
            return player;
        }
    }
}
