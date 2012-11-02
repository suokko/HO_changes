// %3459649550:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.datatype.CBItem;
import ho.core.gui.CursorToolkit;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.module.matches.SpielePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class ArenaStatistikPanel extends ImagePanel {
	private static final long serialVersionUID = 2679088584924124183L;
	private ArenaStatistikTable arenaStatistikTable;
	private JComboBox matchFilterComboBox;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	/**
	 * Creates a new ArenaStatistikPanel object.
	 */
	public ArenaStatistikPanel() {
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
					if (needsRefresh) {
						update();
					}
				}
			}
		});
	}

	private void initialize() {
		CursorToolkit.startWaitCursor(this);
		try {
			initComponents();
			addListeners();
			this.initialized = true;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		ImagePanel panel = new ImagePanel(null);

		matchFilterComboBox = new JComboBox(getMatchFilterItems());
		matchFilterComboBox.setFont(matchFilterComboBox.getFont().deriveFont(Font.BOLD));
		matchFilterComboBox.setSize(200, 25);
		matchFilterComboBox.setLocation(10, 5);
		panel.setPreferredSize(new Dimension(240, 35));
		panel.add(matchFilterComboBox);
		add(panel, BorderLayout.NORTH);

		arenaStatistikTable = new ArenaStatistikTable(UserParameter.instance().spieleFilter);
		add(new JScrollPane(arenaStatistikTable), BorderLayout.CENTER);

		// Nur Pflichtspiele ist default
		matchFilterComboBox.setSelectedIndex(1);
	}

	private void addListeners() {
		RefreshManager.instance().registerRefreshable(new Refreshable() {

			@Override
			public final void reInit() {
				if (isShowing()) {
					update();
				} else {
					needsRefresh = true;
				}
			}

			@Override
			public void refresh() {
			}
		});

		this.matchFilterComboBox.addItemListener(new ItemListener() {

			@Override
			public final void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// Änderung der Tabelle -> Anderer Filter!
					update();
				}
			}
		});
	}

	private CBItem[] getMatchFilterItems() {
		CBItem[] matchFilterItems = {
				new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneSpiele"),
						SpielePanel.NUR_EIGENE_SPIELE),
				new CBItem(HOVerwaltung.instance().getLanguageString("NurEigenePflichtspiele"),
						SpielePanel.NUR_EIGENE_PFLICHTSPIELE),
				new CBItem(HOVerwaltung.instance().getLanguageString("NurEigenePokalspiele"),
						SpielePanel.NUR_EIGENE_POKALSPIELE),
				new CBItem(HOVerwaltung.instance().getLanguageString("NurEigeneLigaspiele"),
						SpielePanel.NUR_EIGENE_LIGASPIELE),
				new CBItem(HOVerwaltung.instance()
						.getLanguageString("NurEigeneFreundschaftsspiele"),
						SpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE) };
		return matchFilterItems;
	}

	private void update() {
		CursorToolkit.startWaitCursor(this);
		try {
			if (matchFilterComboBox.getSelectedIndex() > -1) {
				arenaStatistikTable.refresh(((CBItem) matchFilterComboBox.getSelectedItem())
						.getId());
			}
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
		this.needsRefresh = false;
	}
}
