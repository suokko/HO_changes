package ho.tool.arenasizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.hattrickorganizer.model.HOVerwaltung;

public class ArenaSizerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private ArenaPanel panel;
	private JPanel historyPanel;
	private ArenaPanel infoPanel;
	private ControlPanel controlPanel;
	private JButton refreshButton = new JButton(HOVerwaltung.instance().getLanguageString("Refresh"));
	private JButton closeButton = new JButton(HOVerwaltung.instance().getLanguageString("Beenden"));
	
	public ArenaSizerDialog(JFrame owner){
		super(owner,true);
		initialize();
	}

	private void initialize() {
		setSize(900,480);
		setLayout(new BorderLayout());
		setTitle(HOVerwaltung.instance().getLanguageString("ArenaSizer"));
		JPanel panelC = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelC.add(getControlPanel());
		add(panelC,BorderLayout.NORTH);
		add(new JScrollPane(getTabbedPane()), BorderLayout.CENTER);
		JPanel tmp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tmp.add(refreshButton);
		tmp.add(closeButton);
		refreshButton.addActionListener(this);
		closeButton.addActionListener(this);
		add(tmp, BorderLayout.SOUTH);
		
	}
	
	private ControlPanel getControlPanel(){
		if(controlPanel == null){
			controlPanel = new ControlPanel();
		}
		return controlPanel;
	}
	
	private JPanel getHistoryPanel(){
		if(historyPanel == null){
			historyPanel = new JPanel();
		}
		return historyPanel;
	}
	
	ArenaPanel getArenaPanel(){
		if(panel == null){
			panel = new ArenaPanel();
		}
		return panel;
	}
	
	ArenaPanel getInfoPanel(){
		if(infoPanel == null){
			infoPanel = new ArenaPanel();
		}
		return infoPanel;
	}
	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
			tabbedPane.addTab(HOVerwaltung.instance().getModel().getStadium().getStadienname(), getInfoPanel());
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Stadion"), getArenaPanel());
			//tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Statistik"), getArenaStatistikPanel());
			
		}
		return tabbedPane;
	}
	
	@Override
	public void setSize(int width, int height) {  
	   super.setSize(width, height);  
		    
	   Dimension screenSize = getParent().getSize();  
	   int x = (screenSize.width - getWidth()) / 2;  
	   int y = (screenSize.height - getHeight()) / 2;  
	    
	   setLocation(getParent().getX()+x, getParent().getY()+y);     
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton){
			dispose();
		} else if(e.getSource() == refreshButton){
			Stadium stadium = getControlPanel().getStadium();
			int[] supporter = getControlPanel().getModifiedSupporter();
            getArenaPanel().reinitArena(stadium, supporter[0],supporter[1],supporter[2]);
            getInfoPanel().reinitArena(HOVerwaltung.instance().getModel().getStadium(), supporter[0],supporter[1],supporter[2]);
		}
	}
}
