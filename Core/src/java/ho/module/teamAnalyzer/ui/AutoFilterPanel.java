// %1955470492:hoplugins.teamAnalyzer.ui%
/*
 * AutoFilterPanel.java
 *
 * Created on 20 settembre 2004, 16.12
 */
package ho.module.teamAnalyzer.ui;

import ho.module.teamAnalyzer.vo.Filter;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;


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
    	Filter filter = TeamAnalyzerPanel.filter;
        homeGames.setSelected(filter.isHomeGames());
        awayGames.setSelected(filter.isAwayGames());
        win.setSelected(filter.isWin());
        draw.setSelected(filter.isDraw());
        defeat.setSelected(filter.isDefeat());
        number.setText(filter.getNumber() + "");
        league.setSelected(filter.isLeague());
        cup.setSelected(filter.isCup());
        qualifier.setSelected(filter.isQualifier());
        friendly.setSelected(filter.isFriendly());
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void setFilter() {
    	Filter filter = TeamAnalyzerPanel.filter;
    	filter.setAwayGames(awayGames.isSelected());
    	filter.setHomeGames(homeGames.isSelected());
    	filter.setWin(win.isSelected());
    	filter.setDefeat(defeat.isSelected());
    	filter.setDraw(draw.isSelected());
    	filter.setLeague(league.isSelected());
    	filter.setCup(cup.isSelected());
    	filter.setFriendly(friendly.isSelected());
    	filter.setQualifier(qualifier.isSelected());
    	filter.setNumber(number.getValue());
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
    	Filter filter = TeamAnalyzerPanel.filter;
        JPanel main = new ImagePanel();

        main.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel filters = new ImagePanel();

        filters.setLayout(new GridLayout(11, 2));
        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Home_Games"))); //$NON-NLS-1$
        homeGames.setSelected(filter.isHomeGames());
        homeGames.setOpaque(false);
        filters.add(homeGames);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Away_Games"))); //$NON-NLS-1$
        awayGames.setSelected(filter.isAwayGames());
        awayGames.setOpaque(false);
        filters.add(awayGames);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Win_Games"))); //$NON-NLS-1$
        win.setSelected(filter.isWin());
        win.setOpaque(false);
        filters.add(win);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Draw_Games"))); //$NON-NLS-1$
        draw.setSelected(filter.isDraw());
        draw.setOpaque(false);
        filters.add(draw);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Defeat_Games"))); //$NON-NLS-1$
        defeat.setSelected(filter.isDefeat());
        defeat.setOpaque(false);
        filters.add(defeat);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.LeagueGame"))); //$NON-NLS-1$
        league.setSelected(filter.isLeague());
        league.setOpaque(false);
        filters.add(league);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.CupGame"))); //$NON-NLS-1$
        cup.setSelected(filter.isCup());
        cup.setOpaque(false);
        filters.add(cup);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.FriendlyGame"))); //$NON-NLS-1$
        friendly.setSelected(filter.isFriendly());
        friendly.setOpaque(false);
        filters.add(friendly);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.QualifierGame"))); //$NON-NLS-1$
        qualifier.setSelected(filter.isQualifier());
        qualifier.setOpaque(false);
        filters.add(qualifier);

        filters.add(new JLabel(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Max_Number"))); //$NON-NLS-1$
        number.setText(filter.getNumber() + "");
        filters.add(number);

        main.add(filters, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(main);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

        reload();
    }
}
