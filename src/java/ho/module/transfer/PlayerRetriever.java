// %1126721330885:hoplugins.transfers.utils%
package ho.module.transfer;


import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;

import java.util.Iterator;
import java.util.List;



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
    public static Spieler getPlayer(int id) {
        final Spieler player = HOVerwaltung.instance().getModel().getSpieler(id);

        if (player == null) {
            final List<Spieler> oldPlayers = HOVerwaltung.instance().getModel().getAllOldSpieler();

            for (final Iterator<Spieler> iter = oldPlayers.iterator(); iter.hasNext();) {
                final Spieler oldPlayer = iter.next();

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
