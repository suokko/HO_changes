package ho.module.evilcard.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class EvilCardPanel extends JPanel  {

	private static final long serialVersionUID = 1L;

	
	public EvilCardPanel(){
		initialize();
	}

	private void initialize() {
        	setLayout(new BorderLayout());
        	MainPanel mainPanel = new MainPanel();

            add(new FilterPanel(mainPanel),BorderLayout.NORTH);
            add(mainPanel,BorderLayout.CENTER);
    }
}
