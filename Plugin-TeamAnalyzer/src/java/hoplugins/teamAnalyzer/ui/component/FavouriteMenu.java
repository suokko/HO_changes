// %2153978627:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.dao.FavoritesDAO;
import hoplugins.teamAnalyzer.ui.controller.FavoriteItemListener;
import hoplugins.teamAnalyzer.vo.Team;

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

    /** The DB Access object */
    public FavoritesDAO dao = new FavoritesDAO();

    /** Add menu item */
    public JMenuItem itemAdd = new JMenuItem(Commons.getModel().getLanguageString("Hinzufuegen"));

    /** Delete menu item */
    public JMenuItem itemDelete = new JMenuItem(Commons.getModel().getLanguageString("loeschen"));

    /** List of favourite team menu items */
    public List items;

    /** List of favourite team objects */
    public List teams;

    /** Reference to itself */
    private FavouriteMenu me;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FavouriteMenu object.
     */
    public FavouriteMenu() {
        super(PluginProperty.getString("Favourite"));
        jbInit();
        me = this;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Initiates the gui
     */
    private void jbInit() {
        teams = dao.getTeams();
        items = new ArrayList();

        for (Iterator iter = teams.iterator(); iter.hasNext();) {
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
                    JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(),
                                                  new DeletePanel(me),
                                                  Commons.getModel().getLanguageString("loeschen")
                                                  + " "
                                                  + Commons.getModel().getLanguageString("Verein"),
                                                  JOptionPane.PLAIN_MESSAGE);
                    ;
                }
            });

        itemAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(),
                                                  new AddPanel(me),
                                                  Commons.getModel().getLanguageString("Hinzufuegen")
                                                  + " "
                                                  + Commons.getModel().getLanguageString("Verein"),
                                                  JOptionPane.PLAIN_MESSAGE);
                    ;
                }
            });
    }
}
