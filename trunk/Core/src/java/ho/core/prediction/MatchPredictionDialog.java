package ho.core.prediction;

import ho.core.gui.HOMainFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;


public class MatchPredictionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public MatchPredictionDialog(MatchEnginePanel panel){
		super(HOMainFrame.instance(),"",true);
		initialize(panel);
	}

	private void initialize(MatchEnginePanel panel) {
		getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        setResizable(true);
        setSize(900, 600);
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
