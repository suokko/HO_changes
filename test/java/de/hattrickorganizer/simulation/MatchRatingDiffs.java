package de.hattrickorganizer.simulation;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.gui.menu.option.RatingOffsetPanel;

/*
 * Created on 5-lug-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatchRatingDiffs extends HOSetup implements WindowListener {

	public static void main(String[] args) {
		new MatchRatingDiffs();
	}

	public MatchRatingDiffs() {
		RatingOffsetPanel panel = new RatingOffsetPanel();
		panel.calculate();
		JFrame f = new JFrame();
		f.getContentPane().add(panel);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(this);
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		System.exit(-1);		
	}

	public void windowClosing(WindowEvent arg0) {
		System.exit(-1);
		
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
