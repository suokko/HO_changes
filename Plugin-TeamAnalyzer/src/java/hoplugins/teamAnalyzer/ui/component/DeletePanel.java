// %1176270089:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.vo.Team;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


/**
 * A panel that allows the user to remove a favourite team
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DeletePanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The Favourite Menu itself */
    FavouriteMenu menu;

    /** The add button */
    JButton addButton = new JButton(Commons.getModel().getResource().getProperty("Hinzufuegen"));

    /** The delete button */
    JButton deletebutton = new JButton(Commons.getModel().getResource().getProperty("loeschen"));

    /** ComboBox with the list of favourite teams */
    JComboBox teams = new JComboBox();

    /** A status label */
    JLabel status = new JLabel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     *
     * @param me the favourite menu for reference
     */
    public DeletePanel(FavouriteMenu me) {
        menu = me;
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Methods that fill the combo with  the favourite teams
     */
    private void fillCombo() {
        teams.removeAllItems();

        for (Iterator iter = menu.teams.iterator(); iter.hasNext();) {
            Team element = (Team) iter.next();

            teams.addItem(element);
        }

        teams.setEnabled(true);
        deletebutton.setEnabled(true);

        if (teams.getItemCount() == 0) {
            teams.setEnabled(false);
            deletebutton.setEnabled(false);
        }
    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        setLayout(new BorderLayout());
        add(deletebutton, BorderLayout.CENTER);
        add(teams, BorderLayout.WEST);
        add(status, BorderLayout.SOUTH);

        fillCombo();

        deletebutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Team team = (Team) teams.getSelectedItem();

                    if (team == null) {
                        status.setText(PluginProperty.getString("Favourite.SelectTeam"));

                        return;
                    }

                    int pos = menu.teams.indexOf(team);

                    menu.teams.remove(pos);

                    JMenuItem item = (JMenuItem) menu.items.get(pos);

                    menu.remove(item);
                    menu.items.remove(pos);
                    menu.dao.removeTeam(team.getTeamId());
                    fillCombo();

                    if (menu.teams.size() == 0) {
                        menu.itemDelete.setVisible(false);
                    }
                }
            });
    }
}
