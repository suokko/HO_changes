package de.hattrickorganizer.gui.lineup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A class that is used to overlay panels with green or red color to make selection of positions for
 * the lineup assistant to ignore.
 */
public class LineupAssistantSelectorOverlay extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSelected;
  
	public LineupAssistantSelectorOverlay() {
        super();
        setOpaque(false);
        setLayout(null);
        addMouseListener(this);
    }
    
    @Override
	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Color ppColor;
        if (isSelected) {
        	ppColor = new Color(10, 255, 10, 40); //r,g,b,alpha
        } else {
        	ppColor = new Color(255, 10, 10, 40); //r,g,b,alpha
        }
        
        g.setColor(ppColor);
        g.fillRect(0,0,500,500); //x,y,width,height -big enough, and then some
    }    
	
	
	
	public void mouseClicked(MouseEvent arg0) {
		// Flip color (and selected)
		isSelected = !isSelected;
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
	
	public void setSelected(boolean b) {
		isSelected = b;
		repaint();
	}
	
	public boolean isSelected () {
		return isSelected;
	}
}
