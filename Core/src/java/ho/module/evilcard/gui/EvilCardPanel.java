package ho.module.evilcard.gui;

import ho.core.gui.CursorToolkit;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class EvilCardPanel extends JPanel  {

	private static final long serialVersionUID = 1L;
	private DetailsTable detailsTable;
    private PlayersPanel playersPanel;
	private JPanel mainPanel;
	private boolean initialized = false;
    
	public EvilCardPanel(){
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						initialize();
					}
				}
			}
		});
	}

	private void initialize() {
		CursorToolkit.startWaitCursor(this);
		try {
			initComponents();
			this.initialized = true;
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
	}
	
	private void initComponents() {
        	setLayout(new BorderLayout());
        	detailsTable = new DetailsTable();
	        playersPanel = new PlayersPanel(detailsTable);
            add(new FilterPanel(playersPanel),BorderLayout.NORTH);
            add(getMainPanel(),BorderLayout.CENTER);
    }
	
	
	private JPanel getMainPanel(){
		if(mainPanel == null){
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
	        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playersPanel, new JScrollPane(detailsTable));
	        splitPane.setResizeWeight(0.5d);
	        mainPanel.add(splitPane);
		}
		return mainPanel;
	}
}
