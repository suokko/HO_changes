// %1083068137:hoplugins.evilCard.ui%
/*
 * Created on 6.11.2005
 */
package hoplugins.evilCard.ui;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.evilCard.ui.model.PlayersTableModel;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Filter panel.
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class FilterPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5993279445476499431L;
	private JComboBox chooseMatchTypeComboBox = null;
    private JComboBox choosePlayersComboBox = null;
    private JComboBox fromSeasonComboBox = null;
    private JComboBox toSeasonComboBox = null;
    private MainPanel mainPanel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param mainPanel
     */
    public FilterPanel(MainPanel mainPanel) {
        super();
        this.setOpaque(false);
        this.setLayout(null);
        this.mainPanel = mainPanel;

        JLabel chooseMatchesLabel = new JLabel(PluginProperty.getString("label.ChooseMatches"));
        chooseMatchesLabel.setForeground(java.awt.Color.WHITE);
        chooseMatchesLabel.setFont(new Font(chooseMatchesLabel.getFont().getName(), Font.BOLD, 14));
        chooseMatchesLabel.setSize(200, 25);
        chooseMatchesLabel.setLocation(25, 100);
        this.add(chooseMatchesLabel);

        JLabel fromSeasonLabel = new JLabel(PluginProperty.getString("label.FromSeason"));
        fromSeasonLabel.setForeground(java.awt.Color.WHITE);
        fromSeasonLabel.setFont(new Font(fromSeasonLabel.getFont().getName(), Font.BOLD, 10));
        fromSeasonLabel.setSize(120, 25);
        fromSeasonLabel.setLocation(25, 140);
        this.add(fromSeasonLabel);

        fromSeasonComboBox = new JComboBox();
        fromSeasonComboBox.setSize(120, 25);
        fromSeasonComboBox.setLocation(25, 170);
        fromSeasonComboBox.setEnabled(false);
        this.add(fromSeasonComboBox);

        JLabel toSeasonLabel = new JLabel(PluginProperty.getString("label.ToSeason"));
        toSeasonLabel.setForeground(java.awt.Color.WHITE);
        toSeasonLabel.setFont(new Font(toSeasonLabel.getFont().getName(), Font.BOLD, 10));
        toSeasonLabel.setSize(120, 25);
        toSeasonLabel.setLocation(25, 200);
        this.add(toSeasonLabel); // combobox

        toSeasonComboBox = new JComboBox();
        toSeasonComboBox.setSize(120, 25);
        toSeasonComboBox.setLocation(25, 230);
        toSeasonComboBox.setEnabled(false);
        this.add(toSeasonComboBox);

        // Choose match type label
        JLabel chooseMatchTypeLabel = new JLabel(PluginProperty.getString("label.ChooseMatchType"));
        chooseMatchTypeLabel.setForeground(java.awt.Color.WHITE);
        chooseMatchTypeLabel.setFont(new Font(chooseMatchTypeLabel.getFont().getName(), Font.BOLD,
                                              10));
        chooseMatchTypeLabel.setSize(120, 25);
        chooseMatchTypeLabel.setLocation(25, 270);
        this.add(chooseMatchTypeLabel);

        // Choose match type combo box
        chooseMatchTypeComboBox = new JComboBox();
        chooseMatchTypeComboBox.addItem(PluginProperty.getString("label.AllMatches"));
        chooseMatchTypeComboBox.addItem(PluginProperty.getString("label.OfficialMatchesOnly"));
        chooseMatchTypeComboBox.addItem(PluginProperty.getString("label.FriendlyMatchesOnly"));
        chooseMatchTypeComboBox.setSize(120, 25);
        chooseMatchTypeComboBox.setLocation(25, 300);
        chooseMatchTypeComboBox.setEnabled(false);
        this.add(chooseMatchTypeComboBox);

        JLabel choosePlayersLabel = new javax.swing.JLabel(PluginProperty.getString("label.ChoosePlayers"));
        choosePlayersLabel.setForeground(java.awt.Color.WHITE);
        choosePlayersLabel.setFont(new Font(choosePlayersLabel.getFont().getName(), Font.BOLD, 10));
        choosePlayersLabel.setSize(120, 25);
        choosePlayersLabel.setLocation(25, 340);
        this.add(choosePlayersLabel);

        choosePlayersComboBox = new JComboBox();
        choosePlayersComboBox.addItem(PluginProperty.getString("label.CurrentPlayersOnly"));
        choosePlayersComboBox.addItem(PluginProperty.getString("label.AllPlayers"));
        choosePlayersComboBox.setSize(120, 25);
        choosePlayersComboBox.setLocation(25, 370);
        choosePlayersComboBox.addActionListener(new FilterBoxListener());
        choosePlayersComboBox.setSelectedIndex(0);
        this.add(choosePlayersComboBox);
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * Listener for the ComboBoxes of the panel.
     *
     * @author TODO Author Name
     */
    class FilterBoxListener implements ActionListener {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * handle events
         *
         * @param e
         */
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // combobox for players
            if (e.getSource().equals(choosePlayersComboBox)) {
                switch (choosePlayersComboBox.getSelectedIndex()) {
                    case 0:
                        mainPanel.setFilter(PlayersTableModel.TYPE_CURRENT_PLAYERS);
                        break;

                    case 1:
                        mainPanel.setFilter(PlayersTableModel.TYPE_ALL_PLAYERS);
                        break;

                    default:
                    // no actions.
                }
            }
        }
    }
}
