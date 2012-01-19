// %1614414392:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import java.awt.GridLayout;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.model.SpielerTableRenderer;


class DoppelLabel extends JPanel {
	
	private static final long serialVersionUID = 4801107348466403035L;
	
    public DoppelLabel() {
        setLayout(new GridLayout(1, 2));
        setOpaque(true);
        setBackground(SpielerTableRenderer.SELECTION_BG);
    }


 
}
