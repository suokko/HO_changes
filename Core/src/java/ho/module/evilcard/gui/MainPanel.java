package ho.module.evilcard.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


class MainPanel extends JPanel {
    
	private static final long serialVersionUID = 6129609746403702281L;
	private DetailsTable detailsTable = null;
    private PlayersPanel playersPanel = null;

    MainPanel() {
        super();

        setLayout(new BorderLayout());

        detailsTable = new DetailsTable();
        playersPanel = new PlayersPanel(detailsTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playersPanel, new JScrollPane(detailsTable));
        splitPane.setResizeWeight(0.5d);

        add(splitPane);
    }

    public void setFilter(int filterMode) {
        playersPanel.setFilter(filterMode);
    }
}
