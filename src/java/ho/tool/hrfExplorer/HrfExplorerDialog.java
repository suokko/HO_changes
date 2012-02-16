package ho.tool.hrfExplorer;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.hattrickorganizer.model.HOVerwaltung;

public class HrfExplorerDialog extends JDialog {
	
	public HrfExplorerDialog(JFrame owner){
		super(owner,true);
		initialize();
	}

	private void initialize() {
		setSize(900,430);
		setLayout(new BorderLayout());
		setTitle(HOVerwaltung.instance().getLanguageString("ArenaSizer"));
		
		add(new HrfExplorer(),BorderLayout.CENTER);
		
	}

}
