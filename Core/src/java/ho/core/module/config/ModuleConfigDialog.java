package ho.core.module.config;

import ho.core.model.HOVerwaltung;
import ho.core.module.IModule;
import ho.core.plugins.GUIPluginWrapper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ModuleConfigDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = -6012059855852713150L;
	private IModule module;
	JButton okButton;
//	JButton cancelButton;
	
	public ModuleConfigDialog(JDialog owner, IModule module){
		super(owner,module.getDescription());
		this.module = module;
		initialize();
	}
	

	private void initialize() {
		setSize(300,500);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(module.createConfigPanel()),BorderLayout.CENTER);
		getContentPane().add(createButtons(),BorderLayout.SOUTH);
	}


	@Override
	public void setSize(int width, int height) {  
	   super.setSize(width, height);  
		    
	   Dimension screenSize = getParent().getSize();  
	   int x = (screenSize.width - getWidth()) / 2;  
	   int y = (screenSize.height - getHeight()) / 2;  
	    
	   setLocation(getParent().getX()+x, getParent().getY()+y);     
	}
	
	
    private JPanel createButtons() {
        JPanel buttonPanel = GUIPluginWrapper.instance().createImagePanel();
        ((FlowLayout) buttonPanel.getLayout()).setAlignment(FlowLayout.CENTER);

        okButton = new JButton(HOVerwaltung.instance().getLanguageString("Speichern"));
        okButton.addActionListener(this);

//        cancelButton = new JButton(HOVerwaltung.instance().getLanguageString("Abbrechen"));
//        cancelButton.addActionListener(this);

        buttonPanel.add(okButton);
       // buttonPanel.add(cancelButton);
        return buttonPanel;
    }


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton){
			ModuleConfig.instance().save();
			dispose();
		}
		
	}
}
