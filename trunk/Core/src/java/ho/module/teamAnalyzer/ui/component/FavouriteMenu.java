// %2153978627:hoplugins.teamAnalyzer.ui.component%
package ho.module.teamAnalyzer.ui.component;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.ui.controller.FavoriteItemListener;
import ho.module.teamAnalyzer.vo.Team;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;



/**
 * A Favourite Menu
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class FavouriteMenu extends JMenu {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3404254214435543226L;

    /** Add menu item */
    public JMenuItem itemAdd = new JMenuItem(HOVerwaltung.instance().getLanguageString("Hinzufuegen"));

    /** Delete menu item */
    public JMenuItem itemDelete = new JMenuItem(HOVerwaltung.instance().getLanguageString("loeschen"));

    /** List of favourite team menu items */
    public List<JMenuItem> items;

    /** List of favourite team objects */
    public List<Team> teams;

    /** Reference to itself */
    private FavouriteMenu me;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FavouriteMenu object.
     */
    public FavouriteMenu() {
        super(HOVerwaltung.instance().getLanguageString("Favourite"));
        jbInit();
        me = this;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Initiates the gui
     */
    private void jbInit() {
        teams = DBManager.instance().getTAFavoriteTeams();
        items = new ArrayList<JMenuItem>();

        for (Iterator<?> iter = teams.iterator(); iter.hasNext();) {
            Team element = (Team) iter.next();
            JMenuItem item = new JMenuItem(element.getName());

            item.addActionListener(new FavoriteItemListener(element));
            add(item);
            items.add(item);
        }

        add(new JSeparator());
        add(itemAdd);
        add(itemDelete);

        if (teams.size() == 0) {
            itemDelete.setVisible(false);
        }

        itemDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JOptionPane.showMessageDialog(SystemManager.getPlugin(),
                                                  new DeletePanel(me),
                                                  HOVerwaltung.instance().getLanguageString("loeschen")
                                                  + " "
                                                  + HOVerwaltung.instance().getLanguageString("Verein"),
                                                  JOptionPane.PLAIN_MESSAGE);
                    ;
                }
            });

        itemAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JOptionPane.showMessageDialog(SystemManager.getPlugin(),
                                                  new AddPanel(me),
                                                  HOVerwaltung.instance().getLanguageString("Hinzufuegen")
                                                  + " "
                                                  + HOVerwaltung.instance().getLanguageString("Verein"),
                                                  JOptionPane.PLAIN_MESSAGE);
                    ;
                }
            });
    }
}
