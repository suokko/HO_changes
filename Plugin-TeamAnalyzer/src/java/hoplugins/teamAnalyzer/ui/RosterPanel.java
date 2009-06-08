// %988591988:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.comparator.RosterPlayerComparator;
import hoplugins.teamAnalyzer.manager.PlayerDataManager;
import hoplugins.teamAnalyzer.manager.ReportManager;
import hoplugins.teamAnalyzer.ui.model.UiRosterTableModel;
import hoplugins.teamAnalyzer.ui.renderer.PlayerPositionTableCellRenderer;
import hoplugins.teamAnalyzer.vo.PlayerInfo;
import hoplugins.teamAnalyzer.vo.RosterPlayerData;
import hoplugins.teamAnalyzer.vo.RosterRoleData;
import hoplugins.teamAnalyzer.vo.SpotLineup;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.awt.BorderLayout;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RosterPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3867854224503291836L;
	private JTable table;
    private List oldPlayersInfo = new ArrayList();
    private Map players = new HashMap();
    private UiRosterTableModel tableModel;
    private String[] columns = {
                                   Commons.getModel().getLanguageString("Name"),
                                   PluginProperty.getString("Role"),
                                   PluginProperty.getString("Position"),
                                   PluginProperty.getString("Secondary"),
                                   PluginProperty.getString("GamesCode"),
                                   Commons.getModel().getLanguageString("Alter"),
                                   Commons.getModel().getLanguageString("Form"),
                                   PluginProperty.getString("EXPCode"),
                                   PluginProperty.getString("TSI"),
                                   PluginProperty.getString("SpecialEvent"),
                                   PluginProperty.getString("Max"), PluginProperty.getString("Avg"),
                                   PluginProperty.getString("Min"), "Status", "PlayerId"
                               };
    private boolean reloading = false;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RosterPanel object.
     */
    public RosterPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public PlayerInfo getPrevious(int playerId) {
        for (Iterator iter = oldPlayersInfo.iterator(); iter.hasNext();) {
            PlayerInfo element = (PlayerInfo) iter.next();

            if (element.getPlayerId() == playerId) {
                return element;
            }
        }

        return new PlayerInfo();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void reload() {

    	//### roster disabled, aik, 05.03.2008 ###
    	if (true) return;


        reloading = true;
        players = new HashMap();
        oldPlayersInfo = new ArrayList();

        DecimalFormat df = new DecimalFormat("#0.0");

        for (int i = 1; i <= SystemManager.getFilter().getNumber(); i++) {
            TeamLineup lineup = null;

            try {
                lineup = ReportManager.getLineup(i);
            } catch (RuntimeException e) {
            }

            if (lineup != null) {
                for (int spot = 1; spot < 12; spot++) {
                    SpotLineup spotLineup = lineup.getSpotLineup(spot);

                    if ((spotLineup != null) && (spotLineup.getPlayerId() > 0)) {
                        RosterPlayerData data = getPlayerData(spotLineup);

                        data.addMatch(spotLineup);
                    }
                }
            }
        }

        // Empty model
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        List l = new ArrayList(players.values());

        Collections.sort(l, new RosterPlayerComparator());

        for (Iterator iter = l.iterator(); iter.hasNext();) {
            RosterPlayerData element = (RosterPlayerData) iter.next();

            if (element.getId() > 0) {
                PlayerInfo info = PlayerDataManager.getPlayerInfo(element.getId());

                if (info.getPlayerId() == 0) {
                    info.setPlayerId(element.getId());
                    info.setTeamId(SystemManager.getActiveTeamId());
                }

                info.setName(element.getName());

                Vector rowData = new Vector();
                RosterRoleData mainRole = element.getMainRole();

                rowData.add(element.getName());

                rowData.add(new Integer(mainRole.getPos()));
                rowData.add(new Integer(mainRole.getPos()));

                StringBuffer sec = new StringBuffer();

                for (Iterator iterator = element.getSecondaryRoles().iterator();
                     iterator.hasNext();) {
                    if (sec.toString().length() > 0) {
                        sec.append(" - ");
                    }

                    RosterRoleData role = (RosterRoleData) iterator.next();

                    sec.append(Commons.getModel().getHelper().getNameForPosition((byte) role.getPos()));
                    sec.append("(" + role.getApp() + ") ");
                    sec.append(df.format(role.getMax()) + "/" + df.format(role.getAvg()) + "/"
                               + df.format(role.getMin()));
                }

                rowData.add(sec.toString());
                rowData.add("" + mainRole.getApp());
                rowData.add("" + info.getAge());
                rowData.add("" + info.getForm());
                rowData.add("" + info.getExperience());
                rowData.add("" + info.getTSI());
                rowData.add("" + info.getSpecialEvent());

                rowData.add(df.format(mainRole.getMax()));
                rowData.add(df.format(mainRole.getAvg()));
                rowData.add(df.format(mainRole.getMin()));
                rowData.add("" + info.getStatus());
                rowData.add("" + element.getId());

                tableModel.addRow(rowData);
                oldPlayersInfo.add(PlayerDataManager.getPreviousPlayerInfo(element.getId()));
            }
        }

        setColumnWidth(0, 130);
        setColumnWidth(1, 130);
        setColumnInvisible(2);
        setColumnWidth(3, 130);
        setColumnWidth(4, 25);
        setColumnWidth(5, 25);
        setColumnWidth(6, 30);
        setColumnWidth(7, 25);
        setColumnWidth(8, 40);
        setColumnWidth(9, 25);
        setColumnWidth(10, 25);
        setColumnWidth(11, 25);
        setColumnWidth(12, 25);
        setColumnInvisible(13);
        setColumnInvisible(14);

        table.getColumnModel().getColumn(1).setCellRenderer(new PlayerPositionTableCellRenderer());

        reloading = false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     */
    private void setColumnInvisible(int col) {
        table.getTableHeader().getColumnModel().getColumn(col).setWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     * @param width TODO Missing Method Parameter Documentation
     */
    private void setColumnWidth(int col, int width) {
        table.getTableHeader().getColumnModel().getColumn(col).setWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spot TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private RosterPlayerData getPlayerData(SpotLineup spot) {
        RosterPlayerData data = (RosterPlayerData) players.get("" + spot.getPlayerId());

        if (data == null) {
            data = new RosterPlayerData();
            data.setId(spot.getPlayerId());
            data.setName(spot.getName());
            players.put("" + spot.getPlayerId(), data);
        }

        return data;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        Vector data = new Vector();

        tableModel = new UiRosterTableModel(data, new Vector(Arrays.asList(columns)));
        table = new JTable(tableModel);

        // Set up tool tips for column headers.
        table.getTableHeader().setToolTipText(PluginProperty.getString("RecapPanel.Tooltip")); //$NON-NLS-1$
        table.getTableHeader().setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new RosterTableRenderer());

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }
}
