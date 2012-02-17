package ho.module.evilcard;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


class MainPanel extends JPanel {
    
	private static final long serialVersionUID = 6129609746403702281L;
	private DetailsPanel detailsPanel = null;
    private PlayersPanel playersPanel = null;

    MainPanel() {
        super();

        setLayout(new BorderLayout());

        detailsPanel = new DetailsPanel();
        playersPanel = new PlayersPanel(detailsPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playersPanel, detailsPanel);
        splitPane.setResizeWeight(0.5d);

        add(splitPane);
    }

    public void setFilter(int filterMode) {
        playersPanel.setFilter(filterMode);
    }
}
