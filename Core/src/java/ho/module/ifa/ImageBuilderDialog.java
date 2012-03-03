package ho.module.ifa;

import java.awt.Dimension;

import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;

import javax.swing.JDialog;

class ImageBuilderDialog extends JDialog {

	private static final long serialVersionUID = -3153426395758006044L;

	ImageBuilderDialog(){
		super(HOMainFrame.instance(),HOVerwaltung.instance().getLanguageString("Imagebuilder"),true);
		setSize(800,600);
	}
	
	@Override
	public void setSize(int width, int height) {  
	   super.setSize(width, height);  
		    
	   Dimension screenSize = getParent().getSize();  
	   int x = (screenSize.width - getWidth()) / 2;  
	   int y = (screenSize.height - getHeight()) / 2;  
	    
	   setLocation(getParent().getX()+x, getParent().getY()+y);     
	}
}
