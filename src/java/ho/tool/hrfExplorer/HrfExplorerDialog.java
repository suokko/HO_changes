package ho.tool.hrfExplorer;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.hattrickorganizer.model.HOVerwaltung;

public class HrfExplorerDialog extends JDialog {
	
	private static final long serialVersionUID = -6591856825578209977L;

	public HrfExplorerDialog(JFrame owner){
		super(owner,true);
		initialize();
	}

	private void initialize() {
		setSize(1024,768);
		setLayout(new BorderLayout());
		setTitle(HOVerwaltung.instance().getLanguageString("ArenaSizer"));
		
		add(new HrfExplorer(),BorderLayout.CENTER);
		
	}

}
