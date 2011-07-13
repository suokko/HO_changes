package de.hattrickorganizer.gui.lineup;

import gui.HOColorName;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.theme.ThemeManager;

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
        if (isSelected) {
        	g.setColor(ThemeManager.getColor(HOColorName.SEL_OVERLAY_SELECTION_BG));//new Color(10, 255, 10, 40); //r,g,b,alpha
        } else {
        	g.setColor(ThemeManager.getColor(HOColorName.SEL_OVERLAY_BG));//new Color(255, 10, 10, 40); //r,g,b,alpha
        }
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
