package ho.module.evilcard.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class EvilCardPanel extends JPanel  {

	private static final long serialVersionUID = 1L;
	private DetailsTable detailsTable = null;
    private PlayersPanel playersPanel = null;
	private JPanel mainPanel=null;
    
	public EvilCardPanel(){
		initialize();
	}

	private void initialize() {
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
