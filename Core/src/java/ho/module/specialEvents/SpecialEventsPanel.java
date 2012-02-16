package ho.module.specialEvents;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import plugins.IRefreshable;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.templates.ImagePanel;

public class SpecialEventsPanel extends ImagePanel implements IRefreshable {

	private static final long serialVersionUID = 1L;
	private static SpecialEventsTable specialEventsTable;

	SpecialEventsPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
        JPanel filterPanel = new FilterPanel();
        specialEventsTable = new SpecialEventsTable();
        specialEventsTable.setTableModel(specialEventsTable.getSEModel());
        JScrollPane matchArea = new JScrollPane(specialEventsTable);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel, matchArea);
        splitPane.setDividerLocation(60);
        splitPane.setDividerSize(5);
        splitPane.setContinuousLayout(true);
        add(splitPane);
        RefreshManager.instance().registerRefreshable(this);
    }

	public void refresh() {
		newTableModel();
	}

	public static void newTableModel() {
		specialEventsTable.removeAll();
		specialEventsTable.setTableModel(specialEventsTable.getSEModel());
	}

}
