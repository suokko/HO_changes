// %1955470492:hoplugins.teamAnalyzer.ui%
/*
 * AutoFilterPanel.java
 *
 * Created on 20 settembre 2004, 16.12
 */
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.ui.NumberTextField;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


/**
 * DOCUMENT ME!
 *
 * @author samuele.peli
 */
public class AutoFilterPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 980373632967180587L;
	private JCheckBox awayGames = new JCheckBox();
    private JCheckBox cup = new JCheckBox();
    private JCheckBox defeat = new JCheckBox();
    private JCheckBox draw = new JCheckBox();
    private JCheckBox friendly = new JCheckBox();

    //  Filter filter = SystemManager.getFilter();
    private JCheckBox homeGames = new JCheckBox();
    private JCheckBox league = new JCheckBox();
    private JCheckBox qualifier = new JCheckBox();
    private JCheckBox win = new JCheckBox();
    private NumberTextField number = new NumberTextField(2);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of AutoFilterPanel
     */
    public AutoFilterPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public void reload() {
        homeGames.setSelected(SystemManager.getFilter().isHomeGames());
        awayGames.setSelected(SystemManager.getFilter().isAwayGames());
        win.setSelected(SystemManager.getFilter().isWin());
        draw.setSelected(SystemManager.getFilter().isDraw());
        defeat.setSelected(SystemManager.getFilter().isDefeat());
        number.setText(SystemManager.getFilter().getNumber() + "");
        league.setSelected(SystemManager.getFilter().isLeague());
        cup.setSelected(SystemManager.getFilter().isCup());
        qualifier.setSelected(SystemManager.getFilter().isQualifier());
        friendly.setSelected(SystemManager.getFilter().isFriendly());
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void setFilter() {
        SystemManager.getFilter().setAwayGames(awayGames.isSelected());
        SystemManager.getFilter().setHomeGames(homeGames.isSelected());
        SystemManager.getFilter().setWin(win.isSelected());
        SystemManager.getFilter().setDefeat(defeat.isSelected());
        SystemManager.getFilter().setDraw(draw.isSelected());
        SystemManager.getFilter().setLeague(league.isSelected());
        SystemManager.getFilter().setCup(cup.isSelected());
        SystemManager.getFilter().setFriendly(friendly.isSelected());
        SystemManager.getFilter().setQualifier(qualifier.isSelected());
        SystemManager.getFilter().setNumber(number.getValue());
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        JPanel main = Commons.getModel().getGUI().createImagePanel();

        main.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel filters = Commons.getModel().getGUI().createImagePanel();

        filters.setLayout(new GridLayout(11, 2));
        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Home_Games"))); //$NON-NLS-1$
        homeGames.setSelected(SystemManager.getFilter().isHomeGames());
        homeGames.setOpaque(false);
        filters.add(homeGames);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Away_Games"))); //$NON-NLS-1$
        awayGames.setSelected(SystemManager.getFilter().isAwayGames());
        awayGames.setOpaque(false);
        filters.add(awayGames);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Win_Games"))); //$NON-NLS-1$
        win.setSelected(SystemManager.getFilter().isWin());
        win.setOpaque(false);
        filters.add(win);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Draw_Games"))); //$NON-NLS-1$
        draw.setSelected(SystemManager.getFilter().isDraw());
        draw.setOpaque(false);
        filters.add(draw);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Defeat_Games"))); //$NON-NLS-1$
        defeat.setSelected(SystemManager.getFilter().isDefeat());
        defeat.setOpaque(false);
        filters.add(defeat);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.LeagueGame"))); //$NON-NLS-1$
        league.setSelected(SystemManager.getFilter().isLeague());
        league.setOpaque(false);
        filters.add(league);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.CupGame"))); //$NON-NLS-1$
        cup.setSelected(SystemManager.getFilter().isCup());
        cup.setOpaque(false);
        filters.add(cup);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.FriendlyGame"))); //$NON-NLS-1$
        friendly.setSelected(SystemManager.getFilter().isFriendly());
        friendly.setOpaque(false);
        filters.add(friendly);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.QualifierGame"))); //$NON-NLS-1$
        qualifier.setSelected(SystemManager.getFilter().isQualifier());
        qualifier.setOpaque(false);
        filters.add(qualifier);

        filters.add(new JLabel(PluginProperty.getString("AutoFilterPanel.Max_Number"))); //$NON-NLS-1$
        number.setText(SystemManager.getFilter().getNumber() + "");
        filters.add(number);

        main.add(filters, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(main);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

        reload();
    }
}
