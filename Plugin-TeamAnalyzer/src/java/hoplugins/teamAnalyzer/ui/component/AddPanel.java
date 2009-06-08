// %3790006497:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.Commons;

import hoplugins.commons.ui.NumberTextField;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.ht.HattrickManager;
import hoplugins.teamAnalyzer.manager.TeamManager;
import hoplugins.teamAnalyzer.ui.controller.FavoriteItemListener;
import hoplugins.teamAnalyzer.vo.Team;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


/**
 * A panel that allows the user to add a new favourite team
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class AddPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7424042069787091891L;

	/** The Favourite Menu itself */
    FavouriteMenu menu;

    /** The add button */
    JButton addButton = new JButton(Commons.getModel().getLanguageString("Hinzufuegen"));

    /** A status label */
    JLabel status = new JLabel();

    /** The text field */
    NumberTextField teamId = new NumberTextField(8);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AddPanel object.
     *
     * @param me the favourite menu, for reference
     */
    public AddPanel(FavouriteMenu me) {
        jbInit();
        menu = me;
    }

    /**
     * Constructs a new instance.
     */
    public AddPanel() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        setLayout(new BorderLayout());

        //add(name, BorderLayout.CENTER);
        add(addButton, BorderLayout.CENTER);
        add(teamId, BorderLayout.WEST);
        add(status, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Team team = new Team();

                    team.setTeamId(teamId.getValue());

                    if (TeamManager.isTeamInList(teamId.getValue())) {
                        status.setText(PluginProperty.getString("Favourite.InList"));
                        teamId.setText("");

                        return;
                    }

                    if (menu.dao.isFavourite(teamId.getValue())) {
                        status.setText(PluginProperty.getString("Favourite.Already"));
                        teamId.setText("");

                        return;
                    }

                    try {
                        String teamName = HattrickManager.downloadTeam(teamId.getValue());

                        team.setName(teamName);
                    } catch (Exception e1) {
                        status.setText(PluginProperty.getString("Favourite.Error"));
                        teamId.setText("");

                        return;
                    }

                    status.setText(team.getName() + " "
                                   + Commons.getModel().getLanguageString("hinzugefuegt"));
                    menu.teams.add(team);

                    JMenuItem item = new JMenuItem(team.getName());

                    item.addActionListener(new FavoriteItemListener(team));
                    menu.items.add(item);
                    menu.add(item, 0);
                    menu.dao.addTeam(team);
                    menu.itemDelete.setVisible(true);
                    teamId.setText("");
                }
            });
    }
}
